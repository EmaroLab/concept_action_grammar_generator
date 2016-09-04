package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionStringData;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ActionExpressionNode extends ExpressionNode< ExpressionStringData>{

	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final String SYNTAX_KEYWORD = "action";
	public static final Integer MAX_CHILDREN_NUMBER = 1;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public ActionExpressionNode( ParserRuleContext ctx){		// constructor. ex: !action("do something")
		super( MAX_CHILDREN_NUMBER);
		if( ctx != null) // called on copy 
			parse( ctx);
	}
	public void parse( ParserRuleContext ctx){
		String literal = TextualParser.retrieveDirectiveContent(ctx, SYNTAX_KEYWORD);
		literal = TextualParser.retrieveString(literal);
		this.getData().setLiteral( literal);
	}
	@Override
	public ActionExpressionNode copy( Boolean updateCopyId) {
		return (ActionExpressionNode) this.copyContents( new ActionExpressionNode( null), updateCopyId);
	}

	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */
	@Override
	protected ExpressionStringData assignData() {
		return new ExpressionDataFactory().getNewExpString();	// create a new node data of type string
	}
	@Override
	protected Type assignType() {	
		return Type.ACTION;	// return node type
	}

	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	 */
	@Override
	public ExpressionNode<ExpressionStringData>.NodeEvaluator getEvaluator() {
		return new NodeEvaluator() {
			@Override
			public Boolean evaluate(List<Boolean> childrenValue) { 
				ParserLog.warning( "ACTION Expression Node (" + this + ") Evaluation method not implemented ... propagate the first child's value with no manipulation (or true if it is a leaf)." );
				if( childrenValue.isEmpty())
					return true;
				return childrenValue.get( 0);
			}
		};
	}	
	// Semantic grammar initialisation
	@Override
	public void doPreProcessing( RuleSyntaxNode ruleNode, List< GrammarPrimitive> allPrimitive) {
		// do not do nothing. Called when source files are already parsed but the grammar have not been created yet,
	}
	@Override
	public void doPostProcessing(RuleSyntaxNode ruleNode, GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		// do not do nothing. Called when grammar is created. Here you should rely on getOriginalId()
		// to retrieve data between parsed information and semantic expression creation.
	}

}