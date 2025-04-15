package parser;

import java.util.List;

public class Nonterminal implements StackItem {
    private Nonterminal.Type type;
    private String value;

    enum Type {
        STM, EXP, EXPLIST, BINOP
    }

    public Nonterminal(Nonterminal.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Nonterminal(Nonterminal.Type type) {
        this.type = type;
    }

    public static String replace(List<StackItem> items, String code) {
        if(!code.contains("#")) {
            return code;
        }
        
        String key = code.substring(code.indexOf("#"), code.indexOf("#", code.indexOf("#") + 1) + 1);

        while(true) {
            for(int i = items.size() - 1; i >= 0; i--) {
                if(items.get(i).typeString().equals(key.substring(1, key.length() - 1))) {
                    code = code.replaceFirst(key, items.get(i).value());
                    items.remove(i);
                    break;
                }
            }

            if(code.contains("#")) {
                key = code.substring(code.indexOf("#"), code.indexOf("#", code.indexOf("#") + 1) + 1);
            } else {
                break;
            }
        }
        
        return code;
    }

    public Nonterminal.Type type() {
        return this.type;
    }

    public String typeString() {
        return this.type.toString();
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
