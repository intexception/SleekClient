package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

@ApiStatus.NonExtendable
public interface Sound extends Examinable
{
    @NotNull
    default Sound sound(@NotNull final Key name, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(source, volume, pitch) {
            @NotNull
            @Override
            public Key name() {
                return name;
            }
        };
    }
    
    @NotNull
    default Sound sound(@NotNull final Type type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        return sound(type.key(), source, volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Supplier<? extends Type> type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(source, volume, pitch) {
            @NotNull
            @Override
            public Key name() {
                return type.get().key();
            }
        };
    }
    
    @NotNull
    default Sound sound(@NotNull final Key name, final Source.Provider source, final float volume, final float pitch) {
        return sound(name, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Type type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Supplier<? extends Type> type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    Key name();
    
    @NotNull
    Source source();
    
    float volume();
    
    float pitch();
    
    @NotNull
    SoundStop asStop();
    
    public enum Source
    {
        MASTER("master"), 
        MUSIC("music"), 
        RECORD("record"), 
        WEATHER("weather"), 
        BLOCK("block"), 
        HOSTILE("hostile"), 
        NEUTRAL("neutral"), 
        PLAYER("player"), 
        AMBIENT("ambient"), 
        VOICE("voice");
        
        public static final Index<String, Source> NAMES;
        private final String name;
        
        private Source(final String name) {
            this.name = name;
        }
        
        static {
            NAMES = Index.create(Source.class, source -> source.name);
        }
        
        public interface Provider
        {
            @NotNull
            Source soundSource();
        }
    }
    
    public interface Emitter
    {
        @NotNull
        default Emitter self() {
            return SoundImpl.EMITTER_SELF;
        }
    }
    
    public interface Type extends Keyed
    {
        @NotNull
        Key key();
    }
}
