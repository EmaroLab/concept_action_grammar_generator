package it.emarolab.cagg.example;

import java.util.Set;

import it.emarolab.cagg.core.evaluation.ThreadedEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase.TagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.interfaces.CaggCompiler;
import it.emarolab.cagg.interfaces.TestLog;

public class APIusage { // (API)
	
	// check log4j2 configuration files for logging and debugging (see: log/log4j_configuration/..)
	
	// constant for graammar testing
	public static final String USER_SPEECH_INPUT = "Are there 2 cones?";
	public static boolean TEST = true; // set to true to test the grammar with the USER_SPEECH_INPUT
	
	// constants for grammar files
	public static final String SOURCE_BASE_PATH = DebuggingDefaults.PATH_ABSOLUTE_NOTJAR;
	public static final String[] SOURCE_PATS = { 
		SOURCE_BASE_PATH + "englishBase.cagg", 			SOURCE_BASE_PATH + "englishQuestions.cagg",
		SOURCE_BASE_PATH + "ontoEntities.cagg",			SOURCE_BASE_PATH + "ontoProperty.cagg"
	};
	public static final String SERIALISATION_PATH = DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_NOTJAR + "query_scene.ser";
	
	// selector to choose the behavior of the main method
	public static final int COMPILE = 0;
	public static final int DESERIALISE = 1;
	public static int command = COMPILE; // COMPILE or DESERIALISE
	
	
	// called by the main procedure
	private static CaggCompiler compile() {
		// parse the file and create the grammar
		CaggCompiler compiler = new CaggCompiler( SOURCE_PATS);
		// serialise the grammar (you may wat to give a serialisation path to the CaggCompiler constructor to do it automatically !)
		compiler.setSerialisationPath( SERIALISATION_PATH);
		compiler.serialise();
		return compiler;
	}
	
	// called the main procedure after a grammar is compiled from source or loaded from serialisation file
	private static void evaluate(GrammarBase<SemanticTree> grammar) {
		// create the input formatter
		ThreadedInputFormatter formatter = new ThreadedInputFormatter( grammar);
		// create the evaluator (define the action trigger for evaluator)
		EvaluatorBase< ?, ?> evaluator = new MyActionEvaluator( formatter);
		// evaluate
		evaluator.evaluate( USER_SPEECH_INPUT);
	}
	
	// MAIN PROCEDURE
	public static void main(String[] args) {
		try{
			// initialise textual logging
			new CaggLoggersManager();	
			// interpret the command in order to get the grammar from serialisation or sources file
			GrammarBase<SemanticTree> grammar = null;
			switch ( command) {
			case COMPILE:
				CaggCompiler compiler = compile();
				grammar = compiler.getGrammar();
				break;
			case DESERIALISE:
				grammar = CaggCompiler.deserialise( SERIALISATION_PATH);
				break;
			default:
				TestLog.error( "command " + command + " not found !!");
				break; 
			}
			// evaluate the grammar
			if( TEST){
				if( grammar != null)
					evaluate( grammar);
				else TestLog.error( "null grammar cannot be evaluated !");
			} else TestLog.info( "set TEST flag to true to try to evaluate the grammar.");
		} catch( Exception e){
			// flush possible messages stacked on the logger if an not cached exception occurs.
			TestLog.error( e);
		}
	}
}



//define the behavior of the system when solution are found
class MyActionEvaluator extends ThreadedEvaluator{
	///// default constructor
	public MyActionEvaluator(ThreadedInputFormatter formatter) {
		super(formatter);
	}
	///// CAGG evaluation interface
	@Override
	public void activateTrigger( EvaluationResults result) {
		// if the feasible result is true 
		if( result.getResultOutcome()){
			TestLog.ok( "action triggered: " + result.getResultTags());
			// compute a simple response
			String respose = getSystemRespose( result.getResultTags());
			// get back an answer to the user
			TestLog.ok( "bot: \"" + respose + "\"");
		}
	}
	// Just catch if the subject is "you" and tell that it cannot do
	// whenever the user asked by looking for the auxiliary (if any).
	// this is a very basic implementation just for showing purposes
	private String getSystemRespose(ActionTagBase resultTags) {
		String resposeBasic = "No ", resposeSubj = "", resposeAux = "";
		Set<TagBase< ?>> action = resultTags.getTagsCollector();
		for( TagBase<?> a : action){
			// catch the subject
			if( a.size() >= 1){
				if( a.get( 0).toString().equalsIgnoreCase( "SUBJ")){
					if( a.get( 1).toString().equalsIgnoreCase( "YOU")){
						resposeSubj = "I ";
					}
				}
			}
			// catch the auxliary
			if( a.size() >= 1){
				if( a.get( 0).toString().equalsIgnoreCase( "AUX")){
					resposeAux += a.get( 1).toString() + " not yet.";
				}
			}
		}
		// post respose parsing
		if( resposeSubj.isEmpty())
			resposeBasic = "";
		else if( resposeAux.isEmpty())
			resposeSubj = "";
		String respose = resposeBasic + resposeSubj + resposeAux;
		// give the respose
		if( respose.equalsIgnoreCase( resposeBasic))
			return "I did not get it!";
		return respose;
	}
	
}