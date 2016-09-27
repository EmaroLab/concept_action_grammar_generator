package it.emarolab.cagg.core.evaluation;

import java.util.ArrayList;
import java.util.List;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.GuiEvaluationInterface;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel;

public class GuiEvaluator extends ThreadedEvaluator implements GuiEvaluationInterface {
	
	/* ##################################################################################
	   ################################# FIELDS ######################################### 
	   field to be given to the GUI in order to implement simulation and visualise results*/
	@Deprecated
	private EvaluatingInfo guiInfo = new EvaluatingInfo(); // it is basically an ArrayList< EvaluatingPrimitive>
	private EvaluatedTree evalTree = new EvaluatedTree(); // it is basicallt an ArrayList< SemanticExpressionNodeBase>
	private Boolean hasBeenKilled = null;
	private Integer previousTreeSize = 0;
	
	/* ##################################################################################
	   ######################### DEFAULT COSTRUCTOR ##################################### 
	 */
	public GuiEvaluator(ThreadedInputFormatter formatter) {
		super(formatter);
	}
	
	/* ##################################################################################
	   ######################### SUPER CLASS CHANGES #################################### 
	 */
	@Override
	protected void resetExpression(){
		// save a copy of the result before to reset
		SemanticExpressionNodeBase evaluatedExpression = (SemanticExpressionNodeBase) getGrammar().getSemanticExpression().getRoot().copyTree();
		synchronized (evalTree) {
			evalTree.add( evaluatedExpression);
		}
		// default reset
		super.resetExpression();
	}

	/* ##################################################################################
	   ########################## ACTION INTERFACE ###################################### 
	 */
	@Override
	public void activateTrigger( EvaluationResults result) {
		logOk( "** trigger activation not defined yet !!!!!");
	}

	/* ##################################################################################
	   ############################ GUI INTERFACE ####################################### 
	 */
	// starter, collect logs and stopper
	@Override
	public void startAndMonitorResult( TestDebugginGuiPanel testPanel, SemanticTreeGuiPanel treePanel) { // from gui button (on) or enter
		// reset to show new evaluation results
		EvaluatingInfoPrimitive.setEvaluationInitalInstant();
		treePanel.clearTrees();
		evalTree.clear();
		guiInfo.clear();
		hasBeenKilled  = false;
		previousTreeSize = 0;
		// start evaluation
		this.evaluate( testPanel.getInputText());
		// wait for results and do action		
		EvaluationResults solution = getNextSolution();
		// log feasible solution header
		EvaluatingInfoPrimitive feasibleHeader = new EvaluatingInfoPrimitive();
		feasibleHeader.addFeasibleHearder();
		guiInfo.add( feasibleHeader);
		testPanel.appendToOuptuText( feasibleHeader.format());
		// monitor all the solutions of the evaluator
		List< Integer> trueSolutionId = new ArrayList<>();
		int bestSolutionIdx = -1, bestTagCnt = 0;
		Long previousTimeStamp = null;
		while( solution != null){
			// do something with feasible solution
			EvaluatingInfoPrimitive infoPrim = new EvaluatingInfoPrimitive( SolutionType.FEASIBLE);
			// log index
			infoPrim.appendInfo( "\t\t(I): " + infoPrim.getUnispaceIndex( "" + solution.getResultIdx()));
			// log outcome
			String exclamation = "";  
			if( solution.getResultOutcome()){
				exclamation = "\t !!!!!! ";
				trueSolutionId.add( solution.getResultIdx());
				if( solution.getResultTags().size() > bestTagCnt){
					bestTagCnt = solution.getResultTags().size();
					bestSolutionIdx = solution.getResultIdx();
				}
			}
			infoPrim.appendInfoLn( "\t  (O): " + solution.getResultOutcome() + exclamation);
			// log considered word
			infoPrim.appendInfoLn( "\t    (W): " + solution.getUsedInput());
			// log skipped word
			infoPrim.appendInfoLn( "\t    (U): " + solution.getUnknownWorlds());
			// log fact
			infoPrim.appendInfoLn( "\t    (F): " + solution.getFacts());
			// log tag
			infoPrim.appendInfoLn( "\t    (@): " + solution.getResultTags());
			infoPrim.appendInfoLn( "");			
			// do something with the log above
			guiInfo.add( infoPrim);
			testPanel.appendToOuptuText( infoPrim.format( previousTimeStamp));
			// show evaluated expression tree
			synchronized (evalTree) {
				int evalSize = evalTree.size();
				if( evalSize > previousTreeSize){
					Integer toShow = evalSize - previousTreeSize;
					for( int i = 0; i < toShow; i++)
							treePanel.addTree( evalTree.get( previousTreeSize + i));
					previousTreeSize = evalSize;
				}
			}
			// get new facts to evaluate next solution
			previousTimeStamp = infoPrim.getCreationTimeStamp();
			solution = getNextSolution();
		}
		
		// print true solution id
		EvaluatingInfoPrimitive separator = new EvaluatingInfoPrimitive();
		separator.addHearder();
		testPanel.appendToOuptuText( separator.format());
		guiInfo.add( separator);
		EvaluatingInfoPrimitive trueSolutionInfo = new EvaluatingInfoPrimitive( SolutionType.TRUE_SOL_IDX);
		trueSolutionInfo.appendInfoLn( "\t\t" + "TRUE SOLUTION INDEXES:");
		trueSolutionInfo.appendInfoLn( "\t" + trueSolutionId);
		trueSolutionInfo.appendInfoLn( "\t" + "best result:" + bestSolutionIdx + ", with " + bestTagCnt + " tags.");
		testPanel.appendToOuptuText( trueSolutionInfo.format());
		guiInfo.add( trueSolutionInfo);
		
		// do something with the unfeasible solution (log fact)
		EvaluatingInfoPrimitive unfeasibleHeader = new EvaluatingInfoPrimitive();
		unfeasibleHeader.addUnfeasibleHearder( getFormatter().getUnfeasibleFactsCount());
		testPanel.appendToOuptuText( unfeasibleHeader.format());
		guiInfo.add( unfeasibleHeader);
		for( Facts<Long> unfeasible : getFormatter().getUnfeasibleFacts()){
			EvaluatingInfoPrimitive unfeasiblePrim = new EvaluatingInfoPrimitive( SolutionType.UNFEASIBLE);
			unfeasiblePrim.appendInfoLn( "\t" + unfeasible.toString());
			testPanel.appendToOuptuText( unfeasiblePrim.format());
			guiInfo.add( unfeasiblePrim);
		}
		
		// log also unreasonable words
		EvaluatingInfoPrimitive unreasonableHeader = new EvaluatingInfoPrimitive();
		unreasonableHeader.addUnerasonableHearder(  getGrammar().getUnreasonableWords().size());
		testPanel.appendToOuptuText( unreasonableHeader.format());
		guiInfo.add( unreasonableHeader);
		EvaluatingInfoPrimitive unreasonablePrim = new EvaluatingInfoPrimitive( SolutionType.UNREASONABLE);
		unreasonablePrim.appendInfoLn( "\t" + getGrammar().getUnreasonableWords().toString());
		testPanel.appendToOuptuText( unreasonablePrim.format());
		guiInfo.add( unreasonablePrim);
		
	}
	@Override
	public void stopEvaluation() { // from GUI button (off)
		hasBeenKilled = true;
		super.stop();
	}
	// get running state
	@Override
	public Boolean hasBeenKilled() {
		return hasBeenKilled;
	}
	@Override
	public Boolean inProgress() {
		return this.isRunning();
	}
	@Override
	public Integer getEvaluatingProgress() {
		return this.getEvaluationCount();
	}
	// get info for gui
	@Override
	public EvaluatingInfo getNewEvaluatingInfo() {
		EvaluatingInfo tmp = guiInfo;
		guiInfo.clear();
		return tmp;
	}
	@Override
	public EvaluatedTree getAllEvaluatedExpressions() {
		EvaluatedTree tmp = evalTree;
		return tmp;
	}

}
