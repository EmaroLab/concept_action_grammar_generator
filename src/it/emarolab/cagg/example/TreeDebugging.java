package it.emarolab.cagg.example;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.evaluation.GuiEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.InputFormatterBase;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SyntaxActionTag;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.DebuggingText;
import it.emarolab.cagg.interfaces.TestLog;

@SuppressWarnings("unused")
public class TreeDebugging {

	/////////////////////// CONSTANTS
	// treeId is initernally manager, while command should be set by the user
	public static final int TREE_ID = 0;
	public static final int PSEUDO_BINARY_TREE_ID = 1;
	public static int treeId, command = TREE_ID;  
	
	/////////////////////// FIELDS 
	// internal tree for debugging
	private static BaseNode tree, pseudoBinarytree;
	private static TextualParser parser;
		
	/////////////////////// PARSING SIMULTAION 
	private static String getSampleTree(){ // this method should contain only preamble
		treeId = TREE_ID;
		return "#CAGv1;"
				+ "!start <MAIN>;"
				+ "!modifiable <rule1> <rule2>;"
				+ "!pronounce Dr \"# ' d A k . t $ R+ #\" | \"# ' d A k #\";"
				+ "<DEF> : I {@~;} have {@=r;@?;} ^sad{@!~;} | <H> {@?;};"
				+ "<DEF2> : you {@~;} have {@=r;@?;} ^got{@!!;} | <H> {@!DEF2;@?~;};"
				+ "<MAIN> : <D> | <E> {@?;};"
				+ "<E> : <DEF> {@?~;};"
				+ "<D> : <DEF2> {@?~;};"
				+ "<H> : (hi {@!~;} | hello {@=h;@=h1;}) !optional( there {@=t;});";	
	}
	private static String getSamplePsuedoBinaryTree(){ 
		treeId = PSEUDO_BINARY_TREE_ID; 
		return "#CAGv1;" // this method works only with the expression tree of the first rule of the grammar
				+ "<A> : (hi | hello) !optional( there);";				
	}
	private static BaseNode parseSimpleTree( String toParse){
		// parse
		parser = new TextualParser( toParse, false, treeId);
		TestLog.ok( "Successfully parsed !!!");
		// take tree w.r.t. ID
		switch( treeId){
			case TREE_ID :
				return parser.getASTree();
			case PSEUDO_BINARY_TREE_ID : 
				AbstractSyntaxNode<?> syntaxRoot = parser.getASBodyTree();
				ExpressionNode<?> firstRoole = ( (RuleSyntaxNode) syntaxRoot.getChild( 0)).getExpressionTree();
				if( firstRoole == null){
					TestLog.error( TreeDebugging.class.getSimpleName() + ": Frist rule to retrieve pseudo binary tree not found.");
					break;
				}
				return firstRoole;
			default : 
				TestLog.error( TreeDebugging.class.getSimpleName() + ": Tree type error.");
		}
		return null;
	}
	
	/////////////////////// SIMPLE DEBUGGING INTERFACE 	
	// do here your tree debugging tests (the input depends from the command field)
	private static void debugg( BaseNode tree){
		TestLog.ok( "tree debugging !!! " );
		// some common operation that you can do with CAGG trees
		String usedTree = "?";
		if( command == TREE_ID)
			usedTree = "TREE";
		else if( command == PSEUDO_BINARY_TREE_ID)
			usedTree = "PSEUDO_BINARY_TREE";
		tree.showTree( "original tree (" + usedTree + ")");
		tree.printTree();
		BaseNode simpleCopy = tree.copyTree();
		simpleCopy.showTree( "simple copy");
		TestLog.info( " SIMPLE COPY MAP: " + simpleCopy.toMap());
		BaseNode copyId = tree.copyTree( true);
		TestLog.info( " SIMPLE COPY MAP by Id: " + copyId.toMap());
		TestLog.info( " SIMPLE COPY MAP by copyId: " + copyId.toMap( true));
		copyId.showTree( "simple copy by generating copy id");
		
		// compose grammar expression tree
		// grammar can be composed only starting from ASTs
		if( command == TREE_ID){ 
			// create a grammar primitives
			GrammarPrimitive primitive = new GrammarPrimitive( parser);
			primitive.searchMainRuleName();
			ExpressionNode<?> mainRule = primitive.getMainRuleExpressionTree();
			// compose grammar expression tree
			ExpressionNode<?>.ComposedRule composed = mainRule.composeSubRule( primitive);
			composed.getRoot().showTree( "composed rule");
			// get syntax action tags
			ActionTagBase actions = new SyntaxActionTag( composed);
			TestLog.info( " create syntax node with action tags " + actions);
			// create semantic tree
			SemanticTree semantic = new SemanticTree( composed);
			semantic.getRoot().showTree( "semantic tree");
				
			// create Grammar
			GrammarBase< SemanticTree> grammar = ComposedGrammar.createGrammar( parser);
			// evaluate grammar
			ThreadedInputFormatter formatter = new ThreadedInputFormatter( grammar);
			EvaluatorBase< ?, ?> evaluator = new GuiEvaluator( formatter);
			evaluator.evaluate( " I have sad hi there");

			( (SemanticTree) grammar.getSemanticExpression()).getRoot().showTree("Semantic Expression");
		} else TestLog.error( "Those operation can be performed only for complete trees (and not pseudo binary trees). Set \"command = TREE_ID\"");
	}
	
	/////////////////////// RUNNER 
	public static void main(String[] args) {		
		try{
			// initialise textual logging (anyway the console is more reliable !!!!!!!!!!!!)
			new CaggLoggersManager();
			TestLog.ok( " tree debugging with tyoe: " + command);
			// do your test
			switch (command) {
				case TREE_ID:
					TestLog.ok( " tree parsing ... " );
					tree = parseSimpleTree( getSampleTree());
					debugg( tree);
					TestLog.ok( " succesfully tree debugging !!! " );
				break;
				case PSEUDO_BINARY_TREE_ID:
					TestLog.ok( " speudo binary parsing ... " );
					pseudoBinarytree = parseSimpleTree( getSamplePsuedoBinaryTree());
					debugg( pseudoBinarytree);
					TestLog.ok( " succesfully binary tree debugging !!! " );
				break;
				default:
					TestLog.error( TreeDebugging.class.getSimpleName() + ": Command field not correctly specified.");
					break;
			}
		} catch( Exception e){
			// flush possible messages stacked on the logger if an not cached exception occurs.
			TestLog.error( e);
		}
	}
}
