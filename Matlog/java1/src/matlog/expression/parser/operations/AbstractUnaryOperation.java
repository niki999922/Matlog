package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public abstract class AbstractUnaryOperation implements Expression {
    protected Expression argument;
    protected int code;
    protected boolean state;

    AbstractUnaryOperation(Expression argument) {
        this.argument = argument;
        code = ((Integer) argument.getCode()).hashCode();
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
    public int getHashCode() {
        return ((Integer) argument.getHashCode()).hashCode();
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
    public Expression getLeftSon() {
        return argument;
    }

    @Override
    public Expression getRightSon() {
        return argument;
    }
}
