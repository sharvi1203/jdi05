package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * CourseDaoInterface class
 */
public interface CourseDaoInterface {

    /**
     * Extracts course object of Course class
     *
     * @param courseId unique identifier of course used for getting course information
     * @return
     */
    public Course getCourse(int courseId);

    /**
     * Extracts all the courses present in catalog
     *
     * @return the list of courses from the catalog
     */
    public List<Course> getAllCoursesInCatalog();

    /**
     * Extracts list of students enrolled in a course
     *
     * @param courseId unique identifier of course used for extracting list of enrolled students
     * @return the list of enrolled students
     */
    public List<Student> getStudents(int courseId);

    /**
     * Extracts assigned Professor details
     *
     * @param courseId unique identifier of course used for extracting course details
     * @return professor object which is assigned to a course
     */
    public Professor getProfessorOfCourse(int courseId); // rename this in professor operation

    /**
     * Assigns professor to a particular course
     *
     * @param professorId unique identifier of professor used for assigning a course
     * @param courseId    unique identifier of course used for assigning a course
     */
    public void assignProfessor(int professorId, int courseId);

    /**
     * Adds requested course by a student
     *
     * @param studentId unique identifier of student used for adding requested course
     * @param courseId  unique identifier of course used for adding requested course
     * @param isPrimary indicates whether requested course is primary or not
     */
    public void addRequestedCourse(int studentId, int courseId, Boolean isPrimary); // check data type of isPrimary

    /**
     * Assigns requested course to the student
     *
     * @param studentId unique identifier of student used for assigning requested course
     * @param courseId  unique identifier of course used for assigning requested course
     */
    public void assignRequestedCourse(int studentId, int courseId);

    /**
     * Extracts the list of courses a student has enrolled in
     *
     * @param studentId unique identifier of student used for extracting list of students
     * @return the list of courses a student has enrolled in
     */
    public List<Course> getCourses(int studentId);

    /**
     * Assigns grade to the student for a particular course
     *
     * @param grade object of Grade class
     */
    public void assignGrade(Grade grade);

    /**
     * Extracts the list of grades of a particular student
     *
     * @param studentId unique identifier of student used for extracting the list of grades
     * @return the list of grades of a particular student
     */
    public List<Grade> getGrades(int studentId);

    /**
     * Drops student from a particular course
     *
     * @param studentId unique identifier of student used for dropping a course
     * @param courseId  unique identifier of course used for dropping a course
     */
    public void dropStudentToCourse(int studentId, int courseId);

    /**
     * Extracts the list of requested courses of student
     *
     * @param studentId unique identifier of student used for extracting requested courses of student
     * @return the list of requested courses
     */
    public List<Course> getRequestedCoursesOfStudents(int studentId);
}