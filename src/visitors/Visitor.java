package visitors;

import ast.AssignStm;
import ast.CompoundStm;
import ast.EseqExp;
import ast.IdExp;
import ast.LastExpList;
import ast.NumExp;
import ast.OpExp;
import ast.PairExpList;
import ast.PrintStm;

public interface Visitor {
    Object visit(CompoundStm compoundStm);
    Object visit(AssignStm assignStm);
    Object visit(EseqExp eseqExp);
    Object visit(IdExp idExp);
    Object visit(LastExpList lastExpList);
    Object visit(NumExp numExp);
    Object visit(OpExp opExp);
    Object visit(PairExpList pairExpList);
    Object visit(PrintStm printStm);
}
