package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable

data class Implication(var left: Node, var right: Node) : Node {
    override var type: Node? = null

    private var debugI = lazy {
        ++Node.debug_ind
    }

    override fun node() = "Implication"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun printOriginalNode(): String {
        return "(${left.printOriginalNode()} -> ${right.printOriginalNode()})"
    }

    override fun printNode(): String {
        return "(${left.printNode()} -> ${right.printNode()})"
    }

    override fun printType(): String {
        return "(${left.printType()} -> ${right.printType()})"
    }

    override fun printProof(listHypothesis: MutableList<Variable>, depth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initSystem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun containVariable(other: Variable): Boolean {
        return (left.containVariable(other) || right.containVariable(other))
    }

    override fun openTypeVariable(other: Variable, T: Node) {
        if (left is Variable) {
            if (left == other) {
                left = T
            }
        } else {
            left.openTypeVariable(other, T)
        }
        if (right is Variable) {
            if (right == other) {
                right = T
            }
        } else {
            right.openTypeVariable(other, T)
        }
    }

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {}

    override fun debugInd() = debugI.value
}