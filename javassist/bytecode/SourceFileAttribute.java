package javassist.bytecode;

import java.io.*;
import java.util.*;

public class SourceFileAttribute extends AttributeInfo
{
    public static final String tag = "SourceFile";
    
    SourceFileAttribute(final ConstPool cp, final int n, final DataInputStream in) throws IOException {
        super(cp, n, in);
    }
    
    public SourceFileAttribute(final ConstPool cp, final String filename) {
        super(cp, "SourceFile");
        final int index = cp.addUtf8Info(filename);
        final byte[] bvalue = { (byte)(index >>> 8), (byte)index };
        this.set(bvalue);
    }
    
    public String getFileName() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }
    
    @Override
    public AttributeInfo copy(final ConstPool newCp, final Map<String, String> classnames) {
        return new SourceFileAttribute(newCp, this.getFileName());
    }
}
