package matlog.expression.parser.values;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */

public class Const implements Expression {
    private final String name;
    private final int code;

    public Const(String name) {
        this.name = name;
        this.code = name.hashCode()*37%429496729;
    }


    @Override
    public boolean equals(Object obj) {
        return code == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public int getHashCode() {
        return name.hashCode();
    }

    @Override
    public String printExpresion() {
        return name;
    }

    @Override
    public String printOriginal() {
        return name;
    }

    @Override
    public Expression getLeftSon() {
        return null;
    }

    @Override
    public Expression getRightSon() {
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getType() {
        return 0;
    }
}
