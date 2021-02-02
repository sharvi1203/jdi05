package com.flipkart.dao;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;

/**
 * @author JEDI05
 */

/**
 * AdminDaoInterface class
 */
public interface AdminDaoInterface {

    /**
     * Extracts admin object of Admin class
     *
     * @param username unique identifier of the admin
     */
    public Admin getAdmin(String username);

    /**
     * Assigns course to the professor
     *
     * @param professorId unique identifier of professor used for assigning a course to the professor
     * @param courseId    unique identifier of course used for assigning a course to the professor
     */
    public void assignProfessor(int professorId, int courseId);

    /**
     * Adds new courses to the catalog
     *
     * @param course unique identifier of course used for adding course to the catalog
     */
    public void addCourse(Course course);

    /**
     * Deletes course from the catalog
     *
     * @param courseId unique identifier of course used for dropping a course from the catalog
     */
    public String dropCourse(int courseId);

    /**
     * Adds professor using username and password
     *
     * @param professor unique identifier of professor used for adding professor
     * @param username  username of professor
     * @param password  password of professor
     */
    public String addProfessor(Professor professor, String username, String password);

    /**
     * Drops professor using username
     *
     * @param username unique identifier of professor used for dropping
     */
    public String dropProfessor(String username);

}