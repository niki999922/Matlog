package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Lambda(var left: Node, var right: Node) : Node {
    var parentNode: Node? = null
    var parentCount = 0
    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Lambda"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun parent(): Node? = parentNode

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
        return Lambda(l, r)
    }

    override fun addParentCount() {
        ++parentCount
    }

    override fun subParentCount() {
        --parentCount
    }

    override fun setValueParentCount(value: Int) {
        parentCount = value
    }


    override fun openWrapper(listNode: MutableSet<NodeWrapper>): Node {
        if (right === left) return Lambda(left, right)
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

    override fun getValueParentCount() = parentCount


    override fun deleteNaxerWrappers() {
        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }
        right.deleteNaxerWrappers()
        left.deleteNaxerWrappers()
    }


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
//        Painter.draw(A.treeMy!!) // debug
//        Painter.draw(this) // debug
        if (oldVariableWrapper != null) {
            listNode[leftVariable.printNode()] = oldVariableWrapper
        } else {
            listNode.remove(leftVariable.printNode())
        }
    }

    override fun normalizeLambdaLink(lambdaArgument: NodeWrapper) {
        if ((left as Variable).printNode() == lambdaArgument.node.printNode()) {
            return
        }
        right.normalizeLambdaLink(lambdaArgument)

        if (right is Variable) {
            if (right.printNode() == lambdaArgument.printNode()) {
                right = lambdaArgument
            }
        } else {
            right.setParent(this)
            right.normalizeLambdaLink(lambdaArgument)
        }
    }

    override fun renameLambdaVariables() {
//        if (left is NodeWrapper) {
//            ((left as NodeWrapper).node as Variable).node = "vtyh56${Node.indexVariable}"
//            ++Node.indexVariable
//        }
//        right.renameLambdaVariables()


        ((left as NodeWrapper).node as Variable).node = "vtyh56${Node.indexVariable}"
        ++Node.indexVariable
        right.renameLambdaVariables()
    }

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        val leftVariable = left as Variable
        leftVariable.addParentCount()
        val oldVariableWrapper = listName[leftVariable.node]

        val newName = "vtyh56${Node.indexVariable}"
        ++Node.indexVariable
        val oldName = leftVariable.node

        leftVariable.node = newName

        listName[oldName] = newName

        if (right is Variable) {
            val rightVar = right as Variable
            listName[rightVar.node].let {
                if (it == null) return@let
                rightVar.node = it
                rightVar.addParentCount()
                rightVar.setParent(this)
            }
        } else {
            right.normalizeNamesLambda(listName)
            right.setParent(this)
            right.addParentCount()
        }

        if (oldVariableWrapper != null) {
            listName[oldName] = oldVariableWrapper
        } else {
            listName.remove(oldName)
        }
    }

    override fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper) {
        if ((left as Variable).node == name) return
        if (right is Variable) {
            val rightVar = right as Variable
            if (rightVar.node == name) {
                right = nodeWrapper
                right.addParentCount()
            }
        } else {
            right.setWrapperInVariable(name,nodeWrapper)
        }
    }

    override fun newRenameLambdaVariables(listNode: MutableMap<String, String>) {
        val leftVariable = left as Variable
        val oldValue = leftVariable.printNode()
        val oldVariable = listNode[oldValue]

        leftVariable.node = "vtyh56${Node.indexVariable}"
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
            rightNew = rightNew.leftChild()
        }

        return Lambda(left.oldCreateCopy(listNode), rightNew.oldCreateCopy(listNode))
    }
}