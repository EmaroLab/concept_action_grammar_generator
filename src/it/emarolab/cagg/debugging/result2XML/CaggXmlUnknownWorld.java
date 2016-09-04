package it.emarolab.cagg.debugging.result2XML;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter.SkippedWord;

@XmlRootElement(name = "skipped")
@XmlType(propOrder = { "idxInOriginalInput", "word", "solutionIndexMin", "solutionIndexMax"}) // optional
public class CaggXmlUnknownWorld {
	/*   SEMANTIC RESULT HIERARCHICAL STRUCTURE (CAGG2XML plain)
	 . CaggXmlUnknownWorld unknown;
	 + . Integer idx
	 + . String word
	 */
	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private Integer idxInOriginalInput;
	private String word;
	private Integer solutionIndexMin, solutionIndexMax;
	
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlUnknownWorld() {} // for XML interface
	
	public void initialise(SkippedWord s) {
		this.setIdxInOriginalInput( s.getWordIndexOnUserInput());
		
		this.setWord( s.getWord());
		
		this.setSolutionIndexMin( s.getSolutionMinIndex());
		this.setSolutionIndexMax( s.getSolutionMaxIndex());
		
	}
	
	/* ##################################################################################
	   #########################à##### GETTERS ##########################################
	 */
	public Integer getIdxInOriginalInput() {
		return idxInOriginalInput;
	}
	public String getWord() {
		return word;
	}
	public Integer getSolutionIndexMin() {
		return solutionIndexMin;
	}
	public Integer getSolutionIndexMax() {
		return solutionIndexMax;
	}
	
	/* ##################################################################################
	   ################################# SETTER #########################################
	 */
	public void setIdxInOriginalInput(Integer idx) {
		this.idxInOriginalInput = idx;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void setSolutionIndexMin(Integer solutionIndexMin) {
		this.solutionIndexMin = solutionIndexMin;
	}
	public void setSolutionIndexMax(Integer solutionIndexMax) {
		this.solutionIndexMax = solutionIndexMax;
	}

	@Override
	public String toString() {
		return "[idxInOriginalInput=" + idxInOriginalInput
				+ ", word=" + word + ", solutionIndexMin=" + solutionIndexMin
				+ ", solutionIndexMax=" + solutionIndexMax + "]";
	}
	
		
}
