### Modifications
    Parser: The parser uses two tables, an action table and a goto table, alongisde a grammar structure to parse input. The tables and grammar are encoded in text files in the resources directory using a standard format. Each grammar rule also includes intermediate code with dummy types surrounded by the # symbol (ex: #EXP#) to be generated at each reduction. When a reduction occurs, it pulls the intermediate code from the grammar and replaces the token and/or nonterminal placeholders with values from the stack. Replacements occur in reverse order, with the last spot being filled with the most recent item on the stack to preserve code order and operator precedence. 

    Token: A class that stores a <Token.TYPE, VALUE> pair representing a token type and the associated value. Ex: <ID, a> and <NUM, 8>. Implements StackItem interface with a value and typeString method.

    Nonterminal: A class that stores a <Nonterminal.TYPE, VALUE> pair representing a nonterminal type and the associated code value. Ex: <EXP, "new AssignStm(new IdExp("a", new NumExp(3)))">. Implements StackItem interface with a value and typeString method. 

    Driver: Opens the test files and attempts to parse all tests files or a single file if specified. 

### Time Investment

    A running tally of time investment

    Initial parser: 9 hours
    Intermediate code generation: 8 hours