package matlog.expression.parser.operations;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public final class Not extends AbstractUnaryOperation{

    public Not(Expression argument) {
        super(argument);
        code = (argument.getCode()*29)%429496729;
    }

    @Override
    public String printExpresion() {
        return String.format("(!%s)", argument.printExpresion());
    }

    @Override
    public String printOriginal() {
        return String.format("!(%s)", argument.printOriginal());
    }

    @Override
    public int hashCode() {
        return code;
    }


    @Override
    public int getType() {
        return 4;
    }
}
