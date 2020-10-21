package booleancalc;

public enum UnaryOp implements Op {

    INV('!') {
        public Bool apply(Bool val) {
            return val == Bool.TRUTH ? Bool.FALSE : Bool.TRUTH;
        }
    };

    private final char name;
    UnaryOp(char name) {
        this.name = name;
    }

    @Override
    public char getName() {
        return name;
    }

    public abstract Bool apply(Bool val);
}
