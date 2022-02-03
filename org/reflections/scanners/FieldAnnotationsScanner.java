package org.reflections.scanners;

import javassist.bytecode.*;
import java.util.*;

@Deprecated
public class FieldAnnotationsScanner extends AbstractScanner
{
    @Deprecated
    public FieldAnnotationsScanner() {
        super(Scanners.FieldsAnnotated);
    }
}
