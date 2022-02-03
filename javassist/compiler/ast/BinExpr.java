package javassist.compiler.ast;

import javassist.compiler.*;

public class BinExpr extends Expr
{
    private static final long serialVersionUID = 1L;
    
    private BinExpr(final int op, final ASTree _head, final ASTList _tail) {
        super(op, _head, _tail);
    }
    
    public static BinExpr makeBin(final int op, final ASTree oprand1, final ASTree oprand2) {
        return new BinExpr(op, oprand1, new ASTList(oprand2));
    }
    
    @Override
    public void accept(final Visitor v) throws CompileError {
        v.atBinExpr(this);
    }
}
