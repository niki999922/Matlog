package matlog.expression.proof;

import matlog.expression.parser.Expression;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
@SuppressWarnings("Duplicates")
final public class Axiom {
    private Expression expression;
    private int number;

    public Axiom(Expression expression, int number) {
        this.expression = expression;
        this.number = number;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getNumber() {
        return number;
    }

    private static boolean checkFirstAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null) //a1
                && (expression.getRightSon() != null)
                && (expression.getRightSon().getLeftSon() != null)  //b1
                && (expression.getRightSon().getRightSon() != null) //a2
                && (expression.getRightSon().getType() == 1)
                && (expression.getRightSon().getRightSon().getCode() == expression.getLeftSon().getCode())
        );
    }

    private static boolean checkSecondAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getLeftSon().getType() == 1)
                && (expression.getLeftSon().getLeftSon() != null)  //a1
                && (expression.getLeftSon().getRightSon() != null)  //b1
                && (expression.getRightSon() != null)
                && (expression.getRightSon().getType() == 1)
                && (expression.getRightSon().getLeftSon() != null)
                && (expression.getRightSon().getRightSon() != null)
                && (expression.getRightSon().getLeftSon().getType() == 1)
                && (expression.getRightSon().getLeftSon().getLeftSon() != null) //a2
                && (expression.getRightSon().getLeftSon().getRightSon() != null)
                && (expression.getRightSon().getLeftSon().getRightSon().getType() == 1)
                && (expression.getRightSon().getLeftSon().getRightSon().getLeftSon() != null)  //b2
                && (expression.getRightSon().getLeftSon().getRightSon().getRightSon() != null)   //c1
                && (expression.getRightSon().getRightSon() != null)
                && (expression.getRightSon().getRightSon().getType() == 1)
                && (expression.getRightSon().getRightSon().getLeftSon() != null)  //a3
                && (expression.getRightSon().getRightSon().getRightSon() != null)  //c2
                && (expression.getLeftSon().getLeftSon().getCode() == (expression.getRightSon().getLeftSon().getLeftSon().getCode()))
                && (expression.getRightSon().getLeftSon().getLeftSon().getCode() == expression.getRightSon().getRightSon().getLeftSon().getCode())
                && (expression.getLeftSon().getRightSon().getCode() == expression.getRightSon().getLeftSon().getRightSon().getLeftSon().getCode())
                && (expression.getRightSon().getLeftSon().getRightSon().getRightSon().getCode() == expression.getRightSon().getRightSon().getRightSon().getCode())
        );
    }

    private static boolean checkThirdAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null) //a1
                && (expression.getRightSon() != null)
                && (expression.getRightSon().getType() == 1)
                && (expression.getRightSon().getLeftSon() != null) //b1
                && (expression.getRightSon().getRightSon() != null)
                && (expression.getRightSon().getRightSon().getType() == 2)
                && (expression.getRightSon().getRightSon().getLeftSon() != null) //a2
                && (expression.getRightSon().getRightSon().getRightSon() != null) //b2
                && (expression.getLeftSon().getCode() == expression.getRightSon().getRightSon().getLeftSon().getCode())
                && (expression.getRightSon().getLeftSon().getCode() == expression.getRightSon().getRightSon().getRightSon().getCode())
        );
    }

    private static boolean checkFourthAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getRightSon() != null) //a2
                && (expression.getLeftSon().getType() == 2)
                && (expression.getLeftSon().getLeftSon() != null) //a1
                && (expression.getLeftSon().getRightSon() != null) //b1
                && (expression.getLeftSon().getLeftSon().getCode() == expression.getRightSon().getCode())
        );
    }

    private static boolean checkFifthAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getRightSon() != null) //b2
                && (expression.getLeftSon().getType() == 2)
                && (expression.getLeftSon().getLeftSon() != null) //a1
                && (expression.getLeftSon().getRightSon() != null) //b1
                && (expression.getLeftSon().getRightSon().getCode() == expression.getRightSon().getCode())
        );
    }

    private static boolean checkSixthAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null) //a1
                && (expression.getRightSon() != null)
                && (expression.getRightSon().getType() == 3)
                && (expression.getRightSon().getLeftSon() != null) //a2
                && (expression.getRightSon().getRightSon() != null) //b1
                && (expression.getLeftSon().getCode() == expression.getRightSon().getLeftSon().getCode())
        );
    }

    private static boolean checkSeventhAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null) //b1
                && (expression.getRightSon() != null)
                && (expression.getRightSon().getType() == 3)
                && (expression.getRightSon().getLeftSon() != null) //a1
                && (expression.getRightSon().getRightSon() != null) //b2
                && (expression.getLeftSon().getCode() == expression.getRightSon().getRightSon().getCode())
        );
    }

    private static boolean checkEightAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getRightSon() != null)
                && (expression.getLeftSon().getType() == 1)
                && (expression.getRightSon().getType() == 1)
                && (expression.getLeftSon().getLeftSon() != null) //a1
                && (expression.getLeftSon().getRightSon() != null) //b1
                && (expression.getRightSon().getLeftSon() != null)
                && (expression.getRightSon().getRightSon() != null)
                && (expression.getRightSon().getLeftSon().getType() == 1)
                && (expression.getRightSon().getRightSon().getType() == 1)
                && (expression.getRightSon().getLeftSon().getLeftSon() != null) //c1
                && (expression.getRightSon().getLeftSon().getRightSon() != null) //b2
                && (expression.getRightSon().getRightSon().getLeftSon() != null)
                && (expression.getRightSon().getRightSon().getRightSon() != null) //b3
                && (expression.getRightSon().getRightSon().getLeftSon().getType() == 3)
                && (expression.getRightSon().getRightSon().getLeftSon().getLeftSon() != null) //a2
                && (expression.getRightSon().getRightSon().getLeftSon().getRightSon() != null) //c2
                && (expression.getLeftSon().getLeftSon().getCode() == expression.getRightSon().getRightSon().getLeftSon().getLeftSon().getCode())
                && (expression.getRightSon().getLeftSon().getLeftSon().getCode() == expression.getRightSon().getRightSon().getLeftSon().getRightSon().getCode())
                && (expression.getLeftSon().getRightSon().getCode() == expression.getRightSon().getLeftSon().getRightSon().getCode())
                && (expression.getRightSon().getLeftSon().getRightSon().getCode() ==expression.getRightSon().getRightSon().getRightSon().getCode())
        );
    }

    private static boolean checkNinthAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getRightSon() != null)
                && (expression.getLeftSon().getType() == 1)
                && (expression.getRightSon().getType() == 1)
                && (expression.getLeftSon().getLeftSon() != null) //a1
                && (expression.getLeftSon().getRightSon() != null) //b1
                && (expression.getRightSon().getLeftSon() != null)
                && (expression.getRightSon().getRightSon() != null)
                && (expression.getRightSon().getLeftSon().getType() == 1)
                && (expression.getRightSon().getRightSon().getType() == 4)
                && (expression.getRightSon().getRightSon().getLeftSon() != null) //a3
                && (expression.getRightSon().getLeftSon().getLeftSon() != null) //a2
                && (expression.getRightSon().getLeftSon().getRightSon() != null)
                && (expression.getRightSon().getLeftSon().getRightSon().getType() == 4)
                && (expression.getRightSon().getLeftSon().getRightSon().getLeftSon() != null) //b2
                && (expression.getLeftSon().getLeftSon().getCode() == expression.getRightSon().getLeftSon().getLeftSon().getCode())
                && (expression.getRightSon().getLeftSon().getLeftSon().getCode() == expression.getRightSon().getRightSon().getLeftSon().getCode())
                && (expression.getLeftSon().getRightSon().getCode() == expression.getRightSon().getLeftSon().getRightSon().getLeftSon().getCode())
        );
    }

    private static boolean checkTenthAxiom(Expression expression) {
        return ((expression.getType() == 1)
                && (expression.getLeftSon() != null)
                && (expression.getRightSon() != null) //a2
                && (expression.getLeftSon().getType() == 4)
                && (expression.getLeftSon().getLeftSon() != null)
                && (expression.getLeftSon().getLeftSon().getType() == 4)
                && (expression.getLeftSon().getLeftSon().getLeftSon() != null) //a1
                && (expression.getLeftSon().getLeftSon().getLeftSon().getCode() == expression.getRightSon().getCode())
        );
    }

    /**
     *
     * @param expression
     * @return -1 if not found or number of Axiom
     */
    static public int check(Expression expression) {
        if (expression == null) return -1;
        if (checkFirstAxiom(expression))   return 1;
        if (checkSecondAxiom(expression))  return 2;
        if (checkThirdAxiom(expression))   return 3;
        if (checkFourthAxiom(expression))  return 4;
        if (checkFifthAxiom(expression))   return 5;
        if (checkSixthAxiom(expression))   return 6;
        if (checkSeventhAxiom(expression)) return 7;
        if (checkEightAxiom(expression))   return 8;
        if (checkNinthAxiom(expression))   return 9;
        if (checkTenthAxiom(expression))   return 10;
        return 0;
    }
}
