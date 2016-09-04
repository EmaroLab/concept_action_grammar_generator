package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class LabelExpressionNode extends ExpressionNode< LabelExpressionData>{

	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final String SYNTAX_KEYWORD = "label";
	public static final Integer MAX_CHILDREN_NUMBER = 1;

	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public LabelExpressionNode( ParserRuleContext ctx){		// constructor. ex: !label("do something","something else")
		super( MAX_CHILDREN_NUMBER);
		if( ctx != null) // called on copy 
			parse( ctx);
	}
	public void parse( ParserRuleContext ctx){
		List< String> literals = TextualParser.retrieveDirectiveContents(ctx, SYNTAX_KEYWORD);
		for( String l : literals)
			this.getData().addToDataList( TextualParser.retrieveString( l));
	}
	@Override
	public LabelExpressionNode copy( Boolean updateCopyId) {
		return (LabelExpressionNode) this.copyContents( new LabelExpressionNode( null), updateCopyId);
	}	

	
	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */
	@Override
	protected LabelExpressionData assignData() {
		return new LabelExpressionData();
	}
	@Override
	protected Type assignType() {
		return Type.LABEL;				// return node type
	}

	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	 */
	@Override
	public ExpressionNode<LabelExpressionData>.NodeEvaluator getEvaluator() {
		return new NodeEvaluator() {
			@Override
			public Boolean evaluate(List<Boolean> childrenValue) {
				ParserLog.warning( "LABEL Expression Node (" + this + ") Evaluation method not implemented ... propagate the first child's value with no manipulation (or true if it is a leaf)." );
				if( childrenValue.isEmpty())
					return true;
				return childrenValue.get( 0);
			}
		};
	}
	// Semantic grammar initialisation
	@Override
	public void doPreProcessing(RuleSyntaxNode ruleNode, List< GrammarPrimitive> preliminaryGrammars) {
		// do not do nothing. Called when source files are already parsed but the grammar have not been created yet,
	}
	@Override
	public void doPostProcessing(RuleSyntaxNode ruleNode, GrammarBase<? extends SemanticExpressionTreeBase> grammar) {
		// do not do nothing. Called when grammar is created. Here you should rely on getOriginalId()
		// to retrieve data between parsed information and semantic expression creation.
	}
}


/* 	##################################################################################
	########################### UTILITY CLASS ########################################
 	this is a container for the specific type of data of this expression node.			*/
@SuppressWarnings("serial")
class LabelExpressionData extends BaseData< List< String>>{
	
	public LabelExpressionData(){
		super();
		this.setInstance( new ArrayList< String>());
	}
	
	public void addToDataList( String literal){
		this.getInstance().add( literal);
	}
	
	@Override
	public String toString() {
		return this.getInstance().toString();
	}

}