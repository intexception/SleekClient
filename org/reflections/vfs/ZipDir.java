package org.reflections.vfs;

import java.util.jar.*;
import org.reflections.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ZipDir implements Vfs.Dir
{
    final ZipFile jarFile;
    
    public ZipDir(final JarFile jarFile) {
        this.jarFile = jarFile;
    }
    
    @Override
    public String getPath() {
        return (this.jarFile != null) ? this.jarFile.getName().replace("\\", "/") : "/NO-SUCH-DIRECTORY/";
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return () -> this.jarFile.stream().filter(entry -> !entry.isDirectory()).map(entry -> new org.reflections.vfs.ZipFile(this, entry)).iterator();
    }
    
    @Override
    public void close() {
        try {
            this.jarFile.close();
        }
        catch (IOException e) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close JarFile", e);
            }
        }
    }
    
    @Override
    public String toString() {
        return this.jarFile.getName();
    }
}
