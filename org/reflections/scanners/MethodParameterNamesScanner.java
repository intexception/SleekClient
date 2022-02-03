package org.reflections.scanners;

import org.reflections.util.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.stream.*;
import javassist.bytecode.*;

public class MethodParameterNamesScanner implements Scanner
{
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>();
        for (final MethodInfo method : classFile.getMethods()) {
            final String key = JavassistHelper.methodName(classFile, method);
            final String value = this.getString(method);
            if (!value.isEmpty()) {
                entries.add(this.entry(key, value));
            }
        }
        return entries;
    }
    
    private String getString(final MethodInfo method) {
        final CodeAttribute codeAttribute = method.getCodeAttribute();
        final LocalVariableAttribute table = (codeAttribute != null) ? ((LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable")) : null;
        final int length = JavassistHelper.getParameters(method).size();
        if (length > 0) {
            final int shift = Modifier.isStatic(method.getAccessFlags()) ? 0 : 1;
            return IntStream.range(shift, length + shift).mapToObj(i -> method.getConstPool().getUtf8Info(table.nameIndex(i))).filter(name -> !name.startsWith("this$")).collect((Collector<? super Object, ?, String>)Collectors.joining(", "));
        }
        return "";
    }
}
