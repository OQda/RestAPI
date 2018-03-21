package process_availableCheck;

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

}
