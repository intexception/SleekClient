package me.kansio.client.modules.api;

public enum ModuleCategory
{
    MOVEMENT("Movement"), 
    EXPLOIT("Exploit"), 
    HIDDEN("Hidden"), 
    PLAYER("Player"), 
    COMBAT("Combat");
    
    public /* synthetic */ String name;
    
    WORLD("World"), 
    VISUALS("Visuals");
    
    private ModuleCategory(final String llllllllllllllllllllIIlllIlIlIII) {
        this.name = llllllllllllllllllllIIlllIlIlIII;
    }
    
    public String getName() {
        return this.name;
    }
}
