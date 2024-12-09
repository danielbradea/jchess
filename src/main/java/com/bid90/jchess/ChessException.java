package com.bid90.jchess;

/**
 * A custom exception class for handling chess-specific errors.
 * Extends {@code RuntimeException} to represent unchecked exceptions
 * that may occur in a chess-related application.
 */
public class ChessException extends RuntimeException {

    /**
     * Constructs a new {@code ChessException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception.
     */
    public ChessException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ChessException} with the specified detail message
     * and cause.
     *
     * @param message the detail message describing the cause of the exception.
     * @param cause   the underlying cause of the exception (a {@code Throwable}),
     *                which can be retrieved later using {@link Throwable#getCause()}.
     */
    public ChessException(String message, Throwable cause) {
        super(message, cause);
    }
}
