package org.reflections.util;

import java.util.function.*;
import java.net.*;
import java.util.stream.*;
import org.reflections.*;
import java.util.*;
import org.reflections.scanners.*;

public class ConfigurationBuilder implements Configuration
{
    public static final Set<Scanner> DEFAULT_SCANNERS;
    public static final Predicate<String> DEFAULT_INPUTS_FILTER;
    private Set<Scanner> scanners;
    private Set<URL> urls;
    private Predicate<String> inputsFilter;
    private boolean isParallel;
    private ClassLoader[] classLoaders;
    private boolean expandSuperTypes;
    
    public ConfigurationBuilder() {
        this.isParallel = true;
        this.expandSuperTypes = true;
        this.urls = new HashSet<URL>();
    }
    
    public static ConfigurationBuilder build(final Object... params) {
        final ConfigurationBuilder builder = new ConfigurationBuilder();
        final List<Object> parameters = new ArrayList<Object>();
        for (final Object param : params) {
            if (param.getClass().isArray()) {
                for (final Object p2 : (Object[])param) {
                    parameters.add(p2);
                }
            }
            else if (param instanceof Iterable) {
                for (final Object p3 : (Iterable)param) {
                    parameters.add(p3);
                }
            }
            else {
                parameters.add(param);
            }
        }
        final ClassLoader[] loaders = Stream.of(params).filter(p -> p instanceof ClassLoader).distinct().toArray(ClassLoader[]::new);
        if (loaders.length != 0) {
            builder.addClassLoaders(loaders);
        }
        final FilterBuilder inputsFilter = new FilterBuilder();
        builder.filterInputsBy(inputsFilter);
        for (final Object param : parameters) {
            if (param instanceof String && !((String)param).isEmpty()) {
                builder.forPackage((String)param, loaders);
                inputsFilter.includePackage((String)param);
            }
            else if (param instanceof Class && !Scanner.class.isAssignableFrom((Class<?>)param)) {
                builder.addUrls(ClasspathHelper.forClass((Class<?>)param, loaders));
                inputsFilter.includePackage(((Class)param).getPackage().getName());
            }
            else if (param instanceof URL) {
                builder.addUrls((URL)param);
            }
            else if (param instanceof Scanner) {
                builder.addScanners((Scanner)param);
            }
            else {
                if (param instanceof Class && Scanner.class.isAssignableFrom((Class<?>)param)) {
                    try {
                        builder.addScanners(((Class)param).getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]));
                        continue;
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!(param instanceof Predicate)) {
                    throw new ReflectionsException("could not use param '" + param + "'");
                }
                builder.filterInputsBy((Predicate<String>)param);
            }
        }
        if (builder.getUrls().isEmpty()) {
            builder.addUrls(ClasspathHelper.forClassLoader(loaders));
        }
        return builder;
    }
    
    public ConfigurationBuilder forPackage(final String pkg, final ClassLoader... classLoaders) {
        return this.addUrls(ClasspathHelper.forPackage(pkg, classLoaders));
    }
    
    public ConfigurationBuilder forPackages(final String... packages) {
        for (final String pkg : packages) {
            this.forPackage(pkg, new ClassLoader[0]);
        }
        return this;
    }
    
    @Override
    public Set<Scanner> getScanners() {
        return (this.scanners != null) ? this.scanners : ConfigurationBuilder.DEFAULT_SCANNERS;
    }
    
    public ConfigurationBuilder setScanners(final Scanner... scanners) {
        this.scanners = new HashSet<Scanner>(Arrays.asList(scanners));
        return this;
    }
    
    public ConfigurationBuilder addScanners(final Scanner... scanners) {
        if (this.scanners == null) {
            this.setScanners(scanners);
        }
        else {
            this.scanners.addAll(Arrays.asList(scanners));
        }
        return this;
    }
    
    @Override
    public Set<URL> getUrls() {
        return this.urls;
    }
    
    public ConfigurationBuilder setUrls(final Collection<URL> urls) {
        this.urls = new HashSet<URL>(urls);
        return this;
    }
    
    public ConfigurationBuilder setUrls(final URL... urls) {
        return this.setUrls(Arrays.asList(urls));
    }
    
    public ConfigurationBuilder addUrls(final Collection<URL> urls) {
        this.urls.addAll(urls);
        return this;
    }
    
    public ConfigurationBuilder addUrls(final URL... urls) {
        return this.addUrls(Arrays.asList(urls));
    }
    
    @Override
    public Predicate<String> getInputsFilter() {
        return (this.inputsFilter != null) ? this.inputsFilter : ConfigurationBuilder.DEFAULT_INPUTS_FILTER;
    }
    
    public ConfigurationBuilder setInputsFilter(final Predicate<String> inputsFilter) {
        this.inputsFilter = inputsFilter;
        return this;
    }
    
    public ConfigurationBuilder filterInputsBy(final Predicate<String> inputsFilter) {
        return this.setInputsFilter(inputsFilter);
    }
    
    @Override
    public boolean isParallel() {
        return this.isParallel;
    }
    
    public ConfigurationBuilder setParallel(final boolean parallel) {
        this.isParallel = parallel;
        return this;
    }
    
    @Override
    public ClassLoader[] getClassLoaders() {
        return this.classLoaders;
    }
    
    public ConfigurationBuilder setClassLoaders(final ClassLoader[] classLoaders) {
        this.classLoaders = classLoaders;
        return this;
    }
    
    public ConfigurationBuilder addClassLoaders(final ClassLoader... classLoaders) {
        this.classLoaders = ((this.classLoaders == null) ? classLoaders : Stream.concat((Stream<?>)Arrays.stream((T[])this.classLoaders), (Stream<?>)Arrays.stream((T[])classLoaders)).distinct().toArray(ClassLoader[]::new));
        return this;
    }
    
    @Override
    public boolean shouldExpandSuperTypes() {
        return this.expandSuperTypes;
    }
    
    public ConfigurationBuilder setExpandSuperTypes(final boolean expandSuperTypes) {
        this.expandSuperTypes = expandSuperTypes;
        return this;
    }
    
    static {
        DEFAULT_SCANNERS = new HashSet<Scanner>(Arrays.asList(Scanners.TypesAnnotated, Scanners.SubTypes));
        DEFAULT_INPUTS_FILTER = (t -> true);
    }
}
