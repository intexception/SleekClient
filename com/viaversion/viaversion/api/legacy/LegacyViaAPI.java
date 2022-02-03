package com.viaversion.viaversion.api.legacy;

import com.viaversion.viaversion.api.legacy.bossbar.*;

public interface LegacyViaAPI<T>
{
    BossBar createLegacyBossBar(final String p0, final float p1, final BossColor p2, final BossStyle p3);
    
    default BossBar createLegacyBossBar(final String title, final BossColor color, final BossStyle style) {
        return this.createLegacyBossBar(title, 1.0f, color, style);
    }
}
