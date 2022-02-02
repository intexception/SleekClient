package org.reflections.vfs;

import org.reflections.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import java.util.jar.*;
import java.net.*;
import org.reflections.util.*;
import java.io.*;

public abstract class Vfs
{
    private static List<UrlType> defaultUrlTypes;
    
    public static List<UrlType> getDefaultUrlTypes() {
        return Vfs.defaultUrlTypes;
    }
    
    public static void setDefaultURLTypes(final List<UrlType> urlTypes) {
        Vfs.defaultUrlTypes = urlTypes;
    }
    
    public static void addDefaultURLTypes(final UrlType urlType) {
        Vfs.defaultUrlTypes.add(0, urlType);
    }
    
    public static Dir fromURL(final URL url) {
        return fromURL(url, Vfs.defaultUrlTypes);
    }
    
    public static Dir fromURL(final URL url, final List<UrlType> urlTypes) {
        for (final UrlType type : urlTypes) {
            try {
                if (!type.matches(url)) {
                    continue;
                }
                final Dir dir = type.createDir(url);
                if (dir != null) {
                    return dir;
                }
                continue;
            }
            catch (Throwable e) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.warn("could not create Dir using " + type + " from url " + url.toExternalForm() + ". skipping.", e);
            }
        }
        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\neither use fromURL(final URL url, final List<UrlType> urlTypes) or use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) with your specialized UrlType.");
    }
    
    public static Dir fromURL(final URL url, final UrlType... urlTypes) {
        return fromURL(url, Arrays.asList(urlTypes));
    }
    
    public static Iterable<File> findFiles(final Collection<URL> inUrls, final String packagePrefix, final Predicate<String> nameFilter) {
        final String path;
        String filename;
        final Predicate<File> fileNamePredicate = file -> {
            path = file.getRelativePath();
            if (path.startsWith(packagePrefix)) {
                filename = path.substring(path.indexOf(packagePrefix) + packagePrefix.length());
                return !filename.isEmpty() && nameFilter.test(filename.substring(1));
            }
            else {
                return false;
            }
        };
        return findFiles(inUrls, fileNamePredicate);
    }
    
    public static Iterable<File> findFiles(final Collection<URL> urls, final Predicate<File> filePredicate) {
        return () -> urls.stream().flatMap(url -> {
            try {
                return StreamSupport.stream(fromURL(url).getFiles().spliterator(), false);
            }
            catch (Throwable e) {
                if (Reflections.log != null) {
                    Reflections.log.error("could not findFiles for url. continuing. [" + url + "]", e);
                }
                return Stream.of(new File[0]);
            }
        }).filter((Predicate<? super Object>)filePredicate).iterator();
    }
    
    public static java.io.File getFile(final URL url) {
        try {
            final String path = url.toURI().getSchemeSpecificPart();
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (URISyntaxException ex) {}
        try {
            String path = URLDecoder.decode(url.getPath(), "UTF-8");
            if (path.contains(".jar!")) {
                path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
            }
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (UnsupportedEncodingException ex2) {}
        try {
            String path = url.toExternalForm();
            if (path.startsWith("jar:")) {
                path = path.substring("jar:".length());
            }
            if (path.startsWith("wsjar:")) {
                path = path.substring("wsjar:".length());
            }
            if (path.startsWith("file:")) {
                path = path.substring("file:".length());
            }
            if (path.contains(".jar!")) {
                path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
            }
            if (path.contains(".war!")) {
                path = path.substring(0, path.indexOf(".war!") + ".war".length());
            }
            java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
            path = path.replace("%20", " ");
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (Exception ex3) {}
        return null;
    }
    
    private static boolean hasJarFileInPath(final URL url) {
        return url.toExternalForm().matches(".*\\.jar(!.*|$)");
    }
    
    private static boolean hasInnerJarFileInPath(final URL url) {
        return url.toExternalForm().matches(".+\\.jar!/.+");
    }
    
    static {
        Vfs.defaultUrlTypes = new ArrayList<UrlType>(Arrays.asList(DefaultUrlTypes.values()));
    }
    
    public interface Dir
    {
        String getPath();
        
        Iterable<File> getFiles();
        
        default void close() {
        }
    }
    
    public enum DefaultUrlTypes implements UrlType
    {
        jarFile {
            @Override
            public boolean matches(final URL url) {
                return url.getProtocol().equals("file") && hasJarFileInPath(url);
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new ZipDir(new JarFile(Vfs.getFile(url)));
            }
        }, 
        jarUrl {
            @Override
            public boolean matches(final URL url) {
                return ("jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol())) && !hasInnerJarFileInPath(url);
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                try {
                    final URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        urlConnection.setUseCaches(false);
                        return new ZipDir(((JarURLConnection)urlConnection).getJarFile());
                    }
                }
                catch (Throwable t) {}
                final java.io.File file = Vfs.getFile(url);
                if (file != null) {
                    return new ZipDir(new JarFile(file));
                }
                return null;
            }
        }, 
        directory {
            @Override
            public boolean matches(final URL url) {
                if (url.getProtocol().equals("file") && !hasJarFileInPath(url)) {
                    final java.io.File file = Vfs.getFile(url);
                    return file != null && file.isDirectory();
                }
                return false;
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new SystemDir(Vfs.getFile(url));
            }
        }, 
        jboss_vfs {
            @Override
            public boolean matches(final URL url) {
                return url.getProtocol().equals("vfs");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return JbossDir.createDir(url);
            }
        }, 
        jboss_vfsfile {
            @Override
            public boolean matches(final URL url) throws Exception {
                return "vfszip".equals(url.getProtocol()) || "vfsfile".equals(url.getProtocol());
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new UrlTypeVFS().createDir(url);
            }
        }, 
        bundle {
            @Override
            public boolean matches(final URL url) throws Exception {
                return url.getProtocol().startsWith("bundle");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, url));
            }
        }, 
        jarInputStream {
            @Override
            public boolean matches(final URL url) throws Exception {
                return url.toExternalForm().contains(".jar");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new JarInputDir(url);
            }
        };
    }
    
    public interface UrlType
    {
        boolean matches(final URL p0) throws Exception;
        
        Dir createDir(final URL p0) throws Exception;
    }
    
    public interface File
    {
        String getName();
        
        String getRelativePath();
        
        InputStream openInputStream() throws IOException;
    }
}
