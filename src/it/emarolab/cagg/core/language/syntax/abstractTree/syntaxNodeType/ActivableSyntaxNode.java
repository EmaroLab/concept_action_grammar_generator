package it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrStrData;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ActivableSyntaxNode extends AbstractSyntaxNode< ASNodeStrStrData>{

	// for GUI visualisation and debug (set to null for defaults)
	public static final String GENERIC_INFO 	= "This node specify the activable rules names (\"!activable <Rule1> <Rule2>;\")";
	public static final String INFO_INSTANCE1	= "Rule1:";
	public static final String INFO_INSTANCE2	= "Rule2:";
	// for parsing
	public static final String SYNTAX_KEY_WORLD = "activable";
	
	public ActivableSyntaxNode(ParserRuleContext ctx, Parser parser) {
		super(ctx, parser);
	}

	@Override
	protected ASNodeStrStrData assignData() {
		String modifiable = this.getParsingText(); 										// input: 	"acticvable<r1><r2>"
		modifiable = TextualParser.removePrefix( modifiable, SYNTAX_KEY_WORLD);			// remove: 	"activable"			(in=<r1><r2>)
		String rule1Name = TextualParser.retrieveDeclaration( modifiable);				// getDecl:	"r1"				(in=<r1><r2>)
		modifiable = TextualParser.removeDeclaration( modifiable);						// remove:	"r1"				(in=<r2>)
		String rule2Name = TextualParser.retrieveDeclaration( modifiable);				// getDecl:	"r2"				(in=<r2>)
		// create a new coherent node
		ASNodeStrStrData data = new AbstractDataFactory().getNewDoubleStringData( rule1Name, rule2Name, GENERIC_INFO, INFO_INSTANCE1, INFO_INSTANCE2); 
		return data;  // return the node to be added in the Abstract Syntax Tree
	}

	@Override
	protected AbstractSyntaxNode.Type assignType() {
		return AbstractSyntaxNode.Type.ACTIVABLE;
	}

	@Override
	public ActivableSyntaxNode copy( Boolean updateCopyId) {
		return (ActivableSyntaxNode) super.copyContents( new ActivableSyntaxNode(null, null), updateCopyId);
	}	
}