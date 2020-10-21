package booleancalc.normalform;

import booleancalc.Bool;
import booleancalc.Variable;
import booleancalc.formula.*;
import booleancalc.parser.SimpleFormulaParser;
import org.junit.Assert;
import org.junit.Test;

public class DnfTest {

    @Test
    public void dnfTest1() {
        Formula formula = new Formula(Bool.FALSE);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest2() {
        Formula formula = new Formula(Bool.TRUTH);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest3() {
        Formula formula = new Formula(Variable.A);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest4() {
        Formula formula = new Formula(Variable.A);
        formula = new Inverse().apply(formula);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest5() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Implication().apply(formula1, formula2);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest6() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Conjunction().apply(formula1, formula2);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest7() {
        Formula formula1 = new Formula(Variable.A);
        Formula formula2 = new Formula(Variable.B);
        Formula formula = new Disjunction().apply(formula1, formula2);
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

    @Test
    public void dnfTest8() {
        Formula formula = new SimpleFormulaParser("!(A < C) & D & !1 | A").parse();
        Formula dnf = new Dnf(formula).createNormalForm();

        Assert.assertEquals(formula, dnf);
    }

}
