package visitors;

public class Registers {
    private static int numRegs = 10;
    private static int index = 0;
    
    public static String get() {
        return "$t" + (index++ % numRegs);        
    }
}