package com.epam.brest.rest.exception;

import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.dao.exception.EmptyFieldEntityException;
import com.epam.brest.service.impl.exceptions.ClientNotFoundException;
import com.epam.brest.service.impl.exceptions.RepairNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomExceptionHandler.
 */

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(CustomExceptionHandler.class);

    /**
     * Field constant CLIENT_NOT_FOUND.
     */

    public static final String CLIENT_NOT_FOUND = "client.not_found";

    /**
     * Field constant REPAIR_NOT_FOUND.
     */

    public static final String REPAIR_NOT_FOUND = "repair.not_found";

    /**
     * Field constant VALIDATION_ERROR.
     */

    public static final String VALIDATION_ERROR = "validation_error";

    /**
     * Field constant DATA_BASE_ERROR.
     */
    public static final String DATA_BASE_ERROR = "data_base_error";

    /**
     * Exception handler  Repair not found exception.
     *
     * @param ex RepairNotFoundException class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(RepairNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponse> handleRepairNotFoundException
            (RepairNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(REPAIR_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler  Client not found exception.
     *
     * @param ex ClientNotFoundException class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponse> handleClientNotFoundException
            (ClientNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(CLIENT_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler Duplicate Entity exception.
     *
     * @param ex DuplicateEntityException class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<ErrorResponse> handleDuplicateEntityException
            (DuplicateEntityException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(VALIDATION_ERROR, details);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    /**
     * Exception handler EmptyFieldEntityException exception.
     *
     * @param ex EmptyFieldEntityException class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(EmptyFieldEntityException.class)
    public final ResponseEntity<ErrorResponse> handleEmptyFieldEntityException
    (EmptyFieldEntityException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(VALIDATION_ERROR, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    /**
     * Exception handler DataIntegrityViolationException.
     *
     * @param ex DataIntegrityViolationException class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException
        (DataIntegrityViolationException ex, WebRequest request) {
            List<String> details = new ArrayList<>();
            details.add(ExceptionUtils.getRootCauseMessage(ex));
            ErrorResponse error = new ErrorResponse(DATA_BASE_ERROR, details);
            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Exception handler IllegalArgumentException.
     *
     * @param ex Exception class.
     * @param request WebRequest class.
     * @return error response and HTTP status.
     */

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(VALIDATION_ERROR, ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
