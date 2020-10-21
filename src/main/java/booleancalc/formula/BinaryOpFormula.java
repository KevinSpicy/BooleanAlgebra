package booleancalc.formula;

import booleancalc.BinaryOp;
import booleancalc.util.Node;

public abstract class BinaryOpFormula implements OpFormula {

    public abstract Formula apply(Formula formula1, Formula formula2);

    protected Formula createFormula(BinaryOp op, Formula formula1, Formula formula2) {
        Node binRoot = new Node();
        binRoot.op = op;
        binRoot.setLeft(new Node(formula1.binaryTree));
        binRoot.setRight(new Node(formula2.binaryTree));
        return new Formula(binRoot);
    }
}
