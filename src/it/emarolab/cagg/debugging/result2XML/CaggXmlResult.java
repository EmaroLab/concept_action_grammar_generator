package it.emarolab.cagg.debugging.result2XML;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter.SkippedWord;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.debugging.UILog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

// for JAXB interface
@XmlType(propOrder = { "idx", "secondsFromStart", "input", "facts", "result", "unknownSet" }) // optional
public class CaggXmlResult {

	/*   SEMANTIC RESULT HIERARCHICAL STRUCTURE (CAGG nested)
	 . string preAttribute
	 . int resultIdx;
	 . SemanticResult result;
	 + . Boolean outcome;
	 + . ActionTagBase tags;
	 + + . Set< TagBase<?>> tagsCollector;
	 + + + . Long id;
	 + + + . List<T> tagsList = new ArrayList<>();
	 . Set< SkippedWord> skippedWorlds;
	 + . String word;
	 + . Integer idx;
	 + .  List< Integer> solutionId = new ArrayList<>();
	 . Facts< ?> facts;
	 + . List< F> factsList = new ArrayList<>();
	 . List< String> usedInputs;
	 . Long creationTimeStamp, evaluationStartTimeStamp;
	 . Float secondsFromStart;								*/
	
	/*   SEMANTIC RESULT HIERARCHICAL STRUCTURE (CAGG2XML plain)
	 . CaggXmlResult result
	 + . String attribute
	 + . Integer idx
	 . Float secondsFromStart;	
	 + . CaggXmlSemantic semantic
	 + + . Boolean outcome
	 + + . Set< CaggXmlAction> action;
	 + + + . Long id;
	 + + + . List< String> tags;
	 . CaggXmlUnknownWorld unknown;
	 + . Integer idxInput
	 + . String word
	 + . String minIdx
	 + . String maxIdx
	 . List< Long> facts
	 . List< String> input
	 	
	 */
	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private Integer idx;
	private Float secondsFromStart;
	
	@XmlElement(name = "semantic")
	private CaggXmlSemantic result;
	
	@XmlElementWrapper(name = "unknownFacts")
	@XmlElement(name = "skipped")
	private Set< CaggXmlUnknownWorld> unknownSet;
	
	@XmlElementWrapper(name = "guess")
	@XmlElement(name = "fact")
	private List< Long> facts;
	
	@XmlElementWrapper(name = "userdInputs")
	@XmlElement(name = "token")
	private List< String> input;
	
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlResult() {} // for xml interface
	
	public void initialise( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
		// from row result type
		this.setIdx( result.getResultIdx());
		this.setSecondsFromStart( result.getTimeSpanFromEvaluationStart());
		
		List< String> xmlInput = new ArrayList< String>();
		for( String in : result.getUsedInput())
			xmlInput.add( in);
		this.setInput( xmlInput);
		
		// parse raw cagg semantic result
		CaggXmlSemantic xmlSemantic = new CaggXmlSemantic();
		xmlSemantic.initialise( result.getResultOutcome(), result.getResultTags());
		this.setResult( xmlSemantic);
		
		// parse raw cagg skipped worlds
		Set< CaggXmlUnknownWorld> xmlUnknwon = new LinkedHashSet< CaggXmlUnknownWorld>();
		if( result.getUnknownWorlds() != null)
			for( SkippedWord s : result.getUnknownWorlds()){
				CaggXmlUnknownWorld xmlUnk = new CaggXmlUnknownWorld();
				xmlUnk.initialise( s);
				xmlUnknwon.add( xmlUnk);
			}
		this.setUnknownSet( xmlUnknwon);
		
		// parse raw cagg facts (only Long id considered)
		List< Long> xmlFacts = new ArrayList<Long>();
		for( Object o : result.getFacts().getAllFacts()){
			if( o instanceof Long)
				xmlFacts.add( ( Long) o);
			else{
				UILog.error( " only Long fact type are supported from XML parsing!");
				xmlFacts.add( -1L);	
			}
		}
		this.setFactList( xmlFacts);
	}

	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public Integer getIdx() {
		return idx;
	}
	public CaggXmlSemantic getSemantic() {
		return result;
	}
	public Set< CaggXmlUnknownWorld> getUnknown() {
		return unknownSet;
	}
	public List< Long> getFacts() {
		return facts;
	}
	public List<String> getInputs() {
		return input;
	}
	public Float getSecondsFromStart() {
		return secondsFromStart;
	}
	
	/* ##################################################################################
	   ################################# SETTER #########################################
	 */
	public void setSecondsFromStart(Float secondsFromStart) {
		this.secondsFromStart = secondsFromStart;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public void setResult(CaggXmlSemantic result) {
		this.result = result;
	}
	public void setUnknownSet(Set<CaggXmlUnknownWorld> unknownSet) {
		this.unknownSet = unknownSet;
	}
	public void setFactList(List< Long> factList) {
		this.facts = factList;
	}
	public void setInput(List<String> input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return "CaggXmlResult [idx=" + idx + ", secondsFromStart="
				+ secondsFromStart + ", result=" + result + ", unknownSet="
				+ unknownSet + ", facts=" + facts + ", input=" + input + "]";
	}
	
	
	
	
}
