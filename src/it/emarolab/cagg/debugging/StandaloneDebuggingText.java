package it.emarolab.cagg.debugging;

import it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.DebuggingText.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *    
 * <p>
 * This class is used to initialise the Object ({@link it.emarolab.cagg.debugging.StandaloneDebuggingText.ConsoleLogRidirector})
 * that is used to rederect logging messages of all the system to the GUI and Java console. <br>
 * It is important to be aware that for use the system and its logging features ({@link it.emarolab.cagg.debugging.StandaloneDebuggingText.Logger})
 * thorugh API, the main application function must call: {@code new DebuggingText();} at the begin of its procedure. 
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingDefaults
 * @see it.emarolab.cagg.debugging.StandaloneDebuggingText.ConsoleLogRidirector
 * @see it.emarolab.cagg.debugging.StandaloneDebuggingText.Logger 
 **/
@Deprecated
public class StandaloneDebuggingText {

	// TODO : add time logging flags
	// TODO : add class debugging flag map
	// TODO : add calling method name prining
	// TODO : flush on error bug ????

	private static final int NOT_ATTACHED_ERROR_RATE = 100;
	private static int numberOfNotAttachedError = NOT_ATTACHED_ERROR_RATE, numberOfNotAttachedOut = NOT_ATTACHED_ERROR_RATE;


	// ########################################################################################################################
	// Configurations #########################################################################################################
	// class bheaviour flags
	/**
	 * Set it to {@code true} if the {@code System.out} stream should be listened. {@code false} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_LISTEN_SYSTEM_OUT}
	 */
	public static final Boolean ATTACH_OUT_LOGS = DebuggingDefaults.LOG_LISTEN_SYSTEM_OUT;
	/**
	 * Set it to {@code true} if the {@code System.err} stream should be listened. {@code false} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_LISTEN_SYSTEM_ERR}
	 */
	public static final Boolean ATTACH_ERR_LOGS = DebuggingDefaults.LOG_LISTEN_SYSTEM_ERR;
	/**
	 * Set it to {@code true} if the messages should be printed in the Java console. {@code false} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_ON_CONSOLE}
	 */
	public static final Boolean PRINT_ON_CONSOLE = DebuggingDefaults.LOG_ON_CONSOLE;
	/**
	 * Set it to {@code true} if the messages should be printed in the GUI Panel. 
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel}
	 * {@code false} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_ON_GUI}
	 */
	public static final Boolean PRINT_ON_GUI = DebuggingDefaults.LOG_ON_GUI;
	/**
	 * Set it to {@code false} if the ERROR messages should be discarded. {@code true} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_ERROR}
	 */
	public static final Boolean SHOW_ERROR = DebuggingDefaults.LOG_ERROR;
	/**
	 * Set it to {@code false} if the WARNING messages should be discarded. {@code true} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_WARNING}
	 */
	public static final Boolean SHOW_WARNING = DebuggingDefaults.LOG_WARNING;
	/**
	 * Set it to {@code false} if the INFO messages should be discarded. {@code true} if not. <br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#LOG_INFO}
	 */
	public static final Boolean SHOW_INFO = DebuggingDefaults.LOG_INFO;
	// class text apparence
	/**
	 * The prefix added to an ERROR message.<br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#TAG_ERROR}
	 */
	public static final String TAG_ERROR = DebuggingDefaults.TAG_ERROR;
	/**
	 * The prefix added to an WARNING message.<br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#TAG_WARNING}
	 */
	public static final String TAG_WARNING = DebuggingDefaults.TAG_WARNING;
	/**
	 * The prefix added to an OK message.<br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#TAG_WARNING}
	 */
	public static final String TAG_OK = DebuggingDefaults.TAG_OK;
	/**
	 * The prefix added to an INFO message.<br>
	 * Default value: {@link it.emarolab.cagg.debugging.DebuggingDefaults#TAG_INFO}
	 */
	public static final String TAG_INFO = DebuggingDefaults.TAG_INFO;

	private ConsoleLogRidirector consoleLog;

	// ########################################################################################################################
	// Main constructor #######################################################################################################
	/**
	 * Initialise a new {@link ConsoleLogRidirector} by
	 * using the default values: {@link #ATTACH_OUT_LOGS}, {@link #ATTACH_ERR_LOGS} and {@link #PRINT_ON_CONSOLE} respectively.<br>
	 * Also, this constructor use it to initialise a new {@link Logger}.
	 * Finally it does not print the longs on any file. Set it to null or empty to do not write.
	 */
	public StandaloneDebuggingText(){
		consoleLog = new ConsoleLogRidirector( ATTACH_OUT_LOGS, ATTACH_ERR_LOGS, PRINT_ON_CONSOLE, "");
		new Logger( consoleLog);
	}
	/**
	 * Initialise a new {@link ConsoleLogRidirector} by
	 * using the default values: {@link #ATTACH_OUT_LOGS}, {@link #ATTACH_ERR_LOGS} and {@link #PRINT_ON_CONSOLE} respectively.<br>
	 * Also, this constructor use it to initialise a new {@link Logger}.
	 * Finally it does allow to write all the produced logs in a file by specifying its path.
	 * @param filePath the directory to the path in which write all the logs. Set it to null or empty to do not write.
	 */
	public StandaloneDebuggingText( String filePath){
		consoleLog = new ConsoleLogRidirector( ATTACH_OUT_LOGS, ATTACH_ERR_LOGS, PRINT_ON_CONSOLE, filePath);
		new Logger( consoleLog);
	}
	/**
	 * Initialise a new {@link ConsoleLogRidirector} by
	 * using the input parameters.<br>
	 * Also, this constructor use it to initialise a new {@link Logger}.
	 * Finally, it allows to specifying the path to the file in which the produced logs should be written.
	 * @param attachOut {@code true} if the logger should listener {@code System.out} stream.
	 * @param attachErr {@code true} if the logger should listener {@code System.err} stream.
	 * @param printOnConsole {@code true} if the logger should print in the Java Console  before to be redirected.
	 * @param filePath the directory to the path in which write the activated logs. Set it to null or empty to do not write.
	 */
	public StandaloneDebuggingText( Boolean attachOut, Boolean attachErr, Boolean printOnConsole, String filePath){
		consoleLog = new ConsoleLogRidirector( attachOut, attachErr, printOnConsole, filePath);
		new Logger( consoleLog);
	}


	/* 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
		[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ To attch System.err/out to Logger (this) ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
		[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  */
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.debugging.DebuggingText.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class is used along all the system to have a basing logging object. Particularly, it listen for the standard
	 * streams: {@code System.out} and {@code System.err} and redirect their messages into the {@link StandaloneDebuggingText.Logger} object.<br>
	 * Moreover, the messages can be also printed in the standard java console before to redirect it.<br>
	 * It is important to note that if some exceptions occur in to the system this class may not print the results as long
	 * as its buffer is processed. To obviate to this problem and see the longs in the GUI interact in order to process other logs
	 * (e.g. open a new grammar dialog and cancel it). While, from API it is possible to call:
	 *  {@link Logger#flushOut()},  {@link Logger#flushErr()} or {@link Logger#flushOutErr()} to print out all the messages.
	 * </p>
	 *
	 * @see StandaloneDebuggingText 
	 * @see Logger
	 **/
	// class to rederect the system log to a text to be displayed in the GUI
	public class ConsoleLogRidirector{
		private ByteArrayOutputStream logOut, logErr;
		private PrintStream psOut, psErr;
		private PrintStream oldOut, oldErr;
		private Boolean outAttached = false, errAttached = false;
		private Boolean printOnConsole;
		private String filePath; // file in which print logs


		// ########################################################################################################################
		// Constructor, class initialisation ######################################################################################
		/**
		 * Initialise the redirector with all {@code true} flags which are: {@code attachOut} (listen for {@code System.out} stream), 
		 * {@code attachErr} (listen for {@code System.err} stream) and {@code printOnConsole} (if the message should be printed
		 * on console rather than only redirect them). This constructor does not allow to print the logs on file
		 */
		// @see DebuggingText.ConsoleLogRidirector#ConsoleLogRidirector(Boolean, Boolean, Boolean)
		public ConsoleLogRidirector(){
			initialise( true, true, true, ""); // attach by default
		}
		/**
		 * Initialise the redirector with all {@code true} flags which are: {@code attachOut} (listen for {@code System.out} stream), 
		 * {@code attachErr} (listen for {@code System.err} stream) and {@code printOnConsole} (if the message should be printed
		 * on console rather than only redirect them). This constructor allows to print the logs on file by specifying the file path.
		 * @param filePath the directory to the path in which write all the logs.
		 */
		public ConsoleLogRidirector( String filePath){
			initialise( true, true, true, filePath); // attach by default
		}
		/**
		 * Initialise the redirector with custom flags as input parameters.
		 * Also, this constructor allows to print the logs on file by specifying the file path.
		 * @param attachOut {@code true} if the logger should listener {@code System.out} stream.
		 * @param attachErr {@code true} if the logger should listener {@code System.err} stream.
		 * @param printOnConsole {@code true} if the logger should print in the Java Console before to be redirected.
		 * @param filePath the directory to the path in which write the activated logs. Set it to null or empty to do not write.
		 */
		public ConsoleLogRidirector( Boolean attachOut, Boolean attachErr, Boolean printOnConsole, String filePath){
			initialise( attachOut, attachErr, printOnConsole, filePath);
		}
		private void initialise( Boolean attachOut, Boolean attachErr, Boolean print, String filePath){
			this.filePath = filePath;
			if( attachOut)		// create new stream for System.out
				attachOutLog();
			if( attachErr)		// create new stream for System.err
				attachErrLog();
			printOnConsole = print;
		}

		// ########################################################################################################################
		// method to print and get logs as string #################################################################################
		/** 
		 * Return all the messages streamed in {@code System.out} and, if the flag ({@code printOnConsole}) 
		 * allows it, print them into the Java console.<br>
		 * It returns and empty string (and logs an error) if this redirector is not attached to 
		 * the out stream (specified by the flag {@code attachOut}).
		 * 
		 * @return all the new messages in the {@code System.out}.
		 */
		public synchronized String printOutLog(){		// print only out log  (clean internal buffer variable) 
			if( outAttached){
				detachOutLog();
				String outLog = logOut.toString();
				if( printOnConsole)
					System.out.print( outLog);
				printOnFile( logOut);
				attachOutLog();
				return outLog;
			} else {						// returns empty if the console is not attached !!!!!!!
				if( numberOfNotAttachedOut >= NOT_ATTACHED_ERROR_RATE){
					System.out.println( "Logger is not attached to System.out!"
							+ "(this message is shown every " + NOT_ATTACHED_ERROR_RATE + " times it occurs)");
					numberOfNotAttachedOut = 0;
				}
				numberOfNotAttachedOut++;
				return "";
			}
		}
		/** 
		 * Return all the messages streamed in {@code System.err} and, if the flag ({@code printOnConsole}) 
		 * allows it, print them into the Java console.<br>
		 * It returns and empty string (and logs an error) if this redirector is not attached to 
		 * the out stream (specified by the flag {@code attachErr}).
		 * 
		 * @return all the new messages in the {@code System.err}.
		 */
		public synchronized String printErrLog(){		// print only err log  (clean internal buffer variable)
			if( errAttached){
				detachErrLog();
				String errLog = logErr.toString();
				if( printOnConsole)
					System.err.print( errLog);
				printOnFile( logErr);
				attachErrLog();
				return errLog;
			} else {						// returns empty if the console is not attached !!!!!!!
				if( numberOfNotAttachedError >= NOT_ATTACHED_ERROR_RATE){
					System.err.println( "Logger is not attached to System.err! "
							+ "(this message is shown every " + NOT_ATTACHED_ERROR_RATE + " times it occurs)");
					numberOfNotAttachedError = 0;
				}
				numberOfNotAttachedError++;
				return "";
			}	
		}

		private synchronized void printOnFile( ByteArrayOutputStream toPrint){ // null or empty path for not save
			if( filePath != null)
				if( ! filePath.isEmpty()){
					OutputStream outputStream = null; 
					try {
						outputStream = new FileOutputStream ( filePath, true); // append
						//String time = Logger.getFormattedDateMilli() + " - ";
						//toPrint.write( time.getBytes());
						toPrint.writeTo( outputStream);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						Logger.error( "Cannot find file to save logs. Given path: " + filePath);
					} catch (IOException e) {
						e.printStackTrace();
						Logger.error( "Cannot write logs on file: " + filePath);
					} finally {
						if( outputStream != null)
							try {
								outputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
								Logger.error( "Cannot close the file on path: " + filePath);
							}  
					}  
				}
		}
		
		/**
		 * Set the if this redirector should print the messages on console or not when {@link #printErrLog()}
		 * and {@link #printOutLog()} is called.
		 * @param printOnConsole the flag to be set. {@code true} to print or {@code false} to do not print.
		 */
		public synchronized  void setPrintOnConsole( Boolean printOnConsole){
			this.printOnConsole = printOnConsole;
		}
		/**
		 * get the print on console flag value
		 * @return {@code true} if this redirector prints the message to console. {@code false} otherwise.
		 */
		public synchronized  Boolean getPrintOnConsole(){
			return this.printOnConsole;
		}
		/**
		 * get the {@code attachErr} flag. To modify this value use {@link #attachErrLog()} or {@link #detachErrLog()}.
		 * @return {@code true} if this redirector is attached to {@code Syste.err} stream. {@code false} otherwise.
		 */
		public synchronized  Boolean isErrorAttached(){
			return this.errAttached;
		}
		/**
		 * get the {@code attachOut} flag. To modify this value use {@link #attachOutLog()} or {@link #detachOutLog()}.
		 * @return {@code true} if this redirector is attached to {@code Syste.out} stream. {@code false} otherwise.
		 */
		public synchronized  Boolean isOutAtached(){
			return this.outAttached;
		}

		// ########################################################################################################################
		// Methods to manage System.OUT   #########################################################################################
		/**
		 * Set this redirector to listen the {@code System.out} stream. It has effects only if {@link #isOutAtached()} is false.
		 */
		public synchronized void attachOutLog(){
			if( ! outAttached){
				logOut = new ByteArrayOutputStream(); 	// create out log
				psOut = new PrintStream( logOut);	  	// create out log
				outAttached = true;					  	// update internal state
				oldOut = System.out;				 	// save previous (default) stream 
				System.setOut( psOut);				  	// attach this instance to the console
			} else Logger.error( "Logger.attachOutLog is already attached!");
		}
		/**
		 * Set this redirector to do not listen the {@code System.out} stream. It has always effects.
		 */
		public synchronized  void detachOutLog(){
			System.out.flush();							// flush all the contents
			System.setOut( oldOut);						// set back console for displaying
			outAttached = false;						// update internal state
		}

		// ########################################################################################################################
		// Methods to manage System.ERR   #########################################################################################
		/**
		 * Set this redirector to listen the {@code System.err} stream. It has effects only if {@link #isErrorAttached()} is false.
		 */
		public synchronized  void attachErrLog(){
			if( ! errAttached){
				logErr = new ByteArrayOutputStream();	// create out log
				psErr = new PrintStream( logErr);		// create out log
				errAttached = true;						// update internal state
				oldErr = System.err; 					// save previous (default) stream
				System.setErr( psErr);					// attach this instance to the console
			} else Logger.error( "Logger.attachErrLog is already attached!");
		}
		/**
		 * Set this redirector to do not listen the {@code System.err} stream. It has always effects.
		 */
		public synchronized  void detachErrLog(){
			System.err.flush();							// flush all the contents
			System.setErr( oldErr);						// set back console for displaying
			errAttached = false;						// update internal state
		}		


		/**
		 * @return the directory path to the logging file. It is {@code empty()} if the system is not saving the logs.
		 */
		public synchronized  String getFilePath() {
			return filePath;
		}
		/**
		 * @param filePath the directory path in which save all the logs. Set to {@code empty()} for do not saving any logs.
		 */
		public synchronized   void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		@Override
		protected synchronized void finalize() throws Throwable {
			Logger.flushOutErr();
			super.finalize();
		}
	}

	/* 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
		[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Logging function for all the system  ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
		[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  */
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.debugging.DebuggingText.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class is used along all the system to have a basing logging object. Particularly, it recieve message from the standard
	 * streams: {@code System.out} and {@code System.err} basing on the redirector object given on constructor 
	 * (which is resposible to log those messages also on the Java console or not).<br>
	 * Moreover, it can be used also to directly log data for debugging and showing purposes both in the console or in the GUI.<br>
	 * It is important to note that if some exceptions occur in to the system this class may not print the results as long
	 * as its buffer is processed. To obviate to this problem and see the longs in the GUI interact in order to process other logs
	 * (e.g. open a new grammar dialog and cancel it). While, from API it is possible to call:
	 *  {@link Logger#flushOut()},  {@link Logger#flushErr()} or {@link Logger#flushOutErr()}
	 * </p>
	 *
	 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
	 * @see ConsoleLogRidirector 
	 * @see StandaloneDebuggingText
	 **/
	public static class Logger {

		// ########################################################################################################################
		// Constructors and Fields   ##############################################################################################
		private static ConsoleLogRidirector console;
		/**
		 * Initialise the static Logger for the system.
		 * @param consoleRiderector is the object used to obtain standard output messages
		 */
		public Logger( ConsoleLogRidirector consoleRiderector){
			console = consoleRiderector;
		}

		// ########################################################################################################################
		// Methods to log data (also System.out/err would work around the application)   ##########################################
		private static String getPrefix( String prefix){
			if( prefix != null)
				return "\'" + prefix + "\' ";
			return "";
		}
		private static String getTime4Log(){
			return Logger.getFormattedDate( "dd/MM/yyyy HH:mm:ss.SSS") + " - ";
		}
		/**
		 * calls {@link #info(String)} by using the method {@link Object#toString()}
		 * of the input parameter
		 * @param obj the object to print by calling the method {@code toString()} 
		 */
		public static synchronized void info( String prefix, Object obj){
			if( obj == null)
				Logger.error( "cannot print null message !!!");
			info( prefix, obj.toString());
		}
		public static synchronized void info( Object obj){
			info( null, obj);
		}
		/**
		 * Print a message with the INFO tag ({@link StandaloneDebuggingText#TAG_INFO}) and clear possible redirector message queue
		 * by calling {@link #flushOutErr()} method.<br>
		 * The logs are shown in the {@code System.out} stream if the flag {@link StandaloneDebuggingText#SHOW_INFO} is {@code true}.
		 * @param msg the message to be printed
		 */
		public static synchronized void info( String prefix, String msg){
			if( msg == null)
				Logger.error( "cannot print null message !!!");
			if( SHOW_INFO){
				System.out.println( getTime4Log() + getPrefix(prefix) + TAG_INFO + msg);
				flushOut();
			}
		}
		public static synchronized void info(String msg){
			info( null, msg);
		}
		/**
		 * Print a message with the WARNING tag ({@link StandaloneDebuggingText#TAG_WARNING}) and clear possible redirector message queue
		 * by calling {@link #flushOutErr()} method.<br>
		 * The logs are shown in the {@code System.out} stream if the flag {@link StandaloneDebuggingText#SHOW_WARNING} is {@code true}.
		 * @param msg the message to be printed
		 */
		public static synchronized void warning( String prefix, String msg){
			if( msg == null)
				Logger.error( "cannot print null message !!!");
			if( SHOW_WARNING){
				System.out.println( getTime4Log() + getPrefix(prefix) + TAG_WARNING + msg);
				flushOut();
			}
		}
		public static synchronized void warning( String msg){
			warning( null, msg);
		}
		/**
		 * calls {@link #warning(String)} by using the method {@link Object#toString()}
		 * of the input parameter
		 * @param obj the object to print by calling the method {@code toString()} 
		 */
		public static synchronized void warning( String prefix, Object obj){
			if( obj == null)
				Logger.error( "cannot print null message !!!");
			warning( obj.toString());
		}
		public static synchronized void warning( Object obj){
			warning( null, obj);
		}
		/**
		 * Print a message with the OK tag ({@link StandaloneDebuggingText#TAG_ERROR}) and clear possible redirector message queue
		 * by calling {@link #flushOutErr()} method.<br>
		 * The logs are shown in the {@code System.err} stream if the flag {@link StandaloneDebuggingText#SHOW_ERROR} is {@code true}.
		 * @param msg the message to be printed
		 */
		public static synchronized void ok(String prefix, String msg){
			if( msg == null)
				Logger.error( "cannot print null message !!!");
			if( SHOW_ERROR){
				System.err.println( getTime4Log() + getPrefix(prefix) + TAG_OK + msg);
				flushErr();
			}
		}
		public static synchronized void ok(String msg){
			ok( null, msg);
		}
		/**
		 * calls {@link #error(String)} by using the method {@link Object#toString()}
		 * of the input parameter
		 * @param obj the object to print by calling the method {@code toString()} 
		 */
		public static synchronized void ok( String prefix, Object obj){
			if( obj == null)
				Logger.error( "cannot print null message !!!");
			ok( prefix, obj.toString());
		}
		public static synchronized void ok( Object obj){
			ok( null, obj);
		}
		/**
		 * Print a message with the ERROR tag ({@link StandaloneDebuggingText#TAG_ERROR}) and clear possible redirector message queue
		 * by calling {@link #flushOutErr()} method.<br>
		 * The logs are shown in the {@code System.err} stream if the flag {@link StandaloneDebuggingText#SHOW_ERROR} is {@code true}.
		 * @param msg the message to be printed
		 */
		public static synchronized void error(String prefix, String msg){
			if( msg == null)
				Logger.error( "cannot print null message !!!");
			if( SHOW_ERROR){
				System.err.println( getTime4Log() + getPrefix(prefix) + TAG_ERROR + msg);
				flushErr();
			}
			
			// to see the errors that the logger does not print !!!!
			// comment the above code and decoment the below.
			// also remember to detach from err stream this object (see ATTACH_ERR flag)
			/* System.err.println( TAG_ERROR + msg);*/ 
		}
		public static synchronized void error( String msg){
			error( null, msg);
		}
		/**
		 * calls {@link #error(String)} by using the method {@link Object#toString()}
		 * of the input parameter
		 * @param obj the object to print by calling the method {@code toString()} 
		 */
		public static synchronized void error( String prefix, Object obj){
			if( obj == null)
				Logger.error( "cannot print null message !!!");
			info( obj.toString());
		}
		public static synchronized void error( Object obj){
			error( null, obj);
		}
		/**
		 * Print an exception ({@code e.printStackTrace()}) with the ERROR tag ({@link StandaloneDebuggingText#TAG_ERROR}) and clear possible redirector message queue
		 * by calling {@link #flushOutErr()} method.<br>
		 * The logs are shown in the {@code System.err} stream if the flag {@link StandaloneDebuggingText#SHOW_ERROR} is {@code true}.
		 * @param e the reference to the exception object from which get error informations.
		 */
		public static synchronized void error( String prefix, Exception e){
			if( e == null)
				Logger.error( "cannot print null message !!!");
			if( SHOW_ERROR){
				System.err.print( getTime4Log() + getPrefix(prefix) + TAG_ERROR);
				e.printStackTrace();
				flushErr();
			}
		}
		public static synchronized void error( Exception e){
			error( null, e);
		}

		/**
		 * This method print the current thread stack trace. 
		 * It can be used in order to know the method calling hierarchy in a specific point of the code.
		 * Practically, it uses: {@code Thread.dumpStack();}.   
		 */
		public static synchronized void printStackTrace(){
			Thread.dumpStack();
		}

		// ########################################################################################################################
		// Methods to get system log as String   ##################################################################################
		/**
		 * If {@link StandaloneDebuggingText#ATTACH_OUT_LOGS} is {@code true}, it calls {@link StandaloneDebuggingText.ConsoleLogRidirector#printOutLog()}
		 * in order to eventually print all the message in the console. Moreover, it adds the messages to the GUI if
		 * {@link StandaloneDebuggingText#PRINT_ON_GUI} is {@code true}
		 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
		 * 
		 * @return returns the shown messages as a string or an empty string if {@link StandaloneDebuggingText#ATTACH_OUT_LOGS} is {@code false}
		 */
		public static String flushOut(){		// if the java system is redirected get them as string (and eventually print on console)
			if( ATTACH_OUT_LOGS){
				if( console != null){
					String outLog = console.printOutLog();
					System.out.flush();
					addToGui( outLog);
					return outLog;
				}
			} 
			return "";
		}
		/**
		 * If {@link StandaloneDebuggingText#ATTACH_ERR_LOGS} is {@code true}, it calls {@link StandaloneDebuggingText.ConsoleLogRidirector#printErrLog()}
		 * in order to eventually print all the message in the console. Moreover, it adds the messages to the GUI if
		 * {@link StandaloneDebuggingText#PRINT_ON_GUI} is {@code true}
		 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
		 * 
		 * @return returns the shown messages as a string or an empty string if {@link StandaloneDebuggingText#ATTACH_ERR_LOGS} is {@code false}
		 */
		public static String flushErr(){
			if( ATTACH_ERR_LOGS){
				if( console != null){
					String errLog = console.printErrLog();
					System.err.flush();
					addToGui( errLog);
					return errLog;
				}
			}
			return "";
		}
		/**
		 * If {@link StandaloneDebuggingText#ATTACH_ERR_LOGS} and {@link StandaloneDebuggingText#ATTACH_OUT_LOGS} are {@code true}, 
		 * it calls {@link StandaloneDebuggingText.ConsoleLogRidirector#printErrLog()} and {@link StandaloneDebuggingText.ConsoleLogRidirector#printOutLog()}
		 * in order to eventually print all the message in the console. Moreover, it adds the messages to the GUI if
		 * {@link StandaloneDebuggingText#PRINT_ON_GUI} is {@code true}
		 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
		 * 
		 * @return returns the shown messages as a string or an empty string if {@link StandaloneDebuggingText#ATTACH_ERR_LOGS} or 
		 * {@link StandaloneDebuggingText#ATTACH_OUT_LOGS} are {@code false}
		 */
		public static String flushOutErr(){
			String log = "";
			flushOut();
			flushErr();
			return log;
		}

		/**
		 * @return a string that express the atucal date and time as {@code "dd-MM-yyyy_HH:mm:ss"}
		 */
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

		@Deprecated
		private static void addToGui( String log){
			if( PRINT_ON_GUI)
				LogDebuggingGuiPanel.addLogContents( log);
		}


		// append the txt on the specified file
		public static void appendOnFileLn( String filePath, String txt){ // commplete path to file (with name and estention)
			appendOnFile( filePath, txt + DebuggingDefaults.SYS_LINE_SEPARATOR);
		}
		public static void appendOnFile( String filePath, String txt){ // commplete path to file (with name and estention)
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new BufferedWriter(new FileWriter( filePath, true)));
				writer.print( txt);
			} catch (IOException ex) {
				Logger.error( ex);
			} finally {
				try {
					writer.close();
				} catch (Exception ex) {
					Logger.error( ex);
				}
			}
		}


		/**
		 * @return the directory path to the logging file. It is {@code empty()} if the system is not saving the logs.
		 * Particularly, this indormation is manaed by the internal instance of {@link ConsoleLogRidirector}.
		 */
		public static String getFilePath() {
			return console.getFilePath();
		}
		/**
		 * @param filePath the directory path in which save all the logs. Set to {@code empty()} for do not saving any logs.
		 * Particularly, this indormation is manaed by the internal instance of {@link ConsoleLogRidirector}.
		 */
		public static void setFilePath(String filePath) {
			console.setFilePath( filePath);
		}


		@Override
		protected void finalize() throws Throwable {
			flushOutErr();
			super.finalize();
		}	
	}

	@Override
	protected synchronized void finalize() throws Throwable {
		Logger.flushOutErr();
		super.finalize();
	}

}
