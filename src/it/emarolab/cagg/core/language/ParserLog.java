package it.emarolab.cagg.core.language;

import it.emarolab.cagg.debugging.DebuggingText;
import it.emarolab.cagg.debugging.DebuggingText.LoggerBase;

public class ParserLog {

	// ########################################################################################################################
	// static class ###########################################################################################################
	private ParserLog(){}
	
	// ########################################################################################################################
	// UI logger manager ######################################################################################################
	private static LoggerBase logger;
	
	public static void setLoggers(LoggerBase logger) {
		ParserLog.logger = logger;
	}
	public static void setLoggers( String loggerName) {
		ParserLog.logger = DebuggingText.getLogger( loggerName);
	}
	
	public static LoggerBase getLogger() {
		return logger;
	}
	
	public static void info( Object o){
		if( logger != null)
			logger.info( o);
		else System.err.println( " logger dosn't exists. Lost msg: " + o );
	}
	public static void warning( Object o){
		if( logger != null)
			logger.warning( o);
		else System.err.println( " logger dosn't exists. Lost msg: " + o );
	}
	public static void error( Object o){
		if( logger != null)
			logger.error( o);
		else System.err.println( " logger dosn't exists. Lost msg: " + o );
	}
	public static void error( Exception e){
		if( logger != null)
			logger.error( e);
		else System.err.println( " logger dosn't exists. Lost msg: " + e );
	}
	public static void ok( Object o){
		if( logger != null)
			logger.ok( o);
		else System.err.println( " logger dosn't exists. Lost msg: " + o );
	}	
}
