package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.CourseDaoImplement;
import com.flipkart.dao.ProfessorDaoImplement;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Professor service class that extends UserOperation and implements ProfessorInterface
 */
public class ProfessorOperation extends UserOperation implements ProfessorInterface {


    private static Logger logger = Logger.getLogger(ProfessorOperation.class);
    private int professorId;
    private CourseDaoImplement courseDaoImplement = CourseDaoImplement.getInstance();
    private ProfessorDaoImplement professorDaoImplement = ProfessorDaoImplement.getInstance();

    public ProfessorOperation(int professorId) {
        this.professorId = professorId;
    }

    /**
     * Returns list of courses present in catalog
     *
     * @return list of courses in catalog
     */
    @Override
    public List<Course> viewCourses() {
        return courseDaoImplement.getAllCoursesInCatalog();
    }

    /**
     * Returns list of courses assigned to Professor
     *
     * @return list of assigned courses
     */
    @Override
    public List<Course> viewAssignedCourses() {
        List<Course> allCourses = courseDaoImplement.getAllCoursesInCatalog();
        //logger.info("all.courses.size" + allCourses.size());
        List<Course> professorCourses = new ArrayList<>();
        //logger.info("this.professorId in viewAssignedCourses" + this.professorId);
        //logger.info("professorCourses.size" + professorCourses.size());
        for (Course course : allCourses) {
            //logger.info("CourseId : "+course.getCourseId());
            //logger.info("Professor Id : "+course.getProfessorId());
            if (course.getProfessorId() == this.professorId) {
                professorCourses.add(course);
            }
        }
        //logger.info("professorCourses.size" + professorCourses.size());
        return professorCourses;
    }

    /**
     * Returns list of students enrolled in a course assigned to a Professor and throws CourseNotAccesibleException
     *
     * @param courseId unique identifier used for displaying enrolled students
     * @return list of students enrolled in a course
     */
    @Override
    public List<Student> viewStudents(int courseId) throws CourseNotAccesibleException {

        Professor professorOfCourse = courseDaoImplement.getProfessorOfCourse(courseId);

        if (professorOfCourse == null || professorOfCourse.getProfessorId() != this.professorId)
            throw new CourseNotAccesibleException();


        List<Student> studentsList = courseDaoImplement.getStudentsOfCourse(courseId);
        //logger.info(studentsList.size());
        return studentsList;
    }

    /**
     * Assigns grades to the student and throws CourseNotAccesibleException, StudentNotFoundException, RepeatException
     *
     * @param newGrade object of Grade class taken to assign grades to student
     */
    @Override
    public void assignGrades(Grade newGrade) throws CourseNotAccesibleException, StudentNotFoundException, RepeatException {

        Professor professorOfCourse = courseDaoImplement.getProfessorOfCourse(newGrade.getCourseId());

        if (professorOfCourse == null || professorOfCourse.getProfessorId() != this.professorId)
            throw new CourseNotAccesibleException();


        List<Student> studentsList = courseDaoImplement.getStudentsOfCourse(newGrade.getCourseId());
        boolean studentFound = false;

        for (Student student : studentsList)
            if (student.getStudentId() == newGrade.getStudentId())
                studentFound = true;

        if (!studentFound)
            throw new StudentNotFoundException();


        List<Grade> studentGrades = courseDaoImplement.getGrades(newGrade.getStudentId());
        boolean alreadyGraded = false;

        for (Grade grade : studentGrades) {
            if (grade.getCourseId() == newGrade.getCourseId())
                alreadyGraded = true;
        }

        if (alreadyGraded)
            throw new RepeatException(StatementConstants.GradeAssigned);


        courseDaoImplement.assignGrade(newGrade);
    }


    /**
     * Allows Professor to choose a course and throws RepeatException if course is already assigned
     *
     * @param courseId unique identifier used for selecting course
     */
    @Override
    public void chooseCourse(int courseId) throws RepeatException {

        Professor professor = courseDaoImplement.getProfessorOfCourse(courseId);
        //returns null if empty

        if (professor == null)
            courseDaoImplement.assignProfessorToCourse(professorId, courseId);
        else
            throw new RepeatException(StatementConstants.ProfAssigned);
    }

    /**
     * @return returns username
     */
    @Override
    public String getProfessorUsername() {
        return professorDaoImplement.getProfessorUsername(this.professorId);
    }
}