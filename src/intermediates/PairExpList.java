package intermediates;
import visitors.Visitor;

public class PairExpList extends ExpList {
    public Exp head;
    public ExpList tail;

    public PairExpList(Exp head, ExpList tail ) {
        this.head = head;
        this.tail = tail;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
