package org.reflections.serializers;

import org.reflections.*;
import com.google.gson.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;

public class JsonSerializer implements Serializer
{
    @Override
    public Reflections read(final InputStream inputStream) {
        return (Reflections)new GsonBuilder().setPrettyPrinting().create().fromJson((Reader)new InputStreamReader(inputStream), (Class)Reflections.class);
    }
    
    @Override
    public File save(final Reflections reflections, final String filename) {
        try {
            final File file = Serializer.prepareFile(filename);
            final String json = new GsonBuilder().setPrettyPrinting().create().toJson((Object)reflections);
            Files.write(file.toPath(), json.getBytes(Charset.defaultCharset()), new OpenOption[0]);
            return file;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
