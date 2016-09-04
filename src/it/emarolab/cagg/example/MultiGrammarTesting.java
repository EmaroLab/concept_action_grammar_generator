package it.emarolab.cagg.example;

import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.interfaces.CaggMultiGrammarTesterConfiguration;
import it.emarolab.cagg.interfaces.CaggMultiGrammarTesterOnFile;
import it.emarolab.cagg.interfaces.TestLog;

public class MultiGrammarTesting {
	//////set of user inputs to be tested sequentially
	public static final List< String> USER_COMMANDS = new ArrayList<>();
	static{
		USER_COMMANDS.add( "what do you see");
		USER_COMMANDS.add( "is there a higher sphere");
	}
	
	
	///// files path constants (grammar serialisation path)
	public static final List< String> GRAMMAR_PATHS = new ArrayList<>();
	static{
		GRAMMAR_PATHS.add( DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_NOTJAR + "englishOntoProperties.ser");
		GRAMMAR_PATHS.add( DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_NOTJAR + "englishOntoQuestions.ser");
	}
	// to give an alias name to the grammar (it has the same index as GRAMMAR_PATHS)
	public static final List< String> TEST_NAMES = new ArrayList<>();
	static{
		TEST_NAMES.add( "PROPS");
		TEST_NAMES.add( "QUERY");
	}
	// to give an formatter log4j2 logger to the grammar (it has the same index as GRAMMAR_PATHS)
	public static final List< String> EVALUATOR_LOGGING_NAMES = new ArrayList<>();
	static{
		EVALUATOR_LOGGING_NAMES.add( "log-evaluator");
		EVALUATOR_LOGGING_NAMES.add( "log-evaluator");
	}
	// to give an evaluator log4j2 logger to the grammar (it has the same index as GRAMMAR_PATHS)
	public static final List< String> FORMATTER_LOGGING_NAMES = new ArrayList<>();
	static{
		FORMATTER_LOGGING_NAMES.add( "log-formatter");
		FORMATTER_LOGGING_NAMES.add( "log-formatter");
	}
		
	///// evaluator time out in seconds (null for no time out)
	public static final Float EVALUATION_TIME_OUT = 5.0f; 	
	
	public static List< CaggMultiGrammarTesterConfiguration> getConfigurations(){
		List< CaggMultiGrammarTesterConfiguration> conf = new ArrayList<>();
		if( GRAMMAR_PATHS.size() == TEST_NAMES.size()){
			if( TEST_NAMES.size() == EVALUATOR_LOGGING_NAMES.size()){
				if( EVALUATOR_LOGGING_NAMES.size() == FORMATTER_LOGGING_NAMES.size()){
					for( int i = 0; i < GRAMMAR_PATHS.size(); i++){
						conf.add( new CaggMultiGrammarTesterConfiguration(
								TEST_NAMES.get( i), EVALUATOR_LOGGING_NAMES.get( i), FORMATTER_LOGGING_NAMES.get( i), GRAMMAR_PATHS.get( i)));
					}
					return conf;
				}
			}
		}
		TestLog.error( "Multi grammar test configuration not well formatter!");
		return conf;
	}
	
	// MAIN PROCEDURE
	public static void main(String[] args) {
		try{
			// initialise textual logging 
			new CaggLoggersManager( "log-UI", "log-parsing", "log-grammar", "log-test");
			// prepare configuration (see also log4j2 xml file)
			List< CaggMultiGrammarTesterConfiguration> confs = getConfigurations();
			// load grammars and test
			CaggMultiGrammarTesterOnFile tester = new CaggMultiGrammarTesterOnFile( confs, EVALUATION_TIME_OUT);
			tester.testWithAllGrammars( USER_COMMANDS);
		} catch( Exception e){
			// flush possible messages stacked on the logger if an not cached exception occurs.
			TestLog.error( e);
		}
	}
}
