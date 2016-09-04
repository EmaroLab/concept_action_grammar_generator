package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ImportSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This node specify the exporting rule name (\"!export <Rule>;\")";
	public static final String INFO_INSTANCE	= "Rule:";
	// for parsing
	public static final String SYNTAX_KEY_WORLD = "import";
		
	public ImportSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		String importingRuleName = this.getParsingText(); 										// input: 	"import<name>"
		importingRuleName = TextualParser.removePrefix( importingRuleName, SYNTAX_KEY_WORLD);	// remove:	"import"			(input=<name>)
		importingRuleName = TextualParser.retrieveDeclaration( importingRuleName);				// getDecl:	"name"
		// create a new coherent node
		ASNodeStrData node = new AbstractDataFactory().getNewStringData( importingRuleName, GENERIC_INFO, INFO_INSTANCE); 
		return node;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.IMPORT;
	}
	
	@Override
	public ImportSyntaxNode copy( Boolean updateCopyId) {
		return (ImportSyntaxNode) super.copyContents( new ImportSyntaxNode(null, null), updateCopyId);
	}	
	
}