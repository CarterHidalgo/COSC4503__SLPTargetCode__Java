package intermediates;
import visitors.Visitor;

public class IdExp extends Exp {
    public String id;

    public IdExp(String id) {
        this.id = id;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
