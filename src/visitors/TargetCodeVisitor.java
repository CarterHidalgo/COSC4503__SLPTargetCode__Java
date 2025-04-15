package visitors;

import java.util.HashSet;
import java.util.Set;

import intermediates.AssignStm;
import intermediates.CompoundStm;
import intermediates.EseqExp;
import intermediates.IdExp;
import intermediates.LastExpList;
import intermediates.NumExp;
import intermediates.OpExp;
import intermediates.PairExpList;
import intermediates.PrintStm;
import register.Register;
import register.RegisterManager;

public class TargetCodeVisitor implements Visitor {
    private Set<String> symbols = new HashSet<>();
    private StringBuffer text = new StringBuffer();
    private StringBuffer data = new StringBuffer();
    
    public TargetCodeVisitor() {
        text.append(".text\n");
        text.append("main:\n");

        data.append(".data\n");
        data.append("newline: .asciiz \"\\n\"\n");
    }

    public Object visit(CompoundStm compoundStm, Object inh) {
        compoundStm.stm1.accept(this, inh);
        text.append("\n");
        compoundStm.stm2.accept(this, inh);

        return null;
    }

    public Object visit(AssignStm assignStm, Object inh) {
        Register reg = (Register) assignStm.exp.accept(this, inh);
        text.append("sw " + reg + ", " + assignStm.id.accept(this, inh) + "\n");
        RegisterManager.add(reg);

        return null;
    }

    public Object visit(PrintStm printStm, Object inh) {
        return printStm.exps.accept(this, inh);
    }

    public Object visit(PairExpList pairExpList, Object inh) {
        Register regHead = (Register) pairExpList.head.accept(this, inh);
        addPrintCode(regHead);
        RegisterManager.add(regHead);

        Register regTail = (Register) pairExpList.tail.accept(this, inh);
        RegisterManager.add(regTail);

        return null;
    }

    public Object visit(LastExpList lastExpList, Object inh) {
        Register reg = (Register) lastExpList.head.accept(this, inh);
        addPrintCode(reg);
        addNewlineCode();
        
        return reg;
    }//change

    public Object visit(EseqExp eseqExp, Object inh) {
        eseqExp.stm.accept(this, inh);

        return eseqExp.exp.accept(this, inh);
    }

    public Object visit(IdExp idExp, Object inh) {
        if(!symbols.contains(idExp.id)) {
            symbols.add(idExp.id);
            data.append(idExp.id + ": .word 0\n");
            
            return idExp.id;
        } else {
            Register reg = RegisterManager.get('t', null);
            text.append("lw " + reg + ", " + idExp.id + "\n");
            
            return reg;
        }        
    }

    public Object visit(NumExp numExp, Object inh) {
        Register reg = RegisterManager.get('t', numExp.num);
        text.append("li " + reg + ", " + reg.getValue() + "\n");

        return reg;
    }

    public Object visit(OpExp opExp, Object inh) {
        Register argLeft = (Register) opExp.left.accept(this, inh);
        Register argRight = (Register) opExp.right.accept(this, inh);
        Register result = RegisterManager.get('t', null);
        String post = result + ", "
                + argLeft + ", "
                + argRight + "\n";

        switch (opExp.oper) {
            case OpExp.ADD -> text.append("add " + post);
            case OpExp.SUB -> text.append("sub " + post);
            case OpExp.MUL -> text.append("mul " + post);
            case OpExp.DIV -> text.append("div " + post);
            default -> throw new IllegalStateException("Unexpected operator enum value: " + opExp.oper);
        };

        return result;
    }

    public String toString() {
        addExitCode();

        return data.toString() + "\n" + text.toString();
    }

    private void addPrintCode(Register reg) {
        text.append("li $v0, 1\n");
        text.append("move $a0, " + reg + "\n");
        text.append("syscall\n");
    }

    private void addNewlineCode() {
        text.append("li $v0, 4\n");
        text.append("la $a0, newline\n");
        text.append("syscall\n\n");
    }

    private void addExitCode() {
        text.append("li $v0, 10\n");
        text.append("syscall\n");
    }
}
