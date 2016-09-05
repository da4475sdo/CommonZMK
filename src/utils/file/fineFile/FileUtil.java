package utils.file.fineFile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUtil {
    // Main entry point for POST requests from Fine Uploader.  This currently assumes delete file requests use the
    // default method of DELETE, but that can be adjusted.
    public static void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
    	  String basePath=req.getSession().getServletContext().getRealPath("");
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
	    	      File file = new File(basePath+"\\content\\"+tempFile);
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
}
