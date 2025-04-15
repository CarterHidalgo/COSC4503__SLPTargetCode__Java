package intermediates;
import visitors.Visitor;

public class NumExp extends Exp {
    public int num;

    public NumExp(int num) {
        this.num = num;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
