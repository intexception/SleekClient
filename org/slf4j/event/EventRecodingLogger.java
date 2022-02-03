package org.slf4j.event;

import java.util.*;
import org.slf4j.*;
import org.slf4j.helpers.*;

public class EventRecodingLogger implements Logger
{
    String name;
    SubstituteLogger logger;
    Queue<SubstituteLoggingEvent> eventQueue;
    static final boolean RECORD_ALL_EVENTS = true;
    
    public EventRecodingLogger(final SubstituteLogger logger, final Queue<SubstituteLoggingEvent> eventQueue) {
        this.logger = logger;
        this.name = logger.getName();
        this.eventQueue = eventQueue;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isTraceEnabled() {
        return true;
    }
    
    public void trace(final String msg) {
        this.recordEvent_0Args(Level.TRACE, null, msg, null);
    }
    
    public void trace(final String format, final Object arg) {
        this.recordEvent_1Args(Level.TRACE, null, format, arg);
    }
    
    public void trace(final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.TRACE, null, format, arg1, arg2);
    }
    
    public void trace(final String format, final Object... arguments) {
        this.recordEventArgArray(Level.TRACE, null, format, arguments);
    }
    
    public void trace(final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.TRACE, null, msg, t);
    }
    
    public boolean isTraceEnabled(final Marker marker) {
        return true;
    }
    
    public void trace(final Marker marker, final String msg) {
        this.recordEvent_0Args(Level.TRACE, marker, msg, null);
    }
    
    public void trace(final Marker marker, final String format, final Object arg) {
        this.recordEvent_1Args(Level.TRACE, marker, format, arg);
    }
    
    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.TRACE, marker, format, arg1, arg2);
    }
    
    public void trace(final Marker marker, final String format, final Object... argArray) {
        this.recordEventArgArray(Level.TRACE, marker, format, argArray);
    }
    
    public void trace(final Marker marker, final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.TRACE, marker, msg, t);
    }
    
    public boolean isDebugEnabled() {
        return true;
    }
    
    public void debug(final String msg) {
        this.recordEvent_0Args(Level.DEBUG, null, msg, null);
    }
    
    public void debug(final String format, final Object arg) {
        this.recordEvent_1Args(Level.DEBUG, null, format, arg);
    }
    
    public void debug(final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.DEBUG, null, format, arg1, arg2);
    }
    
    public void debug(final String format, final Object... arguments) {
        this.recordEventArgArray(Level.DEBUG, null, format, arguments);
    }
    
    public void debug(final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.DEBUG, null, msg, t);
    }
    
    public boolean isDebugEnabled(final Marker marker) {
        return true;
    }
    
    public void debug(final Marker marker, final String msg) {
        this.recordEvent_0Args(Level.DEBUG, marker, msg, null);
    }
    
    public void debug(final Marker marker, final String format, final Object arg) {
        this.recordEvent_1Args(Level.DEBUG, marker, format, arg);
    }
    
    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.DEBUG, marker, format, arg1, arg2);
    }
    
    public void debug(final Marker marker, final String format, final Object... arguments) {
        this.recordEventArgArray(Level.DEBUG, marker, format, arguments);
    }
    
    public void debug(final Marker marker, final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.DEBUG, marker, msg, t);
    }
    
    public boolean isInfoEnabled() {
        return true;
    }
    
    public void info(final String msg) {
        this.recordEvent_0Args(Level.INFO, null, msg, null);
    }
    
    public void info(final String format, final Object arg) {
        this.recordEvent_1Args(Level.INFO, null, format, arg);
    }
    
    public void info(final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.INFO, null, format, arg1, arg2);
    }
    
    public void info(final String format, final Object... arguments) {
        this.recordEventArgArray(Level.INFO, null, format, arguments);
    }
    
    public void info(final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.INFO, null, msg, t);
    }
    
    public boolean isInfoEnabled(final Marker marker) {
        return true;
    }
    
    public void info(final Marker marker, final String msg) {
        this.recordEvent_0Args(Level.INFO, marker, msg, null);
    }
    
    public void info(final Marker marker, final String format, final Object arg) {
        this.recordEvent_1Args(Level.INFO, marker, format, arg);
    }
    
    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.INFO, marker, format, arg1, arg2);
    }
    
    public void info(final Marker marker, final String format, final Object... arguments) {
        this.recordEventArgArray(Level.INFO, marker, format, arguments);
    }
    
    public void info(final Marker marker, final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.INFO, marker, msg, t);
    }
    
    public boolean isWarnEnabled() {
        return true;
    }
    
    public void warn(final String msg) {
        this.recordEvent_0Args(Level.WARN, null, msg, null);
    }
    
    public void warn(final String format, final Object arg) {
        this.recordEvent_1Args(Level.WARN, null, format, arg);
    }
    
    public void warn(final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.WARN, null, format, arg1, arg2);
    }
    
    public void warn(final String format, final Object... arguments) {
        this.recordEventArgArray(Level.WARN, null, format, arguments);
    }
    
    public void warn(final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.WARN, null, msg, t);
    }
    
    public boolean isWarnEnabled(final Marker marker) {
        return true;
    }
    
    public void warn(final Marker marker, final String msg) {
        this.recordEvent_0Args(Level.WARN, marker, msg, null);
    }
    
    public void warn(final Marker marker, final String format, final Object arg) {
        this.recordEvent_1Args(Level.WARN, marker, format, arg);
    }
    
    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.WARN, marker, format, arg1, arg2);
    }
    
    public void warn(final Marker marker, final String format, final Object... arguments) {
        this.recordEventArgArray(Level.WARN, marker, format, arguments);
    }
    
    public void warn(final Marker marker, final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.WARN, marker, msg, t);
    }
    
    public boolean isErrorEnabled() {
        return true;
    }
    
    public void error(final String msg) {
        this.recordEvent_0Args(Level.ERROR, null, msg, null);
    }
    
    public void error(final String format, final Object arg) {
        this.recordEvent_1Args(Level.ERROR, null, format, arg);
    }
    
    public void error(final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.ERROR, null, format, arg1, arg2);
    }
    
    public void error(final String format, final Object... arguments) {
        this.recordEventArgArray(Level.ERROR, null, format, arguments);
    }
    
    public void error(final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.ERROR, null, msg, t);
    }
    
    public boolean isErrorEnabled(final Marker marker) {
        return true;
    }
    
    public void error(final Marker marker, final String msg) {
        this.recordEvent_0Args(Level.ERROR, marker, msg, null);
    }
    
    public void error(final Marker marker, final String format, final Object arg) {
        this.recordEvent_1Args(Level.ERROR, marker, format, arg);
    }
    
    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        this.recordEvent2Args(Level.ERROR, marker, format, arg1, arg2);
    }
    
    public void error(final Marker marker, final String format, final Object... arguments) {
        this.recordEventArgArray(Level.ERROR, marker, format, arguments);
    }
    
    public void error(final Marker marker, final String msg, final Throwable t) {
        this.recordEvent_0Args(Level.ERROR, marker, msg, t);
    }
    
    private void recordEvent_0Args(final Level level, final Marker marker, final String msg, final Throwable t) {
        this.recordEvent(level, marker, msg, null, t);
    }
    
    private void recordEvent_1Args(final Level level, final Marker marker, final String msg, final Object arg1) {
        this.recordEvent(level, marker, msg, new Object[] { arg1 }, null);
    }
    
    private void recordEvent2Args(final Level level, final Marker marker, final String msg, final Object arg1, final Object arg2) {
        if (arg2 instanceof Throwable) {
            this.recordEvent(level, marker, msg, new Object[] { arg1 }, (Throwable)arg2);
        }
        else {
            this.recordEvent(level, marker, msg, new Object[] { arg1, arg2 }, null);
        }
    }
    
    private void recordEventArgArray(final Level level, final Marker marker, final String msg, final Object[] args) {
        final Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(args);
        if (throwableCandidate != null) {
            final Object[] trimmedCopy = MessageFormatter.trimmedCopy(args);
            this.recordEvent(level, marker, msg, trimmedCopy, throwableCandidate);
        }
        else {
            this.recordEvent(level, marker, msg, args, null);
        }
    }
    
    private void recordEvent(final Level level, final Marker marker, final String msg, final Object[] args, final Throwable throwable) {
        final SubstituteLoggingEvent loggingEvent = new SubstituteLoggingEvent();
        loggingEvent.setTimeStamp(System.currentTimeMillis());
        loggingEvent.setLevel(level);
        loggingEvent.setLogger(this.logger);
        loggingEvent.setLoggerName(this.name);
        loggingEvent.setMarker(marker);
        loggingEvent.setMessage(msg);
        loggingEvent.setThreadName(Thread.currentThread().getName());
        loggingEvent.setArgumentArray(args);
        loggingEvent.setThrowable(throwable);
        this.eventQueue.add(loggingEvent);
    }
}
