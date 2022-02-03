package org.reflections.scanners;

import javassist.bytecode.*;
import java.util.*;

@Deprecated
class AbstractScanner implements Scanner
{
    protected final Scanner scanner;
    
    AbstractScanner(final Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String index() {
        return this.scanner.index();
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile cls) {
        return this.scanner.scan(cls);
    }
}
