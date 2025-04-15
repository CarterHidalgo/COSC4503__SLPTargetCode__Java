package intermediates;
import visitors.Visitor;

public class LastExpList extends ExpList {
    public Exp head;

    public LastExpList(Exp head) {
        this.head = head;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
