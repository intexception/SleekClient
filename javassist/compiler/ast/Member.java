package javassist.compiler.ast;

import javassist.*;
import javassist.compiler.*;

public class Member extends Symbol
{
    private static final long serialVersionUID = 1L;
    private CtField field;
    
    public Member(final String name) {
        super(name);
        this.field = null;
    }
    
    public void setField(final CtField f) {
        this.field = f;
    }
    
    public CtField getField() {
        return this.field;
    }
    
    @Override
    public void accept(final Visitor v) throws CompileError {
        v.atMember(this);
    }
}
