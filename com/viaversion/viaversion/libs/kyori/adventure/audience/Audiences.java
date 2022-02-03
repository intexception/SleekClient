package com.viaversion.viaversion.libs.kyori.adventure.audience;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

final class Audiences
{
    static final Collector<? super Audience, ?, ForwardingAudience> COLLECTOR;
    
    private Audiences() {
    }
    
    static {
        COLLECTOR = Collectors.collectingAndThen((Collector<? super Audience, ?, ArrayList>)Collectors.toCollection((Supplier<R>)ArrayList::new), audiences -> Audience.audience((Iterable<? extends Audience>)Collections.unmodifiableCollection((Collection<?>)audiences)));
    }
}
