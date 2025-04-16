package ast;
import visitors.Visitor;

public class PairExpList extends ExpList {
    public Exp head;
    public ExpList tail;

    public PairExpList(Exp head, ExpList tail ) {
        this.head = head;
        this.tail = tail;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "PairExpList(" + this.head + ", " + this.tail + ")";
    }
}
