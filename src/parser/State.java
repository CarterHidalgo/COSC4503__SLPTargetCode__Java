package parser;

public class State implements StackItem {
    private int state;

    public State(int state) {
        this.state = state;
    }

    public Integer value() {
        return state;
    }

    @Override
    public String toString() {
        return String.valueOf(this.state);
    }
}
