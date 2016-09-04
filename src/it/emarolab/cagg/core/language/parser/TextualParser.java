package it.emarolab.cagg.core.language.parser;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ParserBase;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionLexer;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.NuanceGrammarContext;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.parser.TextualParser.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link ParserBase} object with parameter set to: {@link CAGGSyntaxDefinitionLexer},
 * {@link CAGGSyntaxDefinitionParser}, {@link NuanceGrammarContext} and {@link ParserListener}; respectively.<br>
 * 
 * It purposes is to activate the parsing procedure and get a populated Abstract Syntax Tree (AST).
 * For doing so, it requires a source text to be parsed in order to create the Parsing Tree (PT) and then
 * walk over it to create the AST by using a listener (this is done in class constructor).<br>
 * 
 * Last but not the least, this class also implements static methods used to manipulate strings
 * and detach information during parsing. 
 * </p>
 * 
 * @see ParserBase
 * @see ParserListener
 */
public class TextualParser extends ParserBase< CAGGSyntaxDefinitionLexer, CAGGSyntaxDefinitionParser, 
										NuanceGrammarContext, ParserListener>{
	
	// ########################################################################################################################
	// CLASS CONSTANTS (used for static string manipulations) #################################################################
	/**
	 * The default data separator symbol of the grammar. By default set to {@literal ","}.
	 */
	public static final String DEFAULT_SEPARATOR = ",";
	/**
	 * The default directive beginning symbol of the grammar. By default set to {@literal "!"}.
	 */
	public static final String DEFAULT_BEGINNING = "!";
	/**
	 * The default expression opening symbol of the grammar. By default set to {@literal "("}.
	 */
	public static final String DEFAULT_OPEN = "(";
	/**
	 * The default expression ending symbol of the grammar. By default set to {@literal ")"}.
	 */
	public static final String DEFAULT_CLOSE = ")";
	/**
	 * The default string identifying symbol of the grammar. By default set to {@literal "\""}.
	 */
	public static final String DEFAULT_STRING_IDENTIFIER = "\"";
	/**
	 * The default symbol replaced to the chars to be removed. By default set to {@literal ""}.
	 */
	public static final String DEFAULT_REMOVING_REPLACE = "";
	/**
	 * The default declaration starting symbol of the grammar. By default set to {@literal "<"}.
	 */
	public static final String DEFAULT_DECLARATION_OPEN = "<";
	/**
	 * The default declaration ending symbol of the grammar. By default set to {@literal ">"}.
	 */
	public static final String DEFAULT_DECLARATION_CLOSE = ">";
		
	// ########################################################################################################################
	// CLASS FIELDS ###########################################################################################################
	private Integer id = null;
	private Boolean hasError;
	
	// ########################################################################################################################
	// CONSTRUCTORS ###########################################################################################################
	/**
	 * This constructor simple rely on {@link ParserBase#ParserBase(String, Boolean)}
	 * by using: {@code toParse} and {@code fromFile} as input parameters respectively.
	 * Further it initialises this class {@code id} with respect to the input specification.
	 * This is used mainly for visualisation and debugging.
	 * Finally, this constructor also creates a new {@link ParserListener} and set it
	 * as listener through the method: {@link #setListener(ParseTreeListener)}.
	 * @param toParse the text to be parsed (if {@code fromFile} is {@code false}). 
	 * Or the path to the file to be parsed (if {@code from file} is {@code true}).
	 * @param fromFile identifies if the parameter {@code toParse} is the text to be parsed
	 * ({@code false}) or the path to the file to be parsed ({@code false}).
	 * @param id the identifier to be assigned to this object.
	 */
	public TextualParser(String toParse, Boolean fromFile, Integer id) {
		super( toParse, fromFile);
		initialise( id);
	}
	/**
	 * This constructor simple rely on {@link ParserBase#ParserBase(String)}
	 * by using: {@code toParse} as input parameter.
	 * Further it initialises this class {@code id} with respect to the input specification.
	 * This is used mainly for visualisation and debugging.
	 * Finally, this constructor also creates a new {@link ParserListener} and set it
	 * as listener through the method: {@link #setListener(ParseTreeListener)}.
	 * @param toParse the source text to be parsed.
	 * @param id the identifier to be assigned to this object.
	 */
	public TextualParser(String toParse, Integer id) {
		super(toParse);
		initialise( id);
	}
	// initialisation and listener setting
	private void initialise( Integer id){
		this.id = id;
		hasError = this.setListener( new ParserListener( this.getParser()));
	}
	
	// ########################################################################################################################
	// ABSTRACT CLASS IMPLEMENTATION ##########################################################################################
	@Override
	protected CAGGSyntaxDefinitionLexer instanciateLexer(ANTLRInputStream stream) {
		return new CAGGSyntaxDefinitionLexer( stream);  	// initialise object for use the ANTLR grammar implementation (generated) 
	}
	@Override
	protected CAGGSyntaxDefinitionParser instanciateParse(CAGGSyntaxDefinitionLexer lexer) {
		return new CAGGSyntaxDefinitionParser( new CommonTokenStream( lexer));		// initialise object for use the ANTLR grammar implementation (generated)
	}
	/**
	 * This method specify the main ANTLR rule with respect to which the parser starts. 
	 * Particularly, it is set to: {@code parser.nuanceGrammar()} 
	 * @see ParserBase#startParser(Parser)
	 */
	@Override
	protected NuanceGrammarContext startParser(CAGGSyntaxDefinitionParser parser) {
		return parser.nuanceGrammar();	// Specify starting grammar rule !!!!!!!!!
	}
	/**
	 * This method describes this object with a string for visualisation and debugging purposes.
	 * It simply returns: {@code "grammarId:" + id}, where {@code id} is the value given on class constructors
	 * ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.ParserBase#toString()
	 */
	@Override
	public String toString() {
		return "grammarId:" + id;
	}
	
	// ########################################################################################################################
	//    GETTERS   ###########################################################################################################
	// getters may return null if there is parsing error
	/**
	 * @return the {@code id} assigned to this object on constructors 
	 * ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * This is used mainly for visualisation or debugging purposes.
	 */
	public Integer getId(){
		return id;
	}
	/**
	 * This method, returns the root node of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstRoot()}.
	 * @return the Abstract Syntax Tree root node.
	 */
	public AbstractSyntaxNode<?> getASTree(){
		return this.getListener().getAstRoot();
	}
	/**
	 * This method, returns the root node of a copy of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstRoot()}
	 * and then the copy is given by calling: {@link AbstractSyntaxNode#copyTree()}.
	 * @return a copy of the Abstract Syntax Tree root node.
	 */

	/**
	 * This method, returns the preamble node of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstPreamble()}.
	 * @return the Abstract Syntax Tree preamble node.
	 */
	public AbstractSyntaxNode<?> getASPreambleTree(){
		return this.getListener().getAstPreamble();
	}
	/**
	 * This method, returns the preamble node of a copy of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstPreamble()}
	 * and then the copy is given by calling: {@link AbstractSyntaxNode#copyTree()}.
	 * @return a copy of the Abstract Syntax Tree preamble node.
	 */

	/**
	 * This method, returns the body node of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstBody()}.
	 * @return the Abstract Syntax Tree body node.
	 */
	public AbstractSyntaxNode<?> getASBodyTree(){
		return this.getListener().getAstBody();
	}
	/**
	 * This method, returns the body node of a copy of the Abstract Syntax Tree generated during parsing.
	 * In particular, it is generated by walking through the Parsing Tree and by applying the listener
	 * created on constructor ({@link #TextualParser(String, Integer)} or {@link #TextualParser(String, Boolean, Integer)}).
	 * More in details, the tree is given by querying: {@link ParserListener#getAstBody()}
	 * and then the copy is given by calling: {@link AbstractSyntaxNode#copyTree()}.
	 * @return a copy of the Abstract Syntax Tree body node.
	 */
	
	/**
	 * @return {@code true} if at lest an error occurred during parsing perfomed on constructor.
	 */
	public boolean hasError() {
		return hasError;
	}		
	
	// ########################################################################################################################
	// Usefull STATIC methods to deal with parsed string and tokens ###########################################################
	/**
	 * This method returns {@code true} if the calls {@code baseLit.contains( {@value #DEFAULT_STRING_IDENTIFIER})} returns {@code true}. 
	 * {@code false} otherwise.
	 * @param baseLit the string to be checked (if it is a string literal or not).
	 * @return {@code true} if {@code baseLit} is a string literal. {@code false} otherwise.
	 */
	public static Boolean isStringLiteral( String baseLit){
		if( baseLit != null)
			return baseLit.contains( DEFAULT_STRING_IDENTIFIER);
		else return false;
	}
	
	/**
	 * This method remove a prefix from a string by returning:
	 * {@code baseStr.substring( prefix.length(), baseStr.length())}.
	 * @param baseStr the string from which remove the prefix
	 * @param prefix the prefix to be removed from {@code baseStr}.
	 * @return the a copy of the {@code baseStr} where a number of char equals to
	 * the length of {@code prefix} is removed (from the beginning of the base string). 
	 * @see String#substring(int, int)
	 */
	public static String removePrefix( String baseStr, String prefix){			// remove a string at the beginning of baseStr
		return baseStr.substring( prefix.length(), baseStr.length());
	}
	
	// returns the first occurrence of the substring contained between start and end symbol (the output contains startSymb and endSymb)
	/**
	 * This method calls: {@link #getWordBetweenSymbols(String, String, String, Boolean)} with:
	 * {@code baseStr}, {@code startsSymb}, {@code endSymb} and {@code false}; respectively.
	 * @param baseStr the string from where retrieve the set of chars between symbols.
	 * @param startSymb the starting symbol type that defines the set of chars returned by this method.
	 * @param endSymb the ending symbol type that defines the set of chars returned by this method.
	 * @return the chars of {@code baseStr} that are within the symbols: {@code startSymb} and {@code endSymb}.
	 * (The first occurrence). 
	 */
	public static String getWordBetweenSymbols( String baseStr, String startSymb, String endSymb){
		return getWordBetweenSymbols( baseStr, startSymb, endSymb, false);
	}
	// returns the first (or last) occurrence of the substring contained between start and end symbol (the output contains startSymb and endSymb)
	/**
	 * This method relies on: {@code String#indexOf(int)}, {@code String#lastIndexOf(int)} and {@code String#substring(int, int)}
	 * in order to get the chars of an input string that are between two symbols. By searching for the
	 * first or last occurrence.
	 * @param baseStr the string from where retrieve the set of chars between symbols.
	 * @param startSymb the starting symbol type that defines the set of chars returned by this method.
	 * @param endSymb the ending symbol type that defines the set of chars returned by this method.
	 * @param lastOf if it is {@code true} the method looks for the last occurrence of the {@code startSymb}
	 * and {@code endSymb}. While, if it is {@code false} it looks for the first occurrence.   
	 * @return the set of chars that are between the two given symbols (excluded).
	 */
	public static String getWordBetweenSymbols( String baseStr, String startSymb, String endSymb, Boolean lastOf){	
		int subStart = baseStr.indexOf( startSymb) + 1;
		int subEnd;
		if( lastOf)
			subEnd = baseStr.lastIndexOf( endSymb);
		else subEnd = baseStr.indexOf( endSymb);
		if( subStart >= 0 && subEnd >= subStart)
			return baseStr.substring( subStart, subEnd);
		ParserLog.warning("Cannot find words between symbols (" + startSymb + "," + endSymb + "). Input text:" +  baseStr);
		return "";
	}
	
	// removes the first occurrence of the substring contained between start and end symbol (the output does not contains startSymb and endSymb!!!!!)
	/**
	 * This method calls: {@link #getWordBetweenSymbols(String, String, String)} with:
	 * {@code baseStr}, {@code startSymb} and {@code endSymb} input parameters respectively.
	 * Then, it returns: {@code baseStr.replace( startSymb + toRemove + endSymb, {@value #DEFAULT_REMOVING_REPLACE})} where
	 * {@code toRemove} is the string returning from the previous call. 
	 * @param baseStr the string from where remove the set of chars between symbols.  
	 * @param startSymb the starting symbol type that defines the set of chars removed by this method.
	 * @param endSymb the ending symbol type that defines the set of chars removed by this method.
	 * @return a copy of {@code baseStr} that does not have the first occurrence of the words between two symbols
	 * anymore. This method also removes the specified starting and ending symbols. 
	 */
	public static String removeWordBetweenTwoSymbols( String baseStr, String startSymb, String endSymb){ 
		String toRemove = getWordBetweenSymbols( baseStr, startSymb, endSymb);
		return  baseStr.replace( startSymb + toRemove + endSymb, DEFAULT_REMOVING_REPLACE);
	}
	/**
	 * This method calls: {@link #removeWordBetweenTwoSymbols(String, String, String)}
	 * with: {@code baseStr}, {@value #DEFAULT_DECLARATION_OPEN} and {@value #DEFAULT_DECLARATION_CLOSE} input parameters respectively.
	 * @param baseStr the string from where remove the set of chars between symbols.
	 * @return  a copy of {@code baseStr} that does not have the first occurrence of the words between two symbols
	 * anymore (symbols: {@value #DEFAULT_DECLARATION_OPEN} and {@value #DEFAULT_DECLARATION_CLOSE}). 
	 * This method also removes the specified starting and ending symbols. 
	 */
	public static String removeDeclaration( String baseStr){
		return removeWordBetweenTwoSymbols( baseStr, DEFAULT_DECLARATION_OPEN, DEFAULT_DECLARATION_CLOSE);
	}
	
	// returns the string contained between the frist and last apex (in:" a"b ", out: a"b )
	/**
	 * This method calls: {@link #getWordBetweenSymbols(String, String, String, Boolean)} with:
	 * {@code baseStr}, {@value #DEFAULT_STRING_IDENTIFIER}, {@value #DEFAULT_STRING_IDENTIFIER} and {@code true}; respectively.
	 * Finally, it removes all the occurrences of {@code {@value #DEFAULT_STRING_IDENTIFIER}{@value #DEFAULT_STRING_IDENTIFIER}}
	 * with: {@code {@value #DEFAULT_STRING_IDENTIFIER}}. 
	 * @param baseStr the string from where retrieving the string literal.
	 * @return the last set of chars of {@code baseStr} within the symbol {@value #DEFAULT_STRING_IDENTIFIER}; 
	 * where possible double occurrences of {@code {@value #DEFAULT_STRING_IDENTIFIER} + {@value #DEFAULT_STRING_IDENTIFIER}} are 
 	 * substituted with: {@code {@value #DEFAULT_STRING_IDENTIFIER}}.  
	 */
	public static String retrieveString( String baseStr){
		String out = getWordBetweenSymbols( baseStr, DEFAULT_STRING_IDENTIFIER, DEFAULT_STRING_IDENTIFIER, true);
		return out.replaceAll( DEFAULT_STRING_IDENTIFIER + DEFAULT_STRING_IDENTIFIER, DEFAULT_STRING_IDENTIFIER);
	}	
	/**
	 * This method calls: {@link #getWordBetweenSymbols(String, String, String)} with:
	 * {@code baseStr}, {@value #DEFAULT_DECLARATION_OPEN}, {@value #DEFAULT_DECLARATION_CLOSE}; respectively.
	 * @param baseStr the string from where retrieving the declaration literal.
	 * @return the first set of chars of {@code baseStr} within the symbols:
	 * {@value #DEFAULT_DECLARATION_OPEN} and {@value #DEFAULT_DECLARATION_CLOSE}.
	 */
	public static String retrieveDeclaration( String baseStr){
		return getWordBetweenSymbols( baseStr, DEFAULT_DECLARATION_OPEN, DEFAULT_DECLARATION_CLOSE);
	}
	/**
	 * This method looks into the string {@link ParserRuleContext#getText()} of all the children
	 * of the input parameter {@code ctx} and returns it (first occurence) if it not equals to:
	 * {@value #DEFAULT_BEGINNING}, {@code declaration}, {@value #DEFAULT_OPEN} and {@value #DEFAULT_CLOSE}.
	 * Otherwise it returns an empty string.  
	 * @param ctx the parsing tree context from which get child and text to check.
	 * @param declaration an optional child content text to be discarder.
	 * @return the first text of the child of {@code ctx} that it is not equal to the standard grammar
	 * declarative literals. 
	 */
	public static String retrieveDirectiveContent( ParserRuleContext ctx, String declaration){
		for( int i = 0; i < ctx.getChildCount(); i++){
			String childTxt = ctx.getChild(i).getText();
			if( 	( ! childTxt.equals( DEFAULT_BEGINNING)) 	&& // discart constant tokens (ex:!label( "...", "...", "...")) 
					( ! childTxt.equals( declaration))			&& 
					( ! childTxt.equals( DEFAULT_OPEN)) 		&& 
					( ! childTxt.equals( DEFAULT_CLOSE))		){
				return childTxt; // only one number is allow in !id() directive
			}
		}
		return "";
	}
	/**
	 * This method looks into the string {@link ParserRuleContext#getText()} of all the children
	 * of the input parameter {@code ctx} and returns it for all the occurences in which it  is not equals to:
	 * {@value #DEFAULT_BEGINNING}, {@code declaration}, {@value #DEFAULT_OPEN}, {@value #DEFAULT_SEPARATOR} and {@value #DEFAULT_CLOSE}.
	 * Otherwise it returns an empty list.  
	 * @param ctx the parsing tree context from which get child and text to check.
	 * @param declaration an optional child content text to be discarder.
	 * @return the all texts of the children of {@code ctx} that it are not equal to the standard grammar
	 * declarative literals. 
	 */
	public static List< String> retrieveDirectiveContents( ParserRuleContext ctx, String declaration){
		List< String> out = new ArrayList< String>();
		for( int i = 0; i < ctx.getChildCount(); i++){
			String childTxt = ctx.getChild(i).getText();
			if( 	( ! childTxt.equals( DEFAULT_BEGINNING)) 	&& // discart constant tokens (ex:!label( "...", "...", "...")) 
					( ! childTxt.equals( declaration))			&& 
					( ! childTxt.equals( DEFAULT_OPEN)) 		&&
					( ! childTxt.equals( DEFAULT_SEPARATOR))	&&
					( ! childTxt.equals( DEFAULT_CLOSE))		){
				out.add( childTxt); // more than one member
			}
		}
		return out;
	}
}
