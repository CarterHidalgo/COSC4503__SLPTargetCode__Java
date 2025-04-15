package register;

public class Register {
    Object value;
    String name;
    
    public Register(String name, int value) {
        this.value = value;
        this.name = name;
    }

    public Register(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public void set(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    @Override 
    public String toString() {
        return "$" + this.name;
    }
}