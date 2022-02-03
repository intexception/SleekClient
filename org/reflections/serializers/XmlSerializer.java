package org.reflections.serializers;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.reflections.*;
import org.dom4j.io.*;
import java.io.*;
import org.dom4j.*;

public class XmlSerializer implements Serializer
{
    @Override
    public Reflections read(final InputStream inputStream) {
        try {
            final Document document = new SAXReader().read(inputStream);
            final Map<String, Map<String, Set<String>>> storeMap = (Map<String, Map<String, Set<String>>>)document.getRootElement().elements().stream().collect(Collectors.toMap((Function<? super Object, ?>)Node::getName, index -> (Map)index.elements().stream().collect(Collectors.toMap(entry -> entry.element("key").getText(), entry -> (Set)entry.element("values").elements().stream().map(Element::getText).collect(Collectors.toSet())))));
            return new Reflections(new Store(storeMap));
        }
        catch (Exception e) {
            throw new ReflectionsException("could not read.", e);
        }
    }
    
    @Override
    public File save(final Reflections reflections, final String filename) {
        final File file = Serializer.prepareFile(filename);
        try (final FileOutputStream out = new FileOutputStream(file)) {
            new XMLWriter((OutputStream)out, OutputFormat.createPrettyPrint()).write(this.createDocument(reflections.getStore()));
        }
        catch (Exception e) {
            throw new ReflectionsException("could not save to file " + filename, e);
        }
        return file;
    }
    
    private Document createDocument(final Store store) {
        final Document document = DocumentFactory.getInstance().createDocument();
        final Element root = document.addElement("Reflections");
        final Element indexElement;
        final Element entryElement;
        final Element valuesElement;
        store.forEach((index, map) -> {
            indexElement = root.addElement(index);
            map.forEach((key, values) -> {
                entryElement = indexElement.addElement("entry");
                entryElement.addElement("key").setText(key);
                valuesElement = entryElement.addElement("values");
                values.forEach(value -> valuesElement.addElement("value").setText(value));
            });
            return;
        });
        return document;
    }
}
