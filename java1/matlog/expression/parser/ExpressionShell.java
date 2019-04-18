package matlog.expression.parser;

/**
 * @author Kochetkov Nikita M3234
 * Date: 18.04.2019
 */
public class ExpressionShell {
    private Expression expression = null;
    private int numberHypothesis = 0;
    private int numberAxiom = 0;
    private int numberMPFirst, numberMPSecond;
    private boolean itHypothesis = false;
    private boolean itAxiom = false;
    private boolean itMP = false;

    public ExpressionShell(Expression expression) {
        this.expression = expression;
    }

    public ExpressionShell(int numberHypothesis, int numberAxiom) {
        this.numberHypothesis = numberHypothesis;
        this.numberAxiom = numberAxiom;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getNumberHypothesis() {
        return numberHypothesis;
    }

    public int getNumberAxiom() {
        return numberAxiom;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setNumberHypothesis(int numberHypothesis) {
        this.numberHypothesis = numberHypothesis;
    }

    public void setNumberAxiom(int numberAxiom) {
        this.numberAxiom = numberAxiom;
    }

    public void setNumberMPFirst(int numberMPFirst) {
        this.numberMPFirst = numberMPFirst;
    }

    public void setNumberMPSecond(int numberMPSecond) {
        this.numberMPSecond = numberMPSecond;
    }

    public void setNumberMP(int numberMPFirst, int numberMPSecond) {
        this.numberMPFirst = numberMPFirst;
        this.numberMPSecond = numberMPSecond;
    }

    public void setItHypothesis() {
        this.itHypothesis = true;
    }

    public void setItAxiom() {
        this.itAxiom = true;
    }

    public void setItMP() {
        this.itMP = true;
    }

    public int getNumberMPFirst() {
        return numberMPFirst;
    }

    public int getNumberMPSecond() {
        return numberMPSecond;
    }

    public boolean isItHypothesis() {
        return itHypothesis;
    }

    public boolean isItAxiom() {
        return itAxiom;
    }

    public boolean isItMP() {
        return itMP;
    }
}
