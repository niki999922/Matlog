package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public abstract class AbstractMultyOperation implements Expression {
    protected Expression leftArgument, rightArgument;
    protected int code;

    AbstractMultyOperation(Expression leftArgument, Expression rightArgument) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        code = ((Integer) (leftArgument.getCode() + rightArgument.getCode())).hashCode();
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        return code == obj.hashCode();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getHashCode() {
        return ((Integer) (leftArgument.getHashCode() + rightArgument.getHashCode())).hashCode();
    }

    @Override
    public Expression getLeftSon() {
        return leftArgument;
    }

    @Override
    public Expression getRightSon() {
        return rightArgument;
    }
}
