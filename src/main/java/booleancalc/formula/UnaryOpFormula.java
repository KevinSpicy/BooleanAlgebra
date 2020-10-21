package booleancalc.formula;

import booleancalc.UnaryOp;
import booleancalc.util.Node;

public abstract class UnaryOpFormula implements OpFormula {

    public abstract Formula apply(Formula formula);

    protected Formula createFormula(UnaryOp op, Formula formula) {
        Node unaryBinaryTree = new Node(formula.binaryTree);
        Node unaryRoot = new Node();
        unaryRoot.op = op;
        unaryRoot.setLeft(unaryBinaryTree);
        return new Formula(unaryRoot);
    }
}
