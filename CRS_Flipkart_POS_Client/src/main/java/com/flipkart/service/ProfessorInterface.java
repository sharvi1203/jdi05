package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Professor interface that extends UserInterface
 */
public interface ProfessorInterface extends UserInterface {

    /**
     * Returns list of courses present in catalog
     *
     * @return list of courses in catalog
     */
    public List<Course> viewCourses();

    /**
     * Returns list of courses assigned to Professor
     *
     * @return list of assigned courses
     */
    public List<Course> viewAssignedCourses();

    /**
     * Returns list of students enrolled in a course assigned to a Professor and throws CourseNotAccesibleException
     *
     * @param courseId unique identifier used for displaying enrolled students
     * @return list of students enrolled in a course
     */
    public List<Student> viewStudents(int courseId) throws CourseNotAccesibleException;

    /**
     * Assigns grades to the student and throws CourseNotAccesibleException, StudentNotFoundException, RepeatException
     *
     * @param newGrade object of Grade class taken to assign grades to student
     */
    public void assignGrades(Grade newGrade) throws CourseNotAccesibleException, StudentNotFoundException, RepeatException;

    /**
     * Allows Professor to choose a course and throws RepeatException if course is already assigned
     *
     * @param courseId unique identifier used for selecting course
     */
    public void chooseCourse(int courseId) throws RepeatException;

    /**
     * @return returns username
     */
    public String getProfessorUsername();

}

