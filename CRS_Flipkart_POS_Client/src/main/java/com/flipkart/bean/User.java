package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * User bean class
 */
public class User {
    private String username;
    private String password;

    User() {
    }

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
