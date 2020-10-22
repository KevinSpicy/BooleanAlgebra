package booleancalc.normalform;

import booleancalc.Bool;
import booleancalc.Variable;
import booleancalc.formula.Conjunction;
import booleancalc.formula.Disjunction;
import booleancalc.formula.Formula;
import booleancalc.formula.Inverse;

import java.util.*;

public class Cnf implements NormalForm {

    private final Formula formula;

    public Cnf(Formula formula) {
        this.formula = formula;
    }

    @Override
    public Formula createNormalForm() {
        if (formula.isConst()) {
            Map<Variable, Bool> tuple = new HashMap<>();
            for (Variable var : formula.getAllVars()) {
                tuple.put(var, Bool.FALSE);
            }
            Bool value = formula.calcFromMap(tuple);
            return value == Bool.TRUTH ? Formula.IDENTITY : Formula.ZERO;
        }

        List<Map<Variable, Bool>> falseTuples = formula.getFalseTuples();

        List<Variable> variables = new ArrayList<>(formula.getAllVars());
        variables.sort(Comparator.comparingInt(Variable::getName));

        Formula cnf = null;
        for (Map<Variable, Bool> falseTuple : falseTuples) {
            Formula disjunct = null;
            for (Variable variable : variables) {
                Bool value = falseTuple.get(variable);

                Formula literal = new Formula(variable);
                if (value == Bool.TRUTH) {
                    literal = new Inverse().apply(literal);
                }

                if (disjunct == null) {
                    disjunct = literal;
                } else {
                    disjunct = new Disjunction().apply(disjunct, literal);
                }
            }

            if (cnf == null) {
                cnf = disjunct;
            } else {
                cnf = new Conjunction().apply(cnf, disjunct);
            }
        }

        return cnf;
    }

}
