package booleancalc;

public enum Variable implements NamedEnum {

    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H');

    private final char name;
    Variable(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }

    public static Variable valueOf(char val) {
        for (Variable var : values()) {
            if (var.getName() == val) {
                return var;
            }
        }
        return null;
    }
}
