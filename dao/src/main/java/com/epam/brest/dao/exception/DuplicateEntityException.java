package com.epam.brest.dao.exception;
/**
 * Contains Custom exception class Duplicate Entity in Database.
 */
public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
