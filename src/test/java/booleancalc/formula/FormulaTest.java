package booleancalc.formula;

import booleancalc.*;
import booleancalc.formula.Formula;
import booleancalc.parser.SimpleFormulaParser;
import booleancalc.util.Node;
import org.junit.Assert;
import org.junit.Test;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class FormulaTest {

    @Test
    public void checkFormula1() {
        // !(A < C)
        Node binaryTree = new Node();
        binaryTree.op = UnaryOp.INV;

        Node node = new Node();
        node.op = BinaryOp.IMP;

        binaryTree.setLeft(node);

        Node nodeLeft = new Node();
        nodeLeft.symbol = Variable.A;
        node.setLeft(nodeLeft);

        Node nodeRight = new Node();
        nodeRight.symbol = Variable.C;
        node.setRight(nodeRight);

        Formula formula = new Formula(binaryTree);
        TupleGenerator tupleBool = new TupleGenerator(formula.getAllVars().size());
        Map<Variable, Bool> map = new HashMap<>();
        for (TupleBool tuple : tupleBool) {
            Bool arg1 = tuple.get(0);
            Bool arg2 = tuple.get(1);

            map.put(Variable.A, arg1);
            map.put(Variable.C, arg2);

            Assert.assertEquals(formula.calcFromMap(map), UnaryOp.INV.apply(BinaryOp.IMP.apply(arg1, arg2)));
        }
    }

    @Test
    public void checkFormula2() {
        // A & B
        Node binaryTree = new Node();
        binaryTree.op = BinaryOp.AND;

        Node nodeLeft = new Node();
        nodeLeft.symbol = Variable.A;
        binaryTree.setLeft(nodeLeft);

        Node nodeRight = new Node();
        nodeRight.symbol = Variable.B;
        binaryTree.setRight(nodeRight);

        Formula formula = new Formula(binaryTree);
        TupleGenerator tupleBool = new TupleGenerator(formula.getAllVars().size());
        Map<Variable, Bool> map = new HashMap<>();
        for (TupleBool tuple : tupleBool) {
            Bool arg1 = tuple.get(0);
            Bool arg2 = tuple.get(1);

            map.put(Variable.A, arg1);
            map.put(Variable.B, arg2);

            Assert.assertEquals(formula.calcFromMap(map), BinaryOp.AND.apply(arg1, arg2));
        }
    }

    @Test
    public void checkFormula3() {
        // A | B
        Node binaryTree = new Node();
        binaryTree.op = BinaryOp.OR;

        Node nodeLeft = new Node();
        nodeLeft.symbol = Variable.A;
        binaryTree.setLeft(nodeLeft);

        Node nodeRight = new Node();
        nodeRight.symbol = Variable.B;
        binaryTree.setRight(nodeRight);

        Formula formula = new Formula(binaryTree);
        TupleGenerator tupleBool = new TupleGenerator(formula.getAllVars().size());
        Map<Variable, Bool> map = new HashMap<>();
        for (TupleBool tuple : tupleBool) {
            Bool arg1 = tuple.get(0);
            Bool arg2 = tuple.get(1);

            map.put(Variable.A, arg1);
            map.put(Variable.B, arg2);

            Assert.assertEquals(formula.calcFromMap(map), BinaryOp.OR.apply(arg1, arg2));
        }
    }

    @Test
    public void checkFormula4() {
        // A ^ B
        Node binaryTree = new Node();
        binaryTree.op = BinaryOp.XOR;

        Node nodeLeft = new Node();
        nodeLeft.symbol = Variable.A;
        binaryTree.setLeft(nodeLeft);

        Node nodeRight = new Node();
        nodeRight.symbol = Variable.B;
        binaryTree.setRight(nodeRight);

        Formula formula = new Formula(binaryTree);
        TupleGenerator tupleBool = new TupleGenerator(formula.getAllVars().size());
        Map<Variable, Bool> map = new HashMap<>();
        for (TupleBool tuple : tupleBool) {
            Bool arg1 = tuple.get(0);
            Bool arg2 = tuple.get(1);

            map.put(Variable.A, arg1);
            map.put(Variable.B, arg2);

            Assert.assertEquals(formula.calcFromMap(map), BinaryOp.XOR.apply(arg1, arg2));
        }
    }


    @Test
    public void testEquals1() {
        Formula formula1 = new SimpleFormulaParser("!A | B").parse();
        Formula formula2 = new SimpleFormulaParser("A < B").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals2() {
        Formula formula1 = new SimpleFormulaParser("C < 1").parse();
        Formula formula2 = new SimpleFormulaParser("0 < 1").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals3() {
        Formula formula1 = new SimpleFormulaParser("C").parse();
        Formula formula2 = new SimpleFormulaParser("C").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals4() {
        Formula formula1 = new SimpleFormulaParser("0").parse();
        Formula formula2 = new SimpleFormulaParser("0").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals5() {
        Formula formula1 = new SimpleFormulaParser("1").parse();
        Formula formula2 = new SimpleFormulaParser("1").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals6() {
        Formula formula1 = new SimpleFormulaParser("!1").parse();
        Formula formula2 = new SimpleFormulaParser("0").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testEquals7() {
        Formula formula1 = new SimpleFormulaParser("1").parse();
        Formula formula2 = new SimpleFormulaParser("!0").parse();

        Assert.assertEquals(formula1, formula2);
    }

    @Test
    public void testToString1() {
        Formula formula = new SimpleFormulaParser("(A)<(!B&(C))").parse();

        Assert.assertEquals(formula.toString(), "(A<((!B)&C))");
    }
}
