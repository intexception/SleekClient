package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

abstract class AbstractBinaryTag implements BinaryTag
{
    @NotNull
    @Override
    public final String examinableName() {
        return this.type().toString();
    }
    
    @Override
    public final String toString() {
        return this.examine((Examiner<String>)StringExaminer.simpleEscaping());
    }
}
