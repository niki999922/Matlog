package matlog.expression.parser.values;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */

public class Const implements Expression {
    private final String name;
    private final int code;
    private boolean state;

    public Const(String name) {
        this.name = name;
        this.code = name.hashCode()*37%429496729;
        if (!variables.containsKey(name)) {
            variables.put(name, false);
            basckMapToVariables.putIfAbsent(basckMapToVariables.size(), name);
        }
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
    public boolean calculate() {
        return variables.get(name);
    }

    /*
    @Override
    public boolean equals(Object obj) {
        return code == obj.hashCode();
    }*/

    @Override
    public boolean equals(Object other) {
        if (other instanceof Const) {
            return name.equals(((Const) other).printOriginal());
        }
        return false;
    }

    @Override
    public Class<?> getRealClass() {
        return Const.class;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
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
