package booleancalc.normalform;

import booleancalc.Bool;
import booleancalc.Variable;
import booleancalc.formula.*;
import booleancalc.parser.SimpleFormulaParser;
import org.junit.Assert;
import org.junit.Test;

public class CnfTest {

    @Test
    public void cnfTest1() {
        Formula formula = new Formula(Bool.FALSE);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest2() {
        Formula formula = new Formula(Bool.TRUTH);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest3() {
        Formula formula = new Formula(Variable.A);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest4() {
        Formula formula = new Formula(Variable.A);
        formula = new Inverse().apply(formula);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest5() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Implication().apply(formula1, formula2);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest6() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Conjunction().apply(formula1, formula2);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest7() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Disjunction().apply(formula1, formula2);
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }

    @Test
    public void cnfTest8() {
        Formula formula = new SimpleFormulaParser("!(A < C) & D & !1 | A").parse();
        Formula cnf = new Cnf(formula).createNormalForm();

        Assert.assertEquals(formula, cnf);
    }
}
