package booleancalc.formula;

import booleancalc.BinaryOp;

public class Conjunction extends BinaryOpFormula {

    @Override
    public Formula apply(Formula formula1, Formula formula2) {
        return createFormula(BinaryOp.AND, formula1, formula2);
    }
}
