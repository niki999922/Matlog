package ru.ifmo

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.operations.Implication
import ru.ifmo.parser.expression.values.Variable
import java.util.*
import kotlin.collections.HashMap

object SystemTypes {
    var systemExpressions: LinkedList<Pair<Node, Node>> = LinkedList()
    lateinit var systemTypeMap: Map<Node, Node>
    var typesMap: HashMap<String, Node> = HashMap()
    var freeVariables = mutableListOf<Variable>()

    /**
     * @return false if type not found, other true
     */
    fun solveSystem():Boolean {
        try {
            while(checkRule1() || checkRule2() || checkRule3() || checkRule4()) {}
        } catch (e: Exception) {
            return false
        }
        systemTypeMap = systemExpressions.toMap()
        return true
    }

    /**
     * @return true if something change, other false
     */
    private fun checkRule1(): Boolean {
        val listEquals = mutableListOf<Pair<Node, Node>>()
        for (it in systemExpressions) {
            if (it.first !is Variable && it.second is Variable) {
                listEquals.add(it)
            }
        }
        if (listEquals.size == 0)  {
            return false
        }
        listEquals.forEach {
            systemExpressions.add(it.second to it.first)
            systemExpressions.remove(it)
        }
        return true
    }


    /**
     * @return true if something change, other false
     */
    private fun checkRule2(): Boolean {
        val listEquals = mutableListOf<Pair<Node, Node>>()
        for (it in systemExpressions) {
            if (it.first == it.second) {
                listEquals.add(it)
            }
        }
        if (listEquals.size == 0)  {
            return false
        }
        listEquals.forEach {
            systemExpressions.remove(it)
        }
        return true
    }

    /**
     * @return true if something change, other false
     */
    private fun checkRule3(): Boolean {
        val listEquals = mutableListOf<Pair<Node, Node>>()
        for (it in systemExpressions) {
            if (it.first is Implication && it.second is Implication) {
                listEquals.add(it)
            }
        }
        if (listEquals.size == 0)  {
            return false
        }
        listEquals.forEach {
            systemExpressions.add((it.first as Implication).left to (it.second as Implication).left)
            systemExpressions.add((it.first as Implication).right to (it.second as Implication).right)
            systemExpressions.remove(it)
        }
        return true
    }

    /**
     * @return true if something change, other false
     */
    private fun checkRule4(): Boolean {
        var flag = false
        for (it in systemExpressions) {
            if (it.first is Variable) {
                if (it.second.containVariable(it.first as Variable)) error("Expression has no type")
                for (it2 in systemExpressions) {
                    if (it === it2) continue
                    if (it2.first.containVariable(it.first as Variable) || it2.second.containVariable(it.first as Variable)) {
                        var needRemove = false
                        var first: Node?
                        var second: Node?

                        if (it2.first is Variable) {
                            if (it2.first == it.first) {
                                needRemove = true
                                first = it.second
                            } else {
                                first = it2.first
                            }
                        } else {
                            it2.first.openTypeVariable((it.first as Variable), it.second)
                            first = it2.first
                        }

                        if (it2.second is Variable) {
                            if (it2.second == it.first) {
                                needRemove = true
                                second = it.second
                            } else {
                                second = it2.second
                            }
                        } else {
                            it2.second.openTypeVariable((it.first as Variable), it.second)
                            second = it2.second
                        }
                        systemExpressions.add(first to second)

                        if (needRemove) {
                            systemExpressions.remove(it2)
                        }
//                        flag = true
                        return true //may be comment
                    }
                }
            }
        }
        return flag
    }
}