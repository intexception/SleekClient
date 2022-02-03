package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;
import java.lang.reflect.*;

public abstract class Tag implements Cloneable
{
    public abstract Object getValue();
    
    public abstract void read(final DataInput p0) throws IOException;
    
    public abstract void write(final DataOutput p0) throws IOException;
    
    public abstract int getTagId();
    
    public abstract Tag clone();
    
    @Override
    public String toString() {
        String value = "";
        if (this.getValue() != null) {
            value = this.getValue().toString();
            if (this.getValue().getClass().isArray()) {
                final StringBuilder build = new StringBuilder();
                build.append("[");
                for (int index = 0; index < Array.getLength(this.getValue()); ++index) {
                    if (index > 0) {
                        build.append(", ");
                    }
                    build.append(Array.get(this.getValue(), index));
                }
                build.append("]");
                value = build.toString();
            }
        }
        return this.getClass().getSimpleName() + " { " + value + " }";
    }
}
