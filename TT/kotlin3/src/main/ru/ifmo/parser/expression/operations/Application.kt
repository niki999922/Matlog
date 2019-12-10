package ru.ifmo.parser.expression.operations

import ru.ifmo.SystemTypes.systemExpressions
import ru.ifmo.parser.Node
import ru.ifmo.parser.Node.Companion.generateTypeName
import ru.ifmo.parser.expression.values.Variable
import java.util.stream.Collectors

data class Application(var left: Node, var right: Node) : Node {
    override var type: Node? = null

    private var debugI = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debugI.value

    override fun node() = "Application"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun printOriginalNode(): String {
        return "(${left.printOriginalNode()} ${right.printOriginalNode()})"
    }

    override fun printNode(): String {
        return "(${left.printNode()} ${right.printNode()})"
    }

    override fun printType(): String {
        return problemOneVariable(this)
    }

    override fun printProof(listHypothesis: MutableList<Variable>, depth: Int) {
        for (i in 0 until depth) {
            print("*   ")
        }
        val str: String = listHypothesis.stream().map {  "${it.printOriginalNode()} : ${it.printType()}" }.collect(Collectors.joining(", "))
        if (str.length != 0) {
            print(str + " ")
        }
        println("|- ${printOriginalNode()} : ${printType()} [rule #2]")
        left.printProof(listHypothesis,depth + 1)
        right.printProof(listHypothesis,depth + 1)
    }

    override fun initSystem() {
        left.initSystem()
        right.initSystem()
        type = Variable(generateTypeName())
        systemExpressions.add(left.type!! to Implication(right.type!!, type!!))
    }

    override fun containVariable(other: Variable): Boolean {
        return (left.containVariable(other) || right.containVariable(other))
    }

    override fun openTypeVariable(other: Variable, T: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        if (left is Variable) {
            val leftVar = left as Variable
            listName[leftVar.node].let {
                if (it == null) return@let
                leftVar.node = it
            }
        } else {
            left.normalizeNamesLambda(listName)
        }

        if (right is Variable) {
            val rightVar = right as Variable
            listName[rightVar.node].let {
                if (it == null) return@let
                rightVar.node = it
            }
        } else {
            right.normalizeNamesLambda(listName)
        }
    }
}