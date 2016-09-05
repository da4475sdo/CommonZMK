package utils.file.fineFile;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java Server-Side Example for Fine Uploader S3.
 * Maintained by Widen Enterprises.
 *
 * This example:
 *  - handles non-CORS environments
 *  - handles delete file requests via the DELETE method
 *  - signs policy documents (simple uploads) and REST requests
 *    (chunked/multipart uploads)
 *  - handles both version 2 and version 4 signatures
 *
 * Requirements:
 *  - Java 1.5 or newer
 *  - Google GSon
 *  - Amazon Java SDK (only if utilizing the delete file feature)
 *
 * If you need to install the AWS SDK, see http://docs.aws.amazon.com/aws-sdk-php-2/guide/latest/installation.html.
 */
public class S3Uploads extends HttpServlet
{
    // This assumes your secret key is available in an environment variable.
    // It is needed to sign policy documents.
    final static String AWS_SECRET_KEY = System.getenv("AWS_SECRET_KEY");

    // You will need to use your own public key here.
    final static String AWS_PUBLIC_KEY = "AKIAJLRYC5FTY3VRRTDA";
    
    private static String basePath=S3Uploads.class.getResource("/").getPath();


    // Main entry point for POST requests from Fine Uploader.  This currently assumes delete file requests use the
    // default method of DELETE, but that can be adjusted.
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
    	  req.setCharacterEncoding("UTF-8");
    	  DiskFileItemFactory factory = new DiskFileItemFactory();
    	  ServletFileUpload upload = new ServletFileUpload(factory);
    	  try {
    	   List<?> items = upload.parseRequest(req);
    	   Iterator<?> itr = items.iterator();
    	   while (itr.hasNext()) {
    	    FileItem item = (FileItem) itr.next();
    	    if (item.isFormField()) {
    	     System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
    	    } else {
    	     if (item.getName() != null && !item.getName().equals("")) {
	    	      System.out.println("上传文件的大小:" + item.getSize());
	    	      System.out.println("上传文件的类型:" + item.getContentType());
	    	      // item.getName()返回上传文件在客户端的完整路径名称
	    	      System.out.println("上传文件的名称:" + item.getName());
	    	      File tempFile = new File(item.getName());
	    	      File file = new File(basePath+"/content/"+tempFile);
	    	      item.write(file);
	    	      req.setAttribute("upload.message", "上传文件成功！");
    	     }else{
    	    	  req.setAttribute("upload.message", "没有选择上传文件！");
    	     }
    	    }
    	   }
    	  }catch(FileUploadException e){
    		  e.printStackTrace();
    	  } catch (Exception e) {
    		  e.printStackTrace();
    		  req.setAttribute("upload.message", "上传文件失败！");
    	  }
	 }

    // Main entry point for DELETE requests sent by Fine Uploader.
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String key = req.getParameter("key");
        String bucket = req.getParameter("bucket");

        resp.setStatus(200);
    }

    // Called by the main POST request handler if Fine Uploader has asked for an item to be signed.  The item may be a
    // policy document or a string that represents multipart upload request headers.
    private void handleSignatureRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setStatus(200);

        JsonParser jsonParser = new JsonParser();
        JsonElement contentJson = jsonParser.parse(req.getReader());
        JsonObject jsonObject = contentJson.getAsJsonObject();

        if (req.getQueryString() != null && req.getQueryString().contains("v4=true")) {
            handleV4SignatureRequest(jsonObject, contentJson, req, resp);
        }
        else {
            handleV2SignatureRequest(jsonObject, contentJson, req, resp);
        }

        resp.setStatus(200);
    }

    private void handleV2SignatureRequest(JsonObject payload, JsonElement contentJson, HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String signature;
        JsonElement headers = payload.get("headers");
        JsonObject response = new JsonObject();

        try
        {
            // If this is not a multipart upload-related request, Fine Uploader will send a policy document
            // as the value of a "policy" property in the request.  In that case, we must base-64 encode
            // the policy document and then sign it. The will include the base-64 encoded policy and the signed policy document.
            if (headers == null)
            {
                String base64Policy = base64EncodePolicy(contentJson);
                signature = sign(base64Policy);

                // Validate the policy document to ensure the client hasn't tampered with it.
                // If it has been tampered with, set this property on the response and set the status to a non-200 value.
//                response.addProperty("invalid", true);

                response.addProperty("policy", base64Policy);
            }

            // If this is a request to sign a multipart upload-related request, we only need to sign the headers,
            // which are passed as the value of a "headers" property from Fine Uploader.  In this case,
            // we only need to return the signed value.
            else
            {
                signature = sign(headers.getAsString());
            }

            response.addProperty("signature", signature);
            resp.getWriter().write(response.toString());
        }
        catch (Exception e)
        {
            resp.setStatus(500);
        }
    }

    private void handleV4SignatureRequest(JsonObject payload, JsonElement contentJson, HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String signature = null;
        JsonElement headers = payload.get("headers");
        JsonObject response = new JsonObject();

        try
        {
            // If this is not a multipart upload-related request, Fine Uploader will send a policy document
            // as the value of a "policy" property in the request.  In that case, we must base-64 encode
            // the policy document and then sign it. The will include the base-64 encoded policy and the signed policy document.
            if (headers == null)
            {
                String base64Policy = base64EncodePolicy(contentJson);
                JsonArray conditions = payload.getAsJsonArray("conditions");
                String credentialCondition = null;
                for (int i = 0; i < conditions.size(); i++) {
                    JsonObject condition = conditions.get(i).getAsJsonObject();
                    JsonElement value = condition.get("x-amz-credential");
                    if (value != null) {
                        credentialCondition = value.getAsString();
                        break;
                    }
                }

                // Validate the policy document to ensure the client hasn't tampered with it.
                // If it has been tampered with, set this property on the response and set the status to a non-200 value.
//                response.addProperty("invalid", true);


                Pattern pattern = Pattern.compile(".+\\/(.+)\\/(.+)\\/s3\\/aws4_request");
                Matcher matcher = pattern.matcher(credentialCondition);
                matcher.matches();
                signature = getV4Signature(matcher.group(1), matcher.group(2), base64Policy);

                response.addProperty("policy", base64Policy);
            }

            // If this is a request to sign a multipart upload-related request, we only need to sign the headers,
            // which are passed as the value of a "headers" property from Fine Uploader.  In this case,
            // we only need to return the signed value.
            else
            {
                Pattern pattern = Pattern.compile(".+\\n.+\\n(\\d+)\\/(.+)\\/s3\\/aws4_request\\n(.+)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(headers.getAsString());
                matcher.matches();
                String canonicalRequest = matcher.group(3);
                String hashedCanonicalRequest = hash256(canonicalRequest);
                String stringToSign = headers.getAsString().replaceAll("(?s)(.+s3\\/aws4_request\\n).+", "$1" + hashedCanonicalRequest);

                // Validate the policy document to ensure the client hasn't tampered with it.
                // If it has been tampered with, set this property on the response and set the status to a non-200 value.
//                response.addProperty("invalid", true);

                signature = getV4Signature(matcher.group(1), matcher.group(2), stringToSign);
            }

            response.addProperty("signature", signature);
            resp.getWriter().write(response.toString());
        }
        catch (Exception e)
        {
            resp.setStatus(500);
        }
    }

    // Called by the main POST request handler if Fine Uploader has indicated that the file has been
    // successfully sent to S3.  You have the opportunity here to examine the file in S3 and "fail" the upload
    // if something in not correct.
    private void handleUploadSuccessRequest(HttpServletRequest req, HttpServletResponse resp)
    {
        String key = req.getParameter("key");
        String uuid = req.getParameter("uuid");
        String bucket = req.getParameter("bucket");
        String name = req.getParameter("name");

        resp.setStatus(200);

        System.out.println(String.format("Upload successfully sent to S3!  Bucket: %s, Key: %s, UUID: %s, Filename: %s",
                bucket, key, uuid, name));
    }

    private String getV4Signature(String date, String region, String stringToSign) throws Exception {
        byte[] kSecret = ("AWS4" + AWS_SECRET_KEY).getBytes("UTF8");
        byte[] kDate    = sha256Encode(date, kSecret);
        byte[] kRegion  = sha256Encode(region, kDate);
        byte[] kService = sha256Encode("s3", kRegion);
        byte[] kSigning = sha256Encode("aws4_request", kService);
        byte[] kSignature = sha256Encode(stringToSign, kSigning);

        return null;
    }

    private byte[] sha256Encode(String data, byte[] key) throws Exception  {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    private String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    private String base64EncodePolicy(JsonElement jsonElement) throws UnsupportedEncodingException
    {
        String policyJsonStr = jsonElement.toString();

        return "";
    }

    private String sign(String toSign) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
    {
        Mac hmac = Mac.getInstance("HmacSHA1");
        hmac.init(new SecretKeySpec(AWS_SECRET_KEY.getBytes("UTF-8"), "HmacSHA1"));
       

        return "";
    }
}
