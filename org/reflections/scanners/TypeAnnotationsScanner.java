package org.reflections.scanners;

import javassist.bytecode.*;
import java.util.*;

@Deprecated
public class TypeAnnotationsScanner extends AbstractScanner
{
    @Deprecated
    public TypeAnnotationsScanner() {
        super(Scanners.TypesAnnotated);
    }
}
