package org.example.exception;

public class MissingIpAddressException extends Exception {
    public MissingIpAddressException(String message) {
        super(message);
    }
}
