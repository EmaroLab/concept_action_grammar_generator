package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class LanguageSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This node specify the language using (\"!language \"name\";\") or (\"!language ID;\")";
	public static final String INFO_INSTANCE	= "name/ID:";
	// for parsing
	public static final String SYNTAX_KEY_WORLD = "language";
		
	public LanguageSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		String grammarLanguage = this.getParsingText();
		if( grammarLanguage.contains("\"")) 													// input: 	language"test 1"
			grammarLanguage = TextualParser.retrieveString( grammarLanguage);					// getStr: 	test1
		else 																					// input: 	grammartest
			grammarLanguage = TextualParser.removePrefix(grammarLanguage, SYNTAX_KEY_WORLD);	// remove:	grammar					(in=test)
		// create a new coherent node
		ASNodeStrData node = new AbstractDataFactory().getNewStringData( grammarLanguage, GENERIC_INFO, INFO_INSTANCE); 
		return node;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.LANGUAGE;
	}

	@Override
	public LanguageSyntaxNode copy( Boolean updateCopyId) {
		return (LanguageSyntaxNode) super.copyContents( new LanguageSyntaxNode(null, null), updateCopyId);
	}
	
}
