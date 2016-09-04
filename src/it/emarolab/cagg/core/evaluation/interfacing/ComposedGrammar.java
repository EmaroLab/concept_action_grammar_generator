package it.emarolab.cagg.core.evaluation.interfacing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SyntaxActionTag;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree.SemanticNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarCreatorBase;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode.Type;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

/* ##################################################################################
   ######################### UTILITY CLASS ##########################################
   this class is used to create the the grammar class (this) which should be only a 
   container of fields (with setters and getters) since it will be serialised. 
   In order to successfully use this instantiation procedure set the constructor of
   the grammar class as in this file.													*/
class ComposedGrammarCreator extends GrammarCreatorBase< ComposedGrammar>{
	/* ##################################################################################
   	   ###################### DEFAULT CONSTRUCTOR #######################################
	 */
	public ComposedGrammarCreator(List<TextualParser> parsers) {
		super(parsers);
	}
	private ComposedGrammarCreator(List<TextualParser> parsers,  Type[] mainPreambleTypes,Type[] auxiliaryPreambleTypes) {
		super(parsers, mainPreambleTypes, auxiliaryPreambleTypes); // populateOnCostructor flag;
	}

	/* ##################################################################################
	   ############# GRAMMAR FIELD INITIALISATION ALGORITHMS ############################
	 */
	@Override
	protected ComposedGrammar populateGrammar() {
		// create an empty grammar container (defauined below)
		// with all fields not initialised. call setters for all of them
		ComposedGrammar grammar = new ComposedGrammar( true);
		// default creators
		grammar.setPreambleMap( this.createSyntaxPreambleMap());
		grammar.setBodyMap( this.createSyntaxBodyMap());
		grammar.setNotEvaluableWords( this.getNotEvaluableWorlds());
		// Instantiate the grammar expression parameters (e.g. in this case SemanticExpression object)
		grammar.setSemanticExpression( createSemanticExpression());
		// Instantiate the map to collect all the term of the semantic expression
		grammar.setExpressionTermMap( createExpressionTermMap( grammar.getSemanticExpression()));
		return grammar;
	}
	private SemanticTree createSemanticExpression(){
		// compose main expression
		ExpressionNode<?> mainExpression = this.getMainPrimitive().getMainRuleExpressionTree();
		ExpressionNode<?>.ComposedRule composedExpression = mainExpression.composeSubRule( getAllPrimitive());
		// get the tag (you may want to change its implementing type)
		ActionTagBase actions = new SyntaxActionTag( composedExpression);
		// create semantic tree
		SemanticTree semantic = new SemanticTree( composedExpression.getRoot(), actions);
		return semantic;
	}
	private Map<Long, SemanticNode> createExpressionTermMap( SemanticTree expression) {
		Map<Long, SemanticNode> out = new LinkedHashMap<>();
		Map<Long, BaseNode> expressionMap = expression.getRoot().toMap();
		for( BaseNode node : expressionMap.values()){
			if( node instanceof SemanticNode){
				SemanticNode semanticNode = ( SemanticNode) node;
				if( semanticNode.isTerm())
					out.put( semanticNode.getId(), semanticNode);
			} else GrammarLog.error( "a semantic tree should have only nodes of the type SemanticNode. "
					+ "Instead, the type found for " + node + " is " + node.getClass().getSimpleName());
		}
		return out;
	}
}


/* 	##################################################################################
	############################ MAIN CLASS ##########################################
	this class describes all the semantic information of the grammar that is used 
	during evaluation and trigger firing. It is suppose to be only a container with
	getters and setters.
	Remember to extend a creator to instantiate it with algorithm that uses syntaxes to create
	a semantic expression and use the static constructor has shown below.	*/
@SuppressWarnings("serial")
public class ComposedGrammar extends GrammarBase< SemanticTree>{

	// TODO: remove serialisable from syntax trees !!!!!!!!!!!!!!!!!!!
	// TODO: remove evaluation procedure from syntax
	
	/* ##################################################################################
	   ####################### STATIC CONSTRUCTOR #######################################
	 do not use constructor by creator instead. this is done to differenciate from data 
	 to be serialised and algorithms to compute them care about performances.				*/
	protected ComposedGrammar (){
		GrammarLog.error( " do not instanciate a GrammarBase object by \"new\" use GrammarCreateBase interface instead.");
	}
	public ComposedGrammar(Boolean suppressWarning) {
		if( ! suppressWarning)
			GrammarLog.error( " do not instanciate a GrammarBase object by \"new\" use GrammarCreateBase interface instead.");
	}
	public static ComposedGrammar createGrammar( TextualParser parsers){
		List<TextualParser> in = new ArrayList<>();
		in.add( parsers);
		return createGrammar( in);
	}
	public static ComposedGrammar createGrammar( List<TextualParser> parsers){
		try{
			ComposedGrammarCreator creator = new ComposedGrammarCreator( parsers);
			ComposedGrammar thisInstance = creator.getGrammar();
			return thisInstance;
		} catch( Exception e){
			GrammarLog.error( "Error on create composed grammar!");
			GrammarLog.error( e);
			return null;
		}
	}
	
	/* ##################################################################################
	   ####################### EXTRA GRAMMR FIELDS ######################################
	 add here private fields with setters and getters. Initialise them in the 
	 populateGrammar() method of the creator and you are ready to use them during 
	 evaluation, independently from deserialisation or source parsing. 						*/
	// ....
	
	/* ##################################################################################
	   #################### EXTRA COMPUTATIONAL METHODS #################################
	 add other computational methods to be used during evaluation. This implementation
	 should care about performances!														*/
	// ....
}
