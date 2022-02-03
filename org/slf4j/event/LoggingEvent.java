package org.slf4j.event;

import org.slf4j.*;

public interface LoggingEvent
{
    Level getLevel();
    
    Marker getMarker();
    
    String getLoggerName();
    
    String getMessage();
    
    String getThreadName();
    
    Object[] getArgumentArray();
    
    long getTimeStamp();
    
    Throwable getThrowable();
}
