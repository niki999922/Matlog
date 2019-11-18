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
    public boolean calculate() {
        return leftArgument.calculate() | rightArgument.calculate();
    }

    @Override
    public int getHashCode() {
        return this.hashCode();
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
       return (leftArgument.hashCode() * 31 + rightArgument.hashCode()) * 31 + "|".hashCode();
   }

    @Override
    public Class<?> getRealClass() {
        return Or.class;
    }


    @Override
    public int getType() {
        return 3;
    }
}
