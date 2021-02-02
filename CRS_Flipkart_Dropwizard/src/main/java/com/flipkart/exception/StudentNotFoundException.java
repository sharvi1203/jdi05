package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for student not found exception
 */
public class StudentNotFoundException extends Exception {

    /**
     * Constructor to display exception message
     */
    public StudentNotFoundException() {
        super("Student Not Found");
    }
}
