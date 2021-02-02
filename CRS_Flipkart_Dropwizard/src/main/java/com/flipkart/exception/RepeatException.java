package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for repeat exception
 */
public class RepeatException extends Exception {

    /**
     * Constructor to display exception message
     */
    public RepeatException(String message) {
        super("Repeat Exception : " + message);
    }
}
