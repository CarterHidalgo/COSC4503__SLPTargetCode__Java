package driver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ast.Stm;
import parser.Parser;
import visitors.TargetCodeVisitor;

public class Driver {
    public static void main(String[] args) {
        Path source = Paths.get("tests", "program.slp");
        Path target = Paths.get("target", "target.asm");

        Stm program = Parser.parse(source);
        TargetCodeVisitor targetCode = new TargetCodeVisitor();

        program.accept(targetCode);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(target.toFile()))) {
            writer.write(targetCode.toString());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
