package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.AdminDaoImplement;
import com.flipkart.dao.CourseDaoImplement;
import com.flipkart.dao.ProfessorDaoImplement;
import com.flipkart.dao.StudentDaoImplement;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Admin service class that extends UserOperation and implements AdminInterface
 */
public class AdminOperation extends UserOperation implements AdminInterface {

    private static Logger logger = Logger.getLogger(AdminOperation.class);
    private int adminId;
    private AdminDaoImplement adminDaoImplement = AdminDaoImplement.getInstance();
    private ProfessorDaoImplement professorDaoImplement = ProfessorDaoImplement.getInstance();
    private StudentDaoImplement studentDaoImplement = StudentDaoImplement.getInstance();
    private CourseDaoImplement courseDaoImplement = CourseDaoImplement.getInstance();

    public AdminOperation(int adminId) {
        this.adminId = adminId;
    }

    /**
     * Adds Course to the catalog and throws RepeatException if course is already present
     *
     * @param newCourse object of Course class used for adding course in the catalog
     */
    @Override
    public void addCourseInCatalog(Course newCourse) throws RepeatException {
        List<Course> allCourses = courseDaoImplement.getAllCoursesInCatalog();

        for (Course course : allCourses)
            if (course.getCourseId() == newCourse.getCourseId())
                throw new RepeatException(StatementConstants.CoursePresent);

        adminDaoImplement.addCourse(newCourse);
        adminDaoImplement.assignProfessor(newCourse.getProfessorId(), newCourse.getCourseId());
    }

    /**
     * Drops Course from the catalog
     *
     * @param courseId unique identifier used for dropping a course from the catalog
     */
    @Override
    public String dropCourseInCatalog(int courseId) {
        return adminDaoImplement.dropCourse(courseId);
    }

    /**
     * Returns list of courses present in the catalog
     *
     * @return the list of course present in catalog
     */
    @Override
    public List<Course> viewCoursesInCatalog() {
        List<Course> allCourses = courseDaoImplement.getAllCoursesInCatalog();
        return allCourses;
    }

    /**
     * Enrolls student in a particular course and throws LimitExceededException if student limit has reached
     *
     * @param studentId unique identifier of student used for enrolling in a course
     * @param courseId  unique identifier of course used for student enrollment
     */
    @Override
    public void AssignStudentToCourse(int studentId, int courseId) throws LimitExceededException {
        List<Student> studentsInCourse = courseDaoImplement.getStudentsOfCourse(courseId);

        if (studentsInCourse.size() < 10) {
            courseDaoImplement.assignStudentToCourse(studentId, courseId);
            logger.info(String.format("The course has %d students", studentsInCourse.size() + 1));
        } else
            throw new LimitExceededException(StatementConstants.CourseFull);

    }

    /**
     * Adds Professor
     *
     * @param professor unique identifier of professor used for adding professor
     * @param username  username of professor
     * @param password  password of professor
     */
    @Override
    public String addProfessor(Professor professor, String username, String password) {
        return adminDaoImplement.addProfessor(professor, username, password);
    }

    /**
     * Drops Professor
     *
     * @param username unique identifier of professor used for dropping professor
     */
    @Override
    public String dropProfessor(String username) {
        return adminDaoImplement.dropProfessor(username);
    }

    /**
     * Assigns course to a Professor and throws RepeatException if the course is already assigned
     *
     * @param professorId unique identifier of professor used for assigning course
     * @param courseId    unique identifier of course which is assigned to a professor
     */
    @Override
    public void assignProfessorToCourse(int professorId, int courseId) throws RepeatException {
        Professor professorOfCourse = courseDaoImplement.getProfessorOfCourse(courseId);

        if (professorOfCourse == null)
            courseDaoImplement.assignProfessorToCourse(professorId, courseId);
        else
            throw new RepeatException(StatementConstants.ProfAssigned);
    }

    /**
     * Returns list of students enrolled in a course
     *
     * @param courseId unique identifier of course used for displaying enrolled students
     * @return the list of enrolled students
     */
    @Override
    public List<Student> viewStudentInCourse(int courseId) {
        List<Student> studentsInCourse = courseDaoImplement.getStudentsOfCourse(courseId);
        return studentsInCourse;
    }

    @Override
    public String getAdminUsername() {
        return adminDaoImplement.getAdminUsername(this.adminId);
    }


    /**
     * Returns list of all requested courses
     *
     * @return
     */
    @Override
    public List<RequestedCourse> getAllRequestedCourses() {
        return courseDaoImplement.getAllRequestedCourses();
    }

    /**
     * Returns list of all Professors
     *
     * @return
     */
    @Override
    public List<Professor> getAllProfessors() {
        return professorDaoImplement.getAllProfessors();
    }


    /**
     * Return list of all Professors
     *
     * @return
     */
    @Override
    public List<Student> getAllStudents() {
        return studentDaoImplement.getAllStudents();
    }


}