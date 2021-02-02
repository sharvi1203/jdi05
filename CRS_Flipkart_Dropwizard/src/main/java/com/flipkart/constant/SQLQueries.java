package com.flipkart.constant;

/**
 * @author JEDI05
 */

/**
 * SQLQueries class that contains all the SQL Queries used in the Application
 */
public class SQLQueries {

    // User Queries
    public static final String LOGIN = "SELECT role FROM User WHERE username = ? and password = ?";
    public static final String CHECK_USER_EXISTS = "SELECT * FROM User WHERE username = ?";
    public static final String REGISTER_USER = "INSERT into User(username, password, role) values (?,?,?)";
    public static final String DROP_USER = "DELETE FROM User WHERE username = ?";

    // Notification Queries
    public static final String ADD_NOTIFY = "INSERT INTO NotificationDetails(username, notificationText) VALUES(?,?)";
    public static final String FORGOT_PASS_TEXT = "Please check your mail for resetting your password";

    // Admin Queries
    public static final String GET_ADMIN = "SELECT adminId, name, username, gender FROM adminDetails where username = ?";
    public static final String REGISTER_ADMIN = "INSERT INTO adminDetails(adminId, username, name, gender) values(?, ?, ?, ?)";
    public static final String UPDATE_COURSE_PROFESSOR = "";
    public static final String ADD_COURSE = "INSERT INTO courseCatalog(courseId, name, dept, fees) values(?, ?, ?, ?)";
    public static final String DROP_COURSE = "DELETE FROM courseCatalog WHERE courseId = ?";
    public static final String ADD_PROF_TO_COURSE = "INSERT INTO assignProfessor(courseId, professorId) values(?, ?)";

    // Student Queries
    public static final String GET_STUDENT = "SELECT studentId, username, name, branch, gender, semester FROM studentdetails where username = ?";
    public static final String REGISTER_STUDENT = "INSERT INTO studentDetails(studentId, username, name, branch, gender, semester) values (?, ?, ?, ?, ?, ?)";
    public static final String ADD_COURSE_STUDENT = "";
    public static final String DROP_COURSE_STUDENT = "";
    public static final String GET_REGISTERED_COURSES = "";
    public static final String GET_GRADES = "";

    // Professor Queries
    public static final String CHECK_PROFESSOR_EXISTS = "SELECT * FROM professorDetails WHERE professorId = ?";
    public static final String GET_PROFESSOR = "SELECT professorId, name, username, gender FROM professordetails where username = ?";
    public static final String ADD_PROFESSOR = "INSERT into professorDetails(professorId, username, name, gender) values (?, ?, ?, ?)";
    public static final String DROP_PROFESSOR = "DELETE FROM professorDetails WHERE professorId = ?";
    public static final String GET_STUDENT_IN_COURSE = "";
    public static final String GRADE_STUDENTS = "";


    // Course Catalog
    public static final String VIEW_CATALOG = "SELECT courseId, name, dept, fees from coursecatalog";
    public static final String GET_COURSE = "SELECT courseId, name, dept, fees from coursecatalog where courseId = ?";
    public static final String GET_STUDENTS = "SELECT s.studentId, s.name, s.branch, s.gender, s.semester FROM assignedcourses a join studentdetails s on a.studentId = s.studentId where a.courseId = ?";
    public static final String GET_PROFESSOR_FOR_COURSE = "SELECT p.professorId, p.name, p.gender FROM assignedprofessor ap join professordetails p on ap.professorId = p.professorId where courseId = ?";
    public static final String ADD_REQUESTED_COURSE = "INSERT into requestedcourses(studentId, courseId, courseFlag) values(?, ?, ?)";
    public static final String ASSIGN_REQUESTED_COURSE = "INSERT into assignedcourses(studentId, courseId) values(?, ?)";
    public static final String ASSIGN_PROFESSOR = "INSERT into assignedprofessor(courseId, professorId) values(?, ?)";
    public static final String GET_COURSES_FOR_STUDENT = "SELECT c.courseId ,c.name ,c.dept, c.fees FROM coursecatalog c join assignedcourses a on c.courseId=a.courseId where a.studentId = ?";
    public static final String DROP_ASSIGNED_COURSE = "DELETE FROM assignedcourses where studentId = ? and courseId = ?";
    public static final String ASSIGN_GRADE_TO_STUDENT = "INSERT into studentgrade(studentId, courseId, grade) values(? , ? ,?)";
    public static final String GET_GRADES_FOR_STUDENT = "SELECT courseId,grade FROM  studentgrade where studentId = ?";
    public static final String GET_REQUESTED_COURSE_OF_STUDENT = "SELECT courseId, courseFlag from requestedcourses where studentId = ? ";
    public static final String GET_PROFESSOR_ID_FOR_COURSE = "SELECT professorId from assignedProfessor where courseId = ?";
}