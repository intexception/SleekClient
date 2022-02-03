package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class StreamEndEvent extends Event
{
    public StreamEndEvent(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    @Override
    public ID getEventId() {
        return ID.StreamEnd;
    }
}
