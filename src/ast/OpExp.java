package ast;
import visitors.Visitor;

public class OpExp extends Exp {
    public Exp left, right;
    public int oper;
    final public static int ADD=1, SUB=2, MUL=3, DIV=4;
    
    public OpExp(Exp left, int oper, Exp right) {
        this.left = left;
        this.oper = oper;
        this.right = right;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "OpExp(" + this.left + ", " + this.oper + ", " + this.right + ")";
    }
}
