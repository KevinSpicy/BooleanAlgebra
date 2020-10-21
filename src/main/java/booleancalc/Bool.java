package booleancalc;

public enum Bool implements NamedEnum {

    FALSE('0', false),
    TRUTH('1', true);

    private final char name;
    private final boolean value;
    Bool(char name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public char getName() {
        return name;
    }

    public boolean getValue() {
        return value;
    }

    public static Bool valueOf(boolean val) {
        for (Bool bool : values()) {
            if (val == bool.value) {
                return bool;
            }
        }
        return null;
    }

    public static Bool valueOf(char val) {
        for (Bool bool : values()) {
            if (val == bool.name) {
                return bool;
            }
        }
        return null;
    }

}
