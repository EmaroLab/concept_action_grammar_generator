package it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler;

import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.GrammarPrimitive;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode.Type;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract public class GrammarCreatorBase<G extends GrammarBase< ?>> {

	/* ##################################################################################
	   ############################ CONSTANTS ###########################################
	   used on constructor */
	// the default set of acceptable preamble node type for the main grammar
	public static final Type[] MAIN_PREAMBLE_TYPE = { 
		AbstractSyntaxNode.Type.ACTIVABLE, 
		AbstractSyntaxNode.Type.GRAMMAR_DECLARATION, 
		AbstractSyntaxNode.Type.HEADER,
		AbstractSyntaxNode.Type.LANGUAGE, 
		AbstractSyntaxNode.Type.MODIFIABLE, 
		AbstractSyntaxNode.Type.PRONOUNCE_PREAMBLE,
		AbstractSyntaxNode.Type.START
	};
	// the default set of acceptable preamble node type for the auxiliary grammars
	public static final Type[] AUXILIARY_PREAMBLE_TYPE = { 
		AbstractSyntaxNode.Type.ACTIVABLE, 
		AbstractSyntaxNode.Type.GRAMMAR_DECLARATION, 
		AbstractSyntaxNode.Type.LANGUAGE, 
		AbstractSyntaxNode.Type.MODIFIABLE, 
		AbstractSyntaxNode.Type.PRONOUNCE_PREAMBLE, 
		AbstractSyntaxNode.Type.START
	};

	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	// the output of this class
	private G grammar;
	// the input of this class
	private List<TextualParser> parsers;
	// base information from syntax language parsing (and the type of information allowable)
	private GrammarPrimitive mainPrimitive;
	private List< GrammarPrimitive> auxiliaryPrimitive = new ArrayList< GrammarPrimitive>();;
	private Set<Type> mainPreambleTypes;
	private Set<Type> auxiliaryPreambleTypes;

	/* ##################################################################################
	   ########################### CONSTRUCTOR ##########################################
	 */
	public GrammarCreatorBase( List< TextualParser> parsers){
		initialise( parsers, MAIN_PREAMBLE_TYPE, AUXILIARY_PREAMBLE_TYPE);
	}
	public GrammarCreatorBase( List< TextualParser> parsers, 
			Type[] mainPreambleTypes, Type[] auxiliaryPreambleTypes){
		initialise( parsers, mainPreambleTypes, auxiliaryPreambleTypes);
	}
	// common method used from all constructors
	private void initialise( List< TextualParser> parsers, 
			AbstractSyntaxNode.Type[] mainPreambleTypes, AbstractSyntaxNode.Type[] auxiliaryPreambleTypes){
		this.mainPreambleTypes = getMainPreambleASNTypes( mainPreambleTypes);					
		this.auxiliaryPreambleTypes = getAuxiliaryPreambleASNTypes( auxiliaryPreambleTypes); 
		this.parsers = parsers;
		setPrimitives(); // requires fields: mainPreambleType, auxiliaryPreambleType and parses already initialised
		performPreProcessing();
		this.grammar = populateGrammar();
		performPostProcessing();
	}
	// set the type of node allowable in the main grammar preamble 
	private Set< AbstractSyntaxNode.Type> getMainPreambleASNTypes( AbstractSyntaxNode.Type[] types){
		Set< AbstractSyntaxNode.Type> mainPreambleTypes = new LinkedHashSet< AbstractSyntaxNode.Type>();
		for( int i = 0; i < types.length; i++)
			mainPreambleTypes.add( types[ i]);
		return mainPreambleTypes;
	}
	// set the type of node allowable in the auxiliary grammars preamble
	private Set< AbstractSyntaxNode.Type> getAuxiliaryPreambleASNTypes( AbstractSyntaxNode.Type[] types){
		Set< AbstractSyntaxNode.Type> auxiliaryPreambleTypes = new LinkedHashSet< AbstractSyntaxNode.Type>();
		for( int i = 0; i < types.length; i++)
			auxiliaryPreambleTypes.add( types[ i]);
		return auxiliaryPreambleTypes;
	}
	// initialise the only information that can be used to create the grammar starting from the source files
	private void setPrimitives(){
		// compute primitive objects
		List<GrammarPrimitive> allPrimitive = initialiseGrammarPrimitive( this.parsers); // create Grammar objects from parsers
		this.mainPrimitive = findMainGrammarPrimitive( allPrimitive); // find main Grammar (set internal primitive state)
		for( GrammarPrimitive g : allPrimitive)	 // create auxiliary grammar list, by collection all the others. It may remain empty
			if( ! g.equals( mainPrimitive))
				this.auxiliaryPrimitive.add( g);
		GrammarLog.info( "Intialise grammar primitives: (mainPrimitive:" + this.mainPrimitive + ") "
				+ "(auxiliaryPrimitives:" + this.auxiliaryPrimitive + ").");
	}
	// Instantiate Grammar Primitive for each parser (namely for each source file)
	private List< GrammarPrimitive> initialiseGrammarPrimitive( List<TextualParser> parsers) {	
		List< GrammarPrimitive> primitives = new ArrayList< GrammarPrimitive>();
		for( TextualParser p : parsers)
			primitives.add( new GrammarPrimitive( p));
		return primitives;
	}
	// find the grammar with !start directive (the first occurrence)
	private GrammarPrimitive findMainGrammarPrimitive( List< GrammarPrimitive> grammars){						
		for( GrammarPrimitive g : grammars){
			g.searchMainRuleName(); // update internal g state
			if( g.isMainGrammar()){
				GrammarLog.info( "Main rule found <" + g.getMainRuleName() + "> in " + g + ". Stop searching !!!!");
				return g;
			}
		}
		GrammarLog.error( "No grammar with START directive found !!");
		return null;
	}
	// call the doPreprocessing method for all the ET nodse in all the rules of all the grammars
	// it does not assure any preprocessing execution ordering.
	// needs to initialise all GrammarPrimitives for all parsers
	protected void performPreProcessing(){
		new PrimitiveAnalyzer(getMainPrimitive(), getAuxiliaryPrimitive()){
			@Override
			public Boolean work(GrammarPrimitive p) {
				Map<Long, RuleSyntaxNode> ruleMap = p.getBodyMap();	
				for( RuleSyntaxNode rule : ruleMap.values()){			// for each node in the AST body (rules)
					Map<Long, ?> et = rule.getExpressionTree().toMap();
					for( Object obj : et.values()){						// for each node of the ET
						if( obj instanceof ExpressionNode<?>){
							ExpressionNode<?> en = ( ExpressionNode<?>) obj;
							en.doPreProcessing( rule, getAllPrimitive()); 
							GrammarLog.info( "perform pre-processing for Expression node: " + en + " of the Syntax rule: " + rule);
						} else GrammarLog.warning( " error on pre-processing. ET cointens a node not of the type ExpressionNode<?>. It is of " + rule.getClass().getSimpleName() + "instenad");
					}
				}
				return true; // continues up to the end
			}
		};
	}
	protected List< GrammarPrimitive> getAllPrimitive(){
		List< GrammarPrimitive> out = new ArrayList<>();
		out.add( getMainPrimitive());
		out.addAll( getAuxiliaryPrimitive());
		return out;
	}
	protected void performPostProcessing(){
		new PrimitiveAnalyzer(getMainPrimitive(), getAuxiliaryPrimitive()){
			@Override
			public Boolean work(GrammarPrimitive p) {
				Map<Long, RuleSyntaxNode> ruleMap = p.getBodyMap();	
				for( RuleSyntaxNode rule : ruleMap.values()){			// for each node in the AST body (rules)
					Map<Long, ?> et = rule.getExpressionTree().toMap();
					for( Object obj : et.values()){						// for each node of the ET
						if( obj instanceof ExpressionNode<?>){
							ExpressionNode<?> en = ( ExpressionNode<?>) obj;
							en.doPostProcessing( rule, grammar); 
							GrammarLog.info( "perform post-processing for Expression node: " + en + " of the Syntax rule: " + rule);
						} else GrammarLog.warning( " error on post-processing. ET cointens a node not of the type ExpressionNode<?>. It is of " + rule.getClass().getSimpleName() + "instenad");
					}
				}
				return true; // continues up to the end
			}
		};
	}

	/* ##################################################################################
	   ############################# ABSTRACT ###########################################
	 */
	abstract protected G populateGrammar();

	/* ##################################################################################
	   ############################## GETTERS ###########################################
	 */
	public G getGrammar(){
		if( grammar == null)
			GrammarLog.error( this.getClass().getSimpleName() + " returns null grammar, are you sure that the method populateGrammar() is getting called?");
		return grammar;
	}
	public List<TextualParser> getParsers(){
		return parsers;
	}
	public Set<Type> getMainPreambleTypes(){
		return mainPreambleTypes;
	}
	public Set<Type> getAuxiliaryPreambleTypes(){
		return auxiliaryPreambleTypes;
	}
	public GrammarPrimitive getMainPrimitive() {
		return mainPrimitive;
	}
	public List<GrammarPrimitive> getAuxiliaryPrimitive() {
		return auxiliaryPrimitive;
	}

	/* ##################################################################################
	   ########################### DEFAULT CREATION #####################################
	 */
	// collects all the allowable node types in the preable of all the source files
	protected Map<Long, AbstractSyntaxNode<?>> createSyntaxPreambleMap(){
		// initialise the output
		final Map< Long, AbstractSyntaxNode<?>> preambleMap = new LinkedHashMap< Long, AbstractSyntaxNode<?>>();
		// call looper for all grammar primitive
		new PrimitiveAnalyzer( this) {
			@Override
			public Boolean work(GrammarPrimitive primitive) { // it gets called for all grammar primitive
				// get the preamble map for the actual primitive
				Map<Long, AbstractSyntaxNode<?>> primitiveMap = primitive.getPreambleMap();
				// get the allowable primitive types w.r.t. main or auxiliarly grammars
				Set< Type> allowableTypes;
				if( this.isEvaluatingMainPrimitive())
					allowableTypes = getMainPreambleTypes();
				else allowableTypes = getAuxiliaryPreambleTypes();
				// save in the preamble map the allowable types
				for( AbstractSyntaxNode<?> node : primitiveMap.values()){
					if( allowableTypes.contains( node.getType()))
						preambleMap.put( node.getId(), node);
				}
				return true; // do for all primitive
			}
		};
		return preambleMap;
	}
	// collects all the node in the body of all the source files
	protected Map<Long, RuleSyntaxNode> createSyntaxBodyMap(){
		final Map< Long, RuleSyntaxNode> bodyMap = new LinkedHashMap< Long, RuleSyntaxNode>();
		new PrimitiveAnalyzer( this) {
			@Override
			public Boolean work(GrammarPrimitive primitive) {  // it gets called for all grammar primitive
				// get the body map for the actual primitive
				Map<Long, RuleSyntaxNode> primitiveMap = primitive.getBodyMap();
				// save in the body map all the rules
				for( RuleSyntaxNode node : primitiveMap.values())
					bodyMap.put( node.getId(), node);
				return true; // do for all primitive
			}
		};
		return bodyMap;
	}
	// get all the words specified with '^'
	protected Set< String> getNotEvaluableWorlds(){
		return TermExpressionNode.getNotEvaluatedTerms();
	}

	/* ##################################################################################
	   ########################## UTILITY CLASS #########################################
	   this class is used to search for something in the Grammar Primitives parsed from
	   source file. Extend this class with a new one. Implement than the work() method
	   and will have access to the main primitive first and auxiliary primitives later.
	   Stopping condition can be controlled by the returning value of work().*/
	public abstract class PrimitiveAnalyzer{
		// fileds
		private GrammarPrimitive mainPrimitive;					
		private List< GrammarPrimitive> auxiliaryPrimitives;
		// the parameter that is going to be evaluated. used in shouldEnd().
		protected GrammarPrimitive actualEvaluating = null;  
		// constructor
		protected PrimitiveAnalyzer( GrammarCreatorBase<G> creator) {
			this.mainPrimitive = creator.getMainPrimitive();
			this.auxiliaryPrimitives = creator.getAuxiliaryPrimitive();
			this.forAllPrimitive();
		}	
		protected PrimitiveAnalyzer( GrammarPrimitive mainPrimitive, List< GrammarPrimitive> auxiliaryPrimitives) {
			this.mainPrimitive = mainPrimitive;
			this.auxiliaryPrimitives = auxiliaryPrimitives;
			this.forAllPrimitive();
		}
		protected PrimitiveAnalyzer( List< GrammarPrimitive> allPrimitives) {
			this.mainPrimitive = null;
			this.auxiliaryPrimitives = allPrimitives;
			this.forAllPrimitive();
		}
		// all primitive scanning philosophy 
		public void forAllPrimitive(){
			// work with main primitive
			if( mainPrimitive != null){
				this.actualEvaluating = mainPrimitive;
				if( shouldEnd()) // actualEvaluating must be update
					return;
			}else GrammarLog.warning( this.getClass().getCanonicalName() + " cannot work with null main primitive!");
			// work with auxilary primitive
			if( auxiliaryPrimitive != null)
				for( GrammarPrimitive g : this.auxiliaryPrimitives){
					this.actualEvaluating = g;
					if( shouldEnd()) // actualEvaluating must be update
						return;
				}
			else GrammarLog.error( this.getClass().getCanonicalName() + " cannot work with null auxiliary primitives list!");
		}
		// trigger work() and return true if the loop must be stopped.
		// it should use actualEvaluating field
		protected Boolean shouldEnd(){
			return ! work( actualEvaluating);
		}
		// do your work with another primitive and return false when it has been done.
		abstract public Boolean work( GrammarPrimitive primitive);
		// returns true if the actualEvaluating primitive is the main primitive
		protected boolean isEvaluatingMainPrimitive() {
			return this.actualEvaluating.isMainGrammar();
		}
	}
}
