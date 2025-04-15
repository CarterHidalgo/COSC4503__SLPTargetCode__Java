package parser;

public class Token implements StackItem {
    private final Token.Type type;
    private final String value;
    
    public static enum Type {
        ID, PRINT, NUM, SEMICOLON, ASSIGN, LPAREN, RPAREN, COMMA, ADD, SUB, MUL, DIV, EPSILON, EOF, LINE, INVALID
    }

    public Token(Token.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(Token.Type type) {
        this.type = type;
        this.value = "";
    }

    public Token.Type type() {
        return type;
    }

    public String typeString() {
        return type.toString();
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.isEmpty() ? this.type.toString() : "<" + this.type + ", " + this.value + ">";
    }
}
