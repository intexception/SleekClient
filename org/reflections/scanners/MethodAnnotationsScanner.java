package org.reflections.scanners;

import javassist.bytecode.*;
import java.util.*;

@Deprecated
public class MethodAnnotationsScanner extends AbstractScanner
{
    @Deprecated
    public MethodAnnotationsScanner() {
        super(Scanners.MethodsAnnotated);
    }
}
