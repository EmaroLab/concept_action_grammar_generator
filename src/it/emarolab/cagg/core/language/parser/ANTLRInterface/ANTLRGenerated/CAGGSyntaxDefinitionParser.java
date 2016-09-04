// Generated from CAGGSyntaxDefinition.g4 by ANTLR 4.4
  package it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CAGGSyntaxDefinitionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__23=1, T__22=2, T__21=3, T__20=4, T__19=5, T__18=6, T__17=7, T__16=8, 
		T__15=9, T__14=10, T__13=11, T__12=12, T__11=13, T__10=14, T__9=15, T__8=16, 
		T__7=17, T__6=18, T__5=19, T__4=20, T__3=21, T__2=22, T__1=23, T__0=24, 
		GRAMMAR_HEADER=25, COMMENT=26, MULTILINE_COMMENT=27, INTEGER=28, IDENT=29, 
		WS=30, TAG_LITERAL=31, RULE_LITERAL=32, STRING_LITERAL=33, BRACKETS=34;
	public static final String[] tokenNames = {
		"<INVALID>", "'!repeat'", "'!pronounce'", "'!language'", "'!start'", "':'", 
		"'!export'", "'{'", "'['", "';'", "'|'", "'}'", "']'", "'!modifiable'", 
		"'^'", "'!activatable'", "'!import'", "'!label'", "'('", "')'", "'!action'", 
		"'!id'", "','", "'!grammar'", "'!optional'", "GRAMMAR_HEADER", "COMMENT", 
		"MULTILINE_COMMENT", "INTEGER", "IDENT", "WS", "TAG_LITERAL", "RULE_LITERAL", 
		"STRING_LITERAL", "BRACKETS"
	};
	public static final int
		RULE_nuanceGrammar = 0, RULE_grammarHearder = 1, RULE_preamble = 2, RULE_grammarDeclaration = 3, 
		RULE_languageDeclaration = 4, RULE_exportDeclaration = 5, RULE_startDeclaration = 6, 
		RULE_importDeclaration = 7, RULE_modifableDeclaration = 8, RULE_activableDeclaration = 9, 
		RULE_pronounceDeclaration = 10, RULE_pronunceLabel = 11, RULE_ruleDeclaration = 12, 
		RULE_body = 13, RULE_ruleBody = 14, RULE_ruleTerm = 15, RULE_andOperator = 16, 
		RULE_orOperator = 17, RULE_ruleExpression = 18, RULE_actionDefinition = 19, 
		RULE_ruleDirective = 20, RULE_labelDirective = 21, RULE_idDirective = 22, 
		RULE_actionDirective = 23, RULE_pronounceDirective = 24, RULE_optionalDirective = 25, 
		RULE_repeatDirective = 26;
	public static final String[] ruleNames = {
		"nuanceGrammar", "grammarHearder", "preamble", "grammarDeclaration", "languageDeclaration", 
		"exportDeclaration", "startDeclaration", "importDeclaration", "modifableDeclaration", 
		"activableDeclaration", "pronounceDeclaration", "pronunceLabel", "ruleDeclaration", 
		"body", "ruleBody", "ruleTerm", "andOperator", "orOperator", "ruleExpression", 
		"actionDefinition", "ruleDirective", "labelDirective", "idDirective", 
		"actionDirective", "pronounceDirective", "optionalDirective", "repeatDirective"
	};

	@Override
	public String getGrammarFileName() { return "CAGGSyntaxDefinition.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CAGGSyntaxDefinitionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class NuanceGrammarContext extends ParserRuleContext {
		public PreambleContext preamble(int i) {
			return getRuleContext(PreambleContext.class,i);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public List<PreambleContext> preamble() {
			return getRuleContexts(PreambleContext.class);
		}
		public TerminalNode EOF() { return getToken(CAGGSyntaxDefinitionParser.EOF, 0); }
		public GrammarHearderContext grammarHearder() {
			return getRuleContext(GrammarHearderContext.class,0);
		}
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public NuanceGrammarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nuanceGrammar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterNuanceGrammar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitNuanceGrammar(this);
		}
	}

	public final NuanceGrammarContext nuanceGrammar() throws RecognitionException {
		NuanceGrammarContext _localctx = new NuanceGrammarContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_nuanceGrammar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); grammarHearder();
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__21) | (1L << T__20) | (1L << T__18) | (1L << T__11) | (1L << T__9) | (1L << T__8) | (1L << T__1))) != 0)) {
				{
				{
				setState(55); preamble();
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE_LITERAL) {
				{
				{
				setState(61); body();
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GrammarHearderContext extends ParserRuleContext {
		public TerminalNode GRAMMAR_HEADER() { return getToken(CAGGSyntaxDefinitionParser.GRAMMAR_HEADER, 0); }
		public GrammarHearderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarHearder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterGrammarHearder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitGrammarHearder(this);
		}
	}

	public final GrammarHearderContext grammarHearder() throws RecognitionException {
		GrammarHearderContext _localctx = new GrammarHearderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_grammarHearder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69); match(GRAMMAR_HEADER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PreambleContext extends ParserRuleContext {
		public LanguageDeclarationContext languageDeclaration() {
			return getRuleContext(LanguageDeclarationContext.class,0);
		}
		public ModifableDeclarationContext modifableDeclaration() {
			return getRuleContext(ModifableDeclarationContext.class,0);
		}
		public ImportDeclarationContext importDeclaration() {
			return getRuleContext(ImportDeclarationContext.class,0);
		}
		public ActivableDeclarationContext activableDeclaration() {
			return getRuleContext(ActivableDeclarationContext.class,0);
		}
		public PronounceDeclarationContext pronounceDeclaration() {
			return getRuleContext(PronounceDeclarationContext.class,0);
		}
		public ExportDeclarationContext exportDeclaration() {
			return getRuleContext(ExportDeclarationContext.class,0);
		}
		public StartDeclarationContext startDeclaration() {
			return getRuleContext(StartDeclarationContext.class,0);
		}
		public GrammarDeclarationContext grammarDeclaration() {
			return getRuleContext(GrammarDeclarationContext.class,0);
		}
		public PreambleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preamble; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterPreamble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitPreamble(this);
		}
	}

	public final PreambleContext preamble() throws RecognitionException {
		PreambleContext _localctx = new PreambleContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_preamble);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			switch (_input.LA(1)) {
			case T__1:
				{
				setState(71); grammarDeclaration();
				}
				break;
			case T__21:
				{
				setState(72); languageDeclaration();
				}
				break;
			case T__18:
				{
				setState(73); exportDeclaration();
				}
				break;
			case T__8:
				{
				setState(74); importDeclaration();
				}
				break;
			case T__20:
				{
				setState(75); startDeclaration();
				}
				break;
			case T__9:
				{
				setState(76); activableDeclaration();
				}
				break;
			case T__11:
				{
				setState(77); modifableDeclaration();
				}
				break;
			case T__22:
				{
				setState(78); pronounceDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(81); match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GrammarDeclarationContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public TerminalNode IDENT() { return getToken(CAGGSyntaxDefinitionParser.IDENT, 0); }
		public GrammarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterGrammarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitGrammarDeclaration(this);
		}
	}

	public final GrammarDeclarationContext grammarDeclaration() throws RecognitionException {
		GrammarDeclarationContext _localctx = new GrammarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_grammarDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83); match(T__1);
			setState(84);
			_la = _input.LA(1);
			if ( !(_la==IDENT || _la==STRING_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LanguageDeclarationContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public TerminalNode IDENT() { return getToken(CAGGSyntaxDefinitionParser.IDENT, 0); }
		public LanguageDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_languageDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterLanguageDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitLanguageDeclaration(this);
		}
	}

	public final LanguageDeclarationContext languageDeclaration() throws RecognitionException {
		LanguageDeclarationContext _localctx = new LanguageDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_languageDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86); match(T__21);
			setState(87);
			_la = _input.LA(1);
			if ( !(_la==IDENT || _la==STRING_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExportDeclarationContext extends ParserRuleContext {
		public RuleDeclarationContext ruleDeclaration() {
			return getRuleContext(RuleDeclarationContext.class,0);
		}
		public ExportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterExportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitExportDeclaration(this);
		}
	}

	public final ExportDeclarationContext exportDeclaration() throws RecognitionException {
		ExportDeclarationContext _localctx = new ExportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_exportDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); match(T__18);
			setState(90); ruleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartDeclarationContext extends ParserRuleContext {
		public RuleDeclarationContext ruleDeclaration() {
			return getRuleContext(RuleDeclarationContext.class,0);
		}
		public StartDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterStartDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitStartDeclaration(this);
		}
	}

	public final StartDeclarationContext startDeclaration() throws RecognitionException {
		StartDeclarationContext _localctx = new StartDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_startDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); match(T__20);
			setState(93); ruleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclarationContext extends ParserRuleContext {
		public RuleDeclarationContext ruleDeclaration() {
			return getRuleContext(RuleDeclarationContext.class,0);
		}
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitImportDeclaration(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_importDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); match(T__8);
			setState(96); ruleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifableDeclarationContext extends ParserRuleContext {
		public RuleDeclarationContext ruleDeclaration(int i) {
			return getRuleContext(RuleDeclarationContext.class,i);
		}
		public List<RuleDeclarationContext> ruleDeclaration() {
			return getRuleContexts(RuleDeclarationContext.class);
		}
		public ModifableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterModifableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitModifableDeclaration(this);
		}
	}

	public final ModifableDeclarationContext modifableDeclaration() throws RecognitionException {
		ModifableDeclarationContext _localctx = new ModifableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_modifableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); match(T__11);
			setState(99); ruleDeclaration();
			setState(100); ruleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActivableDeclarationContext extends ParserRuleContext {
		public RuleDeclarationContext ruleDeclaration(int i) {
			return getRuleContext(RuleDeclarationContext.class,i);
		}
		public List<RuleDeclarationContext> ruleDeclaration() {
			return getRuleContexts(RuleDeclarationContext.class);
		}
		public ActivableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_activableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterActivableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitActivableDeclaration(this);
		}
	}

	public final ActivableDeclarationContext activableDeclaration() throws RecognitionException {
		ActivableDeclarationContext _localctx = new ActivableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_activableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102); match(T__9);
			setState(103); ruleDeclaration();
			setState(104); ruleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PronounceDeclarationContext extends ParserRuleContext {
		public PronunceLabelContext pronunceLabel() {
			return getRuleContext(PronunceLabelContext.class,0);
		}
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public PronounceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronounceDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterPronounceDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitPronounceDeclaration(this);
		}
	}

	public final PronounceDeclarationContext pronounceDeclaration() throws RecognitionException {
		PronounceDeclarationContext _localctx = new PronounceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pronounceDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106); match(T__22);
			setState(107); pronunceLabel();
			setState(108); ruleExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PronunceLabelContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public TerminalNode IDENT(int i) {
			return getToken(CAGGSyntaxDefinitionParser.IDENT, i);
		}
		public List<TerminalNode> IDENT() { return getTokens(CAGGSyntaxDefinitionParser.IDENT); }
		public PronunceLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronunceLabel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterPronunceLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitPronunceLabel(this);
		}
	}

	public final PronunceLabelContext pronunceLabel() throws RecognitionException {
		PronunceLabelContext _localctx = new PronunceLabelContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pronunceLabel);
		try {
			int _alt;
			setState(117);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(110); match(STRING_LITERAL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(111); match(IDENT);
						}
						} 
					}
					setState(116);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleDeclarationContext extends ParserRuleContext {
		public TerminalNode RULE_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.RULE_LITERAL, 0); }
		public RuleDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRuleDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRuleDeclaration(this);
		}
	}

	public final RuleDeclarationContext ruleDeclaration() throws RecognitionException {
		RuleDeclarationContext _localctx = new RuleDeclarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ruleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119); match(RULE_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyContext extends ParserRuleContext {
		public RuleBodyContext ruleBody() {
			return getRuleContext(RuleBodyContext.class,0);
		}
		public RuleDeclarationContext ruleDeclaration() {
			return getRuleContext(RuleDeclarationContext.class,0);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitBody(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121); ruleDeclaration();
			setState(122); match(T__19);
			setState(123); ruleBody();
			setState(124); match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleBodyContext extends ParserRuleContext {
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public RuleBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRuleBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRuleBody(this);
		}
	}

	public final RuleBodyContext ruleBody() throws RecognitionException {
		RuleBodyContext _localctx = new RuleBodyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ruleBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126); ruleExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleTermContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(CAGGSyntaxDefinitionParser.INTEGER, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public RuleDeclarationContext ruleDeclaration() {
			return getRuleContext(RuleDeclarationContext.class,0);
		}
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public ActionDefinitionContext actionDefinition() {
			return getRuleContext(ActionDefinitionContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(CAGGSyntaxDefinitionParser.IDENT, 0); }
		public RuleDirectiveContext ruleDirective() {
			return getRuleContext(RuleDirectiveContext.class,0);
		}
		public RuleTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRuleTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRuleTerm(this);
		}
	}

	public final RuleTermContext ruleTerm() throws RecognitionException {
		RuleTermContext _localctx = new RuleTermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ruleTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			switch (_input.LA(1)) {
			case T__10:
			case INTEGER:
			case IDENT:
			case STRING_LITERAL:
				{
				setState(134);
				switch (_input.LA(1)) {
				case STRING_LITERAL:
					{
					setState(128); match(STRING_LITERAL);
					}
					break;
				case T__10:
				case IDENT:
					{
					{
					setState(130);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(129); match(T__10);
						}
					}

					setState(132); match(IDENT);
					}
					}
					break;
				case INTEGER:
					{
					setState(133); match(INTEGER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case RULE_LITERAL:
				{
				setState(136); ruleDeclaration();
				}
				break;
			case T__23:
			case T__22:
			case T__7:
			case T__4:
			case T__3:
			case T__0:
				{
				setState(137); ruleDirective();
				}
				break;
			case T__16:
			case T__6:
				{
				setState(138);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__6) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				{
				setState(139); ruleExpression();
				}
				setState(140);
				_la = _input.LA(1);
				if ( !(_la==T__12 || _la==T__5) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(145);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(144); actionDefinition();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndOperatorContext extends ParserRuleContext {
		public RuleTermContext ruleTerm() {
			return getRuleContext(RuleTermContext.class,0);
		}
		public AndOperatorContext andOperator() {
			return getRuleContext(AndOperatorContext.class,0);
		}
		public RuleDirectiveContext ruleDirective() {
			return getRuleContext(RuleDirectiveContext.class,0);
		}
		public AndOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterAndOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitAndOperator(this);
		}
	}

	public final AndOperatorContext andOperator() throws RecognitionException {
		AndOperatorContext _localctx = new AndOperatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_andOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(147); ruleTerm();
				}
				break;
			case 2:
				{
				setState(148); ruleDirective();
				}
				break;
			}
			setState(152);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(151); andOperator();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrOperatorContext extends ParserRuleContext {
		public OrOperatorContext orOperator() {
			return getRuleContext(OrOperatorContext.class,0);
		}
		public AndOperatorContext andOperator() {
			return getRuleContext(AndOperatorContext.class,0);
		}
		public RuleDirectiveContext ruleDirective() {
			return getRuleContext(RuleDirectiveContext.class,0);
		}
		public OrOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterOrOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitOrOperator(this);
		}
	}

	public final OrOperatorContext orOperator() throws RecognitionException {
		OrOperatorContext _localctx = new OrOperatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_orOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(154); match(T__14);
				}
			}

			setState(159);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(157); andOperator();
				}
				break;
			case 2:
				{
				setState(158); ruleDirective();
				}
				break;
			}
			setState(162);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(161); orOperator();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleExpressionContext extends ParserRuleContext {
		public OrOperatorContext orOperator() {
			return getRuleContext(OrOperatorContext.class,0);
		}
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public RuleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRuleExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRuleExpression(this);
		}
	}

	public final RuleExpressionContext ruleExpression() throws RecognitionException {
		RuleExpressionContext _localctx = new RuleExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ruleExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(164); orOperator();
			}
			setState(166);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__23) | (1L << T__22) | (1L << T__16) | (1L << T__14) | (1L << T__10) | (1L << T__7) | (1L << T__6) | (1L << T__4) | (1L << T__3) | (1L << T__0) | (1L << INTEGER) | (1L << IDENT) | (1L << RULE_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(165); ruleExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDefinitionContext extends ParserRuleContext {
		public TerminalNode TAG_LITERAL(int i) {
			return getToken(CAGGSyntaxDefinitionParser.TAG_LITERAL, i);
		}
		public List<TerminalNode> TAG_LITERAL() { return getTokens(CAGGSyntaxDefinitionParser.TAG_LITERAL); }
		public ActionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterActionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitActionDefinition(this);
		}
	}

	public final ActionDefinitionContext actionDefinition() throws RecognitionException {
		ActionDefinitionContext _localctx = new ActionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_actionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); match(T__17);
			setState(169); match(TAG_LITERAL);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TAG_LITERAL) {
				{
				{
				setState(170); match(TAG_LITERAL);
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(176); match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleDirectiveContext extends ParserRuleContext {
		public OptionalDirectiveContext optionalDirective() {
			return getRuleContext(OptionalDirectiveContext.class,0);
		}
		public PronounceDirectiveContext pronounceDirective() {
			return getRuleContext(PronounceDirectiveContext.class,0);
		}
		public RepeatDirectiveContext repeatDirective() {
			return getRuleContext(RepeatDirectiveContext.class,0);
		}
		public ActionDirectiveContext actionDirective() {
			return getRuleContext(ActionDirectiveContext.class,0);
		}
		public LabelDirectiveContext labelDirective() {
			return getRuleContext(LabelDirectiveContext.class,0);
		}
		public IdDirectiveContext idDirective() {
			return getRuleContext(IdDirectiveContext.class,0);
		}
		public RuleDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRuleDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRuleDirective(this);
		}
	}

	public final RuleDirectiveContext ruleDirective() throws RecognitionException {
		RuleDirectiveContext _localctx = new RuleDirectiveContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_ruleDirective);
		try {
			setState(184);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(178); labelDirective();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(179); idDirective();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(180); actionDirective();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 4);
				{
				setState(181); pronounceDirective();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 5);
				{
				setState(182); optionalDirective();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 6);
				{
				setState(183); repeatDirective();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelDirectiveContext extends ParserRuleContext {
		public RuleExpressionContext ruleExpression(int i) {
			return getRuleContext(RuleExpressionContext.class,i);
		}
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public List<RuleExpressionContext> ruleExpression() {
			return getRuleContexts(RuleExpressionContext.class);
		}
		public LabelDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterLabelDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitLabelDirective(this);
		}
	}

	public final LabelDirectiveContext labelDirective() throws RecognitionException {
		LabelDirectiveContext _localctx = new LabelDirectiveContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_labelDirective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186); match(T__7);
			setState(187); match(T__6);
			setState(188); match(STRING_LITERAL);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(189); match(T__2);
				setState(190); ruleExpression();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdDirectiveContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(CAGGSyntaxDefinitionParser.INTEGER, 0); }
		public IdDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterIdDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitIdDirective(this);
		}
	}

	public final IdDirectiveContext idDirective() throws RecognitionException {
		IdDirectiveContext _localctx = new IdDirectiveContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_idDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198); match(T__3);
			setState(199); match(T__6);
			setState(200); match(INTEGER);
			setState(201); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDirectiveContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CAGGSyntaxDefinitionParser.STRING_LITERAL, 0); }
		public ActionDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterActionDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitActionDirective(this);
		}
	}

	public final ActionDirectiveContext actionDirective() throws RecognitionException {
		ActionDirectiveContext _localctx = new ActionDirectiveContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_actionDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203); match(T__4);
			setState(204); match(T__6);
			setState(205); match(STRING_LITERAL);
			setState(206); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PronounceDirectiveContext extends ParserRuleContext {
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public PronounceDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronounceDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterPronounceDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitPronounceDirective(this);
		}
	}

	public final PronounceDirectiveContext pronounceDirective() throws RecognitionException {
		PronounceDirectiveContext _localctx = new PronounceDirectiveContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_pronounceDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208); match(T__22);
			setState(209); match(T__6);
			setState(210); ruleExpression();
			setState(211); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionalDirectiveContext extends ParserRuleContext {
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public OptionalDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionalDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterOptionalDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitOptionalDirective(this);
		}
	}

	public final OptionalDirectiveContext optionalDirective() throws RecognitionException {
		OptionalDirectiveContext _localctx = new OptionalDirectiveContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_optionalDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213); match(T__0);
			setState(214); match(T__6);
			setState(215); ruleExpression();
			setState(216); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepeatDirectiveContext extends ParserRuleContext {
		public List<TerminalNode> INTEGER() { return getTokens(CAGGSyntaxDefinitionParser.INTEGER); }
		public RuleExpressionContext ruleExpression() {
			return getRuleContext(RuleExpressionContext.class,0);
		}
		public TerminalNode INTEGER(int i) {
			return getToken(CAGGSyntaxDefinitionParser.INTEGER, i);
		}
		public RepeatDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeatDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).enterRepeatDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CAGGSyntaxDefinitionListener ) ((CAGGSyntaxDefinitionListener)listener).exitRepeatDirective(this);
		}
	}

	public final RepeatDirectiveContext repeatDirective() throws RecognitionException {
		RepeatDirectiveContext _localctx = new RepeatDirectiveContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_repeatDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218); match(T__23);
			setState(219); match(T__6);
			setState(220); ruleExpression();
			setState(221); match(T__2);
			setState(222); match(INTEGER);
			setState(223); match(T__2);
			setState(224); match(INTEGER);
			setState(225); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\u00e6\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\7\2;\n\2\f\2\16\2>\13\2\3\2\7\2"+
		"A\n\2\f\2\16\2D\13\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5"+
		"\4R\n\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\7\r"+
		"s\n\r\f\r\16\rv\13\r\5\rx\n\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\5\21\u0085\n\21\3\21\3\21\5\21\u0089\n\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\5\21\u0091\n\21\3\21\5\21\u0094\n\21\3\22\3\22\5\22"+
		"\u0098\n\22\3\22\5\22\u009b\n\22\3\23\5\23\u009e\n\23\3\23\3\23\5\23\u00a2"+
		"\n\23\3\23\5\23\u00a5\n\23\3\24\3\24\5\24\u00a9\n\24\3\25\3\25\3\25\7"+
		"\25\u00ae\n\25\f\25\16\25\u00b1\13\25\3\25\3\25\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\5\26\u00bb\n\26\3\27\3\27\3\27\3\27\3\27\7\27\u00c2\n\27\f\27"+
		"\16\27\u00c5\13\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3"+
		"\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\2\2\35\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\5\4\2\37\37##\4\2\n\n\24\24\4\2"+
		"\16\16\25\25\u00e9\28\3\2\2\2\4G\3\2\2\2\6Q\3\2\2\2\bU\3\2\2\2\nX\3\2"+
		"\2\2\f[\3\2\2\2\16^\3\2\2\2\20a\3\2\2\2\22d\3\2\2\2\24h\3\2\2\2\26l\3"+
		"\2\2\2\30w\3\2\2\2\32y\3\2\2\2\34{\3\2\2\2\36\u0080\3\2\2\2 \u0090\3\2"+
		"\2\2\"\u0097\3\2\2\2$\u009d\3\2\2\2&\u00a6\3\2\2\2(\u00aa\3\2\2\2*\u00ba"+
		"\3\2\2\2,\u00bc\3\2\2\2.\u00c8\3\2\2\2\60\u00cd\3\2\2\2\62\u00d2\3\2\2"+
		"\2\64\u00d7\3\2\2\2\66\u00dc\3\2\2\28<\5\4\3\29;\5\6\4\2:9\3\2\2\2;>\3"+
		"\2\2\2<:\3\2\2\2<=\3\2\2\2=B\3\2\2\2><\3\2\2\2?A\5\34\17\2@?\3\2\2\2A"+
		"D\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DB\3\2\2\2EF\7\2\2\3F\3\3\2\2\2"+
		"GH\7\33\2\2H\5\3\2\2\2IR\5\b\5\2JR\5\n\6\2KR\5\f\7\2LR\5\20\t\2MR\5\16"+
		"\b\2NR\5\24\13\2OR\5\22\n\2PR\5\26\f\2QI\3\2\2\2QJ\3\2\2\2QK\3\2\2\2Q"+
		"L\3\2\2\2QM\3\2\2\2QN\3\2\2\2QO\3\2\2\2QP\3\2\2\2RS\3\2\2\2ST\7\13\2\2"+
		"T\7\3\2\2\2UV\7\31\2\2VW\t\2\2\2W\t\3\2\2\2XY\7\5\2\2YZ\t\2\2\2Z\13\3"+
		"\2\2\2[\\\7\b\2\2\\]\5\32\16\2]\r\3\2\2\2^_\7\6\2\2_`\5\32\16\2`\17\3"+
		"\2\2\2ab\7\22\2\2bc\5\32\16\2c\21\3\2\2\2de\7\17\2\2ef\5\32\16\2fg\5\32"+
		"\16\2g\23\3\2\2\2hi\7\21\2\2ij\5\32\16\2jk\5\32\16\2k\25\3\2\2\2lm\7\4"+
		"\2\2mn\5\30\r\2no\5&\24\2o\27\3\2\2\2px\7#\2\2qs\7\37\2\2rq\3\2\2\2sv"+
		"\3\2\2\2tr\3\2\2\2tu\3\2\2\2ux\3\2\2\2vt\3\2\2\2wp\3\2\2\2wt\3\2\2\2x"+
		"\31\3\2\2\2yz\7\"\2\2z\33\3\2\2\2{|\5\32\16\2|}\7\7\2\2}~\5\36\20\2~\177"+
		"\7\13\2\2\177\35\3\2\2\2\u0080\u0081\5&\24\2\u0081\37\3\2\2\2\u0082\u0089"+
		"\7#\2\2\u0083\u0085\7\20\2\2\u0084\u0083\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0089\7\37\2\2\u0087\u0089\7\36\2\2\u0088\u0082\3"+
		"\2\2\2\u0088\u0084\3\2\2\2\u0088\u0087\3\2\2\2\u0089\u0091\3\2\2\2\u008a"+
		"\u0091\5\32\16\2\u008b\u0091\5*\26\2\u008c\u008d\t\3\2\2\u008d\u008e\5"+
		"&\24\2\u008e\u008f\t\4\2\2\u008f\u0091\3\2\2\2\u0090\u0088\3\2\2\2\u0090"+
		"\u008a\3\2\2\2\u0090\u008b\3\2\2\2\u0090\u008c\3\2\2\2\u0091\u0093\3\2"+
		"\2\2\u0092\u0094\5(\25\2\u0093\u0092\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"!\3\2\2\2\u0095\u0098\5 \21\2\u0096\u0098\5*\26\2\u0097\u0095\3\2\2\2"+
		"\u0097\u0096\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u009b\5\"\22\2\u009a\u0099"+
		"\3\2\2\2\u009a\u009b\3\2\2\2\u009b#\3\2\2\2\u009c\u009e\7\f\2\2\u009d"+
		"\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u00a2\5\""+
		"\22\2\u00a0\u00a2\5*\26\2\u00a1\u009f\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2"+
		"\u00a4\3\2\2\2\u00a3\u00a5\5$\23\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2"+
		"\2\2\u00a5%\3\2\2\2\u00a6\u00a8\5$\23\2\u00a7\u00a9\5&\24\2\u00a8\u00a7"+
		"\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\'\3\2\2\2\u00aa\u00ab\7\t\2\2\u00ab"+
		"\u00af\7!\2\2\u00ac\u00ae\7!\2\2\u00ad\u00ac\3\2\2\2\u00ae\u00b1\3\2\2"+
		"\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\3\2\2\2\u00b1\u00af"+
		"\3\2\2\2\u00b2\u00b3\7\r\2\2\u00b3)\3\2\2\2\u00b4\u00bb\5,\27\2\u00b5"+
		"\u00bb\5.\30\2\u00b6\u00bb\5\60\31\2\u00b7\u00bb\5\62\32\2\u00b8\u00bb"+
		"\5\64\33\2\u00b9\u00bb\5\66\34\2\u00ba\u00b4\3\2\2\2\u00ba\u00b5\3\2\2"+
		"\2\u00ba\u00b6\3\2\2\2\u00ba\u00b7\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00b9"+
		"\3\2\2\2\u00bb+\3\2\2\2\u00bc\u00bd\7\23\2\2\u00bd\u00be\7\24\2\2\u00be"+
		"\u00c3\7#\2\2\u00bf\u00c0\7\30\2\2\u00c0\u00c2\5&\24\2\u00c1\u00bf\3\2"+
		"\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4"+
		"\u00c6\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7\25\2\2\u00c7-\3\2\2\2"+
		"\u00c8\u00c9\7\27\2\2\u00c9\u00ca\7\24\2\2\u00ca\u00cb\7\36\2\2\u00cb"+
		"\u00cc\7\25\2\2\u00cc/\3\2\2\2\u00cd\u00ce\7\26\2\2\u00ce\u00cf\7\24\2"+
		"\2\u00cf\u00d0\7#\2\2\u00d0\u00d1\7\25\2\2\u00d1\61\3\2\2\2\u00d2\u00d3"+
		"\7\4\2\2\u00d3\u00d4\7\24\2\2\u00d4\u00d5\5&\24\2\u00d5\u00d6\7\25\2\2"+
		"\u00d6\63\3\2\2\2\u00d7\u00d8\7\32\2\2\u00d8\u00d9\7\24\2\2\u00d9\u00da"+
		"\5&\24\2\u00da\u00db\7\25\2\2\u00db\65\3\2\2\2\u00dc\u00dd\7\3\2\2\u00dd"+
		"\u00de\7\24\2\2\u00de\u00df\5&\24\2\u00df\u00e0\7\30\2\2\u00e0\u00e1\7"+
		"\36\2\2\u00e1\u00e2\7\30\2\2\u00e2\u00e3\7\36\2\2\u00e3\u00e4\7\25\2\2"+
		"\u00e4\67\3\2\2\2\24<BQtw\u0084\u0088\u0090\u0093\u0097\u009a\u009d\u00a1"+
		"\u00a4\u00a8\u00af\u00ba\u00c3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}