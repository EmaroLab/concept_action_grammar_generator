package it.emarolab.cagg.core.language.parser.ANTLRInterface;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.ParserListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionBaseListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.AndOperatorContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.BodyContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.GrammarHearderContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.NuanceGrammarContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.OrOperatorContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.PreambleContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.RuleBodyContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.RuleDeclarationContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.RuleDirectiveContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.RuleExpressionContext;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.RuleTermContext;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.parser.ANTLRInterface.LogicalParserListener.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends the {@link CAGGSyntaxDefinitionBaseListener} in order to set the value of flags used later to 
 * identify the the meaning of a node in the Parsing Tree (PT) based on its positions with respect to neighbourhood nodes.
 * This object is design to assure flag consistency in {@code enter}-ing node methods.<br>
 * 
 * 
 * Moreover, it is important to know that this implementation is based on the ANTLR grammar definition available in the file:
 * {@code ../files/parserGrammar/NuanceGrammar.g4} 
 * </p>
 *
 * @see CAGGSyntaxDefinitionBaseListener
 * @see TreeParserListener
 * @see ParserListener
 */
public class LogicalParserListener extends CAGGSyntaxDefinitionBaseListener{
	// implements all the logics used in the ParserListener
	// flags conventions: true->YES, false->(NO or unknown)

	// ########################################################################################################################
	// Attributes (they have getter methods) ##################################################################################
	// state flag of the listern parser
	private Boolean inGrammarRoot = false;
	private Boolean inGrammarPreamble = false;
	private Boolean inGrammarBody = false;
	private Boolean inRuleDeclarationPreamble = false, inRuleDeclarationBody = false;
	private Boolean inRuleBody = false;
	private Boolean orOperation = false;
	private Boolean andOperation = false;
	private Boolean ruleTerm = false;

	// ########################################################################################################################
	// Constructors ###########################################################################################################
	/**
	 * This constructor just calls its relative super class constructor 
	 * {@link it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionBaseListener#NuanceGrammarBaseListener()}
	 * in order to initialise this object.
	 */
	public LogicalParserListener() {
		super();
	}
		
	// ########################################################################################################################
	// Listen the parsing tree generation and set state flags !!!!  ###########################################################

	// #######################################################################################################
	// #####################################   ROOT flag   ###################################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node are inside the AST root node.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside the AST grammar root node. {@code false} otherwise.
	 * @see #enterNuanceGrammar(CAGGSyntaxDefinitionParser.NuanceGrammarContext)
	 * @see #exitNuanceGrammar(CAGGSyntaxDefinitionParser.NuanceGrammarContext)
	 */
	public Boolean isInGrammarRoot(){
		return inGrammarRoot;
	}
	/**
	 * This method just sets {@link #isInGrammarRoot()} to {@code true} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#enterNuanceGrammar(CAGGSyntaxDefinitionParser.NuanceGrammarContext)}.
	 */
	@Override
	public void enterNuanceGrammar(NuanceGrammarContext ctx) {
		this.inGrammarRoot = true;
		super.enterNuanceGrammar(ctx);
	}
	/**
	 * This method just resets {@link #isInGrammarRoot()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitNuanceGrammar(CAGGSyntaxDefinitionParser.NuanceGrammarContext)}.
	 */
	@Override
	public void exitNuanceGrammar(NuanceGrammarContext ctx) {
		this.inGrammarRoot = false;
		super.exitNuanceGrammar(ctx);
	}

	// #######################################################################################################
	// ###################################   PREAMBLE flag   #################################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node are inside the AST preamble node.
	 * This {@code false} otherwise.
	 * @return {@code true} if the parsing walk state is inside the AST grammar preamble node. {@code false} otherwise. 
	 * @see #enterPreamble(CAGGSyntaxDefinitionParser.PreambleContext)
	 * @see #exitPreamble(CAGGSyntaxDefinitionParser.PreambleContext)
	 * @see #enterGrammarHearder(CAGGSyntaxDefinitionParser.GrammarHearderContext)
	 * @see #exitGrammarHearder(CAGGSyntaxDefinitionParser.GrammarHearderContext)
	 */
	public Boolean isInGrammarPreamble(){
		return inGrammarPreamble;
	}
	/**
	 * This method just sets {@link #isInGrammarPreamble()} to {@code true} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#enterPreamble(CAGGSyntaxDefinitionParser.PreambleContext)}.
	 */
	@Override
	public void enterPreamble(PreambleContext ctx) {
		this.inGrammarPreamble = true;
		super.enterPreamble(ctx);
	}
	/**
	 * This method just resets {@link #isInGrammarPreamble()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitPreamble(CAGGSyntaxDefinitionParser.PreambleContext)}.
	 */
	@Override
	public void exitPreamble(PreambleContext ctx) {
		this.inGrammarPreamble = false;
		super.exitPreamble(ctx);
	}
	/**
	 * This method just sets {@link #isInGrammarPreamble()} to {@code true} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#enterGrammarHearder(CAGGSyntaxDefinitionParser.GrammarHearderContext)}.
	 */
	@Override
	public void enterGrammarHearder(GrammarHearderContext ctx) {
		this.inGrammarPreamble = true;
		super.enterGrammarHearder(ctx);
	}
	/**
	 * This method just resets {@link #isInGrammarPreamble()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitGrammarHearder(CAGGSyntaxDefinitionParser.GrammarHearderContext)}.
	 */
	@Override
	public void exitGrammarHearder(GrammarHearderContext ctx) {
		this.inGrammarPreamble = false;
		super.exitGrammarHearder(ctx);
	}

	// #######################################################################################################
	// #####################################   BODY flag   ###################################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node are inside the AST body node.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside the AST grammar body node. {@code false} otherwise.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	public Boolean isInGrammarBody(){
		return inGrammarBody;
	}
	/**
	 * This method just sets {@link #isInGrammarBody()} to {@code true} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#enterBody(CAGGSyntaxDefinitionParser.BodyContext)}.
	 */
	@Override
	public void enterBody(BodyContext ctx) {
		this.inGrammarBody = true;
		super.enterBody(ctx);
	}
	/**
	 * This method just resets {@link #isInGrammarBody()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitBody(CAGGSyntaxDefinitionParser.BodyContext)}.
	 */
	@Override
	public void exitBody(BodyContext ctx) {
		this.inGrammarBody = true;
		super.exitBody(ctx);
	}

	// #######################################################################################################
	// ##############################   RULE DECLARATION  flagS   ############################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node is 
	 * a Rule Declaration node into the the AST preamble. {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is a Rule Declaration inside the AST grammar preamble node. {@code false} otherwise.
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #exitRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 */
	public Boolean isInRuleDeclarationPreamble(){
		return this.inRuleDeclarationPreamble;
	}
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node is 
	 * a Rule Declaration node into the the AST body. {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is a Rule Declaration inside the AST grammar body node. {@code false} otherwise.
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #exitRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 */
	public Boolean isInRuleDeclarationBody(){
		return this.inRuleDeclarationBody;
	}
	/**
	 * This method sets the flags: {@link #isInRuleDeclarationBody()} and {@link #isInRuleDeclarationPreamble()}.
	 * In particular, it sets {@code inRuleDeclarationPreamble = true} and {@code inRuleDeclarationBody = false} if 
	 * {@link #isInGrammarPreamble()} returns {@code true}. Otherwise it sets {@code inRuleDeclarationPreamble = false} 
	 * and {@code inRuleDeclarationBody = true} if {@link #isInGrammarBody()} returns {@code true}. Otherwise it logs
	 * a warning message. Afetr this procedure this method calls its super-method:
	 * {@code #enterRuleDeclaration(RuleDeclarationContext)}. 
	 */
	@Override
	public void enterRuleDeclaration(RuleDeclarationContext ctx) {
		if( this.isInGrammarPreamble()){
			this.inRuleDeclarationPreamble = true;
			this.inRuleDeclarationBody = false;
		} else if( this.isInGrammarBody()){
			this.inRuleDeclarationPreamble = false;
			this.inRuleDeclarationBody = true;
		} else ParserLog.warning( "the rule declaration is not detected either in the grammar preamble nor in the body.");
		super.enterRuleDeclaration(ctx);
	}
	/**
	 * This method just resets {@link #isInRuleDeclarationPreamble()} and {@link #isInRuleDeclarationBody()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitBody(CAGGSyntaxDefinitionParser.BodyContext)}.
	 */
	@Override
	public void exitRuleDeclaration(RuleDeclarationContext ctx) {
		this.inRuleDeclarationPreamble = false;
		this.inRuleDeclarationBody = false;
		super.exitRuleDeclaration(ctx);
	}

	// #######################################################################################################
	// #################################   RULE BODY  flag    ################################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node are inside a Rule Body node.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside a PT Rule Body node. {@code false} otherwise.
	 * @see #enterRuleBody(CAGGSyntaxDefinitionParser.RuleBodyContext)
	 * @see #exitRuleBody(CAGGSyntaxDefinitionParser.RuleBodyContext)
	 */
	public Boolean isInRuleBody(){
		return this.inRuleBody;
	}
	/**
	 * This method just sets {@link #isInRuleBody()} to {@code true} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#enterRuleBody(CAGGSyntaxDefinitionParser.RuleBodyContext)}.
	 */
	@Override
	public void enterRuleBody(RuleBodyContext ctx) {
		this.inRuleBody = true;
		super.enterRuleBody(ctx);
	}
	/**
	 * This method just resets {@link #isInRuleBody()} to {@code false} before to call its super method:
	 * {@link CAGGSyntaxDefinitionBaseListener#exitRuleBody(CAGGSyntaxDefinitionParser.RuleBodyContext)}.
	 */
	@Override
	public void exitRuleBody(RuleBodyContext ctx) {
		this.inRuleBody = false;
		//firstTime_inRuleExpression = true;
		super.exitRuleBody(ctx);
	}


	// #######################################################################################################
	// #################################    OR OPERATION detection   #########################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node indicates an OR operator in an ET.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside an actual OR operation node in an ET. {@code false} otherwise.
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 */
	public Boolean isOrOperation(){
		return orOperation;				// this data is reset at every or operation parsed
	}
	/**
	 * This method set {@link #isOrOperation()} to {@code true} if this node has at least a child node of type {@code orOperation}
	 * which, in turn, has a child that contains the or symbol ({@code "|"}). Otherwise, it set it to {@code false}.
	 * Then, it calls its super-method: {@link #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)}
	 */
	@Override
	public void enterOrOperator(OrOperatorContext ctx) {
		orOperation = false;
		List<ParseTree> orChildren = getChildOfTypeOR( ctx);
		for( ParseTree orChild : orChildren)
			if( hasChildrenWithOrSymbol( orChild))
				orOperation = true;
		super.enterOrOperator(ctx);
	}
	
	
	// #######################################################################################################
	// #################################   AND OPERATION detection   #########################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node indicates an AND operator in an ET.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside an actual AND operation node in an ET. {@code false} otherwise.
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 */
	public Boolean isAndOperation(){
		return andOperation; 			// this data is reset at every and operation parsed
	}
	/**
	 * This method set {@link #isAndOperation()} to {@code true} if this node has at least a child node of type {@code andOperation}
	 * which, in turn, has at least a child. Otherwise, it set it to {@code false}.
	 * Then, it calls its super-method: {@link #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)}
	 */
	@Override
	public void enterAndOperator(AndOperatorContext ctx) {
		andOperation = false;
		List<ParseTree> andChildren = getChildOfTypeAND( ctx);
		if( ! andChildren.isEmpty())
			andOperation = true;
		super.enterAndOperator(ctx);
	}
	
	
	// #######################################################################################################
	// ####################################   RULE TERM detection   ##########################################
	// #######################################################################################################
	/**
	 * This method returns {@code true} if the walking through the PT state indicates that the actual parsing node indicates an expression Term in an ET.
	 * {@code false} otherwise.  
	 * @return {@code true} if the parsing walk state is inside an actual Term expression node in an ET. {@code false} otherwise.
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 */
	public Boolean isRuleTerm(){
		return ruleTerm ; 			// this data is reset at every and operation parsed
	}
	/**
	 * This method set {@link #isRuleTerm()} to {@code true} if this node has no children
	 * of type {@code ruleExpression} or {@code ruleDirective}.
	 * Otherwise, it set it to {@code false}.
	 * Then, it calls its super-method: {@link #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)}
	 */
	@Override
	public void enterRuleTerm(RuleTermContext ctx) {
		ruleTerm = false;
		List<ParseTree> expressionChildren = getChildOfTypeExpression( ctx);
		List<ParseTree> directiveChildren = getChildOfTypeDirective( ctx);
		if( expressionChildren.isEmpty() && directiveChildren.isEmpty())
			ruleTerm = true;
		super.enterRuleTerm(ctx);
	}

	
	
	
	
	
	// ########################################################################################################################
	// Methods to check Parsing Tree state  ###################################################################################
	// return the set of children node that are of the specified type
	/**
	 * This method returns all the node of type {@code orOperator} that are children of the input parameter.
	 * @param node the PT node from which looking for {@code orOperator} children.
	 * @return the list of children of {@code node} that are of type {@code orOperator}. Empty list if none.
	 */
	protected List< ParseTree> getChildOfTypeOR( ParseTree node){
		List< ParseTree> childrenOfType = new ArrayList< ParseTree>();
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i) instanceof OrOperatorContext)			//// type specification !!!!! (the only things that changes !!!)
				childrenOfType.add( node.getChild( i));
		return childrenOfType;
	}
	/**
	 * This method returns all the node of type {@code ondOperator} that are children of the input parameter.
	 * @param node the PT node from which looking for {@code andOperator} children.
	 * @return the list of children of {@code node} that are of type {@code andOperator}. Empty list if none.
	 */
	protected List< ParseTree> getChildOfTypeAND( ParseTree node){
		List< ParseTree> childrenOfType = new ArrayList< ParseTree>();
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i) instanceof AndOperatorContext)			//// type specification
				childrenOfType.add( node.getChild( i));
		return childrenOfType;
	}
	/**
	 * This method returns all the node of type {@code ruleTerm} that are children of the input parameter.
	 * @param node the PT node from which looking for {@code ruleTerm} children.
	 * @return the list of children of {@code node} that are of type {@code ruleTerm}. Empty list if none.
	 */
	protected List< ParseTree> getChildOfTypeTerm( ParseTree node){
		List< ParseTree> childrenOfType = new ArrayList< ParseTree>();
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i) instanceof RuleTermContext)			//// type specification
				childrenOfType.add( node.getChild( i));
		return childrenOfType;
	}
	/**
	 * This method returns all the node of type {@code ruleExpression} that are children of the input parameter.
	 * @param node the PT node from which looking for {@code ruleExpression} children.
	 * @return the list of children of {@code node} that are of type {@code ruleExpression}. Empty list if none.
	 */
	protected List<ParseTree> getChildOfTypeExpression( ParseTree node) {
		List< ParseTree> childrenOfType = new ArrayList< ParseTree>();
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i) instanceof RuleExpressionContext)			//// type specification
				childrenOfType.add( node.getChild( i));
		return childrenOfType;
	}
	/**
	 * This method returns all the node of type {@code ruleDirective} that are children of the input parameter.
	 * @param node the PT node from which looking for {@code ruleDirective} children.
	 * @return the list of children of {@code node} that are of type {@code ruleDirective}. Empty list if none.
	 */
	protected List<ParseTree> getChildOfTypeDirective( ParseTree node) {
		List< ParseTree> childrenOfType = new ArrayList< ParseTree>();
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i) instanceof RuleDirectiveContext)			//// type specification
				childrenOfType.add( node.getChild( i));
		return childrenOfType;
	}
	// TODO: add with more types....

	// returns true if the node has at list one child with has exactly X children
	/**
	 * This method returns {@code true} if the input parameter {@code node} has at least a child 
	 * with a number of children (sub-child) equal to {@code X}. 
	 * @param node the PT node from which get child of which check if the number of children is equal to {@code X}.
	 * @param X the number of children that at least one child of the input {@code node} must have to return a positive boolean.
	 * @return {@code true} if the input {@code node} has at least a child which have {@code X} children. {@code false} otherwise.
	 */
	protected Boolean hasChildWithXChildren( ParseTree node, int X){
		for( int i = 0; i < node.getChildCount(); i++)
			if( node.getChild(i).getChildCount() == X)
				return true;
		return false;
	}
	/**
	 * This method simple calls: {@link #hasChildWithXChildren(ParseTree, int)}
	 * with {@code node} and {@code 0} input parameters respectively.
	 * @param node the PT node from which get child of which check if the number of children is equal to {@code 0}.
	 * @return {@code true} if the input {@code node} has at least a child which have {@code 0} children. {@code false} otherwise.
	 */
	protected Boolean hasChildWith0Children( ParseTree node){		// has a child that is a leaf
		return hasChildWithXChildren( node, 0);
	}
	/**
	 * This method simple calls: {@link #hasChildWithXChildren(ParseTree, int)}
	 * with {@code node} and {@code 1} input parameters respectively.
	 * @param node the PT node from which get child of which check if the number of children is equal to {@code 1}.
	 * @return {@code true} if the input {@code node} has at least a child which have {@code 1} child. {@code false} otherwise.
	 */
	protected Boolean hasChildWith1Children( ParseTree node){ 		// has a child that has only one other child
		return hasChildWithXChildren( node, 1);
	}
	/**
	 * This method simple calls: {@link #hasChildWithXChildren(ParseTree, int)}
	 * with {@code node} and {@code 2} input parameters respectively.
	 * @param node the PT node from which get child of which check if the number of children is equal to {@code 2}.
	 * @return {@code true} if the input {@code node} has at least a child which have {@code 2} children. {@code false} otherwise.
	 */
	protected Boolean hasChildWith2Children( ParseTree node){		// has a child that has two children
		return hasChildWithXChildren( node, 2);
	}
	/**
	 * This method simple calls: {@link #hasChildWithXChildren(ParseTree, int)}
	 * with {@code node} and {@code 3} input parameters respectively.
	 * @param node the PT node from which get child of which check if the number of children is equal to {@code 3}.
	 * @return {@code true} if the input {@code node} has at least a child which have {@code 3} children. {@code false} otherwise.
	 */
	protected Boolean hasChildWith3Children( ParseTree node){		// has a child that has tree children
		return hasChildWithXChildren( node, 3);
	}

	// returns true if the node has at list one child which contains the exactly symbol
	/**
	 * This method returns true if the input parameter has a child which have at least a child which text is equals to {@code symbol}.
	 * It returns {@code false} otherwise.
	 * @param orChild the {@code node} from which check if its children have at least a child which text is equals to {@code symbol}.
	 * @param symbol the string to identify specific sub children by comparing it with its text (retrieved through: {@link ParseTree#getText()}).
	 * @return {@code true} if the input {@code orNode} has at least a child which have a child with text equals to {@code symbol}.
	 */
	protected Boolean hasChildrenWithSymbol( ParseTree orChild, String symbol){
		for( int i = 0; i < orChild.getChildCount(); i++)
			if( orChild.getChild(i).getText().equals(symbol))
				return true;
		return false;
	}
	/**
	 * This method just calls and returns the results of:
	 * {@link #hasChildrenWithOrSymbol(ParseTree)} with:
	 * {@code orChild} and {@code "|"}; as input parameter respectively.
	 * @param orChild the {@code node} from which check if its children have at least a child which text is equals to {@code symbol}.
	 * @return {@code true} if the input {@code orNode} has at least a child which have a child with text equals to {@code "|"}.
	 */
	protected Boolean hasChildrenWithOrSymbol( ParseTree orChild){
		return hasChildrenWithSymbol( orChild, "|");
	}
	// TODO: add with symbols "!" "option" "repeat" ....
}