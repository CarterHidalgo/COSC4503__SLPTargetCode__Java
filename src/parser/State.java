package parser;

public class State implements StackItem {
    private int state;

    public State(int state) {
        this.state = state;
    }

    public int state() {
        return state;
    }

    public String value() {
        return String.valueOf(this.state);
    }

    public String typeString() {
        return "";
    }

    @Override
    public String toString() {
        return String.valueOf(this.state);
    }
}
