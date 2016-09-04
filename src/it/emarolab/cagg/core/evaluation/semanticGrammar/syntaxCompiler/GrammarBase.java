package it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler;

import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticResult;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionTermData;
import it.emarolab.cagg.debugging.DebuggingText.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class GrammarBase< S extends SemanticExpressionTreeBase> implements Serializable { // S stands for "semantic". In the simple case it is an Expression Tree (SemanticTree object)
	
	/* ##################################################################################
	   ############################## FIELDS ############################################
	   */
	// base information from syntax language parsing marged with all the sources files
	private Map< Long, AbstractSyntaxNode<?>> preambleMap;			// all the preamble nodes in the main and auxiliary grammars organised in a map w.r.t. node id
	private Map< Long, RuleSyntaxNode> bodyMap;						// all the rule nodes (body) in the main and auxiliary grammars organised in a map w.r.t. node id
	private S expression; // a semantic expression
	private Map< Long, ? extends SemanticExpressionNodeBase> expressionTermMap; // all the term nodes of the semantic expression collected in a map w.r.t. node id
	private Set< String> notEvaluableWords; // refere to '^' symbol
	private String deserialisationFilePath = null; // null if not deseraialised from file

	/* ##################################################################################
	   ########################### CONSTRUCTOR ##########################################
	   */
	protected GrammarBase(){
		// this method does not do nothing, it relies on GrammarCreatorBase.populateGrammar()
		// and the setters of this class fields.
	}
	
	/* ##################################################################################
	   ###################### SEMANTIC EXPRESSION INTERFACE #############################
	   */
	public void imposeExpressionFacts( Facts< ?> facts){
		this.getSemanticExpression().setFacts( facts, expressionTermMap);
	}
	public SemanticResult evaluateExpression(){
		return this.getSemanticExpression().evaluate();
	}
	public void resetExpression(){
		this.getSemanticExpression().reset();
	}
		
	/* ##################################################################################
	   ############################### COMPUTATIONS #####################################
	   those implementations are used during evaluation. They should rely only on the 
	   fields of this class and care about perfomances										*/
	public List< Long> getAllTermOccurence( String termStr) { 
		List< Long> out = new ArrayList<>();
		for(  SemanticExpressionNodeBase node : expressionTermMap.values()){
			ExpressionTermData termData = ( ExpressionTermData) node.getSyntaxData(); 
			if( ! termData.getInstance2()){	// if it is not a rule declaration
				String termDefinition = termData.getInstance();
				if( termDefinition.equalsIgnoreCase( termStr))
					out.add( node.getId());
			}
		}
		return out;
	}
	// this implementetaion returns null if the term is not found on the grammar (SkippedWords)
	// otherwise it returns an objects containing the id of the terms that are directive equals to termStr.
	// Such an object describes the set of feasible and unfeasible (startingId) paths to be evaluated.
	// Be careful that if termId<startingId (=> unfeasible) the result must not be null!!!!
	public TermOccurenceCollector getAllTermOccurence( Long startingId, String termStr) {
		// create an empty output
		TermOccurenceCollector out = new TermOccurenceCollector( termStr);
		// check if the term is in grammar (in order to populated SkippedWorld consistently)
		// it may be not efficient !!!!!!! since requires to iterate two times over expressionTermMap.
		Boolean grammarContainsTerm = false;
		for(  SemanticExpressionNodeBase node : expressionTermMap.values()){
			ExpressionTermData termData = ( ExpressionTermData) node.getSyntaxData(); 
			if( ! termData.getInstance2()){	// if it is not a rule declaration
				String termDefinition = termData.getInstance();
				if( termDefinition.equalsIgnoreCase( termStr))
					grammarContainsTerm = true;
			}
			if( grammarContainsTerm)
				break;
		}
		if( ! grammarContainsTerm)
			return null;
		
		
		// for all the terms of the expression tree
		for(  SemanticExpressionNodeBase node : expressionTermMap.values()){
			// get ordering node id
			Long termId = node.getId();
			// check if unfeasible
			if( termId <= startingId)
				out.getUnfeasibleTermComposition().add( termId);
			else { // if feasible
				ExpressionTermData termData = ( ExpressionTermData) node.getSyntaxData(); 
				if( ! termData.getInstance2()){	// if it is not a rule declaration
					String termDefinition = termData.getInstance();
					if( termDefinition.equalsIgnoreCase( termStr)){
						out.getFeasibleTermComposition().add( node.getId());
					}
				}
			}
		}
		// order by increasing id
		Collections.sort( out.getUnfeasibleTermComposition());
		Collections.sort( out.getFeasibleTermComposition());
		return out;
	}
	public class TermOccurenceCollector{
		////// fields
		private List< Long> feesibleTermComposition, unfeasibleTermComposition;
		private String termName = null;
		////// constructor
		public TermOccurenceCollector(){
			initialise();
		}
		public TermOccurenceCollector(String termStr) {
			initialise();
			this.termName = termStr;
		}
		private void initialise(){
			this.feesibleTermComposition = new ArrayList< Long>();
			this.unfeasibleTermComposition = new ArrayList< Long>();
		}
		////// getters  
		public List< Long> getFeasibleTermComposition() {
			return feesibleTermComposition;
		}
		public List< Long> getUnfeasibleTermComposition() {
			return unfeasibleTermComposition;
		}
		////// debugging
		@Override
		public String toString() {
			String termStr = "term";
			if( termStr != null)
				termStr = "\"" + this.termName + "\"";
			return "[fiesible " + termStr + " occurence: " + feesibleTermComposition
					+ "  ;   unfeasible: " + unfeasibleTermComposition + "]";
		}
		
	}
	

	/* ##################################################################################
	   ############################## GETTERS ###########################################
	   */
	public Map<Long, AbstractSyntaxNode<?>> getPreambleMap() {
		return preambleMap;
	}
	public Map<Long, RuleSyntaxNode> getBodyMap() {
		return bodyMap;
	}
	public S getSemanticExpression() {
		return expression;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " "
				+ "PREAMBLE MAP : " + preambleMap + " ; \t"
				+ "BODY MAP : " + bodyMap + " ; \t"
				+ "EXPRESSION : " + expression;
	}
	public Map<Long, ? extends SemanticExpressionNodeBase> getExpressionTermMap() {
		return expressionTermMap;
	}
	public Set< String> getUnreasonableWords() {
		return notEvaluableWords;
	}
	public String getDeserialisationFilePath() {
		return deserialisationFilePath;
	}
	

	/* ##################################################################################
	   ############################## SETTERS ###########################################
	   */
	public void setPreambleMap(Map<Long, AbstractSyntaxNode<?>> preambleMap) {
		this.preambleMap = preambleMap;
	}
	public void setBodyMap(Map<Long, RuleSyntaxNode> bodyMap) {
		this.bodyMap = bodyMap;
	}
	public void setSemanticExpression( S expression) {
		this.expression = expression;
	}
	public void setExpressionTermMap(Map<Long, ? extends SemanticExpressionNodeBase> expressionTermMap) {
		this.expressionTermMap = expressionTermMap;
	}
	public void setNotEvaluableWords( Set< String> notEvaluableWords) {
		this.notEvaluableWords = notEvaluableWords;
	}
		

	/* ##################################################################################
	   ############################## STATIC ############################################
	   */
	/**
	 * This method can be used to store on a file all the data initialisated in this object.
	 * This improves portability and efficiency.
	 * This method calls {@link GrammarBase#resetExpression()} before to write.
	 * @param filePath the path to the serialisation file of this object to be created.
	 */
	public void serialise( String filePath){
		try {
			FileOutputStream fos = new FileOutputStream( filePath);
			ObjectOutputStream out = new ObjectOutputStream( fos);
			this.resetExpression(); // be sure to do not propagate testing states
			out.writeObject( this);
			out.close();
			GrammarLog.info( "Grammar (" + this + ") serialised on path: " + filePath);
		} catch (Exception ex) {
			GrammarLog.error(ex);
			GrammarLog.error("Cannot serialise grammars (" + this + ") on path: " + filePath);
		}
	} 

	/**
	 * This method can be used to load from a file all the data previously initialisated and serialised 
	 * of an instance of this object.
	 * This improves portability and efficiency.
	 * @param filePath the path to the serialisation file of an instance of this object to be read.
	 * @return the data stored on an instance of this object when it has been serialised.
	 */
	public static GrammarBase<? extends SemanticExpressionTreeBase> deserialise( String filePath){
		return deserialise( filePath, null);
	}
	public static GrammarBase<? extends SemanticExpressionTreeBase> deserialise( String filePath, String loggerName){
		GrammarBase<?> grammars = null;
		try {
			FileInputStream fis = new FileInputStream( filePath);
			ObjectInputStream in = new ObjectInputStream(fis);
			grammars = ( GrammarBase<?>) in.readObject();
			in.close();
			grammars.deserialisationFilePath = filePath; // set serialisation path It is null if not serialised from file
			logging( loggerName, "Grammar (" + grammars + ") deserialised from path: " + filePath, false);
		} catch (Exception ex) {
			logging( loggerName, "Cannot deserialise grammars from path: " + filePath + " (check grammar logging stream for stackTrace.)", true);
			GrammarLog.error( ex);
		}
		return grammars;
	}
	private static void logging( String loggerName, String msg, Boolean isError){
		if( loggerName == null){
			if( isError)
				GrammarLog.error( msg);
			else GrammarLog.info( msg);
		} else {
			if( isError)
				Logger.error( loggerName, msg);
			else Logger.info( loggerName, msg);
		}
	}
}
