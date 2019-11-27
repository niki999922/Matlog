// Generated from LambdaExpression.g4 by ANTLR 4.7.2
package ru.ifmo.parserAntLr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LambdaExpressionParser}.
 */
public interface LambdaExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LambdaExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(LambdaExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(LambdaExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaExpressionParser#lambda}.
	 * @param ctx the parse tree
	 */
	void enterLambda(LambdaExpressionParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaExpressionParser#lambda}.
	 * @param ctx the parse tree
	 */
	void exitLambda(LambdaExpressionParser.LambdaContext ctx);
}