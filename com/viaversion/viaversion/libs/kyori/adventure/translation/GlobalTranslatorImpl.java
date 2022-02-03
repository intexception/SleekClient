package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.*;
import java.util.concurrent.*;
import java.text.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class GlobalTranslatorImpl implements GlobalTranslator
{
    private static final Key NAME;
    static final GlobalTranslatorImpl INSTANCE;
    final TranslatableComponentRenderer<Locale> renderer;
    private final Set<Translator> sources;
    
    private GlobalTranslatorImpl() {
        this.renderer = TranslatableComponentRenderer.usingTranslationSource(this);
        this.sources = Collections.newSetFromMap(new ConcurrentHashMap<Translator, Boolean>());
    }
    
    @NotNull
    @Override
    public Key name() {
        return GlobalTranslatorImpl.NAME;
    }
    
    @NotNull
    @Override
    public Iterable<? extends Translator> sources() {
        return (Iterable<? extends Translator>)Collections.unmodifiableSet((Set<?>)this.sources);
    }
    
    @Override
    public boolean addSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        if (source == this) {
            throw new IllegalArgumentException("GlobalTranslationSource");
        }
        return this.sources.add(source);
    }
    
    @Override
    public boolean removeSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        return this.sources.remove(source);
    }
    
    @Nullable
    @Override
    public MessageFormat translate(@NotNull final String key, @NotNull final Locale locale) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(locale, "locale");
        for (final Translator source : this.sources) {
            final MessageFormat translation = source.translate(key, locale);
            if (translation != null) {
                return translation;
            }
        }
        return null;
    }
    
    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("sources", this.sources));
    }
    
    static {
        NAME = Key.key("adventure", "global");
        INSTANCE = new GlobalTranslatorImpl();
    }
}
