package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class BooleanMemberValue extends MemberValue
{
    int valueIndex;
    
    public BooleanMemberValue(final int index, final ConstPool cp) {
        super('Z', cp);
        this.valueIndex = index;
    }
    
    public BooleanMemberValue(final boolean b, final ConstPool cp) {
        super('Z', cp);
        this.setValue(b);
    }
    
    public BooleanMemberValue(final ConstPool cp) {
        super('Z', cp);
        this.setValue(false);
    }
    
    @Override
    Object getValue(final ClassLoader cl, final ClassPool cp, final Method m) {
        return this.getValue();
    }
    
    @Override
    Class<?> getType(final ClassLoader cl) {
        return Boolean.TYPE;
    }
    
    public boolean getValue() {
        return this.cp.getIntegerInfo(this.valueIndex) != 0;
    }
    
    public void setValue(final boolean newValue) {
        this.valueIndex = this.cp.addIntegerInfo(newValue ? 1 : 0);
    }
    
    @Override
    public String toString() {
        return this.getValue() ? "true" : "false";
    }
    
    @Override
    public void write(final AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor visitor) {
        visitor.visitBooleanMemberValue(this);
    }
}
