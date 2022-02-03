package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.util.*;

public final class JsonObject extends JsonElement
{
    private final LinkedTreeMap<String, JsonElement> members;
    
    public JsonObject() {
        this.members = new LinkedTreeMap<String, JsonElement>();
    }
    
    @Override
    public JsonObject deepCopy() {
        final JsonObject result = new JsonObject();
        for (final Map.Entry<String, JsonElement> entry : this.members.entrySet()) {
            result.add(entry.getKey(), entry.getValue().deepCopy());
        }
        return result;
    }
    
    public void add(final String property, final JsonElement value) {
        this.members.put(property, (value == null) ? JsonNull.INSTANCE : value);
    }
    
    public JsonElement remove(final String property) {
        return this.members.remove(property);
    }
    
    public void addProperty(final String property, final String value) {
        this.add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }
    
    public void addProperty(final String property, final Number value) {
        this.add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }
    
    public void addProperty(final String property, final Boolean value) {
        this.add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }
    
    public void addProperty(final String property, final Character value) {
        this.add(property, (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }
    
    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }
    
    public Set<String> keySet() {
        return this.members.keySet();
    }
    
    public int size() {
        return this.members.size();
    }
    
    public boolean has(final String memberName) {
        return this.members.containsKey(memberName);
    }
    
    public JsonElement get(final String memberName) {
        return this.members.get(memberName);
    }
    
    public JsonPrimitive getAsJsonPrimitive(final String memberName) {
        return this.members.get(memberName);
    }
    
    public JsonArray getAsJsonArray(final String memberName) {
        return this.members.get(memberName);
    }
    
    public JsonObject getAsJsonObject(final String memberName) {
        return this.members.get(memberName);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof JsonObject && ((JsonObject)o).members.equals(this.members));
    }
    
    @Override
    public int hashCode() {
        return this.members.hashCode();
    }
}
