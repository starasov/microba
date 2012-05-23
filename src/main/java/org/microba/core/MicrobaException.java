package org.microba.core;

/**
 * @author starasov
 */
public class MicrobaException extends RuntimeException {
    public MicrobaException(String message) {
        super(message);
    }

    public MicrobaException(String message, Throwable cause) {
        super(message, cause);
    }
}
