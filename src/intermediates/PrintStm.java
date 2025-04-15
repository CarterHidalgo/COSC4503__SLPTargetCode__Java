package intermediates;
import visitors.Visitor;

public class PrintStm extends Stm {
    public ExpList exps;

    public PrintStm(ExpList exps) {
        this.exps = exps;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
