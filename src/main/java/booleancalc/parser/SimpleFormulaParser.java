package booleancalc.parser;

import booleancalc.BinaryOp;
import booleancalc.Bool;
import booleancalc.formula.Formula;
import booleancalc.UnaryOp;
import booleancalc.Variable;
import booleancalc.util.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleFormulaParser implements FormulaParser {

    private static final String DELIMITER = ".";

    private final String formulaString;

    public SimpleFormulaParser(String formulaString) {
        this.formulaString = formulaString;
    }

    public Formula parse() {
        if (formulaString == null || formulaString.equals("")) {
            throw new IllegalArgumentException("String formula is null or empty");
        }
        String varFormulaString = formulaString;
        varFormulaString = varFormulaString.replaceAll("\\s+", "");

        varFormulaString = deleteNotNeededInvOp(varFormulaString);
        varFormulaString = addParenthesisesToInvOp(varFormulaString);
        varFormulaString = addParenthesisesToVariables(varFormulaString);
        varFormulaString = addParenthesisesToConsts(varFormulaString);
        Node root = new Node();
        createFormulaTree(root, new StringBuilder(varFormulaString), 0, varFormulaString.length() - 1);

        return new Formula(root);
    }

    private String deleteNotNeededInvOp(String varFormula) {
        Pattern p = Pattern.compile(UnaryOp.INV.getName() + "{2}");
        while (p.matcher(varFormula).find()) {
            int start = varFormula.indexOf(String.valueOf(UnaryOp.INV.getName()) + String.valueOf(UnaryOp.INV.getName()));
            int end = start;
            while (end < varFormula.length() && varFormula.charAt(end) == UnaryOp.INV.getName()) {
                end++;
            }
            if ((end - start) % 2 == 0) {
                varFormula = varFormula.substring(0, start) + varFormula.substring(end);
            } else {
                varFormula = varFormula.substring(0, start + 1) + varFormula.substring(end);
            }
        }
        return varFormula;
    }

    private String addParenthesisesToVariables(String varFormula) {
        for (Variable variable : Variable.values()) {
            varFormula = varFormula.replaceAll(String.valueOf(variable.getName()), "(" + variable.getName() + ")");
        }
        return varFormula;
    }

    private String addParenthesisesToConsts(String varFormula) {
        for (Bool bool : Bool.values()) {
            varFormula = varFormula.replaceAll(String.valueOf(bool.getName()), "(" + bool.getName() + ")");
        }
        return varFormula;
    }

    private String addParenthesisesToInvOp(String varFormula) {
        // Надеемся что в формуле нет точки
        while (varFormula.contains(String.valueOf(UnaryOp.INV.getName()))) {
            int nextInv = varFormula.indexOf(UnaryOp.INV.getName());
            if (nextInv == varFormula.length()) {
                throw new IllegalStateException("Incorrect formula");
            }
            int lastIndex = getLastIndexOfSubformula(new StringBuilder(varFormula), nextInv + 1);
            varFormula = varFormula.substring(0, lastIndex + 1) + ")" + varFormula.substring(lastIndex + 1);
            varFormula = varFormula.substring(0, nextInv) + "(" + varFormula.substring(nextInv);
            varFormula = varFormula.replaceFirst(String.valueOf(UnaryOp.INV.getName()), DELIMITER);
        }
        return varFormula.replaceAll("\\" + DELIMITER, String.valueOf(UnaryOp.INV.getName()));
    }

    private static boolean isVariable(char var) {
        return Variable.valueOf(var) != null;
    }

    private static boolean isConst(char var) {
        return Bool.valueOf(var) != null;
    }

    private void createFormulaTree(Node node, StringBuilder varFormula, int firstIndex, int lastIndex) {
        if (firstIndex > lastIndex) {
            throw new IllegalStateException("Incorrect formula");
        }
        if (varFormula.charAt(firstIndex) == '(') {
            int endParenthesis = getLastIndexOfSubformula(varFormula, firstIndex);
            if (endParenthesis == -1) {
                throw new IllegalStateException(String.format("Incorrect formula. Error in parenthesises: %s", varFormula));
            }

            if (endParenthesis == lastIndex) {
                createFormulaTree(node, varFormula, firstIndex + 1, lastIndex - 1);
            } else {
                List<BinaryOp> ops = new ArrayList<>();
                List<Integer> startOfSubformula = new ArrayList<>();
                List<Integer> endOfSubformula = new ArrayList<>();

                int startIndex = firstIndex;
                startOfSubformula.add(startIndex + 1);
                endOfSubformula.add(endParenthesis - 1);
                while (endParenthesis < lastIndex) {
                    BinaryOp operation = BinaryOp.valueOf(varFormula.charAt(endParenthesis + 1));
                    if (operation == null) {
                        throw new IllegalStateException(String.format("Incorrect formula: %s. '%s' is undefined operator at index %d",
                                varFormula, varFormula.charAt(endParenthesis + 1), endParenthesis + 1));
                    }
                    ops.add(operation);

                    if (endParenthesis + 1 == lastIndex) {
                        throw new IllegalStateException(String.format("Incorrect formula: %s", varFormula));
                    }

                    if (varFormula.charAt(endParenthesis + 2) == '(') {
                        startIndex = endParenthesis + 2;
                        endParenthesis = getLastIndexOfSubformula(varFormula, endParenthesis + 2);
                        if (endParenthesis == -1) {
                            throw new IllegalStateException(String.format("Incorrect formula: %s", varFormula));
                        }
                        startOfSubformula.add(startIndex + 1);
                        endOfSubformula.add(endParenthesis - 1);
                    } else {
                        throw new IllegalStateException(String.format("Incorrect formula: %s", varFormula));
                    }
                }

                Node nextNode = node;
                int sizeOfSubformulas = startOfSubformula.size();
                for (int i = sizeOfSubformulas - 1; i > 0; i--) {
                    nextNode.op = ops.get(i - 1);
                    Node left = new Node();
                    nextNode.setLeft(left);
                    Node right = new Node();
                    nextNode.setRight(right);
                    createFormulaTree(right, varFormula, startOfSubformula.get(i), endOfSubformula.get(i));
                    nextNode = left;
                }
                createFormulaTree(nextNode, varFormula, startOfSubformula.get(0), endOfSubformula.get(0));
            }
            //TODO
        } else if (varFormula.charAt(firstIndex) == UnaryOp.INV.getName()) {
            node.op = UnaryOp.INV;
            Node nextNode = new Node();
            node.setLeft(nextNode);
            if (firstIndex < lastIndex) {
                createFormulaTree(nextNode, varFormula, firstIndex + 1, lastIndex);
            } else {
                throw new IllegalStateException(String.format("Incorrect formula: %s", varFormula));
            }
        } else if (isVariable(varFormula.charAt(firstIndex))) {
            node.symbol = Variable.valueOf(varFormula.charAt(firstIndex));
        } else if (isConst(varFormula.charAt(firstIndex))) {
            node.symbol = Bool.valueOf(varFormula.charAt(firstIndex));
        } else {
            throw new IllegalStateException("Incorrect formula");
        }
    }

    private int getLastIndexOfSubformula(StringBuilder varFormula, int start) {
        int counter = 0;
        for (int i = start; i < varFormula.length(); i++) {
            if (varFormula.charAt(i) == '(') {
                counter++;
            } else if (varFormula.charAt(i) == ')') {
                counter--;
            }

            if (counter == 0) {
                return i;
            }
        }
        return -1;
    }

}
