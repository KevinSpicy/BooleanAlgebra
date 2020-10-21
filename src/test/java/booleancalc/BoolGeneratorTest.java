package booleancalc;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;


public class BoolGeneratorTest {

    @Test
    public void generate() {
        TupleGenerator arrayTuple = new TupleGenerator(2);

        Iterator<TupleBool> it = arrayTuple.iterator();
        TupleBool tuple1 = it.next();
        TupleBool tuple2 = it.next();
        TupleBool tuple3 = it.next();
        TupleBool tuple4 = it.next();

        Assert.assertEquals(tuple1.get(0), Bool.FALSE);
        Assert.assertEquals(tuple1.get(1), Bool.FALSE);

        Assert.assertEquals(tuple2.get(0), Bool.FALSE);
        Assert.assertEquals(tuple2.get(1), Bool.TRUTH);

        Assert.assertEquals(tuple3.get(0), Bool.TRUTH);
        Assert.assertEquals(tuple3.get(1), Bool.FALSE);

        Assert.assertEquals(tuple4.get(0), Bool.TRUTH);
        Assert.assertEquals(tuple4.get(1), Bool.TRUTH);
    }
}