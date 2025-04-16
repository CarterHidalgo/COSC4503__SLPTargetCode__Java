package visitors;

import java.util.ArrayList;
import java.util.List;

public class Registers {
    private static List<String> registerPool = new ArrayList<>();
    
    static {
        for(int i = 0; i < 10; i++) {
            registerPool.add("$t" + i);
        }
    }

    public static String get() {
        if(!registerPool.isEmpty()) {
            return registerPool.remove(0);
        } else {
            throw new IndexOutOfBoundsException("index 0 is out of bounds for size 0: no registers available");
        }        
    }

    public static void add(String reg) {
        registerPool.add(reg);
    }
}