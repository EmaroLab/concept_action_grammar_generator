package it.emarolab.cagg.interfaces;

import it.emarolab.cagg.core.evaluation.TestingTimeOutedEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.GrammarTesterBase;
import it.emarolab.cagg.core.evaluation.interfacing.TimeOutedEvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.debugging.DebuggingText.Logger;

public class CaggGrammarTester extends GrammarTesterBase{

	/* 	##################################################################################
		####################### DEFAULT CONSTRUCTOR INTERFACE ############################
	 */
	public CaggGrammarTester( GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		super(grammar);
	}
	public CaggGrammarTester( GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
		super(grammar, timeOutSec);	
	}
	public CaggGrammarTester(String grammarPath) {
		super(grammarPath);
	}
	public CaggGrammarTester(String grammarPath, Float timeOutSec) {
		super(grammarPath, timeOutSec);
	}
	public CaggGrammarTester(String evaluatorLoggerName, String formatterLoggerName, 
			String grammarPath) {
		super(evaluatorLoggerName, formatterLoggerName, grammarPath);
	}
	public CaggGrammarTester(String evaluatorLoggerName, String formatterLoggerName, 
			String grammarPath, Float timeOutSec) {
		super(evaluatorLoggerName, formatterLoggerName, grammarPath, timeOutSec);
	}
	public CaggGrammarTester(String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		super(evaluatorLoggerName, formatterLoggerName, grammar);
	}
	public CaggGrammarTester(String evaluatorLoggerName, String formatterLoggerName,
			GrammarBase<? extends SemanticExpressionTreeBase> grammar, Float timeOutSec) {
		super(evaluatorLoggerName, formatterLoggerName, grammar, timeOutSec);
	}	
	
	// called from super constructors
	@Override
	protected ThreadedInputFormatter getNewFormatter( String prefixLog, GrammarBase< ? extends SemanticExpressionTreeBase> grammar) {
		return new ThreadedInputFormatter( prefixLog, grammar);
	}
	@Override
	protected TimeOutedEvaluatorBase getNewEvaluator( String prefixLog, ThreadedInputFormatter formatter, Float timeOutSec) {
		return new TestingTimeOutedEvaluator( prefixLog, formatter, timeOutSec); // it care about logging !!!!
	}

	/* 	##################################################################################
		########################## EVALUATION INTERFACE ##################################
	 */
	@Override
	public synchronized void willEvaluateCommand(String command, int testIdx) {
		Logger.ok( 2, this.getEvaluatorLoggerName(), "*********************** WILL EVALUATE NEW COMMAND: \"" + command + "\" (" + testIdx + ")");
		//Logger.flushErr();
	}
	@Override
	public synchronized void didEvaluateCommand(String command, int testIdx) {
		Logger.ok( 2, this.getEvaluatorLoggerName(), "*********************** DID EVALUATE COMMAND: \"" + command + "\" (" + testIdx + ")");
		//Logger.flushErr();
	}
}

