package intermediates;

import visitors.Visitor;

public abstract class Stm {
    public abstract Object accept(Visitor v, Object inh);
}