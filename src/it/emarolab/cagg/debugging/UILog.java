package it.emarolab.cagg.debugging;

import it.emarolab.cagg.core.evaluation.CaggThread.ThreadLogger;
import it.emarolab.cagg.debugging.DebuggingText.LoggerBase;

public class UILog {

	// ########################################################################################################################
	// static class ###########################################################################################################
	private UILog(){}
	
	// ########################################################################################################################
	// UI logger manager ######################################################################################################
	private static LoggerBase logger = null;
	private static ThreadLogger threadLogger = null;
	
	public static void setLoggers(LoggerBase logger) { // reset by assign null
		UILog.logger = logger;
	}
	public static void setLoggers( String loggerName) { // reset by assign null
		UILog.logger = DebuggingText.getLogger( loggerName);
	}
	public static void setLoggers(ThreadLogger threadlogger) { // reset by assign null
		UILog.threadLogger = threadlogger;
	}	
	
	public static LoggerBase getLogger() {
		return logger;
	}
	public static ThreadLogger getThreadLogger() {
		return threadLogger;
	}
	
	public static synchronized void info( Object o){
		if( threadLogger != null)
			threadLogger.info( o);
		else if( logger != null)
			logger.info( o);
		else System.err.println( " logger dosn't exists. Lost INFO msg: " + o );
	}
	public static synchronized void warning( Object o){
		if( threadLogger != null)
			threadLogger.warning( o);
		else if( logger != null)
			logger.warning( o);
		else System.err.println( " logger dosn't exists. Lost WARNING msg: " + o );
	}
	public static synchronized void error( Object o){
		if( threadLogger != null)
			threadLogger.error( o);
		else if( logger != null)
			logger.error( o);
		else System.err.println( " logger dosn't exists. Lost ERROR msg: " + o );
	}
	public static synchronized void error( Exception e){
		if( threadLogger != null)
			threadLogger.error( e);
		else if( logger != null)
			logger.error( e);
		else System.err.println( " logger dosn't exists. Lost ERROR msg: " + e );
	}
	public static synchronized void ok( Object o){
		if( threadLogger != null)
			threadLogger.ok( o);
		if( logger != null)
			logger.ok( o);
		else System.err.println( " logger dosn't exists. Lost DEBUG msg: " + o );
	}
}
