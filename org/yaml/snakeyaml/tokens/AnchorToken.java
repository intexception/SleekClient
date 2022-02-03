package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public final class AnchorToken extends Token
{
    private final String value;
    
    public AnchorToken(final String value, final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Anchor;
    }
}
