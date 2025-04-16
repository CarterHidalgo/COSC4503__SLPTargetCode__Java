package ast;
import visitors.Visitor;

public class EseqExp extends Exp {
    public Stm stm;
    public Exp exp;

    public EseqExp(Stm stm, Exp exp) {
        this.stm = stm;
        this.exp = exp;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "EseqExp(" + this.stm + ", " + this.exp + ")";
    }
}
