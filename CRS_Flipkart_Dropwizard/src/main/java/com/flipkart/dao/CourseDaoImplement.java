package com.flipkart.dao;

import com.flipkart.bean.*;
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
 * CourseDaoImplement class
 */
public class CourseDaoImplement {

    private static Logger logger = Logger.getLogger(CourseDaoImplement.class);
    private static CourseDaoImplement singleton;
    static Connection conn = DBUtils.getConnection();


    private CourseDaoImplement() {
        // pass
    }


    /**
     * @return singlton instance of class CourseDaoImplement
     */
    public static CourseDaoImplement getInstance() {
        if (singleton == null) {
            singleton = new CourseDaoImplement();
        }
        return singleton;
    }


    /**
     * Extracts course object of Course class
     *
     * @param courseId unique identifier of course used for getting course information
     * @return
     */
    public Course getCourse(int courseId) {

        PreparedStatement stmt = null;
        PreparedStatement stmtFetchProf = null;
        Course course = null; // error due to the need of updation of bean classes
        try {
            stmt = conn.prepareStatement(SQLQueries.GET_COURSE); // red lines due to unhandled exception
            stmt.setInt(1, courseId);
            stmtFetchProf = conn.prepareStatement(SQLQueries.GET_PROFESSOR_FOR_COURSE);
            stmtFetchProf.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            ResultSet rsProf = stmt.executeQuery();
            //courseId, name, department, fees
            course = new Course(rs.getString(2), courseId, rsProf.getInt(1), rs.getString(3), rs.getInt(4));

        } catch (SQLException se) {
            logger.info(se.getMessage());
        }

        return course;
    }

    /**
     * Extracts all the courses present in catalog
     *
     * @return the list of courses from the catalog
     */
    public List<Course> getAllCoursesInCatalog() {

        PreparedStatement stmt = null;
        PreparedStatement stmtFetchProf = null;
        List<Course> allCourses = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(SQLQueries.VIEW_CATALOG);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                Course course = new Course(rs.getString("name"), courseId, 0, rs.getString("dept"), rs.getInt("fees"));
                allCourses.add(course);
            }
            rs.close();
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        try {
            stmtFetchProf = conn.prepareStatement(SQLQueries.GET_PROFESSOR_ID_FOR_COURSE);
            for (Course course : allCourses) {
                //logger.info(course.getCourseId());
                stmtFetchProf.setInt(1, course.getCourseId());
                ResultSet rsProf = stmtFetchProf.executeQuery();
                int professorId = 0;
                while (rsProf.next()) {
                    professorId = rsProf.getInt("professorId");
                }
                //logger.info(professorId);
                course.setProfessorId(professorId);
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return allCourses;

    }

    /**
     * Extracts list of students enrolled in a course
     *
     * @param courseId unique identifier of course used for extracting list of enrolled students
     * @return the list of enrolled students
     */
    public List<Student> getStudentsOfCourse(int courseId) {

        PreparedStatement stmt = null;

        List<Student> students = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(SQLQueries.GET_STUDENTS);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString(2), rs.getInt(1), rs.getString(4), rs.getString(3), rs.getInt(5));
                students.add(student);
            }

            //logger.info(students.size());
        } catch (SQLException se) {
            logger.info(se.getMessage());
        }

        return students;
    }

    /**
     * Extracts assigned Professor details
     *
     * @param courseId unique identifier of course used for extracting course details
     * @return professor object which is assigned to a course
     */
    public Professor getProfessorOfCourse(int courseId) {

        PreparedStatement stmt = null;

        Professor professor = null;

        try {
            stmt = conn.prepareStatement(SQLQueries.GET_PROFESSOR_FOR_COURSE);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                professor = new Professor(rs.getString(2), rs.getInt(1), rs.getString(3));
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        }

        return professor;
    }

    /**
     * Assigns professor to a particular course
     *
     * @param professorId unique identifier of professor used for assigning a course
     * @param courseId    unique identifier of course used for assigning a course
     */
    public void assignProfessorToCourse(int professorId, int courseId) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQLQueries.ASSIGN_PROFESSOR);
            stmt.setInt(1, courseId);
            stmt.setInt(2, professorId);
            int rows = stmt.executeUpdate();
            logger.info("Successfully added " + rows + " Course");

        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }

    }

    /**
     * Adds requested course by a student
     *
     * @param requestedCourse unique identifier of course used for adding requested course
     */
    public void addRequestedCourse(RequestedCourse requestedCourse) {

        PreparedStatement stmt = null;
        try {

            int studentId = requestedCourse.getStudentId();
            //logger.info(studentId);
            int courseId = requestedCourse.getCourseId();
            Boolean isPrimary = requestedCourse.isPrimary();

            stmt = conn.prepareStatement(SQLQueries.ADD_REQUESTED_COURSE);

            int isPrimaryInt = 1;
            if (isPrimary == false) {
                isPrimaryInt = 0;
            }

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setInt(3, isPrimaryInt);
            int rows = stmt.executeUpdate();

            logger.info("Successfully added " + rows + " Course");

        } catch (SQLException se) {
            logger.info(se.getMessage());
        }
    }

    /**
     * Assigning requested course to student
     *
     * @param studentId unique identifier of student used for assigning requested course to student
     * @param courseId  unique identifier of course used for assigning requested course to student
     */
    public void assignStudentToCourse(int studentId, int courseId) {
        PreparedStatement stmt = null;


        try {
            stmt = conn.prepareStatement(SQLQueries.ASSIGN_REQUESTED_COURSE);

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            logger.info(StatementConstants.CourseSuccess);

        } catch (SQLException se) {
            logger.info(se.getMessage());
        }
    }

    /**
     * Extracts assigned courses of student
     *
     * @param studentId unique identifier of student to extract assigned courses of student
     * @return the list of assigned courses
     */
    public List<Course> getAssignedCoursesOfStudent(int studentId) { // should rename this to getAssignedCoursesOfStudent
        // this is function to get assigned courses of student
        PreparedStatement stmt = null;
        PreparedStatement stmtFetchProf = null;
        List<Course> allCourses = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(SQLQueries.GET_COURSES_FOR_STUDENT);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt(1);
                String name = rs.getString(2);
                String department = rs.getString(3);
                int fees = rs.getInt(4);
                Course course = new Course(name, courseId, 0, department, fees);
                allCourses.add(course);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        try {
            stmtFetchProf = conn.prepareStatement(SQLQueries.GET_PROFESSOR_ID_FOR_COURSE);
            for (Course course : allCourses) {
                //logger.info(course.getCourseId());
                stmtFetchProf.setInt(1, course.getCourseId());
                ResultSet rsProf = stmtFetchProf.executeQuery();
                int professorId = 0;
                while (rsProf.next()) {
                    professorId = rsProf.getInt("professorId");
                }
                //logger.info(professorId);
                course.setProfessorId(professorId);
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return allCourses;
    }

    /**
     * Drops student from a particular course
     *
     * @param studentId unique identifier of student used for dropping a course
     * @param courseId  unique identifier of course used for dropping a course
     */
    public void dropStudentToCourse(int studentId, int courseId) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQLQueries.DROP_ASSIGNED_COURSE);
            stmt.setInt(2, courseId);
            stmt.setInt(1, studentId);
            int rows = stmt.executeUpdate();
            logger.info("Successfully deleted " + rows + " Course");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Assigns grade to the student for a particular course
     *
     * @param grade object of Grade class
     */
    public void assignGrade(Grade grade) {
        PreparedStatement stmt = null;
        try {
            int studentId = grade.getStudentId();
            int courseId = grade.getCourseId();
            String gradeLetter = Character.toString(grade.getGradeLetter());
            stmt = conn.prepareStatement(SQLQueries.ASSIGN_GRADE_TO_STUDENT);
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setString(3, gradeLetter);
            int rows = stmt.executeUpdate();
            logger.info("Successfully added " + rows + " Course");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Extracts the list of grades of a particular student
     *
     * @param studentId unique identifier of student used for extracting the list of grades
     * @return the list of grades of a particular student
     */
    public List<Grade> getGrades(int studentId) {
        PreparedStatement stmt = null;
        List<Grade> gradeList = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(SQLQueries.GET_GRADES_FOR_STUDENT);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //logger.info(rs.getInt(1));
                Grade grade = new Grade(studentId, rs.getInt(1), rs.getString(2).charAt(0));
                gradeList.add(grade);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return gradeList;
    }

    /**
     * Extracts the list of requested courses of student
     *
     * @param studentId unique identifier of student used for extracting requested courses of student
     * @return the list of requested courses
     */
    public List<RequestedCourse> getRequestedCoursesOfStudent(int studentId) {
        PreparedStatement stmt = null;
        //PreparedStatement stmtFetchProf =  null;
        List<RequestedCourse> requestedCoursesList = new ArrayList<>();
        try {

            stmt = conn.prepareStatement(SQLQueries.GET_REQUESTED_COURSE_OF_STUDENT);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                int courseId = rs.getInt(1);
                int courseFlag = rs.getInt(2);

                RequestedCourse requestedCourse = new RequestedCourse(courseId, studentId, courseFlag == 1);
                //Course course = new Course(rs.getString(2),courseId,rs1.getInt(1),rs.getString(3),rs.getInt(4));
                requestedCoursesList.add(requestedCourse);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        //logger.info(requestedCoursesList.size());
        return requestedCoursesList;
    }


    public List<RequestedCourse> getAllRequestedCourses() {
        PreparedStatement stmt = null;
        List<RequestedCourse> requestedCourseList = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("Select * from requestedcourses");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt(1);
                int studentId = rs.getInt(2);
                int courseFlag = rs.getInt(3);
                RequestedCourse requestedCourse = new RequestedCourse(courseId, studentId, courseFlag == 1);
                logger.info(requestedCourse.getCourseId());
                requestedCourseList.add(requestedCourse);
            }
        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return requestedCourseList;
    }


}

