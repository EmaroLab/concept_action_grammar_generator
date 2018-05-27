package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.PseudoBinaryNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionRepeatData;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.RepeatExpressionNode.RepeatToLogicalExpression.RepeatitionRange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class RepeatExpressionNode extends ExpressionNode< ExpressionRepeatData>{
	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final Integer MAX_CHILDREN_NUMBER = 1;

	/* ##################################################################################
	   ############################ FIELD ##########################################
	   namely its sub class defined in this file is not serialised !!
	   object used to create the Boolean representation of the repeat directive */
	transient private RepeatToLogicalExpression translator;
	private RepeatEvaluator evaluator;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public RepeatExpressionNode( ParserRuleContext ctx){		// constructor. ex: !action("do something")
		super( MAX_CHILDREN_NUMBER);
		if( ctx != null) // called on copy 
			parse( ctx);
	}
	public void parse( ParserRuleContext ctx){
		List< String> values = TextualParser.retrieveDirectiveContents(ctx, "repeat");
		if( values.size() == 4){ // [!repeat, expression, min, max]
			try{
				Integer minLoopCounter = Integer.valueOf( values.get( 2));
				Integer maxLoopCounter = Integer.valueOf( values.get( 3));
				if( maxLoopCounter >= minLoopCounter)
					this.getData().setInstances( minLoopCounter, maxLoopCounter);
				else ParserLog.error( "Cannot create a repeat node with \"minLoopCnt > maxLoopCnt\". Error givene by: " + this);
			} catch( Exception e){
				ParserLog.error( e);
			}
		} else ParserLog.error( "The repeat declaration does not have two number of repetitions: " + ctx.getText());
	}
	@Override
	public RepeatExpressionNode copy( Boolean updateCopyId) {
		return (RepeatExpressionNode) this.copyContents( new RepeatExpressionNode( null), updateCopyId);
	}
	@Override
	public ExpressionNode<?> copyContents( BaseNode newNode, Boolean generateCopyId) {
		ExpressionNode<?> copiedNode = ( ExpressionNode<?>) super.copyContents(newNode, generateCopyId);
		( ( RepeatExpressionNode) copiedNode).evaluator =  this.evaluator;
		return copiedNode;
	}
	
	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */	
	@Override
	protected ExpressionRepeatData assignData() {
		return new ExpressionDataFactory().getNewExpRepeat();	// create a new node data of empty type
	}
	@Override
	protected Type assignType() {
		return Type.REPEAT;
	}

	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	   preprocessing called from the GrammarCreator when the sources files are already 
	   parsed but the grammar is not created.
	   It is used to replace repeat nodes in the ET with their Boolean equivalent 
	   representation.																		*/
	// actually the preliminaryGrammars are not used in this implementation but they can be useful in case information about parsed source files is needed
	@Override 
	public void doPreProcessing(RuleSyntaxNode ruleNode, List<GrammarPrimitive> preliminaryGrammars) {
		// initialise the object in charge to perform the convention between a repeat directive and a Boolean expression
		translator = new RepeatToLogicalExpression( this); 
		// remove this node from the tree and add the translated expression
		ExpressionNode<?> parent = (ExpressionNode<?>) this.getParent();
		if( parent != null){ // if it is not already the root node
			this.removeFromParent();	// (must respect assignMaxNumberOfChildren(). Remove first)
			parent.addChild( translator.getLogicalExpression());  // add the translated expression
		} else // since this is a root we must to change completely the expression tree
			ruleNode.setTree( translator.getLogicalExpression());
		evaluator = new RepeatEvaluator( translator);
	}

	@Override
	public void doPostProcessing(RuleSyntaxNode ruleNode, GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		SemanticExpressionTreeBase postProcessed = grammar.getSemanticExpression();
		new IdPostProcessor( postProcessed.getRoot()).walkLeftFirst();
	}
	private class IdPostProcessor extends PseudoBinaryWalker{
		////// default constructor
		public IdPostProcessor( SemanticExpressionNodeBase root) {
			super(root);
		}
		///// walker
		@Override
		public Boolean enterNode(BaseNode node) {
			return true; // continue up to the end
		}
		@Override
		public void exitNode(BaseNode node) {
			for( RepeatitionRange range : translator.getRepetitionRanges())
				range.updateDefinititon( ( SemanticExpressionNodeBase) node);
		}	
	}
	
	@Override
	public ExpressionNode<ExpressionRepeatData>.NodeEvaluator getEvaluator() {
		return evaluator; 
	}
	//my custom evaluation class
	public class RepeatEvaluator extends NodeEvaluator{
		// field
		private List< RepeatitionRange> repetitionRanges;

		private RepeatEvaluator( RepeatToLogicalExpression translator){
			this.repetitionRanges = translator.getRepetitionRanges();
		}

		// this method uses the repetition range to analyse if the actual evaluation solution is feasible.
		// it basically checks that a solution is valid iff all the true statements are set on the same repetition boolean representation.
		public Boolean isFeasibleSolution(){
			// search for the frist range in which at least one node has true value and store it
			// Moreover, check that the other ranges does not have any true value. if true the solution is feasible
			RepeatitionRange belongingRange = null;
			// it may be not efficient since do "for" two times
			for( RepeatitionRange range : repetitionRanges){
				if( range.hasTrueOnRange()){
					belongingRange = range;
					break;
				}
			}
			if( belongingRange != null){
				for( RepeatitionRange range : repetitionRanges){
					if( ! range.equals( belongingRange)){
						if( ! range.hasFalseOnRange()){
							return false;
						}
					}
				}
			} else ParserLog.warning( "Repetition solution does not belong to any ranges.");
			return true;
		}

		@Override
		public Boolean evaluate(List<Boolean> childrenValue) {
			return childrenValue.get( 0) & isFeasibleSolution();
		}
	}

	/* 	##################################################################################
	########################## UTILITY CLASS #########################################
 	this auxiliary object is called on doPreprocessing() method for create a equivalent 
	Boolean representation (using ET) of the repeat directive.
	Moreover, it collects for every repetition translated in the boolean domain the set 
	of leafs added to the ET and the counter representing their specific repetition 
	(see Repeat Term Range)	this is used during evaluation in order to accept true term 
	node only inside of an iteration range	and consider as infeasible the solutions 
	that consider terms on different iterations representation.							*/
	/////////////////////////////////////////////////// RepeatToLogicalExpression CONSTANTS
	public static final Boolean AND_OPERATOR = true;
	public static final Boolean OR_OPERATOR = false;	
	class RepeatToLogicalExpression implements java.io.Serializable{
		/////////////////////////////////////////////////// FIELDS
		// the variable contained the traslation of the repeat directive into a boolean ExpressionTree
		private ExpressionNode<?> toLogicalExpression; 
		// collect all the terms added to translate a specific repetition (used on evaluation).
		private List< RepeatitionRange> repetitionRanges = new ArrayList< RepeatitionRange>(); 
		// used to get the parent node on the AST and its tag map
		private RepeatExpressionNode callingNode;

		/////////////////////////////////////////////////// CONSTRUCTOR
		// everything is done here!!!
		public RepeatToLogicalExpression( RepeatExpressionNode repeatNode){
			callingNode = repeatNode;
			Integer minLoopCnt = repeatNode.getData().getMinLoopCount();
			Integer maxLoopCnt = repeatNode.getData().getMaxLoopCount();
			// compute the equivalent representation of the repeat directive in a Boolean Expression Tree
			toLogicalExpression = (ExpressionNode<?>) getTranslatedExpression(minLoopCnt, maxLoopCnt, repeatNode);
			if( minLoopCnt != maxLoopCnt) 
				// set this repeatNode as the root of the logical expression (in order to manage the evaluate() function)
				toLogicalExpression = addRepatNode( toLogicalExpression, repeatNode);
		}

		// convert a repeat expression into an equivalent translation composed by a Boolean Expression Tree.
		private ExpressionNode<?> getTranslatedExpression( Integer min, Integer max, RepeatExpressionNode repeatNode){
			// create all the binary trees to represents that the expression is repeated K times
			// ex: E(0) = !optional(E) ; E(1)=E ; E(2)=E&E ; E(3)=E&E&E ; E(4)=E&E&E&E ; .... E(i) with i in [min, max]
			ExpressionNode<?> originalExpression = (ExpressionNode<?>) repeatNode.getChild( 0); // (see assignMaxNumberOfChildren())
			List< ExpressionNode<?>> repetitionExpressions = new ArrayList< ExpressionNode<?>>();
			for( int i = min; i <= max; i++){
				ExpressionNode<?> repetition;
				if( i == 0) // create the !optional repetition by calling a dedicated method and add it to the collection.
					repetition = repeatNode.getExpressionGenerator( getCopiedTree( originalExpression), AND_OPERATOR).buildTree();
				else { // create all the other repetitions
					List< ExpressionNode<?>> leafNodes = new ArrayList< ExpressionNode<?>>();
					// create the leaf node to be added when the binary tree is composed (in the above example for i=4: [E,E,E,E])
					for( int j = 0; j < i; j++)
						leafNodes.add( getCopiedTree( originalExpression));
					// create the binary tree and add to the collection
					repetition = repeatNode.getExpressionGenerator( leafNodes, AND_OPERATOR).buildTree();
				}
				// store the repetition node ranges to be used on evaluator
				repetitionRanges.add( new RepeatitionRange( repetition, i));
				// store the trees that represents the repetition to be added later
				repetitionExpressions.add( repetition);
				//repetition.showTree( "repetition tree for order: " + i); 
			}

			// create the binary tree (that include the previous one) to represent different repetition of the expression
			// ex: repeat(0-0)=E(0) ; repeat(0-1)=E(0)|E(1) ; ... ; repeat(n-m)=E(n)|E(n+1)|...|E(m)
			ExpressionNode<?> logicalExpression = repeatNode.getExpressionGenerator( repetitionExpressions, OR_OPERATOR).buildTree();
			return logicalExpression; 
		}
		// easy way to get a copy of a sub tree with the same copyId and different Id.
		// Moreover it also compose the rule by considering sub-rules in the grammar in order 
		// to be sure to duplicate all final terms fro correct evaluation. In fact,
		// we need different id on the terms for valuation (do not consider different repetition as different node)
		private ExpressionNode<?> getCopiedTree( ExpressionNode<?> toCopy){
			RuleSyntaxNode ruleASN = (RuleSyntaxNode) callingNode.getParentASN();
			ExpressionNode<?> copied = (ExpressionNode<?>) toCopy.copyTreeWithNewID( ruleASN.getActions(), true);
			return (ExpressionNode<?>) copied;
		}

		// it add the logical expression with this repeat node as the root.
		// It removes all previous children of this repeat node.
		public ExpressionNode<?> addRepatNode( ExpressionNode<?> logicalExpression, RepeatExpressionNode repeatNode){
			// remove all the child of the repeat node
			repeatNode.removeAllChildren();
			// add the logical expression to the repeat node
			repeatNode.addChild( logicalExpression);
			// return the new root of the expression node
			return repeatNode;
		}

		/////////////////////////////////////////////////// GETTERS
		// returns the repeat relation translated in a boolean expression tree (computed on constructor)
		public ExpressionNode<?> getLogicalExpression() {
			return toLogicalExpression;
		}
		// returns the list of leafs added to the binary tree during translation for all the repetitions 
		// the size of output is (maxLoopCnt - minLoopCnt)
		public List<RepeatitionRange> getRepetitionRanges() {
			return repetitionRanges;
		}

		/////////////////////////////////////////////////// AUXILIARY TRANSLATOR OBJECT
		// this class contains the term added in translating a single repeat iteration.
		// It is used on repeat evaluation to avoid case in which the root OR is true when 
		// the number of repetitions are greater than the minLoopCount.
		// Practically this is just a container object to describe the set of terms added to
		// translate a particular iteration number (repetitionNumber).
		protected class RepeatitionRange implements Serializable{
			/////////////////////////////////////////////////// FIELDS	
			// represent the times of repetition assigned to this term range (order of the binary construction)
			private Integer repetitionOrder;
			// represents the nodes added on this sub tree while creating the binary tree representation (boolean) of the repeat directive
			private Set< PseudoBinaryNode> binaryNodes = new LinkedHashSet<>(); 

			/////////////////////////////////////////////////// CONSTRUCTOR
			public RepeatitionRange( ExpressionNode<?> binaryTree, Integer order){
				// store the order in class field
				this.repetitionOrder = order;
				// get the set of nodes in the incoming tree
				Map< Long, ?> treeMap = binaryTree.toMap();
				for( Object node : treeMap.values())
					if( node instanceof ExpressionNode<?>)
						binaryNodes.add( (ExpressionNode<?>) node);
					else ParserLog.error( "Repeat Term Range skip the node " + node + " since it is not an instance of ExpressionNode<?>");
			}
			// called on post-processing
			public void updateDefinititon( PseudoBinaryNode node) {
				Set< PseudoBinaryNode> newNodes = new LinkedHashSet<>();
				for( PseudoBinaryNode r : binaryNodes){
					if( r.getOriginalId() == node.getOriginalId())
						newNodes.add( node);
					else 
						newNodes.add( r);
				}
				binaryNodes = newNodes;
			}

			/////////////////////////////////////////////////// ABSTRACT CHECKER
			// it returns true if at least one element in the repetition range has true value 
			public Boolean hasTrueOnRange(){
				for( PseudoBinaryNode t : binaryNodes)
					if( t instanceof SemanticExpressionNodeBase)
						if( ! ( ( SemanticExpressionNodeBase) t).getValue())
							return false;
				return true;
			}
			public Boolean hasFalseOnRange(){
				for( PseudoBinaryNode t : binaryNodes)
					if( t instanceof SemanticExpressionNodeBase)
						if( ( ( SemanticExpressionNodeBase) t).getValue())
							return false;
				return true;
				
			}
			
			/////////////////////////////////////////////////// GETTERS
			public Integer getRepetitionOrder() {
				return repetitionOrder;
			}
			public Set< PseudoBinaryNode> getRepetitionBinaryNodes() {
				return binaryNodes;
			}
			@Override
			public String toString(){
				return "(Order: " + repetitionOrder + ") range: " + binaryNodes;
			}
		}
	}
}