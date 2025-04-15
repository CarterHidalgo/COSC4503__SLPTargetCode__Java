package driver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import intermediates.AssignStm;
import intermediates.CompoundStm;
import intermediates.EseqExp;
import intermediates.IdExp;
import intermediates.LastExpList;
import intermediates.NumExp;
import intermediates.OpExp;
import intermediates.PairExpList;
import intermediates.PrintStm;
import intermediates.Stm;
import parser.Parser;
import visitors.TargetCodeVisitor;

public class Driver {
    public static void main(String[] args) {
        Path source = Paths.get("tests", "program.slp");
        Path target = Paths.get("target", "target.asm");

        if (Parser.parse(source)) {
            TargetCodeVisitor targetCode = new TargetCodeVisitor();

            Stm intermediate = new CompoundStm(
                    new CompoundStm(
                            new AssignStm(new IdExp("a"), new OpExp(new NumExp(5), OpExp.ADD,
                                    new NumExp(3))),
                            new AssignStm(new IdExp("b"),
                                    new EseqExp(
                                            new PrintStm(new PairExpList(new IdExp("a"),
                                                    new LastExpList(
                                                            new OpExp(new IdExp("a"), OpExp.SUB, new NumExp(1))))),
                                            new OpExp(new NumExp(10), OpExp.MUL, new IdExp("a"))))),
                    new PrintStm(new LastExpList(new IdExp("b"))));

            intermediate.accept(targetCode, null);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(target.toFile()))) {
                writer.write(targetCode.toString());
            } catch (IOException e) {
                System.err.println(e);
            }

            System.out.println(targetCode);
        }
    }
}
