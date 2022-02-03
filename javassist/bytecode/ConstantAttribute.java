package javassist.bytecode;

import java.io.*;
import java.util.*;

public class ConstantAttribute extends AttributeInfo
{
    public static final String tag = "ConstantValue";
    
    ConstantAttribute(final ConstPool cp, final int n, final DataInputStream in) throws IOException {
        super(cp, n, in);
    }
    
    public ConstantAttribute(final ConstPool cp, final int index) {
        super(cp, "ConstantValue");
        final byte[] bvalue = { (byte)(index >>> 8), (byte)index };
        this.set(bvalue);
    }
    
    public int getConstantValue() {
        return ByteArray.readU16bit(this.get(), 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool newCp, final Map<String, String> classnames) {
        final int index = this.getConstPool().copy(this.getConstantValue(), newCp, classnames);
        return new ConstantAttribute(newCp, index);
    }
}
