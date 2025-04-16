package parser;

public class Token implements StackItem {
    private final Token.Type type;
    private final Object value;
    
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
        return this.type;
    }

    public Object value() {
        return this.value;
    }

    @Override
    public String toString() {
        return "<" + this.type + ", " + this.value.toString() + ">";
    }
}
