package ast;
import visitors.Visitor;

public class NumExp extends Exp {
    public int num;

    public NumExp(int num) {
        this.num = num;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "NumExp(" + num + ")";
    }
}
