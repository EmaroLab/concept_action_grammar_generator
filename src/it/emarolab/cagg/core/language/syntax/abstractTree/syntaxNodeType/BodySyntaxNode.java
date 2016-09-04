package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class BodySyntaxNode extends AbstractSyntaxNode< ASNodeEmptyData>{

	public static final String INFO_GENERIC = "This node collects all the grammar body. It does not contain any data"; 
	
	public BodySyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeEmptyData assignData() {
		return new AbstractDataFactory().getNewEmptyData( INFO_GENERIC);
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.BODY;
	}	
	
	@Override
	public BodySyntaxNode copy( Boolean updateCopyId) {
		return (BodySyntaxNode) super.copyContents( new BodySyntaxNode(null, null), updateCopyId);
	}	
}
