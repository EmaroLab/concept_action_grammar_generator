package it.emarolab.cagg.debugging.result2XML;

import java.io.File;
import java.io.StringWriter;

import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "cagg.xml.testEvaluation")
@XmlType(propOrder = { "testInfo", "evaluations"})
public class CaggXmlManager{
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private CaggXMLTestDescriptor testInfo;
	private CaggXmlResultList evaluations;

	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlManager() {} // for XML interface

	public void initialise( CaggXMLTestDescriptor info, CaggXmlResultList result) {
		this.testInfo = info;
		this.evaluations = result;
	}

	/* ##################################################################################
	   ############################## XML INTERFACE #####################################
	 */
	public void printXml(){
		printXml( false);
	}
	public void printXml( Boolean useErrStream){
		try {
			JAXBContext context = getJAXBContext();
			Marshaller m = context.createMarshaller();
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if( useErrStream)
				m.marshal( this, System.err);
			else m.marshal( this, System.out);
		} catch (JAXBException e) {
			UILog.error( e);
		} 
	}
	public String toXml(){
		try {
			JAXBContext context = getJAXBContext();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal( this, sw);
			return sw.toString();
		} catch (JAXBException e) {
			UILog.error( e);
		} 
		return null;
	}
	public File toFile( String filePath){
		try {
			JAXBContext context = getJAXBContext();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File( filePath);
			m.marshal( this, file);
			return file;
		} catch (JAXBException e) {
			UILog.error( e);
		} 
		return null;
	}
	public static CaggXmlManager fromFile( String filePath){
		try {
			File file = new File( filePath);
			JAXBContext context = getJAXBContext();
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			return (CaggXmlManager) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			UILog.error( e);
		}
		return null;
	}
	private static JAXBContext getJAXBContext(){
		try {
			return JAXBContext.newInstance( CaggXmlManager.class);
		} catch (JAXBException e) {
			UILog.error( e);
		}
		return null;
	}

	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public CaggXMLTestDescriptor getTestInfo() {
		return testInfo;
	}
	public CaggXmlResultList getEvaluations() {
		return evaluations;
	}

	/* ##################################################################################
	   ################################ SETTERS #########################################
	 */
	public void setTestInfo(CaggXMLTestDescriptor info) {
		this.testInfo = info;
	}
	public void setEvaluations(CaggXmlResultList evaluationResult) {
		this.evaluations = evaluationResult;
	}

	@Override
	public String toString() {
		return "Cagg Xml Test logging: " + DebuggingDefaults.SYS_LINE_SEPARATOR 
				+ "\t testInfo= " + testInfo + DebuggingDefaults.SYS_LINE_SEPARATOR
				+ "\t evaluations=" + evaluations;
	}
}
