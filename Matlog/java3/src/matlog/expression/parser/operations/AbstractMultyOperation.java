package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public abstract class AbstractMultyOperation implements Expression {
    protected Expression leftArgument, rightArgument;
    protected int code;
    protected boolean state;

    AbstractMultyOperation(Expression leftArgument, Expression rightArgument) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        code = ((Integer) (leftArgument.getCode() + rightArgument.getCode())).hashCode();
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AbstractMultyOperation && ((AbstractMultyOperation) other).getRealClass() == getRealClass()) {
            AbstractMultyOperation op = (AbstractMultyOperation) other;
            return leftArgument.equals(op.leftArgument) && rightArgument.equals(op.rightArgument);
        }
        return false;
    }

    @Override
    public int getCode() {
        return code;
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
