package com.flipkart.dao;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.constant.SQLQueries;
import com.flipkart.constant.StatementConstants;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JEDI05
 */

/**
 * AdminDaoImplement class that implements AdminDoaInterface
 */
public class AdminDaoImplement implements AdminDaoInterface {

    private static Logger logger = Logger.getLogger(AdminDaoImplement.class);
    private static AdminDaoImplement singleton;
    static Connection connection = DBUtils.getConnection();
    UserInterface userOperation = new UserOperation();


    private AdminDaoImplement() {
        // pass
    }


    public static AdminDaoImplement getInstance() {
        if (singleton == null) {
            singleton = new AdminDaoImplement();
        }
        return singleton;
    }

    /**
     * Adds new courses to the catalog
     *
     * @param course unique identifier of course used for adding course to the catalog
     */
    @Override
    public void addCourse(Course course) {
        PreparedStatement stmt = null;
        String res = "";
        try {
            stmt = connection.prepareStatement(SQLQueries.ADD_COURSE);
            stmt.setInt(1, course.getCourseId());
            stmt.setString(2, course.getName());
            stmt.setString(3, course.getDepartment());
            stmt.setInt(4, course.getFees());
            int rows = stmt.executeUpdate();
            res = "Successfully added " + rows + " Course";
        } catch (SQLException se) {
            res = se.getMessage();
        } catch (Exception e) {
            res = e.getMessage();
        }
    }

    /**
     * Checks whether a particular course exist in catalog or not
     *
     * @param courseId unique identifier of course used for checking course in the catalog
     */
    public Boolean courseExist(int courseId) {
        PreparedStatement stmt = null;
        Boolean result = false;
        try {
            stmt = connection.prepareStatement(SQLQueries.GET_COURSE);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Checks whether a particular professor exist or not
     *
     * @param professorId unique identifier of professor used for checking whether present or not
     */
    public Boolean professorExist(int professorId) {
        PreparedStatement stmt = null;
        Boolean result = false;
        try {
            stmt = connection.prepareStatement(SQLQueries.CHECK_PROFESSOR_EXISTS);
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Checks whether a particular user exist or not
     *
     * @param username unique identifier of user used for checking whether present or not
     */
    public boolean userExist(String username) {
        PreparedStatement stmt = null;
        Boolean result = false;
        try {
            stmt = connection.prepareStatement(SQLQueries.CHECK_USER_EXISTS);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Deletes course from the catalog
     *
     * @param courseId unique identifier of course used for dropping a course from the catalog
     */
    @Override
    public String dropCourse(int courseId) {
        PreparedStatement stmt = null;
        String res = "";
        if (!courseExist(courseId)) {
            logger.warn(StatementConstants.ValidCId);
        } else {
            try {
                stmt = connection.prepareStatement(SQLQueries.DROP_COURSE);
                stmt.setInt(1, courseId);
                int rows = stmt.executeUpdate();
                res = "Successfully deleted " + rows + " Course";
            } catch (SQLException se) {
                res = se.getMessage();
            } catch (Exception e) {
                res = e.getMessage();
            }
        }
        return res;
    }

    /**
     * Adds professor using username and password
     *
     * @param professor unique identifier of professor used for adding professor
     * @param username  username of professor
     * @param password  password of professor
     */
    @Override
    public String addProfessor(Professor professor, String username, String password) {
        PreparedStatement stmt = null;
        String res = "";
        if (userExist(username)) {
            res = StatementConstants.UserExists;
        } else if (professorExist(professor.getProfessorId())) {
            res = StatementConstants.ProfIdExists;
        } else {
            try {
                Boolean result = userOperation.registerUser(username, password, "p");
                stmt = connection.prepareStatement(SQLQueries.ADD_PROFESSOR);
                stmt.setInt(1, professor.getProfessorId());
                stmt.setString(2, username);
                stmt.setString(3, professor.getName());
                stmt.setString(4, professor.getGender());
                int rows = stmt.executeUpdate();
                res = "Successfully added " + rows + " Professor";
            } catch (SQLException se) {
                res = se.getMessage();
            } catch (Exception e) {
                res = e.getMessage();
            }
        }
        return res;
    }

    /**
     * Drops professor using username
     *
     * @param username unique identifier of professor used for dropping
     */
    @Override
    public String dropProfessor(String username) {
        String res = "";
        if (!userExist(username)) {
            res = StatementConstants.ProfDoesntExists;
        } else {
            PreparedStatement stmt = null;
            try {
                stmt = connection.prepareStatement(SQLQueries.DROP_USER);
                stmt.setString(1, username);
                int rows = stmt.executeUpdate();
                res = "Successfully removed " + rows + " Professor";
            } catch (SQLException se) {
                res = se.getMessage();
            } catch (Exception e) {
                res = e.getMessage();
            }
        }
        return res;
    }

    /**
     * Extracts admin object of Admin class
     *
     * @param username unique identifier of the admin
     */
    @Override
    public Admin getAdmin(String username) {
        PreparedStatement stmt = null;
        Admin admin = null;
        try {
            stmt = connection.prepareStatement(SQLQueries.GET_ADMIN);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                admin = new Admin(rs.getInt(1), rs.getString(2), rs.getString(4));
            }

        } catch (SQLException se) {
            logger.info(se.getMessage());
        }

        return admin;
    }

    /**
     * Assigns course to the professor
     *
     * @param professorId unique identifier of professor used for assigning a course to the professor
     * @param courseId    unique identifier of course used for assigning a course to the professor
     */
    @Override
    public void assignProfessor(int professorId, int courseId) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueries.ASSIGN_PROFESSOR);
            stmt.setInt(1, courseId);
            stmt.setInt(2, professorId);
            int rows = stmt.executeUpdate();
            logger.info(StatementConstants.ProfSuccess);
        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Extracts admin information by using adminId
     *
     * @param adminId unique identifier of admin used for fetching admin username
     * @return admin username
     */
    public String getAdminUsername(int adminId) {
        PreparedStatement stmt = null;
        String username = null;
        try {
            stmt = connection.prepareStatement("SELECT username from admindetails where adminId = ?");
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                username = rs.getString(1);
            }

        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return username;
    }

}