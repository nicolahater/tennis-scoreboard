package dev.backend.exception;

public class SamePlayersException extends RuntimeException {
    public SamePlayersException(String name) {
        super("Players must be different. Both players' name is: %s".formatted(name));
    }
}
