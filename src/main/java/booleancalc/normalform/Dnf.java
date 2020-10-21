package booleancalc.normalform;

import booleancalc.Bool;
import booleancalc.formula.Conjunction;
import booleancalc.formula.Disjunction;
import booleancalc.formula.Formula;
import booleancalc.Variable;
import booleancalc.formula.Inverse;

import java.util.*;

public class Dnf implements NormalForm {

    private final Formula formula;

    public Dnf(Formula formula) {
        this.formula = formula;
    }

    public Formula createNormalForm() {
        if (formula.isConst()) {
            Map<Variable, Bool> tuple = new HashMap<>();
            for (Variable var : formula.getAllVars()) {
                tuple.put(var, Bool.FALSE);
            }
            Bool value = formula.calcFromMap(tuple);
            return value == Bool.TRUTH ? Formula.IDENTITY : Formula.ZERO;
        }

        List<Map<Variable, Bool>> truthTuples = formula.getTruthTuples();

        List<Variable> variables = new ArrayList<>(formula.getAllVars());
        variables.sort(Comparator.comparingInt(Variable::getName));

        Formula dnf = null;
        for (Map<Variable, Bool> truthTuple : truthTuples) {
            Formula conjuct = null;
            for (Variable variable : variables) {
                Bool value = truthTuple.get(variable);

                Formula literal = new Formula(variable);
                if (value == Bool.FALSE) {
                    literal = new Inverse().apply(literal);
                }

                if (conjuct == null) {
                    conjuct = literal;
                } else {
                    conjuct = new Conjunction().apply(conjuct, literal);
                }
            }

            if (dnf == null) {
                dnf = conjuct;
            } else {
                dnf = new Disjunction().apply(dnf, conjuct);
            }
        }

        return dnf;
    }


}
