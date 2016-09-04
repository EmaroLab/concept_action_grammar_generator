package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class RootSyntaxNode extends AbstractSyntaxNode< ASNodeEmptyData>{

	public static final String INFO_GENERIC = "This is the AST root node. It does not contain any data";
	
	public RootSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeEmptyData assignData() {
		return new AbstractDataFactory().getNewEmptyData( INFO_GENERIC);
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.GRAMMAR;
	}
	
	@Override
	public RootSyntaxNode copy( Boolean updateCopyId) {
		return (RootSyntaxNode) super.copyContents( new RootSyntaxNode(null, null), updateCopyId);
	}	
	
}
