package booleancalc;

public enum Variable implements NamedEnum {

    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H'),
    I('I'),
    J('J'),
    K('K'),
    L('L'),
    M('M'),
    N('N'),
    O('O'),
    P('P'),
    Q('Q'),
    R('R'),
    S('S'),
    T('T'),
    U('U'),
    V('V'),
    W('W'),
    X('X'),
    Y('Y'),
    Z('Z');

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
