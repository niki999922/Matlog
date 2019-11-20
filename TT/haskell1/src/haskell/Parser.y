{
module Parser where

import Grammar
import Lexer
}

%name      parser
%tokentype { Token }
%error     { fail "Parse error" }
%monad     { Either String }{ >>= }{ return }

%token
	IDENT      { Ident $$ }
	DOT        { DotT }
	LAMBDA     { LambdaP }
	LEFTP      { LeftP }
	RIGHTP     { RightP }

%%

Expr :
	Application                             { ExprApl $1 }
	| Atom                                  { JustAtom $1 }
	| Application  LAMBDA  Ident DOT Expr   { Triple $1 $3 $5 }
	| LAMBDA  Ident DOT Expr                { TripleL $2 $4 }

Application :
	Atom                                    { Atoms $1 }
	| Application Atom                      { Binary $1 $2 }
	| Ident Ident                           { BinaryIdent $1 $2 }
	| {- empty -}                           { Nothings }

Atom :
  	Ident                                   { Markers $1 }
	| LEFTP Expr RIGHTP                     { LRBrack $2 }

Ident:
    IDENT                                   { Var $1 }
