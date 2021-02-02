package com.flipkart.exception;

/**
 * @author JEDI05
 */

/**
 * Exception class for user not found exception
 */
public class UserNotFoundException extends Exception {
    String username;

    /**
     * Constructor to initialize username
     *
     * @param username unique identifer of user
     */
    public UserNotFoundException(String username) {
        super();
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
