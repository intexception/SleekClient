package de.gerrygames.viarewind.storage;

public class BlockState
{
    public static int extractId(final int raw) {
        return raw >> 4;
    }
    
    public static int extractData(final int raw) {
        return raw & 0xF;
    }
    
    public static int stateToRaw(final int id, final int data) {
        return id << 4 | (data & 0xF);
    }
}
