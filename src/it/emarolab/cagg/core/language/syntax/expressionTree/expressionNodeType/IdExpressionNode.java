package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionIntegerData;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class IdExpressionNode extends ExpressionNode< ExpressionIntegerData>{


	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final String SYNTAX_KEYWORD = "id";
	public static final Integer MAX_CHILDREN_NUMBER = 1;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public IdExpressionNode( ParserRuleContext ctx){				// constructor. example: !id(3)
		super( MAX_CHILDREN_NUMBER);
		if( ctx != null) // called on copy 
			parse( ctx);
	}
	public void parse( ParserRuleContext ctx){
		String literal = null;
		try{
			literal = TextualParser.retrieveDirectiveContent(ctx, SYNTAX_KEYWORD);
			Integer id = Integer.valueOf( literal);
			this.getData().setInteger( id);
		} catch( Exception e){
			ParserLog.error( "Id Expression node creation error, the id cannot be parsed to an integer: " + literal);
			ParserLog.error(e);
			this.getData().setInteger( null);
		}
	}
	@Override
	public IdExpressionNode copy( Boolean updateCopyId) {
		return (IdExpressionNode) this.copyContents( new IdExpressionNode( null), updateCopyId);
	}

	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */
	@Override
	protected ExpressionIntegerData assignData() {
		return new ExpressionDataFactory().getNewExpInteger();		// create a new node data of type string
	}

	@Override
	protected Type assignType() {
		return Type.ID;			// return node type
	}
	
	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	 */
	@Override
	public ExpressionNode<ExpressionIntegerData>.NodeEvaluator getEvaluator() {
		return new NodeEvaluator() {
			@Override
			public Boolean evaluate(List<Boolean> childrenValue) {
				ParserLog.warning( "ID Expression Node (" + this + ") Evaluation method not implemented ... propagate the first child's value with no manipulation (or true if it is a leaf)." );
				if( childrenValue.isEmpty())
					return true;
				return childrenValue.get( 0);
			}
		};
	}	
	// Semantic grammar initialisation
	@Override
	public void doPreProcessing( RuleSyntaxNode ruleNode, List< GrammarPrimitive> preliminaryGrammars) {
		// do not do nothing. Called when source files are already parsed but the grammar have not been created yet,
	}
	@Override
	public void doPostProcessing(RuleSyntaxNode ruleNode, GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		// do not do nothing. Called when grammar is created. Here you should rely on getOriginalId()
		// to retrieve data between parsed information and semantic expression creation.
	}
}