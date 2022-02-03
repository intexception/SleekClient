package javassist.bytecode.annotation;

import javassist.bytecode.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class CharMemberValue extends MemberValue
{
    int valueIndex;
    
    public CharMemberValue(final int index, final ConstPool cp) {
        super('C', cp);
        this.valueIndex = index;
    }
    
    public CharMemberValue(final char c, final ConstPool cp) {
        super('C', cp);
        this.setValue(c);
    }
    
    public CharMemberValue(final ConstPool cp) {
        super('C', cp);
        this.setValue('\0');
    }
    
    @Override
    Object getValue(final ClassLoader cl, final ClassPool cp, final Method m) {
        return this.getValue();
    }
    
    @Override
    Class<?> getType(final ClassLoader cl) {
        return Character.TYPE;
    }
    
    public char getValue() {
        return (char)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final char newValue) {
        this.valueIndex = this.cp.addIntegerInfo(newValue);
    }
    
    @Override
    public String toString() {
        return Character.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor visitor) {
        visitor.visitCharMemberValue(this);
    }
}
