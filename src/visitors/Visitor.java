package visitors;

import intermediates.AssignStm;
import intermediates.CompoundStm;
import intermediates.EseqExp;
import intermediates.IdExp;
import intermediates.LastExpList;
import intermediates.NumExp;
import intermediates.OpExp;
import intermediates.PairExpList;
import intermediates.PrintStm;

public interface Visitor {
    Object visit(CompoundStm compoundStm, Object inh);
    Object visit(AssignStm assignStm, Object inh);
    Object visit(EseqExp eseqExp, Object inh);
    Object visit(IdExp idExp, Object inh);
    Object visit(LastExpList lastExpList, Object inh);
    Object visit(NumExp numExp, Object inh);
    Object visit(OpExp opExp, Object inh);
    Object visit(PairExpList pairExpList, Object inh);
    Object visit(PrintStm printStm, Object inh);
}
