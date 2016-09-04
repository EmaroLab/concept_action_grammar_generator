package it.emarolab.cagg.core.language.syntax;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

import java.util.LinkedHashMap;
import java.util.Map;

// ----------------------------- PRIMITIVE GRAMMAR CLASS (common features for every source files) --------------------------------
// data structure to manage grammar features (given from TextualParser and BaseNode)
/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.GrammarPrimitive.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class is used to fully describe the grammar obtained by parsing a source code.
 * Particularly, instances of this class are created during constructors of {@link GrammarCreator}.<br>
 * 
 * More in details, this grammar contains the preamble and body ASTs for a given source code,
 * as well as those nodes collected in an {@link java.util.HashMap} with node ID has identifier
 * (this is done by using: {@link it.emarolab.cagg.core.language.SyntaxNode#toMap()}).<br>
 * 
 * To note that the methods implemented from this class may be inefficient 
 * (also because their are designed to be used with the GUI). So, for a real usage of this sistem
 * it is suggest to serialise those results and use them directly without compute them all 
 * the times that a result should be evaluated. In order to do so, use: 
 * {@link GrammarCreator#serialise(String)}
 * </p>
 *
 * @see it.emarolab.cagg.core.language.parser.TextualParser
 * @see it.emarolab.cagg.core.language.SyntaxNode
 * @see GrammarCreator
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode
 */
public class GrammarPrimitive {
	////////////////////////////////////////	CLASS FIELDS
	// a copy of the preamble and body tree of the AST (copied to do not affect other visualisation of those tree in the GUI)
	private AbstractSyntaxNode<?> preambleTree, ruleTree; 
	// the preambleTree and ruleTree as map with ID as unique node identifier.
	private Map< Long, AbstractSyntaxNode<?>> preambleMap;
	private Map< Long, RuleSyntaxNode> ruleMap;
	private Map< Long, ExpressionNode<?>> expMap;
	// just used in toString method
	private String parserToString;
	// filled with a value if searchMainRuleName() [not called in constructor] have found that the preamble contsins !start <RuleName> directive
	private String mainRuleName = ""; // if it is empty then it is not the main grammar 

	////////////////////////////////////////	CLASS CONSTRUCTOR
	/**
	 * This method construct the preamble and body trees and maps from the input parameter.
	 * More in details, the preamble and body trees are given by calling:
	 * {@link it.emarolab.cagg.core.language.parser.TextualParser#getASPreambleTreeCopy()} and
	 * {@link it.emarolab.cagg.core.language.parser.TextualParser#getASBodyTreeCopy()}.
	 * Where a copy is done in order to do not modify all the visualisations of those trees in the GUI during evaluation and
	 * manipulation (even if this is not efficient).
	 * Finally, this constructors class {@link it.emarolab.cagg.core.language.SyntaxNode#toMap()} on
	 * those tree in order to fully initialise this class. 
	 * @param parser the source code parser from which this Grammar should be created.
	 */
	public GrammarPrimitive( TextualParser parser){
		parserToString = parser.toString();
		preambleTree = parser.getASPreambleTree();
		preambleMap = setPreambleMap( preambleTree);
		ruleTree = parser.getASBodyTree();
		ruleMap = setRuleMap( ruleTree);
		expMap = setExpressionsNodesMap( ruleMap);
	}
	// call tree.toMap() by parsing the elemnts in a map of (Long-AbstractSyntaxNode) rather than (Long-Object) has BaseNode.toMap() would do
	private Map< Long, AbstractSyntaxNode<?>> setPreambleMap( AbstractSyntaxNode<?> tree){
		Map<Long, AbstractSyntaxNode<?>> map = new LinkedHashMap< Long, AbstractSyntaxNode<?>>();
		Map<Long, ?> genericMap = tree.toMap();
		for( Long id : genericMap.keySet()){
			Object value = genericMap.get( id);
			if( value instanceof AbstractSyntaxNode<?>)
				map.put( id, (AbstractSyntaxNode<?>) value);
			else ParserLog.error( "Object in preamble map is an instance of" + value.getClass().getSimpleName() + ". NOT of AbstractSyntaxNode");
		}
		return map;
	}
	// call tree.toMap() by parsing the elemnts in a map of (Long-RuleSyntaxNode) rather than (Long-Object) has BaseNode.toMap() would do
	private Map< Long, RuleSyntaxNode> setRuleMap( AbstractSyntaxNode<?> tree){		
		Map<Long, RuleSyntaxNode> map = new LinkedHashMap< Long, RuleSyntaxNode>();
		Map<Long, ?> genericMap = tree.toMap();
		for( Long id : genericMap.keySet()){
			Object value = genericMap.get( id);
			if( value instanceof RuleSyntaxNode)
				map.put( id, (RuleSyntaxNode) value);
			else ParserLog.warning( "Object in rule map is an instance of " + value.getClass().getSimpleName() + ". NOT of RuleSyntaxNode");
		}
		return map;
	}
	// collects all the nodes of all the ETs in a source file
	private Map<Long, ExpressionNode<?>> setExpressionsNodesMap(Map<Long, RuleSyntaxNode> ruleMap){
		Map<Long, ExpressionNode<?>> out = new LinkedHashMap<Long, ExpressionNode<?>>();
		for( RuleSyntaxNode r : ruleMap.values()){ // for all rule
			Map<Long, ?> et = r.getExpressionTree().toMap(); // get expression tree
			for( Object en : et.values()){ // for all expression node in the the tree
				if( en instanceof ExpressionNode<?>){
					ExpressionNode<?> toAdd = (ExpressionNode<?>) en;
					out.put( toAdd.getId(), toAdd);
				} else ParserLog.warning( "Cannot add this primitive to node map becose it is of type " + en.getClass().getSimpleName() + " insted of ExpressionNode<?>");
			}
		}
		return out;
	}
	
	////////////////////////////////////////	GETTERS
	/**
	 * @return the preamble tree parsed from source and copied on constructors {@link GrammarCreator#GrammarCreator(List)}}. 
	 */
	public AbstractSyntaxNode<?> getPreambleTree() {
		return preambleTree;
	}
	/**
	 * @return the preamble map created on constructors {@link GrammarCreator#GrammarCreator(List)}}.
	 */
	public Map<Long, AbstractSyntaxNode<?>> getPreambleMap() {
		return preambleMap;
	}

	/**
	 * @return the body tree parsed from source and copied on constructors {@link GrammarCreator#GrammarCreator(List)}}.
	 */
	public AbstractSyntaxNode<?> getBodyTree() {
		return ruleTree;
	}
	/**
	 * @return the body map created on constructors {@link GrammarCreator#GrammarCreator(List)}}.
	 */
	public Map<Long, RuleSyntaxNode> getBodyMap() {
		return ruleMap;
	}

	/**
	 * @return the map containing every node of all the Expression Trees in this primitive w.r.t. to base node identifier. 
	 */
	public Map<Long, ExpressionNode<?>> getExpressionsNodesMap(){
		return this.expMap;
	}
	
	/**
	 * This methods return the ASN for the main rule in case in which this is a main grammar.
	 * It returns {@code null} otherwise or if {@link #searchMainRuleName()} has not been called.
	 * To note that this method has a not efficient implementation.
	 * @return the value of {@link #getNamedRule(String)} with input parameter set to:
	 * {@link #getMainRuleName()}.
	 */
	public RuleSyntaxNode getMainRule(){
		return getNamedRule( mainRuleName);
	}
	/**
	 * This method search for the ASN of a rule given its declarative name.
	 * It break the searching loop as soon as the first occurrence is found.
	 * @param ruleName the declarative name of a rule to be found in this grammar.
	 * @return the Abstract Syntax Node describing the rule if it exists. {@code null} if it
	 * has not such a rule or if the {@link #getBodyMap()} is empty.
	 */
	public RuleSyntaxNode getNamedRule(String ruleName) { // return the first occurence or null if not found
		for( RuleSyntaxNode rule : ruleMap.values()){
			if( rule.getRuleName().equals( ruleName)){
				//ParserLog.info( "Named rule <" + ruleName + "> found in " + this.toString());
				return rule;
			}
		}
		//ParserLog.info( "Named rule <" + ruleName + "> NOT found in " + this.toString());
		return null; 
	}


	/**
	 * This method calls: {@link #getNamedRuleExpressionTree(String, Boolean)}
	 * where the input parameter {@code ruleName} is the result of {@link #getMainRuleName()}
	 * @param copy if {@code true} the returning ET is a copy extrapolated from the ASN. If it is 
	 * {@code false} the returning tree is the same object that is contained in the ASN.
	 * @return The ET described from the main rule. It returns {@code null}
	 * if the grammar does not contain this rule definition or if {@link #getMainRuleName()}
	 * returns {@code empty}.
	 */
	public ExpressionNode<?> getMainRuleExpressionTree(){// Boolean copy){	// be careful it copy it all times get called
		return getNamedRuleExpressionTree( mainRuleName);//, copy);
	}
	/**
	 * This method calls: {@link #getNamedRuleExpressionTree(String, Boolean, Boolean)}
	 * where the input parameter {@code ruleName} is the result of {@link #getMainRuleName()}
	 * @param copy if {@code true} the returning ET is a copy extrapolated from the ASN. If it is 
	 * {@code false} the returning tree is the same object that is contained in the ASN.
	 * @param createNewId this data is considered only when {@code copy} is {@code true}. 
	 * Particularly, if {@code createNewId} is {@code true} the copied tree would have the same ID but
	 * different copyID. If it is {@code false} the copied tree will have the same ID and copyId.
	 * This is done by calling {@link cagg.core.sourceInterface.grammar.abstractSyntaxTree.syntaxNodeType.RuleSyntaxNode#getExpressionTreeCopy(Boolean)} 
	 * with {@code createNewId} as input parameter.
	 * @return The ET described from the main rule. It returns {@code null}
	 * if the grammar does not contain this rule definition or if {@link #getMainRuleName()}
	 * returns {@code empty}.
	 */
	/*public ExpressionNode<?> getMainRuleExpressionTree( Boolean copy, Boolean createNewId){	// be careful it copy it all times get called
		return  getNamedRuleExpressionTree( mainRuleName, copy, createNewId);
	}*/
	/**
	 * This method calls {@link #getNamedRuleExpressionTree(String, Boolean, Boolean)} with 
	 * input parameter respectively set to: {@code ruleName}, {@code copy} and {@code false}.
	 * @param ruleName the declarative name of the rule to be searched in the body map. 
	 * @param copy if {@code true} the returning ET is a copy extrapolated from the ASN. If it is 
	 * {@code false} the returning tree is the same object that is contained in the ASN.
	 * @return the ET contained in the body Abstract Syntax Node that have name as {@code ruleName} 
	 * copied with respect to the given parameter. If this grammar does not contains a rule with the given name
	 * it returns {@code null}.
	 */
	/*public ExpressionNode<?> getNamedRuleExpressionTree( String ruleName, Boolean copy){	// be careful it copy it all times get called
		return getNamedRuleExpressionTree( ruleName, copy, false);
	}*/
	/**
	 * This method use {@link #getNamedRule(String)} in order to get the ASN of the rule
	 * from its name. Than, it returns the ET that represents such a rule.
	 * Moreover, it managge the copying of tree and id both for the visualisation in the GUI of 
	 * more than one tree as well as for evaluation 
	 * (see {@link it.emarolab.cagg.core.evaluatorOLD.InputFormatter}).
	 * @param ruleName the declarative name of the rule to be searched in the body map.
	 * @param copy if {@code true} the returning ET is a copy extrapolated from the ASN. If it is 
	 * {@code false} the returning tree is the same object that is contained in the ASN.
	 * @param createNewId this data is considered only when {@code copy} is {@code true}. 
	 * Particularly, if {@code createNewId} is {@code true} the copied tree would have the same ID but
	 * different copyID. If it is {@code false} the copied tree will have the same ID and copyId.
	 * This is done by calling {@link it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode#getExpressionTreeCopy(Boolean)} 
	 * with {@code createNewId} as input parameter.
	 * @return the ET contained in the body Abstract Syntax Node that have name as {@code ruleName} 
	 * copied with respect to the given parameter. If this grammar does not contains a rule with the given name
	 * it returns {@code null}. 
	 */
	public ExpressionNode<?> getNamedRuleExpressionTree( String ruleName){	// be careful it copy it all times get called
		RuleSyntaxNode rule = getNamedRule( ruleName);
		if( rule != null){
			return rule.getExpressionTree();
		}
		return null;
	}
	
	/*public ExpressionNode<?> getNamedRuleExpressionTreeCopy( String ruleName, Boolean updateCopyId){	// be careful it copy it all times get called
		RuleSyntaxNode rule = getNamedRule( ruleName);
		if( rule != null){
			return rule.getExpressionTree().copyTree( updateCopyId);
		}
		return null;
	}*/

	////////////////////////////////////////	METHOD FOR IDENTIFY THE MAIN GRAMMAR
	/**
	 * This method (not called on constructor) returns the node data attached to the ASN
	 * that defines the main source file and its main rule. To note that it breaks the searching loop
	 * when the first occurrence is found. 
	 * It is important to know that this implementation is not efficient and so it is raccomanded to 
	 * call it as few times as possible, see also {@link #isMainGrammar()} and {@link #getMainRuleName()}.
	 * @return an empty String if the preamble map does not contains a
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.StartSyntaxNode} 
	 * node. Otherwise it returns the node data attached to it (e.g. the name of the main rule).
	 */
	public String searchMainRuleName(){ // return empty if the grammar does not contains "!start <ruleName>" directive (for the first occurrence of the directive)
		mainRuleName = "";
		for( AbstractSyntaxNode<?> node : preambleMap.values()){
			if( node.getType().equals( AbstractSyntaxNode.Type.START)){
				mainRuleName = (String) node.getData().getInstance();	
				break;
			}
		}
		return mainRuleName; 
	}
	/**
	 * This method describe if the source file parsed in this object is a main grammar or not.
	 * To note that it returns always {@code false} if {@link #searchMainRuleName()} is not called first.
	 * @return {@code true} if this source file identify a main grammar (so, has a main rule).
	 * {@code false} otherwise.
	 */
	public Boolean isMainGrammar(){ // returns false if searchMainRuleName() is not called
		if( mainRuleName.isEmpty())
			return false;
		else return true;
	}
	/**
	 * This method returns the name of the main rule in cases in which this source file identify a main grammar.
	 * To note that it returns an empty string in case in which this is not a main grammar or 
	 * {@link #searchMainRuleName()} has not be called.
	 * @return the name of the main rule found by the method {@link #searchMainRuleName()} (not automatically called).
	 * Or it returns an empty string in case in which the name has not been evaluated or this is not a main grammar.
	 */
	public String getMainRuleName(){
		return mainRuleName;
	}

	/**
	 * Returns the description given on constructor {@link GrammarCreator.GrammarPrimitive#GrammarCreator.Grammar(TextualParser)}
	 * as {@code parser.toString()}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return parserToString;
	}
}