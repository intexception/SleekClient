package org.reflections.scanners;

import java.util.function.*;
import java.util.*;
import org.reflections.util.*;
import javassist.bytecode.*;

public class TypeElementsScanner implements Scanner
{
    private boolean includeFields;
    private boolean includeMethods;
    private boolean includeAnnotations;
    private boolean publicOnly;
    private Predicate<String> resultFilter;
    
    public TypeElementsScanner() {
        this.includeFields = true;
        this.includeMethods = true;
        this.includeAnnotations = true;
        this.publicOnly = true;
        this.resultFilter = (s -> true);
    }
    
    @Override
    public List<Map.Entry<String, String>> scan(final ClassFile classFile) {
        final List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>();
        final String className = classFile.getName();
        if (this.resultFilter.test(className) && this.isPublic(classFile)) {
            entries.add(this.entry(className, ""));
            if (this.includeFields) {
                classFile.getFields().forEach(field -> entries.add(this.entry(className, field.getName())));
            }
            if (this.includeMethods) {
                classFile.getMethods().stream().filter(this::isPublic).forEach(method -> entries.add(this.entry(className, method.getName() + "(" + String.join(", ", JavassistHelper.getParameters(method)) + ")")));
            }
            if (this.includeAnnotations) {
                JavassistHelper.getAnnotations(classFile::getAttribute).stream().filter((Predicate<? super Object>)this.resultFilter).forEach(annotation -> entries.add(this.entry(className, "@" + annotation)));
            }
        }
        return entries;
    }
    
    private boolean isPublic(final Object object) {
        return !this.publicOnly || JavassistHelper.isPublic(object);
    }
    
    public TypeElementsScanner filterResultsBy(final Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }
    
    public TypeElementsScanner includeFields() {
        return this.includeFields(true);
    }
    
    public TypeElementsScanner includeFields(final boolean include) {
        this.includeFields = include;
        return this;
    }
    
    public TypeElementsScanner includeMethods() {
        return this.includeMethods(true);
    }
    
    public TypeElementsScanner includeMethods(final boolean include) {
        this.includeMethods = include;
        return this;
    }
    
    public TypeElementsScanner includeAnnotations() {
        return this.includeAnnotations(true);
    }
    
    public TypeElementsScanner includeAnnotations(final boolean include) {
        this.includeAnnotations = include;
        return this;
    }
    
    public TypeElementsScanner publicOnly(final boolean only) {
        this.publicOnly = only;
        return this;
    }
    
    public TypeElementsScanner publicOnly() {
        return this.publicOnly(true);
    }
}
