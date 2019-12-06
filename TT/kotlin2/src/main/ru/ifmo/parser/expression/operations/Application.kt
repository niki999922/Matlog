package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Application(var left: Node, var right: Node) : Node {
    var parentNode: Node? = null
    var parentCount = 0
    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Application"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun addParentCount() {
        ++parentCount
    }

    override fun subParentCount() {
        --parentCount
    }

    override fun printNode(): String {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }

        return "(${left.printNode()} ${right.printNode()})"
    }

    override fun getBReduction(): Node? {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }


        if (right is Application) {
            var r = right as Application
            if (r.left is NodeWrapper && left is NodeWrapper && ((left as NodeWrapper).node === (r.left as NodeWrapper).node)) {
                r.left = left
            }
        }


        if (right is Application) {
            var r = right as Application
            if (r.right is NodeWrapper && left is NodeWrapper && ((left as NodeWrapper).node === (r.right as NodeWrapper).node)) {
                r.right = left
            }
        }


        if (left is Application) {
            var l = left as Application
            if (l.left is NodeWrapper && right is NodeWrapper && ((right as NodeWrapper).node === (l.left as NodeWrapper).node)) {
                l.left = right
            }
        }


        if (left is Application) {
            var l = left as Application
            if (l.right is NodeWrapper && right is NodeWrapper && ((right as NodeWrapper).node === (l.right as NodeWrapper).node)) {
                l.right = right
            }
        }


        if (left is Lambda) {
            var copy = left.createCopy()
            copy.setParent(this)
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
                copy.setParent(this)
                left = copy
                left.addParentCount()
                left.normalizeNamesLambda(mutableMapOf())
                return this
            }
//            return l.getBReduction() ?: right.getBReduction()
        }

        return left.getBReduction() ?: right.getBReduction()
    }

    override fun createCopy(): Node {
        var l = left.createCopy()
        var r = right.createCopy()
        l.setParent(this)
        r.setParent(this)

        return Application(l, r)
    }

    override fun setValueParentCount(value: Int) {
        parentCount = value
    }

    override fun getValueParentCount() = parentCount

    override fun deleteNaxerWrappers() {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }
//
        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }
//
//
        if (right is Application) {
            var r = right as Application
            if (r.left is NodeWrapper && left is NodeWrapper && ((left as NodeWrapper).node === (r.left as NodeWrapper).node)) {
                r.left = left
            }
        }
//
//
        if (right is Application) {
            var r = right as Application
            if (r.right is NodeWrapper && left is NodeWrapper && ((left as NodeWrapper).node === (r.right as NodeWrapper).node)) {
                r.right = left
            }
        }
//
//
        if (left is Application) {
            var l = left as Application
            if (l.left is NodeWrapper && right is NodeWrapper && ((right as NodeWrapper).node === (l.left as NodeWrapper).node)) {
                l.left = right
            }
        }
//
//
        if (left is Application) {
            var l = left as Application
            if (l.right is NodeWrapper && right is NodeWrapper && ((right as NodeWrapper).node === (l.right as NodeWrapper).node)) {
                l.right = right
            }
        }


        left.deleteNaxerWrappers()
        right.deleteNaxerWrappers()
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
        left.addParentCount()
        left.setParent(this)
        right.addParentCount()
        right.setParent(this)
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

    override fun bReduction() {
        val leftLambda = left as Lambda
        val lambdaArg = (leftLambda.leftChild() as Variable)


        val newWrapper = NodeWrapper(right)
        right.setParent(newWrapper)
        if (leftLambda.right is Variable) {
            val rightVar = leftLambda.right as Variable
            if (rightVar.node == lambdaArg.node) {
                leftLambda.right = newWrapper
                leftLambda.right.addParentCount()
            }
        } else {
            leftLambda.right.setWrapperInVariable(lambdaArg.node, newWrapper)
        }

        if (parentNode is NodeWrapper) {
            var prevPar = parentNode as NodeWrapper
            (parentNode as NodeWrapper).node = leftLambda.right
            leftLambda.rightChild().setParent(prevPar)
            leftLambda.right.deleteNaxerWrappers()
            return
        }
        if (parentNode is Application) {
            var prevApl = parentNode as Application
            if ((parentNode as Application).leftChild() === this) {
                (parentNode as Application).left = leftLambda.rightChild()
                (parentNode as Application).left.deleteNaxerWrappers()
            } else {
                (parentNode as Application).right = leftLambda.rightChild()
                (parentNode as Application).right.deleteNaxerWrappers()
            }
            leftLambda.rightChild().setParent(prevApl)
            return
        }
        if (parentNode is Lambda) {
            var prevLam = parentNode as Lambda
            (parentNode as Lambda).right = leftLambda.rightChild()
            leftLambda.rightChild().setParent(prevLam)
            leftLambda.rightChild().deleteNaxerWrappers()
            return
        }
    }

}