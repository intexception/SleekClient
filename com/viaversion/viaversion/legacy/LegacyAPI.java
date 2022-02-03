package com.viaversion.viaversion.legacy;

import com.viaversion.viaversion.api.legacy.*;
import com.viaversion.viaversion.api.legacy.bossbar.*;
import com.viaversion.viaversion.legacy.bossbar.*;

public final class LegacyAPI<T> implements LegacyViaAPI<T>
{
    @Override
    public BossBar createLegacyBossBar(final String title, final float health, final BossColor color, final BossStyle style) {
        return new CommonBoss(title, health, color, style);
    }
}
