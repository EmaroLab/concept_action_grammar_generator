package it.emarolab.cagg.core.language.parser;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.LogicalParserListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.*;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.ActivableSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.BodySyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.ExportSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.GrammarSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.HeaderSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.ImportSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.LanguageSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.ModifiableSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.PreambleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.PronounceSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RootSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.StartSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionTreeGenerator;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.ActionExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.AndExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.IdExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.LabelExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OptionalExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OrExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.PronounceExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.RepeatExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode;

import java.util.List;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.parser.ParserListener.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class is the top level parser listener of the system. It extends the {@link TreeParserListener} in order to 
 * generate the Abstract Syntax Tree from Parsing Tree traversal.<br>
 * </p>
 *
 * @see TreeParserListener
 * @see LogicalParserListener
 * @see AbstractSyntaxNode
 * @see TextualParser
 * @see ExpressionNode
 * @see ExpressionTreeGenerator
 */
public class ParserListener extends TreeParserListener{
	// implements all the part of the listener that create the abstract syntax tree (AST).
	// The AST is made by AbstractSyntaxNode which implementations are in the nuanceSyntaxNode package
	
	// super class constructor
    /**
     * This constructor just initialises this object by calling its super-constructor:
     * {@link TreeParserListener#TreeParserListener(CAGGSyntaxDefinitionParser)}.
     * @param parser the ANTLR generated class for parsing the source code. 
     */
    public ParserListener( CAGGSyntaxDefinitionParser parser) {
    	super( parser); // remember to initialise the super class with the method setBaseNodes();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // initialise the abstract syntax tree  (root and sub-roots:Preamble and Body)  ||||||||||||||||||||
    // no command			 														||||||||||||||||||||
    // body data: grammar file path, other node does not have any data				||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterNuanceGrammar(CAGGSyntaxDefinitionParser.NuanceGrammarContext)}) first.
	 * Then, it initialises the main AST nodes (root, preamble and body) by instantiate: {@link RootSyntaxNode}, 
	 * {@link PreambleSyntaxNode} and {@link BodySyntaxNode} (respectively). Finally, it uses them in order to set the AST 
	 * attached to this class by calling: {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}. 
	 */
	@Override
	public void enterNuanceGrammar(NuanceGrammarContext ctx) {
		super.enterNuanceGrammar(ctx);
		
		// create grammar preamble and body node (INITIALISE SUPER CLASS)	
		AbstractSyntaxNode<?> astRoot = new RootSyntaxNode( ctx, getParser()); // create abstract syntax tree root when the grammar starts
		AbstractSyntaxNode<?> astPreamble = new PreambleSyntaxNode( ctx, getParser()); // create abstract syntax tree sub-root (GRAMMAR PREAMBLE)
		AbstractSyntaxNode<?> astBody = new BodySyntaxNode( ctx, getParser()); // create abstract syntax tree sub-root (GRAMMAR BODY)
		this.setBaseNodes( astRoot, astPreamble, astBody);
		// show logging messages
		ParserLog.info( "<enter NuanceGrammar>\t" + getAstRoot().toString() + "\t\t [AST root created]");
		ParserLog.info( "<enter NuanceGrammar>\t" + getAstPreamble().toString() + "\t\t [Preamble sub tree created]");
		ParserLog.info( "<enter NuanceGrammar>\t" + getAstBody().toString() + "\t\t [Body sub tree created]");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// add to the preamble the grammar header definition (add to the preamble)	 	||||||||||||||||||||
	// command example: "#BNF+EMV2.1;"												||||||||||||||||||||
	// data: "BNF+EMV2.1" (code specification)										||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterGrammarHearder(CAGGSyntaxDefinitionParser.GrammarHearderContext)}) first.
	 * Then, it initialises the header node ({@link HeaderSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterGrammarHearder(GrammarHearderContext ctx) { 
		super.enterGrammarHearder(ctx);
		
		// create hearder node and add in the preamble
		AbstractSyntaxNode<?> asn = new HeaderSyntaxNode( ctx, getParser());
		this.addNode( asn);
		// log data
		ParserLog.info( "<enter GrammarHearder>\t" + asn.toString() + "\t\t [node added to preamble]");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the grammar declaration name (add to the preamble)		||||||||||||||||||||||||||||||||||||
	// command example: "!grammar test;" 							||||||||||||||||||||||||||||||||||||
	// data: test (grammar name)									||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterGrammarDeclaration(CAGGSyntaxDefinitionParser.GrammarDeclarationContext)}) first.
	 * Then, it initialises the grammar node ({@link GrammarSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterGrammarDeclaration(GrammarDeclarationContext ctx) {
		super.enterGrammarDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new GrammarSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter GrammarDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the grammar language (add to the preamble)				||||||||||||||||||||||||||||||||||||
	// command example: "!language test;" or  "!language "te st";"  ||||||||||||||||||||||||||||||||||||
	// data: test or te st (grammar language)						||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterLanguageDeclaration(CAGGSyntaxDefinitionParser.LanguageDeclarationContext)}) first.
	 * Then, it initialises the language node ({@link LanguageSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterLanguageDeclaration(LanguageDeclarationContext ctx) {
		super.enterLanguageDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new LanguageSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter LanguageDeclaration>\t" +  asn.toString() + "\t\t [node added to preamble]");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the rule export declarations (add to the preamble)		||||||||||||||||||||||||||||||||||||
	// command example: "!export <test>;"							||||||||||||||||||||||||||||||||||||
	// data: test (exporting rule name)								||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterExportDeclaration(CAGGSyntaxDefinitionParser.ExportDeclarationContext)}) first.
	 * Then, it initialises the export node ({@link ExportSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterExportDeclaration(ExportDeclarationContext ctx) {
		super.enterExportDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new ExportSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter ExportDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the import rule declarations (add to the preamble)		||||||||||||||||||||||||||||||||||||
	// command example: "!import <test>;"							||||||||||||||||||||||||||||||||||||
	// data: test (import rule name)								||||||||||||||||||||||||||||||||||||	
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterImportDeclaration(CAGGSyntaxDefinitionParser.ImportDeclarationContext)}) first.
	 * Then, it initialises the grammar node ({@link ImportSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterImportDeclaration(ImportDeclarationContext ctx) {
		super.enterImportDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new ImportSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter ImportDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the gramm main rule declarations (add to the preamble)	||||||||||||||||||||||||||||||||||||
	// command example: "!start <test>;"							||||||||||||||||||||||||||||||||||||
	// data: test (starting rule name)								||||||||||||||||||||||||||||||||||||	
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterStartDeclaration(CAGGSyntaxDefinitionParser.StartDeclarationContext)}) first.
	 * Then, it initialises the start node ({@link StartSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterStartDeclaration(StartDeclarationContext ctx) {
		super.enterStartDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new StartSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter StartDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the modifiable rule declarations (add to the preamble)	||||||||||||||||||||||||||||||||||||
	// command example: "!modifiable <r1> <r2>;"					||||||||||||||||||||||||||||||||||||
	// data: r1 and r2 (modifiable rule 1 and 2 names)				||||||||||||||||||||||||||||||||||||	
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterModifableDeclaration(CAGGSyntaxDefinitionParser.ModifableDeclarationContext)}) first.
	 * Then, it initialises the grammar node ({@link ModifiableSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterModifableDeclaration(ModifableDeclarationContext ctx) {
		super.enterModifableDeclaration(ctx);
		
		AbstractSyntaxNode<?> asn = new ModifiableSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter ModifableDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the activatable rule declarations (add to the preamble)||||||||||||||||||||||||||||||||||||
	// command example: "!activatable <r1> <r2>;"					||||||||||||||||||||||||||||||||||||
	// data: r1 and r2 (activatable rule 1 and 2 names)				||||||||||||||||||||||||||||||||||||	
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterActivableDeclaration(CAGGSyntaxDefinitionParser.ActivableDeclarationContext)}) first.
	 * Then, it initialises the grammar node ({@link ActivableSyntaxNode}) and add it as a child of the preamble node into the AST.   
	 */
	@Override
	public void enterActivableDeclaration(ActivableDeclarationContext ctx) {
		super.enterActivableDeclaration(ctx);
	
		AbstractSyntaxNode<?> asn = new ActivableSyntaxNode( ctx, getParser());
		this.addNode( asn);
		ParserLog.info( "<enter ActivableDeclaration>\t" + asn.toString() + "\t\t [node added to preamble]");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the pronounce declarations (add to the preamble)		||||||||||||||||||||||||||||||||||||
	// command example: "!pronounce Ident 1 "phonic" | "phonic""	||||||||||||||||||||||||||||||||||||
	//					"!pronounce "ident1" "phonic" "phonic""		||||||||||||||||||||||||||||||||||||
	// data: ident1 and phonicsTree (Expression tree)				||||||||||||||||||||||||||||||||||||
	private PronounceSyntaxNode asnPronounce; 
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)}) first.
	 * Then, it creates and initialises a new pronounce node ({@link PronounceSyntaxNode}).
	 * @see #enterPronunceLabel(CAGGSyntaxDefinitionParser.PronunceLabelContext)
	 * @see #exitPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)
	 */
	@Override
	public void enterPronounceDeclaration(PronounceDeclarationContext ctx) { // create node
		super.enterPronounceDeclaration(ctx);
		
		asnPronounce = new PronounceSyntaxNode( ctx, getParser());
		ParserLog.info( "<enter PronoundeDeclaration>\t" + asnPronounce.toString() + "\t\t [node created, tree itialising...]");
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterPronunceLabel(CAGGSyntaxDefinitionParser.PronunceLabelContext)}) first.
	 * Then, it calls the method {@link PronounceSyntaxNode#setName(org.antlr.v4.runtime.ParserRuleContext)} 
	 * by giving {@code ctx} as input parameter in 
	 * order to retrieve and store in the pronounce AST node the label attached to this definition. 
	 * This is done on the instance of the ASN created in {@link #enterPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)}.
	 * Finally, it creates a new Expression Tree instance by calling: {@link #newExpressionGenerator()} that can be manipulated by:
	 * {@link #enterOptionalDirective(CAGGSyntaxDefinitionParser.OptionalDirectiveContext)},
	 * {@link #enterLabelDirective(CAGGSyntaxDefinitionParser.LabelDirectiveContext)}, {@link #enterIdDirective(CAGGSyntaxDefinitionParser.IdDirectiveContext)},
	 * {@link #enterActionDirective(CAGGSyntaxDefinitionParser.ActionDirectiveContext)}, {@link #enterRepeatDirective(CAGGSyntaxDefinitionParser.RepeatDirectiveContext)},
	 * {@link #enterPronounceDirective(CAGGSyntaxDefinitionParser.PronounceDirectiveContext)}, {@link #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)},
	 * {@link #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)} and {@link #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)}; 
	 * in order to generate the logical definition of the pronounce.
	 * @see #enterPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)
	 * @see #exitPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)
	 */
	@Override
	public void enterPronunceLabel(PronunceLabelContext ctx) {				// set pronounce name
		super.enterPronunceLabel(ctx);										// the expression tree is created in the RULE section (as for the others)
		
		this.newExpressionGenerator();
		asnPronounce.setName( ctx);										
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#exitPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)}) first.
	 * Then, it calls the method {@link PronounceSyntaxNode#setTree(ExpressionNode)} by giving {@link #getExpressionTree()} as input parameter in 
	 * order to compose and store the ET that logically defines the pronounce relations. 
	 * This is done on the instance of the ASN created in {@link #enterPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)}.
	 * Finally, it adds the pronounce node to the AST preamble node.
	 * @see #enterPronounceDeclaration(CAGGSyntaxDefinitionParser.PronounceDeclarationContext)
	 * @see #enterPronunceLabel(CAGGSyntaxDefinitionParser.PronunceLabelContext)
	 */
	@Override
	public void exitPronounceDeclaration(PronounceDeclarationContext ctx) { // add node to AST
		super.exitPronounceDeclaration(ctx);
		
		asnPronounce.setTree( this.getExpressionTree()); // add the Expression node to the pronound node in the ASN
		this.addNode( asnPronounce);   // add the Abstract Syntax Node (ASN) in the Abstract Synatax Tree (AST)
		ParserLog.info( "<exit PronounceDeclaration>\t" + asnPronounce.toString() + "\t\t [node added to preamble]");
		asnPronounce = null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage OPTIONAL in the ExpressionTree creation				||||||||||||||||||||||||||||||||||||
	// command example: "!optional(<R1> A "ciao")"					||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterOptionalDirective(CAGGSyntaxDefinitionParser.OptionalDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link OptionalExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Optional node.   
	 */
	@Override
	public void enterOptionalDirective(OptionalDirectiveContext ctx) {
		super.enterOptionalDirective(ctx);	
		
		OptionalExpressionNode optNode = new OptionalExpressionNode();
		setParentASN( optNode);
		this.addExpressionOperation( optNode); // introduce a new OPTIONAL node
		ParserLog.info( "<enter OptionalDeclaration>\t" + "add OPTIONAL operational node to the expression tree.");
	}
	
//	////////////////////////////////////////////////////////////////////////////////////////////////////
//	// Manage IGNORE in the ExpressionTree creation					||||||||||||||||||||||||||||||||||||
//	// command example: "!ignore(<R1> A "ciao")"					||||||||||||||||||||||||||||||||||||
//	/**
//	 * This method calls its super-method ({@link TreeParserListener#enterIgnoreDirective(NuanceGrammarParser.IgnoreDirectiveContext)}) first.
//	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link IgnoreExpressionNode} as input parameter.
//	 * This is done in order to add to the actual expression tree state of this object a new Ignore node.   
//	 */
//	@Override
//	public void enterIgnoreDirective(IgnoreDirectiveContext ctx) {
//		super.enterIgnoreDirective(ctx);
//		
//		this.addExpressionOperation( new IgnoreExpressionNode()); // introduce a new IGNORE node
//		ParserLog.info( "<enter IgnoreDirective>\t" + "add IGNORE operational node to the expression tree.");
//	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage LABEL in the ExpressionTree creation					||||||||||||||||||||||||||||||||||||
	// command example: "!label( "uno" "due" "tre")"				||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterLabelDirective(CAGGSyntaxDefinitionParser.LabelDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link LabelExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Label node.   
	 */
	@Override
	public void enterLabelDirective(LabelDirectiveContext ctx){
		super.enterLabelDirective(ctx);
		
		LabelExpressionNode lbNode = new LabelExpressionNode( ctx);
		this.setParentASN( lbNode);
		this.addExpressionOperation( lbNode); // introduce a new LABEL node
		ParserLog.info( "<enter LabelDirective>\t" + "add LABEL operational node to the expression tree.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage ID in the ExpressionTree creation						||||||||||||||||||||||||||||||||||||
	// command example: "a !id( 12) b"								||||||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterIdDirective(CAGGSyntaxDefinitionParser.IdDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link IdExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Id node.   
	 */
	@Override
	public void enterIdDirective(IdDirectiveContext ctx){
		super.enterIdDirective(ctx);
		
		IdExpressionNode idNode = new IdExpressionNode( ctx);
		setParentASN( idNode);
		this.addExpressionOperation( idNode); // introduce a new ID node
		ParserLog.info( "<enter IdDirective>\t" + "add ID operational node to the expression tree.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage ACTION in the ExpressionTree creation						||||||||||||||||||||||||||||||||
	// command example: "file close !action("fclose ( fileHandle);");"	||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterActionDirective(CAGGSyntaxDefinitionParser.ActionDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link ActionExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Action node.   
	 */
	@Override
	public void enterActionDirective(ActionDirectiveContext ctx){
		super.enterActionDirective(ctx);
		
		ActionExpressionNode actNode = new ActionExpressionNode( ctx);
		setParentASN( actNode);
		this.addExpressionOperation( actNode); // introduce a new ACTION node
		ParserLog.info( "<enter ActionDirective>\t" + "add ACTION operational node to the expression tree.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage PRONOUNCE in the ExpressionTree creation					||||||||||||||||||||||||||||||||
	// command example: "read !pronounce("'R+Ed")"						||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterPronounceDirective(CAGGSyntaxDefinitionParser.PronounceDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link PronounceExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Pronounce node.   
	 */
	@Override
	public void enterPronounceDirective(PronounceDirectiveContext ctx){
		super.enterPronounceDirective(ctx);
		
		PronounceExpressionNode prNode = new PronounceExpressionNode();
		setParentASN( prNode);
		this.addExpressionOperation( prNode); // introduce a new PRONOUNCE node
		ParserLog.info( "<enter PronounceDirective>\t" + "add PRONOUNCE operational node to the expression tree.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Manage REPEAT in the ExpressionTree creation					||||||||||||||||||||||||||||||||
	// command example: "!repeat( <digit>, 9, 12 )"						||||||||||||||||||||||||||||||||
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterRepeatDirective(CAGGSyntaxDefinitionParser.RepeatDirectiveContext)}) first.
	 * Then, it calls {@link #addExpressionOperation(ExpressionNode)} with a new {@link RepeatExpressionNode} as input parameter.
	 * This is done in order to add to the actual expression tree state of this object a new Repeat node.   
	 */
	@Override
	public void enterRepeatDirective(RepeatDirectiveContext ctx){
		super.enterRepeatDirective(ctx);
		
		RepeatExpressionNode rpNode = new RepeatExpressionNode( ctx);
		setParentASN( rpNode);
		this.addExpressionOperation( rpNode); // introduce a new REPEAT node
		ParserLog.info( "<enter RepeatDirective>\t" + "add REPEAT operational node to the expression tree.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// catch the rule expression declaration (add to the body)		||||||||||||||||||||||||||||||||||||
	// command example: "<R>: (A | B) !optional(<R1>)"				||||||||||||||||||||||||||||||||||||
	// data: R and ruleTree (ExpressionTree)						||||||||||||||||||||||||||||||||||||
	private RuleSyntaxNode asnRule;
	private ExpressionNode<?> lastTerm = null;
	private List< String> actionPending = null; // if null no action pending
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterBody(CAGGSyntaxDefinitionParser.BodyContext)}) first.
	 * Then, it creates and initialises a new pronounce node ({@link RuleSyntaxNode}).
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterBody( BodyContext ctx) {
		super.enterBody(ctx);
		
		asnRule = new RuleSyntaxNode( ctx, getParser()); // create rule node (for the abstract parsing tree)
		ParserLog.info( "<enter Body>\t" + "create new Rule node" + "\t\t [node created]");
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)}) first.
	 * Then, if {@link #isInRuleDeclarationBody()} is {@code true} and {@link #isInRuleBody()} is {@code false}, it calls
	 * {@link RuleSyntaxNode#setRuleName(org.antlr.v4.runtime.ParserRuleContext)} in order to set the name of the rule. Finally, it calls also:
	 * {@code #newExpressionGenerator()} to initialise a new expression tree to describe the rule. Otherwise it does not do nothing.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterRuleDeclaration( RuleDeclarationContext ctx) {
		super.enterRuleDeclaration(ctx);
		
		if( this.isInRuleDeclarationBody() & ! this.isInRuleBody()){ // is inside a rule declaration (no preamble) & is not on the rule body (possible variables)
			asnRule.setRuleName( ctx); // set rule name from rule declaration (either not in the preamble nor in the rule body)
			this.newExpressionGenerator();
			ParserLog.info( "<enter RuleDeclaration>\t" + asnRule.toString() + "\t\t [node named, tree itialising...]");
		}
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)}) first.
	 * Then, if {@link #isAndOperation()} is {@code true}, it calls {@link #addExpressionOperation(ExpressionNode)}
	 * with a new {@link AndExpressionNode} as input parameter in order to add a new And node to the ET state. 
	 * Otherwise it does not do nothing.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterAndOperator(AndOperatorContext ctx) {
		super.enterAndOperator(ctx);
		
		if( isAndOperation()){	// it is in an AndOperation node which has at least a child that is an AndOperation node as well.
			AndExpressionNode andNode = new AndExpressionNode();
			setParentASN( andNode);
			this.addExpressionOperation( andNode); // So, introduce a new AND node
			ParserLog.info( "<enter AndDeclaration>\t" + "add AND node to the expression tree.");
		}
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)}) first.
	 * Then, if {@link #isOrOperation()} is {@code true}, it calls {@link #addExpressionOperation(ExpressionNode)}
	 * with a new {@link OrExpressionNode} as input parameter in order to add a new Or node to the ET state. 
	 * Otherwise it does not do nothing.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterOrOperator(OrOperatorContext ctx) { 
		super.enterOrOperator(ctx);
		
		if( isOrOperation()){	// it is in an OrOperation node which has at least a child that is an OrOperation node as well and the latter has also '!' as one of its children.
			OrExpressionNode orNode = new OrExpressionNode();
			setParentASN( orNode);
			this.addExpressionOperation( orNode); // so, introduce a new OR node
			ParserLog.info( "<enter OrDeclaration>\t" + "add OR node to the expression tree.");
		}
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)}) first.
	 * Then, if {@link #isRuleTerm()} is {@code true}, it calls {@link #addExpressionTerm(ExpressionNode)}
	 * with a new {@link TermExpressionNode} as input parameter in order to add a new Term node to the ET state. 
	 * Moreover, if there are pending action tags to be added it calls {@link RuleSyntaxNode#addActions(List, ExpressionNode)}
	 * in order to relate the AST rule note also with its action tags.
	 * Otherwise it does not do nothing.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterRuleTerm(RuleTermContext ctx) {
		super.enterRuleTerm(ctx);
		
		if( isRuleTerm()){		// is a term that does not have children of type ExpressionRule or RuleDirective
			lastTerm = new TermExpressionNode( ctx); // ctx.getText() may contains actions ( "{ ... }") that here are discarded.
			setParentASN( lastTerm);
			this.addExpressionTerm( lastTerm);	// So, introduce a new TERM node
			ParserLog.info( "<enter RuleTerm>\t" + "add TERM node to the expression tree.");
			if( actionPending != null){ // add pending action to this term
				asnRule.addActions( actionPending, lastTerm);	// add pending actions
				ParserLog.info( "<enter RuleTerm>\t" + "action pending stored with value: " + actionPending);
				actionPending = null;							// reset for next Term
			}
		}
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)}) first.
	 * Then, it resets the object identifying the last term added to the ET.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void exitRuleTerm(RuleTermContext ctx) {
		super.enterRuleTerm(ctx);
		
		lastTerm = null;
		ParserLog.info( "<exit RuleTerm>\t" + "reset last term.");
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)}) first.
	 * Then, it parses the string containing the action tags (if any) and add them to the last term of this expression
	 * by calling {@link RuleSyntaxNode#addActions(List, ExpressionNode)}.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitBody(CAGGSyntaxDefinitionParser.BodyContext)
	 */
	@Override
	public void enterActionDefinition(ActionDefinitionContext ctx) {
		super.enterActionDefinition(ctx);	// to collect eventual action to be attached to the last rule term
				
		List< String> actions = asnRule.getActions( ctx);
		if( lastTerm == null) {	
			actionPending = actions;				// this tag must be attached to the next Term, store actions
			ParserLog.info( "<enter ActionDefinition>\t" + "new pendig action parsed (" + actionPending + ")");
		} else {
			asnRule.addActions( actions, lastTerm);	// add actions
			actionPending = null;					// reset for next Term
			ParserLog.info( "<enter ActionDefinition>\t" + "new action parsed and stored: " + actions);
		}
	}
	/**
	 * This method calls its super-method ({@link TreeParserListener#exitBody(CAGGSyntaxDefinitionParser.BodyContext)}) first.
	 * Then, it calls {@link #getExpressionTree()} in order to generate the ET and set it to the actual AST rule node by calling:
	 * {@link RuleSyntaxNode#setTree(ExpressionNode)}. Finally, it adds that rule node to the AST body node.
	 * @see #enterBody(CAGGSyntaxDefinitionParser.BodyContext)
	 * @see #enterRuleDeclaration(CAGGSyntaxDefinitionParser.RuleDeclarationContext)
	 * @see #enterAndOperator(CAGGSyntaxDefinitionParser.AndOperatorContext)
	 * @see #enterOrOperator(CAGGSyntaxDefinitionParser.OrOperatorContext)
	 * @see #enterRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #exitRuleTerm(CAGGSyntaxDefinitionParser.RuleTermContext)
	 * @see #enterActionDefinition(CAGGSyntaxDefinitionParser.ActionDefinitionContext)
	 */
	@Override
	public void exitBody( BodyContext ctx) {
		super.enterBody(ctx);
		
		ExpressionNode<?> et = this.getExpressionTree();
		/*if( et.isLeaf())
			if( et instanceof TermExpressionNode)
				if( ( (TermExpressionNode) et).getData().getTermFlag())
					ParserLog.error("Cannot parse a rule which is composed by only one ruleDeclaration. (Rule Name: " + asnRule.getRuleName());*/
		asnRule.setTree( et); // add the Expression node to the rule node in the ASN
		addNode( asnRule);   // add the Abstract Syntax Node (ASN) in the Abstract Synatax Tree (AST)
		ParserLog.info( "<exit Body>\t" + asnRule.toString() + "\t\t [node added to body]");
		asnRule = null;
	}
	
	private void setParentASN( ExpressionNode<?> node){
		if( asnPronounce != null)
			node.setParentASN( asnPronounce);
		else if( asnRule != null)
			node.setParentASN( asnRule);
		else ParserLog.error("Parser Listener cannot add the parent AST node to the ET node: " + node);
	}
}
