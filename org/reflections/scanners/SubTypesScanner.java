package org.reflections.scanners;

import javassist.bytecode.*;
import java.util.*;

@Deprecated
public class SubTypesScanner extends AbstractScanner
{
    @Deprecated
    public SubTypesScanner() {
        super(Scanners.SubTypes);
    }
    
    @Deprecated
    public SubTypesScanner(final boolean excludeObjectClass) {
        super(excludeObjectClass ? Scanners.SubTypes : Scanners.SubTypes.filterResultsBy(s -> true));
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile cls) {
        return this.scanner.scan(cls);
    }
}
