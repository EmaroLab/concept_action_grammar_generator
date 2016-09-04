package it.emarolab.cagg.core.evaluation.interfacing;

import it.emarolab.cagg.core.evaluation.ThreadedEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;

public abstract class TimeOutedEvaluatorBase extends ThreadedEvaluator {
	/* 	##################################################################################
		############################### CONSTANT #########################################
	 */
	public static final Float DEFAULT_EVALUATION_TIME_OUT_SEC = null; // null to do no time it out !!!
	public static final Boolean VERBOSE = true; 


	/* 	##################################################################################
		################################# FIELD ##########################################
	 */
	private Float timeOut;
	private Integer timedOutCount = 0;
	private Boolean verbose;

	/* 	##################################################################################
		############################## CONSTRUCTOR #######################################
	 */
	public TimeOutedEvaluatorBase(ThreadedInputFormatter formatter) {
		super(formatter);
		this.timeOut = DEFAULT_EVALUATION_TIME_OUT_SEC;
		this.verbose = VERBOSE;
	}
	public TimeOutedEvaluatorBase(ThreadedInputFormatter formatter, Float timeOutSec) {
		super(formatter);
		this.timeOut = timeOutSec;
		this.verbose = VERBOSE;
	}
	public TimeOutedEvaluatorBase(ThreadedInputFormatter formatter, Boolean verbose) {
		super(formatter);
		this.timeOut = DEFAULT_EVALUATION_TIME_OUT_SEC;
		this.verbose = verbose;
	}
	public TimeOutedEvaluatorBase(ThreadedInputFormatter formatter, Float timeOutSec, Boolean verbose) {
		super(formatter);
		this.timeOut = timeOutSec;
		this.verbose = verbose;
	}
	
	public TimeOutedEvaluatorBase( String prefix, ThreadedInputFormatter formatter) {
		super( prefix, formatter);
		this.timeOut = DEFAULT_EVALUATION_TIME_OUT_SEC;
		this.verbose = VERBOSE;
	}
	public TimeOutedEvaluatorBase( String prefix, ThreadedInputFormatter formatter, Float timeOutSec) {
		super( prefix, formatter);
		this.timeOut = timeOutSec;
		this.verbose = VERBOSE;
	}
	public TimeOutedEvaluatorBase(  String prefix, ThreadedInputFormatter formatter, Boolean verbose) {
		super( prefix, formatter);
		this.timeOut = DEFAULT_EVALUATION_TIME_OUT_SEC;
		this.verbose = verbose;
	}
	public TimeOutedEvaluatorBase( String prefix, ThreadedInputFormatter formatter, Float timeOutSec, Boolean verbose) {
		super( prefix, formatter);
		this.timeOut = timeOutSec;
		this.verbose = verbose;
	}
	

	/* 	##################################################################################
		########################## EVALUATOR INTERFACE ###################################
	 */
	@Override
	public void activateTrigger( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result) {
		// if the feasible result is true 
		if( result.getResultOutcome())
			doWithTrueResult( result);
		else doWithFalseResutl( result);
		if( getTimeOutSecRange() != null)
			if( triggerTimedOut( result)){
				timedOutCount += 1;
				this.stop();
			}
	} 
	// 	###############  testing evaluator base interface (ABSTRACT)  ####################
	abstract public Boolean triggerTimedOut( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result); // true if time out applied
	abstract public void doWithTrueResult( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result);
	public void doWithFalseResutl( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults result){}; // minimal implementation does not do nothing !!

	/* 	##################################################################################
		############################# GETTERS & SETTERS ##################################
	 */
	public Float getTimeOutSecRange(){
		return timeOut;
	}
	
	public Boolean isVerbose(){
		return verbose;
	}
	public void setVerbose( Boolean verbose){
		this.verbose = verbose;
	}
	
	public Integer getTimedOutCount(){
		return this.timedOutCount;
	}
	public void resetTimedOutCount(){
		this.timedOutCount = 0;
	}
}