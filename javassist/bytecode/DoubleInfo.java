package javassist.bytecode;

import java.util.*;
import java.io.*;

class DoubleInfo extends ConstInfo
{
    static final int tag = 6;
    double value;
    
    public DoubleInfo(final double d, final int index) {
        super(index);
        this.value = d;
    }
    
    public DoubleInfo(final DataInputStream in, final int index) throws IOException {
        super(index);
        this.value = in.readDouble();
    }
    
    @Override
    public int hashCode() {
        final long v = Double.doubleToLongBits(this.value);
        return (int)(v ^ v >>> 32);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof DoubleInfo && ((DoubleInfo)obj).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 6;
    }
    
    @Override
    public int copy(final ConstPool src, final ConstPool dest, final Map<String, String> map) {
        return dest.addDoubleInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream out) throws IOException {
        out.writeByte(6);
        out.writeDouble(this.value);
    }
    
    @Override
    public void print(final PrintWriter out) {
        out.print("Double ");
        out.println(this.value);
    }
}
