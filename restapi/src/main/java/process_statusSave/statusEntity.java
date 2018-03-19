package process_statusSave;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class statusEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String xmltojson;
	String jsontoxml;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getXmltojson() {
		return xmltojson;
	}
	
	public void setXmltojson(String status) {
		this.xmltojson = status;
	}
	
	public String getJsontoxml() {
		return jsontoxml;
	}
	
	public void setJsontoxml(String status) {
		this.jsontoxml = status;
	}
	
	
	
}