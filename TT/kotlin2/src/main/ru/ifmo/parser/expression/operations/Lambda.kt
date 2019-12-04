package ru.ifmo.parser.expression.operations

import ru.ifmo.A
import ru.ifmo.Painter
import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Lambda(var left: Node, var right: Node): Node {
    var parentNode: Node? = null
    var debug_i = lazy {
        ++Node.debug_ind
    }
    override fun debugInd() = debug_i.value

    override fun node()= "Lambda"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun parent(): Node?  = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun printNode() = "(\\${left.printNode()}.${right.printNode()})"

    override fun getBReduction(): Node? = right.getBReduction()

    override fun createCopy(): Node {
        var l = left.createCopy()
        var r = right.createCopy()
        l.setParent(this)
        r.setParent(this)
        return Lambda(l,r)
    }

    override fun openWrapper(listNode: MutableSet<NodeWrapper>): Node {
        if (right === left) return Lambda(left,right)
        while (!listNode.contains(right) && right is NodeWrapper) {
//        if (!listNode.contains(right) && right is NodeWrapper) {
            right = right.leftChild()!!
        }
        listNode.add(left as NodeWrapper)
//        right.openWrapper(listNode)
        var res = Lambda(left, right.openWrapper(listNode))
        listNode.remove(left as NodeWrapper)
        return res
    }

    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) {
        val leftVariable = left as Variable
        val oldVariableWrapper = listNode[leftVariable.printNode()]

        val newVariableWrapper = NodeWrapper(leftVariable)
        newVariableWrapper.setParent(this)
        newVariableWrapper.node.setParent(newVariableWrapper)
        left = newVariableWrapper
        listNode[leftVariable.printNode()] = newVariableWrapper

        if (right is Variable) {
            val rightVar = right as Variable
            listNode[rightVar.printNode()].let {
                if (it == null) return@let
                if (rightVar.printNode() == it.leftChild()!!.printNode()) {
                    right = it

                }
            }
        } else {
            right.setParent(this)
            right.normalizeLinks(listNode)
        }
//        Painter.draw(A.treeMy!!) // debug
//        Painter.draw(this) // debug
        if (oldVariableWrapper != null) {
            listNode[leftVariable.printNode()] = oldVariableWrapper
        } else {
            listNode.remove(leftVariable.printNode())
        }
    }

    override fun renameLambdaVariables() {
        ((left as NodeWrapper).node as Variable).node = "v${Node.indexVariable}"
        ++Node.indexVariable
        right.renameLambdaVariables()
    }

    override fun newRenameLambdaVariables(listNode: MutableMap<String, String>) {
        val leftVariable = left as Variable
        val oldValue = leftVariable.printNode()
        val oldVariable = listNode[oldValue]

        leftVariable.node = "v${Node.indexVariable}"
        ++Node.indexVariable

//        ((left as NodeWrapper).node as Variable).node = "v${Node.indexVariable}"
        listNode[oldValue] = leftVariable.printNode()
        right.newRenameLambdaVariables(listNode)

        if (oldVariable != null) {
            listNode[oldValue] = oldVariable
        } else {
            listNode.remove(oldValue)
        }
    }

    override fun bReduction() {}

    override fun oldCreateCopy(listNode: MutableMap<String, NodeWrapper>): Node {
        if (right === left) return Lambda(left.oldCreateCopy(listNode), right.oldCreateCopy(listNode))
        var rightNew = right
        while (rightNew is NodeWrapper) {
            rightNew = rightNew.leftChild()!!
        }

        return Lambda(left.oldCreateCopy(listNode), rightNew.oldCreateCopy(listNode))
    }
}