package ast;
import visitors.Visitor;

public class LastExpList extends ExpList {
    public Exp head;

    public LastExpList(Exp head) {
        this.head = head;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "LastExpList(" + this.head + ")";
    }
}
