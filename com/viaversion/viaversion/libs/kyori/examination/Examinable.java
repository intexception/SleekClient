package com.viaversion.viaversion.libs.kyori.examination;

import org.jetbrains.annotations.*;
import java.util.stream.*;

public interface Examinable
{
    @NotNull
    default String examinableName() {
        return this.getClass().getSimpleName();
    }
    
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.empty();
    }
    
    @NotNull
    default <R> R examine(@NotNull final Examiner<R> examiner) {
        return examiner.examine(this);
    }
}
