package utils.xml;

import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class XMLUtils {
	//Ω‚ŒˆXML
	public static Element parserXml(String fileName) {   
		SAXBuilder builder=new SAXBuilder(false);    
		try {
			String basePath=XMLUtils.class.getResource("/").getPath();
			Document document=builder.build(basePath+fileName);   
			Element employees=document.getRootElement();    
			return  employees;   
		}catch (JDOMException ex) {   
			ex.printStackTrace();
			return null;
		}catch (IOException ex) {   
		    ex.printStackTrace();
		    return null;
		}    
	}
}
