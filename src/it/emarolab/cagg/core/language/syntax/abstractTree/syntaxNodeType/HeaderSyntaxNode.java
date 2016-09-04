package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class HeaderSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This is the grammar specification header";
	public static final String INFO_INSTANCE	= "header:";
		
	public HeaderSyntaxNode( ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		// parse the data
		String text = this.getParsingText(); 				// it returns (("#" + ... + ";") for example:"#BNF + EMV 2.1;")
		text = text.replace("#", "");
		text = text.replace(";", ""); 						// so, the grammar header will be just (("...") for example:"BNF+EMV2.1")
		// create a new coherent node
		ASNodeStrData node = new AbstractDataFactory().getNewStringData( text, GENERIC_INFO, INFO_INSTANCE); 
		return node;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.HEADER; 
	}

	@Override
	public HeaderSyntaxNode copy( Boolean updateCopyId) {
		return (HeaderSyntaxNode) super.copyContents( new HeaderSyntaxNode(null, null), updateCopyId);
	}	
}