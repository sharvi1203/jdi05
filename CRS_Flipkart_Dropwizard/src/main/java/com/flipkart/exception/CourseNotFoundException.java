package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for course not found exception
 */
public class CourseNotFoundException extends Exception {

    /**
     * Constructor to display exception message
     */
    public CourseNotFoundException() {
        super("Course Not Found");
    }

}
