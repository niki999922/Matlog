package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public final class And extends AbstractMultyOperation {

    public And(Expression leftArgument, Expression rightArgument) {
        super(leftArgument, rightArgument);
        code = (leftArgument.getCode() *7 + rightArgument.getCode()*9)%429496729;
    }

    @Override
    public String printExpresion() {
        return String.format("(&,%s,%s)", leftArgument.printExpresion(), rightArgument.printExpresion());
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String printOriginal() {
        return String.format("(%s & %s)", leftArgument.printOriginal(), rightArgument.printOriginal());
    }

    @Override
    public int getType() {
        return 2;
    }
}
