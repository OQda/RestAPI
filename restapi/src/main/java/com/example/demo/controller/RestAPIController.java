package com.example.demo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.asilvestre.jpurexml.XmlParseException;

import process_jsonToxml.XmlConverter;
import process_xmlTojson.JsonConverter;

@RestController
public class RestAPIController {
	
	JsonConverter json_converter = new JsonConverter();
	XmlConverter xml_converter = new XmlConverter();

    @RequestMapping(value="/xml2json",
    		method=RequestMethod.POST,
    		consumes="application/xml",
    		produces="application/json")
    public String xtoj(HttpServletRequest request, 
			@RequestBody String xml) throws IOException, XmlParseException {
//    	Map<String,Object> map = new HashMap<String,Object>();
//    	map.put("xml2json", "success");
    	
//      System.out.println("xml2json success \n"+xml);
//      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    	return json_converter.convertXml(xml);
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
    	
    	return xml_converter.convertJson(json); 
  
    }
    
    @RequestMapping(value="/status",
    		method=RequestMethod.GET,
    		produces="application/json")
    public ResponseEntity<Map<String,Object>> status() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("status", "success");
        
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
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

}
