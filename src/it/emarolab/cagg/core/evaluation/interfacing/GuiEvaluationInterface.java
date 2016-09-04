package it.emarolab.cagg.core.evaluation.interfacing;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel;

import java.util.ArrayList;
import java.util.Collection;

public interface GuiEvaluationInterface {

	/* 	##################################################################################
		############################# MAIN INTERFACE #####################################
	 */
	public void startAndMonitorResult( TestDebugginGuiPanel testPanel, SemanticTreeGuiPanel treePanel);
	public void stopEvaluation();
	public Boolean hasBeenKilled();
	public Boolean inProgress();
	public Integer getEvaluatingProgress();
	public EvaluatingInfo getNewEvaluatingInfo();
	public EvaluatedTree getAllEvaluatedExpressions();

	/* 	##################################################################################
		############################ UTILITY CLASS #######################################
	 	to contain a list of info (primitives) associated with the evaluation step. 
	 	To be used for debugging (perhaps on the GUI).			
	 	As well as to contain the evaluated tree to be shown.		*/
	@SuppressWarnings("serial")
	public class EvaluatedTree extends ArrayList< SemanticExpressionNodeBase>{
		/////// default list constructor
		public EvaluatedTree() {
			super();
		}
		public EvaluatedTree(Collection<? extends SemanticExpressionNodeBase> c) {
			super(c);
		}
		public EvaluatedTree(int initialCapacity) {
			super(initialCapacity);
		}
	}
	@SuppressWarnings("serial")
	public class EvaluatingInfo extends ArrayList< EvaluatingInfoPrimitive>{
		/////// default list constructor
		public EvaluatingInfo() {
			super();
		}
		public EvaluatingInfo(Collection<? extends EvaluatingInfoPrimitive> c) {
			super(c);
		}
		public EvaluatingInfo(int initialCapacity) {
			super(initialCapacity);
		}
		/// easy primitive adding
		public void addInfo( String info){
			super.add( new EvaluatingInfoPrimitive( info));
		}
		public void addInfo( SolutionType type, String info){
			super.add( new EvaluatingInfoPrimitive( type, info));
		}
		public void addHeaderInfo( String info){
			super.add( new EvaluatingInfoPrimitive( SolutionType.HEADER, info));
		}
		public void addFeasibleInfo( String info){
			super.add( new EvaluatingInfoPrimitive( SolutionType.FEASIBLE, info));
		}
		public void addUnfeasibleInfo( String info){
			super.add( new EvaluatingInfoPrimitive( SolutionType.UNFEASIBLE, info));
		}
		public void addSkippedInfo( String info){
			super.add( new EvaluatingInfoPrimitive( SolutionType.SKIPPED, info));
		}
		public void addUnreasonableInfo( String info){
			super.add( new EvaluatingInfoPrimitive( SolutionType.UNREASONABLE, info));
		}
		public void setType( Integer idx, SolutionType type){
			super.get( idx).setSolutionType( type);
		}
		public void setType( SolutionType type){ // takes the last element
			this.getLast().setSolutionType( type);
		}
		public EvaluatingInfoPrimitive getLast(){
			return super.get( super.size() - 1);
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}
	public class EvaluatingInfoPrimitive{
		//// constants
		public static final Integer SECOND_RESOLUTION = 15; // number of char to display
		public static final Integer INDEX_RESOLUTION = 5; // number of char to display
		//// static reference
		private static Long initialTime;
		public static void setEvaluationInitalInstant(){
			initialTime = System.nanoTime();
		}
		//// fields
		private Long creatingTime;
		private String info;
		private SolutionType type;
		//// constructor
		public EvaluatingInfoPrimitive( SolutionType type, String info){
			this.creatingTime = System.nanoTime();
			this.type = type;
			this.info = info;
		}
		public EvaluatingInfoPrimitive( SolutionType type){
			this.creatingTime = System.nanoTime();
			this.type = type;
			this.info = "";
		}
		public EvaluatingInfoPrimitive( String info){
			this.creatingTime = System.nanoTime();
			this.info = info;
		}
		public EvaluatingInfoPrimitive(){
			this.creatingTime = System.nanoTime();
			this.info = "";
		}
		///// manipualtor
		public void appendInfo( Object appendix){
			this.info += appendix.toString();
		}
		public void appendInfoLn( String appendix){
			this.info += appendix + DebuggingDefaults.SYS_LINE_SEPARATOR;
		}
		//// setter
		public void setSolutionType( SolutionType type){
			this.type = type;
		}
		//// time manager
		public Long getCreationTimeStamp(){
			return this.creatingTime;
		}
		public Float getCreationTimeInterval( Long instant){ // sec
			if( instant < this.creatingTime) // instant is before creating time
				return Float.valueOf( this.creatingTime - instant) / DebuggingDefaults.NANOSEC_2_SEC; 
			else // instant is later creating time
				return Float.valueOf( instant - this.creatingTime) / DebuggingDefaults.NANOSEC_2_SEC;
		}
		public Float getCreationTimeInterval(){ // sec
			return getCreationTimeInterval( initialTime);
		}
		///// time formatting
		public String getFormattedDate(){
			return "(" + this.getCreationTimeInterval() + " sec)";
		}
		private String getUnispaceZeroSeconds(){
			return getUnispace( "-.-------", SECOND_RESOLUTION);
		}
		private String getUnispaceSeconds( String seconds){
			seconds = seconds.trim();
			return getUnispace( seconds, SECOND_RESOLUTION);
		}
		public String getUnispaceIndex( String index){
			index = index.trim();
			return getUnispace( index, INDEX_RESOLUTION);
		}
		private String getUnispace( String msg, int numberOfChar){
			if( msg.length() > numberOfChar)
				return msg.substring( 0, numberOfChar);
			else { 
				int range = numberOfChar - msg.length();
				for( int i = 0; i < range; i++)
					msg += " ";
				return msg;
			}
		}
		// format data for logging w.r.t. type
		public String format(){
			return format( null);
		}
		public String format( Long previousStamp){
			switch( getType()){
			case TRUE_SOL_IDX:	return getRawInfo();
			case HEADER: 		return getRawInfo();
			case FEASIBLE: 		return parseFeasible( previousStamp);
			case UNFEASIBLE:	return getRawInfo();
			case UNREASONABLE:	return getRawInfo();
			default: return "unknown case for " + getRawInfo();
			}
		}
		private String parseFeasible( Long previousStamp) {
			String out = "\t(S): " + getUnispaceSeconds( getCreationTimeInterval().toString());
			if( previousStamp != null)
				out += "\t(P): " + getUnispaceSeconds( getCreationTimeInterval( previousStamp).toString());
			else out += "\t(P): " + getUnispaceZeroSeconds();
			out += getRawInfo();
			return out;
		}
		public void addHearder() {
			// create type or do not nothing if not HEARDER
			if( type == null)
				type = SolutionType.HEADER;
			else if( ! type.equals( SolutionType.HEADER)){
				UILog.error( "Cannot add Hearder for feasible result for a not HERADER info collector.");
				return;
			}
			// add hearder
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( "");
		}
		// easy information about log adding
		public void addFeasibleHearder(){
			// create type or do not nothing if not HEARDER
			if( type == null)
				type = SolutionType.HEADER;
			else if( ! type.equals( SolutionType.HEADER)){
				UILog.error( "Cannot add Hearder for feasible result for a not HERADER info collector.");
				return;
			}
			// add hearder
			appendInfoLn( "");
			appendInfoLn( "");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( "                                      FEASIBLE SOLUTIONS                                            ");
			appendInfoLn( "        (S): sec from start.   (P): sec from preious step.   (I): soluton index.  (O):outcome       ");
			appendInfoLn( "            (W): considered tokens.                                                                 ");
			appendInfoLn( "            (U): unknown tokens. [x..y(z)] skipped in (I) x..y. Given at position z in (W)          ");
			appendInfoLn( "            (F): semantic facts.                                                                    ");
			appendInfoLn( "            (@): recognised action tags.                                                            ");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( "");
			appendInfoLn( "");
		}
		public void addUnfeasibleHearder( int size) {
			// create type or do not nothing if not HEARDER
			if( type == null)
				type = SolutionType.HEADER;
			else if( ! type.equals( SolutionType.HEADER)){
				UILog.error( "Cannot add Hearder for feasible result for a not HERADER info collector.");
				return;
			}
			// add hearder
			appendInfoLn( "");
			appendInfoLn( "");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( "                           UNFEASIBLE SEMANTIC FACTS  (size: " + size + ")                          ");
			appendInfoLn( "        set of node ID's not collected by the input formatter if a list does not appear below       ");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( "");
			appendInfoLn( "");
		}
		public void addUnerasonableHearder(int size) {
			// create type or do not nothing if not HEARDER
			if( type == null)
				type = SolutionType.HEADER;
			else if( ! type.equals( SolutionType.HEADER)){
				UILog.error( "Cannot add Hearder for feasible result for a not HERADER info collector.");
				return;
			}
			// add hearder
			appendInfoLn( "");
			appendInfoLn( "");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( "                              UNREASONABLE WORLDS  (size: " + size + ")                             ");
			appendInfoLn( " ##                                                                                               ##");
			appendInfoLn( " ###################################################################################################");
			appendInfoLn( "");
			appendInfoLn( "");
		}
		///// getters
		@Override
		public String toString() {
			return getFormattedDate() + "\t" + this.getType() + ": \t" + this.getRawInfo();
		}
		public String getRawInfo() {
			return info;
		}
		public SolutionType getType() {
			return type;
		}
		

	}

	/* 	##################################################################################
	 	############################## ENUM TYPE #########################################
 		for all the types of info.	*/
	public enum SolutionType{
		HEADER,
		FEASIBLE,
		UNFEASIBLE,
		SKIPPED,
		UNREASONABLE,
		TRUE_SOL_IDX;
	}

}
