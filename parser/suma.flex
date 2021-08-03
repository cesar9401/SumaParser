package com.cesar31.sumaparser.parser;

import static com.cesar31.sumaparser.parser.SumaParserSym.*;
import java_cup.runtime.Symbol;

%%

%class SumaLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column

%{
		private Symbol symbol(int type) {
			return new Symbol(type, yyline + 1, yycolumn + 1);
		}

		private Symbol symbol(int type, Object object) {
			return new Symbol(type, yyline + 1, yycolumn + 1, object);
		}
%}

%eofval{
	return symbol(EOF);
%eofval}
%eofclose

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* Integer */
Integer = 0|[1-9][0-9]*

%%

<YYINITIAL> {

	{Integer}
	{ return symbol(INTEGER, Integer.valueOf(yytext())); }

	"+"
	{ return symbol(PLUS); }

	"*"
	{ return symbol(TIMES); }

	"("
	{ return symbol(LPAREN); }

	")"
	{ return symbol(RPAREN); }

	{WhiteSpace}
	{ /* Ignore */ }
}

[^]
{
	System.out.println("Error: " + yytext());
	return symbol(ERROR, yytext());
}
