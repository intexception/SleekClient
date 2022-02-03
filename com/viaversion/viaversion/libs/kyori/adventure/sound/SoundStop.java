package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public interface SoundStop extends Examinable
{
    @NotNull
    default SoundStop all() {
        return SoundStopImpl.ALL;
    }
    
    @NotNull
    default SoundStop named(@NotNull final Key sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) {
            @NotNull
            @Override
            public Key sound() {
                return sound;
            }
        };
    }
    
    @NotNull
    default SoundStop named(final Sound.Type sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) {
            @NotNull
            @Override
            public Key sound() {
                return sound.key();
            }
        };
    }
    
    @NotNull
    default SoundStop named(@NotNull final Supplier<? extends Sound.Type> sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) {
            @NotNull
            @Override
            public Key sound() {
                return sound.get().key();
            }
        };
    }
    
    @NotNull
    default SoundStop source(final Sound.Source source) {
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) {
            @Nullable
            @Override
            public Key sound() {
                return null;
            }
        };
    }
    
    @NotNull
    default SoundStop namedOnSource(@NotNull final Key sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) {
            @NotNull
            @Override
            public Key sound() {
                return sound;
            }
        };
    }
    
    @NotNull
    default SoundStop namedOnSource(final Sound.Type sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        return namedOnSource(sound.key(), source);
    }
    
    @NotNull
    default SoundStop namedOnSource(@NotNull final Supplier<? extends Sound.Type> sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) {
            @NotNull
            @Override
            public Key sound() {
                return sound.get().key();
            }
        };
    }
    
    @Nullable
    Key sound();
    
    Sound.Source source();
}
