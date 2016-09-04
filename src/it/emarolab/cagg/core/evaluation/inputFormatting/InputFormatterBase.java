package it.emarolab.cagg.core.evaluation.inputFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import it.emarolab.cagg.core.evaluation.CaggThread.ThreadLogger;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingText.Logger;
import it.emarolab.cagg.interfaces.TestLog;

public abstract class InputFormatterBase {

	/* ##################################################################################
	   ############################# CONSTANTS ##########################################
	 */
	public static final String DEFAULT_NOT_ALLOWABLE_CHAR_PATTERN = "[^a-zA-Z0-9À-ÿ\\s]";
	public static final Boolean SHOULD_COLLECT_UNFEASIBLE_FACTS = false;
	public static final String DEFAULT_LOGGER_NAME = CaggLoggersManager.DEFAULT_LOGGING_NAME_FORMATTER; // w.r.t. log4j2
	public static final int DEFAULT_LOGGING_BACK_STEP = 2;


	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private GrammarBase<?> grammar;
	private List< String> tokenisedInputs; 	// the tokenised string given by the user as input (perhaps the result of peach-to-text recognition)
	private String notAllowableCharPatern;
	private Long formattingStartTimeStamp = null; // in nanosec (has the last value it gets update only on call evaluate(..))
	private String loggerName; // used to distinguish independent instances of the formatter in the loggs. Any implementation should use log(..) function to write messages
	private ThreadLogger threadLogger;

	/* ##################################################################################
	   ############################ CONSTRUCTOR #########################################
	   this class should be instantiate every time a new input is given by the user.
	   It tokenises the string by spaces and its purposes is to populate Facts to be
	   used during semantic expression evaluation.		*/
	public InputFormatterBase( GrammarBase<?> grammar){
		initialise(grammar, DEFAULT_NOT_ALLOWABLE_CHAR_PATTERN, DEFAULT_LOGGER_NAME);
	}
	public InputFormatterBase( String loggerName, GrammarBase<?> grammar){
		initialise(grammar, DEFAULT_NOT_ALLOWABLE_CHAR_PATTERN, loggerName);
	}
	public InputFormatterBase( GrammarBase<?> grammar, String notAllowableCharPatern){
		initialise(grammar, notAllowableCharPatern, DEFAULT_LOGGER_NAME);
	}
	public InputFormatterBase( String loggerName, GrammarBase<?> grammar, String notAllowableCharPatern){
		initialise(grammar, notAllowableCharPatern, loggerName);
	}
	private void initialise( GrammarBase<?> grammar, String notAllowableCharPatern, String logName){
		this.grammar = grammar;
		this.notAllowableCharPatern = notAllowableCharPatern;
		if( logName == null){
			this.loggerName = DEFAULT_LOGGER_NAME;
			TestLog.warning( "Cannot process a null logger name. Default value used instead: " + DEFAULT_LOGGER_NAME);
		} else this.loggerName = logName;
	}
	
	/* ##################################################################################
	   ############################### RUNNER ###########################################
	 */
	// intialiser called from all the constructors
	public void searchFacts( String input){
		this.log( "+++++++++++++++++++++++++++++++ FACT ANALISING +++++++++++++++++++++++++++++++++");
		this.tokenisedInputs = tokenize( input, notAllowableCharPatern);
		// start searching for facts in
		formattingStartTimeStamp = System.nanoTime();
		start();
	}
	// Remove all the chars that are not letter or numbers and tokenise by space
	/**
	 * This method is used to pre-filtering the input given from the user. 
	 * In particular it uses {@code allowableCharPatern} to discard not allowable chars
	 * in the input. Then, it tokenise the string and returns each tokens in a list.
	 * @param input the peach-to-text string given by the user.
	 * @param allowableCharPatern the set of char that are allowable in the {@code input}. 
	 * The others will be discarded.
	 * @return the input given by the user where the not allowable chars are removed. Moreover,
	 * the returning value is a list which contains input words (e.g. tokenised by space).
	 */
	protected List< String> tokenize( String input, String notAllowableCharPatern){
		// remove not allowable chars
		String filteredInput = input.trim();
		filteredInput = filteredInput.replaceAll( notAllowableCharPatern, "");
		if( ! input.equals( filteredInput))
			this.logWarning( "++ Input contains not allowable chars that are removed. Actual input: \"" + filteredInput + "\" (original command: \"" + input + "\")");
		// tokenise by space
		List< String> out = new ArrayList< >();
		StringTokenizer tokenizer = new StringTokenizer( filteredInput);
		while ( tokenizer.hasMoreElements())
			out.add( tokenizer.nextElement().toString());
		this.log( "++ given original tokens: " + out);
		// remove not evaluable terms
		if( getGrammar().getUnreasonableWords() != null){
			out.removeAll( getGrammar().getUnreasonableWords());
			this.log( "Not evaluated term list: " + getGrammar().getUnreasonableWords());
		}
		this.log( "++ final input tokens given: " + out);
		return out;
	}

	/* ##################################################################################
	   ############################## GETTERS ##########################################
	 */
	public GrammarBase<?> getGrammar(){
		return this.grammar;
	}
	public List< String> getTokenisedInputs(){
		return this.tokenisedInputs;
	}
	public String getNotAllowableCharPatern() {
		return notAllowableCharPatern;
	}
	public Long getFormattingStatingTimeStamp(){
		return formattingStartTimeStamp;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public ThreadLogger getThreadLogger(){
		return threadLogger;
	}
	
	/* ##################################################################################
	   ############################## SETTERS ##########################################
	 */
	public void setGrammar(GrammarBase<?> grammar) {
		this.grammar = grammar;
	}
	public void setNotAllowableCharPatern(String notAllowableCharPatern) {
		this.notAllowableCharPatern = notAllowableCharPatern;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public void setThreadLogger( ThreadLogger logger){
		this.threadLogger = logger;
	}
	
	/* ##################################################################################
	   ######################## ABSTRACT INTERFACE ######################################
	 */
	// it returns the set of Facts to be evaluate for the next solution. Returns null if no other solutions are found.
	public abstract Facts<?> getNextFacts();
	public abstract Integer getFactsCount();
	protected abstract void start();
	public abstract void stop();
	public abstract void join();
	public abstract boolean isRunning();
	
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
}
