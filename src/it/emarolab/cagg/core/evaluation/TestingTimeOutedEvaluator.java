package it.emarolab.cagg.core.evaluation;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.interfacing.TimeOutedEvaluatorBase;

public class TestingTimeOutedEvaluator extends TimeOutedEvaluatorBase{ // a easy implementation for logging on Logger.ok stream
	
	/* 	##################################################################################
		############################## CONSTRUCTOR #######################################
	 */
	public TestingTimeOutedEvaluator( ThreadedInputFormatter formatter, Float timeOutSec) {
		super( formatter, timeOutSec);
	}
	public TestingTimeOutedEvaluator( String prefix, ThreadedInputFormatter formatter, Float timeOutSec) {
		super( prefix, formatter, timeOutSec);
	}
	
	/* 	##################################################################################
		########################### TESTING SIMPLE INTERFACE #############################
	 */
	// called only if getTimeOutSecRange() is not null
	public Boolean triggerTimedOut( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result){
		if( result.getTimeSpanFromEvaluationStart() > getTimeOutSecRange()){
			showTestResult( "\t\t Evaluation Time out !!!! " + result.getTimeSpanFromEvaluationStart() + " > " + getTimeOutSecRange() + "[sec]");
			return true;
		}
		return false;
	}
	// called only if the result is true (this method should be overridden for testing purposes)
	// you may also whant to override doWithFaseResult(..) (which has a minimum implementation that does not do nothing!)
	public void doWithTrueResult( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result){
		showTestResult( "\t(" + result.getResultIdx() +") action triggered in:  "
				+ result.getTimeSpanFromEvaluationStart() + "[m] with tags: "+ result.getResultTags());
	}
	
	public void showTestResult( String msg){
		if( isVerbose())
			logOk( 1, msg);
	}
}
