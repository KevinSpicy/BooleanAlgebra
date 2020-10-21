package booleancalc.normalform;

import booleancalc.formula.Formula;
import booleancalc.TupleBool;
import booleancalc.TupleGenerator;
import booleancalc.Variable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dnf implements NormalForm {

    private final Formula formula;

    public Dnf(Formula formula) {
        this.formula = formula;
    }

    public Formula createNormalForm() {
        List<Variable> variables = new ArrayList<>(formula.getAllVars());
        variables.sort(Comparator.comparingInt(Variable::getName));

        List<TupleBool> trueTuples = new ArrayList<>();
        TupleGenerator tupleGenerator = new TupleGenerator(variables.size());
        for (TupleBool tupleBool : tupleGenerator) {
            ;
        }

        return null;
    }
}
