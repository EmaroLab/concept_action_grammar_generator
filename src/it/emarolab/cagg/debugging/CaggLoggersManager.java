package it.emarolab.cagg.debugging;

import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.interfaces.TestLog;

public class CaggLoggersManager {
	/* ##################################################################################
	   ################################ CONSTANTS #######################################
	 */
	public static final String DEFAULT_LOGGING_NAME_EVALUATOR = "log-evaluator"; // evaluator operations and debug
	public static final String DEFAULT_LOGGING_NAME_FORMATTER = "log-formatter"; // formatter operations and debug
	public static final String DEFAULT_LOGGING_NAME_UI = "log-UI"; // gui and debugging tools (pkg: it.emarolab.debugging)
	public static final String DEFAULT_LOGGING_NAME_PARSING = "log-parsing"; // parsing, trees and structure debug (pkg: it.emarolab.core and it.emarolab.core.language)
	public static final String DEFAULT_LOGGING_NAME_GRAMMAR = "log-grammar"; // compiler (pkg: it.emarolab.cagg.core.evaluation.semanticGrammar)
	public static final String DEFAULT_LOGGING_NAME_TEST = "log-test"; // compiler (pkg: it.emarolab.cagg.example and it.emarolab.cagg.interfaces) TODO : to set in code!!!!
	public static final String DEFAULT_CONFIGURATION_PATH = DebuggingDefaults.PATH_LOG_CONF_BASE + "log4j_guiConf.xml"; 
	
	public static final String SYST_PROPERTY_PATH = "caggLoggerPath";
	
	/* ##################################################################################
	   ################################## FIELDS ########################################
	 */
	private LoggersNames names;
	private String log4jConfigPath;
		
	/* ##################################################################################
	   ############################### CONSTRUCTOR ######################################
	 */
	public CaggLoggersManager(){
		initialise( new LoggersNames(), DEFAULT_CONFIGURATION_PATH);
	}
	public CaggLoggersManager( String log4jConfigPath){
		initialise( new LoggersNames(), log4jConfigPath);
	}
	public CaggLoggersManager( String evaluatorLoggerName, String formatterLoggerName, String uiLoggerName, String parsingLoggerName, String grammarLoggingName, String testLoggerName){
		LoggersNames names = new LoggersNames( evaluatorLoggerName, formatterLoggerName, uiLoggerName, parsingLoggerName, grammarLoggingName, testLoggerName);
		initialise( names, DEFAULT_CONFIGURATION_PATH);
	}
	public CaggLoggersManager( String evaluatorLoggerName, String formatterLoggerName, String uiLoggerName, String parsingLoggerName, String grammarLoggingName, String testLoggerName, String log4jConfigPath){
		LoggersNames names = new LoggersNames( evaluatorLoggerName, formatterLoggerName, uiLoggerName, parsingLoggerName, grammarLoggingName, testLoggerName);
		initialise( names, log4jConfigPath);
	}
	public CaggLoggersManager( String uiLoggerName, String parsingLoggerName, String grammarLoggingName, String testLoggerName){
		LoggersNames names = new LoggersNames( uiLoggerName, parsingLoggerName, grammarLoggingName, testLoggerName);
		initialise( names, DEFAULT_CONFIGURATION_PATH);
	}
	public CaggLoggersManager( String uiLoggerName, String parsingLoggerName, String grammarLoggingName, String testLoggerName, String log4jConfigPath){
		LoggersNames names = new LoggersNames( uiLoggerName, parsingLoggerName, grammarLoggingName, testLoggerName);
		initialise( names, log4jConfigPath);
	}
	private void initialise( LoggersNames names, String log4jConfigPath){
		this.names = new LoggersNames();
		this.log4jConfigPath = log4jConfigPath;
		this.applyAll();
		setFilePath( null);
	}
	
	/* ##################################################################################
	   ############################## GETTERS & SETTERS #################################
	 */
	public LoggersNames getNames() {
		return names;
	}
	public String getLog4jConfigPath() {
		return log4jConfigPath;
	}	

	/* ##################################################################################
	   ########################### DebuggingText INTERFACE ##############################
	 */	
	public void applyAll(){
		// initialise logger in the commun mapp stored on DebuggingText
		if( names.isComplete){
			apply( names.getEvaluatorLoggerName());
			apply( names.getFormatterLoggerName());
		}
		apply( names.getUILoggerName());
		apply( names.getParsingLoggerName());
		apply( names.getGrammarLoggerName());
		apply( names.getTestLoggerName());
		// initialise logger in the main classes 
		UILog.setLoggers( DebuggingText.getLogger( names.getUILoggerName()));
		ParserLog.setLoggers( DebuggingText.getLogger( names.getParsingLoggerName()));
		GrammarLog.setLoggers( DebuggingText.getLogger( names.getGrammarLoggerName()));
		TestLog.setLoggers( DebuggingText.getLogger( names.getTestLoggerName()));
		names.logUIInfo( "Logger initialised: " + names);
	}
	protected void apply( String name){
		new DebuggingText( name, getLog4jConfigPath());
	}
	
	public static void setFilePath( String path){
		String p = path;
		if( path == null)
			p = DebuggingDefaults.PATH_LOG_BASE + DebuggingText.getFormattedDate();
		else if( path.isEmpty())
			p = DebuggingDefaults.PATH_LOG_BASE + DebuggingText.getFormattedDate();
		// pad and set the value
		try{
			UILog.info( "Log output redirected on file: " + p);
			System.setProperty( SYST_PROPERTY_PATH, p);
		} catch( Exception e){
			System.err.println( "Cannot store logging information on System.properties. Lost attribute: " + p);
		}
	}
	public static String getFilePath(){
		return System.getProperty( SYST_PROPERTY_PATH);
	}

	/* ##################################################################################
	   ################### UTILITY CLASS FOR BUCH OF LOGGERS ############################
	 */
	public class LoggersNames{
		//// fields
		private String evaluatorLoggerName = null;
		private String formatterLoggerName = null;
		private String uiLoggerName = null;
		private String parsingLoggingName = null;
		private String grammarLoggingName = null;
		private String testLoggingName = null;
		protected Boolean isComplete = null;
		//// constrcutor
		public LoggersNames(){
			this.evaluatorLoggerName = DEFAULT_LOGGING_NAME_EVALUATOR;
			this.formatterLoggerName = DEFAULT_LOGGING_NAME_FORMATTER;
			this.uiLoggerName = DEFAULT_LOGGING_NAME_UI;
			this.parsingLoggingName = DEFAULT_LOGGING_NAME_PARSING;
			this.grammarLoggingName = DEFAULT_LOGGING_NAME_GRAMMAR;
			this.testLoggingName = DEFAULT_LOGGING_NAME_TEST;
			this.isComplete = true;
		} 
		public LoggersNames( String evaluatorLoggerName, String formatterLoggerName, String uiLoggerName, String parsingLoggingName, String grammarLoggingName, String testLoggingName) {
			this.evaluatorLoggerName = evaluatorLoggerName;
			this.formatterLoggerName = formatterLoggerName;
			this.uiLoggerName = uiLoggerName;
			this.parsingLoggingName = parsingLoggingName;
			this.grammarLoggingName = grammarLoggingName;
			this.testLoggingName = testLoggingName;
			this.isComplete = true;
		}
		public LoggersNames(String uiLoggerName, String parsingLoggerName, String grammarLoggingName, String testLoggingName) {
			this.uiLoggerName = uiLoggerName;
			this.parsingLoggingName = parsingLoggerName;
			this.grammarLoggingName = grammarLoggingName;
			this.testLoggingName = testLoggingName;
			this.isComplete = false;
		}
		//// getters
		public String getEvaluatorLoggerName() {
			return evaluatorLoggerName;
		}
		public String getFormatterLoggerName() {
			return formatterLoggerName;
		}
		public String getUILoggerName() {
			return uiLoggerName;
		}
		public String getParsingLoggerName() {
			return parsingLoggingName;
		}
		public String getGrammarLoggerName() {
			return grammarLoggingName;
		}
		public String getTestLoggerName() {
			return testLoggingName;
		}
		///// interfaces
		@Override
		public String toString(){
			return "Logger names: {" + getParsingLoggerName() + ", " + getEvaluatorLoggerName() + ", " + getFormatterLoggerName() 
						+ ", " + getUILoggerName() + ", " + getGrammarLoggerName() + ", " + getTestLoggerName() + "}";
		}
		// logging for evaluator
		public void logEvaluatorInfo( Object o){
			DebuggingText.getLogger( getEvaluatorLoggerName()).info( o);
		}
		public void logEvaluatorWarning( Object o){
			DebuggingText.getLogger( getEvaluatorLoggerName()).warning( o);
		}
		public void logEvaluatorError( Object o){
			DebuggingText.getLogger( getEvaluatorLoggerName()).error( o);
		}
		public void logEvaluatorError( Exception e){
			DebuggingText.getLogger( getEvaluatorLoggerName()).error( e);
		}
		public void logEvaluatorOk( Object o){
			DebuggingText.getLogger( getEvaluatorLoggerName()).ok( o);
		}
		// logging for formatter
		public void logFormatterInfo( Object o){
			DebuggingText.getLogger( getFormatterLoggerName()).info( o);
		}
		public void logFormatterWarning( Object o){
			DebuggingText.getLogger( getFormatterLoggerName()).warning( o);
		}
		public void logFormatterError( Object o){
			DebuggingText.getLogger( getFormatterLoggerName()).error( o);
		}
		public void logFormatterError( Exception e){
			DebuggingText.getLogger( getFormatterLoggerName()).error( e);
		}
		public void logFormatterOk( Object o){
			DebuggingText.getLogger( getFormatterLoggerName()).ok( o);
		}
		// logging for UI
		public void logUIInfo( Object o){
			DebuggingText.getLogger( getUILoggerName()).info( o);
		}
		public void logUIWarning( Object o){
			DebuggingText.getLogger( getUILoggerName()).warning( o);
		}
		public void logUIError( Object o){
			DebuggingText.getLogger( getUILoggerName()).error( o);
		}
		public void logUIError( Exception e){
			DebuggingText.getLogger( getUILoggerName()).error( e);
		}
		public void logUIOk( Object o){
			DebuggingText.getLogger( getUILoggerName()).ok( o);
		}
		// logging for parsing
		public void logParsingInfo( Object o){
			DebuggingText.getLogger( getParsingLoggerName()).info( o);
		}
		public void logParsingWarning( Object o){
			DebuggingText.getLogger( getParsingLoggerName()).warning( o);
		}
		public void logParsingError( Object o){
			DebuggingText.getLogger( getParsingLoggerName()).error( o);
		}
		public void logParsingError( Exception e){
			DebuggingText.getLogger( getParsingLoggerName()).error( e);
		}
		public void logParsingOk( Object o){
			DebuggingText.getLogger( getParsingLoggerName()).ok( o);
		}
		// logging for grammar
		public void logGrammarInfo( Object o){
			DebuggingText.getLogger( getGrammarLoggerName()).info( o);
		}
		public void logGrammarWarning( Object o){
			DebuggingText.getLogger( getGrammarLoggerName()).warning( o);
		}
		public void logGrammarError( Object o){
			DebuggingText.getLogger( getGrammarLoggerName()).error( o);
		}
		public void logGrammarError( Exception e){
			DebuggingText.getLogger( getGrammarLoggerName()).error( e);
		}
		public void logGrammarOk( Object o){
			DebuggingText.getLogger( getGrammarLoggerName()).ok( o);
		}
		// logging for test
		public void logTestInfo( Object o){
			DebuggingText.getLogger( getTestLoggerName()).info( o);
		}
		public void logTestWarning( Object o){
			DebuggingText.getLogger( getTestLoggerName()).warning( o);
		}
		public void logTestError( Object o){
			DebuggingText.getLogger( getTestLoggerName()).error( o);
		}
		public void logTestError( Exception e){
			DebuggingText.getLogger( getTestLoggerName()).error( e);
		}
		public void logTestOk( Object o){
			DebuggingText.getLogger( getTestLoggerName()).ok( o);
		}
	}
}
