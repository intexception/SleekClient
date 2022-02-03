package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.security.*;
import java.nio.charset.*;
import java.util.*;
import java.io.*;
import java.net.*;

public final class UTF8ResourceBundleControl extends ResourceBundle.Control
{
    private static final UTF8ResourceBundleControl INSTANCE;
    
    public static ResourceBundle.Control get() {
        return UTF8ResourceBundleControl.INSTANCE;
    }
    
    @Override
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        if (format.equals("java.properties")) {
            final String bundle = this.toBundleName(baseName, locale);
            final String resource = this.toResourceName(bundle, "properties");
            InputStream is;
            try {
                final String s;
                URL url;
                URLConnection connection;
                is = AccessController.doPrivileged(() -> {
                    if (reload) {
                        url = loader.getResource(s);
                        if (url != null) {
                            connection = url.openConnection();
                            if (connection != null) {
                                connection.setUseCaches(false);
                                return connection.getInputStream();
                            }
                        }
                        return null;
                    }
                    else {
                        return loader.getResourceAsStream(s);
                    }
                });
            }
            catch (PrivilegedActionException e) {
                throw (IOException)e.getException();
            }
            if (is != null) {
                final InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                try {
                    final PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(isr);
                    isr.close();
                    return propertyResourceBundle;
                }
                catch (Throwable t) {
                    try {
                        isr.close();
                    }
                    catch (Throwable t2) {
                        t.addSuppressed(t2);
                    }
                    throw t;
                }
            }
            return null;
        }
        return super.newBundle(baseName, locale, format, loader, reload);
    }
    
    static {
        INSTANCE = new UTF8ResourceBundleControl();
    }
}
