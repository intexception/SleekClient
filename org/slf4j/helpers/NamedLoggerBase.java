package org.slf4j.helpers;

import org.slf4j.*;
import java.io.*;

abstract class NamedLoggerBase implements Logger, Serializable
{
    private static final long serialVersionUID = 7535258609338176893L;
    protected String name;
    
    public String getName() {
        return this.name;
    }
    
    protected Object readResolve() throws ObjectStreamException {
        return LoggerFactory.getLogger(this.getName());
    }
}
