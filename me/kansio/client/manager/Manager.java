package me.kansio.client.manager;

import java.util.*;

public abstract class Manager<T>
{
    private final /* synthetic */ List<T> list;
    
    protected Manager(final List<T> llllllllllllllllllllIIIIIlllIIIl) {
        this.list = llllllllllllllllllllIIIIIlllIIIl;
        this.onCreated();
    }
    
    public final void remove(final T llllllllllllllllllllIIIIIllIIlll) {
        this.list.remove(llllllllllllllllllllIIIIIllIIlll);
    }
    
    public final void add(final T llllllllllllllllllllIIIIIllIlIll) {
        this.list.add(llllllllllllllllllllIIIIIllIlIll);
    }
    
    public final T get(final int llllllllllllllllllllIIIIIllIIIIl) {
        return this.list.get(llllllllllllllllllllIIIIIllIIIIl);
    }
    
    public <E extends T> E get(final Class<? extends T> llllllllllllllllllllIIIIIlIllIll) {
        final NoSuchElementException ex;
        return (E)this.list.stream().filter(llllllllllllllllllllIIIIIlIIllII -> llllllllllllllllllllIIIIIlIIllII.getClass().equals(llllllllllllllllllllIIIIIlIllIll)).findFirst().orElseThrow(() -> {
            new NoSuchElementException(String.valueOf(new StringBuilder().append("RETARD ALERT: Element belonging to class '").append(llllllllllllllllllllIIIIIlIllIll.getName()).append("' not found")));
            return ex;
        });
    }
    
    public void onCreated() {
    }
    
    public List<T> getObjects() {
        return this.list;
    }
}
