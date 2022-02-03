package org.reflections.vfs;

import org.jboss.vfs.*;
import java.net.*;
import java.util.jar.*;
import java.util.*;

public class JbossDir implements Vfs.Dir
{
    private final VirtualFile virtualFile;
    
    private JbossDir(final VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
    }
    
    public static Vfs.Dir createDir(final URL url) throws Exception {
        final VirtualFile virtualFile = (VirtualFile)url.openConnection().getContent();
        if (virtualFile.isFile()) {
            return new ZipDir(new JarFile(virtualFile.getPhysicalFile()));
        }
        return new JbossDir(virtualFile);
    }
    
    @Override
    public String getPath() {
        return this.virtualFile.getPathName();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return () -> new Iterator<Vfs.File>() {
            Vfs.File entry;
            final Stack stack;
            
            {
                this.stack = new Stack();
                this.entry = null;
                this.stack.addAll(JbossDir.this.virtualFile.getChildren());
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
                while (!this.stack.isEmpty()) {
                    final VirtualFile file = this.stack.pop();
                    if (!file.isDirectory()) {
                        return new JbossFile(JbossDir.this, file);
                    }
                    this.stack.addAll(file.getChildren());
                }
                return null;
            }
        };
    }
}
