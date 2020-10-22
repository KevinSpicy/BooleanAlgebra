package booleancalc.formula;

import booleancalc.BinaryOp;
import booleancalc.Bool;
import booleancalc.UnaryOp;
import booleancalc.util.Node;

public class SimpleOptimizer implements Optimizer {

    private final Formula formula;

    public SimpleOptimizer(Formula formula) {
        this.formula = formula;
    }

    @Override
    public Formula getOptimizedFormula() {
        Node binaryTree = new Node(formula.binaryTree);
        optimizeBinaryTree(binaryTree);
        return new Formula(binaryTree);
    }

    private Bool optimizeBinaryTree(Node node) {
        Bool leftConstValue = null;
        Bool rightConstValue = null;

        if (node.getLeft() != null) {
            leftConstValue = optimizeBinaryTree(node.getLeft());
        }

        if (node.getRight() != null) {
            rightConstValue = optimizeBinaryTree(node.getRight());
        }

        if (node.op != null) {
            if (node.op instanceof UnaryOp) {
                UnaryOp unaryOp = (UnaryOp) node.op;
                if (leftConstValue != null) {
                    node.op = null;
                    Bool value = unaryOp.apply(leftConstValue);
                    node.symbol = value;
                    node.setLeft(null);
                    node.setRight(null);
                    return value;
                }

                if (rightConstValue != null) {
                    node.op = null;
                    Bool value = unaryOp.apply(rightConstValue);
                    node.symbol = value;
                    node.setLeft(null);
                    node.setRight(null);
                    return value;
                }
            } else if (node.op instanceof BinaryOp) {
                BinaryOp binaryOp = (BinaryOp) node.op;

                if (leftConstValue != null && rightConstValue != null) {
                    node.op = null;
                    Bool value = binaryOp.apply(leftConstValue, rightConstValue);
                    node.symbol = value;
                    node.setLeft(null);
                    node.setRight(null);
                    return value;
                }

                if (leftConstValue != null) {
                    Bool falseLeftResult = binaryOp.apply(leftConstValue, Bool.FALSE);
                    Bool truthLeftResult = binaryOp.apply(leftConstValue, Bool.TRUTH);
                    if (falseLeftResult == truthLeftResult) {
                        node.op = null;
                        node.symbol = falseLeftResult;
                        node.setLeft(null);
                        node.setRight(null);
                        return falseLeftResult;
                    }

                    if (falseLeftResult == Bool.FALSE && truthLeftResult == Bool.TRUTH) {
                        Node rightNode = node.getRight();
                        node.op = rightNode.op;
                        node.symbol = rightNode.symbol;
                        node.setLeft(rightNode.getLeft());
                        node.setRight(rightNode.getRight());
                    }

                    return null;
                }

                if (rightConstValue != null) {
                    Bool falseRightResult = binaryOp.apply(Bool.FALSE, rightConstValue);
                    Bool truthRightResult = binaryOp.apply(Bool.TRUTH, rightConstValue);
                    if (falseRightResult == truthRightResult) {
                        node.op = null;
                        node.symbol = falseRightResult;
                        node.setLeft(null);
                        node.setRight(null);
                        return falseRightResult;
                    }

                    if (falseRightResult == Bool.FALSE && truthRightResult == Bool.TRUTH) {
                        Node leftNode = node.getLeft();
                        node.op = leftNode.op;
                        node.symbol = leftNode.symbol;
                        node.setLeft(leftNode.getLeft());
                        node.setRight(leftNode.getRight());
                    }

                    return null;
                }

                Formula formula = new Formula(node);
                if (formula.isConst()) {
                    node.op = null;
                    node.symbol = formula.getConstValue();
                    node.setLeft(null);
                    node.setRight(null);
                    return formula.getConstValue();
                }

                Formula leftSubFormula = new Formula(node.getLeft());
                Formula rightSubFormula = new Formula(node.getRight());
                if (leftSubFormula.getAllVars().equals(rightSubFormula.getAllVars())) {
                    if (leftSubFormula.equals(rightSubFormula)) {
                        if (binaryOp.apply(Bool.FALSE, Bool.FALSE) == Bool.FALSE) {
                            Node leftNode = node.getLeft();
                            node.op = leftNode.op;
                            node.symbol = leftNode.symbol;
                            node.setLeft(leftNode.getLeft());
                            node.setRight(leftNode.getRight());
                        } else {
                            Node leftNode = node.getLeft();
                            if (leftNode.op instanceof UnaryOp) {
                                Node auxNode = null;
                                if (leftNode.getLeft() != null) {
                                    auxNode = leftNode.getLeft();
                                }
                                if (leftNode.getRight() != null) {
                                    auxNode = leftNode.getRight();
                                }
                                node.op = auxNode.op;
                                node.symbol = auxNode.symbol;
                                node.setLeft(auxNode.getLeft());
                                node.setRight(auxNode.getRight());
                            } else {
                                node.op = UnaryOp.INV;
                            }
                            node.setRight(null);
                        }
                    } else if (leftSubFormula.equals(new Inverse().apply(rightSubFormula))) {
                        if (binaryOp.apply(Bool.FALSE, Bool.TRUTH) == Bool.FALSE) {
                            Node rightNode = node.getLeft();
                            node.op = rightNode.op;
                            node.symbol = rightNode.symbol;
                            node.setLeft(rightNode.getLeft());
                            node.setRight(rightNode.getRight());
                        } else {
                            Node leftNode = node.getLeft();
                            if (leftNode.op instanceof UnaryOp) {
                                Node auxNode = null;
                                if (leftNode.getLeft() != null) {
                                    auxNode = leftNode.getLeft();
                                }
                                if (leftNode.getRight() != null) {
                                    auxNode = leftNode.getRight();
                                }
                                node.op = auxNode.op;
                                node.symbol = auxNode.symbol;
                                node.setLeft(auxNode.getLeft());
                                node.setRight(auxNode.getRight());
                            } else {
                                node.op = UnaryOp.INV;
                            }
                            node.setRight(null);
                        }
                    }
                }
            }
        } else {
            if (node.symbol instanceof Bool) {
                return (Bool) node.symbol;
            }
        }

        return null;
    }

}
