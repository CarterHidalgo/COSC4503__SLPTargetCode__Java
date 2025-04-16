package parser;

public class Nonterminal implements StackItem {
    private Nonterminal.Type type;
    private Object value;

    enum Type {
        STM, EXP, EXPLIST, BINOP
    }

    public Nonterminal(Nonterminal.Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Nonterminal(Nonterminal.Type type) {
        this.type = type;
        this.value = null;
    }

    public Object value() {
        return this.value;
    }

    public Nonterminal.Type type() {
        return this.type;
    }

    @Override
    public String toString() {
        return "N-" + type.toString();
    }
}
