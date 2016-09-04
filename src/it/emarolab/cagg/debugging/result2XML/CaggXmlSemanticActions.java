package it.emarolab.cagg.debugging.result2XML;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase.TagBase;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "actions")
public class CaggXmlSemanticActions {

	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private Set< CaggXmlAction> actions;
	
	
	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	public CaggXmlSemanticActions() {} // for xml interface

	public void intialise( ActionTagBase actions){
		Set< CaggXmlAction> xmlActions = new LinkedHashSet< CaggXmlAction>();
		for( TagBase<?> action : actions.getTagsCollector()){
			CaggXmlAction xmlAction = new CaggXmlAction();
			xmlAction.initialise( action.getId(), action.getTagList());
			xmlActions.add( xmlAction);
		}
		this.setAction( xmlActions);
	}
	
	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public Set<CaggXmlAction> getAction() {
		return actions;
	}
	
	/* ##################################################################################
	   ################################# SETTER #########################################
	 */
	public void setAction(Set<CaggXmlAction> action) {
		this.actions = action;
	}

	@Override
	public String toString() {
		return "[actions=" + actions + "]";
	}	
}
