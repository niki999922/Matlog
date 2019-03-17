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
	IDENT  { Ident $$ }
	IMPL   { ImplT }
	OR     { OrT }
	AND    { AndT }
	NOT    { NotT }
	LEFTP  { LeftP }
	RIGHTP { RightP }

%%

Expr :
	Disj                 { $1 }
	| Disj IMPL Expr     { Binary Impl $1 $3 }

Disj :
	Conj                 { $1 }
	| Disj OR Conj       { Binary Or $1 $3 }

Conj :
	Term                 { $1 }
  	| Conj AND Term      { Binary And $1 $3 }

Term :
	NOT Term             { Not $2 }
  	| LEFTP Expr RIGHTP  { $2 }
  	| IDENT              { Var $1 }