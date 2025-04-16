### Target Code Generation
    The program uses a visitor to generate target code. The visitor removes from and adds to a pool of available registers (Strings).
    Target code is appended into two StringBuffers, one for the .text and one for the .data. These are returned in the visitor toString().
    Target code is written to target\target.asm and will compile and run in the MARS simulator. 

### Time Investment
    A running tally of time investment

    Parser: 9 hours
    Intermediate Code Generation: 8 hours
    Target Code Generation: 5 hours