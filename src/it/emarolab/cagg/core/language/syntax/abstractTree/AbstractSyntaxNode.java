package it.emarolab.cagg.core.language.syntax.abstractTree;

import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.SyntaxNode;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractSyntaxNode.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.core.language.SyntaxNode},
 * where the type {@code T} is {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode.Type},
 * while the data {@code D} are classes that extends {@link it.emarolab.cagg.core.language.BaseData}
 * (typically  instances of:
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData},
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData},
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrStrData} or
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrTreeData}.
 * Instantiated through {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory}).<br>
 * 
 * This is done in order to implement a generic Note (ASN) for the Abstract Syntax Tree (AST). 
 * This kind of tree is build from the Parsing Tree (PS) and it only contains the minimal set of information.
 * Particularly, the AST contains a root node with two children. The first collects (at the same depth) all the preamble of the
 * source code, while the second contains, in the same structure, all the rules.<br>
 * 
 * Those nodes (so the AST) are instantiated during the parsing tree building performed by:
 * {@link it.emarolab.cagg.core.language.parser.ParserListener};
 * activable during the constructor of the object: 
 * {@link it.emarolab.cagg.core.language.parser.TextualParser}.<br>
 * 
 * Last but not the least, the final implementation of those type of nodes are collected in the package: 
 * {@literal dotVocal.nuanceGoogleIntegration.semanticParser.parser.abstractSyntaxGenerator.nuanceSyntaxNode.syntaxNodeType}.
 * </p>
 *
 * @see it.emarolab.cagg.core.language.SyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode.Type
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrStrData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrTreeData
 * @see it.emarolab.cagg.core.language.parser.ParserListener
 * @see it.emarolab.cagg.core.language.parser.TextualParser
 *  
 * @param <D> the type of data attached to this node. 
 * {@code D} must be a class that extends {@link it.emarolab.cagg.core.language.BaseData}.
 */
@SuppressWarnings("serial")
abstract public class AbstractSyntaxNode<D extends BaseData<?>> extends SyntaxNode< AbstractSyntaxNode.Type, D>{
	/**
	 * The default prefix id for all Abstract Syntax Tree node.
	 * By default {@code "AST:"}.
	 */
	public static final String TREE_PREFIX_ID = "AST:";
	
	// ####################################################  FIELDS (not serialisable!!!!!)
	transient private ParserRuleContext ctx; // string data coming from parser
	transient private Parser parser; // parser to manage ParseRuleContext
		
	// ####################################################  CONSTRUCTOR
	/**
	 * Initialise this class by calling {@link it.emarolab.cagg.core.language.SyntaxNode#BaseNode(String)}
	 * and storing in the internal variable of this class the parsing context ({@code ctx}) and 
	 * the parser ({@code parser}); given as input parameters.
	 * Finaly, this also sets {@link it.emarolab.cagg.core.language.SyntaxNode#setPrefixId(String)}
	 * to {@link #TREE_PREFIX_ID}.
	 * 
	 * @param ctx the paring rule context of the analysed parsing tree node.
	 * @param parser a structure to better get informations from context parsing rule.
	 */
	public AbstractSyntaxNode( ParserRuleContext ctx, Parser parser){
		super( TREE_PREFIX_ID);
		if( ctx != null && parser != null){
			this.ctx = ctx;
			this.parser = parser;
			this.assign();
		} //else ParserLog.info( "Create new node copy, you can't call context parsing information on it !! (ctx:null, parser:null)");
	}
	
	/**
	 * This constructor can be used to create an empty node to be initialised.
	 * It calls {@link it.emarolab.cagg.core.language.SyntaxNode#BaseNode(String)} and then 
	 * {@link #assign()}. 
	 */
	protected AbstractSyntaxNode() {
		super( TREE_PREFIX_ID);
		this.assign();
	}
	
	// ####################################################  ABSTRACT
	@Override
	abstract protected D assignData(); 	// initialise the data field, called on constructor

	@Override
	abstract protected AbstractSyntaxNode.Type assignType(); // initialise the type field, called on constructor

	@Override
	abstract public AbstractSyntaxNode< D> copy( Boolean updateCopyId);
	
	// ####################################################  GETTERS & SETTERS

	/**
	 * @return the context parsing rule ({@code ctx})
	 */
	public ParserRuleContext getParsingTokens(){				// return token context
		return this.ctx;										
	}
	
	/**
	 * @param ctx the new context parsing rule to set in this node.
	 */
	public void updateParsingTokens( ParserRuleContext ctx){	// set new token context
		this.ctx = ctx;											
	}
	
	/**
	 * @return the parser given during class construction
	 */
	public Parser getParser(){									// get parser
		return this.parser;										
	}
	
	/**
	 * @return the text relate to the context parsing rule. 
	 * It basically calls: {@link org.antlr.v4.runtime.ParserRuleContext#getText()}. 
	 * If {@code ctx} contained in this node is {@code null}, it returns an empty String and logs an error. 
	 */
	public String getParsingText(){								// get tokens as string
		if( ctx != null){
			String tex = ctx.getText();		
			ParserLog.info( "parsing text: " + tex);
			return tex;
		} else {
			ParserLog.error( "Cannot give prsing text since the parsing rule context is null or not initialised.");
			return "";
		}
	}
		
	/**
	 * It updates the internal parsing context by calling: 
	 * {@link #updateParsingTokens(ParserRuleContext)} using the input parameter {@code ctx}.
	 * Than, it return the text of the input context by calling: {@link #getParsingTokens()}.
	 * @param ctx the new context parsing rule to set in this node.
	 * @return the text relate to the context parsing rule given as input ({@code ctx}). 
	 */
	public String getParsingText( ParserRuleContext ctx){		// get new tokens as string and update the token context
		this.updateParsingTokens( ctx);
		return this.getParsingText();
	}
	
	/**
	 * It returns the child of a node by index.
	 * It overlay the function {@link javax.swing.tree.DefaultMutableTreeNode#getChildAt(int)}
	 * by returning an object of {@code AbstractSyntaxNode< D>} type by calling:
	 * {@link it.emarolab.cagg.core.language.SyntaxNode#getChild(int)}.  
	 * @param index the number of child to be retrieved.
	 * @return the child of this node by index.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AbstractSyntaxNode< D> getChild(int index) {
		return (AbstractSyntaxNode<D>) super.getChild(index);
	}
	
	// ####################################################  OVERWITING METHODS
	
	/**
	 * This method returns the description of this node as a Sting for easy debugging
	 * and viualisation on the GUI.<br>
	 * It returns {@code "[" + debuggingId + "] " + type + " (" + dataStr + ")"},
	 * where the {@code debuggingId} is the data returning from
	 * {@link it.emarolab.cagg.core.language.SyntaxNode#getDebuggingId()}.
	 * While, {@code type} is given from {@link it.emarolab.cagg.core.language.SyntaxNode#getType()}.
	 * Finally, {@code dataStr} is {@literal "null"} if 
	 * {@link it.emarolab.cagg.core.language.SyntaxNode#getData()}
	 * or {@link it.emarolab.cagg.core.language.BaseData#getInstance()}
	 * are {@code null}. Otherwise, {@code dataStr = this.getData().getInstance().toString()}
	 * @see it.emarolab.cagg.core.language.SyntaxNode#toString()
	 **/
	@Override
	public String toString(){
		String dataStr = "null";
		if( this.getData() != null)
			if( this.getData().getInstance() != null)
				dataStr = this.getData().getInstance().toString();
		//else ParserLog.warning( "AbstractSyntacNode.toString cannot be called since data is null");
		return "[" + this.getDebuggingId() + "] " + this.getType() + " (" + dataStr + ")";
	}

	// TODO equals method override !!! ???	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractSyntaxNode.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This enumerator defines the possible node types in the Abstract Syntax Tree (AST).
	 * Particularly, each nodes that implements {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode} 
	 * must return, as a type ({@link SyntaxNode#assignType()}), one different value of this enumeration.<br>
	 * Add value to this enumeration if you want to add a new abstract syntax node type.
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
	 * @see SyntaxNode
	 */
	public enum Type {
		// root and sub root types
		GRAMMAR				, // used in RootSyntaxNode class
		PREAMBLE			, // used in PreambleSyntaxNode class
		BODY				, // used in BodySyntaxNode class
		// preamble children
		HEADER				, // used in HearderSyntaxNode class
		GRAMMAR_DECLARATION , // used in GrammarSyntaxNode class
		LANGUAGE			, // used in LanguageSyntaxNode class
		EXPORT				, // used in ExportSyntaxNode class
		IMPORT				, // used in ImportSyntaxNode class
		START				, // used in StartSyntaxNode class
		MODIFIABLE			, // used in ModifiableSyntaxNode class
		ACTIVABLE			, // used in ActivableSyntaxNode class
		PRONOUNCE_PREAMBLE	, // used in PronoundeSyntaxNode class (this type of nodes contains an expression tree)
		// body children
		RULE				, // used in RuleSyntaxNode class (this type of nodes contains an expression tree)
	}

}