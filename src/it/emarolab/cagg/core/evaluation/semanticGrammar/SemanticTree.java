package it.emarolab.cagg.core.evaluation.semanticGrammar;

import it.emarolab.cagg.core.BaseNode; 
import it.emarolab.cagg.core.PseudoBinaryNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase.TagBase;
import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionTermData;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode.Type;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO : reset,set,evaluate,copy
@SuppressWarnings("serial")
public class SemanticTree implements SemanticExpressionTreeBase, Serializable{

	// TODO : the same kind of logging for formatter
	public static final Boolean EVALUTION_VERBOSE_FLAG = false;

	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private SemanticNode root;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ###################################### 
	 */
	public SemanticTree( ExpressionNode<?> syntaxExpressionRoot, ActionTagBase actions){
		initialise( syntaxExpressionRoot, actions);
	}
	public SemanticTree(ExpressionNode<?>.ComposedRule comp) {
		initialise( comp.getRoot(), new SyntaxActionTag( comp.getTagsMap(), comp.getRoot()));
	}
	private void initialise( ExpressionNode<?> syntaxExpressionRoot, ActionTagBase actions){
		// create semantic tree w.r.t. to the given expression tree
		root = new SemanticNode( syntaxExpressionRoot);
		root = root.buildSemanticTree( syntaxExpressionRoot);
		// add the tags to the generated semantic tree
		Map<Long, BaseNode> semanticMap = root.toMap();
		for( Long id : semanticMap.keySet()){
			SemanticNode sn = ( SemanticNode) semanticMap.get(id);
			if( sn.isTerm()){
				Long termId = sn.getId();
				if( actions.contains( termId)){
					TagBase<?> tags = actions.get( termId);
					sn.setTag( tags);
				}
			}
		}
	}

	/* ##################################################################################
	   ############################### GETTERS ########################################## 
	 */
	public SemanticNode getRoot(){
		return root;
	}
	/*@Override
	public List<Long> getTermOccurences( SemanticExpressionNodeBase startingId, String termName) { 
		
		return null ;
	}*/
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " tre root: "+ root;
	}
		
	/* ##################################################################################
	   ####################### BASE EXPRESSION INTERFACE ################################ 
	 */
	@Override
	public void setFacts( Facts< ?> facts, Map< Long, ? extends SemanticExpressionNodeBase> expressionTermMap) { // Facts< Long> !!!!!!!?????????
		// do not calculate again expressionTermMap for performances
		for( SemanticExpressionNodeBase sn : expressionTermMap.values())
			if( facts.contains( sn.getId()))
				sn.setValue( true);
			else sn.setValue( false);
	}
	@Override
	public SemanticResult evaluate() {
		return this.getRoot().evaluate();
	}
	@Override
	public void reset() {
		Map<Long, BaseNode> semanticMap = getRoot().toMap();
		for( Long id : semanticMap.keySet()){
			BaseNode node =  semanticMap.get( id);
			if( node instanceof SemanticNode){
				// reset node value
				SemanticNode semanticNode = ( SemanticNode) node;
				semanticNode.setValue( null);
			} else GrammarLog.error( "A semantic expression tree should contains only node of the type: SemanticNode. Type: " 
					+ node.getClass().getSimpleName() + " found instead for node: " + node);
		}
	}


	/* ##################################################################################
	########################## UTILITY CLASS #########################################
	describes the single node of the main class tree*/
	public class SemanticNode extends SemanticExpressionNodeBase{
		// ####################################################  CONSTANTS
		public static final String TREE_PREFIX_ID = "ST:";
		// ####################################################  FIELD
		private TagBase<?> tag;
		private Boolean hasTags = false, isTerm;
		private Boolean value = null;
		private Type type;
		private ExpressionNode<?>.NodeEvaluator evaluator;
		private BaseData<?> syntaxData;
		private ActionTagBase evaluationActionTag; // do not copied and externally given only through evaluate() method
		// ####################################################  CONSTRUCTOR
		private SemanticNode() { // called only on copy 
			super( ( Integer) null);
		}
		public SemanticNode(ExpressionNode<?> syntaxNode) {
			super( syntaxNode.getMaxChildrenCount(), TREE_PREFIX_ID);
			initialise( syntaxNode);
		}
		public SemanticNode(ExpressionNode<?> syntaxNode, TagBase<?> tags) {
			super( syntaxNode.getMaxChildrenCount(), TREE_PREFIX_ID);
			initialise( syntaxNode);
			setTag( tags);
		}
		private void initialise(ExpressionNode<?> syntaxNode) {
			this.setId( syntaxNode.getId());
			this.setOriginalId( syntaxNode.getOriginalId());
			type = syntaxNode.getType();
			evaluator = syntaxNode.getEvaluator();
			if( syntaxNode instanceof TermExpressionNode)
				this.isTerm = true;
			else this.isTerm = false;
			this.syntaxData = syntaxNode.getData();
		}
		// ####################################################  PSEUDO_BINARY NODE INTERFACE
		@Override
		public SemanticNode copy(Boolean updateCopyId) {
			return (SemanticNode) copyContents( new SemanticNode(), updateCopyId);
		}
		@Override
		public SemanticNode copyContents( BaseNode newNode, Boolean generateCopyId) {
			SemanticNode copiedNode = ( SemanticNode) super.copyContents( newNode, generateCopyId);
			copiedNode.isTerm = this.isTerm();
			if( isTerm())
				copiedNode.setTag( this.tag); // refresh also hasTag flag (suppress warning)
			copiedNode.setValue( this.value);
			copiedNode.type = this.getType();
			copiedNode.evaluator = this.evaluator;
			copiedNode.syntaxData = this.getSyntaxData();
			return copiedNode;
		}
		@Override
		public String toString() {
			// print term or rule declaration name
			String data = "\"";
			if( this.isTerm != null){
				if( this.isTerm){
					ExpressionTermData termData = ( ExpressionTermData)  this.syntaxData;
					data += termData.getInstance() + "\"";
				} else data = "";
			}
			// print node value 
			String value = "(?)";
			if( this.getValue() != null)
				if( this.getValue())
					value = "(T)";
				else value = "(F)";
			// print tags
			String tag = "";
			if( this.isTerm){
				tag = "@";
				if( this.getTags() != null)
					tag += this.getTags().toShortString();
				else tag += "[]";
			} 
			// compose output
			return "[" + this.getDebuggingId() + "] " + this.getType() + " " + value + " " + data + " " + tag; // the data log for the GUI is defined in the extending classes
		}
		// ####################################################  SETTERS
		protected void setTag( TagBase<?> tag) {// give null to remove tag 
			if( ! this.isTerm)
				GrammarLog.warning( "Are you sure to attach a tag to a not term syntax node?");
			this.tag = tag;
			if( tag != null)
				this.hasTags = true;
			else this.hasTags = false;			
		}
		public void setValue(Boolean value) {
			this.value = value;
		}
		// ####################################################  GETTERS
		public Boolean hasTags() {
			return hasTags;
		}
		public Boolean isTerm() {
			return isTerm;
		}
		public Type getType() {
			return type;
		}
		public TagBase<?> getTags() {
			return tag;
		}
		public Boolean getValue() {
			return value;
		}
		public BaseData<?> getSyntaxData(){
			return this.syntaxData;
		}
		// ####################################################  EVALUATION
		protected void evaluate( List< Boolean> childrenValue){ // evaluate only this node
			if( evaluator != null){
				Boolean outcome = this.evaluator.compute( childrenValue);
				//GrammarLog.info( "** evaluating outcome " + outcome + "..\t for  semantic node " + this + "\t with children values: " + childrenValue);
				this.setValue( outcome);
			} else // leave the previous value (it is supposed to be false since reset()) 
				GrammarLog.error( "Cannot evaluate " + this + " whit children:" + childrenValue + ". Since evaluator is null");
		}
		protected ActionTagBase buildNewEvaluationActionTags(){ // it should create a new empty ActionTagBase instance (returned by the evaluate() method)
			return new SyntaxActionTag();
		}
		public SemanticResult evaluate() { // evaluate all tree with this node as a root
			// call evaluation (this as a root)
			SemanticExpressionEvaluator wk = new SemanticExpressionEvaluator( this);
			wk.walkLeftFirst();
			// build results (get the evaluated action of the semantic tree root (this)) 
			ActionTagBase action = this.evaluationActionTag; // (this is reseted "buildNewEvaluationActionTags()" by wk)
			SemanticResult semanticResult = new SemanticResult( this.getValue(), action);
			return semanticResult;
		}
		protected class SemanticExpressionEvaluator extends PseudoBinaryWalker{
			////// default constructor
			public SemanticExpressionEvaluator(PseudoBinaryNode root) {
				super(root);
			}
			////// walker interface
			@Override
			public Boolean enterNode(BaseNode node) { // continue for all node and reset tag collector (stop id error)
				// care about input
				if( ! (node instanceof SemanticNode)){
					GrammarLog.error( "error on cleaning the semantic tree before to evaluate. A semantic tree should contains only SemanticNode."
							+ "Type: " + node.getClass().getSimpleName() + " found instead.");
					return false;
				}
				// reset action
				SemanticNode semanticNode = ( SemanticNode) node;
				semanticNode.evaluationActionTag = buildNewEvaluationActionTags();
				evaluatorlog( "reset evaluation action togs in node: " + semanticNode);
				return true; // continues for all nodes
			}
			@Override
			public void exitNode(BaseNode node) {
				// care about input
				SemanticNode n;
				if( node instanceof SemanticNode)
					n = ( SemanticNode) node;
				else {
					GrammarLog.error( "All semantic expression tree should contains only PseudoBinaryNode components to assure priority and data consistency.");
					return;
				}
				// evaluate procedure
				if( ! n.isLeaf()){
					// get child values with left priority
					SemanticNode leftChild = (SemanticNode) ( ( SemanticNode) n).getLeftChild(); // returns null if does not have it
					SemanticNode rightChild = (SemanticNode) ( ( SemanticNode) n).getRightChild(); // returns null if does not have it
					List< Boolean> childrenValue = new ArrayList<Boolean>();
					if( leftChild != null)
						childrenValue.add( leftChild.getValue());
					if( rightChild != null)
						childrenValue.add( rightChild.getValue());
					// evaluate this node
					n.evaluate( childrenValue); // n.setValue() called internally
					// collect the true action tags up to the end (left first !!!!)
					evaluatorlog( "evaluated node: " + n + "  looking for a semantic action ...");
					String logLeft  = "(L)\t\t" + leftChild;
					String logRight = "(R)\t\t" + rightChild;
					if( n.getValue()){
						// care about left action child tags propagation
						logLeft = updateActionTags(n, leftChild, logLeft);
						evaluatorlog( logLeft);
						// care about right child action tags propagation
						logRight = updateActionTags(n, rightChild, logRight);
						evaluatorlog( logRight);
					}
					evaluatorlog( "(action)\t -> " + n.evaluationActionTag);
					evaluatorlog( "-----------------------------------------------------------");
				}
			}
			private String updateActionTags( SemanticNode parent, SemanticNode child, String msg){ // return log str
				String log = msg;
				if( child != null){
					if( child.getValue()){
						// care about the tags of this node of the left child
						log += "\t hasTag:";
						if( child.hasTags()){
							log += child.getTags(); 
							parent.evaluationActionTag.add( child.getTags());
						} else log += "[]";
						// care about inhered tags during evaluation
						log += "\t hasAction:" + child.evaluationActionTag;
						parent.evaluationActionTag.addAll( child.evaluationActionTag);
					}
				}
				return log;
			}
			/////// logger
			private void evaluatorlog( String msg){
				if( SemanticTree.EVALUTION_VERBOSE_FLAG)
					GrammarLog.info( "** ** " + msg);
			}
		}
		// ####################################################  EXPRESSION TREE INTERFACE
		public SemanticNode buildSemanticTree( ExpressionNode<?> composedTree){
			SemanticTreeBuilder wk = new SemanticTreeBuilder( composedTree);
			wk.walkLeftFirst();
			return wk.getNewRoot();
		}	
		protected class SemanticTreeBuilder extends CopyBinaryWalker{
			//////// fields
			//////// contractor
			protected SemanticTreeBuilder(ExpressionNode<?> root) {
				super( root, false); // do not copy and updateCopyId
			}
			protected SemanticTreeBuilder(ExpressionNode<?> root, ActionTagBase actions) {
				super( root, false); // do not copy and updateCopyId
			}
			//////// builder implementation
			@Override
			public Boolean enterNode(BaseNode node) {
				// new semantic node 
				SemanticNode semantic = new SemanticNode( ( ExpressionNode<?>) node);
				// add this node to the stack (used by exit method)
				getStack().push( semantic);
				return true;
			}
			//////// getters
			@Override
			public SemanticNode getNewRoot(){
				return ( SemanticNode) super.getNewRoot();
			}
		}
	}

}