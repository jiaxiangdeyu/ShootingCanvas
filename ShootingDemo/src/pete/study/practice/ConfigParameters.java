package pete.study.practice;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigParameters {
	
	private static final String configFileName = "config.xml";
	private static double dpiValue = -1.0;
	
	// TODO: DPI should given firstly.
	public static double getScreenDPI() {
		if (dpiValue > 0)
			return dpiValue;
		// int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution(); // This always return 96
		String configFilePath = System.getProperty("user.dir") + File.separator + configFileName;
		String sizeString = "14"; // Screen size by default
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		try {
			 DocumentBuilder db = dbf.newDocumentBuilder();  
			 Document doc = db.parse(configFilePath); 
			 NodeList ndl = doc.getElementsByTagName("screen");
			 if (ndl.getLength() >= 1) {
				 Node screenNode = ndl.item(0);
				 sizeString = screenNode.getFirstChild().getNodeValue();
			 }
			 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Fail to get screen DPI.");
		}
		finally {
			
		}
		double size = Double.parseDouble(sizeString);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		dpiValue = Math.sqrt(width * width + height * height) / size;
		return dpiValue;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("The screen DPI is : " + ConfigParameters.getScreenDPI());
	}

}
