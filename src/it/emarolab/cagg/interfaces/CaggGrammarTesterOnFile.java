package it.emarolab.cagg.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.core.evaluation.TestingTimeOutedEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.interfacing.TimeOutedEvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.DebuggingText;
import it.emarolab.cagg.debugging.DebuggingText.Logger;
import it.emarolab.cagg.debugging.result2XML.CaggXMLTestDescriptor;
import it.emarolab.cagg.debugging.result2XML.CaggXmlManager;
import it.emarolab.cagg.debugging.result2XML.CaggXmlResultList;

/* 	##################################################################################
	###################### UTILITY CLASS FOR FILE NAME ###############################
 */
class TestFileNames{
	///// constants
	public static final String LOGS_FILE_FORMAT = ".log";
	public static final String TESTMAP_FILE_FORMAT = ".txt";
	public static final String RESULT_FILE_FORMAT = ".xcagg";
	///// fields 
	private String logs, testMap, resulBase;
	public TestFileNames( String logs, String testMap, String resultBase){
		this.logs = logs ;
		this.testMap = testMap ;
		this.resulBase = resultBase;
	}
	///// getters
	public String getLogs() {
		return logs + LOGS_FILE_FORMAT;
	}
	public String getQuestionMap() {
		return testMap + TESTMAP_FILE_FORMAT;
	}
	public String getResul( Integer idx) {
		return idx + resulBase + RESULT_FILE_FORMAT;
	}
	public String getResul( String name) {
		return name + resulBase + RESULT_FILE_FORMAT;
	}
	@Override
	public String toString(){
		return "{" + getLogs() + ", " + getQuestionMap() + ", " + getResul( "i-th") + "}";
	}

	//// statics for defaults
	static TestFileNames getDefaultTestFileNames(){ 
		return new TestFileNames( DEFAULT_LOGS_FILE_NAME, DEFAULT_TESTMAP_FILE_NAME, DEFAULT_RESULT_FILE_NAME);
	}
	///// constants
	public static final String DEFAULT_LOGS_FILE_NAME = "_info";
	public static final String DEFAULT_TESTMAP_FILE_NAME = "_testMap";
	public static final String DEFAULT_RESULT_FILE_NAME = "_evaluation";
}

/* 	##################################################################################
	############################## MAIN CLASS ########################################
 */
public class CaggGrammarTesterOnFile extends CaggGrammarTester{

	/* 	##################################################################################
		############################## CONSTANTS #########################################
	 */
	// for easy writing add return "directive/"
	public static final String DEFAULT_BASE_PATH = pathDir( DebuggingDefaults.SYS_PATH_WORKING) + pathDir( "file") + pathDir( "tests");
	public static final String DEFAULT_ATTRIBUTE = null; // no attributes
	public static final Boolean DEFAULT_USE_DATE = true;

	/* 	##################################################################################
		###################### UTILITY CLASS FOR FILE PATHS ##############################
	 */
	public static final String ATTRIBUTE_SEPARATOR = "_";
	public static final String EMPTY_DIRECTORY_NAME = "xxx" + ATTRIBUTE_SEPARATOR;
	public static final String NO_TEST_SUBFOLDER_ATTRIBUTE = ".."; // do not use attribute subfolder
	public static String pathDir( String directive){ 
		return directive + DebuggingDefaults.SYS_PATH_DELIM; 
	}
	public class TestPathManager{
		//// fields
		private String testPath, primitivePath, testFolder;
		private TestFileNames fileNames;
		//// constructor
		public TestPathManager( String basePath, String attribute, boolean useDate, TestFileNames fileNames){
			// get a valid base path (e.g. "..../cagg/files/test/results/")
			primitivePath = DEFAULT_BASE_PATH;
			if( validatePath( basePath))
				primitivePath = basePath;
			// create the testing path based on the basePath (e.g "..../cagg/files/test/results/test_07-05-16/")
			if( ! attribute.equals( NO_TEST_SUBFOLDER_ATTRIBUTE)){
				testFolder = "";
				String date = DebuggingText.getFormattedDate();
				if( useDate){
					if( validateStr( attribute))
						testFolder = pathDir( attribute + ATTRIBUTE_SEPARATOR + date); // (e.g "test_07-05-16_00-15/")
					else testFolder = pathDir( date); // (e.g "07-05-16_00-15/")
				} else {
					if( validateStr( attribute))
						testFolder = pathDir( attribute); // (e.g "test")
					else testFolder = pathDir( EMPTY_DIRECTORY_NAME + date); // (e.g "xxx_07-05-16_00-15/") (Error no folder name) !!!!!
				}
				this.testPath = primitivePath + testFolder;
			} else this.testPath = primitivePath;
			// store the names
			this.fileNames = fileNames;
		}
		private boolean validateStr( String str){
			if( str != null)
				if( ! str.trim().isEmpty())
					return true;
				else Logger.error( "\"" + str + "\" is not a valid string for testing");
			return false;
		}
		private boolean validatePath( String str){
			if( validateStr( str)){
				if( str.endsWith( DebuggingDefaults.SYS_PATH_DELIM))
					return true;
				else Logger.error( "\"" + str + "\" is not a valid path for testing. Be sure it ends with a path delimiting symbol");
			}
			return false;
		}
		//// getters
		public String getTestPath() {
			return this.testPath;
		}
		public String getLogPath(){
			return this.testPath + fileNames.getLogs();
		}
		public String getQuestionMapPath(){
			return this.testPath + fileNames.getQuestionMap();
		}
		public String getQuestionMapPath( String prefix){
			return this.testPath + prefix + fileNames.getQuestionMap();
		}
		public String getResultPath( String idx){
			return this.testPath + fileNames.getResul(idx);
		}
		public String getResultPath( int idx){
			return this.testPath + fileNames.getResul(idx);
		}
		public String getPrimitivePath(){
			return this.primitivePath;
		}
		public TestFileNames getFileNames(){
			return this.fileNames;
		}
		@Override
		public String toString(){
			return this.testPath + "::" + fileNames;
		}
	}

	/* 	##################################################################################
		############################### FIELDS ###########################################
	 */
	private Long initialTime; // for xml computation time field compilation
	private CaggXMLTestDescriptor xmlDescriptor;
	// paths to file
	private TestPathManager pathManager = null;

	/* 	##################################################################################
		####################### DEFAULT CONSTRUCTOR INTERFACE ############################
	 */
	public CaggGrammarTesterOnFile( String grammarPath) {
		super(grammarPath);
	}
	public CaggGrammarTesterOnFile( String grammarPath, Float timeOutSec) {
		super(grammarPath, timeOutSec);
	}
	public CaggGrammarTesterOnFile( GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		super(grammar);
	}
	public CaggGrammarTesterOnFile( GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
		super(grammar, timeOutSec);	
	}
	public CaggGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName, 
			String grammarPath) {
		super(evaluatorLoggerName, formatterLoggerName, grammarPath);
	}
	public CaggGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName, 
			String grammarPath, Float timeOutSec) {
		super(evaluatorLoggerName, formatterLoggerName, grammarPath, timeOutSec);
	}
	public CaggGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		super(evaluatorLoggerName, formatterLoggerName, grammar);
	}
	public CaggGrammarTesterOnFile( String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
		super(evaluatorLoggerName, formatterLoggerName, grammar, timeOutSec);
	}	

	// called on super constructors
	@Override
	protected ThreadedInputFormatter getNewFormatter( String prefixLog, GrammarBase< ? extends SemanticExpressionTreeBase> grammar) {
		return new ThreadedInputFormatter( prefixLog, grammar);
	}
	@Override
	protected TimeOutedEvaluatorBase getNewEvaluator( String prefixLog, ThreadedInputFormatter formatter, Float timeOutSec) {
		return new EvaluatorTesterOnFile( prefixLog, formatter, timeOutSec); // it care about logging !!!!
	}

	/* 	##################################################################################
		####################### EVALUATION STORING INTERFACE #############################
	 */
	@Override
	public synchronized void willEvaluateCommand(String command, int testIdx) {
		super.willEvaluateCommand(command, testIdx); // just log on Logger.ok stream
		// initialise test logging on file structure
		if( pathManager != null){
			if( testIdx == 0)
				createTestMap( testIdx);
			// move the Logger stream into the folder
			//storeSystemLog( pathManager.getLogPath());
		}else Logger.error( "Cannot log the result with a null pathManager! Be sure to call tester.setFile(..) before to call tester.test()");
		// generate the description of this test (all the fields must be setted manually)
		initialTime = System.nanoTime();
		xmlDescriptor = new CaggXMLTestDescriptor(); 
		xmlDescriptor.setData( null); // the actual data
		xmlDescriptor.setEvaluatorType( getEvaluator().getClass().getName());
		xmlDescriptor.setFormatterType( getEvaluator().getFormatter().getClass().getName());
		xmlDescriptor.setGrammarType( getGrammar().getClass().getName());
		xmlDescriptor.setUserInput( command);
		xmlDescriptor.setGrammarFilePath( getGrammar().getDeserialisationFilePath());
	}
	protected void createTestMap( int testIdx){
		// creating testing folder
		String path = pathManager.getTestPath();
		if( path.endsWith( DebuggingDefaults.SYS_PATH_DELIM))
			path = path.substring( 0, pathManager.getTestPath().lastIndexOf( DebuggingDefaults.SYS_PATH_DELIM));
		File folder = new File( path);
		if ( ! folder.mkdirs())
			TestLog.error( "Cannot create a folder to contains the result of the test in: \"" + pathManager.getTestPath() + "\"");
		else TestLog.info( "## succesfully initialise folder to contain the results of the test on: " + pathManager.getTestPath());
		// initialise the question map file
		createSystemLog( pathManager.getQuestionMapPath());
	}
	protected void createSystemLog( String logPath){
		DebuggingText.appendOnFileLn( logPath, "idx ,\t User Input ,\t Computation Time [sec] " + DebuggingDefaults.SYS_LINE_SEPARATOR);
	}
	/*protected void storeSystemLog( String logPath){
		DebuggingText.setFilePath( logPath);
	}*/

	@Override
	public synchronized void didEvaluateCommand(String command, int testIdx) {
		super.didEvaluateCommand(command, testIdx); // just log on Logger.ok stream

		// complete the description of the test
		xmlDescriptor.setTimeOut( getEvaluator().getTimeOutSecRange());
		xmlDescriptor.setEvaluationTime( getTimeRange( getEvaluator().getEvaluationStatingTimeStamp()));
		xmlDescriptor.setFormattingTime( getTimeRange( getEvaluator().getFormatter().getFormattingStatingTimeStamp()));
		xmlDescriptor.setUnfeasibleSolutions( getEvaluator().getFormatter().getUnfeasibleFactsCount());
		xmlDescriptor.setFalseSolution( getEvaluator().getFormatter().getFactsCount());
		xmlDescriptor.setTrueSolution( getEvaluator().getTrueSolutionCnt());
		xmlDescriptor.setFalseSolution( getEvaluator().getFalseSolutionCnt());
		xmlDescriptor.setMaxActionOrder( getEvaluator().getMaxActionOrder());
		xmlDescriptor.setBestSolutionIdx( getEvaluator().getBestSolutionIdx());
		xmlDescriptor.setUnreasonableWorlds( getGrammar().getUnreasonableWords());
		xmlDescriptor.setComputationTime( getTimeRange( initialTime));

		// create an object to contains all the results
		CaggXmlResultList xmlResults = new CaggXmlResultList();
		xmlResults.initialise( getEvaluator().getAllTrueResults());

		// create new object ready to write the logs on xml format
		CaggXmlManager xmlManager = new CaggXmlManager();
		xmlManager.initialise( xmlDescriptor, xmlResults);

		// store the result
		if( pathManager != null) // if set file has been called
			storeResults( xmlManager, testIdx, command);
		else Logger.error( "Cannot log the result with a null pathManager! Be sure to call tester.setFile(..) before to call tester.test()");
	}
	private Float lastTime = 0.0f;
	protected void storeResults( CaggXmlManager xmlManager, int testIdx, String command){
		// store the new results found so far
		storeResult( getPathManager().getResultPath( testIdx), xmlManager);
		// update the question map
		updateTestMap( getPathManager().getQuestionMapPath(), command, testIdx);
	}
	protected void storeResult( String filePath, CaggXmlManager xmlManager){
		xmlManager.toFile( filePath);
	}
	protected synchronized void updateTestMap( String filePath, String command, int testIdx){
		// update info in hte test map
		Float thisTime = getRangeFromTestingStart();
		DebuggingText.appendOnFileLn( filePath, testIdx + " ,\t " + command + " ,\t " + ( thisTime - lastTime));
		lastTime = thisTime;
		// add other info to the Question Map when the experiment is going to end
		if( this.isTestingLastCommand())
			addTestMapFooter( pathManager.getQuestionMapPath());
	}
	protected void addTestMapFooter( String logPath){
		DebuggingText.appendOnFileLn( logPath, DebuggingDefaults.SYS_LINE_SEPARATOR
				+ "\tTest executed on " + DebuggingText.getFormattedDate() 
				+ " (overall computation and logging time: " + getRangeFromTestingStart() + " sec).");
		DebuggingText.appendOnFileLn( logPath, "\t######################################################################################################\t"
				+ DebuggingDefaults.SYS_LINE_SEPARATOR);
	}

	/* 	##################################################################################
		################################# GETTERS ########################################
	 */
	@Override
	public EvaluatorTesterOnFile getEvaluator(){
		// just to make easy writing
		return (EvaluatorTesterOnFile) super.getEvaluator(); 
	}
	public TestPathManager getPathManager() {
		return pathManager;
	}
	private Float getTimeRange( Long stampNanoSec){ // returns in sec
		return Float.valueOf( System.nanoTime() - stampNanoSec) / DebuggingDefaults.NANOSEC_2_SEC;
	}

	/* 	##################################################################################
		####################### SETTERS TO FILE INTERFACE ################################
	 */
	public void setFile(){
		setFile( DEFAULT_BASE_PATH, DEFAULT_ATTRIBUTE, DEFAULT_USE_DATE, TestFileNames.getDefaultTestFileNames());
	}
	public void setFile( String attribute){
		setFile( DEFAULT_BASE_PATH, attribute, DEFAULT_USE_DATE, TestFileNames.getDefaultTestFileNames());
	}
	public void setFile( String basePath, String attribute){
		setFile( basePath, attribute, DEFAULT_USE_DATE, TestFileNames.getDefaultTestFileNames());
	}
	public void setFile( String attribute, boolean useDate){
		setFile( DEFAULT_BASE_PATH, attribute, useDate, TestFileNames.getDefaultTestFileNames());
	}
	public void setFile( boolean useDate, String basePath){
		setFile( basePath, DEFAULT_ATTRIBUTE, useDate, TestFileNames.getDefaultTestFileNames());
	}
	public void setFile( String basePath, String attribute, boolean useDate){
		setFile( basePath, attribute, useDate, TestFileNames.getDefaultTestFileNames());
	}	
	public void setFile( String basePath, String attribute, boolean useDate, 
			String logsFileName, String testMapFileName, String resultBaseFileName){
		TestFileNames names = new TestFileNames( logsFileName, testMapFileName, resultBaseFileName);
		setFile( basePath, attribute, useDate, names);
	}
	private void setFile( String basePath, String attribute, boolean useDate, TestFileNames fileNames){
		this.pathManager = new TestPathManager(basePath, attribute, useDate, fileNames);
	}
	public void setPathManager(TestPathManager pathManager) {
		this.pathManager = pathManager;
	}

}

class EvaluatorTesterOnFile extends TestingTimeOutedEvaluator{
	// fields (used for logging purposes)
	private List< EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults> allResults = new ArrayList<>();
	private Integer falseSolutionCnt = 0, maxActionOrder = 0, bestSolutionIdx = -1; // the true result count is the size of the array above

	/// just one of the default constructors
	public EvaluatorTesterOnFile( ThreadedInputFormatter formatter, Float timeOutSec) {
		super( formatter, timeOutSec);
	}
	public EvaluatorTesterOnFile( String logPrefix, ThreadedInputFormatter formatter, Float timeOutSec) {
		super( logPrefix, formatter, timeOutSec);
	}

	// called every time a new solution has been found by this evaluator
	@Override
	public void doWithTrueResult( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
		// just log on Logger.ok stream
		super.doWithTrueResult( result);
		// store the new result
		allResults.add( result);
		// save the the maximum size of the actions find so far
		if( result != null)
			if( result.getResultTags() != null)
				if( result.getResultTags().getTagsCollector() != null)
					// it consider the first found (not <=)
					if( maxActionOrder < result.getResultTags().getTagsCollector().size()){ 
						maxActionOrder = result.getResultTags().getTagsCollector().size();
						bestSolutionIdx = result.getResultIdx();
					}
	}
	@Override
	public void doWithFalseResutl( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
		falseSolutionCnt += 1;		
	}

	/// getters
	public List< EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults> getAllTrueResults(){
		return allResults;
	}
	public Integer getTrueSolutionCnt() {
		return getAllTrueResults().size();
	}
	public Integer getFalseSolutionCnt() {
		return falseSolutionCnt;
	}
	public Integer getMaxActionOrder() {
		return maxActionOrder;
	}
	public Integer getBestSolutionIdx() {
		return bestSolutionIdx;
	}
}
