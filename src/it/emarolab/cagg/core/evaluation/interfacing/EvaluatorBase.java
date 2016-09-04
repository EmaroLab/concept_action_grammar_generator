package it.emarolab.cagg.core.evaluation.interfacing;

import java.util.List;
import java.util.Set;

import it.emarolab.cagg.core.evaluation.CaggThread.ThreadLogger;
import it.emarolab.cagg.core.evaluation.inputFormatting.InputFormatterBase;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter.SkippedWord;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticResult;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.DebuggingText.Logger;
import it.emarolab.cagg.interfaces.TestLog;

public abstract class EvaluatorBase< G extends GrammarBase< ? extends SemanticExpressionTreeBase>, F extends InputFormatterBase> {
	
	/* ##################################################################################
	   ############################# CONSTANTS ##########################################
	 */
	public static final String DEFAULT_LOGGER_NAME = CaggLoggersManager.DEFAULT_LOGGING_NAME_EVALUATOR; // w.r.t. log4j2
	public static final int DEFAULT_LOGGING_BACK_STEP = 2;
	
	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private G grammar;
	private F formatter;
	private Long evaluatingStartTimeStamp = null; // in nanosec (has the last value it gets update only on call evaluate(..))
	private String loggerName; // used to distinguish independent instances of the formatter in the loggs. Any implementation should use log(..) function to write messages
	private ThreadLogger threadLogger;
	
	/* ##################################################################################
	   ########################### CONSTRUCTOR ##########################################
	 */
	public EvaluatorBase( F formatter){
		initialise( DEFAULT_LOGGER_NAME, formatter);
	}
	public EvaluatorBase( String loggerName, F formatter){
		initialise( loggerName, formatter);
	}
	@SuppressWarnings("unchecked")
	private void initialise( String logName, F formatter){
		this.grammar = ( G) formatter.getGrammar();
		this.formatter = formatter;
		if( logName == null){
			this.loggerName = DEFAULT_LOGGER_NAME;
			TestLog.warning( "Cannot process a null logger name. Default value used instead: " + DEFAULT_LOGGER_NAME);
		} else this.loggerName = logName;
	}
	
	/* ##################################################################################
	   ############################ EVALUATION ##########################################
	 */
	public void evaluate( String userInput){
		this.formatter.searchFacts( userInput);
		log("********************************  EVALUATE ALL  ********************************");
		evaluatingStartTimeStamp = System.nanoTime();
		start();  //this.evaluateAll(); 
	}
	
	
	/* ##################################################################################
	   ######################## ABSTRACT INTERFACE ######################################
	 */
	// it returns the set of Facts to be evaluate for the next solution. Returns null if no other solutions are found.
	public abstract EvaluationResults getNextSolution();
	public abstract Integer getEvaluationCount();
	protected abstract void start();
	public abstract void stop();
	public abstract void join();
	public abstract boolean isRunning();
	
	/* ##################################################################################
	   ######################## ABSTRACT INTERFACE ######################################
	 */
	abstract public void activateTrigger( EvaluationResults result);
	
	
	// internal interface with formatter->facts->grammar
	// this class provides a minimal implementation but you may want to change it (all used on evaluateOnce())
	protected void setExpressionFacts( Facts< ?> trueFacts){
		grammar.imposeExpressionFacts( trueFacts);
	}
	protected SemanticResult evaluateExpression(){
		return this.getGrammar().evaluateExpression(); 
	}
	protected void resetExpression(){
		grammar.resetExpression();
	}

	/* ##################################################################################
	   ############################## GETTERS ###########################################
	 */
	public G getGrammar() {
		return grammar;
	}
	public F getFormatter() {
		return formatter;
	}
	public Long getEvaluationStatingTimeStamp(){
		return evaluatingStartTimeStamp;
	}
	public Float getActualDealyFromStart(){ // in sec
		return Float.valueOf( System.nanoTime() - evaluatingStartTimeStamp) / DebuggingDefaults.NANOSEC_2_SEC;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public ThreadLogger getThreadLogger(){
		return threadLogger;
	}
	// setters
	public void setThreadLogger( ThreadLogger logger){
		this.threadLogger = logger;
	}
	
	/* ##################################################################################
	   ################# LOGGIN PROCEDURE FOR MULTI INSTANCE LOGGING ####################
	 */
	protected void log( Object msg){
		Logger.info( DEFAULT_LOGGING_BACK_STEP, loggerName, msg);
	}
	protected void log( int backStep, Object msg){
		Logger.info( DEFAULT_LOGGING_BACK_STEP + backStep, loggerName, msg);
	}
	
	protected void logWarning( Object msg){
		Logger.warning( DEFAULT_LOGGING_BACK_STEP, loggerName, msg);
	}
	protected void logWarning( int backStep, Object msg){
		Logger.warning( DEFAULT_LOGGING_BACK_STEP + backStep, loggerName, msg);
	}
	
	protected void logOk( Object msg){
		Logger.ok( DEFAULT_LOGGING_BACK_STEP, loggerName, msg);
	}
	protected void logOk( int backStep, Object msg){
		Logger.ok( DEFAULT_LOGGING_BACK_STEP + backStep, loggerName, msg);
	}
	
	protected void logError( Object msg){
		Logger.error( DEFAULT_LOGGING_BACK_STEP, loggerName, msg);
	}
	protected void logError( int backStep, Object msg){
		Logger.error( DEFAULT_LOGGING_BACK_STEP + backStep, loggerName, msg);
	}
	
	protected void logError( Exception e){
		Logger.error( DEFAULT_LOGGING_BACK_STEP, loggerName, e);
	}
	protected void logError( int backStep, Exception e){
		Logger.error( DEFAULT_LOGGING_BACK_STEP + backStep, loggerName, e);
	}
	
	/* ##################################################################################
	   ########################### UTILITY CLASS ########################################
	  	to store the actual results. */
	public class EvaluationResults{
		//// fields
		private int resultIdx;
		private SemanticResult result;
		private Set< SkippedWord> skippedWorlds;
		private Facts< ?> facts;
		private List< String> usedInputs;
		private Long creationTimeStamp, evaluationStartTimeStamp;
		private Float secondsFromStart;
		//// constructor
		public EvaluationResults( int resultIdx, SemanticResult result, Facts< ?> facts, 
				Set< SkippedWord> skippedWorlds, List< String> usedInputs, 
				Long evaluationStartTimeStamp){
			initialise(resultIdx, result, facts, 
					skippedWorlds, usedInputs, 
					evaluationStartTimeStamp);
		}
		public EvaluationResults( Integer resultIdx, SemanticResult result, Facts<?> facts, 
				ThreadedInputFormatter formatter,
				Long evaluationStartTimeStamp) {
			initialise(resultIdx, result, facts, 
					formatter.getSkippedWords( resultIdx), formatter.getUsedWords( resultIdx), 
					evaluationStartTimeStamp);			
		}
		private void initialise( int resultIdx, SemanticResult result, Facts< ?> facts, 
				Set< SkippedWord> skippedWorlds, List< String> usedInputs, Long evaluationStartTimeStamp){
			this.resultIdx = resultIdx;
			this.result = result;
			this.facts = facts;
			this.skippedWorlds = skippedWorlds;
			this.usedInputs = usedInputs;
			this.creationTimeStamp = System.nanoTime();
			this.evaluationStartTimeStamp = evaluationStartTimeStamp;
			this.secondsFromStart = getTimeSpan( evaluationStartTimeStamp);
		}
		///// getters
		public Set<SkippedWord> getUnknownWorlds() {
			return skippedWorlds;
		}
		public Facts<?> getFacts() {
			return facts;
		}
		public SemanticResult getResult(){
			return result;
		}
		public Boolean getResultOutcome(){
			return result.getOutcome();
		}
		public ActionTagBase getResultTags(){
			return result.getTags();
		}
		public int getResultIdx(){
			return resultIdx;
		}
		public Long getGerationTimeStamp(){
			return creationTimeStamp; // in nanosec
		}
		public Long getEvaluationStartTimeStamp(){
			return evaluationStartTimeStamp; // in nanosec
		}
		public Float getTimeSpan( Long nanoSecTimeStamp){
			if( nanoSecTimeStamp > creationTimeStamp){
				Logger.warning( "Cannot compute a time span with a referiment on the future.");
				return null;
			}
			return Float.valueOf( creationTimeStamp - nanoSecTimeStamp) / DebuggingDefaults.NANOSEC_2_SEC;
		}
		public Float getTimeSpanFromEvaluationStart(){
			return secondsFromStart;
		}
		@Override
		public String toString(){
			return "(" + resultIdx + ")->{facts:" + facts + " ; used:" + usedInputs + " ; skipped:" + skippedWorlds + " ; outcome:" + getResultOutcome() + " ; tags:" + getResultTags() + "}"; 
		}
		public List< String> getUsedInput() {
			return usedInputs;
		}
	}
}
