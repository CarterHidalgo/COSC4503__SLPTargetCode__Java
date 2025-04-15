package parser;

import java.util.List;

public class Pair {
    private Nonterminal.Type first;
    private List<StackItem> second;

    public Pair(Nonterminal.Type first, List<StackItem> second) {
        this.first = first;
        this.second = second;
    }

    public Nonterminal.Type first() {
        return this.first;
    }

    public List<StackItem> second() {
        return this.second;
    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }
}
