package matlog.expression.proof;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 18.04.2019
 */
public class Hypothesis {
    private Expression expression;
    private int number;
    private static int globalNumber = 1;

    /**
     * Can have some problems with numbers of hypothesis
     *
     * @param expression
     * @param number
     */
    public Hypothesis(Expression expression, int number) {
        this.expression = expression;
        this.number = number;
    }

    public Hypothesis(Expression expression) {
        this.expression = expression;
        this.number = globalNumber++;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return expression.getCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hypothesis) {
            Hypothesis hypothesis = (Hypothesis) obj;
            return expression.equals(hypothesis.expression);
        }
        return false;
    }
}
