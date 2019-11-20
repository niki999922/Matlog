{
module Lexer where
}

%wrapper "basic"

tokens :-

  $white+                        ;
  "#".*                          ;
  '\n'                           ;
  '\r'                           ;
  '\t'                           ;
  \.                             { \s -> DotT }
  \\                             { \s -> LambdaP }
  \(                             { \s -> LeftP }
  \)                             { \s -> RightP }
  [a-z] [[a-z] 0-9 \` \' \’ ]*   { \s -> Ident s }

{
data Token = DotT | LambdaP | LeftP | RightP | Ident String deriving (Show, Eq)
}