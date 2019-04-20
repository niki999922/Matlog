import matlog.expression.parser.Expression;
import matlog.expression.parser.ExpressionParser;
import matlog.expression.parser.Parser;
import matlog.expression.proof.Axiom;

import java.util.Arrays;


/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public class TestExpression {
    public static void main(String[] args) {
        Parser expressionParser = new ExpressionParser();
        Expression expression1 = expressionParser.parse("A->B->C");
        Expression expression2 = expressionParser.parse("!A&!B->!(A|B)");
        Expression expression3 = expressionParser.parse("P1’->!QQ->!R10&S|!T&U&V");
        Expression expression4 = expressionParser.parse("(A -> B) -> (A -> !B) -> !A");
        System.out.println("Test 1:");
        System.out.println(expression1.printExpresion());
        System.out.println("Test 2:");
        System.out.println("my   " + expression2.printExpresion());
        System.out.println("real (->,(&,(!A),(!B)),(!(|,A,B)))");
        System.out.println("Test 3:");
        System.out.println("my   " + expression3.printExpresion());
        System.out.println("real (->,P1’,(->,(!QQ),(|,(&,(!R10),S),(&,(&,(!T),U),V))))");
        System.out.println("original " + expression3.printOriginal());
        System.out.println("Test 4:");
        System.out.println("my   " + expression4.printOriginal());
        System.out.println("real ((A -> B) -> ((A -> !B) -> !A))");
        System.out.println("\nTesting Axioms...");
        System.out.println("Axiom 1 : " + ((Axiom.check(expressionParser.parse("a->b->a")) == 1) ? "work." : "don't work"));
        System.out.println("Axiom 2 : " + ((Axiom.check(expressionParser.parse("(a->b)->(a->b->c)->(a->c)")) == 2) ? "work." : "don't work"));
        System.out.println("Axiom 3 : " + ((Axiom.check(expressionParser.parse("a->b->a&b")) == 3) ? "work." : "don't work"));
        System.out.println("Axiom 4 : " + ((Axiom.check(expressionParser.parse("a&b->a")) == 4) ? "work." : "don't work"));
        System.out.println("Axiom 5 : " + ((Axiom.check(expressionParser.parse("a&b->b")) == 5) ? "work." : "don't work"));
        System.out.println("Axiom 6 : " + ((Axiom.check(expressionParser.parse("a->a|b")) == 6) ? "work." : "don't work"));
        System.out.println("Axiom 7 : " + ((Axiom.check(expressionParser.parse("b->a|b")) == 7) ? "work." : "don't work"));
        System.out.println("Axiom 8 : " + ((Axiom.check(expressionParser.parse("(a->b)->(c->b)->(a|c->b)")) == 8) ? "work." : "don't work"));
        System.out.println("Axiom 9 : " + ((Axiom.check(expressionParser.parse("(a->b)->(a->!b)->(!a)")) == 9) ? "work." : "don't work"));
        System.out.println("Axiom 10: " + ((Axiom.check(expressionParser.parse("!!a->a")) == 10) ? "work." : "don't work"));

        System.out.println(Arrays.toString("|-qqqq".split("\\|-")));
    }
}
