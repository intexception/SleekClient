package org.reflections.serializers;

import org.reflections.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import org.reflections.scanners.*;
import java.util.stream.*;
import java.util.*;

public class JavaCodeSerializer implements Serializer
{
    private static final String pathSeparator = "_";
    private static final String doubleSeparator = "__";
    private static final String dotSeparator = ".";
    private static final String arrayDescriptor = "$$";
    private static final String tokenSeparator = "_";
    private StringBuilder sb;
    private List<String> prevPaths;
    private int indent;
    
    @Override
    public Reflections read(final InputStream inputStream) {
        throw new UnsupportedOperationException("read is not implemented on JavaCodeSerializer");
    }
    
    @Override
    public File save(final Reflections reflections, String name) {
        if (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1);
        }
        final String filename = name.replace('.', '/').concat(".java");
        final File file = Serializer.prepareFile(filename);
        final int lastDot = name.lastIndexOf(46);
        String packageName;
        String className;
        if (lastDot == -1) {
            packageName = "";
            className = name.substring(name.lastIndexOf(47) + 1);
        }
        else {
            packageName = name.substring(name.lastIndexOf(47) + 1, lastDot);
            className = name.substring(lastDot + 1);
        }
        try {
            this.sb = new StringBuilder();
            this.sb.append("//generated using Reflections JavaCodeSerializer").append(" [").append(new Date()).append("]").append("\n");
            if (packageName.length() != 0) {
                this.sb.append("package ").append(packageName).append(";\n");
                this.sb.append("\n");
            }
            this.sb.append("public interface ").append(className).append(" {\n\n");
            this.toString(reflections);
            this.sb.append("}\n");
            Files.write(new File(filename).toPath(), this.sb.toString().getBytes(Charset.defaultCharset()), new OpenOption[0]);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        return file;
    }
    
    private void toString(final Reflections reflections) {
        final Map<String, Set<String>> map = ((HashMap<K, Map<String, Set<String>>>)reflections.getStore()).get(TypeElementsScanner.class.getSimpleName());
        this.prevPaths = new ArrayList<String>();
        this.indent = 1;
        final List<String> typePaths;
        final String className;
        final List<String> fields;
        final List<String> methods;
        final List<String> annotations;
        final Map<K, Set> map2;
        final List<String> list;
        int i;
        String name;
        String params;
        String string;
        String paramsDescriptor;
        final List<String> list2;
        String string2;
        final List<String> list3;
        final int j;
        map.keySet().stream().sorted().forEach(fqn -> {
            typePaths = Arrays.asList(fqn.split("\\."));
            className = fqn.substring(fqn.lastIndexOf(46) + 1);
            fields = new ArrayList<String>();
            methods = new ArrayList<String>();
            annotations = new ArrayList<String>();
            map2.get(fqn).stream().sorted().forEach(element -> {
                if (element.startsWith("@")) {
                    list.add(element.substring(1));
                }
                else if (element.contains("(")) {
                    if (!element.startsWith("<")) {
                        i = element.indexOf(40);
                        name = element.substring(0, i);
                        params = element.substring(i + 1, element.indexOf(")"));
                        if (params.length() != 0) {
                            string = "_" + params.replace(".", "_").replace(", ", "__").replace("[]", "$$");
                        }
                        else {
                            string = "";
                        }
                        paramsDescriptor = string;
                        if (!list2.contains(name)) {
                            string2 = name;
                        }
                        else {
                            string2 = name + paramsDescriptor;
                        }
                        list2.add(string2);
                    }
                }
                else if (!element.isEmpty()) {
                    list3.add(element);
                }
                return;
            });
            j = this.indentOpen(typePaths, this.prevPaths);
            this.addPackages(typePaths, j);
            this.addClass(typePaths, className);
            this.addFields(typePaths, fields);
            this.addMethods(typePaths, fields, methods);
            this.addAnnotations(typePaths, annotations);
            this.prevPaths = typePaths;
            return;
        });
        this.indentClose(this.prevPaths);
    }
    
    protected int indentOpen(final List<String> typePaths, final List<String> prevPaths) {
        int i;
        for (i = 0; i < Math.min(typePaths.size(), prevPaths.size()) && typePaths.get(i).equals(prevPaths.get(i)); ++i) {}
        for (int j = prevPaths.size(); j > i; --j) {
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
        return i;
    }
    
    protected void indentClose(final List<String> prevPaths) {
        for (int j = prevPaths.size(); j >= 1; --j) {
            this.sb.append(this.indent(j)).append("}\n");
        }
    }
    
    protected void addPackages(final List<String> typePaths, final int i) {
        for (int j = i; j < typePaths.size() - 1; ++j) {
            this.sb.append(this.indent(this.indent++)).append("interface ").append(this.uniqueName(typePaths.get(j), typePaths, j)).append(" {\n");
        }
    }
    
    protected void addClass(final List<String> typePaths, final String className) {
        this.sb.append(this.indent(this.indent++)).append("interface ").append(this.uniqueName(className, typePaths, typePaths.size() - 1)).append(" {\n");
    }
    
    protected void addFields(final List<String> typePaths, final List<String> fields) {
        if (!fields.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface fields {\n");
            for (final String field : fields) {
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(field, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    protected void addMethods(final List<String> typePaths, final List<String> fields, final List<String> methods) {
        if (!methods.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface methods {\n");
            for (final String method : methods) {
                final String methodName = this.uniqueName(method, fields);
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(methodName, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    protected void addAnnotations(final List<String> typePaths, final List<String> annotations) {
        if (!annotations.isEmpty()) {
            this.sb.append(this.indent(this.indent++)).append("interface annotations {\n");
            for (final String annotation : annotations) {
                this.sb.append(this.indent(this.indent)).append("interface ").append(this.uniqueName(annotation, typePaths)).append(" {}\n");
            }
            final StringBuilder sb = this.sb;
            final int n = this.indent - 1;
            this.indent = n;
            sb.append(this.indent(n)).append("}\n");
        }
    }
    
    private String uniqueName(final String candidate, final List<String> prev, final int offset) {
        final String normalized = this.normalize(candidate);
        for (int i = 0; i < offset; ++i) {
            if (normalized.equals(prev.get(i))) {
                return this.uniqueName(normalized + "_", prev, offset);
            }
        }
        return normalized;
    }
    
    private String normalize(final String candidate) {
        return candidate.replace(".", "_");
    }
    
    private String uniqueName(final String candidate, final List<String> prev) {
        return this.uniqueName(candidate, prev, prev.size());
    }
    
    private String indent(final int times) {
        return IntStream.range(0, times).mapToObj(i -> "  ").collect((Collector<? super Object, ?, String>)Collectors.joining());
    }
}
