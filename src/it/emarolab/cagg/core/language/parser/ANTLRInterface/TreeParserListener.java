package it.emarolab.cagg.core.language.parser.ANTLRInterface;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionTreeGenerator;

import javax.swing.JTree;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.parser.ANTLRInterface.TreeParserListener.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements the base object for parsing listener used in order to create the Abstract Syntax Tree (AST) 
 * from the Parsing Tree (PS) by extending the {@link LogicalParserListener}.<br>
 * 
 * It main purpose is to initialise this class and provides method for easy AST management and creation. Particularly for its main
 * features as, for example: the preamble node, the body node and basic actions to generate Expression Trees (ET).
 * </p>
 *
 * @see LogicalParserListener
 * @see ParserBase
 * @see ExpressionTreeGenerator
 * @see AbstractSyntaxNode
 */
public class TreeParserListener extends LogicalParserListener{

	// ########################################################################################################################
	// Attributes (they have getter methods) ##################################################################################
	// global class variables
	private AbstractSyntaxNode<?> astRoot = null, astPreamble = null, astBody = null;
	private CAGGSyntaxDefinitionParser parser = null;
	private ExpressionTreeGenerator etGenerator;

	// ########################################################################################################################
	// Constructors ###########################################################################################################
	/**
	 * This constructor instantiates this object without attach to it any AST structure. 
	 * To initialise a new AST structure remember to call: {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}.
	 * @param parser the ANTLR generated class for parsing the source code.
	 */
	public TreeParserListener(  CAGGSyntaxDefinitionParser parser){ // empty constructor, you must to call setBaseNodes() to make this class working !!!!
		this.parser = parser;
		ParserLog.info( "LogicalListener instanciate with empty construct. Be sure to call setBaseNode() method befoure to do other operations.");
	} 
	/**
	 * This constructor instantiates this object by attaching to it an AST structure. 
	 * @param parser the ANTLR generated class for parsing the source code.
	 * @param astRoot the root node of the generated AST. 
	 * @param astPreamble the main preamble node of the generated AST.
	 * @param astBody the main body node of the generated AST.
	 */
	public TreeParserListener( CAGGSyntaxDefinitionParser parser, AbstractSyntaxNode<?> astRoot, AbstractSyntaxNode<?>  astPreamble, AbstractSyntaxNode<?>  astBody){
		this.parser = parser;
		this.setBaseNodes( astRoot, astPreamble, astBody);
	}
	// initialisation
	public void setBaseNodes( AbstractSyntaxNode<?> astRoot, AbstractSyntaxNode<?>  astPreamble, AbstractSyntaxNode<?> astBody){
		this.astRoot = astRoot;				// AST tree root node
		this.astPreamble = astPreamble;  	// AST sub tree roots node (grammar preamble)
		astRoot.add( astPreamble);			// add preamble to the root
		this.astBody = astBody;				// AST sub tree roots node (grammar body)
		astRoot.add( astBody);				// add body to the root
	}

	// ########################################################################################################################
	// Getters and tree manipulation methods ##################################################################################
	/**
	 * @return the generated AST as a {@code JTree}. Mainly used for tree visualisation.
	 */
	public JTree getAbstractSyntaxTree(){				// get completed AST (get root as JTree)
		return new JTree( getAstRoot().copyTree());// false, true));
	}
	/**
	 * Returns the same object used in {@link #TreeParserListener(CAGGSyntaxDefinitionParser, AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}
	 * or in {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}.
	 * @return the root node of the generated AST.
	 */
	public AbstractSyntaxNode<?> getAstRoot(){			// get main AST nodes (root)
		return this.astRoot;
	}
	/**
	 * Returns the same object used in {@link #TreeParserListener(CAGGSyntaxDefinitionParser, AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}
	 * or in {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}.
	 * @return the main preamble node of the generated AST.
	 */
	public AbstractSyntaxNode<?> getAstPreamble(){		// get main AST nodes (preamble)
		return this.astPreamble;
	}
	/**
	 * Returns the same object used in {@link #TreeParserListener(CAGGSyntaxDefinitionParser, AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}
	 * or in {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}.
	 * @return the main body node of the generated AST.
	 */
	public AbstractSyntaxNode<?> getAstBody(){			// get main AST nodes (body)
		return this.astBody;
	}
	/**
	 * Returns the same object used in {@link #TreeParserListener(CAGGSyntaxDefinitionParser, AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}
	 * or in {@link #setBaseNodes(AbstractSyntaxNode, AbstractSyntaxNode, AbstractSyntaxNode)}.
	 * @return the ANTLR parser object.
	 */
	public CAGGSyntaxDefinitionParser getParser(){				// get nuance parser (ANTLR)
		return this.parser;
	}
	
	/**
	 * This method is used to add a new node to the AST during its generation.
	 * It automatically understands from the definition of the grammar in which main node (root, preamble or body) the {@code newChild} should be added.
	 * More in particular, if a node is added through this method when {@link #isInGrammarPreamble()} is {@code true} then, {@code newChild} is added to
	 * the AST preamble node. Otherwise, if {@link #isInGrammarBody()} is {@code true} then, it is added to the AST body node. Else, 
	 * if {@link #isInGrammarRoot()} is {@code true}, then, {@code newChild} is added to the AST root node.
	 * @param newChild the new node to be added to the AST.
	 */
	protected void addNode( AbstractSyntaxNode<?> newChild){	// add a node to the Asbract Syntax Tree (AST)
		if( this.isInGrammarPreamble())							// thanks to class flag it automatically ad it to one of the main node (root, preamble or body)
			astPreamble.add( newChild);
		else if( this.isInGrammarBody())
			astBody.add( newChild);
		else if( this.isInGrammarRoot()){
			astRoot.add( newChild);
		}else ParserLog.warning( "try add node [" + newChild + "] failure!!!! "
				+ "isInFlags:{ROOT:" + this.isInGrammarRoot() + ", PREAMBLE:" + this.isInGrammarPreamble() + ", BODY:" + this.isInGrammarBody() + "}.");
	}
	
	// interface to easy propagate class ExpressionTreeGenerator
	/**
	 * This method is used to initialise a new ET to be generated during parsing.
	 * The returning instance is also internally stored in this class and used when calling:
	 * {@link #addExpressionOperation(ExpressionNode)} and {@link #addExpressionTerm(ExpressionNode)}.
	 * Remember to call {@link #getExpressionTree()} before to call this method 
	 * if you do not want to override the previous generate ET.
	 * @return the new ET to be populated during parsing. 
	 */
	protected ExpressionTreeGenerator newExpressionGenerator(){
		etGenerator = new ExpressionTreeGenerator();
		return etGenerator;
	}
	/**
	 * This method adds a new operation node to the internal ET. This operation uses the queue implemented
	 * in {@link ExpressionTreeGenerator} in order to generate the ET from the PT.
	 * It is important to note that if {@link #newExpressionGenerator()} is called, then all the operation
	 * node added to this object are deleted.
	 * @param e the operation node to be added to the ET.
	 */
	protected void addExpressionOperation(ExpressionNode<?> e){
		etGenerator.addOperation(e);
	}
	/**
	 * This method adds a new term node to the internal ET. This operation uses the queue implemented
	 * in {@link ExpressionTreeGenerator} in order to generate the ET from the PT.
	 * It is important to note that if {@link #newExpressionGenerator()} is called, then all the term
	 * node added to this object are deleted.
	 * @param e the term node to be added to the ET.
	 */
	protected void addExpressionTerm(ExpressionNode<?> e){
		etGenerator.addTerm(e);
	}
	/**
	 * This method is used to generate the ET and return its root node. 
	 * It is based on {@link #newExpressionGenerator()}, {@link #addExpressionOperation(ExpressionNode)}
	 * and {@link #addExpressionTerm(ExpressionNode)}. 
	 * @return the root node of the ET generated from the PT.
	 */
	protected ExpressionNode<?> getExpressionTree(){
		return etGenerator.getExpressionTree();
	}
}