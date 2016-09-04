package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser; 
import org.antlr.v4.runtime.ParserRuleContext;


@SuppressWarnings("serial")
public class GrammarSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This node specify the grammar name using (\"!grammar \"name\";\") or (\"!grammar ID;\")";
	public static final String INFO_INSTANCE	= "name/ID:";
	// for parsing
	public static final String SYNTAX_KEY_WORLD = "grammar";
		
	public GrammarSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		String grammarName = this.getParsingText();  
		if( TextualParser.isStringLiteral( grammarName)) 								// input: 			grammar"test 1"
			grammarName = TextualParser.retrieveString( grammarName);					// getStr:	test 1
		else 																			// input: 			grammartest
			grammarName = TextualParser.removePrefix(grammarName, SYNTAX_KEY_WORLD);	// remove:  grammar
		// create a new coherent node
		ASNodeStrData node = new AbstractDataFactory().getNewStringData(grammarName, GENERIC_INFO, INFO_INSTANCE); 
		return node;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.GRAMMAR_DECLARATION;
	}
	
	@Override
	public GrammarSyntaxNode copy( Boolean updateCopyId) {
		return (GrammarSyntaxNode) super.copyContents( new GrammarSyntaxNode(null, null), updateCopyId);
	}
}