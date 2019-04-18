package matlog.expression.proof;

import matlog.expression.parser.Expression;
import matlog.expression.parser.ExpressionParser;
import matlog.expression.parser.ExpressionShell;
import matlog.expression.proof.Exception.ProofException;

import java.util.*;

/**
 * @author Kochetkov Nikita M3234
 * Date: 18.04.2019
 */
public class Proof {
    private int count = 0;
    private Expression endStatement;
    static private ExpressionParser expressionParser = new ExpressionParser();
    private List<Hypothesis> hypotheses = new ArrayList<>();
    private List<ExpressionShell> proof = new ArrayList<>();
    private Map<Expression, Set<Expression>> rightPartMP = new HashMap<>();
    private Map<Expression, Integer> indexProof = new HashMap<>();

    public Proof(int count) {
        this.count = count;
    }

    public Proof() {
    }

    private void parseHypothesisAndProofStatement(String statement) throws ProofException {
        if (!statement.contains("|-")) throw new ProofException("Can't find |- in proof");
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
                return addExpression(expressionShell);
            }
        }
        return false;
    }

    private boolean checkOnAxiom(Expression expression) {
        int numberAxiom = Axiom.check(expression);
        if (numberAxiom == 0) return false;
        ExpressionShell expressionShell = new ExpressionShell(expression);
        expressionShell.setItAxiom();
        expressionShell.setNumberAxiom(numberAxiom);
        return addExpression(expressionShell);
    }

    private boolean checkOnMP(Expression expression) {
        if (!rightPartMP.containsKey(expression)) {
            return false;
        }
        Set<Expression> expressionsFromRightPartMP = rightPartMP.get(expression);
        for (Expression expression1 : expressionsFromRightPartMP) {
            if (expression1.getType() == 1 && indexProof.containsKey(expression1.getLeftSon())) {
                Integer index = indexProof.get(expression1.getLeftSon());
                ExpressionShell expressionShell = new ExpressionShell(expression);
                expressionShell.setItMP();
                expressionShell.setNumberMP(index, indexProof.get(expression1));
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
            stringJoiner.add(hypothesis.getExpression().toString());
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

    public void checkLastEqualsStatement() throws ProofException {
        //System.out.println(endStatement.printOriginal());
        //System.out.println(proof.get(proof.size() - 1).getExpression().printOriginal());
        if (!proof.get(proof.size() - 1).getExpression().equals(endStatement)) {
            throw new ProofException("Last statement != begin statement");
        }
    }
}