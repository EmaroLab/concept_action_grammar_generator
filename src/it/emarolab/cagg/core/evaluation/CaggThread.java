package it.emarolab.cagg.core.evaluation;

import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingText.Logger;
import it.emarolab.cagg.debugging.UILog;

public abstract class CaggThread extends Thread{
	
	/* ##################################################################################
	   ############################# CONSTANTS ###########################################
	 */
	public static final String DEFAULT_NAME_LOGGER = CaggLoggersManager.DEFAULT_LOGGING_NAME_UI;
	public static final String DEFAULT_NAME_THREAD = "CaggThread";
	public static final int DEFAULT_LOG_STEP_BACK = 3;
	public static final String SYST_PROPERTY_RUNNABLE_NAME = "caggLoggerThread";
	
	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	// field for constructors
	private String threadName;
	private Long runnableId;
	private String loggerName;
	// internal fields (setters)
	private int loggingStepBack = DEFAULT_LOG_STEP_BACK; // in the stake trace for get the producer of the logging message
	private Long startingTime = null, endingTime = null; // time stamp in nanosec
	private Boolean running = false;
	// static management of the id
	//private static Long INSTANCE_CNT = 0L;
	private static Long assignId(){
		/*if( INSTANCE_CNT >= Long.MAX_VALUE){
			UILog.warning( "Thread pool runnable id reach its limit: " + Long.MAX_VALUE + ". Counter resetted!");
			INSTANCE_CNT = 0L;
		}
		return INSTANCE_CNT++;*/
		
		return Thread.currentThread().getId();
	}
	
	/* ##################################################################################
	   ############################ CONSTRUCTORS ########################################
	 */
	public CaggThread(){
		initialise( DEFAULT_NAME_THREAD, DEFAULT_NAME_LOGGER);
	}
	public CaggThread( String name){
		initialise( name, DEFAULT_NAME_LOGGER);
	}
	public CaggThread( String name, String loggerName){
		initialise( name, loggerName);
	}
	private void initialise( String name, String loggerName){
		// generate sequential runnable id
		this.runnableId = assignId();
		// set the name of the thread
		if( name == null)
			this.threadName = DEFAULT_NAME_THREAD;
		else this.threadName = name;
		// set the name of the logger
		if( loggerName == null)
			this.loggerName = DEFAULT_NAME_LOGGER;
		else this.loggerName = loggerName;
		// assign method to show uncatched exceptions
		this.setUncaughtExceptionHandler( new ExceptionHandler());
	}

	/* ##################################################################################
	   ############################### SETTERS ##########################################
	 */
	public synchronized void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public synchronized void setRunnableId(Long runnableId) {
		this.runnableId = runnableId;
	}
	public synchronized void setLoggerName( String name) {
		this.loggerName = name;
	}
	public synchronized void setLoggingStepBack(int loggingStepBack) {
		this.loggingStepBack = loggingStepBack;
	}
	public synchronized void shouldRun( boolean flag){
		this.running = flag;
	}

	/* ##################################################################################
	   ############################### GETTERS ##########################################
	 */
	public synchronized String getFormattedName(){
		return threadName + "-" + runnableId;
	}
	public synchronized String getThreadName() {
		return threadName;
	}
	public synchronized Long getRunnableId() {
		return runnableId;
	}
	public synchronized String getLoggerName() {
		return loggerName;
	}
	public synchronized int getLoggingStepBack() {
		return loggingStepBack;
	}
	public synchronized Long getStartingTime() {
		return startingTime;
	}
	public synchronized Long getEndingTime() {
		return endingTime;
	}
	public synchronized boolean isRunning(){
		return running;
	}

	/* ##################################################################################
	   ############################### LOGGERS ##########################################
	 */
	public synchronized ThreadLogger getThreadLogger(){
		return new ThreadLogger();
	}
	public synchronized void log( Object msg){
		getThreadLogger().info( msg);
	}
	public synchronized void logWarning( Object msg){
		getThreadLogger().warning(msg);
	}
	public synchronized void logOk( Object msg){
		getThreadLogger().ok( msg);
	}
	public synchronized void logError( Object msg){
		getThreadLogger().error( msg);
	}
	public synchronized void logError( Exception e){
		getThreadLogger().error( e);
	}
	// UTILITY CLASS: to be used for externally get the thread logger and use this strem to produce message
	public class ThreadLogger{
		// Default empty  constructors (perhaps make it static)
		
		// method to easy format log from inner class used by threads
		public synchronized void info( Object msg){
			//System.setProperty( SYST_PROPERTY_RUNNABLE_NAME, getFormattedName());
			Logger.info( getLoggingStepBack(), loggerName, getFormattedName(), msg); // systemProperties automatically resetted!!!
		}
		public synchronized void warning( Object msg){
			//System.setProperty( SYST_PROPERTY_RUNNABLE_NAME, getFormattedName());
			Logger.warning( getLoggingStepBack(), loggerName, getFormattedName(), msg); // systemProperties automatically resetted!!!
		}
		public synchronized void ok( Object msg){
			//System.setProperty( SYST_PROPERTY_RUNNABLE_NAME, getFormattedName());
			Logger.ok( getLoggingStepBack(), loggerName, getFormattedName(), msg); // systemProperties automatically resetted!!!
		}
		public synchronized void error( Object msg){
			//System.setProperty( SYST_PROPERTY_RUNNABLE_NAME, getFormattedName());
			Logger.error( getLoggingStepBack(), loggerName, getFormattedName(), msg); // systemProperties automatically resetted!!!
		}
		public synchronized void error( Exception e){
			//System.setProperty( SYST_PROPERTY_RUNNABLE_NAME, getFormattedName());
			Logger.error( getLoggingStepBack(), loggerName, getFormattedName(), e); // systemProperties automatically resetted!!!
		}
	}
		
	/* ##################################################################################
	   ########################### RUNNING INTERFACE ####################################
	 */
	
	public synchronized void terminate(){
		this.running = false;
	}
	
	@Override
	public synchronized void start() {
		super.start();
		// wait that the flag is set (actually run)
		if( ! running){
			try {
				this.wait();
			} catch (InterruptedException e) {
				logError( e);
			}
		}
	}
	@Override
	public void run() {
		preImplement();
		synchronized( this){
			running = true;
			this.notifyAll();
		}
		this.startingTime = getTimeStamp();
		implement();
		this.running = false;
		this.endingTime = getEndingTime();
		postImplement();
	}
	// here white your runnable and remember to check for isRunning flag to actually terminate the computations
	public void preImplement(){};
	public abstract void implement();
	public void postImplement(){};
	
	
	/* ##################################################################################
	   ############################ TIMING INTERFACE ####################################
	 */
	protected synchronized Long getTimeStamp(){
		return System.nanoTime();
	}
	protected synchronized Long getRunningTime(){
		if( running)
			return getTimeStamp() - this.startingTime;
		return this.endingTime - this.startingTime;
	}
}

// to get uncatched exception out of the main thread
class ExceptionHandler implements Thread.UncaughtExceptionHandler{
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if( t instanceof CaggThread){
			CaggThread caggT = ( CaggThread) t;
			caggT.logError( e);
		} else UILog.error( e);
	}
}
