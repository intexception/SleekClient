package org.reflections.util;

import org.reflections.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.regex.*;

public class FilterBuilder implements Predicate<String>
{
    private final List<Predicate<String>> chain;
    
    public FilterBuilder() {
        this.chain = new ArrayList<Predicate<String>>();
    }
    
    private FilterBuilder(final Collection<Predicate<String>> filters) {
        (this.chain = new ArrayList<Predicate<String>>()).addAll(filters);
    }
    
    public FilterBuilder includePackage(final String value) {
        return this.includePattern(prefixPattern(value));
    }
    
    public FilterBuilder excludePackage(final String value) {
        return this.excludePattern(prefixPattern(value));
    }
    
    public FilterBuilder includePattern(final String regex) {
        return this.add(new Include(regex));
    }
    
    public FilterBuilder excludePattern(final String regex) {
        return this.add(new Exclude(regex));
    }
    
    @Deprecated
    public FilterBuilder include(final String regex) {
        return this.add(new Include(regex));
    }
    
    @Deprecated
    public FilterBuilder exclude(final String regex) {
        this.add(new Exclude(regex));
        return this;
    }
    
    public static FilterBuilder parsePackages(final String includeExcludeString) {
        final List<Predicate<String>> filters = new ArrayList<Predicate<String>>();
        for (final String string : includeExcludeString.split(",")) {
            final String trimmed = string.trim();
            final char prefix = trimmed.charAt(0);
            final String pattern = prefixPattern(trimmed.substring(1));
            switch (prefix) {
                case '+': {
                    filters.add(new Include(pattern));
                    break;
                }
                case '-': {
                    filters.add(new Exclude(pattern));
                    break;
                }
                default: {
                    throw new ReflectionsException("includeExclude should start with either + or -");
                }
            }
        }
        return new FilterBuilder(filters);
    }
    
    public FilterBuilder add(final Predicate<String> filter) {
        this.chain.add(filter);
        return this;
    }
    
    @Override
    public boolean test(final String regex) {
        boolean accept = this.chain.isEmpty() || this.chain.get(0) instanceof Exclude;
        for (final Predicate<String> filter : this.chain) {
            if (accept && filter instanceof Include) {
                continue;
            }
            if (!accept && filter instanceof Exclude) {
                continue;
            }
            accept = filter.test(regex);
            if (!accept && filter instanceof Exclude) {
                break;
            }
        }
        return accept;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals(this.chain, ((FilterBuilder)o).chain));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.chain);
    }
    
    @Override
    public String toString() {
        return this.chain.stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, String>)Collectors.joining(", "));
    }
    
    private static String prefixPattern(String fqn) {
        if (!fqn.endsWith(".")) {
            fqn += ".";
        }
        return fqn.replace(".", "\\.").replace("$", "\\$") + ".*";
    }
    
    abstract static class Matcher implements Predicate<String>
    {
        final Pattern pattern;
        
        Matcher(final String regex) {
            this.pattern = Pattern.compile(regex);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.pattern);
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals(this.pattern.pattern(), ((Matcher)o).pattern.pattern()));
        }
        
        @Override
        public String toString() {
            return this.pattern.pattern();
        }
    }
    
    static class Include extends Matcher
    {
        Include(final String regex) {
            super(regex);
        }
        
        @Override
        public boolean test(final String regex) {
            return this.pattern.matcher(regex).matches();
        }
        
        @Override
        public String toString() {
            return "+" + this.pattern;
        }
    }
    
    static class Exclude extends Matcher
    {
        Exclude(final String regex) {
            super(regex);
        }
        
        @Override
        public boolean test(final String regex) {
            return !this.pattern.matcher(regex).matches();
        }
        
        @Override
        public String toString() {
            return "-" + this.pattern;
        }
    }
}
