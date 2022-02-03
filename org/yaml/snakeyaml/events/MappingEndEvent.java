package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class MappingEndEvent extends CollectionEndEvent
{
    public MappingEndEvent(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    @Override
    public ID getEventId() {
        return ID.MappingEnd;
    }
}
