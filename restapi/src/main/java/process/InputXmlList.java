package process;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

public class InputXmlList {
	
	private String xmlToJson;	

	public String getXmlToJson() {
		return xmlToJson;
	}

	public void setXmlToJson(String xml) {
		
		JSON xmlJsonObj = new XMLSerializer().read(xml);
		String jsonPrint = xmlJsonObj.toString();
		this.xmlToJson = jsonPrint;
	}

}
