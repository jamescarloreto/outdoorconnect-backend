package com.outdoor.connect.exception;

/**
 * @author James Carl Oreto
 */
public class NoParticipantException extends RuntimeException {

    public NoParticipantException(String message) {
        super(message);
    }

    public NoParticipantException() {
        super("Participant not found");
    }
}
