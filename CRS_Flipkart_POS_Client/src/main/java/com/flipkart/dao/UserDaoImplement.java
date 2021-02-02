package com.flipkart.dao;

import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;
import com.flipkart.constant.SQLQueries;
import com.flipkart.constant.StatementConstants;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * UserDaoImplement class
 */
public class UserDaoImplement {
    private static Logger logger = Logger.getLogger(UserDaoImplement.class);
    private static UserDaoImplement singleton;
    static Connection connection = DBUtils.getConnection();

    private UserDaoImplement() {
        // pass
    }


    public static UserDaoImplement getInstance() {
        if (singleton == null) {
            singleton = new UserDaoImplement();
        }
        return singleton;
    }

    /**
     * Logging into the application using username and password
     *
     * @param username unique identifier of user for logging into the application
     * @param password password of user for logging into the application
     * @return role of the user
     */
    public String login(String username, String password) {
        PreparedStatement stmt = null;
        String role = "";
        try {
            stmt = connection.prepareStatement(SQLQueries.LOGIN);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return role;
    }

    /**
     * Registration of student into the application using username and password
     *
     * @param username unique identifier of student for registering into the application
     * @param password password of student for registering into the application
     * @return true if registration successful else false
     */
    public Boolean registerStudent(Student student, String username, String password) {
        if (!registerUser(username, password, "s")) {
            logger.info(StatementConstants.NoUser);
            return false;
        }

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(SQLQueries.REGISTER_STUDENT);
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, username);
            stmt.setString(3, student.getName());
            stmt.setString(4, student.getBranch());
            stmt.setString(5, student.getGender());
            stmt.setInt(6, student.getSemester());

            int rows = stmt.executeUpdate();

            if (rows >= 0) {
                return true;
            } else {
                stmt = connection.prepareStatement(SQLQueries.DROP_USER);
                stmt.setString(1, username);
                int rows2 = stmt.executeUpdate();
            }

        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return false;
    }

    /**
     * Registration of user into the application using username and password
     *
     * @param username unique identifier of user for registering into the application
     * @param password password of user for registering into the application
     * @return true if registration successful else false
     */
    public Boolean registerUser(String username, String password, String role) {

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueries.REGISTER_USER);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            int rows = stmt.executeUpdate();
            if (rows != 0) {
                logger.info(StatementConstants.Accepted);
                return true;
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return false;
    }

    /**
     * Forgot Password for the user
     *
     * @param username unique identifier of user for forgot password
     * @return notifies user whether forgot password was successful or not
     */
    public String forgotPassword(String username) {
        PreparedStatement stmt = null;
        PreparedStatement stmtNotify = null;
        String role = "";
        String notificationText = "";
        int c = 0;
        try {
            stmt = connection.prepareStatement(SQLQueries.CHECK_USER_EXISTS);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stmtNotify = connection.prepareStatement(SQLQueries.ADD_NOTIFY);
                stmtNotify.setString(1, username);
                stmtNotify.setString(2, SQLQueries.FORGOT_PASS_TEXT);
                notificationText = SQLQueries.FORGOT_PASS_TEXT;
                int rows = stmtNotify.executeUpdate();
                logger.info(StatementConstants.CheckMail);
            } else {
                logger.info(StatementConstants.CorrectUName);
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return notificationText;
    }

    /**
     * Displaying Notifications to the user
     *
     * @param username unique identifier of user for displaying notifications
     * @return sends user list of notifications
     */
    public List<Notification> showNotifications(String username) {
        PreparedStatement stmt = null;
        String notificationText = "";

        List<Notification> notf = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT username, notificationText FROM notificationdetails where username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String notfText = rs.getString(2);
                Notification notification = new Notification(username, notfText);
                notf.add(notification);
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return notf;
    }
}