package matlog.expression.proof;

import matlog.expression.parser.Expression;
import matlog.expression.parser.ExpressionParser;
import matlog.expression.parser.ExpressionShell;
import matlog.expression.parser.operations.Implication;
import matlog.expression.parser.operations.Not;
import matlog.expression.proof.Exception.ProofException;

import java.util.*;

import static matlog.expression.proof.MinusBrain.part;


/**
 * @author Kochetkov Nikita M3234
 * Date: 18.04.2019
 */
@SuppressWarnings("Duplicates")
public class Proof {
    private int count = 0;
    private Expression endStatement;
    static private ExpressionParser expressionParser = new ExpressionParser();
    private List<Hypothesis> hypotheses = new ArrayList<>();
    private List<ExpressionShell> proof = new ArrayList<>();
    private Expression a, t, x, y;

    private Map<Expression, Set<Expression>> rightPartMP = new HashMap<>();
    private Map<Expression, Integer> indexProof = new HashMap<>();

    public Proof(int count) {
        this.count = count;
    }

    public Proof() {
    }

    private void parseHypothesisAndProofStatement(String statement) throws ProofException {
        if (!statement.contains("|-"))
            throw new ProofException("Can'new  find |- in proof");
        final int index = statement.indexOf("|-");
        if (index == 0) {
            endStatement = expressionParser.parse(statement.substring(2));
        } else {
            for (String hypothesa : statement.substring(0, index).split(",")) {
                hypotheses.add(new Hypothesis(expressionParser.parse(hypothesa)));
            }
            endStatement = expressionParser.parse(statement.substring(index + 2));
        }
    }

    private void parseHypothesisAndProofStatementSafe(String statement) {
        final int index = statement.indexOf("|-");
        if (index == 0) {
            endStatement = expressionParser.parse(statement.substring(2));
        } else {
            for (String hypothesa : statement.substring(0, index).split(",")) {
                hypotheses.add(new Hypothesis(expressionParser.parse(hypothesa)));
            }
            endStatement = expressionParser.parse(statement.substring(index + 2));
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Hypothesis hypothesis : hypotheses) {
            stringJoiner.add(hypothesis.getExpression().printOriginal());
        }
        if (hypotheses.size() > 0) {
            stringBuilder.append(stringJoiner.toString());
        }
        stringBuilder.append("|- ").append(getNotNot(endStatement).printOriginal()).append(System.lineSeparator());
        System.out.print(stringBuilder.toString());
    }

    /**
     * @param expression of checking
     * @return true if found, or false if not found
     */
    private boolean checkOnHypothesis(Expression expression) {
        for (Hypothesis hypothesis : hypotheses) {
            if (hypothesis.getExpression().equals(expression)) {
                ExpressionShell expressionShell = new ExpressionShell(expression);
                expressionShell.setItHypothesis();
                expressionShell.setNumberHypothesis(hypothesis.getNumber());
                deduceNotation(expression);
                return addExpression(expressionShell);
            }
        }
        return false;
    }

    private void deduceNotation(Expression expression) {
        System.out.println(expression.printOriginal());
        System.out.println(new Implication(expression, new Implication(new Not(expression), expression)).printOriginal());
        System.out.println(new Implication(new Not(expression), expression).printOriginal());
        System.out.println(new Implication(new Not(expression), new Implication(new Not(expression), new Not(expression))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression), new Implication(new Not(expression), new Not(expression))), new Implication(new Implication(new Not(expression), new Implication(new Implication(new Not(expression), new Not(expression)), new Not(expression))), new Implication(new Not(expression), new Not(expression)))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression), new Implication(new Implication(new Not(expression), new Not(expression)), new Not(expression))), new Implication(new Not(expression), new Not(expression))).printOriginal());
        System.out.println(new Implication(new Not(expression), new Implication(new Implication(new Not(expression), new Not(expression)), new Not(expression))).printOriginal());
        System.out.println(new Implication(new Not(expression), new Not(expression)).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression), expression), new Implication(new Implication(new Not(expression), new Not(expression)), getNotNot(expression))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression), new Not(expression)), getNotNot(expression)).printOriginal());
        System.out.println(getNotNot(expression).printOriginal());
    }

    private void deduceTenth(Expression expression) {
        a = expression.getRightSon();
        t = new Not(new Implication(getNotNot(a), a));
        x = new Implication(new Implication(a, new Implication(getNotNot(a), a)), new Implication(new Implication(a, new Not(new Implication(getNotNot(a), a))), new Not(a)));
        y = new Implication(a, new Implication(getNotNot(a), a));
        System.out.println(new Implication(new Implication(t, new Not(a)), new Implication(new Implication(t, getNotNot(a)), getNotNot(expression))).printOriginal());
        System.out.println(x.printOriginal());
        System.out.println(new Implication(x, new Implication(t, x)).printOriginal());
        System.out.println(new Implication(t,x).printOriginal());
        System.out.println(y.printOriginal());
        System.out.println(new Implication(y, new Implication(t, y)).printOriginal());
        System.out.println(new Implication(t ,y).printOriginal());
        System.out.println(new Implication(new Implication(t, y), new Implication(new Implication(t, x), new Implication(t, new Implication(new Implication(a, new Not(new Implication(getNotNot(a), a))), new Not(a))))).printOriginal());
        System.out.println(new Implication(new Implication(t, x), new Implication(t, new Implication(new Implication(a, new Not(new Implication(getNotNot(a), a))), new Not(a)))).printOriginal());
        System.out.println(new Implication(t, new Implication(new Implication(a, new Not(new Implication(getNotNot(a), a))), new Not(a))).printOriginal());
        System.out.println(new Implication(t, new Implication(t, t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(t, t)), new Implication(new Implication(t, new Implication(new Implication(t, t), t)),new Implication(t, t))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Implication(t, t), t)),new Implication(t, t)).printOriginal());
        System.out.println(new Implication(t, new Implication(new Implication(t, t), t)).printOriginal());
        System.out.println(new Implication(t, t).printOriginal());
        System.out.println(new Implication(t, new Implication(a,t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(a,t)), new Implication(t, new Implication(t, new Implication(a,t)))).printOriginal());
        System.out.println(new Implication(t, new Implication(t, new Implication(a,t))).printOriginal());
        System.out.println(new Implication(new Implication(t, t), new Implication(new Implication(t, new Implication(t, new Implication(a,t))), new Implication(t, new Implication(a,t)))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(t, new Implication(a,t))), new Implication(t, new Implication(a,t))).printOriginal());
        System.out.println(new Implication(t, new Implication(a,t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(a,t)), new Implication(new Implication(t, new Implication(new Implication(a, t), new Not(a))) ,new Implication(t, new Not(a)))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Implication(a, t), new Not(a))) ,new Implication(t, new Not(a))).printOriginal());
        System.out.println(new Implication(t, new Not(a)).printOriginal());
        t = new Not(new Implication(getNotNot(a), a));
        x = new Implication(new Implication(new Not(a), new Implication(getNotNot(a), a)), new Implication(new Implication(new Not(a), new Not(new Implication(getNotNot(a), a))), getNotNot(a)));
        y = new Implication(new Not(a), new Implication(getNotNot(a), a));
        System.out.println(x.printOriginal());
        System.out.println(new Implication(x, new Implication(t, x)).printOriginal());
        System.out.println(new Implication(t,x).printOriginal());
        System.out.println(y.printOriginal());
        System.out.println(new Implication(y, new Implication(t, y)).printOriginal());
        System.out.println(new Implication(t ,y).printOriginal());
        System.out.println(new Implication(new Implication(t, y), new Implication(new Implication(t, x), new Implication(t, new Implication(new Implication(new Not(a), new Not(new Implication(getNotNot(a), a))), getNotNot(a))))).printOriginal());
        System.out.println(new Implication(new Implication(t, x), new Implication(t, new Implication(new Implication(new Not(a), new Not(new Implication(getNotNot(a), a))), getNotNot(a)))).printOriginal());
        System.out.println(new Implication(t, new Implication(new Implication(new Not(a), new Not(new Implication(getNotNot(a), a))), getNotNot(a))).printOriginal());
        System.out.println(new Implication(t, new Implication(t, t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(t, t)), new Implication(new Implication(t, new Implication(new Implication(t, t), t)),new Implication(t, t))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Implication(t, t), t)),new Implication(t, t)).printOriginal());
        System.out.println(new Implication(t, new Implication(new Implication(t, t), t)).printOriginal());
        System.out.println(new Implication(t, t).printOriginal());
        System.out.println(new Implication(t, new Implication(new Not(a),t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Not(a),t)), new Implication(t, new Implication(t, new Implication(new Not(a),t)))).printOriginal());
        System.out.println(new Implication(t, new Implication(t, new Implication(new Not(a),t))).printOriginal());
        System.out.println(new Implication(new Implication(t, t), new Implication(new Implication(t, new Implication(t, new Implication(new Not(a),t))), new Implication(t, new Implication(new Not(a),t)))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(t, new Implication(new Not(a),t))), new Implication(t, new Implication(new Not(a),t))).printOriginal());
        System.out.println(new Implication(t, new Implication(new Not(a),t)).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Not(a),t)), new Implication(new Implication(t, new Implication(new Implication(new Not(a), t), getNotNot(a))) ,new Implication(t, getNotNot(a)))).printOriginal());
        System.out.println(new Implication(new Implication(t, new Implication(new Implication(new Not(a), t), getNotNot(a))) ,new Implication(t, getNotNot(a))).printOriginal());
        System.out.println(new Implication(t, getNotNot(a)).printOriginal());
        System.out.println(new Implication(new Implication(t, getNotNot(a)), getNotNot(expression)).printOriginal());
        System.out.println(getNotNot(expression).printOriginal());
    }

    private void deduceMP(Expression expression, Expression leftSon) {
        part(expression, leftSon);
    }

    private Expression getNotNot(Expression expression) {
        return new Not(new Not(expression));
    }

    private boolean checkOnAxiom(Expression expression) {
        int numberAxiom = Axiom.check(expression);
        if (numberAxiom == 0) return false;
        ExpressionShell expressionShell = new ExpressionShell(expression);
        expressionShell.setItAxiom();
        expressionShell.setNumberAxiom(numberAxiom);
        if (expressionShell.getNumberAxiom() < 10) {
            deduceNotation(expression);
        } else {
            deduceTenth(expression);
        }
        return addExpression(expressionShell);
    }

    private boolean checkOnMP(Expression expression) {
        Set<Expression> expressionsFromRightPartMP = rightPartMP.get(expression);
        for (Expression expression1 : expressionsFromRightPartMP) {
            if (expression1.getType() == 1 && indexProof.containsKey(expression1.getLeftSon())) {
                ExpressionShell expressionShell = new ExpressionShell(expression);
                expressionShell.setItMP();
                deduceMP(expression, expression1.getLeftSon());
                return addExpression(expressionShell);
            }
        }
        return false;
    }

    private boolean addExpression(ExpressionShell expressionShell) {
        proof.add(expressionShell);
        if (expressionShell.getExpression().getType() == 1) {

            rightPartMP.putIfAbsent(expressionShell.getExpression().getRightSon(), new HashSet<>());

            rightPartMP.get(expressionShell.getExpression().getRightSon()).add(expressionShell.getExpression());
        }
        indexProof.putIfAbsent(expressionShell.getExpression(), count++);
        /*if (indexProof.containsKey(expressionShell.getExpression())) {
            indexProof.put(expressionShell.getExpression(), count);
        } else {
            indexProof.put(expressionShell.getExpression(), count++);
        }
        */

        return true;
    }

    public void createTreeUsage() {
        createTree(proof.get(proof.size() - 1));
    }

    private void createTree(ExpressionShell expressionShell) {
        expressionShell.setInResultProof();
        if (expressionShell.isItMP()) {
            createTree(proof.get(expressionShell.getNumberMPFirst() - 1));
            createTree(proof.get(expressionShell.getNumberMPSecond() - 1));
        }
    }

    public void printProof() {
        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Hypothesis hypothesis : hypotheses) {
            stringJoiner.add(hypothesis.getExpression().printOriginal());
        }
        if (hypotheses.size() > 0) {
            stringBuilder.append(stringJoiner.toString());
        }
        stringBuilder.append("|- ").append(endStatement.printOriginal()).append(System.lineSeparator());
        System.out.print(stringBuilder.toString());
        int newCounter = 1;
        Map<Integer, Integer> newIndexes = new HashMap<>();
        for (ExpressionShell expressionShell : proof) {
            if (expressionShell.isInResultProof()) {
                System.out.print("[" + newCounter + ". ");
                newIndexes.put(indexProof.get(expressionShell.getExpression()), newCounter);
                newCounter++;
                if (expressionShell.isItHypothesis()) {
                    System.out.print("Hypothesis " + expressionShell.getNumberHypothesis() + "] " + expressionShell.getExpression().printOriginal());
                }
                if (expressionShell.isItAxiom()) {
                    System.out.print("Ax. sch. " + expressionShell.getNumberAxiom() + "] " + expressionShell.getExpression().printOriginal());
                }
                if (expressionShell.isItMP()) {
                    System.out.print(String.format("M.P. %d, %d", newIndexes.get(expressionShell.getNumberMPSecond()), newIndexes.get(expressionShell.getNumberMPFirst())) + "] " + expressionShell.getExpression().printOriginal());
                }
                System.out.println();
            }
        }
    }

    public void addInsecure(String statement) throws ProofException {
        if (count == 0) {
            parseHypothesisAndProofStatement(statement);
            ++count;
            return;
        }
        Expression expression = expressionParser.parse(statement);
        if (checkOnHypothesis(expression)) return;
        if (checkOnAxiom(expression)) return;
        if (checkOnMP(expression)) return;
        throw new ProofException("It isn't hypothesis, axiom or MP");
    }

    public void addSafe(String statement) {
        if (count == 0) {
            parseHypothesisAndProofStatementSafe(statement);
            ++count;
            return;
        }
        Expression expression = expressionParser.parse(statement);
        System.out.println(expression.printOriginal());
        if (checkOnHypothesis(expression)) return;
        if (checkOnAxiom(expression)) return;
        checkOnMP(expression);
    }

    public void checkLastEqualsStatement() throws ProofException {
        if (!proof.get(proof.size() - 1).getExpression().equals(endStatement)) {
            throw new ProofException("Last statement != begin statement");
        }
    }
}