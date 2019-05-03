package matlog.expression.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */


public interface Expression {
    int getHashCode();
    String printExpresion();
    String printOriginal();
    boolean calculate();
    Expression getLeftSon();
    Expression getRightSon();
    void setState(boolean state);
    boolean getState();
    int getCode();
    Class<?> getRealClass();
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
    Map<String, Boolean> variables = new HashMap<>();
    Map<Integer, String> basckMapToVariables = new HashMap<>();
}
