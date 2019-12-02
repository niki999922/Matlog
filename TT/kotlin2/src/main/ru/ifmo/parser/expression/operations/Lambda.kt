package ru.ifmo.parser.expression.operations

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

    override fun bReduction() {}
}