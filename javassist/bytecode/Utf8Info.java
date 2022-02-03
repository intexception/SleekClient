package javassist.bytecode;

import java.util.*;
import java.io.*;

class Utf8Info extends ConstInfo
{
    static final int tag = 1;
    String string;
    
    public Utf8Info(final String utf8, final int index) {
        super(index);
        this.string = utf8;
    }
    
    public Utf8Info(final DataInputStream in, final int index) throws IOException {
        super(index);
        this.string = in.readUTF();
    }
    
    @Override
    public int hashCode() {
        return this.string.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Utf8Info && ((Utf8Info)obj).string.equals(this.string);
    }
    
    @Override
    public int getTag() {
        return 1;
    }
    
    @Override
    public int copy(final ConstPool src, final ConstPool dest, final Map<String, String> map) {
        return dest.addUtf8Info(this.string);
    }
    
    @Override
    public void write(final DataOutputStream out) throws IOException {
        out.writeByte(1);
        out.writeUTF(this.string);
    }
    
    @Override
    public void print(final PrintWriter out) {
        out.print("UTF8 \"");
        out.print(this.string);
        out.println("\"");
    }
}
