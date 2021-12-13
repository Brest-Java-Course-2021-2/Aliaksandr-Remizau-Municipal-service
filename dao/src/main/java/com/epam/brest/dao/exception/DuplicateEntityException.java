package com.epam.brest.dao.exception;
/**
 * Contains Custom exception class Duplicate Entity in Database.
 */
public class DuplicateEntityException extends RuntimeException{
    /**
     * Constructor with  String message.
     *
     * @param message
     */
    public DuplicateEntityException(String message) {
        super(message);
    }
    /**
     * Constructor with String message and Throwable cause.
     *
     * @param message
     * @param cause
     */

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor with  Throwable cause.
     *
     * @param cause
     */

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
