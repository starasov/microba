package org.microba.core;

/**
 * @author starasov
 */
public class MicrobaConfigurationException extends MicrobaException {
    public MicrobaConfigurationException(String message) {
        super(message);
    }

    public MicrobaConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
