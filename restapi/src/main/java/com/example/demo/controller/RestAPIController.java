package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import process_jsonToxml.XmlConverter;
import process_statusSave.statusEntity;
import process_statusSave.statusRepository;
import process_xmlToFromjson.dualConverter;
import process_xmlTojson.JsonConverter;

@SpringBootApplication
@EntityScan("process_statusSave")
@EnableJpaRepositories("process_statusSave")
@RestController
public class RestAPIController {	

    @Autowired
    private statusRepository repository;
	
	JsonConverter json_converter = new JsonConverter();
	XmlConverter xml_converter = new XmlConverter();

    @RequestMapping(value="/xml2json",
    		method=RequestMethod.POST,
    		consumes="application/xml",
    		produces="application/json")
    public String xtoj(HttpServletRequest request, 
			@RequestBody String xml) throws IOException {
//    	Map<String,Object> map = new HashMap<String,Object>();
//    	map.put("xml2json", "success");
    	
//      System.out.println("xml2json success \n"+xml);
//      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    	
//    	return json_converter.convertXml(xml);
    	
    	return dualConverter.xml2json(xml);
    }
    
    @RequestMapping(value="/json2xml",
    		method=RequestMethod.POST,
    		consumes="application/json",
    		produces="application/xml")
    public String jtox(HttpServletRequest request, 
			@RequestBody String json) throws XMLStreamException, TransformerConfigurationException, TransformerException, IOException {
    	
//    	String xml = "<?xml version='1.0' encoding='utf-8'?>"; 
//    	xml += "<service></service>";
//        System.out.println("json2xml success \n"+json);
//    	return xml;
    	
//    	return xml_converter.convertJson(json);
//    	System.out.println(dual_converter.json2xml(json));
    	
    	return dualConverter.json2xml(json);
  
    }
    
    @RequestMapping(value="/status",
    		method=RequestMethod.GET,
    		produces="application/json")
    public ResponseEntity<Map<String,Map<String,Object>>> status() {
    	Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();    	
		
    	Map<String,Object> xtojMap = new HashMap<String,Object>();		
		xtojMap.put("success", repository.countByXmltojson("success"));
		xtojMap.put("fail", repository.countByXmltojson("fail"));
		
		Map<String,Object> jtoxMap = new HashMap<String,Object>();
		jtoxMap.put("success", repository.countByJsontoxml("success"));
		jtoxMap.put("fail", repository.countByJsontoxml("fail"));
		
		map.put("XML-to-Json", xtojMap);
		map.put("Json-to-XML", jtoxMap);
        
        return new ResponseEntity<Map<String,Map<String,Object>>>(map, HttpStatus.OK);
    }
    
    @RequestMapping(value="/uploadFile",
    		method=RequestMethod.POST,
    		produces="application/xml")
    public ResponseEntity<String> uploadJsonFile(HttpServletRequest request,
    		@RequestParam("file") MultipartFile file) throws IOException {
    	String json = new String(file.getBytes());
    	System.out.println("uploadJsonFile success \n" + json);
    	
        return new ResponseEntity<String>(json, HttpStatus.OK);
    }    
    
    @RequestMapping(value="/mark", method=RequestMethod.GET)
    public statusEntity test(statusEntity testdata) {
    	
    	statusEntity godata = repository.save(testdata);
    	
    	return godata;
    }
   
}
