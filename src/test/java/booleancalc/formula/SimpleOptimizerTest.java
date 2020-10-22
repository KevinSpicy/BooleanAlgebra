package booleancalc.formula;

import booleancalc.parser.SimpleFormulaParser;
import org.junit.Assert;
import org.junit.Test;

public class SimpleOptimizerTest {

    @Test
    public void test1() {
        Formula formula = new SimpleFormulaParser("A").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
    }

    @Test
    public void test2() {
        Formula formula = new SimpleFormulaParser("0").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
    }

    @Test
    public void test3() {
        Formula formula = new SimpleFormulaParser("A | B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
    }

    @Test
    public void test4() {
        Formula formula = new SimpleFormulaParser("A ^ B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
    }

    @Test
    public void test5() {
        Formula formula = new SimpleFormulaParser("A < 1").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "1");
    }

    @Test
    public void test6() {
        Formula formula = new SimpleFormulaParser("A < 0").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(A<0)");
    }

    @Test
    public void test7() {
        Formula formula = new SimpleFormulaParser("(A < B) | (B < A)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "1");
    }

    @Test
    public void test8() {
        Formula formula = new SimpleFormulaParser("B | B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "B");
    }

    @Test
    public void test9() {
        Formula formula = new SimpleFormulaParser("A & A").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "A");
    }

    @Test
    public void test10() {
        Formula formula = new SimpleFormulaParser("(A & A) < B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(A<B)");
    }

    @Test
    public void test11() {
        Formula formula = new SimpleFormulaParser("(A & 0) < B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "1");
    }

    @Test
    public void test12() {
        Formula formula = new SimpleFormulaParser("(A & 0) < B").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "1");
    }

    @Test
    public void test13() {
        Formula formula = new SimpleFormulaParser("(1 | 0) < 0").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "0");
    }

    @Test
    public void test14() {
        Formula formula = new SimpleFormulaParser("(A | (!A)) & ((C | D) & (D | C))").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertNotEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(C|D)");
    }

    @Test
    public void test15() {
        Formula formula = new SimpleFormulaParser("(A | B) < (B | A | C)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "1");
    }

    @Test
    public void test16() {
        Formula formula = new SimpleFormulaParser("!!A").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "A");
    }

    @Test
    public void test17() {
        Formula formula = new SimpleFormulaParser("!!!A").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(!A)");
    }

    @Test
    public void test18() {
        Formula formula = new SimpleFormulaParser("!!!!(A & B) | (C | C)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "((A&B)|C)");
    }

    @Test
    public void test19() {
        Formula formula = new SimpleFormulaParser("(!A < A)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "A");
    }

    @Test
    public void test20() {
        Formula formula = new SimpleFormulaParser("(A < !A)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(!A)");
    }

    @Test
    public void test21() {
        Formula formula = new SimpleFormulaParser("(A # A)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "(!A)");
    }

    @Test
    public void test22() {
        Formula formula = new SimpleFormulaParser("(!A # !A)").parse();
        Formula optimizedFormula = new SimpleOptimizer(formula).getOptimizedFormula();

        Assert.assertEquals(formula, optimizedFormula);
        Assert.assertEquals(optimizedFormula.toString(), "A");
    }

}
