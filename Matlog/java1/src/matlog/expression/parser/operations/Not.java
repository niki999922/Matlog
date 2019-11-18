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
    public boolean calculate() {
        return !argument.calculate();
    }

    @Override
    public String printExpresion() {
        return String.format("(!%s)", argument.printExpresion());
    }

    @Override
    public String printOriginal() {
        return "!" + argument.printOriginal();
    }

    @Override
    public int hashCode() {
        return argument.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Not) {
            Not neg = (Not) obj;
            return argument.equals(neg.argument);
        }
        return false;
    }

    @Override
    public Class<?> getRealClass() {
        return Not.class;
    }


    @Override
    public int getType() {
        return 4;
    }
}
