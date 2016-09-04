package it.emarolab.cagg.debugging.result2XML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "tag")
@XmlType(propOrder = { "id", "tag"}) // optional
public class CaggXmlAction {

	/*   SEMANTIC RESULT HIERARCHICAL STRUCTURE
	 + + . Set< CaggXmlAction> action;
	 + + + . Long id;
	 + + + . List< String> tags;
	 */
	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private Long id;
	@XmlElementWrapper(name = "tag")
	@XmlElement(name = "hint")
	private List< String> tag;
	
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlAction() {} // for XML interface
	
	public void initialise( Long id, List<?> tags) {
		this.setId( id);
		
		List< String> xmlTags = new ArrayList<String>();
		for( Object o : tags)
			if( o instanceof String)
				xmlTags.add( (String) o);
			else xmlTags.add( o.toString());
		this.setTag( xmlTags);
	}

	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public Long getId() {
		return id;
	}
	@XmlTransient
	public List<String> getTag() {
		return tag;
	}
	
	/* ##################################################################################
	   ################################ SETTERS #########################################
	 */
	public void setId(Long id) {
		this.id = id;
	}
	public void setTag(List<String> tags) {
		this.tag = tags;
	}		
}
