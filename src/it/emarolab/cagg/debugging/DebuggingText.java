package it.emarolab.cagg.debugging;

import it.emarolab.cagg.core.evaluation.CaggThread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

public class DebuggingText {
	
	/* ##################################################################################
	   ################################# FIELDS #########################################
	 */
	private String loggerName;
	private String configurationPath;
	private LoggerBase logger;

	/* ##################################################################################
	   ############################### CONSTRUCUTOR #####################################
	 */
	public DebuggingText( String loggerName, String configurationPath){
		initialise( loggerName, configurationPath);
	}
	private void initialise( String loggerName, String configurationPath){
		setLoggerName( loggerName);
		setConfigurationPath( configurationPath);
		setLogger( createLogger());
		addLogger();
	}
	protected LoggerBase createLogger(){
		return new Log4j( getLoggerName(), getConfigurationPath()); // il null default on system properties (than change it from there)
	}
	protected void addLogger(){
		addLogger( getLoggerName(), getLogger());
	}
	public static void reconfigureLogger(){
		( ( LoggerContext) LogManager.getContext(false)).reconfigure();
	}
	
	/* ##################################################################################
	   ########################### GETTERS AND SETTERS ##################################
	 */
	public String getConfigurationPath() {
		return configurationPath;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public LoggerBase getLogger() {
		return logger;
	}
	protected void setConfigurationPath(String configurationPath) {
		this.configurationPath = configurationPath;
	}
	protected void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	protected void setLogger(LoggerBase logger) {
		this.logger = logger;
	}
	

	/* ##################################################################################
	   ##################### STATIC UTILITIES FOR LOGGING ###############################
	 */
	public static synchronized void printStackTrace(){
		Thread.dumpStack();
	}

	public static String getFormattedDate(){
		return getFormattedDate("dd-MM-yyyy_HH-mm-ss");
	}
	public static String getFormattedDateMilli(){
		return getFormattedDate("dd-MM-yyyy_HH-mm-ss-SSS");
	}
	public static String getFormattedDate( String format){
		SimpleDateFormat sdf = new SimpleDateFormat( format);
		Date now = new Date();
		String strDate = sdf.format(now);
		return strDate;
	}

	public static void appendOnFileLn( String filePath, String txt){ // commplete path to file (with name and estention)
		appendOnFile( filePath, txt + DebuggingDefaults.SYS_LINE_SEPARATOR);
	}
	public static void appendOnFile( String filePath, String txt){ // commplete path to file (with name and estention)
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter( filePath, true)));
			writer.print( txt);
		} catch (IOException ex) {
			UILog.error( ex);
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				UILog.error( ex);
			}
		}
	}

	/* ##################################################################################
	   ##################### LOGGER STATIC MAP INTERFACE ################################
	 */
	private static Map< String, LoggerBase> loggerMap = new HashMap<>(); 

	protected static void addLogger( String loggerName, LoggerBase logger){
		if( loggerMap.containsKey( loggerName))
			System.err.print( " A logger with name: " + loggerName + " already exists!");
		loggerMap.put( loggerName, logger);
	}
	public static LoggerBase getLogger( String loggerName){
		if( ! loggerMap.containsKey( loggerName))
			System.err.print( " A logger with name: " + loggerName + " does not exists!");
		return loggerMap.get( loggerName);
	}
	public static void removeLogger( String loggerName){
		LoggerBase removed = loggerMap.remove( loggerName);
		if( removed == null)
			System.err.println( " Logger " + loggerName + " does not exists and it cannot be removed");
		else System.err.println( " Logger " + loggerName + " removed!");
	}

	/* ##################################################################################
	   ######################### LOGGING STATIC INTERFACE ###############################
	 */
	public static class Logger{

		public synchronized static void info( int backStep, String loggerName, String threadName, Object msg){
			getLogger(loggerName).info( backStep, threadName, msg);
		}
		public synchronized static void info( int backStep, String loggerName, Object msg){
			getLogger(loggerName).info( backStep, msg);
		}
		public synchronized static void info( int backStep, Object msg){
			for( LoggerBase l : loggerMap.values())
				l.info( backStep, msg);
		}
		public synchronized static void info( String loggerName, Object msg){
			getLogger(loggerName).info( msg);
		}
		public synchronized static void info( Object msg){
			for( LoggerBase l : loggerMap.values())
				l.info( msg);
		}

		public synchronized static void warning( int backStep, String loggerName, String threadName, Object msg){
			getLogger(loggerName).warning( backStep, threadName, msg);
		}
		public synchronized static void warning( int backStep, String loggerName, Object msg){
			getLogger(loggerName).warning( backStep, msg);
		}
		public synchronized static void warning( int backStep, Object msg){
			for( LoggerBase l : loggerMap.values())
				l.warning( backStep, msg);
		}
		public synchronized static void warning( String loggerName, Object msg){
			getLogger(loggerName).warning( msg);
		}
		public synchronized static void warning( Object msg){
			for( LoggerBase l : loggerMap.values())
				l.warning( msg);
		}

		public synchronized static void error( int backStep, String loggerName, String threadName, Object msg){
			getLogger( loggerName).error( backStep, threadName, msg);
		}
		public synchronized static void error( int backStep, String loggerName, Object msg){
			getLogger(loggerName).error( backStep, msg);
		}
		public synchronized static void error( int backStep, Object msg){
			for( LoggerBase l : loggerMap.values())
				l.error( backStep, msg);
		}
		public synchronized static void error( String loggerName, Object msg){
			getLogger(loggerName).error( msg);
		}
		public synchronized static void error( Object msg){
			for( LoggerBase l : loggerMap.values())
				l.error( msg);
		}

		public synchronized static void error( int backStep, String loggerName, String threadName, Exception e){ 
			getLogger( loggerName).error( backStep, threadName, e);
		}
		public synchronized static void error( int backStep, String loggerName, Exception e){ 
			getLogger( loggerName).error( backStep, e);
		}
		public synchronized static void error( int backStep, Exception e){
			for( LoggerBase l : loggerMap.values())
				l.info( backStep, e);
		}
		public synchronized static void error( String loggerName, Exception e){
			getLogger(loggerName).error( e);
		}
		public synchronized static void error( Exception e){
			for( LoggerBase l : loggerMap.values())
				l.info( e);
		}

		public synchronized static void ok( int backStep, String loggerName, String threadName, Object msg){
			getLogger(loggerName).ok( backStep, threadName, msg);
		}
		public synchronized static void ok( int backStep, String loggerName, Object msg){
			getLogger(loggerName).ok( backStep, msg);
		}
		public synchronized static void ok( int backStep, Object msg){
			for( LoggerBase l : loggerMap.values())
				l.ok( backStep, msg);
		}
		public synchronized static void ok( String loggerName, Object msg){
			getLogger(loggerName).ok( msg);
		}
		public synchronized static void ok( Object msg){
			for( LoggerBase l : loggerMap.values())
				l.ok( msg);
		}
	}

	/* ##################################################################################
	   ############################ LO SYST_PROPERTY_PATH, pGGER INTERFACE ####################################
	 */
	// log4j2 interface
	public class Log4j extends LoggerBase{
		/// set the system property to be used in the log4j configuration file
		public static final String SYST_PROPERTY_CALLER_CLASS = "caggLoggerCallerClass";
		public static final String SYST_PROPERTY_CALLER_METHOD = "caggLoggerCallerMethod";
		public static final String SYST_PROPERTY_CALLER_LINE = "caggLoggerCallerLine";
		public static final String SYST_PROPERTY_CALLER = "caggLoggerCaller";
		public static final String SYST_PROPERTY_ATTRIBUTE = "caggLoggerAttribute";
		public static final String SYST_PROPERTY_CALLER_CUT_SYMBOL = "-";
		public static final int SYST_PROPERTY_CALLER_CHAR_CNT = 35;
		public static final int SYST_PROPERTY_NAME_THREAD_CHAR_CNT = 15;
		public static final int SYST_PROPERTY_CALLER_LINE_CHAR_CNT = 4;
		public static final int SYST_PROPERTY_ATTRIBUTE_CHAR_CNT = 5;
		public static final int DEFAULT_CALLER_BACK_STEP_ON_START_TRACE = 4;
		/// fields 
		private org.apache.log4j.Logger logger4j;
		/// constructor
		public Log4j( String loggerName, String configFilePath){
			this.logger4j = org.apache.log4j.Logger.getLogger( loggerName);
			if( configFilePath != null & loggerName != null){
				Configurator.initialize( loggerName, configFilePath);
				info( "Log4j2 initialised with name: " + loggerName + " and file: " + configFilePath);
			} else info( "Log4j2 configuration file (" + configFilePath + ") not loaded!");
		}

		public void closeLogger(){
			info( "Log4j2 shutting down!");
			LoggerContext ctx = (LoggerContext) LogManager.getContext();
			Configurator.shutdown( ctx);
		}

		/// methods for logging
		@Override
		public synchronized void info(Object msg) {
			this.info( 0, msg);
		}
		@Override
		public synchronized void info( int backStep, Object msg) {
			this.info( backStep, null, msg);
		}
		@Override
		public synchronized void info(int backStep, String threadName, Object msg) {
			this.info( backStep, threadName, null, msg);
		}
		@Override
		public synchronized void info(int backStep, String threadName, String attribute, Object msg) {
			synchronized( logger4j){
				applyPropertyCallerMethod( DEFAULT_CALLER_BACK_STEP_ON_START_TRACE + backStep);
				applayPropertyThreadLoggerName( threadName);
				applayPropertyAttribute( attribute);
				reconfigureLogger();
				this.logger4j.info( msg);
				resetProperties();
				reconfigureLogger();
			}
		}
		
		@Override
		public synchronized void warning(Object msg) {
			this.warning( 0, msg);
		}
		@Override
		public synchronized void warning( int backStep, Object msg) {
			this.warning( backStep, null, msg);
		}
		@Override
		public synchronized void warning( int backStep, String threadName, Object msg) {
			this.warning(backStep, threadName, null, msg);
		}
		@Override
		public synchronized void warning( int backStep, String threadName, String attribute, Object msg) {
			synchronized( logger4j){
				applyPropertyCallerMethod( DEFAULT_CALLER_BACK_STEP_ON_START_TRACE + backStep);
				applayPropertyThreadLoggerName( threadName);
				applayPropertyAttribute( attribute);
				reconfigureLogger();
				this.logger4j.warn( msg);
				resetProperties();
				reconfigureLogger();
			}
		}
		
		@Override
		public synchronized void error(Object msg) {
			this.error( 0, msg);
		}
		@Override
		public synchronized void error( int backStep, Object msg) {
			this.error( backStep, null, msg);
		}
		@Override
		public synchronized void error( int backStep, String threadName, Object msg) {
			this.error(backStep, threadName, null, msg);
		}
		@Override
		public synchronized void error( int backStep, String threadName, String attribute, Object msg) {
			synchronized( logger4j){
				applyPropertyCallerMethod( DEFAULT_CALLER_BACK_STEP_ON_START_TRACE + backStep);
				applayPropertyThreadLoggerName( threadName);
				applayPropertyAttribute( attribute);
				reconfigureLogger();
				this.logger4j.error( msg);
				resetProperties();
				reconfigureLogger();
			}
		}
		
		@Override
		public synchronized void error(Exception e) {
			this.error( 0, e);
		}
		@Override
		public synchronized void error( int backStep, Exception e) {
			this.error( backStep, CaggThread.DEFAULT_NAME_THREAD, e);
		}
		@Override
		public synchronized void error( int backStep, String threadName, Exception e) { 
			this.error( backStep, threadName, null, e);
		}
		@Override
		public synchronized void error( int backStep, String threadName, String attribute, Exception e) { 
			synchronized( logger4j){
				applyPropertyCallerMethod( DEFAULT_CALLER_BACK_STEP_ON_START_TRACE + backStep);
				applayPropertyThreadLoggerName( threadName);
				applayPropertyAttribute( attribute);
				reconfigureLogger();
				this.logger4j.error( "Exception", e);
				resetProperties();
				reconfigureLogger();
			}
		}
		
		@Override
		public synchronized void ok(Object msg) {
			this.ok( 0, msg);
		}
		@Override
		public synchronized void ok( int backStep, Object msg) {
			this.ok( backStep, null, msg);
		}
		@Override
		public synchronized void ok( int backStep, String threadName, Object msg) {
			this.ok( backStep, threadName, null, msg);
		}
		@Override
		public synchronized void ok( int backStep, String threadName, String attribute, Object msg) {
			synchronized( logger4j){
				applyPropertyCallerMethod( DEFAULT_CALLER_BACK_STEP_ON_START_TRACE + backStep);
				applayPropertyThreadLoggerName( threadName);
				applayPropertyAttribute( attribute);
				reconfigureLogger();
				this.logger4j.debug( msg);
				resetProperties();
				reconfigureLogger();
			}
		}
		
		// function for formatting info stored on System.properties
		private synchronized void applyPropertyCallerMethod( int backStep){		
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			try{
				StackTraceElement trace = stacktrace[ backStep]; // [0] -> java, [1] -> getMethodLog, [2] -> who call getMethodLog, [3] -> who call who call getMethodLog .....
				//String className = trace.getClassName().substring( trace.getClassName().lastIndexOf( ".") + 1);
				// set property for the log4j configuration
				System.setProperty( SYST_PROPERTY_CALLER_CLASS, trace.getClassName());
				System.setProperty( SYST_PROPERTY_CALLER_METHOD, trace.getMethodName());
				System.setProperty( SYST_PROPERTY_CALLER_LINE, String.valueOf( trace.getLineNumber()));
				System.setProperty( SYST_PROPERTY_CALLER, pad( trace.getClassName() + "#" + trace.getMethodName(), true, SYST_PROPERTY_CALLER_CHAR_CNT)
						+ ":" + pad( String.valueOf( trace.getLineNumber()), false, SYST_PROPERTY_CALLER_LINE_CHAR_CNT));
			}
			catch( Exception e){
				System.err.println( "Cannot store logging information on System.properties. Lost caller info.");
			}
		}
		private synchronized void applayPropertyThreadLoggerName( String threadName){
			// set the task name if is not provided in the system properties from the cagg thread class
			if( threadName == null)
				threadName = Thread.currentThread().getName();
			else if( threadName.isEmpty())
				threadName = Thread.currentThread().getName();
			// pad and set the value
			try{
				System.setProperty( CaggThread.SYST_PROPERTY_RUNNABLE_NAME, pad( threadName, true, SYST_PROPERTY_NAME_THREAD_CHAR_CNT));
			} catch( Exception e){
				System.err.println( "Cannot store logging information on System.properties. Lost thread name: " + threadName);
			}
		}
		private synchronized void applayPropertyAttribute( String attribute){
			String attr = attribute;
			if( attribute == null)
				attr = "??";
			else if( attribute.isEmpty())
				attr = "??";
			// pad and set the value
			try{
				System.setProperty( SYST_PROPERTY_ATTRIBUTE, pad( attr, true, SYST_PROPERTY_ATTRIBUTE_CHAR_CNT));
			} catch( Exception e){
				System.err.println( "Cannot store logging information on System.properties. Lost attribute: " + attr);
			}
		}
		private synchronized void resetProperties(){
			try{
				// reset thread name
				System.setProperty( CaggThread.SYST_PROPERTY_RUNNABLE_NAME, pad( "??", true, SYST_PROPERTY_NAME_THREAD_CHAR_CNT));
				// reset caller info
				System.setProperty( SYST_PROPERTY_CALLER_CLASS, "??");
				System.setProperty( SYST_PROPERTY_CALLER_METHOD, "??");
				System.setProperty( SYST_PROPERTY_CALLER_LINE, "??");
				System.setProperty( SYST_PROPERTY_CALLER, pad( "??" + "#" + "??", true, SYST_PROPERTY_CALLER_CHAR_CNT)
						+ ":" + pad( String.valueOf( "??"), false, SYST_PROPERTY_CALLER_LINE_CHAR_CNT));
				// reset attribute
				System.setProperty( SYST_PROPERTY_ATTRIBUTE, pad( "??", true, SYST_PROPERTY_ATTRIBUTE_CHAR_CNT));
				// the path is not resetted!!!!
			} catch( Exception e){
				System.err.println( "Cannot store logging information on System.properties.");
			}
		}

		// function to make string of a given length aligned to left or right
		private synchronized String pad( String str, boolean left, int size){ // to make the string of the same length by adding/removing char on left or right side
			if( str.length() == size)
				return str;
			if( str.length() < size){
				if( left)
					return String.format("%1$" + size + "s", str);
				else return String.format("%1$-" + size + "s", str);
			} else {
				if( left)
					return SYST_PROPERTY_CALLER_CUT_SYMBOL + str.substring( str.length() - size + SYST_PROPERTY_CALLER_CUT_SYMBOL.length());
				else return str.substring( 0, size - SYST_PROPERTY_CALLER_CUT_SYMBOL.length()) + SYST_PROPERTY_CALLER_CUT_SYMBOL;
			}
		}
	}
	
	// logging base interface
	public abstract class LoggerBase{
		public abstract void info( Object msg);
		public abstract void info( int backStep, Object msg);
		public abstract void info( int loggingStepBack, String threadName, Object msg);
		public abstract void info( int loggingStepBack, String threadName, String attribute, Object msg);
		
		public abstract void warning( Object msg);
		public abstract void warning( int backStep, Object msg);
		public abstract void warning( int backStep, String threadName, Object msg);
		public abstract void warning( int backStep, String threadName, String attribute, Object msg);
		
		public abstract void error( Object msg);
		public abstract void error( int backStep, Object msg);
		public abstract void error( int backStep, String threadName, Object msg);
		public abstract void error( int backStep, String threadName, String attribute, Object msg);
		
		public abstract void error( Exception e);
		public abstract void error( int backStep, Exception e);
		public abstract void error( int backStep, String threadName, Exception e);
		public abstract void error( int backStep, String threadName, String attribute, Exception e);
		
		public abstract void ok( Object msg);
		public abstract void ok( int backStep, Object msg);
		public abstract void ok( int backStep, String threadName, Object msg);
		public abstract void ok( int backStep, String threadName, String attribute, Object masg);
	}

	@Override
	public String toString(){
		return "active logging map: " + loggerMap;
	}
}
