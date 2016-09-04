package it.emarolab.cagg.core.language.syntax.expressionTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.PseudoBinaryNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SyntaxActionTag;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.SyntaxBinaryNode;
import it.emarolab.cagg.core.language.SyntaxNode;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.AndExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OptionalExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OrExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode;

@SuppressWarnings("serial")
public abstract class ExpressionNode<D extends BaseData<?>> extends SyntaxBinaryNode< ExpressionNode.Type, D>{

	/* ##################################################################################
	   ############################ CONSTSANTS ##########################################
	 */
	public static final String TREE_PREFIX_ID = "ET:";

	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private AbstractSyntaxNode<?> parentASN; // the AST node that has this node in its ET

	/* ##################################################################################
	   ########################### CONSTRUCTOR ##########################################
	 */
	public ExpressionNode() {
		super();
		initialise();
	}
	public ExpressionNode(Integer maxNumberOfChildren) {
		super(maxNumberOfChildren);
		if( maxNumberOfChildren != null) // called on copy
			initialise();
	}
	public ExpressionNode(String prefixId) {
		super(prefixId);
		if( prefixId != null)
			initialise();
	}
	public ExpressionNode(Integer maxNumberOfChildren, String prefixId) {
		super(maxNumberOfChildren, prefixId);
		initialise();
	}
	private void initialise(){
		// other internal fields (eventually)
		this.assign();
	}

	/* ##################################################################################
	   ############################## ABSTRACT ##########################################
	   all those methods are implemented by the classes in the package:
	   it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType... 		*/
	// from this interface
	abstract public void doPreProcessing( RuleSyntaxNode ruleNode, List< GrammarPrimitive> preliminaryGrammars); // rule is the same as ( (RuleSyntaxNode) this).getParentAST();
	abstract public void doPostProcessing( RuleSyntaxNode ruleNode, GrammarBase< ? extends SemanticExpressionTreeBase> grammar);
	abstract public NodeEvaluator getEvaluator();
	// from SyntaxBinaryNode interface
	@Override
	abstract protected D assignData(); 	// initialise the data field, called on constructor	
	@Override
	abstract protected Type assignType(); // initialise the type field, called on constructor
	// from BaseNode interface
	@Override
	abstract public ExpressionNode< D> copy( Boolean createNewCopyID);
	
	/* ##################################################################################
	   ######################### GETTER & SETTERS #######################################
	 */
	public void setParentASN( AbstractSyntaxNode<?> rule){ 
		this.parentASN = rule;
	}
	public AbstractSyntaxNode<?> getParentASN(){ 
		return parentASN;
	}
	public Boolean getEvaluatorState(){ // null (not evaluate), true/false otherwise
		return this.getEvaluator().getState();
	}
	
	/* 	##################################################################################
		########################## SUB RULE COMPOSITION ##################################
	 	*/
	// they always copy the tree. set the boolean value to update copyId
	public ComposedRule composeSubRule( GrammarPrimitive primitive){
		List< GrammarPrimitive> allPrimitives = new ArrayList<>();
		allPrimitives.add( primitive);
		return composeSubRule( allPrimitives);
	}
	public ComposedRule composeSubRule( List< GrammarPrimitive> allGrammarPrimitive){ // it copy with new id twice !!!!!!
		// use copyes of the primitives to compose the overall grammar tree
 		RuleComposerVisitor wk = new RuleComposerVisitor( this, allGrammarPrimitive);
 		wk.traverseDepthFirst();
 		// order composed node to have all consequential id
 		ExpressionNode<?> composedRoot = (ExpressionNode<?>) wk.getRoot();
 		Map<Long, List<String>> composedTagMap = wk.getComposedTags();
 		ExpressionNode<?> sequentialComposeTree = (ExpressionNode<?>) composedRoot.copyTreeWithNewID( composedTagMap); // upadtes given map
 		return new ComposedRule( sequentialComposeTree, composedTagMap);
	}
 	/* 	##################################################################################
		############################# UTILITY CLASS ######################################
 	 	to store the result of the sub rule composition.			*/
 	public class ComposedRule{
 		///////// fields
 		private ExpressionNode<?> root;
 		private Map< Long, List< String>> tagsMap;
 		///////// constructor
 		private ComposedRule( RuleComposerVisitor worker) {
			initialise( (ExpressionNode<?>) worker.getRoot(), worker.getComposedTags());
		}
		private ComposedRule( ExpressionNode<?> root, Map<Long, List<String>> tagsMaP) {
			initialise( root, tagsMaP);
		}
		private void initialise( ExpressionNode<?> root, Map<Long, List<String>> tagsMaP) {
			this.root = root;
			this.tagsMap = assureOrdering( tagsMaP);
		}
		private Map< Long, List< String>> assureOrdering( Map< Long, List< String>> inMap){
			String directivePushTerm = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_TERM); // "@~;"
			Map< Long, List< String>> out = new LinkedHashMap<>();
			for( Long id : inMap.keySet()){
				List< String> inTags = inMap.get( id);
				List< String> pushedTags = new ArrayList<>();
				// push @~ directive at the start of the vector
				List< String> tmp = new ArrayList<>();
				for( String t : inTags){
					if( t.equals( directivePushTerm))
						tmp.add( t);
					else 
						pushedTags.add( t);	
				}
				pushedTags.addAll( tmp);
				// add the ordered tag to the new map
				out.put( id, pushedTags);
			}
			return out;
		}
		///////// getter
		public ExpressionNode<?> getRoot() {
			return root;
		}
		public Map<Long, List<String>> getTagsMap() {
			return tagsMap;
		}
		///////// interface
		@Override
		public String toString(){
			return "[composedExpressionTree: " + root + " ; tagMap: " + tagsMap;
		}
 	}
 	/* 	##################################################################################
		############################# UTILITY CLASS ######################################
	 	to compute the sub rules tree and propagate tags on it up to the leafs.			*/
 	class RuleComposerVisitor extends TreeVisitor{
 		///////// fields
 	 	private List< GrammarPrimitive> allPrimitives;
 	 	private Map< Long, List< String>> composedTagsMap = new LinkedHashMap<>();
 	 	private Map< Long, List< String>> originalTagsMap = new LinkedHashMap<>();
 	 	private List< String> preTags = null;
 	 	///////// constructors
 	 	public RuleComposerVisitor(BaseNode root, List< GrammarPrimitive> allPrimitives) {
			super(root);
			initialise( (ExpressionNode<?>) root, allPrimitives);
		}
		private RuleComposerVisitor(BaseNode root, RuleComposerVisitor caller, List< String> preTags) {
 	 		super(root);
 	 		initialise( (ExpressionNode<?>) root, caller.allPrimitives);
 	 		this.preTags = preTags;
 	 	}
 	 	private void initialise( ExpressionNode<?> root, List< GrammarPrimitive> allPrimitives){
 	 		this.allPrimitives = allPrimitives;
 	 		// update map with new id w.r.t ( ( RuleSyntaxNode) root.getParentASN()).getActions()
 	 		Map<Long, List<String>> copyMap = ( ( RuleSyntaxNode) root.getParentASN()).getActions();
 	 		// copy all the components to not have conflicts on recourse calls on the same objects 
 	 		for( Long l : copyMap.keySet()){
 	 			List< String> newTags = new ArrayList<>();
 	 			for( String s : copyMap.get( l)){
 	 				newTags.add( s);
 	 			}
 	 			originalTagsMap.put( l, newTags);
 	 		}
 	 		// copy the tree with new id in order to assure sequential id ordering
 	 		PseudoBinaryNode rootNewCopy = root.copyTreeWithNewID( originalTagsMap);
 	 		this.setRoot( rootNewCopy); 
 	 	}
 	 	////////	traverse implementation
 	 	@Override
		public Boolean work(BaseNode node) {
			if( node instanceof TermExpressionNode){ // tags are supposed to be apllied only on the terms
				// care about the propagation of tags during composition
				TermExpressionNode term = (TermExpressionNode) node;
				//List< String> augmentedTags = getTags( ( ExpressionNode<?>) term);
				String termName = term.getData().getInstance();
				
				// prepere tag list to be pushed to further terms on the tree
				List< String> augmentedTags = getAugmentedTag( term);
				// log the tags to be pushed during composition 
				ParserLog.info( "Sub tree composition rule is running...\t on term " + term + "\t pushing tags: " + augmentedTags);
				
				// compose rule
				if( term.getData().getInstance2()){ // if it is a sub rule declaration
					ExpressionNode<?> definition = searchRuleDefinition( termName);
					RuleComposerVisitor wk = new RuleComposerVisitor( definition, this, augmentedTags);
					wk.traverseDepthFirst();
					// propagate tags during recursive call
					composedTagsMap.putAll( wk.composedTagsMap);
					// add sub tree for rule composition
					ExpressionNode<?> parent = (ExpressionNode<?>) node.getParent();
					if( parent == null){ 
						// is a root do not add, move the node
						this.setRoot( wk.getRoot());
					}else { // add definition with priority
						Boolean wasLeft = term.isLeft();
						parent.remove( node);
						if( wasLeft == null) 
							parent.addChild( (PseudoBinaryNode) wk.getRoot());
						else if( wasLeft) // update the removed term with its definition tree
							parent.addLeftChild( (PseudoBinaryNode) wk.getRoot());
						else parent.addRightChild( (PseudoBinaryNode) wk.getRoot());
					}
				} else // add basic tags
					if( augmentedTags != null)
						composedTagsMap.put( term.getId(), augmentedTags);
			}
			return true; // continue for all  node
		}
 	 	// care about propagating tags to the leafs of the composed tree
 	 	private List< String> getAugmentedTag( TermExpressionNode term){ 
 	 		List< String> augmentedTags = getTags( term);
 	 		if( preTags == null)
				preTags = new ArrayList< String>();
 	 		
 	 		if( augmentedTags != null){
 	 			List< String> tmp = new ArrayList<>();
				for( String p : preTags)
					if( ! augmentedTags.contains( p))
						tmp.add( p);
 	 			augmentedTags.addAll( 0, tmp);
			}else augmentedTags = preTags;
		
			// get the directive to parse the tags on composition
 	 		String directive = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_PUSH_TERM); // "@?~;"
 	 		String directPushTerm = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_PUSH); // "@?;"
 	 		String directivePushTerm = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_TERM); // "@~;"
 	 		String directStopAll = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_STOP_ALL); // "@!!;"
			String directStop = SyntaxActionTag.getStartTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_STOP); // "@!"
			String directStopTerm = SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_STOP_TERM); // "@!~;"
			// manage the shortcut {@?~;} = {@?;@~;}
			for( int i = 0; i < augmentedTags.size(); i++){
				String tag = augmentedTags.get( i);
				if( tag.equals( directive)){
					augmentedTags.remove( i);
					augmentedTags.add( i, directPushTerm);
					if( ! augmentedTags.contains( directivePushTerm)){
						augmentedTags.add( i + 1, directivePushTerm);
						i += 1;
					}
				}
			}
			// parse the tags to be pushed
			for( int i = 0; i < augmentedTags.size(); i++){
				// get tag
				String tag = augmentedTags.get( i);
				// get tag syntax directive
				if( tag.equals( directPushTerm)){ // @?;
					String termName = term.getData().getInstance();
					int j = i;
					if( ! augmentedTags.contains( SyntaxActionTag.getFullTagDirective( termName))){
						augmentedTags.add( i, SyntaxActionTag.getFullTagDirective( termName));
						j += 1;
					}
					augmentedTags.remove( j);
				} else if( tag.equals( directStopAll)){
					augmentedTags.clear();
					i = augmentedTags.size();
				} else if( tag.equals( directStopTerm)){
					augmentedTags.remove( i);
					augmentedTags.remove( SyntaxActionTag.getFullTagDirective( SyntaxActionTag.TAG_DIRECTIVE_SYMB_TERM)); // "@~"
					i = i - 2;
				} else if( tag.startsWith( directStop)){
					// {@!T;} to not push any farter the tag T  
					String toRemove = tag.replace( SyntaxActionTag.TAG_DIRECTIVE_SYMB_STOP, "");
					augmentedTags.remove( toRemove);
					augmentedTags.remove( tag);
					i = i - 2;		
				}
				if( i < 0)
					i = 0;
			}
			return augmentedTags;
 	 	}
 	 	private List<String> getTags( ExpressionNode<?> node){ // returs null if no tags are found
			for( Long id : originalTagsMap.keySet())
				if( id == node.getId())
					return originalTagsMap.get( id);
			return null; 
		}
		private ExpressionNode< ?> searchRuleDefinition( String ruleName){
			for( GrammarPrimitive g : allPrimitives){	// search in grammars primitive
				ExpressionNode< ?> exp = g.getNamedRuleExpressionTree( ruleName);
				if( exp != null)
					return exp;
			}
			ParserLog.error( "Named rule <" + ruleName + "> NOT found in grammars: " + allPrimitives);
			return null;
		}
		//////// getter
		public Map< Long, List< String>> getComposedTags() {
			return composedTagsMap;
		}
 	}

 		
	/* 	##################################################################################
   		####################### EXPRESSION TREE GENERATOR ################################
	 */
	public ExpressionGenerator getExpressionGenerator( List< ExpressionNode<?>> leafs, Boolean useAndOperator){
		return new ExpressionGenerator( leafs, useAndOperator, true); // use left priority
	}
	public ExpressionGenerator getExpressionGenerator( ExpressionNode<?> leaf, Boolean useAndOperator){
		return new ExpressionGenerator( leaf, useAndOperator, true); // use left priority
	}
	public class ExpressionGenerator extends PseudoBinaryTreeGenerator< ExpressionNode<?>>{
		////////////////// DEFAULT CONSTRUCOTRS
		private ExpressionGenerator(List<ExpressionNode<?>> leafs, Boolean useAndOperator, Boolean priorityLeft) {
			super(leafs, useAndOperator, priorityLeft);
		}
		private ExpressionGenerator(List<ExpressionNode<?>> leafs, List<ExpressionNode<?>> operations, int order,	Boolean priorityLeft) {
			super(leafs, operations, order, priorityLeft);
		}
		private ExpressionGenerator(List<ExpressionNode<?>> leafs) {
			super(leafs);
		}
		private ExpressionGenerator(ExpressionNode<?> leafs, Boolean useAndOperator, Boolean priorityLeft) {
			super(leafs, useAndOperator, priorityLeft);
		}
		private ExpressionGenerator(ExpressionNode<?> leafs) {
			super(leafs);
		}
		////////////////// PSEUDO BINARY TREE GENERATOR INTERFACE
		@Override
		protected ExpressionNode<?> getNewAndNode() {
			ExpressionNode<?> node = new AndExpressionNode();
			node.setParentASN( getParentASN());
			return node;
		}
		@Override
		protected ExpressionNode<?> getNewOrNode() {
			ExpressionNode<?> node = new OrExpressionNode();
			node.setParentASN( getParentASN());
			return node;
		}
		@Override
		protected ExpressionNode<?> getNewOptionalNode() {
			ExpressionNode<?> node = new OptionalExpressionNode();
			node.setParentASN( getParentASN());
			return node;
		}
		
	}

	/* 	##################################################################################
		######################### BASE NODE INTERFACE ####################################
	 */
	@Override
	public String toString(){
		String dataStr = "";
		if( this.getData() == null)
			dataStr = "null";
		else dataStr = this.getData().toString();
		return "[" + this.getDebuggingId() + "] " + this.getType() + "(" + dataStr + ")"; // the data log for the GUI is defined in the extending classes
	}
	@Override
	public ExpressionNode<?> copyContents( BaseNode newNode, Boolean generateCopyId) {
		ExpressionNode<?> copiedNode = ( ExpressionNode<?>) super.copyContents(newNode, generateCopyId);
		copiedNode.setParentASN( this.getParentASN().copy( false));
		return copiedNode;
	}
		
	/* ##################################################################################
	   ############################ NODE TYPES ##########################################
	 */
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionNode.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This enumerator defines the possible node types in the Expression Tree (ET).
	 * Particularly, each nodes that implements {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld} 
	 * must return, as a type ({@link SyntaxNode#assignType()}), one different value of this enumeration.<br>
	 * Add value to this enumeration if you want to add a new Expression Tree node type.
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see SyntaxNode
	 */
	public enum Type {
		ROOT,		// used in RootExpressionNode class
		OR, 		// used in OrExpressionNode class
		AND,		// used in AndExpressionNode class
		TERM,		// used in TERMExpressionNode class
		OPTIONAL,	// used in OptionalExpressionNode class
		LABEL,		// used in LabelExpressionNode class
		ID,			// used in IdExpressionNode class
		ACTION,		// used in ActionExpressionNode class
		PRONOUNCE,	// used in PronounceExpressionNode class
		REPEAT, 	// used in RepeatExpressionNode class
		//IGNORE,		// used in IgnoreExpressionNode class
	}

	/* 	##################################################################################
		############################# UTILITY CLASS ######################################
	 	this class is used to delegate to the extending class the definition of the method
	 	used to evaluate all expression nodes in the SemanticTree.
	 	compute( ..) input parameter constraints:
	 		list.size==MAX_CHILDREN_NUMBER with no NULL values								*/
	public abstract class NodeEvaluator implements Serializable{
		// field 
		private Boolean state = null; // not evaluated
		/////// EMPTY CONSTRUCTOR
		protected NodeEvaluator(){}
		/////// ABSTRACT EVALUATION INTERFACE
		public Boolean compute( List< Boolean> childrenValue){
			state = evaluate( childrenValue);
			return state;
		}
		protected abstract Boolean evaluate( List< Boolean> childrenValue);
		/////// GETTER
		public Boolean getState(){
			return state;
		}
	}
	
	/* 	##################################################################################
		############################# UTILITY CLASS ######################################
	 	this class is used to delegate to the extending class the definition of the method
	 	used to evaluate all expression nodes in the SemanticTree.*/
	public class StateNodeEvaluator extends NodeEvaluator{
		/////// FIELDS 
		private Boolean value;
		/////// EMPTY CONSTRUCTOR
		public StateNodeEvaluator(){
			super();
		}
		/////// GETTER & SETTER
		public void setValue( Boolean value){
			this.value = value;
		}
		protected Boolean getValue(){
			return value;
		}
		/////// ABSTRACT EVALUATION INTERFACE
		public Boolean evaluate( List< Boolean> childrenValue){
			return this.getValue();
		}
	}
}
