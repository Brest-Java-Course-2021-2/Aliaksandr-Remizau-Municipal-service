package com.epam.brest.rest.exception;

import com.epam.brest.rest.ClientController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * CustomExceptionHandler.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception exception){
        return new ResponseEntity<String>
                (String.format("Handle : %s",exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
