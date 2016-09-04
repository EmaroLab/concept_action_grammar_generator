package it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler;

import it.emarolab.cagg.core.PseudoBinaryNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase.TagBase;
import it.emarolab.cagg.core.language.BaseData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*  ##################################################################################
	########################## UTILITY CLASS #########################################
	this class describes the basic implementation that an Semantic Expression must
	provide. This object is used into the Grammar Base to describe the semantic
	expression to be evaluated.*/
//public interface SemanticExpressionTreeBase{
public abstract interface SemanticExpressionTreeBase{

	/* 	##################################################################################
		################## SEMANTIC EXPRESSION INTERFACE #################################
		those methods are called during evaluation in order to get results. 
		Particularly, setFacts( f) is called first in order to prepare the expression 
		structure to be evaluated. Than, the system calls evaluate() in order to get the 
		results in terms of boolean outcome and semantic tags. Finally, the reset()
		method can be got called in order to be able to evaluate again.*/
	public void setFacts( Facts< ?> f, Map< Long, ? extends SemanticExpressionNodeBase> expressionTermMap);
	public SemanticResult evaluate();
	public void reset();
	public SemanticExpressionNodeBase getRoot();
	//public List< Long> getTermOccurences( SemanticExpressionNodeBase startingId, String termName);


	/* ##################################################################################
	   ########################## UTILITY CLASS #########################################
	   to describes the nodes of the expression.											*/
	@SuppressWarnings("serial")
	public abstract class SemanticExpressionNodeBase extends PseudoBinaryNode implements SemanticExpressionPrimitive{
		// construcors
		public SemanticExpressionNodeBase() {
			super();
		}
		public SemanticExpressionNodeBase(Integer maxChildrenCount, String prefixId) {
			super(maxChildrenCount, prefixId);
		}
		public SemanticExpressionNodeBase(Integer maxChildrenCount) {
			super(maxChildrenCount);
		}
		public SemanticExpressionNodeBase(String prefixId) {
			super(prefixId);
		}
	}
	public interface SemanticExpressionPrimitive {
		public Long getId();
		public void setValue( Boolean value);
		public Boolean getValue();
		public BaseData<?> getSyntaxData();
		public TagBase<?> getTags();
	}

	/* ##################################################################################
	   ########################## UTILITY CLASS #########################################
	   container for evaluation results and action tags*/
	public class SemanticResult{
		// fields
		// added by the evaluator
		private Boolean outcome;
		private ActionTagBase tags;
		// constructor
		public SemanticResult() {
		}
		public SemanticResult(Boolean outcome, ActionTagBase tags) {
			this.outcome = outcome;
			this.tags = tags;
		}
		// setters
		protected void setOutcome( Boolean outcome){
			this.outcome = outcome;
		}
		protected void setTags( ActionTagBase tags){
			this.tags = tags;
		}
		// getters
		public Boolean getOutcome() {
			return outcome;
		}
		public ActionTagBase getTags() {
			return tags;
		}
		// object interface
		@Override
		public String toString(){
			String outcome = "?"; 
			if( this.outcome != null){
				if( this.outcome)
					outcome = "TRUE";
				else outcome = "FALSE";
			}
			String tagsStr = "?";
			if( this.tags != null)
				tagsStr = this.tags.toString(); 
			return "{outcome: " + outcome + ";\t@:" + tagsStr + "}"; 
		}
	}

	/* ##################################################################################
	   ########################## UTILITY CLASS #########################################
	   this is a simple class containing a list of R. It purposes is to define the node
	   that are true in an ExpressionBase objects. In the default implementation R is 
	   a long and it identify the id of the true terms node in the SemanticTree.*/
	public class Facts< F extends Object>{
		// fields and constructor
		private List< F> factsList = new ArrayList<>();
		public Facts(){
		}
		public Facts( F fact){
			factsList.add( fact);
		}
		public Facts( Collection<? extends F> facts){
			factsList.addAll( facts);
		}
		// facts list manipulation
		public void clear(){
			this.factsList.clear();
		}
		public void addAll( Collection<? extends F> facts){
			for( F f : facts)
				add( f);
		}
		public void add( F fact){
			if( fact != null)
				this.factsList.add( fact);
			else GrammarLog.warning( "tag: " + fact + " not added to the action tag set. (" + factsList + ")");
		}
		public void removeAll( Collection<? extends F> facts){
			for( F f : facts)
				remove( f);
		}
		public void remove( F fact){
			if( fact != null)
				this.factsList.remove( fact);
			else GrammarLog.warning( "tag: " + fact + " not removed from the action tag set. (" + factsList + ")");
		}
		// fact list getters
		public int size(){
			return this.factsList.size();
		}
		public Boolean isEmpty(){
			return this.factsList.isEmpty();
		}
		public Boolean contains( Long id){
			for( F f : factsList)
				if( f.equals( id))
					return true;
			return false;
		}
		public F get( Integer idx){
			return this.get(idx);
		}
		public List< F> getAllFacts(){
			return factsList;
		}
		public String toString(){
			return  this.factsList.toString();
		}
	}

}