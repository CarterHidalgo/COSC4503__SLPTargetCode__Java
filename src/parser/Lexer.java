package parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private Scanner input;
    private Queue<Token> list;
    private int lineNumber = 1;
    private int pos = 0;
    private Map<String, Token.Type> patterns = new LinkedHashMap<>() {{
        put("print", Token.Type.PRINT);
        put("[a-zA-Z_][a-zA-Z0-9_]*", Token.Type.ID);
        put("\\d+", Token.Type.NUM);
        put(";", Token.Type.SEMICOLON);
        put(":=", Token.Type.ASSIGN);
        put("\\(", Token.Type.LPAREN);
        put("\\)", Token.Type.RPAREN);
        put(",", Token.Type.COMMA);
        put("\\+", Token.Type.ADD);
        put("-", Token.Type.SUB);
        put("\\*", Token.Type.MUL);
        put("/", Token.Type.DIV);
        put("", Token.Type.EPSILON);
        put("$", Token.Type.EOF);
    }};

    public Lexer(Path program) {
        try {
            this.input = new Scanner(program);
            this.lex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void lex() {
        this.list = new LinkedList<>();
        this.lineNumber = 1;

        while(this.input.hasNext()) {
            String line = this.input.nextLine();
            this.list.add(new Token(Token.Type.LINE, String.valueOf(this.lineNumber++)));

            if(line.isBlank()) {
                continue;
            }

            this.pos = 0;
            while(pos < line.length()) {
                char c = line.charAt(pos);

                if(Character.isWhitespace(c)) {
                    pos++;
                    continue;
                }
                
                if(pos >= line.length()) {
                    throw new RuntimeException("Unsupported character: \"" + c + "\"");
                }

                Token token = nextToken(line);

                if(token != null) {
                    list.add(token);
                } else {
                    throw new RuntimeException("Unsupported character: \"" + c + "\"");
                }
            }
        }

        this.input.close();
    }

    public Token nextToken() {
        while(!this.list.isEmpty() && this.list.peek().type().equals(Token.Type.LINE)) {
            this.lineNumber = Integer.valueOf(this.list.remove().value());
        }

        return this.list.isEmpty() ? new Token(Token.Type.EOF, "") : this.list.remove();
    }

    public Token nextLine() {
        while (!this.list.isEmpty() && !this.list.peek().type().equals(Token.Type.LINE)) {
            this.list.remove();
        }
        while (!this.list.isEmpty() && this.list.peek().type().equals(Token.Type.LINE)) {
            lineNumber = Integer.valueOf(this.list.remove().value());
        }

        if (this.list.isEmpty()) {
            return null;
        } else {
            return this.list.remove();
        }
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public boolean hasNext() {
        return !this.list.isEmpty();
    }

    private Token nextToken(String line) {
        for(Map.Entry<String, Token.Type> entry : patterns.entrySet()) {
            Pattern pattern = Pattern.compile("^" + entry.getKey());
            Matcher matcher = pattern.matcher(line.substring(this.pos));
            
            if(matcher.find()) {
                String value = matcher.group();
                this.pos += value.length();
                return new Token(entry.getValue(), value);
            }
        }

        return null;
    }
}
