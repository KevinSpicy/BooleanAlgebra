package booleancalc.util;

import booleancalc.NamedEnum;
import booleancalc.Op;

public class Node {

    public Op op;
    public NamedEnum symbol;
    private Node left;
    private Node right;

    public Node() {
    }

    public Node(Node o) {
        if (o == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        deepCopy(this, o);
    }

    private void deepCopy(Node newNode, Node oldNode) {
        if (oldNode == null) {
            return;
        }
        newNode.op = oldNode.op;
        newNode.symbol = oldNode.symbol;
        if (oldNode.getLeft() != null) {
            newNode.setLeft(new Node());
        }
        if (oldNode.getRight() != null) {
            newNode.setRight(new Node());
        }
        deepCopy(newNode.getLeft(), oldNode.getLeft());
        deepCopy(newNode.getRight(), oldNode.getRight());
    }

    public Node(Op op) {
       this.op = op;
    }

    public Node(NamedEnum var) {
        this.symbol = var;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void initNode(Op op, NamedEnum symbol, Node left, Node right) {
        this.op = op;
        this.symbol = symbol;
        this.left = left;
        this.right = right;
    }
}
