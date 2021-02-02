package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for course not accessible exception
 */
public class CourseNotAccesibleException extends Exception {

    /**
     * Constructor to display exception message
     */
    public CourseNotAccesibleException() {
        super("This course is not accessible");
    }
}
