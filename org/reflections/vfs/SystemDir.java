package org.reflections.vfs;

import org.reflections.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class SystemDir implements Vfs.Dir
{
    private final java.io.File file;
    
    public SystemDir(final java.io.File file) {
        if (file != null && (!file.isDirectory() || !file.canRead())) {
            throw new RuntimeException("cannot use dir " + file);
        }
        this.file = file;
    }
    
    @Override
    public String getPath() {
        return (this.file != null) ? this.file.getPath().replace("\\", "/") : "/NO-SUCH-DIRECTORY/";
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        if (this.file == null || !this.file.exists()) {
            return (Iterable<Vfs.File>)Collections.emptyList();
        }
        final ReflectionsException ex;
        return () -> {
            try {
                return (Iterator<Vfs.File>)Files.walk(this.file.toPath(), new FileVisitOption[0]).filter(x$0 -> Files.isRegularFile(x$0, new LinkOption[0])).map(path -> new SystemFile(this, path.toFile())).iterator();
            }
            catch (IOException e) {
                new ReflectionsException("could not get files for " + this.file, e);
                throw ex;
            }
        };
    }
}
