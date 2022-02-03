package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.*;

public interface Rewriter<T extends Protocol>
{
    void register();
    
    T protocol();
}
