package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionEmptyData;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class PronounceExpressionNode extends ExpressionNode< ExpressionEmptyData>{

	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final Integer MAX_CHILDREN_NUMBER = 1;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public PronounceExpressionNode(){							
		super( MAX_CHILDREN_NUMBER);
	}
	public PronounceExpressionNode( ParserRuleContext ctx){
		super( MAX_CHILDREN_NUMBER);
		// care about copy to initalise further fields
	}
	@Override
	public PronounceExpressionNode copy( Boolean updateCopyId) {
		return (PronounceExpressionNode) this.copyContents( new PronounceExpressionNode( null), updateCopyId);
	}
	
	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */
	@Override
	protected ExpressionEmptyData assignData() {
		return new ExpressionDataFactory().getNewExpEmpty();	// create a new node data of empty type
	}
	@Override
	protected Type assignType() {
		return Type.PRONOUNCE;	// return node type
	}

	
	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	 */
	@Override
	public ExpressionNode<ExpressionEmptyData>.NodeEvaluator getEvaluator() {
		return new NodeEvaluator() {
			@Override
			public Boolean evaluate(List<Boolean> childrenValue) {
				ParserLog.warning( "PRONOUNCE Expression Node (" + this + ") Evaluation method not implemented ... propagate the first child's value with no manipulation (or true if it is a leaf)." );
				if( childrenValue.isEmpty())
					return true;
				return childrenValue.get( 0);
			}
		};
	}
	// semantic grammar initialisation
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
