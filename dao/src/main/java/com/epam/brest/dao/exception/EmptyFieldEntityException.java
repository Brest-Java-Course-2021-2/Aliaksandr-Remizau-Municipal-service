package com.epam.brest.dao.exception;
/**
 * Contains Custom exception class Empty Field Entity Exception.
 */
public class EmptyFieldEntityException extends NullPointerException{

    public EmptyFieldEntityException() {
    }

    public EmptyFieldEntityException(String message) {
        super(message);
    }
}
