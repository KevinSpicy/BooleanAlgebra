package booleancalc;

import java.util.*;

public class TupleGenerator implements Iterable<TupleBool> {

    private final int extent;
    List<TupleBool> tuples = new ArrayList<>();

    public TupleGenerator(int extent) {
        this.extent = extent;
        generate();
    }

    @Override
    public Iterator<TupleBool> iterator() {
        return tuples.iterator();
    }

    private void generate() {
        int sizeList = (int) Math.pow(2, extent);
        TupleBool tuple = new TupleBool(extent);
        tuple.setAll(Bool.FALSE);
        tuples.add(tuple);
        for (int i = 1; i < sizeList; i++) {
            tuple = nextTuple(tuple);
            tuples.add(tuple);
        }
    }

    private TupleBool nextTuple(TupleBool tuple) {
        int tupleSize = tuple.getSize();
        TupleBool nextTuple = new TupleBool(tupleSize);
        for (int i = tupleSize - 1; i > -1; i--) {
            if (tuple.get(i) == Bool.FALSE) {
                nextTuple.set(i, Bool.TRUTH);
                for (int j = 0; j < i; j++) {
                    nextTuple.set(j, tuple.get(j));
                }
                for (int j = i+1; j < tupleSize; j++) {
                    nextTuple.set(j, Bool.FALSE);
                }
                return nextTuple;
            }
            nextTuple.set(i, tuple.get(i));
        }

        nextTuple.setAll(Bool.FALSE);
        return nextTuple;
    }
}
