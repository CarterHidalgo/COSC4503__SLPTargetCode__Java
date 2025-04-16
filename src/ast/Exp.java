package ast;
import visitors.Visitor;

public abstract class Exp{
    public abstract Object accept(Visitor v);
}
