package javassist.compiler.ast;

import javassist.compiler.*;

public class Keyword extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected int tokenId;
    
    public Keyword(final int token) {
        this.tokenId = token;
    }
    
    public int get() {
        return this.tokenId;
    }
    
    @Override
    public String toString() {
        return "id:" + this.tokenId;
    }
    
    @Override
    public void accept(final Visitor v) throws CompileError {
        v.atKeyword(this);
    }
}
