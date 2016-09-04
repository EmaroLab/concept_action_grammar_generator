package it.emarolab.cagg.interfaces;

import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;

/* 	##################################################################################
	############## UTILITY CLASS FOR  CaggMultiGrammarTesterOnFile ###################
 */
public class CaggMultiGrammarTesterConfiguration{
	/* 	##################################################################################
		################################## FIELDS ########################################
	 */
	private String testName;
	private String evaluatorLoggerName, formatterLoggerName;
	private GrammarBase<? extends SemanticExpressionTreeBase> grammar;
	
	/* 	##################################################################################
		############################### CONSTRUCTOR ######################################
	 */
	public CaggMultiGrammarTesterConfiguration( String evaluatorLoggerName, String formatterLoggerName, String grammarPath){
		initialise( CaggMultiGrammarTesterOnFile.DEFAULT_TEST_NAME, evaluatorLoggerName, formatterLoggerName, loadGrammar( grammarPath));
	}
	public CaggMultiGrammarTesterConfiguration( String testName, String evaluatorLoggerName, String formatterLoggerName, String grammarPath){
		initialise( testName, evaluatorLoggerName, formatterLoggerName, loadGrammar( grammarPath));
	}
	public CaggMultiGrammarTesterConfiguration( String testName, String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar){
		initialise( testName, evaluatorLoggerName, formatterLoggerName, grammar);
	}
	private GrammarBase< ? extends SemanticExpressionTreeBase> loadGrammar( String grammarFilePath){
		GrammarBase< ? extends SemanticExpressionTreeBase> grammar = CaggCompiler.deserialise( grammarFilePath);
		if( grammar == null)
			GrammarLog.error( "null grammar cannot be evaluated ! try to deserialise from: " + grammarFilePath);
		return grammar;
	}
	
	private void initialise( String testName, String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar){
		this.testName = testName;
		this.evaluatorLoggerName = evaluatorLoggerName;
		this.formatterLoggerName = formatterLoggerName;
		this.grammar = grammar;
	}
	
	/* 	##################################################################################
		################################## GETTERS #######################################
	 */
	public String getTestName() {
		return testName;
	}
	public String getEvaluatorLoggerName() {
		return evaluatorLoggerName;
	}
	public String getFormatterLoggerName() {
		return formatterLoggerName;
	}
	public GrammarBase<? extends SemanticExpressionTreeBase> getGrammar() {
		return grammar;
	}
}