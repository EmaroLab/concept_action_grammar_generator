package it.emarolab.cagg.core.evaluation.interfacing;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.interfaces.CaggCompiler;

import java.util.List;

public abstract class GrammarTesterBase{
	
	/* 	##################################################################################
   		################################ FIELDS ##########################################
	 */
	private GrammarBase< ? extends SemanticExpressionTreeBase> grammar;
	private TimeOutedEvaluatorBase evaluator;
	private ThreadedInputFormatter formatter;
	private boolean isTestingLastCommand = false;
	private Long testStartingTimeStamp;
	private Float testingTime; // in sec
	private String evaluationLoggerName, formatterLoggerName;

	/* 	##################################################################################
   		############################## CONSTUCTORS #######################################
	 */
	public GrammarTesterBase( String grammarPath){
		initialise( CaggLoggersManager.DEFAULT_LOGGING_NAME_EVALUATOR, CaggLoggersManager.DEFAULT_LOGGING_NAME_FORMATTER,
				loadGrammar( grammarPath), TimeOutedEvaluatorBase.DEFAULT_EVALUATION_TIME_OUT_SEC);
	}
	public GrammarTesterBase( String grammarPath, Float timeOutSec){
		initialise( CaggLoggersManager.DEFAULT_LOGGING_NAME_EVALUATOR, CaggLoggersManager.DEFAULT_LOGGING_NAME_FORMATTER,
				loadGrammar( grammarPath), timeOutSec);
	}
	public GrammarTesterBase( GrammarBase< ? extends SemanticExpressionTreeBase> grammar){
		initialise( CaggLoggersManager.DEFAULT_LOGGING_NAME_EVALUATOR, CaggLoggersManager.DEFAULT_LOGGING_NAME_FORMATTER,
				grammar, TimeOutedEvaluatorBase.DEFAULT_EVALUATION_TIME_OUT_SEC);
	}
	public GrammarTesterBase( GrammarBase< ? extends SemanticExpressionTreeBase> grammar, Float timeOutSec){
		initialise( CaggLoggersManager.DEFAULT_LOGGING_NAME_EVALUATOR, CaggLoggersManager.DEFAULT_LOGGING_NAME_FORMATTER,
				grammar, timeOutSec);
	}
	public GrammarTesterBase( String evaluatorLoggerName, String formatterLoggerName, String grammarPath){
		initialise( evaluatorLoggerName, formatterLoggerName,
				loadGrammar( grammarPath), TimeOutedEvaluatorBase.DEFAULT_EVALUATION_TIME_OUT_SEC);
	}
	public GrammarTesterBase( String evaluatorLoggerName, String formatterLoggerName, String grammarPath, Float timeOutSec){
		initialise( evaluatorLoggerName, formatterLoggerName, loadGrammar( grammarPath), timeOutSec);
	}
	public GrammarTesterBase( String evaluatorLoggerName, String formatterLoggerName, GrammarBase< ? extends SemanticExpressionTreeBase> grammar){
		initialise( evaluatorLoggerName, formatterLoggerName, grammar, TimeOutedEvaluatorBase.DEFAULT_EVALUATION_TIME_OUT_SEC);
	}
	public GrammarTesterBase( String evaluatorLoggerName, String formatterLoggerName, GrammarBase< ? extends SemanticExpressionTreeBase> grammar, Float timeOutSec){
		initialise( evaluatorLoggerName, formatterLoggerName, grammar, timeOutSec);
	}
	/// initialisation methods used from constructors
	private GrammarBase< ? extends SemanticExpressionTreeBase> loadGrammar( String grammarFilePath){
		GrammarBase< ? extends SemanticExpressionTreeBase> grammar = CaggCompiler.deserialise( grammarFilePath);
		if( grammar == null)
			GrammarLog.error( "null grammar cannot be evaluated ! try to deserialise from: " + grammarFilePath);
		return grammar;
	}
	private void initialise( String evaluatorLoggerName, String formatterLoggerName, GrammarBase< ? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
		/// get the grammar first
		this.grammar = grammar;
		/// store the formatter
		this.formatter = getNewFormatter( formatterLoggerName, grammar);
		/// store the evaluator
		this.evaluator = getNewEvaluator( evaluatorLoggerName, formatter, timeOutSec);
		/// store the prefix log
		this.evaluationLoggerName = evaluatorLoggerName;
		this.formatterLoggerName = formatterLoggerName;
	}
	// 	####################  CONTRUCTOR INTERFACE (ABSTRACT)  ###########################
	protected abstract ThreadedInputFormatter getNewFormatter( String loggerName, GrammarBase< ? extends SemanticExpressionTreeBase> grammar);
	protected abstract TimeOutedEvaluatorBase getNewEvaluator( String loggerName, ThreadedInputFormatter formatter, Float timeOutSec);

	/* 	##################################################################################
   		################################ GETTERS #########################################
	 */
	public GrammarBase< ? extends SemanticExpressionTreeBase> getGrammar() {
		return grammar;
	}
	public TimeOutedEvaluatorBase getEvaluator() {
		return evaluator;
	}
	public ThreadedInputFormatter getFormatter() {
		return formatter;
	}
	public boolean isTestingLastCommand(){
		return isTestingLastCommand;
	}
	public void resetLastCommand(){
		this.isTestingLastCommand = false;
	}
	public Float getTestingTime(){
		return testingTime;
	}
	public  void setTestingTime(){
		testingTime = Float.valueOf( System.nanoTime() - testStartingTimeStamp) / DebuggingDefaults.NANOSEC_2_SEC;
	}
	public synchronized Float getRangeFromTestingStart(){ // up to now (in seconds)
		return Float.valueOf( System.nanoTime() - testStartingTimeStamp) / DebuggingDefaults.NANOSEC_2_SEC;
	}
	public synchronized String getEvaluatorLoggerName() {
		return evaluationLoggerName;
	}
	public String getFormatterLoggerName() {
		return formatterLoggerName;
	}
	/* 	##################################################################################
   		########################## EVALUATION INTERFACE ##################################
	 */
	public synchronized void test( List< String> commands){
		testStartingTimeStamp = System.nanoTime();
		for( int i = 0; i < commands.size(); i++){
			if( i == commands.size() - 1)
				this.isTestingLastCommand = true;
			performTest( commands.get( i), i);
		}
	}
	public synchronized void test( String command){ // testIdx = 0
		testStartingTimeStamp = System.nanoTime();
		this.isTestingLastCommand = true;
		performTest( command, 0);
	}
	public synchronized void test( String command, int testIdx){
		testStartingTimeStamp = System.nanoTime();
		this.isTestingLastCommand = true;
		performTest( command, testIdx);
	}
	protected void performTest( String command, int testIdx){ // internal function. use test( ..) from externall classes instead
		// evaluate
		willEvaluateCommand( command, testIdx);
		evaluator.evaluate( command);
		evaluator.join();
		setTestingTime();
		didEvaluateCommand( command, testIdx);
		resetLastCommand();
	}
	// abstact method called every time a new command is going to be tested
	public abstract void willEvaluateCommand( String command, int testIdx);
	public abstract void didEvaluateCommand( String command, int testIdx);
}