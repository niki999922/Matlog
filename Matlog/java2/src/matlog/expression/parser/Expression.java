package matlog.expression.parser;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */


public interface Expression {
    int getHashCode();
    String printExpresion();
    String printOriginal();
    Expression getLeftSon();
    Expression getRightSon();
    int getCode();

    /**
     *
     * @return 0 == const,
     * <p>
     * 1 == impl,
     * <p>
     * 2 == and,
     * <p>
     * 3 == or,
     * <p>
     * 4 == not
     */
    int getType();
    boolean equals(Object obj);
    int hashCode();
}
