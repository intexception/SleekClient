package io.netty.util.internal.logging;

import org.apache.commons.logging.*;

class CommonsLogger extends AbstractInternalLogger
{
    private static final long serialVersionUID = 8647838678388394885L;
    private final transient Log logger;
    
    CommonsLogger(final Log logger, final String name) {
        super(name);
        if (logger == null) {
            throw new NullPointerException("logger");
        }
        this.logger = logger;
    }
    
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }
    
    @Override
    public void trace(final String msg) {
        this.logger.trace((Object)msg);
    }
    
    @Override
    public void trace(final String format, final Object arg) {
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, arg);
            this.logger.trace((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void trace(final String format, final Object argA, final Object argB) {
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            this.logger.trace((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void trace(final String format, final Object... arguments) {
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            this.logger.trace((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void trace(final String msg, final Throwable t) {
        this.logger.trace((Object)msg, t);
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }
    
    @Override
    public void debug(final String msg) {
        this.logger.debug((Object)msg);
    }
    
    @Override
    public void debug(final String format, final Object arg) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, arg);
            this.logger.debug((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void debug(final String format, final Object argA, final Object argB) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            this.logger.debug((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void debug(final String format, final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            this.logger.debug((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void debug(final String msg, final Throwable t) {
        this.logger.debug((Object)msg, t);
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }
    
    @Override
    public void info(final String msg) {
        this.logger.info((Object)msg);
    }
    
    @Override
    public void info(final String format, final Object arg) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, arg);
            this.logger.info((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void info(final String format, final Object argA, final Object argB) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            this.logger.info((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void info(final String format, final Object... arguments) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            this.logger.info((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void info(final String msg, final Throwable t) {
        this.logger.info((Object)msg, t);
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }
    
    @Override
    public void warn(final String msg) {
        this.logger.warn((Object)msg);
    }
    
    @Override
    public void warn(final String format, final Object arg) {
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, arg);
            this.logger.warn((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void warn(final String format, final Object argA, final Object argB) {
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            this.logger.warn((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void warn(final String format, final Object... arguments) {
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            this.logger.warn((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void warn(final String msg, final Throwable t) {
        this.logger.warn((Object)msg, t);
    }
    
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }
    
    @Override
    public void error(final String msg) {
        this.logger.error((Object)msg);
    }
    
    @Override
    public void error(final String format, final Object arg) {
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, arg);
            this.logger.error((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void error(final String format, final Object argA, final Object argB) {
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            this.logger.error((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void error(final String format, final Object... arguments) {
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            this.logger.error((Object)ft.getMessage(), ft.getThrowable());
        }
    }
    
    @Override
    public void error(final String msg, final Throwable t) {
        this.logger.error((Object)msg, t);
    }
}
