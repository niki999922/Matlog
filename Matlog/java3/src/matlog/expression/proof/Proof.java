package matlog.expression.proof;

import matlog.expression.parser.Expression;
import matlog.expression.parser.ExpressionParser;
import matlog.expression.parser.ExpressionShell;
import matlog.expression.parser.operations.Implication;
import matlog.expression.parser.operations.Not;
import matlog.expression.parser.values.Const;
import matlog.expression.proof.Exception.ProofException;

import java.util.*;
import java.util.stream.Collectors;

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
    private Map<Expression, Set<Expression>> rightPartMP = new HashMap<>();
    private Map<Expression, Integer> indexProof = new HashMap<>();
    private Expression toDeduce = null;
    private List<String> answer = null;
    private List<String> proofs = null;
    private Map<Expression, Set<Expression>> byRightPart = new HashMap<>();
    private Map<Expression, Integer> byIndex = new HashMap<>();
    private static Proofs proofsList = new Proofs();
    private static String Ar = "\\(A\\)";
    private static String Br = "\\(B\\)";
    private static String Cr = "\\(C\\)";

    public Proof(List<String> proofs, Expression toDeduce, List<Expression> hypothesis, List<String> external) {
        this.toDeduce = toDeduce;
        this.proofs = proofs;
        for (Expression e : hypothesis) {
            hypotheses.add(new Hypothesis(e));
        }
        answer = external;
    }

    public ExpressionParser getParser() {
        return expressionParser;
    }

    private void deduceMe() {
        answer.addAll(proofsList.proofs.get("se").stream().map(s -> s.replaceAll("A", toDeduce.printOriginal())).collect(Collectors.toList()));
        //answer.addAll(proofsList.proofs.get("se").stream().map(s -> s.replaceAll(Ar, toDeduce.printOriginal() + "000000000000000000000000000000000000000")).collect(Collectors.toList()));
    }

    private void deduceHypothesisOrAxiom(Expression expression) {
        answer.addAll(proofsList.proofs.get("ax").stream().map(s -> s.replaceAll(Ar, expression.printOriginal())).map(s -> s.replaceAll(Br, toDeduce.printOriginal())).collect(Collectors.toList()));
    }

    private void deduceModusPonens(Expression leftSon, Expression rightSon) {
        answer.addAll(proofsList.proofs.get("mp").stream().map(s -> s.replaceAll(Br, leftSon.printOriginal())).map(s -> s.replaceAll(Cr, rightSon.printOriginal())).map(s -> s.replaceAll(Ar, toDeduce.printOriginal())).collect(Collectors.toList()));
    }

    void deduce() {
        proofs.forEach(this::add);
    }

    private boolean checkMe(Expression expression) {
        if (expression.equals(toDeduce)) {
            deduceMe();
            return addExpressionF(expression);
        }
        return false;
    }

    private boolean checkHypotheses(Expression expression) {
        for (Hypothesis hypothesis : hypotheses) {
            if (hypothesis.getExpression().equals(expression)) {
                deduceHypothesisOrAxiom(expression);
                return addExpressionF(expression);
                //return addExpression(expression);
            }
        }
        return false;
    }

    private boolean checkAxioms(Expression expression) {
        int numberAxiom = Axiom.check(expression);
        if (numberAxiom >= 1) {
            deduceHypothesisOrAxiom(expression);
            return addExpressionF(expression);
        }
        return false;
    }


    private void checkModusPonens(Expression expression) {
        for (Expression e : byRightPart.get(expression)) {
            if ((e.getType() == 1) && (byIndex.containsKey(e.getLeftSon()))) {
                deduceModusPonens(e.getLeftSon(), expression);
            }
        }
        addExpressionF(expression);
    }

    private boolean addExpressionF(Expression expression) {
        if (expression.getType() == 1) {
            byRightPart.putIfAbsent(expression.getRightSon(), new HashSet<>());
            byRightPart.get(expression.getRightSon()).add(expression);
        }
        byIndex.putIfAbsent(expression, count++);
        return true;
    }

    private void add(String string) {
        Expression expression = expressionParser.parse(string);
        if (checkMe(expression)) return;
        if (checkHypotheses(expression)) return;
        if (checkAxioms(expression)) return;
        checkModusPonens(expression);
    }

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
            stringBuilder.append(stringJoiner.toString() + " ");
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
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), getNotNot(expression.getRightSon())), getNotNot(expression))).printOriginal());
        System.out.println(new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())).printOriginal());
        System.out.println(new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(expression.getRightSon(), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(expression.getRightSon())).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())).printOriginal());
        System.out.println(new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())))), new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), getNotNot(expression.getRightSon())))).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), new Implication(new Implication(new Not(expression.getRightSon()), new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon()))), getNotNot(expression.getRightSon()))), new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), getNotNot(expression.getRightSon()))).printOriginal());
        System.out.println(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), getNotNot(expression.getRightSon())).printOriginal());
        System.out.println(new Implication(new Implication(new Not(new Implication(getNotNot(expression.getRightSon()), expression.getRightSon())), getNotNot(expression.getRightSon())), getNotNot(expression)).printOriginal());
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
        if (checkOnHypothesis(expression)) return;
        if (checkOnAxiom(expression)) return;
        checkOnMP(expression);
    }

    public void checkLastEqualsStatement() throws ProofException {
        if (!proof.get(proof.size() - 1).getExpression().equals(endStatement)) {
            throw new ProofException("Last statement != begin statement");
        }
    }


    public static String printHypothesis(int input, boolean positive) {
        if (input == -10) {
            return "|- ";
        }
        int numberVariables = Expression.variables.size();
        for (int i = 0; i < numberVariables; i++) {
            boolean ithBit = (((input >> (numberVariables - i - 1)) & 1) == 1);
            Expression.variables.replace(Expression.basckMapToVariables.get(i), ithBit);
        }
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < numberVariables; i++) {
            String variable = Expression.basckMapToVariables.get(i);
            if ((!Expression.variables.get(variable)) && !positive) {
                joiner.add(new Not(new Const(variable)).printOriginal());
            } else if (Expression.variables.get(variable) && positive) {
                joiner.add(new Const(variable).printOriginal());
            }
        }
        return (joiner.toString() + " |- ");
    }


}