package org.yaml.snakeyaml.tokens;

import java.util.*;
import org.yaml.snakeyaml.error.*;

public final class DirectiveToken<T> extends Token
{
    private final String name;
    private final List<T> value;
    
    public DirectiveToken(final String name, final List<T> value, final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
        this.name = name;
        if (value != null && value.size() != 2) {
            throw new YAMLException("Two strings must be provided instead of " + String.valueOf(value.size()));
        }
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<T> getValue() {
        return this.value;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Directive;
    }
}
