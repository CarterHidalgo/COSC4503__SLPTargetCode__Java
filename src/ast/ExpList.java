package ast;
import visitors.Visitor;

public abstract class ExpList{
    public abstract Object accept(Visitor v);
}