package booleancalc.formula;

import booleancalc.BinaryOp;

public class Disjunction extends BinaryOpFormula {
    @Override
    public Formula apply(Formula formula1, Formula formula2) {
        return createFormula(BinaryOp.OR, formula1, formula2);
    }
}
