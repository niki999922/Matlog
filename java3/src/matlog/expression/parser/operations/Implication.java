package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public final class Implication extends AbstractMultyOperation {

    public Implication(Expression leftArgument, Expression rightArgument) {
        super(leftArgument, rightArgument);
        code = (leftArgument.getCode() *17 + rightArgument.getCode()*4)%429496729;
    }

    @Override
    public String printExpresion() {
        return String.format("(->,%s,%s)", leftArgument.printExpresion(), rightArgument.printExpresion());
    }

    @Override
    public String printOriginal() {
        return String.format("(%s -> %s)", leftArgument.printOriginal(), rightArgument.printOriginal());
    }

    public String printOriginalWithoutBrackets() {
        return String.format("%s -> %s", leftArgument.printOriginal(), rightArgument.printOriginal());
    }


    @Override
    public int hashCode() {
        return code;
    }


    @Override
    public int getType() {
        return 1;
    }
}
