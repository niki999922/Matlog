package ru.ifmo

import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parser.expression.values.Variable

fun main() {
    val parser = ParserLambdaExpression()

//    val was = "x"
//    val was = "\\a.a' a z8'"
//    val was = "\\f.\\x.f (a x)"
//    val was = "(\\x. x a) (\\y. y b)"
//    val was = "(\\x. x) (\\y. y)"
    val was = "(\\x. x) (\\x. x)"
//    val was = "(\\x. y) (\\x. y)"
//    val was = "(\\x. x x x x)((\\x.  x)(\\x.  x))"


    var tree = parser.parse(was)
    tree.normalizeNamesLambda(mutableMapOf())
    tree.initSystem()

    val result = SystemTypes.solveSystem()
    if (!result) {
        println("Expression has no type")
        return
    }
    tree.printProof(ArrayList(SystemTypes.freeVariables),0)
}
