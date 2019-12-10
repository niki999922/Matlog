package ru.ifmo.parser.expression.values

import ru.ifmo.SystemTypes.freeVariables
import ru.ifmo.SystemTypes.systemTypeMap
import ru.ifmo.SystemTypes.typesMap
import ru.ifmo.parser.Node
import ru.ifmo.parser.Node.Companion.generateTypeName
import java.util.stream.Collectors

data class Variable(var node: String) : Node {
    private val originalName = node
    override var type: Node? = null

    private var debugI = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debugI.value

    override fun node() = "Variable"

    override fun printOriginalNode() = originalName

    override fun printNode() = node

    override fun printType(): String {
        if (type != null) return type!!.printType()
        var res = systemTypeMap[this]
        if (res != null) {
            if (res is Variable) {
                return res.node
            } else {
                return res.printType()
            }
        } else {
            return node
        }
    }

    override fun printProof(listHypothesis: MutableList<Variable>, depth: Int) {
        for (i in 0 until depth) {
            print("*   ")
        }

        val str: String = listHypothesis.stream().map {  "${it.printOriginalNode()} : ${problemOneVariable(it)}" }.collect(Collectors.joining(", "))
        if (str.length != 0) {
            print(str + " ")
        }
        println("|- ${printOriginalNode()} : ${problemOneVariable(this)} [rule #1]")
    }

    override fun initSystem() {
        if (typesMap[node] != null) {
            type = typesMap[node]!!
        } else {
            type = Variable(generateTypeName())
            typesMap[node] = type!!
            freeVariables.add(this)
        }
    }

    override fun containVariable(other: Variable): Boolean {
        return this == other
    }

    override fun openTypeVariable(other: Variable, T: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        node = listName[node] ?: node
    }
}