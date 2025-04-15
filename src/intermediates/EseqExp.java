package intermediates;
import visitors.Visitor;

public class EseqExp extends Exp {
    public Stm stm;
    public Exp exp;

    public EseqExp(Stm stm, Exp exp) {
        this.stm = stm;
        this.exp = exp;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
