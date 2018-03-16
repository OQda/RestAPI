package process_jsonToxml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXResult;

import com.sun.xml.internal.ws.util.xml.StAXSource;

import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLStreamWriter;

public class XmlConverter {	
	
	public static String convertJson(String json) throws XMLStreamException, TransformerConfigurationException, TransformerException, IOException {
		
		InputStream input = new ByteArrayInputStream(json.getBytes("UTF-8"));	
		StringWriter stringOut = new StringWriter();	
		
		XMLInputFactory inputfactory = new JsonXMLInputFactory();
		inputfactory.setProperty(JsonXMLInputFactory.PROP_MULTIPLE_PI, false);
		Source source = new StAXSource(inputfactory.createXMLStreamReader(input), false);
		
		XMLOutputFactory outputfactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = outputfactory.createXMLStreamWriter(stringOut);
		writer = new PrettyXMLStreamWriter(writer);
		Result result = new StAXResult(writer);
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		transformerfactory.newTransformer().transform(source, result);	
						
		input.close();
		
		return stringOut.toString();
	}

}
