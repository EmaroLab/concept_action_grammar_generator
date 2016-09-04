package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class PreambleSyntaxNode extends  AbstractSyntaxNode< ASNodeEmptyData>{

	public static final String INFO_GENERIC = "This node collects all the grammar preamble. It does not contain any data";
	
	public PreambleSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeEmptyData assignData() {
		return new AbstractDataFactory().getNewEmptyData( INFO_GENERIC);
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.PREAMBLE; 
	}

	@Override
	public PreambleSyntaxNode copy( Boolean updateCopyId) {
		return (PreambleSyntaxNode) super.copyContents( new PreambleSyntaxNode(null, null), updateCopyId);
	}	

}
