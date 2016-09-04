package it.emarolab.cagg.debugging.result2XML;

import it.emarolab.cagg.debugging.DebuggingText;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "data", "userInput", "grammarFilePath", "evaluatorType", "formatterType", "grammarType", 
		"computationTime", "evaluationTime", "formattingTime", "timeOut", 
		"feasibleSolutions", "unfeasibleSolutions", "trueSolution", "falseSolution", 
		"maxActionOrder", "bestSolutionIdx",
		"unreasonableWorld"})
public class CaggXMLTestDescriptor {
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	// info
	private String data;
	private String userInput;
	private String grammarFilePath;
	private String evaluatorType;
	private String formatterType;
	private String grammarType;
	// time in secnds
	private Float computationTime;  // for all the procedure (evaluation+formatting+logging+visualising+....)
	private Float evaluationTime;
	private Float formattingTime;
	private Float timeOut;
	// number of (size of vector)
	private Integer feasibleSolutions;
	private Integer unfeasibleSolutions;
	private Integer trueSolution;
	private Integer falseSolution;
	private Integer maxActionOrder;
	private Integer bestSolutionIdx; // base on maxActionOrder (indeed also on the frist found)
	// the actual string values
	@XmlElementWrapper(name = "unreasonable")
	@XmlElement(name = "world")
	private Set< String> unreasonableWorld;
		
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	// Everything should be initialised manually through setter.
	public CaggXMLTestDescriptor() {} // for XML interface

	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public String getData() {
		return data;
	}
	public String getUserInput() {
		return userInput;
	}
	public String getGrammarFilePath() {
		return grammarFilePath;
	}
	public String getEvaluatorType() {
		return evaluatorType;
	}
	public String getFormatterType() {
		return formatterType;
	}
	public String getGrammarType() {
		return grammarType;
	}
	public Float getComputationTime() {
		return computationTime;
	}
	public Float getEvaluationTime() {
		return evaluationTime;
	}
	public Float getFormattingTime() {
		return formattingTime;
	}
	public Float getTimeOut() {
		return timeOut;
	}
	public Integer getFeasibleSolutions() {
		return feasibleSolutions;
	}
	public Integer getUnfeasibleSolutions() {
		return unfeasibleSolutions;
	}
	public Integer getTrueSolution() {
		return trueSolution;
	}
	public Integer getFalseSolution() {
		return falseSolution;
	}
	@XmlTransient // for a bug of eclipse that make the field printed twice
	public Set<String> getUnreasonableWorlds() {
		return unreasonableWorld;
	}
	public Integer getMaxActionOrder() {
		return maxActionOrder;
	}
	public Integer getBestSolutionIdx(){
		return bestSolutionIdx;
	}

	/* ##################################################################################
	   ################################ SETTERS #########################################
	 */
	public void setData(String data) {
		if( data == null)
			this.data = DebuggingText.getFormattedDateMilli();
		else this.data = data;
	}
	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
	public void setGrammarFilePath(String grammarFilePath) {
		this.grammarFilePath = grammarFilePath;
	}
	public void setEvaluatorType(String evaluatorType) {
		this.evaluatorType = evaluatorType;
	}
	public void setFormatterType(String formatterType) {
		this.formatterType = formatterType;
	}
	public void setGrammarType(String grammarType) {
		this.grammarType = grammarType;
	}
	public void setComputationTime(Float computationTime) {
		this.computationTime = computationTime;
	}
	public void setEvaluationTime(Float evaluationTime) {
		this.evaluationTime = evaluationTime;
	}
	public void setFormattingTime(Float formattingTime) {
		this.formattingTime = formattingTime;
	}
	public void setTimeOut(Float timeOut) {
		this.timeOut = timeOut;
	}
	public void setFeasibleSolutions(Integer feasibleSolutions) {
		this.feasibleSolutions = feasibleSolutions;
	}
	public void setUnfeasibleSolutions(Integer unfeasibleSolutions) {
		this.unfeasibleSolutions = unfeasibleSolutions;
	}
	public void setTrueSolution(Integer trueSolution) {
		this.trueSolution = trueSolution;
	}
	public void setFalseSolution(Integer falseSolution) {
		this.falseSolution = falseSolution;
	}
	public void setUnreasonableWorlds( Set<String> unreasonableWorlds) { // copy the array
		this.unreasonableWorld = new LinkedHashSet< String>();
		for( String s : unreasonableWorlds)
			this.unreasonableWorld.add( s);
	}
	public void setMaxActionOrder(Integer maxActionOrder) {
		this.maxActionOrder = maxActionOrder;
	}
	public void setBestSolutionIdx(Integer bestSolutionIdx){
		this.bestSolutionIdx = bestSolutionIdx;
	}
	
	@Override
	public String toString() {
		return "CaggXMLTestDescriptor [data=" + data + ", userInput="
				+ userInput + ", grammarFilePath=" + grammarFilePath
				+ ", evaluatorType=" + evaluatorType + ", formatterType="
				+ formatterType + ", grammarType=" + grammarType
				+ ", computationTime=" + computationTime + ", evaluationTime="
				+ evaluationTime + ", formattingTime=" + formattingTime
				+ ", timeOut=" + timeOut + ", feasibleSolutions="
				+ feasibleSolutions + ", unfeasibleSolutions="
				+ unfeasibleSolutions + ", trueSolution=" + trueSolution
				+ ", falseSolution=" + falseSolution + ", maxActionOrder="
				+ maxActionOrder + ", unreasonableWorld=" + unreasonableWorld
				+ "]";
	}	
	
	
	
}
