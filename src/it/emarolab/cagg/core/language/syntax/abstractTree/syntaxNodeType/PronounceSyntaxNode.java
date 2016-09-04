package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrTreeData;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class PronounceSyntaxNode extends AbstractSyntaxNode< ASNodeStrTreeData>{

	public static final String INFO_GENERIC 	= "This is the node representing a prounounce phonic directive given in the preablme (global). The label is considered to be a single identifier (add \"\" if has spaces)";;
	public static final String INFO_INSTANCE1 	= "pronounce label:"; 
	public static final String INFO_INSTANCE2	= "pronounce preamble expression tree:";
	
	public PronounceSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrTreeData assignData() {
		return new AbstractDataFactory().getNewStringTreeData( INFO_GENERIC, INFO_INSTANCE1, INFO_INSTANCE2); // create as empty
	}

	// set the pronounce name to the node. So, if the input is: (Dr CA) or ("Dr CA") then the output would be (Dr Ca).
	public void setName( ParserRuleContext parserRuleContext) {
		String inputTxt = this.getParsingText( parserRuleContext); // it also update internal ctx variable
		String pronounceLabel = "";
		if( ! TextualParser.isStringLiteral( inputTxt)){ 							// they are one or more identifier divided by space 
			for( int i = 0; i < this.getParsingTokens().getChildCount(); i++){  	// compose label by looking in childs value
				pronounceLabel += this.getParsingTokens().getChild( i).getText(); 	// get label child to deal with possible spaces
				if( i != this.getParsingTokens().getChildCount() - 1) 				// do not put space at the end
					pronounceLabel += " ";
			}
		} else pronounceLabel = TextualParser.retrieveString( inputTxt); 			// it is a string literal
		this.getData().setInstance( pronounceLabel);
	}
	
	//// set tree ??????????????????
	public void setTree(ExpressionNode<?> tree){
		this.getData().setInstance2( tree);
	}
	
	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.PRONOUNCE_PREAMBLE; 
	}
	
	@Override
	public PronounceSyntaxNode copy( Boolean updateCopyId) {
		return (PronounceSyntaxNode) super.copyContents( new PronounceSyntaxNode(null, null), updateCopyId);
	}	
	
}
