package booleancalc.formula;

import booleancalc.UnaryOp;

public class Inverse extends UnaryOpFormula {

    @Override
    public Formula apply(Formula formula) {
        return createFormula(UnaryOp.INV, formula);
    }
}
