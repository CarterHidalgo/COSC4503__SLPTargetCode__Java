package ast;
import visitors.Visitor;

public class IdExp extends Exp {
    public String id;

    public IdExp(String id) {
        this.id = id;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IdExp(" + this.id + ")";
    }
}
