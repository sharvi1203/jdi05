package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * Notification bean class
 */
public class Notification {
    private String username;
    private String notificationText;

    public Notification(String username, String notificationText) {
        this.username = username;
        this.notificationText = notificationText;
    }

    public String getUsername() {
        return username;
    }

    public String getNotificationText() {
        return notificationText;
    }

}



