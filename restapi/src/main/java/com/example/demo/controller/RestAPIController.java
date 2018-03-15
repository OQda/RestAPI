package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.asilvestre.jpurexml.XmlParseException;

import process.InputXmlList;
import process.JsonConverter;

@RestController
public class RestAPIController {
	
	InputXmlList ixl = new InputXmlList();
	JsonConverter converter = new JsonConverter();

    @RequestMapping(value="/xml2json",
    		method=RequestMethod.POST,
    		consumes="application/xml",
    		produces="application/json")
    public String xtoj(HttpServletRequest request, 
			@RequestBody String xml) throws IOException, XmlParseException {
//    	Map<String,Object> map = new HashMap<String,Object>();
//    	map.put("xml2json", "success");
    	
//    	ixl.setXmlToJson(xml);    	
//    	System.out.println(ixl.getXmlToJson());
//    	String jsonPrint = ixl.getXmlToJson();
    	
//      System.out.println("xml2json success \n"+xml);
//      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    	return converter.convertXml(xml);
    }
    
    @RequestMapping(value="/json2xml",
    		method=RequestMethod.POST,
    		consumes="application/json",
    		produces="application/xml")
    public ResponseEntity<String> jtox(HttpServletRequest request, 
			@RequestBody String json) {
    	String xml = "<?xml version='1.0' encoding='utf-8'?>"; 
    	xml += "<service></service>";
        System.out.println("json2xml success \n"+json);
        return new ResponseEntity<String>(xml, HttpStatus.OK);
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