package it.emarolab.cagg.interfaces;

import it.emarolab.cagg.debugging.DebuggingText;
import it.emarolab.cagg.debugging.DebuggingText.LoggerBase;

public class TestLog {

	// ########################################################################################################################
	// static class ###########################################################################################################
	private TestLog(){}
	
	// ########################################################################################################################
	// UI logger manager ######################################################################################################
	private static LoggerBase logger;
	
	public static void setLoggers(LoggerBase logger) {
		TestLog.logger = logger;
	}
	public static void setLoggers(String loggerName) {
		TestLog.logger = DebuggingText.getLogger(loggerName);
	}
		
	public static LoggerBase getLogger() {
		return logger;
	}
	
	public static synchronized void info( Object msg){
		if( logger != null)
			logger.info( 1, msg);
		else System.err.println( " logger dosn't exists. Lost msg: " + msg );
	}
	public static synchronized void warning( Object msg){
		if( logger != null)
			logger.warning( 1, msg);
		else System.err.println( " logger dosn't exists. Lost msg: " + msg );
	}
	public static synchronized void error( Object msg){
		if( logger != null)
			logger.error( 1, msg);
		else System.err.println( " logger dosn't exists. Lost msg: " + msg );
	}
	public static synchronized void error( Exception e){
		if( logger != null)
			logger.error( 1, e);
		else System.err.println( " logger dosn't exists. Lost msg: " + e );
	}
	public static synchronized void ok( Object msg){
		if( logger != null)
			logger.ok( 1, msg);
		else System.err.println( " logger dosn't exists. Lost msg: " + msg );
	}	
}
