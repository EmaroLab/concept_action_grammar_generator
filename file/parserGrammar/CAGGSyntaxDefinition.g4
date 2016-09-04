grammar CAGGSyntaxDefinition;

options{
	language=Java;
//	output=AST;
//	ASTLabelType = CommonTree;
}

@header {  package it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated;}
 
/*tokens{
	PREDICATE; 
	FUNCTION;
}*/
 
// rule for the parse to generate treee !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



// starting rule 
nuanceGrammar 
	:	grammarHearder 
		(preamble)*
		(body)*  
		EOF
	; 

// the grammar header must be the first thing of the file to make the parser working
grammarHearder
	:	GRAMMAR_HEADER 
	;

// grammar preamble definition (it must be after the header and before the body) !(grammar, language, export, start, import, modifiable, activatable 
preamble
	:  (grammarDeclaration | languageDeclaration | exportDeclaration | importDeclaration |  startDeclaration| activableDeclaration | modifableDeclaration | pronounceDeclaration) ';'
	;
// possible declarations in the preamble: !(grammar, language, export, start, import, modifiable, activable, pronounce)
grammarDeclaration
	:	'!grammar' (IDENT | STRING_LITERAL)
	; 
languageDeclaration
	:	'!language' (IDENT | STRING_LITERAL)
	;	
exportDeclaration
	:	'!export' ruleDeclaration
	;
startDeclaration
	:	'!start' ruleDeclaration
	;
importDeclaration
	:	'!import' ruleDeclaration
	;
modifableDeclaration
	:	'!modifiable' ruleDeclaration ruleDeclaration 
	;
activableDeclaration
	:	'!activatable' ruleDeclaration ruleDeclaration
	;	
pronounceDeclaration
	:	'!pronounce' pronunceLabel ruleExpression
	;
pronunceLabel
	:	 STRING_LITERAL | (IDENT)*
	;
	
ruleDeclaration
	:	RULE_LITERAL
	;
	
// grammar body definition
body
	:	ruleDeclaration ':' ruleBody ';'	
	;

// definition of the expression evaluation (with '('')' and priority)
ruleBody
	:	ruleExpression
	;
ruleTerm 
	: 	//(actionDefinition)?
		(	( STRING_LITERAL | ( ('^')? IDENT ) | INTEGER) 					 
		  |	ruleDeclaration 		
		  |	ruleDirective 		 	
		  |	('(' | '[') (ruleExpression) (')' | ']') // in nuance [c] means !optional(c) in cagg not yet!!
		)
		(actionDefinition)? 
	;

andOperator
	:	(ruleTerm | ruleDirective) 
		(andOperator)?  
	;  
orOperator 
	:	('|')? 
		(andOperator | ruleDirective) 
		(orOperator)?	
	; 
ruleExpression
	:	(orOperator) (ruleExpression)?	
	;
	
      
// define the action tag (@) 
actionDefinition
	:	'{' TAG_LITERAL (TAG_LITERAL)* '}'
	;	

// definition of the rule directive that can be used on expressions !(label, id, action, pronounce, optional, repeat)
ruleDirective
	:	labelDirective | idDirective | actionDirective | pronounceDirective | optionalDirective | repeatDirective
	;
labelDirective
	:	'!label' '(' STRING_LITERAL (',' ruleExpression)* ')' // not ruleDirective or RuleExpression on it!!!!
	; 
idDirective
	:	'!id' '(' INTEGER ')' // not ruleDirective or RuleExpression on it!!!!
	;
actionDirective
	:	'!action' '(' STRING_LITERAL ')' // not ruleDirective on it!!!
	; 
pronounceDirective
	:	'!pronounce' '(' ruleExpression')' // not ruleDirective on it!!
	;
optionalDirective
	:	'!optional' '(' ruleExpression ')' // all possible expression and directive on it
	;
repeatDirective
	:	'!repeat' '(' ruleExpression ',' INTEGER ',' INTEGER ')'
	; 
 
 
// rule for the scanner !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// + : any combination ????	
// * : 0 or more
// ? : optional (0 or 1)
// . : all value 
// ~ : all value that are not '..'

// does not get any toker, it is just an helper to make this code more readable 
fragment LETTER :	('a' .. 'z' | 'A' .. 'Z' );
fragment ACCENT :	('\u00C4' .. '\u00FC');	 
fragment DIGIT :	('0' .. '9');
//fragment SYMB : 	('\'' | '"' | '|' |'+' | '-' | '*' | '/' | '>' | '<' | '<=' | '>=' | '!' | '=' | '.' | ',' | ';' | ':' | '(' | ')');

GRAMMAR_HEADER :	'#CAG v1;' | '#CAGv1;' | '#BNF+EMV1.0;' | '#BNF+EM V1.0;' | '#BNF+AM V1.0;' | '#BNF+AMV1.0;' | '#BNF+EMV2.1;' | '#BNF+EM V2.1;' | '#BNF+AMV2.1;' | '#BNF+AM V2.1;'; // | other ...

COMMENT	:			 '//' (~('\n'|'\r'))* ('\n'|'\r')* {setChannel( HIDDEN);};
MULTILINE_COMMENT :	'/*' .*? '*/' {setChannel( HIDDEN);};

INTEGER :			(DIGIT+);
IDENT :				((LETTER | ACCENT) (LETTER | DIGIT | ACCENT)*);  							
  
WS :				(' ' | '\n' | '\t' | '\r' | '\f')+ {setChannel( HIDDEN);}; // white space
 
TAG_LITERAL : 		'@' (.)*? ('=')? (.)*? ';'; 
RULE_LITERAL :		'<' .*? '>';
STRING_LITERAL :	'"' ( '"' '"' | ~('"' | '\r' | '\n') )* '"'; // " in string is  ""  (Nuance returns _ but use carefully !!!!!!)

BRACKETS:			'[' | '(' | ')' | ']';