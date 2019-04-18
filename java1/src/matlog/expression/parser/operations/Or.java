package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public final class Or extends AbstractMultyOperation {

    public Or(Expression leftArgument, Expression rightArgument) {
        super(leftArgument, rightArgument);
        code = (leftArgument.getCode() *23 + rightArgument.getCode()*37)%429496729;
    }

    @Override
    public String printExpresion() {
        return String.format("(|,%s,%s)", leftArgument.printExpresion(), rightArgument.printExpresion());
    }

    @Override
    public String printOriginal() {
        return String.format("(%s | %s)", leftArgument.printOriginal(), rightArgument.printOriginal());
    }

    @Override
    public int hashCode() {
        return code;
    }


    @Override
    public int getType() {
        return 3;
    }
}
