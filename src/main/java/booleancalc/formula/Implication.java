package booleancalc.formula;

import booleancalc.BinaryOp;

public class Implication extends BinaryOpFormula {

    @Override
    public Formula apply(Formula formula1, Formula formula2) {
        return createFormula(BinaryOp.IMP, formula1, formula2);
    }
}
