grammar LambdaExpression;

testexpr
    : WORD testexpr1
    | LB testexpr RB testexpr1
    | LAMBDA WORD DOT testexpr testexpr1
    ;

testexpr1
    : testexpr testexpr1
    | EMPTY
    ;

expression
    : LAMBDA WORD DOT expression
    | expression expression
    | WORD
    | LB expression RB
    ;

lambda
    : LAMBDA WORD DOT expression
    ;

WORD
    : [a-z1-9]+
    ;

LAMBDA
    : '\\'
    ;

DOT
    : '.'
    ;

LB
    : '('
    ;

RB
    : ')'
    ;

WS
   : [ \t\r\n]+ -> skip
   ;