package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Application(var left: Node, var right: Node) : Node {
    var parentCount = 0
    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Application"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun addParentCount() {
        ++parentCount
    }

    override fun printNode(): String {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            right = (right as NodeWrapper).node
        }

        return "(${left.printNode()} ${right.printNode()})"
    }

    override fun getBReduction(nodeTmp: NodeWrapper): Node? {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            right = (right as NodeWrapper).node
        }

        if (left is Lambda) {
            var copy = left.createCopy()
            left = copy
            left.addParentCount()
            left.normalizeNamesLambda(mutableMapOf())
            return this
        }
        if (left is NodeWrapper) {
            var l = left as NodeWrapper
            while (l.node is NodeWrapper) {
                l = l.node as NodeWrapper
            }
            if (l.node is Lambda) {
                var copy = l.createCopy()
                left = copy
                left.addParentCount()
                left.normalizeNamesLambda(mutableMapOf())
                return this
            }
            left = l
            nodeTmp.node = this
            val resLeft = l.getBReduction(nodeTmp)
            if (resLeft != null) {
                return resLeft
            }
            nodeTmp.node = this
            return right.getBReduction(nodeTmp)
        }

        nodeTmp.node = this
        val resLeft = left.getBReduction(nodeTmp)
        if (resLeft != null) {
            return resLeft
        }
        nodeTmp.node = this
        return right.getBReduction(nodeTmp)
    }

    override fun createCopy(): Node {
        var l = left.createCopy()
        var r = right.createCopy()

        return Application(l, r)
    }

    override fun getValueParentCount() = parentCount

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

        left.addParentCount()
        right.addParentCount()
    }

    override fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper) {
        if (left is Variable) {
            val leftVar = left as Variable
            if (leftVar.node == name) {
                left = nodeWrapper
                left.addParentCount()
            }
        } else {
            left.setWrapperInVariable(name, nodeWrapper)
        }

        if (right is Variable) {
            val rightVar = right as Variable
            if (rightVar.node == name) {
                this.right = nodeWrapper
                right.addParentCount()
            }
        } else {
            right.setWrapperInVariable(name, nodeWrapper)
        }
    }

    override fun bReduction(parent: Node) {
        val leftLambda = left as Lambda
        val lambdaArg = (leftLambda.leftChild() as Variable)
        val newWrapper = NodeWrapper(right)

        if (leftLambda.right is Variable) {
            val rightVar = leftLambda.right as Variable
            if (rightVar.node == lambdaArg.node) {
                leftLambda.right = newWrapper
                leftLambda.right.addParentCount()
            }
        } else {
            leftLambda.right.setWrapperInVariable(lambdaArg.node, newWrapper)
        }

        if (parent is NodeWrapper) {
            parent.node = leftLambda.right
            return
        }
        if (parent is Application) {
            if (parent.leftChild() === this) {
                parent.left = leftLambda.rightChild()
            } else {
                parent.right = leftLambda.rightChild()
            }
            return
        }
        if (parent is Lambda) {
            parent.right = leftLambda.rightChild()
            return
        }
    }
}