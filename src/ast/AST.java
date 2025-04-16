package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import parser.StackItem;
import parser.Token;

public class AST {
    private static final Map<Integer, Function<List<StackItem>, Object>> astCode = new HashMap<>();

    static {
        astCode.put(0, args -> new CompoundStm((Stm) args.get(2).value(), (Stm) args.get(0).value()));
        astCode.put(1, args -> new AssignStm(new IdExp((String) args.get(2).value()), (Exp) args.get(0).value()));
        astCode.put(2, args -> new PrintStm((ExpList) args.get(1).value()));
        astCode.put(4, args -> new IdExp((String) args.get(0).value()));
        astCode.put(5, args -> new NumExp(Integer.valueOf(((Token) args.get(0)).value().toString())));
        astCode.put(6, args -> new OpExp((Exp) args.get(2).value(), (int) args.get(1).value(), (Exp) args.get(0).value()));
        astCode.put(7, args -> new EseqExp((Stm) args.get(3).value(), (Exp) args.get(1).value()));
        astCode.put(8, args -> new PairExpList((Exp) args.get(2).value(), (ExpList) args.get(0).value()));
        astCode.put(9, args -> new LastExpList((Exp) args.get(0).value()));
        astCode.put(10, args -> OpExp.ADD);
        astCode.put(11, args -> OpExp.SUB);
        astCode.put(12, args -> OpExp.MUL);
        astCode.put(13, args -> OpExp.DIV);
    }

    public static Object get(int rule, List<StackItem> args) {
        return astCode.get(rule).apply(args);
    }
}
