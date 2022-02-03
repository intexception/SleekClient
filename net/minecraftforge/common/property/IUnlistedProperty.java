package net.minecraftforge.common.property;

public interface IUnlistedProperty<V>
{
    String getName();
    
    boolean isValid(final V p0);
    
    Class<V> getType();
    
    String valueToString(final V p0);
}
