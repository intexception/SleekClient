package org.reflections.vfs;

import java.net.*;
import java.util.jar.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;
import org.reflections.*;

public class JarInputDir implements Vfs.Dir
{
    private final URL url;
    JarInputStream jarInputStream;
    long cursor;
    long nextCursor;
    
    public JarInputDir(final URL url) {
        this.cursor = 0L;
        this.nextCursor = 0L;
        this.url = url;
    }
    
    @Override
    public String getPath() {
        return this.url.getPath();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return () -> new Iterator<Vfs.File>() {
            Vfs.File entry;
            
            {
                try {
                    JarInputDir.this.jarInputStream = new JarInputStream(JarInputDir.this.url.openConnection().getInputStream());
                }
                catch (Exception e) {
                    throw new ReflectionsException("Could not open url connection", e);
                }
                this.entry = null;
            }
            
            @Override
            public boolean hasNext() {
                return this.entry != null || (this.entry = this.computeNext()) != null;
            }
            
            @Override
            public Vfs.File next() {
                final Vfs.File next = this.entry;
                this.entry = null;
                return next;
            }
            
            private Vfs.File computeNext() {
                try {
                    while (true) {
                        final ZipEntry entry = JarInputDir.this.jarInputStream.getNextJarEntry();
                        if (entry == null) {
                            return null;
                        }
                        long size = entry.getSize();
                        if (size < 0L) {
                            size += 4294967295L;
                        }
                        final JarInputDir this$0 = JarInputDir.this;
                        this$0.nextCursor += size;
                        if (!entry.isDirectory()) {
                            return new JarInputFile(entry, JarInputDir.this, JarInputDir.this.cursor, JarInputDir.this.nextCursor);
                        }
                    }
                }
                catch (IOException e) {
                    throw new ReflectionsException("could not get next zip entry", e);
                }
            }
        };
    }
    
    @Override
    public void close() {
        try {
            if (this.jarInputStream != null) {
                this.jarInputStream.close();
            }
        }
        catch (IOException e) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close InputStream", e);
            }
        }
    }
}
