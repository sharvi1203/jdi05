package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.CourseDaoImplement;
import com.flipkart.dao.StudentDaoImplement;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Student service class that extends UserOperation and implements StudentInterface
 */
public class StudentOperation extends UserOperation implements StudentInterface {
    private static Logger logger = Logger.getLogger(StudentOperation.class);
    private CourseDaoImplement courseDaoImplement = CourseDaoImplement.getInstance();
    private StudentDaoImplement studentDaoImplement = StudentDaoImplement.getInstance();
    private int studentId;

    public StudentOperation(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Extracts list of courses present in catalog
     *
     * @return the list of courses present in catalog
     */
    @Override
    public List<Course> viewCourses() {

        List<Course> allCourses = courseDaoImplement.getAllCoursesInCatalog();
        return allCourses;
    }

    /**
     * Allows students to choose a course and throws RepeatException, LimitExceededException if course is already selected or if the
     * limit is exceeded
     *
     * @param courseId  unique identifier for choosing a course
     * @param isPrimary denotes whether course is primary or secondary
     * @throws RepeatException        thrown when course is already chosen by student
     * @throws LimitExceededException thrown when limit is exceeded
     */
    @Override
    public void chooseCourse(int courseId, boolean isPrimary) throws RepeatException, LimitExceededException {

        RequestedCourse newRequestedCourse = new RequestedCourse(courseId, this.studentId, isPrimary);
        List<RequestedCourse> requestedCourseOfStudent = courseDaoImplement.getRequestedCoursesOfStudent(this.studentId);

        int noOfPrimaryCourse = 0;
        int noOfSecondaryCourse = 0;
        for (RequestedCourse requestedCourse : requestedCourseOfStudent) {
            if (requestedCourse.isPrimary())
                noOfPrimaryCourse++;
            else
                noOfSecondaryCourse++;

            if (requestedCourse.getCourseId() == newRequestedCourse.getCourseId())
                throw new RepeatException(StatementConstants.AlreadyRequested);
        }

        if (newRequestedCourse.isPrimary()) {
            if (noOfPrimaryCourse < 4)
                courseDaoImplement.addRequestedCourse(newRequestedCourse);
            else
                throw new LimitExceededException(StatementConstants.PrimaryRequested);

        } else {
            if (noOfSecondaryCourse < 2)
                courseDaoImplement.addRequestedCourse(newRequestedCourse);
            else
                throw new LimitExceededException(StatementConstants.SecondaryRequested);
        }

    }

    /**
     * Allows students to drop a course and throws CourseNotFoundException
     *
     * @param courseId unique identifier for dropping a course
     * @throws CourseNotFoundException thrown when course is not found
     */
    @Override
    public void dropCourse(int courseId) throws CourseNotFoundException {
        //check if student is in the course
        List<Student> studentsInCourse = courseDaoImplement.getStudentsOfCourse(courseId);
        for (Student student : studentsInCourse)
            if (student.getStudentId() == this.studentId) {
                courseDaoImplement.dropStudentToCourse(this.studentId, courseId);
                return;
            }
        throw new CourseNotFoundException();
    }

    /**
     * Returns total fees of the courses in which the student has enrolled
     *
     * @return Total fees of the student
     */
    @Override
    public int getFees() {

        List<Course> allCourseOfStudent = courseDaoImplement.getAssignedCoursesOfStudent(this.studentId);
        int totalFees = 0;

        for (Course course : allCourseOfStudent)
            totalFees += course.getFees();

        return totalFees;
    }

    /**
     * Returns list of grades of a particular student
     *
     * @return list of grades
     */
    @Override
    public List<Grade> viewReportCard() {

        List<Grade> allGradesOfStudent = courseDaoImplement.getGrades(this.studentId);
        return allGradesOfStudent;
    }

    /**
     * Returns list of courses requested by the student
     *
     * @return list of requested courses
     */
    @Override
    public List<RequestedCourse> viewRequestedCourses() {
        List<RequestedCourse> requestedCourses = courseDaoImplement.getRequestedCoursesOfStudent(this.studentId);
        return requestedCourses;
    }

    /**
     * Returns list of courses the student has enrolled in
     *
     * @return list of enrolled courses
     */
    @Override
    public List<Course> viewAssignedCourses() {
        List<Course> allCourses = courseDaoImplement.getAssignedCoursesOfStudent(this.studentId);
        return allCourses;
    }

    /**
     * Notifies for payment of course
     *
     * @param totalFees of courses student has enrolled in
     */
    @Override
    public void notifyPayment(int totalFees) {
        String username = studentDaoImplement.getStudentUsername(this.studentId);
        studentDaoImplement.notifyPayment(username, totalFees);
    }

    @Override
    public String getStudentUsername() {
        return studentDaoImplement.getStudentUsername(this.studentId);
    }

}