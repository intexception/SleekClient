package com.viaversion.viaversion.libs.kyori.adventure.translation;

import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.text.*;
import org.jetbrains.annotations.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import java.util.function.*;
import java.util.*;

public interface TranslationRegistry extends Translator
{
    public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");
    
    @NotNull
    default TranslationRegistry create(final Key name) {
        return new TranslationRegistryImpl(Objects.requireNonNull(name, "name"));
    }
    
    boolean contains(@NotNull final String key);
    
    @Nullable
    MessageFormat translate(@NotNull final String key, @NotNull final Locale locale);
    
    void defaultLocale(@NotNull final Locale locale);
    
    void register(@NotNull final String key, @NotNull final Locale locale, @NotNull final MessageFormat format);
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Map<String, MessageFormat> formats) {
        final Set<String> keySet = formats.keySet();
        Objects.requireNonNull(formats);
        this.registerAll(locale, keySet, formats::get);
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Path path, final boolean escapeSingleQuotes) {
        try {
            final BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            try {
                this.registerAll(locale, new PropertyResourceBundle(reader), escapeSingleQuotes);
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Throwable t) {
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (Throwable t2) {
                        t.addSuppressed(t2);
                    }
                }
                throw t;
            }
        }
        catch (IOException ex) {}
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final ResourceBundle bundle, final boolean escapeSingleQuotes) {
        final String format;
        final MessageFormat messageFormat;
        this.registerAll(locale, bundle.keySet(), key -> {
            format = bundle.getString(key);
            new MessageFormat(escapeSingleQuotes ? TranslationRegistry.SINGLE_QUOTE_PATTERN.matcher(format).replaceAll("''") : format, locale);
            return messageFormat;
        });
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Set<String> keys, final Function<String, MessageFormat> function) {
        List<IllegalArgumentException> errors = null;
        for (final String key : keys) {
            try {
                this.register(key, locale, function.apply(key));
            }
            catch (IllegalArgumentException e) {
                if (errors == null) {
                    errors = new LinkedList<IllegalArgumentException>();
                }
                errors.add(e);
            }
        }
        if (errors != null) {
            final int size = errors.size();
            if (size == 1) {
                throw errors.get(0);
            }
            if (size > 1) {
                throw new IllegalArgumentException(String.format("Invalid key (and %d more)", size - 1), errors.get(0));
            }
        }
    }
    
    void unregister(@NotNull final String key);
}
