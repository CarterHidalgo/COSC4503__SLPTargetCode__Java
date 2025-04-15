package register;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class RegisterManager {
    private static List<Register> registerPool = new ArrayList<>();
    private static Register argReg = new Register("a0", 0);
    private static Register valueReg = new Register("v0", 0);
    
    static {
        for(int i = 0; i < 10; i++) {
            registerPool.add(new Register("t" + String.valueOf(i), 0));
        }
    }

    public static Register get(char key, Object value) {
        if(registerPool.size() <= 0) {
            throw new IndexOutOfBoundsException("index 0 is out of bounds for size 0: no registers left");
        } else {
            switch (key) {
            case 't':
                registerPool.get(0).set(value);
                return registerPool.remove(0);
            case 'a':
                argReg.set(value);
                return argReg;
            case 'v':
                valueReg.set(value);
                return valueReg;
            default:
                throw new InvalidParameterException("get method must have a parameter value of ['t', 'a', 'v']");
        }
        }
        
    }

    public static void add(Register reg) {
        registerPool.add(reg);
    }
}