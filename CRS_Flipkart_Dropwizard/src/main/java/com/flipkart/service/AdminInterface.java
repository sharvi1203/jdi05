package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.bean.Student;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Admin interface that extends UserInterface
 */
public interface AdminInterface extends UserInterface {

    /**
     * Assigns course to a Professor and throws RepeatException if the course is already assigned
     *
     * @param professorId unique identifier of professor used for assigning course
     * @param courseId    unique identifier of course which is assigned to a professor
     */
    public void assignProfessorToCourse(int professorId, int courseId) throws RepeatException;

    /**
     * Adds Course to the catalog and throws RepeatException if course is already present
     *
     * @param course object of Course class used for adding course in the catalog
     */
    public void addCourseInCatalog(Course course) throws RepeatException;

    /**
     * Drops Course from the catalog
     *
     * @param courseId unique identifier used for dropping a course from the catalog
     */
    public String dropCourseInCatalog(int courseId);

    /**
     * Returns list of courses present in the catalog
     *
     * @return the list of course present in catalog
     */
    public List<Course> viewCoursesInCatalog();

    /**
     * Enrolls student in a particular course and throws LimitExceededException if student limit has reached
     *
     * @param studentId unique identifier of student used for enrolling in a course
     * @param courseId  unique identifier of course used for student enrollment
     */
    public void AssignStudentToCourse(int studentId, int courseId) throws LimitExceededException;

    /**
     * Adds Professor
     *
     * @param professor unique identifier of professor used for adding professor
     * @param username  username of professor
     * @param password  password of professor
     */
    public String addProfessor(Professor professor, String username, String password);

    /**
     * Drops Professor
     *
     * @param username unique identifier of professor used for dropping professor
     */
    public String dropProfessor(String username);

    /**
     * Returns list of students enrolled in a course
     *
     * @param courseId unique identifier of course used for displaying enrolled students
     * @return the list of enrolled students
     */
    List<Student> viewStudentInCourse(int courseId);

    /**
     * returns username of logged in admin
     *
     * @return username of Admin
     */
    public String getAdminUsername();

    public List<RequestedCourse> getAllRequestedCourses();

    public List<Professor> getAllProfessors();

    public List<Student> getAllStudents();
}