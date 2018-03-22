package process_availableCheck;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class simpleCheck {
	
    public static boolean tagInValue(String xml) {
    	if ( xml.contains("<") && xml.contains(">") && xml.contains("</") ) {
    		return true;
    	}else {
    		return false;
    	}    	
    }
    
    public static boolean startInBrace(String json) {
    	if ( json.contains("{") ) {
    		if( json.trim().startsWith("{") ) {
    			return true;
    		}else {
    			return false;
    		}    		
    	}else {
    		return false;
    	}
    }
    
    public static boolean endInBrace(String json) {
    	if ( json.lastIndexOf("}") == json.length()-1 ) {    		
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public static boolean jsonValidCheck(String json) {
    	try {
    		final ObjectMapper mapper = new ObjectMapper();
    		mapper.readTree(json);
    		return true;
    	} catch (IOException e) {
    		return false;
    	}
    }

}
