package matlog.expression.proof;

import matlog.expression.parser.Expression;
import matlog.expression.parser.operations.Not;
import matlog.expression.parser.values.Const;

import java.util.*;

/**
 * @author Kochetkov Nikita M3234
 * Date: 18.04.2019
 */
public class Hypothesis {
    private Expression expression;
    private int number;
    private static int globalNumber = 1;
    private int numberOfVariables = 0;
    private int mask;
    private boolean condition;
    private Set<Integer> failVariables = null;
    private List<Integer> realBits = null;


    /**
     * Can have some problems with numbers of hypothesis
     *
     * @param expression
     * @param number
     */
    public Hypothesis(Expression expression, int number) {
        this.expression = expression;
        this.number = number;
    }

    public Hypothesis(Expression expression) {
        this.expression = expression;
        this.number = globalNumber++;
    }

    public Hypothesis(Expression expression, boolean bool, boolean condition) {
        this.expression = expression;
        if (bool) {
            this.number = globalNumber++;
        } else {
            this.number = 0;
            numberOfVariables = Expression.variables.size();
            mask = (1 << numberOfVariables) - 1;
            this.condition = condition;
        }
    }


    public static int getGlobalNumber() {
        return globalNumber;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getMask() {
        return mask;
    }

    public boolean isCondition() {
        return condition;
    }

    public Set<Integer> getFailVariables() {
        return failVariables;
    }

    public List<Integer> getRealBits() {
        return realBits;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return expression.getCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hypothesis) {
            Hypothesis hypothesis = (Hypothesis) obj;
            return expression.equals(hypothesis.expression);
        }
        return false;
    }

    private int createMask(int a, int b) {
        if (condition) {
            return (a | b);
        } else {
            return (a & b);
        }
    }

    public List<Expression> expressionsFromMask(int numr, int exceptNumber) {
        List<Expression> hypotheses = new ArrayList<>();
        int size = Expression.variables.size();
        for (int j = 0; j < size; j++) {
            if (j == exceptNumber) continue;
            Expression hypothesis = new Const(Expression.basckMapToVariables.get(j));
            if (((numr >> (size - j - 1)) & 1) == 0) {
                hypothesis = new Not(hypothesis);
            }
            hypotheses.add(hypothesis);
        }
        return hypotheses;
    }

    private void addFull() {
        for (int j = 0; j < 4; j++) {
            failVariables.add(j);
        }
    }

    private Set<Integer> checkTable(int constantBit) {
        boolean result;
        int goodVars;
        failVariables = new HashSet<>();
        for (int i = 0; i < (1 << numberOfVariables); i++) {
            int input = createMask(i, constantBit);
            setValues(input);
            result = expression.calculate();
            if (!result) {
                goodVars = 0;
                for (int j = 0; j < numberOfVariables; j++) {
                    if (((input >> (numberOfVariables - j - 1)) & 1) == (condition ? 0 : 1)) {
                        failVariables.add(j);
                    } else {
                        goodVars++;
                    }
                }
                if (goodVars == numberOfVariables) {
                    addFull();
                }
            }
        }
        return failVariables;
    }

    public void setValues(int maskValues) {
        for (int i = 0; i < numberOfVariables; i++) {
            Expression.variables.replace(Expression.basckMapToVariables.get(i), (((maskValues >> (numberOfVariables - i - 1)) & 1) == 1));
        }
    }

    public Queue<Integer> createPath(Queue<Integer> path, int hypotheses) {
        path = new ArrayDeque<>();
        if (hypotheses == -10) {
            for (int i = 0; i < (1 << numberOfVariables); i++) {
                path.add(i);
            }
            return path;
        }
        for (int i = 0; i < (1 << numberOfVariables); i++) {
            setValues(i);

            if (expression.calculate()) {
                if (condition) {
                    if ((i & hypotheses) == hypotheses) {
                        path.add(i);
                    }
                } else {
                    if ((i | hypotheses) == hypotheses) {
                        path.add(i);
                    }
                }
            }
        }
        return path;
    }


    /**
     * @return -1 if bad =((
     */
    public int getRelativeHypothesis() {
        realBits = new ArrayList<>();
        List<Integer> failed = new ArrayList<>(checkTable(condition ? 0 : mask));
        if (failed.size() == 4 || checkTable(7).size() == 4 && condition || checkTable(0).size() == 4 && !condition) {
            return -1;
        }
        int size = failed.size();
        int a, b, c;
        switch (size) {
            case 1: {
                a = 1 << (numberOfVariables - failed.get(0) - 1);
                realBits.add(((condition) ? a : (mask & ~a)));
                break;
            }
            case 2: {
                b = 1 << (numberOfVariables - failed.get(1) - 1);
                a = 1 << (numberOfVariables - failed.get(0) - 1);
                realBits.addAll(((condition) ? Arrays.asList(a, b, a | b) : Arrays.asList(mask & ~a, mask & ~b, mask & ~(a | b))));
                break;
            }
            case 3: {
                c = 1 << (numberOfVariables - failed.get(2) - 1);
                b = 1 << (numberOfVariables - failed.get(1) - 1);
                a = 1 << (numberOfVariables - failed.get(0) - 1);
                realBits.addAll(((condition) ? Arrays.asList(a, b, c, a | b, a | c, b | c, a | b | c) : Arrays.asList(mask & ~a, mask & ~b, mask & ~c, mask & ~(a | b), mask & ~(a | c), mask & ~(b | c), mask & ~(a | b | c))));
                break;
            }
            default:
                break;
        }
        for (int realB : realBits) {
            if ((checkTable(realB)).size() == 0) {
                return realB;
            }
        }
        return -10;
    }

}
