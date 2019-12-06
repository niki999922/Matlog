package ru.ifmo.parser.expression.operations

import ru.ifmo.Painter
import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Lambda(var left: Node, var right: Node): Node {
    var parentNode: Node? = null
    var parentCount = 0

    override fun node()= "Lambda"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun parent(): Node?  = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun printNode(): String {
        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }

        return "(\\${left.printNode()}.${right.printNode()})"
    }

    override fun getBReduction(): Node? {
        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }

        return right.getBReduction()
    }

    override fun createCopy(): Node {
        var l = left.createCopy()
        var r = right.createCopy()
        l.setParent(this)
        r.setParent(this)
        return Lambda(l,r)
    }
    override fun addParentCount() {
        ++parentCount
    }

    override fun getValueParentCount() = parentCount


    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) {
        val leftVariable = left as Variable
        val oldVariableWrapper = listNode[leftVariable.printNode()]

        val newVariableWrapper = NodeWrapper(leftVariable)
        newVariableWrapper.setParent(this)
        newVariableWrapper.node.setParent(newVariableWrapper)
        newVariableWrapper.addParentCount()
        newVariableWrapper.node.addParentCount()
        left = newVariableWrapper

        listNode[leftVariable.printNode()] = newVariableWrapper

        if (right is Variable) {
            val rightVar = right as Variable
            listNode[rightVar.printNode()].let {
                if (it == null) return@let
                if (rightVar.printNode() == it.leftChild().printNode()) {
                    right = it
                    right.addParentCount()

                }
            }
        } else {
            right.setParent(this)
            right.normalizeLinks(listNode)
            right.addParentCount()
        }
        if (oldVariableWrapper != null) {
            listNode[leftVariable.printNode()] = oldVariableWrapper
        } else {
            listNode.remove(leftVariable.printNode())
        }
    }

    override fun renameLambdaVariables() {
        ((left as NodeWrapper).node as Variable).node = "vtyh56${Node.indexVariable}"
        ++Node.indexVariable
        right.renameLambdaVariables()
    }

    override fun bReduction() {}
}