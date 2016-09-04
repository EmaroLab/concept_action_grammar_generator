package it.emarolab.cagg.core.language.parser.ANTLRInterface;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.ParserListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionBaseListener;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionLexer;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.NuanceGrammarContext;
import it.emarolab.cagg.debugging.DebuggingDefaults;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.gui.TreeViewer;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.parser.ANTLRInterface.ParserBase.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements a base structure to parse a source code in order to generate the Parsing Tree (PT);
 * where the source text can be given as a String as well as a File (by specifying its directory path).
 * Also, it is possible to connect to this class a Listener in order to walk through the PT and create
 * (by node entrance/exit listening) the Abstract Syntax Tree (AST) (which contains also Expression Trees (ET)).
 * More in particular, the class is designed in order to parse the file on constructor and so create the PT.
 * Then, it uses the Listener (that can be also set through {@link #setListener(ParseTreeListener)} or {@link #setListener(ParseTreeListener, ParserRuleContext)})
 * in order to walk trough the PT and create the AST.<br>
 * 
 * Moreover, this class must be based on the classes created by the ANTLR library, those are collected in the package:
 * {@code dotVocal.nuanceGoogleIntegration.semanticParser.parser.contentsParser.ANTLRInterface.ANTLRGenerated}.
 * In which are contained the base class types for all the parameters: {@code GLexer}, {@code GParser}, {@code GContext} and {@code GListener}.
 * Furthermore, consider that those objects are generated starting from an ANTLR grammar definition located in this 
 * project in the folder: {@code ../files/parserGrammar/NUanceGrammar.g4} .<br>
 * 
 * Last but not the least, this class can be also used in order to show a simple GUI visualisation of the PS; 
 * which is completely independent from the GUI defined within the packages:
 * {@code dotVocal.nuanceGoogleIntegration.semanticParser.debuggingGui}
 * </p>
 *
 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionLexer
 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser
 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionBaseListener
 * @see it.emarolab.cagg.core.language.parser.TextualParser
 * @see it.emarolab.cagg.core.language.parser.ParserListener
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see Lexer
 * @see Parser
 * @see ParserRuleContext
 * @see ParseTreeListener
 * @see ParserBase.ParsedTreeVisualizer
 * @see CAGGSyntaxDefinitionLexer
 * @see CAGGSyntaxDefinitionParser
 * @see CAGGSyntaxDefinitionBaseListener
 * @see NuanceGrammarContext
 * @see ParserListener
 * 
 * @param <GLexer> this is the Lexer used by the ANTL library to parse the source text. It must extends the {@link Lexer} class.
 * Typically, for this system, it would be the {@link CAGGSyntaxDefinitionLexer} or an extension. 
 * @param <GParser> this is the Parser used by the ANTL library to parse the source text. It must extends the {@link Parser} class.
 * Typically, for this system, it would be the {@link CAGGSyntaxDefinitionParser} or an extension.
 * @param <GContext> this is the rule context of the grammar from which this object start parsing. It must extends the {@link ParserRuleContext} class.
 * Typically, for this system, this is the main rule defined in the ANTL grammar. 
 * @param <GListener> this is the class that by listening the creation of the PS creates and populates the AST. It must extends the {@link ParseTreeListener} class.
 * Typically, for this system, it would be the {@link ParserListener} or an extension.
 */
public abstract class ParserBase< 	GLexer extends Lexer, GParser extends Parser, 
							GContext extends ParserRuleContext, GListener extends ParseTreeListener> {

	/**
	 * The default window title used for visualise the Parsing Tree (PS). Value: {@code "Parsing Tree"}.
	 */
	public static final String DEFAULT_TITLE = "Parsing Tree"; 

	// ########################################################################################################################
	// Attributes (they have getter methods) ##################################################################################
	private GParser parser = null;
	private GContext tree = null;
	private GListener listener = null;
	
	// ########################################################################################################################
	// Constructors ###########################################################################################################
	/**
	 * It initialises this object in order to parse a source from string.
	 * Moreover, it does not initialise any parsing tree listener into this object.
	 * To node that this object is designed to perform parsing the source on constructor
	 * and the listener can be added later through the method {@link #setListener(ParseTreeListener)}.
	 * @param toParse the source text to be parsed.
	 */
	public ParserBase( String toParse){											// parse from string (default fromFile=false)
		initialise( null, toParse, false);
	}
	/**
	 * It initialises this object in order to parse a source from string.
	 * Moreover, it initialises the parsing tree listener into this object.
	 * To node that this object is designed to perform parsing the source on constructor
	 * and the listener can be added later through the method {@link #setListener(ParseTreeListener)}.
	 * @param listener the object that listens the parsing tree generation and creates the AST and ETs.
	 * @param toParse the source text to be parsed.
	 */
	public ParserBase( GListener listener, String toParse){						// parse from string (default fromFile=false)
		initialise( listener, toParse, false);
	}
	/**
	 * It initialises this object in order to parse a source from string or file.
	 * Moreover, it does not initialise any parsing tree listener into this object.
	 * To node that this object is designed to perform parsing the source on constructor
	 * and the listener can be added later through the method {@link #setListener(ParseTreeListener)}.
	 * @param toParse the directory path to the file to be parsed or directly the source text to be parsed 
	 * (with respect to the input flag {@code fromFile}).
	 * @param fromFile set to {@code true} if the String {@code toParse} identifies a file path to be parsed. 
	 * Otherwise, set to {@code false} if the String {@code toParse} identifies the actual text to be parsed.
	 */
	public ParserBase( String toParse, Boolean fromFile){						// parse from filePath (if fromFile=true) or from string (if fromFile=false)
		initialise( null, toParse, fromFile);
	}
	/**
	 * It initialises this object in order to parse a source from string or file.
	 * Moreover, it initialises the parsing tree listener into this object. 
	 * To node that this object is designed to perform parsing the source on constructor
	 * and the listener can be added later through the method {@link #setListener(ParseTreeListener)}.
	 * @param listener the object that listens the parsing tree generation and creates the AST and ETs.
	 * @param toParse the directory path to the file to be parsed or directly the source text to be parsed 
	 * (with respect to the input flag {@code fromFile}).
	 * @param fromFile set to {@code true} if the String {@code toParse} identifies a file path to be parsed. 
	 * Otherwise, set to {@code false} if the String {@code toParse} identifies the actual text to be parsed.
	 */
	public ParserBase( GListener listener, String toParse, Boolean fromFile){	// parse from filePath (if fromFile=true) or from string (if fromFile=false)
		initialise( listener, toParse, fromFile);
	}
	// common initialisation: everything is done in constructors, you may add listener later
	private void initialise( GListener listener, String toParse, Boolean fromFile){
		try{
			// call parser from file or string w.r.t. input specifications (it created parser and tree attributes)
			if( fromFile)							
				parser = prepareFromFile( toParse);
			else parser = prepareFromString( toParse);
			// do your work
			tree = parse();	
			// set the listener on constructor or do it later with setListener(...)
			if( listener != null)
				setListener( listener, tree);
			//else ParserLog.warning( " extention of ParserBase " + this + " initialised without listener!");
		} catch( Exception e){
			ParserLog.error( e);
		}	
	}
	
	/**
	 * This methods calls: {@link #setListener(ParseTreeListener, ParserRuleContext)}
	 * by using as input parameters: {@code listener} and {@link #getTree()}.
	 * @param listener the object that listens the parsing tree generation and creates the AST and ETs (to set).
	 */
	protected Boolean setListener( GListener listener){
		return this.setListener( listener, this.getTree());
	}
	/**
	 * This method add a listener object and walk through the Parsing Tree in order to fire 
	 * fire the listening methods and create from it the Abstract Syntax Tree and related Expression Trees.
	 * @param listener the object that listens the parsing tree generation and creates the AST and ETs (to set).
	 * @param tree the tree in which this method walk through for node entrance/exit listening.
	 * @return {@code true} if a parsing error occurs. {@code false} if not.
	 */
	protected Boolean setListener( GListener listener, GContext tree){
		// create and attach a listener to the parsing tree (the listener creates the Abstract Syntax Tree(AST))
	    ParseTreeWalker walker = new ParseTreeWalker();
	    this.listener = listener;
	    walker.walk( listener, tree);
		if( parser.getNumberOfSyntaxErrors() > 0 ){
			ParserLog.error( "file not parsed (parsing error)");
			return true;
			//System.exit(1);
		}
		return false;
	}
	
	// ########################################################################################################################
	// ABSTRACT METHODS #######################################################################################################
	// for initialisation with respect your grammar generated class (class parameters)
	/**
	 * This method is called on class constructor. It must return a new instance of the Lexer used from the ANTLR parsing library
	 * @param stream the input stream of the source code to be parsed (from file or string).
	 * @return the Lexer object used from this class to perform source parse on constructor.
	 */
	protected abstract GLexer instanciateLexer( ANTLRInputStream stream);
	/**
	 * This method is called on class constructor. It must return a new instance of the Parser used from the ANTLR parsing library
	 * @param lexer the input Lexer instanciated through {@link #instanciateLexer(ANTLRInputStream)}.
	 * @return the Parser object used from this class to perform source parse on constructor.
	 */
	protected abstract GParser instanciateParse( GLexer lexer);
	/**
	 * This method is called on class constructor and it purpose is to give an interface to ANTLR library.
	 * Particularly, it allow to specify the starting rule for parsing the grammar. 
	 * @param parser the parser from which get the parsing context of the starting rule for creating the Parsing Tree. 
	 * @return  the context of the starting rule from creating the Parsing Tree.
	 */
	protected abstract GContext startParser( GParser parser); // Specify starting grammar rule !!!!!!!!!	
	/**
	 * This is the method to describe this class through an instance. 
	 * To be used from extending class in order to have easy debugging visualisation on the GUI.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();
	
	
	// ########################################################################################################################
	// ANTLRL parser calls in constructor #####################################################################################
	// prepared on constructor (parse from file or string)
	private GParser prepareFromString(String toParseText) {
		Long initialTime = System.nanoTime();
		ParserLog.info( "Parsing structure prepared from string: " + toParseText.replace( DebuggingDefaults.SYS_LINE_SEPARATOR, "").replace("\t", "") + " ...");
		GLexer lexer = instanciateLexer( new ANTLRInputStream( toParseText));
		GParser parser = instanciateParse( lexer);
		ParserLog.info( "[time:" + (System.nanoTime() - initialTime) + " ns] end preparing structure.");
		return parser;
	}
	private GParser prepareFromFile(String toParseFilePath) throws IOException {
		Long initialTime = System.nanoTime();
		ParserLog.info( "Parsing structure prepared from file: " + toParseFilePath + " ...");
		GLexer lexer = instanciateLexer( new ANTLRFileStream( toParseFilePath));
		GParser parser = instanciateParse( lexer);
		ParserLog.info( "[time:" + (System.nanoTime() - initialTime) + " ns] end preparing structure.");
		return parser;
	}
	private GContext parse(){
		Long initialTime = System.nanoTime();
		GContext tree = startParser( getParser());
		ParserLog.info( "Content parsed in: " + (System.nanoTime() - initialTime) + "ns.");
		return tree;
	}
	
	// ########################################################################################################################
	//    GETTERS   ###########################################################################################################
	// getters may return null if there is parsing error
	/**
	 * @return the initialised parser. It returns null in case of parsing errors.
	 */
	public GParser getParser(){
		return this.parser;
	}
	/**
	 * @return the Parsing Tree initialised in constructor. It may be {@code null} in case of parsing errors.
	 */
	public GContext getTree(){
		return this.tree;
	}
	/**
	 * @return the object listening the parsing tree by walking through.  
	 */
	public GListener getListener(){
		return this.listener;
	}
	
	
	// ########################################################################################################################
	//    VISUALIZATION   #####################################################################################################
	/**
	 * Visualise the Parsing Tree through a dedicated Graphical User Interface.
	 * It calls {@link #visualizeTree(String)} with {@link #DEFAULT_TITLE} as input parameter. 
	 */
	public void visualizeTree(){
		visualizeTree( DEFAULT_TITLE);
	}
	/**
	 * Visualise the Parsing Tree through a dedicated Graphical User Interface.
	 * More in details, this basic GUI is implemented by: {@link ParserBase.ParsedTreeVisualizer}.
	 * @param frameTitle the name of the main windows in which the PS is visualised.
	 */
	public void visualizeTree( final String frameTitle){
		EventQueue.invokeLater(new CaggThread() {
			public void implement() {
				try {
					ParsedTreeVisualizer frame = new ParsedTreeVisualizer( frameTitle, tree, parser);
					frame.setVisible(true);
				} catch (Exception e) {
					ParserLog.error( e);
				}
			}
		});
	}
	
	// simple visualisation of the parsing tree as a new independent frame
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.parser.ANTLRInterface.ParserBase.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements a basic GUI to visualise just the resulting Parsing Tree
	 * in a dedicated window. 
	 * </p>
	 *
	 * @see ParserBase
	 */
	@SuppressWarnings("serial")
	private class ParsedTreeVisualizer extends JFrame{
		/**
		 * The scale factor of the parsing tree in its panel. Default value set to: 
		 * {@link DebuggingDefaults#TREE_SCALE_TYPE}
		 */
		public static final double TREE_SCALE_TYPE = DebuggingDefaults.TREE_SCALE_TYPE; 
		
		/**
		 * This constructor fully initialise all the visual components of this class. 
		 * @param title the title of the main window frame.
		 * @param tree the object containing the ANTLR Parsing Tree.
		 * @param parser the object describing the ANTLR parser.
		 */
		public ParsedTreeVisualizer( String title, GContext tree, GParser parser){
			// set frame
			setBounds(100, 100, 450, 300);
			setTitle( title);
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			// show tree
			if( tree != null && parser != null){
				 // create visualisation tree and show on windows
		        TreeViewer viewer = new TreeViewer( Arrays.asList( parser.getRuleNames()), tree);
				viewer.setScale( TREE_SCALE_TYPE);//scale a little
				JScrollPane scroll = new JScrollPane( viewer);
				contentPane.add(scroll);
			} else ParserLog.error( "BASE. Cannot visualize parsing tree (null tree or parser) !!!");
		}
	}
}
