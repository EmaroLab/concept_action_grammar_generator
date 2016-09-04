package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.ActionDefinitionContext;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class RuleSyntaxNode extends AbstractSyntaxNode< ASNodeStrTreeTagData>{
	
	public static final String INFO_GENERIC 	= "This is the rule node containing the rule declaration, its expression tree and tag map";
	public static final String INFO_INSTANCE1 	= "Rule name declaration:"; 
	public static final String INFO_INSTANCE2	= "Rule expression tree:";
	public static final String INFO_INSTANCE3	= "This is the action map (nodeId[Long] <--> tags[@])";
	
	public RuleSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}
	
	// constructor used to manually created this node (perhaps on preprocessing (repeat))
	public RuleSyntaxNode( String repeatRuleName, ExpressionNode<?> repeatExpressionNode) {
		super();
		this.getData().setInstance( repeatRuleName);
		this.getData().setInstance2( repeatExpressionNode);
	}
	
	@Override
	protected ASNodeStrTreeTagData assignData() {
		return new ASNodeStrTreeTagData( INFO_GENERIC, INFO_INSTANCE1, INFO_INSTANCE2, INFO_INSTANCE3);
	}

	public void setRuleName( ParserRuleContext ctx){
		String inputTxt = this.getParsingText( ctx); // it also update internal ctx variable
		String ruleName = TextualParser.retrieveDeclaration(inputTxt);
		this.getData().setInstance( ruleName);
	}
	public String getRuleName(){
		return this.getData().getInstance();
	}
	
	public void setTree( ExpressionNode<?> tree){
		this.getData().setInstance2( tree);
	}
	public ExpressionNode<?> getExpressionTree() {
		return this.getData().getInstance2();
	}
	
	// to add action to the map contained in this node data
	public void addActions( String action, ExpressionNode<?> termNode){
		addActions( action, termNode.getId());
	}
	public void addActions( List< String> actions, ExpressionNode<?> termNode){
		addActions( actions, termNode.getId());
	}
	public void addActions( String action, Long termId){
		List< String> actions = new ArrayList< String>();
		actions.add( action);
		addActions( actions, termId);
	}
	// all adding method are base on this !!!
	public void addActions( List< String> actions, Long termId){
		Map<Long, List<String>> actionMap = this.getData().getInstance3();
		if( actionMap.containsKey( termId)){
			// add to the existing node tag list
			List<String> tagList = actionMap.get( termId);
			for( String a : actions)
				if( ! tagList.contains( a))
					tagList.add( 0, a);
		}else actionMap.put( termId, actions); // add a new one
	}	
	
	public Map<Long, List< String>> getActions(){
		return this.getData().getInstance3();
	}
	
	public void addActions( Map< Long, List<String>> actions){
		this.getData().getInstance3().putAll( actions);
	}
	
	// parse the text to retrieve action comands
	public List<String> getActions(ActionDefinitionContext ctx) {
		ArrayList< String> actions = new ArrayList< String>();
		for( int i = 0; i < ctx.getChildCount(); i++){
			if( ( ! ctx.getChild(i).getText().equals("{")) && ( ! ctx.getChild(i).getText().equals("}"))){
				actions.add( ctx.getChild(i).getText());
			}
		}
		return actions;
	}
	
	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.RULE;
	}

	@Override
	public RuleSyntaxNode copy( Boolean updateCopyId) {
		Parser ps = null; // to do not have ambiguous constructors (null, null)
		return (RuleSyntaxNode) super.copyContents( new RuleSyntaxNode(null, ps), updateCopyId);
	}

}



@SuppressWarnings("serial")
class ASNodeStrTreeTagData extends AbstractNodeDoubleData< String, ExpressionNode<?>>{ // str+tree+@tag node data class interface (can be visualised in GUI)
	protected Map<Long, List< String>> actionMap; // between node id (term nodes) and @ tags 
	public static final String INFO3 = "No action map info given! ";
	private String info3;

	public ASNodeStrTreeTagData(String genericInfo, String info1, String info2, String info3){
		super(genericInfo, info1, info2);
		actionMap = new LinkedHashMap<Long, List< String>>(); // use only instances of LinkedHashMap !!!!
		this.info3 = info3; 		
	}

	public ASNodeStrTreeTagData(String instance1, ExpressionNode<?> instance2, String genericInfo, String info1, String info2) {
		super(instance1, instance2, genericInfo, info1, info2);
		setCopyTree(); // be sure that instance2 has been set from all constructors!!!!
	}
	
	public ASNodeStrTreeTagData(String instance1, ExpressionNode<?> instance2, Map<Long, List< String>> actionMap, String genericInfo, String info1, String info2, String info3) {
		super(instance1, instance2, genericInfo, info1, info2);
		this.actionMap = actionMap;
		this.info3 = info3;
		setCopyTree(); // be sure that instance2 has been set from all constructors!!!!
	}

	@Override
	public void setInstance2(ExpressionNode<?> data) {
		super.setInstance2(data);
		setCopyTree();
	}

	public Map<Long, List< String>> getInstance3(){
		return actionMap;
	}
	
	public void setInstance3( Map<Long, List< String>> action){
		actionMap = action;
	}
	
	// copy tree to show it correctly on the GUI
	private ExpressionNode<?> copyToShow;
	private void setCopyTree(){
		copyToShow = (ExpressionNode<?>) this.getInstance2().copyTree();
	}
	@Override
	public void addDataToGui(JPanel contentPanel) {
		getVisualizer().addSeparator( contentPanel);
		getVisualizer().addLabel( this.getInfo1() + " " + this.getInstance(), contentPanel);
		getVisualizer().addSeparator( contentPanel);
		getVisualizer().addTree( copyToShow, contentPanel);
		getVisualizer().addSeparator( contentPanel);
		getVisualizer().addLabel( this.getInfo3(), contentPanel);
		getVisualizer().addSeparator( contentPanel);
		getVisualizer().addMap( this.getInstance3(), contentPanel);
		getVisualizer().addSeparator( contentPanel);
	}
	
	public String getInfo3(){
		return this.info3;
	}
		
	@Override
	public String toString(){
		return super.toString() + " <" + this.getInstance() + ">";
	}
}

