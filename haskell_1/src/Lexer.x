{
module Lexer where
}

%wrapper "basic"

tokens :-

  $white+                    ;
  "#".*                      ;
  &                          { \s -> AndT }
  "->"                       { \s -> ImplT }
  !                          { \s -> NotT }
  \(                         { \s -> LeftP }
  \)                         { \s -> RightP }
  \|                         { \s -> OrT }
  [A-Z] [[A-Z] 0-9 \` \' ]*    { \s -> Ident s }

{
data Token = AndT | OrT | ImplT | NotT | LeftP | RightP | Ident String deriving (Show, Eq)
}