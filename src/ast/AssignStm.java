package ast;

import visitors.Visitor;

public class AssignStm extends Stm {
    public IdExp id;
    public Exp exp;
    
    public AssignStm(IdExp id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "AssignStm(" + this.id + ", " + this.exp + ")";
    }
}
