package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.*;

final class Services0
{
    private Services0() {
    }
    
    static <S> ServiceLoader<S> loader(final Class<S> type) {
        return ServiceLoader.load(type, type.getClassLoader());
    }
}
