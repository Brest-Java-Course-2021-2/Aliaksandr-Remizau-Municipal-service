package com.epam.brest.rest.exception;

import java.util.Arrays;
import java.util.List;
/**
 * ErrorResponse.
 */
public class ErrorResponse {
    /**
     * Field message.
     */

    private String message;

    /**
     * Field details.
     */

    private List<String> details;

    /**
     * Constructor.
     */

    public ErrorResponse() {
        super();
    }

    /**
     * Constructor.
     *
     * @param enterDetails List<String>.
     * @param enterMessage String.
     */

    public ErrorResponse(final String enterMessage,
                         final List<String> enterDetails) {
        super();
        this.message = enterMessage;
        this.details = enterDetails;
    }

    /**
     * Constructor.
     *
     * @param enterMessage String.
     * @param enterEx Exception.
     */

    public ErrorResponse(final String enterMessage, final Exception enterEx) {
        super();
        this.message = enterMessage;
        if (enterEx != null) {
            this.details = Arrays.asList(enterEx.getMessage());
        }
    }

    /**
     * Getter for message.
     *
     * @return message String.
     */

    public String getMessage() {
        return message;
    }

    /**
     * Setter for message.
     *
     * @param enterMessage String.
     */

    public void setMessage(final String enterMessage) {
        this.message = enterMessage;
    }

    /**
     * Getter for details.
     *
     * @return details List<String>.
     */

    public List<String> getDetails() {
        return details;
    }

    /**
     * Getter for details.
     *
     * @param enterDetails List<String>.
     */

    public void setDetails(final List<String> enterDetails) {
        this.details = enterDetails;
    }
}
