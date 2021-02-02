package com.flipkart.service;

import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;
import com.flipkart.dao.UserDaoImplement;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * User service class that implements UserInterface
 */
public class UserOperation implements UserInterface {

    private UserDaoImplement userDaoImplement = UserDaoImplement.getInstance();

    /**
     * Allows user to perform login into the system
     *
     * @param username unique identifier of user required for login
     * @param password password of the user required for login
     * @return String denoting the user type
     */
    @Override
    public String login(String username, String password) {
        String userType = null;
        userType = userDaoImplement.login(username, password);
        return userType;
    }

    /**
     * Allows student to do registration
     *
     * @param student  object of student class passed for registration
     * @param username unique identifier of user required for registration
     * @param password password of the user required for registration
     */
    @Override
    public Boolean registerStudent(Student student, String username, String password) {
        return userDaoImplement.registerStudent(student, username, password);
    }

    /**
     * Allows user to perform forgot password
     *
     * @param username unique identifier of user used for forgot password
     * @return String denoting whether the forgot password was successful or not
     */
    @Override
    public String forgotPassword(String username) {
        return userDaoImplement.forgotPassword(username);
    }

    /**
     * Allows user registration
     *
     * @param username unique identifier of user used for registration
     * @param password password of the user used for registration
     * @param role     denoting usertype
     * @return true if successful registration else false
     */
    @Override
    public Boolean registerUser(String username, String password, String role) {
        return userDaoImplement.registerUser(username, password, role);
    }

    /**
     *
     * @param username
     * @return List of Notifications
     */
    @Override
    public List<Notification> showNotifications(String username) {
        return userDaoImplement.showNotifications(username);
    }

}