package matlog.expression.parser;

import matlog.expression.parser.operations.*;
import matlog.expression.parser.values.Const;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */

public class ExpressionParser implements Parser {
    private String expression, value;
    private Token currentToken, nextToken;
    private int index;

    private enum Token {
        EMPTY, OPENING_PARENTHESIS, CLOSING_PARENTHESIS, CONST, END, IMPL, AND, OR, NOT
    }

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }
    }

    private boolean available(int count) {
        return index + count <= expression.length();
    }

    private int getBorder(int start) {
        while (start < expression.length() && (Character.isLetter(expression.charAt(start)) || Character.isDigit(expression.charAt(start)) || expression.charAt(start) == '\'')) {
            ++start;
        }
        return start;
    }

    private boolean assay(String pattern) {
        skipWhitespace();
        if (!available(pattern.length())) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (expression.charAt(index + i) != pattern.charAt(i)) {
                return false;
            }
        }
        index += pattern.length();
        return true;
    }

    private Token processToken() {
        skipWhitespace();
        if (!available(1)) {
            return Token.END;
        }
        if (assay("&")) {
            return Token.AND;
        } else if (assay("|")) {
            return Token.OR;
        } else if (assay("!")) {
            return Token.NOT;
        } else if (assay("->")) {
            return Token.IMPL;
        } else if (assay("(")) {
            return Token.OPENING_PARENTHESIS;
        } else if (assay(")")) {
            return Token.CLOSING_PARENTHESIS;
        } else {
            int end = getBorder(index);
            value = expression.substring(index, end);
            index = end;
            return Token.CONST;
        }
    }

    private Expression buildUnary() {
        Expression result = null;
        currentToken = nextToken;
        nextToken = processToken();
        //currentToken = processToken();
        switch (currentToken) {
            case CONST:
                result = new Const(value);
                currentToken = nextToken;
                nextToken = processToken();
                break;
            case NOT:
                result = new Not(buildUnary());
                break;
            case OPENING_PARENTHESIS:
                result = buildImplication();
                currentToken = nextToken;
                nextToken = processToken();
        }
        return result;
    }

    private Expression buildAnd() {
        Expression result = buildUnary();
        while (true) {
            switch (currentToken) {
                case AND:
                    result = new And(result, buildUnary());
                    break;
                default:
                    return result;
            }
        }
    }
    private Expression buildOr() {
        Expression result = buildAnd();
        while (true) {
            switch (currentToken) {
                case OR:
                    result = new Or(result, buildAnd());
                    break;
                default:
                    return result;
            }
        }
    }

    private Expression buildImplication() {
        Expression result = buildOr();
        while (true) {
            if (currentToken == Token.IMPL) {
                result = new Implication(result, buildImplication());
            } else {
                return result;
            }
        }
    }


    public Expression parse(String expression) {
        this.expression = expression;
        currentToken = Token.EMPTY;
        nextToken = Token.END;
        index = 0;
        nextToken = processToken();
        Expression result = buildImplication();
        return result;
    }
}