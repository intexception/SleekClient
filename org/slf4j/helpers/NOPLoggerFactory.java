package org.slf4j.helpers;

import org.slf4j.*;

public class NOPLoggerFactory implements ILoggerFactory
{
    public Logger getLogger(final String name) {
        return NOPLogger.NOP_LOGGER;
    }
}
