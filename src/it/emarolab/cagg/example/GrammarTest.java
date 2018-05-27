package it.emarolab.cagg.example;

import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.core.evaluation.TestingTimeOutedEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.interfacing.GrammarTesterBase;
import it.emarolab.cagg.core.evaluation.interfacing.TimeOutedEvaluatorBase;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.interfaces.CaggGrammarTester;
import it.emarolab.cagg.interfaces.CaggGrammarTesterOnFile;

public class GrammarTest {
	
	///// file path constants (serialisation path)
	public static final String GRAMMAR_PATH = DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_NOTJAR + "query_scene.ser";
	
	////// set of user inputs to be tested sequentially
	public static final List< String> USER_COMMANDS = new ArrayList<>();
	static{
		/*
		USER_COMMANDS.add( "How many planes do you see?");
		USER_COMMANDS.add( "Are there 2 coaxial planes?");
		USER_COMMANDS.add( "Do you see 1 cylinder?");
		USER_COMMANDS.add( "Is X3 in front of every other objects?");
		USER_COMMANDS.add( "Do you see a green object?");
		USER_COMMANDS.add( "How many planes are there in the scene?");
		USER_COMMANDS.add( "Are there any objects aligned?");
		USER_COMMANDS.add( "Is X1 further than X2?");
		USER_COMMANDS.add( "Is X3 on the right of X2?");
		USER_COMMANDS.add( "Is there any green object?");
		USER_COMMANDS.add( "Is there a cylinder in the scene?");
		USER_COMMANDS.add( "Are the spheres far?");
		*/
		// AA
		/*
		USER_COMMANDS.add( "Are there any objects in parallel?");
		USER_COMMANDS.add( "How many object are on xy?");
		USER_COMMANDS.add( "Is X3 the closest object?");
		USER_COMMANDS.add( "Are all the objects along line y?");
		USER_COMMANDS.add( "Is X3 in the center?");
		USER_COMMANDS.add( "Is X2 on the left of X3?");
		USER_COMMANDS.add( "Is X2 close?");
		USER_COMMANDS.add( "How many objects do you see?");
		USER_COMMANDS.add( "What are the colours of the objects you see?");
		USER_COMMANDS.add( "What are the shapes of the objects you see?");
		USER_COMMANDS.add( "Is there a cylinder?");
		USER_COMMANDS.add( "How is it oriented?");
		USER_COMMANDS.add( "How many vertical planes do you see?");
		USER_COMMANDS.add( "Are they aligned?");
		*/
		// BB
		// CC
		/*
		USER_COMMANDS.add( "Which is the most object to the right?");
		USER_COMMANDS.add( "Is the cone at the extreme right?");
		USER_COMMANDS.add( "How many objects are aligned along x?");
		USER_COMMANDS.add( "Is the cone to the left of x4?");
		USER_COMMANDS.add( "Is the yellow sphere the closest object?");
		USER_COMMANDS.add( "Do you see any vertical boxes?");
		USER_COMMANDS.add( "How many vertical cylinders do you see?");
		USER_COMMANDS.add( "Do you see a cup?");
		USER_COMMANDS.add( "Is the cone between the three spheres?");
		USER_COMMANDS.add( "Is the cone at the right of the entire scene?");
		USER_COMMANDS.add( "Is the smaller ball at the right of the scene?");
		USER_COMMANDS.add( "Is the medium ball at the right of the scene?");
		USER_COMMANDS.add( "Is the object right of the biggest black object?");
		USER_COMMANDS.add( "Is the object black?");
		*/
		// DD
		
		USER_COMMANDS.add( "Is your object further than the cone?");
		USER_COMMANDS.add( "is your object the closest?");
		
		USER_COMMANDS.add( "Is there any object aligned with the y axis?"); // EX AA
		USER_COMMANDS.add( "Do the objects in the scene remind you of a cone?"); // EX BB
		USER_COMMANDS.add( "Is there any horizontal plane laying on the xy plane?"); // EX CC
		USER_COMMANDS.add( "Is the object below the biggest black object?"); // EX DD
	}
	
	///// evaluator time out in seconds (null for no time out)
	public static final Float EVALUATION_TIME_OUT = 250.0f; // in seconds
	public static final Boolean USE_DEFAULT_TESTER = true;
	public static final String TEST_LOGGING_NAME = "PhDReport15_GrammarTest"; 

	///// MAIN PROCEDURE
	public static void main(String[] args) {
		// attach debugger (w.r.t. log4j2 configurations)
		new CaggLoggersManager( "log-evaluator", "log-formatter", "log-UI", "log-parsing", "log-grammar", "log-test", 
				DebuggingDefaults.PATH_LOG_CONF_BASE + "log4j_grammarTestConf.xml");
		// test the grammar
		GrammarTesterBase tester;
		if( USE_DEFAULT_TESTER)
			tester = getDefaultTester(); 
		else tester = getCustomTester();
		tester.test(USER_COMMANDS);
	}
	///// TESTING PROCEDURE CALLED FROM MAIN
	private static GrammarTesterBase getDefaultTester(){ // just logs on the Logger.ok stream
		// Perform default testing by logging on files
		CaggGrammarTesterOnFile tester = new CaggGrammarTesterOnFile( GRAMMAR_PATH, EVALUATION_TIME_OUT); 
		tester.setFile( TEST_LOGGING_NAME); // use default value. see CaggGrammarTesterOnFile class for full custumisation
		return tester;
		
		// if you want just to log on the Logger.ok stream you may what to use also the default logger
		/* return new CaggGrammarTester( GRAMMAR_PATH, EVALUATION_TIME_OUT); */
	}
	// it uses the utility class below for customisation
	private static GrammarTesterBase getCustomTester(){
		return new MyGrammarTester( GRAMMAR_PATH, EVALUATION_TIME_OUT);
	}
}	


/* 	##################################################################################
	############################# UTILITY CLASS ######################################
 	to set custom tester logging behavior. 	You may want to rely also on classes into
 	the result2XML package to easy store or load the result to/from file with an
 	human readable formalism. To do so, see CaggXmlManager object documentation and 
 	CaggGrammarTesterOnFile classes for an example. Other examples:
 		xmlManager.printXml();
		xmlManager.toFile( "test.xml");
		CaggXmlManager f = CaggXmlManager.fromFile( "test.xml");
		Logger.error( f);															*/
class MyGrammarTester extends CaggGrammarTester{
	/// just one of the default constructors
	public MyGrammarTester(String grammarPath, Float timeOutSec) {
		super(grammarPath, timeOutSec);
	}
	
	// override getNewFormatter to set your formatter during constructor
	/*@Override
	protected ThreadedInputFormatter getNewFormatter(GrammarBase<SemanticTree> grammar) {
		// this is what super."getNewFormatter( grammar);" would do.
		return new ThreadedInputFormatter( grammar);
	}*/
	
	// override getNewFormatter to set your evaluator during constructor
	@Override
	protected TimeOutedEvaluatorBase getNewEvaluator( String prefixLog, ThreadedInputFormatter formatter, Float timeOutSec) {
		// implement your own evaluator for testing 
		return new MyTestingEvaluator( formatter, timeOutSec);
		
		// or you may want to use the default Testing evaluator that just longs info on the Logger.ok stream
		// note, this is what "super.getNewEvaluator( formatter, timeOutSec);" would do.
		/* return new TestingTimeOutedEvaluator( formatter, timeOutSec); */
	}

	/*@Override
	public void willEvaluateCommand(String command) { // called every time a new input command is going to be evaluated
		super.willEvaluateCommand(command);
	}
	@Override
	public void didEvaluateCommand(String command) { // called every time a new input command has just been evaluated
		super.didEvaluateCommand(command);
	}*/
}
class MyTestingEvaluator extends TestingTimeOutedEvaluator{ 
	/// just one of the default constructors
	public MyTestingEvaluator(ThreadedInputFormatter formatter, Float timeOutSec) {
		super(formatter, timeOutSec);
	}
	
	// override applyTimeOut() to do something before to stop the evaluation
	/*@Override
	public Boolean triggerTimedOut( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result){
		if( super.triggerTimedOut( result)){  // also log on Logger.ok stream
			// ... do something here if needed
			return true;
		}
		return false;
	}*/

	// called every time a new true solution has been found by this evaluator. (the default implementation just logs on Logger.ok stream)
	@Override
	public void doWithTrueResult( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
		// just for showing purposes
		this.logOk( "\t[custom]\t(" + result.getResultIdx() +") action triggered in:  "
				+ result.getTimeSpanFromEvaluationStart() + "[s] with tags: "+ result.getResultTags());
	}
	
	// called every time a new true solution has been found by this evaluator. (the default implementation does no do nothing)
	/*@Override
	public void doWithFalseResult( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
	
	}*/
}


	