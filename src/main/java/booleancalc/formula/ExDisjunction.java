package booleancalc.formula;

import booleancalc.BinaryOp;

public class ExDisjunction extends BinaryOpFormula {

    @Override
    public Formula apply(Formula formula1, Formula formula2) {
        return createFormula(BinaryOp.XOR, formula1, formula2);
    }
}
