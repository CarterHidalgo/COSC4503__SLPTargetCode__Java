package parser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import ast.AST;
import ast.Stm;

public class Parser {
    private static Map<Token.Type, Map<Integer, String>> actionTable = new HashMap<>();
    private static Map<Nonterminal.Type, Map<Integer, Integer>> gotoTable = new HashMap<>();
    private static List<Pair> grammar = new ArrayList<>();
    private static final Set<String> tokens = Arrays.stream(Token.Type.values()).map(Enum::name)
            .collect(Collectors.toSet());
    private static final Set<String> nonterminals = Arrays.stream(Nonterminal.Type.values()).map(Enum::name)
            .collect(Collectors.toSet());
    private static final Map<Integer, HashSet<Token.Type>> expected = new HashMap<>();

    static {
        Path actionTableFile = Paths.get("resources", "actiontable.txt");
        Path gotoTableFile = Paths.get("resources", "gototable.txt");
        Path grammarFile = Paths.get("resources", "grammar.txt");

        try (Scanner input = new Scanner(actionTableFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(" ;");
                int state = Integer.valueOf(parts[0]);
                Token.Type terminal = Token.Type.valueOf(parts[1]);
                String action = parts[2];

                if (!actionTable.containsKey(terminal)) {
                    actionTable.put(terminal, new HashMap<>());
                }
                actionTable.get(terminal).put(state, action);

                if (!expected.containsKey(state)) {
                    expected.put(state, new HashSet<>());
                }
                expected.get(state).add(terminal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Scanner input = new Scanner(gotoTableFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(" ;");
                int state = Integer.valueOf(parts[0]);
                Nonterminal.Type nonterminal = Nonterminal.Type.valueOf(parts[1]);
                int action = Integer.valueOf(parts[2]);

                if (!gotoTable.containsKey(nonterminal)) {
                    gotoTable.put(nonterminal, new HashMap<>());
                }

                gotoTable.get(nonterminal).put(state, action);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Scanner input = new Scanner(grammarFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(" ");
                List<StackItem> rule = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    if (tokens.contains(parts[i])) {
                        rule.add(new Token(Token.Type.valueOf(parts[i])));
                    } else if (nonterminals.contains(parts[i])) {
                        rule.add(new Nonterminal(Nonterminal.Type.valueOf(parts[i])));
                    } else {
                        throw new RuntimeException("Unsupported StackItem value \"" + parts[i] + "\"");
                    }
                }

                grammar.add(new Pair(Nonterminal.Type.valueOf(parts[0]), rule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stm parse(Path program) {
        Lexer lexer = new Lexer(program);
        Stack<StackItem> stack = new Stack<>();
        boolean exception = false;
        int state = 0;
        Token token;

        token = lexer.nextToken();
        stack.push(new Token(Token.Type.INVALID));
        stack.push(new State(0));

        while (true) {
            try {
                state = ((State) stack.peek()).value();
                String cmd = actionTable.get(token.type()).get(state);

                if (cmd == null) {
                    throw new SyntaxException("Exception in thread \"main\" SyntaxException: Unexpected symbol \"" +
                            token.value() + "\" expected one of " + expected.get(state) +
                            "\n" + " ".repeat(8) + "at " + program + ":" + lexer.getLineNumber());
                } else if (cmd.startsWith("r")) {
                    int rule = Integer.valueOf(cmd.substring(1)) - 1;

                    List<StackItem> items = new ArrayList<>();
                    for (int i = 0; i < (grammar.get(rule).second().size()); i++) {
                        stack.pop();
                        items.add(stack.pop());
                    }

                    Nonterminal nonterminal = new Nonterminal(grammar.get(rule).first(), AST.get(rule, items));

                    state = ((State) stack.peek()).value();
                    stack.push(nonterminal);
                    stack.push(new State(gotoTable.get(nonterminal.type()).get(state)));
                } else if (cmd.startsWith("s")) {
                    stack.push(token);
                    stack.push(new State(Integer.valueOf(cmd.substring(1))));

                    token = lexer.nextToken();
                } else if (cmd.startsWith("a")) {
                    break;
                }
            } catch (SyntaxException e) {
                exception = true;
                System.out.println(e.getMessage());

                token = lexer.nextLine();
                if (token != null) {
                    stack.clear();
                    stack.push(new Token(Token.Type.EOF));
                    stack.push(new State(0));
                } else {
                    break;
                }
            }
        }

        if (!exception) {
            stack.pop();
            return (Stm) stack.peek().value();
        } else {
            return null;
        }

    }
}
