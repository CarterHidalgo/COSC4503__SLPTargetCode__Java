package ast;
import visitors.Visitor;

public class PrintStm extends Stm {
    public ExpList exps;

    public PrintStm(ExpList exps) {
        this.exps = exps;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "PrintStm(" + this.exps + ")";
    }
}
