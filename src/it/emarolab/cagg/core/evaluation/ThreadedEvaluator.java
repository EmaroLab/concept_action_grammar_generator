package it.emarolab.cagg.core.evaluation;

import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticResult;
import it.emarolab.cagg.debugging.DebuggingDefaults;

public abstract class ThreadedEvaluator extends EvaluatorBase< ComposedGrammar, ThreadedInputFormatter> {
	/* ##################################################################################
	   ############################# CONSTANTS ##########################################
	 */
	private static final String DEFAULT_THREAD_NAME = "th-eval";
	
	/* ##################################################################################
	   ############################### FIELDS ###########################################
	 */
	private EvaluatorRunner evalThread = null;
	private Integer processedSolutionIdx;
	
	/* ##################################################################################
	   ###################### DEFAULT CONSTRUCTOR #######################################
	 */
	public ThreadedEvaluator(ThreadedInputFormatter formatter) {
		super(formatter);
	}	
	public ThreadedEvaluator(String logPrefix, ThreadedInputFormatter formatter) {
		super(logPrefix, formatter);
	}


	/* ##################################################################################
	   ############################## UTILY CLASS #######################################
	   to implement the procedure to search for facts in a separate thread for
	   performances care.																*/
	public class EvaluatorRunner extends CaggThread{
		///////////////////////////////////////////////// FIELDS
		//private Boolean isRunning = true; // must be true at the beginning. Thread start() on constructor
		private List< EvaluationResults> results = new ArrayList<>();
		private Integer evaluateSolutionIdx = 0;
		
		///////////////////////////////////////////////// CONSTRUCT (starts a new thread)
		public EvaluatorRunner(  String threadName, String loggerName){
			super( threadName, loggerName);
			processedSolutionIdx = 0;
			this.start();
		}
		
		///////////////////////////////////////////////// SEPARATE THREAD IMPLEMENTATION
		/*public void terminate(){
			this.isRunning = false;
		}*/
		@Override
		public void implement() { // evaluate all
			Facts< ?> trueFacts = getFormatter().getNextFacts();
			while( trueFacts != null){
				// evaluate a solution
				evaluateOnce( trueFacts);
				// check if it should stop
				if( ! isRunning()){
					log( "** " + this.getClass().getSimpleName() + " evaluation forced to stop !!!");
					getFormatter().stop();
					break;
				}
				// get new facts to evaluate next solution
				trueFacts = getFormatter().getNextFacts();
				evaluateSolutionIdx += 1;
			}
			// when trueFact is null free the evaluation waiters
			synchronized (results) {
				shouldRun( false);
				results.notifyAll();		
			}
			//log( "** Skipped words given by the user but not specified in the grammar : " + getFormatter().getSkippedWords( evaluateSolutionIdx));
			log( "*********************** Evaluation ends in: " + ( Float.valueOf(System.nanoTime() - getEvaluationStatingTimeStamp()) / DebuggingDefaults.NANOSEC_2_SEC) + "[s]. **********************");
		}
		protected void evaluateOnce( Facts< ?> trueFacts){
			Long initialTimeForThisEvaluation = System.nanoTime(); 
			// set facts
			log("**\t sol:" + evaluateSolutionIdx + " ---------- SET INPUTS: " + trueFacts);
			setExpressionFacts( trueFacts); // change the grammar state
			// evaluate solution
			log("**\t sol:" + evaluateSolutionIdx + " ---------- EVALUATING ...");
			SemanticResult result = evaluateExpression(); // evaluate the grammar
			
			
			// TODO send just the formatter
			EvaluationResults newResult = new EvaluationResults( evaluateSolutionIdx, result, trueFacts, getFormatter(), getEvaluationStatingTimeStamp());
			// activate action triggers
			if( newResult.getResultOutcome())
				log("**\t sol:" + evaluateSolutionIdx + " ---------- NEW TRUE RESULT FOUND !!! \t {" + newResult + "}");
				//activateTrigger( newResult);
			else log("**\t sol:" + evaluateSolutionIdx + " ---------- DISCARD FALSE SOLUTION    \t {" + newResult + "}");
			activateTrigger( newResult); // called always !!!!
			// store solution
			synchronized (results) {
				results.add( newResult);
				results.notifyAll();
			}
			// reset grammar for a new evaluation
			log("**\t sol:" + evaluateSolutionIdx + " ---------- RESET RULES.");
			Long finalTimeForThisEvaluation = System.nanoTime();
			log("**\t ------------------------ time spent on this evaluation: " 
					+ Float.valueOf( finalTimeForThisEvaluation - initialTimeForThisEvaluation) / DebuggingDefaults.NANOSEC_2_SEC
					+ "[s]. Time spent from evaluation beginning: " + 
					+ + Float.valueOf( finalTimeForThisEvaluation - getEvaluationStatingTimeStamp()) / DebuggingDefaults.NANOSEC_2_SEC + "[s].");
			resetExpression(); // reset the grammar state
		}
		///// GETTERS
		/*public Boolean isRunning() {
			return isRunning;
		}*/
		public Integer getEvaluatedSolutionCount() {
			return evaluateSolutionIdx;
		}
		// searcher result getter (synchronized)
		public List<EvaluationResults> getAllResults() {
			synchronized( results){
				return results;
			}
		}
		public EvaluationResults getResult( Integer idx) {// trade save ... returns as soon as it is available
			synchronized( results){
				EvaluationResults out = null;
				if( idx >= 0 && idx < results.size())
					out = results.get( idx);
				else if( isRunning()){
					try {
						results.wait();
						return getResult( idx);
					} catch (InterruptedException e) {
						logError( e);
					}
				}
				return out;
			}
		}
	}
		
	/* ##################################################################################
	   ########################## EVALUATOR BASE INTERFACE ##############################
	 */
	@Override
	public void start() {
		start( DEFAULT_THREAD_NAME);
	}
	public void start( String threadName) {
		if( evalThread == null)
			evalThread = new EvaluatorRunner( threadName, getLoggerName());
		else if( ! evalThread.isRunning())
			evalThread = new EvaluatorRunner( threadName, getLoggerName());
		else logError( "Cannot start the evaluator. Be sure to stop the previous thread frist!");
	}
	@Override
	public void stop() {
		if( evalThread != null)
			if( evalThread.isRunning()){
				this.getFormatter().stop();
				evalThread.terminate();
			} else logWarning( "The evaluator thread is no more alive !");
		else logWarning( "Cannot stop a dead evaluator. Be sure to call start() before.");
	}
	@Override
	public void join() {
		if( evalThread != null)
			if( evalThread.isRunning())
				try {
					evalThread.join();
				} catch ( Exception e) {
					logError( e);
				}
			else logWarning( "The evaluator thread is no more alive !");
		else logError( "Cannot join a dead evaluator. Be sure to call start() before.");
	}
	@Override
	public boolean isRunning() {
		if( evalThread != null)
			return evalThread.isRunning();
		return false;
	}
	@Override
	public EvaluationResults getNextSolution() {
		if( evalThread != null)
			return evalThread.getResult( processedSolutionIdx++);
		return null;
	}
	@Override
	public Integer getEvaluationCount() {
		if( evalThread != null)
			return evalThread.getEvaluatedSolutionCount();
		return null;
	}
	
	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	protected EvaluatorRunner getEvaluatorThread() {
		return evalThread;
	}
	protected void setEvaluatorThread( EvaluatorRunner evalThread) {
		this.evalThread = evalThread;
	}
	protected Integer getProcessedSolutionIdx() {
		return processedSolutionIdx;
	}
}
