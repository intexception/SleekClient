package org.yaml.snakeyaml.error;

public class MissingEnvironmentVariableException extends YAMLException
{
    public MissingEnvironmentVariableException(final String message) {
        super(message);
    }
}
