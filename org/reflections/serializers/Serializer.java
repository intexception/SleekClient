package org.reflections.serializers;

import org.reflections.*;
import java.io.*;

public interface Serializer
{
    Reflections read(final InputStream p0);
    
    File save(final Reflections p0, final String p1);
    
    default File prepareFile(final String filename) {
        final File file = new File(filename);
        final File parent = file.getAbsoluteFile().getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return file;
    }
}
