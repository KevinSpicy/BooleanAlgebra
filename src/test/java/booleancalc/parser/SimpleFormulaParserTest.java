package booleancalc.parser;

import booleancalc.formula.Formula;
import org.junit.Assert;
import org.junit.Test;


public class SimpleFormulaParserTest {

    @Test
    public void parseFormula1() {
       Formula formula = new SimpleFormulaParser("A").parse();
       Assert.assertEquals(formula.toString(), "A");
    }

    @Test
    public void parseFormula2() {
        Formula formula = new SimpleFormulaParser("B").parse();
        Assert.assertEquals(formula.toString(), "B");
    }

    @Test
    public void parseFormula3() {
        Formula formula = new SimpleFormulaParser("((((A))))").parse();
        Assert.assertEquals(formula.toString(), "A");
    }

    @Test
    public void parseFormula4() {
        Formula formula = new SimpleFormulaParser("(A & B | C)").parse();
        Assert.assertEquals(formula.toString(), "((A&B)|C)");
    }

    @Test
    public void parseFormula5() {
        Formula formula = new SimpleFormulaParser("!A & !(B | C)").parse();
        Assert.assertEquals(formula.toString(), "((!A)&(!(B|C)))");
    }

    @Test
    public void parseFormula6() {
        Formula formula = new SimpleFormulaParser("!A & !(B | C)").parse();
        Assert.assertEquals(formula.toString(), "((!A)&(!(B|C)))");
    }

    @Test
    public void parseFormula7() {
        Formula formula = new SimpleFormulaParser("!!!(B)").parse();
        Assert.assertEquals(formula.toString(), "(!B)");
    }

    @Test
    public void parseFormula8() {
        Formula formula = new SimpleFormulaParser("!!(B)").parse();
        Assert.assertEquals(formula.toString(), "B");
    }

    @Test
    public void parseFormula9() {
        Formula formula = new SimpleFormulaParser("!!(B < C < D)").parse();
        Assert.assertEquals(formula.toString(), "((B<C)<D)");
    }
}
