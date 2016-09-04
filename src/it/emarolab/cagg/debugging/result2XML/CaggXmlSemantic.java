package it.emarolab.cagg.debugging.result2XML;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "result")
@XmlType(propOrder = { "outcome", "actions"}) // optional
public class CaggXmlSemantic {
	/*   SEMANTIC RESULT HIERARCHICAL STRUCTURE (CAGG2XML plain)
	 + . CaggXmlSemantic semantic
	 + + . Boolean outcome
	 + + . Set< CaggXmlAction> action;
	 + + + . Long id;
	 + + + . List< CaggXmlTag> tags;
	 + + + + CaggXmlTag tag (: String)
	 */
	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private Boolean outcome;
	private CaggXmlSemanticActions actions;
	
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlSemantic() {} // for xml interface
	
	public void initialise(Boolean outcome, ActionTagBase actions) {
		this.setOutcome( outcome);
		
		CaggXmlSemanticActions xmlActions = new CaggXmlSemanticActions();
		xmlActions.intialise( actions);
		this.setActions( xmlActions);
	}
	
	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public Boolean getOutcome() {
		return outcome;
	}
	public CaggXmlSemanticActions getActions() {
		return actions;
	}
	
	/* ##################################################################################
	   ################################# SETTER #########################################
	 */
	public void setOutcome( Boolean outcome) {
		this.outcome = outcome;
	}
	public void setActions( CaggXmlSemanticActions actions) {
		this.actions = actions;
	}

	@Override
	public String toString() {
		return "[outcome=" + outcome + ", actions=" + actions + "]";
	}
}
