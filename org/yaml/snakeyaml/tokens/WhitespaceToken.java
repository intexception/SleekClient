package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public class WhitespaceToken extends Token
{
    public WhitespaceToken(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    @Override
    public ID getTokenId() {
        return ID.Whitespace;
    }
}
