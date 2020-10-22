package booleancalc.formula;

import booleancalc.*;
import booleancalc.util.Node;

import java.util.*;

public class Formula {

    public static final Formula IDENTITY;

    public static final Formula ZERO;

    private final Bool constValue;

    static {
        Node nodeTruth = new Node();
        nodeTruth.symbol = Bool.TRUTH;
        IDENTITY = new Formula(nodeTruth);

        Node nodeFalse = new Node();
        nodeFalse.symbol = Bool.FALSE;
        ZERO = new Formula(nodeFalse);
    }

    final Node binaryTree;
    private final Map<Variable, Integer> variableCounter;
    private final Map<Bool, Integer> constCounter;

    public Formula(Node binaryTree) {
        if (binaryTree == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }

        variableCounter = new HashMap<>();
        constCounter = new HashMap<>();

        this.binaryTree = new Node(binaryTree);
        checkCorrectnessAndInit(this.binaryTree);

        constValue = initConstValue();
    }

    public Formula(Formula formula) {
        binaryTree = new Node(formula.binaryTree);

        variableCounter = new HashMap<>(formula.variableCounter);
        constCounter = new HashMap<>(formula.constCounter);

        constValue = initConstValue();
    }

    public Formula(Variable variable) {
        binaryTree = new Node();
        binaryTree.symbol = variable;

        variableCounter = new HashMap<>();
        variableCounter.put(variable, 1);

        constCounter = new HashMap<>();

        constValue = null;
    }

    public Formula(Bool bool) {
        binaryTree = new Node();
        binaryTree.symbol = bool;

        variableCounter = new HashMap<>();

        constCounter = new HashMap<>();
        constCounter.put(bool, 1);

        constValue = bool;
    }

    private void checkCorrectnessAndInit(Node node) {
        if (node == null) {
            return;
        }

        if (node.op != null) {
            checkCorrectnessAndInit(node.getLeft());
            checkCorrectnessAndInit(node.getRight());
        } else {
            if (node.symbol == null) {
                throw new IllegalStateException("Incorrect formula. Symbol(variable or const) cannot be a null");
            }

            if (node.symbol instanceof Variable) {
                Variable var = (Variable) node.symbol;
                if (variableCounter.containsKey(var)) {
                    variableCounter.put(var, variableCounter.get(var) + 1);
                } else {
                    variableCounter.put(var, 1);
                }
            } else if (node.symbol instanceof Bool) {
                Bool bool = (Bool) node.symbol;
                if (constCounter.containsKey(bool)) {
                    constCounter.put(bool, constCounter.get(bool) + 1);
                } else {
                    constCounter.put(bool, 1);
                }
            } else {
                throw new IllegalStateException("Incorrect formula. Unknowing symbol");
            }
        }
    }

    public int getCountOfAllVars() {
        return variableCounter.values()
                .stream()
                .reduce(0, Integer::sum);
    }

    public int getCountOfAllConsts() {
        return constCounter.values()
                .stream()
                .reduce(0, Integer::sum);
    }

    public Set<Variable> getAllVars() {
        return Collections.unmodifiableSet(variableCounter.keySet());
    }

    public boolean isContainConsts() {
        return !constCounter.isEmpty();
    }

    public boolean isContainVar(Variable var) {
        return variableCounter.containsKey(var);
    }

    public List<Map<Variable, Bool>> getTruthTuples() {
        List<Map<Variable, Bool>> truthTuples = new ArrayList<>();
        List<Variable> variables = new ArrayList<>(getAllVars());
        if (!variables.isEmpty()) {
            for (TupleBool tupleBool : new TupleGenerator(variables.size())) {
                Map<Variable, Bool> map = new HashMap<>();
                for (int i = 0; i < variables.size(); ++i) {
                    map.put(variables.get(i), tupleBool.get(i));
                }
                Bool value = calcFromTree(binaryTree, map);
                if (value == Bool.TRUTH) {
                    truthTuples.add(map);
                }
            }
        }
        return truthTuples;
    }

    public List<Map<Variable, Bool>> getFalseTuples() {
        List<Map<Variable, Bool>> falseTuples = new ArrayList<>();
        List<Variable> variables = new ArrayList<>(getAllVars());
        if (!variables.isEmpty()) {
            for (TupleBool tupleBool : new TupleGenerator(variables.size())) {
                Map<Variable, Bool> map = new HashMap<>();
                for (int i = 0; i < variables.size(); ++i) {
                    map.put(variables.get(i), tupleBool.get(i));
                }
                Bool value = calcFromTree(binaryTree, map);
                if (value == Bool.FALSE) {
                    falseTuples.add(map);
                }
            }
        }
        return falseTuples;
    }

    public Bool getConstValue() {
        return constValue;
    }

    public boolean isConst() {
        return constValue != null;
    }

    private Bool initConstValue() {
        List<Variable> orderedVars = new ArrayList<>(getAllVars());
        Map<Variable, Bool> tuple = new HashMap<>();
        Bool firstValue = null;
        for (TupleBool tupleBool : new TupleGenerator(getAllVars().size())) {
            for (int i = 0; i < tupleBool.getSize(); ++i) {
                tuple.put(orderedVars.get(i), tupleBool.get(i));
            }
            Bool secondValue = calcFromTree(binaryTree, tuple);
            if (firstValue == null) {
                firstValue = secondValue;
            }
            if (firstValue != secondValue) {
                return null;
            }
        }

        return firstValue;
    }

    public Bool calcFromMap(Map<Variable, Bool> tuple) {
        if (tuple == null) {
            throw new IllegalArgumentException("Tuple is null");
        }

        if (!getAllVars().equals(tuple.keySet()) && constValue != null) {
            throw new IllegalArgumentException("This tuple is not for this formula");
        }

        return calcFromTree(binaryTree, tuple);
    }

    private Bool calcFromTree(Node node, Map<Variable, Bool> tuple) {
        if (node.symbol != null) {
            Bool value = null;
            if (node.symbol instanceof Variable) {
                Variable var = (Variable) node.symbol;
                value = tuple.get(var);
            } else if (node.symbol instanceof Bool) {
                value = (Bool) node.symbol;
            }

            if (value == null) {
                throw new IllegalArgumentException("Tuple has null instead Bool value");
            }
            return value;
        } else if (node.op != null) {
            if (node.op instanceof UnaryOp) {
                UnaryOp unaryOp = (UnaryOp) node.op;
                return node.getLeft() != null
                        ? unaryOp.apply(calcFromTree(node.getLeft(), tuple))
                        : unaryOp.apply(calcFromTree(node.getRight(), tuple));
            } else if (node.op instanceof BinaryOp) {
                BinaryOp binaryOp = (BinaryOp) node.op;
                return binaryOp.apply(
                        calcFromTree(node.getLeft(), tuple),
                        calcFromTree(node.getRight(), tuple)
                );
            } else {
                throw new IllegalStateException("Operator R > 2");
            }
        } else {
            throw new IllegalStateException("Incorrect formula; operation is null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formula formula = (Formula) o;

        Bool thisConst = constValue;
        Bool thatConst = formula.getConstValue();

        if ((thisConst != null) ^ (thatConst != null)) {
            return false;
        }

        if (thisConst != null) {
            return thisConst == thatConst;
        }

        if (!getAllVars().equals(formula.getAllVars())) {
            return false;
        }

        List<Variable> variables = new ArrayList<>(getAllVars());
        TupleGenerator tuples = new TupleGenerator(variables.size());
        Map<Variable, Bool> mapVar = new HashMap<>();
        for (TupleBool tuple : tuples) {
            for (int i = 0; i < variables.size(); i++) {
                mapVar.put(variables.get(i), tuple.get(i));
            }
            if (calcFromTree(binaryTree, mapVar) != formula.calcFromTree(formula.binaryTree, mapVar)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountOfAllVars(), getCountOfAllConsts());
    }

    @Override
    public String toString() {
        return toStringRecursively(binaryTree);
    }

    public String toStringWithoutParenthesises() {
        return toStringRecursivelyWithoutParenthesises(binaryTree);
    }

    private String toStringRecursively(Node node) {
        if (node != null) {
            if (node.getLeft() != null && node.getRight() != null) {
                return "(" + toStringRecursively(node.getLeft()) + node.op.getName() + toStringRecursively(node.getRight()) + ")";
            } else if (node.getLeft() != null) {
                return "(" + node.op.getName() + toStringRecursively(node.getLeft()) + ")";
            } else if (node.getRight() != null) {
                return "(" + node.op.getName() + toStringRecursively(node.getRight()) + ")";
            }

            return Character.toString(node.symbol.getName());
        }

        return "";
    }

    private String toStringRecursivelyWithoutParenthesises(Node node) {
        if (node != null) {
            if (node.getLeft() != null && node.getRight() != null) {
                return toStringRecursivelyWithoutParenthesises(node.getLeft()) + node.op.getName() + toStringRecursivelyWithoutParenthesises(node.getRight());
            } else if (node.getLeft() != null) {
                return node.op.getName() + toStringRecursivelyWithoutParenthesises(node.getLeft());
            } else if (node.getRight() != null) {
                return node.op.getName() + toStringRecursivelyWithoutParenthesises(node.getRight());
            }

            return Character.toString(node.symbol.getName());
        }

        return "";
    }
}
