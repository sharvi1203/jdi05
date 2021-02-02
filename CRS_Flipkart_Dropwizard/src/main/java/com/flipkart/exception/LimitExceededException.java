package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for limit exceeded exception
 */
public class LimitExceededException extends Exception {

    /**
     * Constructor to display exception message
     */
    public LimitExceededException(String message) {
        super("Limit Exceeded Exception : " + message);
    }

}
