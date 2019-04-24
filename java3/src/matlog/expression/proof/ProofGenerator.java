package matlog.expression.proof;

import matlog.expression.parser.Expression;
import matlog.expression.parser.operations.Implication;
import matlog.expression.parser.operations.Not;
import matlog.expression.parser.operations.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kochetkov Nikita M3234
 * Date: 22.04.2019
 */
public class ProofGenerator {
    public Proofs proofsList = new Proofs();
    private String A = "\\(A\\)";
    private String B = "\\(B\\)";

    private void proveBigNegation(Expression expression, List<String> answer) {
        int depth = 0;
        if (expression.getType() != 4) return;
        while (expression.getType() == 4) {
            expression = expression.getLeftSon();
            depth++;
        }
        if (depth < 2) return;
        if (depth % 2 == 1) {
            expression = new Not(expression);
            depth--;
        }
        StringBuilder stringBuilder = new StringBuilder(expression.printOriginal());

        List<String> proof = proofsList.proofs.get("01");
        for (int i = 0; i < depth; i += 2) {
            final String str = stringBuilder.toString();
            answer.addAll(proof.stream().map(s -> s.replaceAll(A, str)).collect(Collectors.toList()));
            stringBuilder.insert(0, "!!");
        }
    }

    private void prove(Expression expression, List<String> answer) {
        if (expression.getType() == 0) {
            boolean state = Expression.variables.get(expression.printOriginal());
            expression.setState(state);
            answer.addAll(proofsList.proofs.get("0" + (state ? "1" : "0")).stream().map(s -> s.replaceAll(A, expression.printOriginal())).collect(Collectors.toList()));
            return;
        }
        if (expression.getType() == 4) {
            prove(expression.getRightSon(), answer);
            expression.setState(!(expression.getRightSon().getState()));
            return;
        }
        if ((expression.getType() >= 1) && (expression.getType() <= 3)) {
            Expression leftSon, rightSon;
            leftSon = expression.getLeftSon();
            rightSon = expression.getRightSon();
            prove(leftSon, answer);
            prove(rightSon, answer);
            proveBigNegation(((leftSon.getState()) ? leftSon : new Not(leftSon)), answer);
            proveBigNegation(((rightSon.getState()) ? rightSon : new Not(rightSon)), answer);
            answer.addAll(proofsList.proofs.get(Integer.toString(expression.getType()) + (leftSon.getState() ? "1" : "0") + ((rightSon.getState() ? "1" : "0"))).stream().map(s -> s.replaceAll(A, leftSon.printOriginal())).map(s -> s.replaceAll(B, rightSon.printOriginal())).collect(Collectors.toList()));
            switch (expression.getType()) {
                case 1: {
                    expression.setState(!(leftSon.getState()) | (rightSon.getState()));
                    break;
                }
                case 2: {
                    expression.setState((leftSon.getState()) & (rightSon.getState()));
                    break;
                }
                case 3: {
                    expression.setState((leftSon.getState()) | (rightSon.getState()));
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void proveTL(Expression expression, Expression provible, List<String> extern) {
        List<String> proof = proofsList.proofs.get("tl").stream().map(s -> s.replaceAll(B, expression.printOriginal())).collect(Collectors.toList());
        extern.addAll(proof);
        Expression neg = new Not(expression);
        Expression axiom8 = new Implication(new Implication(expression, provible), new Implication(new Implication(neg, provible), new Implication(new Or(expression, neg), provible)));
        extern.add(axiom8.printOriginal());
        Expression mp1 = new Implication(new Implication(neg, provible), new Implication(new Or(expression, neg), provible));
        extern.add(mp1.printOriginal());
        Expression mp2 = new Implication(new Or(expression, neg), provible);
        extern.add(mp2.printOriginal());
        extern.add(provible.printOriginal());
    }

    public List<String> proveExpression(Expression expression) {
        List<String> proof = new ArrayList<>();
        prove(expression, proof);
        proveBigNegation(expression, proof);
        return proof;
    }

    public void deduceProof(List<String> proof, Expression deductuble, List<Expression> hypotheses, List<String> external) {
        Proof proofer = new Proof(proof, deductuble, hypotheses, external);
        proofer.deduce();
    }
}
