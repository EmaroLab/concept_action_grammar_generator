package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ExportSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	public static final String INFO_GENERIC = "This node specify the exporting rule name (\"!export <Rule>;\")";
	public static final String INFO_INSTANCE = "Rule:";
	
	public ExportSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		String exportingRuleName = this.getParsingText(); 									// input: 	"export<name>"
		exportingRuleName = TextualParser.removePrefix( exportingRuleName, "export");		// remove: 	"export"			(in=<name>)
		exportingRuleName = TextualParser.retrieveDeclaration( exportingRuleName);			// getDecl:	"name"		
		// create a new coherent node
		ASNodeStrData data = new AbstractDataFactory().getNewStringData( exportingRuleName, INFO_GENERIC, INFO_INSTANCE); 
		return data;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.EXPORT;
	}
	
	@Override
	public ExportSyntaxNode copy( Boolean updateCopyId) {
		return (ExportSyntaxNode) super.copyContents( new ExportSyntaxNode(null, null), updateCopyId);
	}
	
}
