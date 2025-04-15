package intermediates;

import visitors.Visitor;

public class AssignStm extends Stm {
    public IdExp id;
    public Exp exp;
    
    public AssignStm(IdExp id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public Object accept(Visitor v, Object inh) {
        return v.visit(this, inh);
    }
}
