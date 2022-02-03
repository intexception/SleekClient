package com.viaversion.viaversion.api.data;

import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.util.*;

public interface MappingData
{
    void load();
    
    int getNewBlockStateId(final int p0);
    
    int getNewBlockId(final int p0);
    
    int getNewItemId(final int p0);
    
    int getOldItemId(final int p0);
    
    int getNewParticleId(final int p0);
    
    List<TagData> getTags(final RegistryType p0);
    
    Int2IntBiMap getItemMappings();
    
    ParticleMappings getParticleMappings();
    
    Mappings getBlockMappings();
    
    Mappings getBlockEntityMappings();
    
    Mappings getBlockStateMappings();
    
    Mappings getSoundMappings();
    
    Mappings getStatisticsMappings();
}
