package visitors;

import java.util.HashSet;
import java.util.Set;

import ast.AssignStm;
import ast.CompoundStm;
import ast.EseqExp;
import ast.IdExp;
import ast.LastExpList;
import ast.NumExp;
import ast.OpExp;
import ast.PairExpList;
import ast.PrintStm;

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

    public Object visit(CompoundStm compoundStm) {
        compoundStm.stm1.accept(this);
        text.append("\n");
        compoundStm.stm2.accept(this);

        return null;
    }

    public Object visit(AssignStm assignStm) {
        String reg = (String) assignStm.exp.accept(this);
        text.append("sw " + reg + ", " + assignStm.id.accept(this) + "\n");

        return null;
    }

    public Object visit(PrintStm printStm) {
        return printStm.exps.accept(this);
    }

    public Object visit(PairExpList pairExpList) {
        String regHead = (String) pairExpList.head.accept(this);
        addPrintCode(regHead);
        pairExpList.tail.accept(this);

        return null;
    }

    public Object visit(LastExpList lastExpList) {
        String reg = (String) lastExpList.head.accept(this);
        addPrintCode(reg);
        addNewlineCode();
        
        return reg;
    }

    public Object visit(EseqExp eseqExp) {
        eseqExp.stm.accept(this);

        return eseqExp.exp.accept(this);
    }

    public Object visit(IdExp idExp) {
        if(!symbols.contains(idExp.id)) {
            symbols.add(idExp.id);
            data.append(idExp.id + ": .word 0\n");
            
            return idExp.id;
        } else {
            String reg = Registers.get();
            text.append("lw " + reg + ", " + idExp.id + "\n");
            
            return reg;
        }        
    }

    public Object visit(NumExp numExp) {
        String reg = Registers.get();
        text.append("li " + reg + ", " + numExp.num + "\n");

        return reg;
    }

    public Object visit(OpExp opExp) {
        String argLeft = (String) opExp.left.accept(this);
        String argRight = (String) opExp.right.accept(this);
        String result = Registers.get();
        String postfix = result + ", "
                + argLeft + ", "
                + argRight + "\n";

        switch (opExp.oper) {
            case OpExp.ADD -> text.append("add " + postfix);
            case OpExp.SUB -> text.append("sub " + postfix);
            case OpExp.MUL -> text.append("mul " + postfix);
            case OpExp.DIV -> text.append("div " + postfix);
            default -> throw new IllegalStateException("Unexpected operator enum value: " + opExp.oper);
        };

        return result;
    }

    public String toString() {
        addExitCode();

        return data.toString() + "\n" + text.toString();
    }

    private void addPrintCode(String reg) {
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
