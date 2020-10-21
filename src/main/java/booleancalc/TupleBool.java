package booleancalc;

import java.util.Arrays;

public class TupleBool {

    private final int size;
    private final Bool[] tuple;

    public TupleBool(int size) {
        this.size = size;
        tuple = new Bool[size];
    }

    public int getSize() {
        return size;
    }

    public Bool get(int i) {
        return tuple[i];
    }

    public void set(int i, Bool bool) {
        tuple[i] = bool;
    }

    public void setAll(Bool bool) {
        Arrays.fill(tuple, bool);
    }
}
