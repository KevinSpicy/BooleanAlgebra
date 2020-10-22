package booleancalc;


public enum BinaryOp implements Op {

    OR('|') {
        public Bool apply(Bool val1, Bool val2) {
            return Bool.valueOf(val1.getValue() || val2.getValue());
        }
    },
    AND('&') {
        public Bool apply(Bool val1, Bool val2) {
         return Bool.valueOf(val1.getValue() && val2.getValue());
        }
    },
    XOR('^') {
        public Bool apply(Bool val1, Bool val2) {
            if (val1.getValue() && val2.getValue()) {
                return Bool.FALSE;
            }
            return OR.apply(val1, val2);
        }
    },
    IMP('<') {
        public Bool apply(Bool val1, Bool val2) {
            return OR.apply(UnaryOp.INV.apply(val1), val2);
        }
    },
    TEST('#') {
        public Bool apply(Bool val1, Bool val2) {
            if (val1.getValue() && val2.getValue()) {
                return Bool.FALSE;
            }

            if (!val1.getValue() && !val2.getValue()) {
                return Bool.TRUTH;
            }

            return Bool.TRUTH;
        }
    };

    private final char name;
    BinaryOp(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }

    public static BinaryOp valueOf(char name) {
        for (BinaryOp value : values()) {
            if (value.getName() == name) {
                return value;
            }
        }
        return null;
    }

    public abstract Bool apply(Bool val1, Bool val2);
}
