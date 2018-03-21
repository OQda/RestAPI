package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
	
    @RequestMapping(value="/xml2json",
    		method=RequestMethod.POST,
    		consumes="application/xml",
    		produces="application/json")
    public String xtoj(HttpServletRequest request, 
			@RequestBody String xml) throws IOException {
    	
    	// xml 태그 형식이 아닌 경우 null 전송
    	if (!tagInValue(xml)) return "";    	
    	
    	// <?xml version="1.0" encoding="UTF-8"?> 제거 후 임시태그 추가
    	String parsehelper = xml;    	
    	if (xml.contains("<?xml")) {
    		parsehelper = xml.substring(0,xml.indexOf("<?xml"))+xml.substring(xml.indexOf("?>")+3);
    	}
    	parsehelper = "<xxtemptag>"+parsehelper+"</xxtemptag>";
    	
//    	try {
//			return JsonConverter.convertXml(parsehelper);
//		} catch (XmlParseException e) {
//			return "";
//		}
    	return dualConverter.xml2json(parsehelper);    	
    }
    
    @RequestMapping(value="/json2xml",
    		method=RequestMethod.POST,
    		consumes="application/json",
    		produces="application/xml")
    public String jtox(HttpServletRequest request, 
			@RequestBody String json) throws IOException {
    	    	    		
    	return "<xxtemptag>"+dualConverter.json2xml(json)+"</xxtemptag>";
  
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
    
    boolean tagInValue(String xml) {
    	if ( xml.contains("<") && xml.contains(">") && xml.contains("</") ) {
    		return true;
    	}else {
    		return false;
    	}    	
    }
   
}
