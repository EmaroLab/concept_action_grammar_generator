package it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionTermData;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class TermExpressionNode  extends ExpressionNode< ExpressionTermData>{

	/* ##################################################################################
	   ############################ CONSTANTS ##########################################
	 */
	public static final String SYNTAX_KEYWORD_OPEN = "{";
	public static final String SYNTAX_KEYWORD_CLOSE = "}";
	public static final Integer MAX_CHILDREN_NUMBER = 0;
	
	/* ##################################################################################
	   ############################# STATIC #############################################
	   term that are not evaluated from the input formatter and those are always set to true
		this mechanism improve a lot the performances of the SympleImputFormatter 
		(that implements a brutal force algorithm)
		DO NOT PUT ACTION TAGS ON THIS TERM SINCE THEY WILL BE ALWAYS TRUE !!!!	*/
	private static Set< String> notEvaluatedTerm = new LinkedHashSet< String>(); 
	public static Set< String> getNotEvaluatedTerms(){
		return notEvaluatedTerm;
	}
	private void setNotEvaluatedTerm( String term){
		this.getData().setInstances( term, false);
		notEvaluatedTerm.add( term);;
	}	
	
	/* ##################################################################################
	   ######################### COSTRUCTOR & COPY ######################################
	   the method copy(..) defines which constructor gets called during copy (by default
	   with only one "null"). In this case do not initialise the class, the copyContents(..)
	   method will do it for you. Override that method if you want to store extra fields*/
	public TermExpressionNode( ParserRuleContext ctx){		// constructor. ex: !action("do something")
		super( MAX_CHILDREN_NUMBER);
		if( ctx != null) // called on copy 
			parse( ctx);
	}
	public void parse( ParserRuleContext ctx){
		// remove eventually action tag (@) all between "{ .. }" will be discarded
		String text = TextualParser.removeWordBetweenTwoSymbols( ctx.getText(), SYNTAX_KEYWORD_OPEN, SYNTAX_KEYWORD_CLOSE); 	
		// parse the term text base on is type and context
		switch( this.getData().getTokenType( text)){
		case ExpressionTermData.FROM_ERROR:		
			ParserLog.error("Term node creation with no token: " + ctx.getText());
			break;
		case ExpressionTermData.FROM_IDENTIFIER:
			addFromIdentifierOrString( text);
			break;
		case ExpressionTermData.FROM_STRING:
			addFromIdentifierOrString( TextualParser.retrieveString( text));
			break;
		case ExpressionTermData.FROM_DECLARATION:
			this.getData().setInstances( TextualParser.retrieveDeclaration( text), true);
			break;
		}
	}
	private void addFromIdentifierOrString( String parsedText){
		if( parsedText.startsWith( "^"))
			setNotEvaluatedTerm( parsedText.replace( "^", ""));
		else this.getData().setInstances( parsedText, false);
	}
	@Override
	public TermExpressionNode copy( Boolean updateCopyId) {
		return (TermExpressionNode) this.copyContents( new TermExpressionNode( null), updateCopyId);
	}
	
	/* ##################################################################################
	   ######################### INITIALISATION #########################################
	 */
	@Override
	protected ExpressionTermData assignData() {
		return new ExpressionDataFactory().getNewExpTerm();		// create a new node data of empty type
	}
	@Override
	protected Type assignType() {
		return Type.TERM;
	}

	/* ##################################################################################
	   #################### SEMANTIC GRAMMAR INTERFACE ##################################
	 */
	@Override
	public ExpressionNode<ExpressionTermData>.NodeEvaluator getEvaluator() { // it would be always empty
		return new StateNodeEvaluator();
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