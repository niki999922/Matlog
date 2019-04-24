import matlog.expression.parser.Expression;
import matlog.expression.parser.ExpressionParser;
import matlog.expression.parser.operations.Not;
import matlog.expression.parser.values.Const;
import matlog.expression.proof.Hypothesis;
import matlog.expression.proof.Proof;
import matlog.expression.proof.ProofGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
@SuppressWarnings("Duplicates")
public class EvidenceParser {
    private String inputLine = "";
    private ExpressionParser expressionParser;
    private ProofGenerator proofGenerator;
    private Expression expression;
    private Hypothesis hypothesis;
    private int numberHypothesisMask;
    private int numberVariables;
    private Queue<Integer> path;
    private Queue<Integer> secondDeduction = new ArrayDeque<>();
    private Queue<Integer> deductionPath = new ArrayDeque<>();
    private Queue<List<String>> proofs = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        new EvidenceParser().run();
    }

    public EvidenceParser() {
        expressionParser = new ExpressionParser();
        proofGenerator = new ProofGenerator();
    }

    private void init() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            inputLine = reader.readLine();
        }
        expression = expressionParser.parse(inputLine);
    }

    private void part1() {
        int index, tmp1, tmp2;
        while (path.size() > 1) {
            Expression firstExpression = null, secondExpression = null;
            index = -1;
            tmp1 = path.remove();
            tmp2 = path.remove();
            for (int i = 0; i < numberVariables; i++) {
                if ((((tmp1 >> (numberVariables - i - 1)) & 1) ^ ((tmp2 >> (numberVariables - i - 1)) & 1)) == 1) {
                    secondExpression = new Const(Expression.basckMapToVariables.get(i));
                    firstExpression = new Not(secondExpression);
                    index = i;
                    break;
                }
            }
            List<String> proof = new ArrayList<>();
            List<Expression> hypothesesList = hypothesis.expressionsFromMask(tmp1 & tmp2, index);
            hypothesis.setValues(tmp1);
            List<String> proof1 = proofGenerator.proveExpression(expression);
            proofGenerator.deduceProof(proof1, firstExpression, hypothesesList, proof);
            hypothesis.setValues(tmp2);
            List<String> proof2 = proofGenerator.proveExpression(expression);
            proofGenerator.deduceProof(proof2, secondExpression, hypothesesList, proof);
            proofGenerator.proveTL(secondExpression, expression, proof);
            deductionPath.offer(tmp1 & tmp2);
            proofs.offer(proof);
        }
    }

    private void part2() {
        int tmp1, tmp2, index;
        while (deductionPath.size() > 1) {
            Expression firstExpression = null, secondExpression = null;
            List<String> proof = new ArrayList<>();
            index = -1;
            tmp1 = deductionPath.remove();
            tmp2 = deductionPath.remove();
            for (int i = 0; i < numberVariables; i++) {
                if ((((tmp1 >> (numberVariables - i - 1)) & 1) ^ ((tmp2 >> (numberVariables - i - 1)) & 1)) == 1) {
                    secondExpression = new Const(Expression.basckMapToVariables.get(i));
                    firstExpression = new Not(secondExpression);
                    index = i;
                    break;
                }
            }
            List<Expression> hypothesesList = hypothesis.expressionsFromMask(tmp1 & tmp2, index);
            hypothesis.setValues(tmp1);
            proofGenerator.deduceProof(proofs.remove(), firstExpression, hypothesesList, proof);
            hypothesis.setValues(tmp2);
            proofGenerator.deduceProof(proofs.remove(), secondExpression, hypothesesList, proof);
            proofGenerator.proveTL(secondExpression, expression, proof);
            secondDeduction.offer(tmp1 & tmp2);
            proofs.offer(proof);
        }
    }

    private void part3() {
        int tmp1, tmp2, index;
        while (secondDeduction.size() > 1) {
            Expression firstExpression = null, secondExpression = null;
            List<String> proof = new ArrayList<>();
            index = -1;
            tmp1 = secondDeduction.remove();
            tmp2 = secondDeduction.remove();
            for (int i = 0; i < numberVariables; i++) {
                if ((((tmp1 >> (numberVariables - i - 1)) & 1) ^ ((tmp2 >> (numberVariables - i - 1)) & 1)) == 1) {
                    secondExpression = new Const(Expression.basckMapToVariables.get(i));
                    firstExpression = new Not(secondExpression);
                    index = i;
                    break;
                }
            }
            List<Expression> hypothesesList = hypothesis.expressionsFromMask(tmp1 & tmp2, index);
            hypothesis.setValues(tmp1);
            proofGenerator.deduceProof(proofs.remove(), firstExpression, hypothesesList, proof);
            hypothesis.setValues(tmp2);
            proofGenerator.deduceProof(proofs.remove(), secondExpression, hypothesesList, proof);
            proofGenerator.proveTL(secondExpression, expression, proof);
            proofs.offer(proof);
        }
    }


    private boolean createHypothesises() {
        hypothesis = new Hypothesis(expression, false, true);
        numberHypothesisMask = hypothesis.getRelativeHypothesis();
        if (numberHypothesisMask == -1) {
            expression = new Not(expression);
            hypothesis.setExpression(expression);
            hypothesis.setCondition(false);
            numberHypothesisMask = hypothesis.getRelativeHypothesis();
            if (numberHypothesisMask == -1) {
                return false;
            }
        }
        return true;
    }

    void run() throws IOException {
        init();
        if (!createHypothesises()) {
            System.out.println(":(");
            return;
        }

        System.out.print(Proof.printHypothesis(numberHypothesisMask, hypothesis.isCondition()));
        System.out.println(expression.printOriginal());
        path = hypothesis.createPath(path, numberHypothesisMask);
        numberVariables = Expression.variables.size();
        if (path.size() == 1) {
            hypothesis.setValues(numberHypothesisMask);
            List<String> proof = proofGenerator.proveExpression(expression);
            proofs.offer(proof);
        }
        part1();
        part2();
        part3();
        for (List<String> p : proofs) {
            p.stream().forEach(s -> System.out.println(s));
        }
    }

}
