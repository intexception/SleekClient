package javassist.bytecode;

import java.util.*;
import java.io.*;

class StringInfo extends ConstInfo
{
    static final int tag = 8;
    int string;
    
    public StringInfo(final int str, final int index) {
        super(index);
        this.string = str;
    }
    
    public StringInfo(final DataInputStream in, final int index) throws IOException {
        super(index);
        this.string = in.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.string;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof StringInfo && ((StringInfo)obj).string == this.string;
    }
    
    @Override
    public int getTag() {
        return 8;
    }
    
    @Override
    public int copy(final ConstPool src, final ConstPool dest, final Map<String, String> map) {
        return dest.addStringInfo(src.getUtf8Info(this.string));
    }
    
    @Override
    public void write(final DataOutputStream out) throws IOException {
        out.writeByte(8);
        out.writeShort(this.string);
    }
    
    @Override
    public void print(final PrintWriter out) {
        out.print("String #");
        out.println(this.string);
    }
}
