package com.flipkart.service;

import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * User interface
 */
public interface UserInterface {
    /**
     * Allows user to perform login into the system
     *
     * @param username unique identifier of user required for login
     * @param password password of the user required for login
     * @return String denoting the user type
     */
    public String login(String username, String password);

    /**
     * Allows student to do registration
     *
     * @param student  object of student class paased for registration
     * @param username unique identifier of user required for registration
     * @param password password of the user required for registration
     */
    public Boolean registerStudent(Student student, String username, String password);

    /**
     * Allows user to perform forgot password
     *
     * @param username unique identifier of user used for forgot password
     * @return String denoting whether the forgot password was successful or not
     */
    public String forgotPassword(String username);

    /**
     * Allows user registration
     *
     * @param username unique identifier of user used for registration
     * @param password password of the user used for registration
     * @param role     denoting usertype
     * @return true if successful registration else false
     */
    public Boolean registerUser(String username, String password, String role);

    /**
     * @param username
     * @return List of Notifications
     */
    public List<Notification> showNotifications(String username);

}
