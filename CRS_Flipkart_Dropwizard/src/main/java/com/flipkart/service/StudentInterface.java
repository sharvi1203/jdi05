package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Student interface that extends UserInterface
 */
public interface StudentInterface extends UserInterface {

    /**
     * Allows students to choose a course and throws RepeatException, LimitExceededException if course is already selected or if the
     * limit is exceeded
     *
     * @param courseId  unique identifier for choosing a course
     * @param isPrimary denotes whether course is primary or secondary
     * @throws RepeatException        thrown when course is already chosen by student
     * @throws LimitExceededException thrown when limit is exceeded
     */
    public void chooseCourse(int courseId, boolean isPrimary) throws RepeatException, LimitExceededException;

    /**
     * Allows students to drop a course and throws CourseNotFoundException
     *
     * @param courseId unique identifier for dropping a course
     * @throws CourseNotFoundException thrown when course is not found
     */
    public void dropCourse(int courseId) throws CourseNotFoundException;

    /**
     * Extracts list of courses present in catalog
     *
     * @return the list of courses present in catalog
     */
    public List<Course> viewCourses();

    /**
     * Returns total fees of the courses in which the student has enrolled
     *
     * @return Total fees of the student
     */
    public int getFees();

    /**
     * Returns list of grades of a particular student
     *
     * @return list of grades
     */
    public List<Grade> viewReportCard();

    /**
     * Returns list of courses requested by the student
     *
     * @return list of requested courses
     */
    public List<RequestedCourse> viewRequestedCourses();

    /**
     * Returns list of courses the student has enrolled in
     *
     * @return list of enrolled courses
     */
    public List<Course> viewAssignedCourses();

    /**
     * Notifies for payment of course
     *
     * @param totalFees of courses student has enrolled in
     */
    public void notifyPayment(int totalFees);

    /**
     * @return username as String
     */
    public String getStudentUsername();
}
