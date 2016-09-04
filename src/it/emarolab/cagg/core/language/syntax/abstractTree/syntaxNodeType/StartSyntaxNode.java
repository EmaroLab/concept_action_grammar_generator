package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class StartSyntaxNode extends AbstractSyntaxNode< ASNodeStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This node specify the starting rule name (\"!start <Rule>;\")";
	public static final String INFO_INSTANCE	= "Rule:";
	// for parsing
	public static final String SYNTAX_KEY_WORLD = "start";
		
	public StartSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrData assignData() {
		String startRuleName = this.getParsingText();										// input:	"start<name>"
		startRuleName = TextualParser.removePrefix( startRuleName, SYNTAX_KEY_WORLD);		// remove:	start			(in=<name>)
		startRuleName = TextualParser.retrieveDeclaration( startRuleName);					// getDecl:	name
		// create a new coherent node
		ASNodeStrData node = new AbstractDataFactory().getNewStringData( startRuleName, GENERIC_INFO, INFO_INSTANCE); 
		return node;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.START; 
	}
	
	@Override
	public StartSyntaxNode copy( Boolean updateCopyId) {
		return (StartSyntaxNode) super.copyContents( new StartSyntaxNode(null, null), updateCopyId);
	}
}
