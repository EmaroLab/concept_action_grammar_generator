package it.emarolab.cagg.core.evaluation.semanticGrammar;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.ActionTagBase;
import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory.ExpressionTermData;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class SyntaxActionTag extends ActionTagBase{

	/* 	##################################################################################
	    ######################### CONSTANTS UTILITY CLASS ################################
	used to parse tags for source file. Particularly, specifications are:
 		- [@~] impose to push to the leafs of the composed tree a tag equal to their 
 	  term value (it cannot be controlled) (it should be implemented by ActionTagBase.parse)
		- [@T] impose to push to the leafs of the composed tree the T tag 
	  (can be controlled by "!") (implemented on ExpressionNode.composeTree)
		- [@?] impose to push to the leafs of the composed tree a tag equal to the sub 
	  rule definition name relate to this tag (can be controlled by "!") 
	  (implemented on ExpressionNode.composeTree)
		- [@!T] impose to stop pushing to the leafs of the composed tree the tag T 
	  (it can be used to control "?") (implemented on ExpressionNode.composeTree)
	  	- [@!!] impose to stop pushing all the tag to the children sub rules of the 
	  composed tree. (it cannot be controlled) (implemented on ExpressionNode.composeTree)
	  	- [@!~] impose to stop pushing the tag equal to the term value to the children 
	  sub rules of the  composed tree. (it cannot be controlled) (implemented on ExpressionNode.composeTree)
		- [@?~] it is a short cut that stands for [@?;@:].   						*/
	public final static String TAG_DIRECTIVE_SYMB_TERM = "~";
	public final static String TAG_DIRECTIVE_SYMB_PUSH = "?";
	public final static String TAG_DIRECTIVE_SYMB_PUSH_TERM = TAG_DIRECTIVE_SYMB_PUSH + TAG_DIRECTIVE_SYMB_TERM; // "?~"
	public final static String TAG_DIRECTIVE_SYMB_STOP = "!";
	public final static String TAG_DIRECTIVE_SYMB_STOP_ALL = TAG_DIRECTIVE_SYMB_STOP + TAG_DIRECTIVE_SYMB_STOP; // "!!"
	public final static String TAG_DIRECTIVE_SYMB_STOP_TERM = TAG_DIRECTIVE_SYMB_STOP + TAG_DIRECTIVE_SYMB_TERM; // "!~"
	public static String getFullTagDirective( String directive){
		return "@" + directive + ";";
	}
	public static String getStartTagDirective( String directive){
		return "@" + directive;
	}
	
	/* 	##################################################################################
		########################## DEFOULT CONSTRUCTOR ###################################
	 */
	protected SyntaxActionTag() {
		super();
	}
	public SyntaxActionTag( Map< Long, List< String>> tagsMap, ExpressionNode<?> rootNode){
		super( tagsMap, rootNode);
	}
	public SyntaxActionTag(ExpressionNode<?>.ComposedRule composedExpression) {
		super(composedExpression);
	}
	
	@Override
	protected Set<TagBase<?>> initialise() {
		// it must call new on something
		return new LinkedHashSet<>();
	}
	@Override
	public void toSyntaxTag( Map< Long, List< String>> tagsMap, ExpressionNode<?> rootNode) {
		Map<Long, BaseNode> nodeMap = rootNode.toMap();
		for( Long id : tagsMap.keySet()){
			List<String> parsedTag = parseAll( tagsMap.get( id), (ExpressionNode<?>) nodeMap.get( id));
			this.add( new TagBase< String>( id, parsedTag));
		}
	}
	private List< String> parseAll( List<String> list, ExpressionNode<?> relatedNode) {
		List< String> out = new ArrayList<>();
		for( String s : list){
			// remove some symbols
			String parsedTag = parse( s);
			// check if this has the directive to use the name of the term as tag (e.g. {@??;})
			// this directive assign as tag the name of the term for all the leafs node of the composed tree starting from this root.
			if( parsedTag.equals( TAG_DIRECTIVE_SYMB_TERM))
				parsedTag = parseEmptyTag( relatedNode);
			out.add( parsedTag);
		}
		return out;
	}
	
	/* 	##################################################################################
		############################# TAG PROCESSING #####################################
	 	you may want to override only this !!! 			*/
	protected String parse(String s) {
		s = s.replaceAll( "\"", "");
		s = s.replace( ";", "");
		s = s.replace( "@", "");
		s = s.replace( " ", "_");
		s = s.replace( "=", "");
		return s;
	}
	protected String parseEmptyTag( ExpressionNode<?> relateNode){
		if( relateNode instanceof TermExpressionNode){
			 BaseData<?> relateNodeData = relateNode.getData();
			 if( relateNodeData instanceof ExpressionTermData){
				 return ( (( ExpressionTermData) relateNodeData).getTermLiteral());
			 } else GrammarLog.error( "error tag extraction. TermExpressionNode should have data object of type: ExpressionTermData."
			 		+ " Type: " + relateNodeData.getClass().getSimpleName() + " found instead.");
		} else GrammarLog.error( "error tag extraction. The node should be of the type TermExpressionNode."
				+ " Type: " + relateNode.getClass().getSimpleName() + " found instead.");
		return TAG_DIRECTIVE_SYMB_TERM;
	}
}
