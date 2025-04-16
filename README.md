### Parser
The parser has been reworked to generate the abstract syntax tree to allow for a single program execution to generate target code. This was accomplished using java.util.function.Function in src\ast\AST.java. This allows for a mapping between integer rule numbers in the grammar and constructor calls. The StackItem value() method stores the running tree in Nonterminals or the proper value to insert in Tokens. Burner methods such as typeString() have been removed. The parser method now returns a Stm() instance for the intermediate code representation. The String for this IR code can be output using the toString method. 

### Target Code Generation
The program uses a visitor to generate target code. The visitor removes from and adds to a pool of available registers (Strings). Target code is appended into two StringBuffers, one for text and one for data. These are returned in the visitor toString(). Target code is written to target\target.asm and will compile and run in the MARS simulator. A successful generation will not output any error messages. The expected output for the test program is:

    87
    80

The output should contain a final newline character at the end. 

### Time Investment
A running tally of time investment:

- Parser v1: 9 hours
- Intermediate Code Generation: 8 hours
- Target Code Generation: 5 hours
- Parser v2: 5 hours