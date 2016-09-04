package it.emarolab.cagg.interfaces;

import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.core.evaluation.interfacing.TimeOutedEvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.DebuggingText;

public class CaggMultiGrammarTesterOnFile {
	
	/* 	##################################################################################
 		############################### CONSTANTS ########################################
	 */
	public static final String DEFAULT_TEST_NAME = "Cagg Test";
	
	/* 	##################################################################################
	 	################################ FIELDS ##########################################
	 */
	private List< MultiGrammarTesterOnFile> grammarTesters = new ArrayList<>();

	/* 	##################################################################################
		############################# CONSTRUCTOR ########################################
	 */
	public CaggMultiGrammarTesterOnFile( List< CaggMultiGrammarTesterConfiguration> configurations){ 
		initialise( configurations, TimeOutedEvaluatorBase.DEFAULT_EVALUATION_TIME_OUT_SEC);
	}
	public CaggMultiGrammarTesterOnFile( List< CaggMultiGrammarTesterConfiguration> configurations, Float timeOut){ 
		initialise( configurations, timeOut);
	}
	private void initialise( List< CaggMultiGrammarTesterConfiguration> configurations, Float timeOut){
		String date = DebuggingText.getFormattedDate();
		for( CaggMultiGrammarTesterConfiguration c : configurations){
			MultiGrammarTesterOnFile setsUp = new MultiGrammarTesterOnFile( c.getEvaluatorLoggerName(), c.getFormatterLoggerName(), c.getGrammar(), timeOut);
			setsUp.setMultiFile( c.getTestName(), date);
			grammarTesters.add( setsUp);
		}
	}
	
	/* 	##################################################################################
		############################### UTILITY CLASS ####################################
	 */
	public class MultiGrammarTesterOnFile extends CaggGrammarTesterOnFile implements Runnable{// develop and use CaggRunnable to have nice logging
		/* 	##################################################################################
			############################ FIELDS & SETTER #####################################
		 */
		private String testName; 
		private List< String> userInputToTest = null;
		private Integer testingIndex = null;
		public void setUserInputToTest( List<String> userInputToTest) {
			this.userInputToTest = userInputToTest;
		}
		public void setUserInputToTest( String userInputToTest) {
			setUserInputToTest( userInputToTest, 0);
		}
		public void setUserInputToTest( String userInputToTest, int idx) {
			this.userInputToTest = new ArrayList< String>();
			this.userInputToTest.add( userInputToTest);
			this.testingIndex = idx;
		}
		public void resetUserInputToTest(){
			this.userInputToTest = null;
			this.testingIndex = null;
		}
		
		/* 	##################################################################################
			####################### USEFULL DEFAULT CONSTRUCTORS #############################
		 */
		public MultiGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName, 
				String grammarPath) {
			super(evaluatorLoggerName, formatterLoggerName, grammarPath);
			this.testName = DEFAULT_TEST_NAME;
		}
		private MultiGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName, 
				String grammarPath, Float timeOutSec) {
			super(evaluatorLoggerName, formatterLoggerName, grammarPath, timeOutSec);
			this.testName = DEFAULT_TEST_NAME;
		}
		private MultiGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName,
				GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
			super(evaluatorLoggerName, formatterLoggerName, grammar);
			this.testName = DEFAULT_TEST_NAME;
		}
		private MultiGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName,
				GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
			super(evaluatorLoggerName, formatterLoggerName, grammar, timeOutSec);
			this.testName = DEFAULT_TEST_NAME;
		}
		private MultiGrammarTesterOnFile( String testName, String evaluatorLoggerName, String formatterLoggerName, 
				String grammarPath) {
			super(evaluatorLoggerName, formatterLoggerName, grammarPath);
			this.testName = testName;
		}
		private MultiGrammarTesterOnFile( String testName, String evaluatorLoggerName, String formatterLoggerName, 
				String grammarPath, Float timeOutSec) {
			super(evaluatorLoggerName, formatterLoggerName, grammarPath, timeOutSec);
			this.testName = testName;
		}
		private MultiGrammarTesterOnFile( String testName, String evaluatorLoggerName, String formatterLoggerName,
				GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
			super(evaluatorLoggerName, formatterLoggerName, grammar);
			this.testName = testName;
		}
		private MultiGrammarTesterOnFile( String testName, String evaluatorLoggerName, String formatterLoggerName,
				GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
			super(evaluatorLoggerName, formatterLoggerName, grammar, timeOutSec);
			this.testName = testName;
		}
		
		/* 	##################################################################################
			################### MULTI GRAMMAR FILE STORING INTERFACE #########################
		 */
		public void setMultiFile( String attribute, String date) {
			String multiTestBasePath = DEFAULT_BASE_PATH + pathDir( testName + ATTRIBUTE_SEPARATOR + date + DebuggingDefaults.SYS_PATH_DELIM + attribute);
			this.setPathManager( new TestPathManager( multiTestBasePath, NO_TEST_SUBFOLDER_ATTRIBUTE, false, TestFileNames.getDefaultTestFileNames()));
		}
		
		/* 	##################################################################################
			############### MULTI GRAMMAR MULTI THREAD TESTING INTERFACE #####################
		 */
		@Override
		public void run() {
			if( userInputToTest != null){
				if( ! userInputToTest.isEmpty()){
					if( testingIndex == null)
						test( userInputToTest);
					else test( userInputToTest.get( 0), testingIndex);
				} else TestLog.warning( "No user inputs griven to be tested!");
			} else TestLog.error( "Cannot test grammars with null user input. Be sure to call setUserInputToTest(..) first");  
	    }	
		
		@Override
		protected synchronized void updateTestMap( String filePath, String command, int testIdx){
			// update info in hte test map
			Float thisTime = getRangeFromTestingStart();
			DebuggingText.appendOnFileLn( filePath, testIdx + " ,\t " + command + " ,\t " + thisTime);
			// add other info to the Question Map when the experiment is going to end
			if( this.isTestingLastCommand())
				addTestMapFooter( this.getPathManager().getQuestionMapPath());
		}
	}
	
	/* 	##################################################################################
		########################### TESTING INTERFACE ####################################
	 */
	public void testWithAllGrammars( List< String> userInputs){
		int idx = 0;
		for( String in : userInputs)
			testWithAllGrammars( in, idx++);
	}
	public void testWithAllGrammars( String userInput){
		// run a new evaluator for the given grammars
		List< Thread> allTester = new ArrayList<>();
		for( MultiGrammarTesterOnFile tester : grammarTesters){
			tester.setUserInputToTest( userInput);
			Thread testerThread = new Thread( tester);
			allTester.add( testerThread);
			testerThread.start(); // it does not use join()
		}
		waitForGrammarTesters( allTester);
		resetGrammarTesters();
	}
	public void testWithAllGrammars( String userInput, int idx){
		// run a new evaluator for the given grammars
		List< Thread> allTester = new ArrayList<>();
		for( MultiGrammarTesterOnFile tester : grammarTesters){
			tester.setUserInputToTest( userInput, idx);
			Thread testerThread = new Thread( tester);
			allTester.add( testerThread);
			testerThread.start(); // it does not use join()
		}
		waitForGrammarTesters( allTester);
		resetGrammarTesters();
	}
	private void waitForGrammarTesters( List< Thread> allTester){ // to end (join())
		for( Thread tester : allTester){
			try {
				tester.join();
			} catch (InterruptedException e) {
				TestLog.error( e);
			}
		}
	}
	private void resetGrammarTesters(){
		for( MultiGrammarTesterOnFile tester : grammarTesters){
			tester.resetUserInputToTest();
			tester.resetLastCommand();
		}
	}
}
