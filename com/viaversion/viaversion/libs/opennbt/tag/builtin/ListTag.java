package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import org.jetbrains.annotations.*;
import com.google.common.base.*;
import com.viaversion.viaversion.libs.opennbt.tag.*;
import java.io.*;
import java.util.*;

public class ListTag extends Tag implements Iterable<Tag>
{
    public static final int ID = 9;
    private final List<Tag> value;
    private Class<? extends Tag> type;
    
    public ListTag() {
        this.value = new ArrayList<Tag>();
    }
    
    public ListTag(@Nullable final Class<? extends Tag> type) {
        this.type = type;
        this.value = new ArrayList<Tag>();
    }
    
    public ListTag(final List<Tag> value) throws IllegalArgumentException {
        this.value = new ArrayList<Tag>(value.size());
        this.setValue(value);
    }
    
    @Override
    public List<Tag> getValue() {
        return this.value;
    }
    
    public void setValue(final List<Tag> value) throws IllegalArgumentException {
        Preconditions.checkNotNull((Object)value);
        this.type = null;
        this.value.clear();
        for (final Tag tag : value) {
            this.add(tag);
        }
    }
    
    public Class<? extends Tag> getElementType() {
        return this.type;
    }
    
    public boolean add(final Tag tag) throws IllegalArgumentException {
        Preconditions.checkNotNull((Object)tag);
        if (this.type == null) {
            this.type = tag.getClass();
        }
        else if (tag.getClass() != this.type) {
            throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
        }
        return this.value.add(tag);
    }
    
    public boolean remove(final Tag tag) {
        return this.value.remove(tag);
    }
    
    public <T extends Tag> T get(final int index) {
        return (T)this.value.get(index);
    }
    
    public int size() {
        return this.value.size();
    }
    
    @Override
    public Iterator<Tag> iterator() {
        return this.value.iterator();
    }
    
    @Override
    public void read(final DataInput in) throws IOException {
        this.type = null;
        final int id = in.readByte();
        if (id != 0) {
            this.type = TagRegistry.getClassFor(id);
            if (this.type == null) {
                throw new IOException("Unknown tag ID in ListTag: " + id);
            }
        }
        for (int count = in.readInt(), index = 0; index < count; ++index) {
            Tag tag;
            try {
                tag = TagRegistry.createInstance(id);
            }
            catch (TagCreateException e) {
                throw new IOException("Failed to create tag.", e);
            }
            tag.read(in);
            this.add(tag);
        }
    }
    
    @Override
    public void write(final DataOutput out) throws IOException {
        if (this.type == null) {
            out.writeByte(0);
        }
        else {
            final int id = TagRegistry.getIdFor(this.type);
            if (id == -1) {
                throw new IOException("ListTag contains unregistered tag class.");
            }
            out.writeByte(id);
        }
        out.writeInt(this.value.size());
        for (final Tag tag : this.value) {
            tag.write(out);
        }
    }
    
    @Override
    public final ListTag clone() {
        final List<Tag> newList = new ArrayList<Tag>();
        for (final Tag value : this.value) {
            newList.add(value.clone());
        }
        return new ListTag(newList);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ListTag tags = (ListTag)o;
        return Objects.equals(this.type, tags.type) && this.value.equals(tags.value);
    }
    
    @Override
    public int hashCode() {
        int result = (this.type != null) ? this.type.hashCode() : 0;
        result = 31 * result + this.value.hashCode();
        return result;
    }
    
    @Override
    public int getTagId() {
        return 9;
    }
}
