package javassist.bytecode;

import java.io.*;
import java.util.*;

public class DeprecatedAttribute extends AttributeInfo
{
    public static final String tag = "Deprecated";
    
    DeprecatedAttribute(final ConstPool cp, final int n, final DataInputStream in) throws IOException {
        super(cp, n, in);
    }
    
    public DeprecatedAttribute(final ConstPool cp) {
        super(cp, "Deprecated", new byte[0]);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool newCp, final Map<String, String> classnames) {
        return new DeprecatedAttribute(newCp);
    }
}
