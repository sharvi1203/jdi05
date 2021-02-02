package com.flipkart.client;

import com.flipkart.bean.*;
import com.flipkart.constant.StatementConstants;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Admin Client that gives different options to admin
 */
public class AdminCRSClient {
    private static Logger logger = Logger.getLogger(AdminCRSClient.class);
    private int adminId;
    AdminInterface adminOperation = new AdminOperation(this.adminId);
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    public AdminCRSClient(int adminId) {
        this.adminId = adminId;
    }

    /**
     * Displays menu for the admin
     */
    public void displayMenu() {

        int choice;
        do {
            showChoices();
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    addNewCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    assignCourseToStudent();
                    break;
                case 5:
                    addProfessor();
                    break;
                case 6:
                    viewStudentsInCourse();
                    break;
                case 7:
                    showNotifications();
                    break;
                case 8:
                    getAllCourseRequests();
                    break;
                case 9:
                    getAllStudents();
                    break;
                case 10:
                    getAllProfessors();
                    break;
                case 11:
                    return;
            }
        } while (true);
    }


    /**
     * Displays available choices to the admin
     */
    void showChoices() {
        logger.info(StatementConstants.Choice);
        logger.info(StatementConstants.ViewCourses);
        logger.info(StatementConstants.AddNewCourse);
        logger.info(StatementConstants.DropCourse);
        logger.info(StatementConstants.AssignCourseToStudent);
        logger.info(StatementConstants.AddProfessor);
        logger.info("6. View Students in course");
        logger.info("7. Show Notifications");
        logger.info("8. View All Course Requests");
        logger.info("9. View all students");
        logger.info("10. View all professors");
        logger.info("11. Logout");
    }

    /**
     * Displays courses with their ids and names from course catalog
     */
    void viewCourses() {
        List<Course> courseList = adminOperation.viewCoursesInCatalog();
        for (Course course : courseList) {
            logger.info("Course ID: " + course.getCourseId());
            logger.info("Course Name: " + course.getName());
            logger.info("Department: "+ course.getDepartment());
            logger.info("Course Fees: "+ course.getFees());
            logger.info("Professor Id: "+ course.getProfessorId());
            logger.info(" ");
        }
    }

    /**
     * Gathers required information to add new courses in the course catalog
     */
    void addNewCourse() {
        logger.info(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.Cname);
        String courseName = sc.nextLine();
        logger.info(StatementConstants.Dept);
        String department = sc.nextLine();
        logger.info(StatementConstants.PId);
        int professorId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.Fees);
        int fees = Integer.parseInt(sc.nextLine());
        Course course = new Course(courseName, courseId, professorId, department, fees); // zero professor Id means not assigned since null can not be used
        try {
            adminOperation.addCourseInCatalog(course);
        } catch (RepeatException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Uses course id to delete a particular course
     */
    void deleteCourse() {
        logger.info(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        adminOperation.dropCourseInCatalog(courseId);
    }

    /**
     * Uses course Id and student Id to assign a particular course to a student
     */
    void assignCourseToStudent() {
        logger.info(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.nextLine());
        try {
            adminOperation.AssignStudentToCourse(studentId, courseId);
        } catch (LimitExceededException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Gathers details of professor and assigns role of Professor
     */
    void addProfessor() {
        logger.info(StatementConstants.PId);
        int professorId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.PName);
        String professorName = sc.nextLine();
        logger.info(StatementConstants.PGender);
        String professorGender = sc.nextLine();
        logger.info(StatementConstants.Uname);
        String username = sc.nextLine();
        logger.info(StatementConstants.Pwd);
        String password = sc.nextLine();
        Professor professor = new Professor(professorName, professorId, professorGender);
        adminOperation.addProfessor(professor, username, password);
    }

    /**
     * Uses Professor username to drop him/her
     */
    void dropProfessor() {
        logger.info(StatementConstants.Uname);
        String username = sc.nextLine();
        adminOperation.dropProfessor(username);
    }

    /**
     * Displays student details of all the students registered in a particular course
     */
    private void viewStudentsInCourse() {
        logger.info(StatementConstants.CIdToView);
        int courseId = Integer.parseInt(sc.nextLine());
        List<Student> studentsInCourse = adminOperation.viewStudentInCourse(courseId);

        logger.info("The List of Students enrolled in " + courseId);
        for (Student student : studentsInCourse) {
            logger.info("Student Id: " + student.getStudentId());
            logger.info("Name: " + student.getName());
            logger.info("Branch: " + student.getBranch());
            logger.info("Semester: " + student.getSemester());
            logger.info("Gender: " + student.getGender() + "\n");
        }
    }


    public void getAllCourseRequests() {
        List<RequestedCourse> requestedCourseList = adminOperation.getAllRequestedCourses();
        logger.info("All Course Requests : ");

        for (RequestedCourse requestedCourse : requestedCourseList) {
            logger.info("Student Id: " + requestedCourse.getStudentId());
            logger.info("Course Id: " + requestedCourse.getCourseId());
            logger.info(requestedCourse.isPrimary() ? "Primary Choice" : "Secondary Choice");
            logger.info(" ");
        }
    }


    public void getAllProfessors() {
        List<Professor> professorList = adminOperation.getAllProfessors();
        logger.info("Professor List:\n");
        for (Professor professor : professorList) {
            logger.info("Professor Id: " + professor.getProfessorId());
            logger.info("Name: " + professor.getName());
            logger.info("Gender: " + professor.getGender());
            logger.info(" ");
        }
    }


    public void getAllStudents() {
        List<Student> studentsList = adminOperation.getAllStudents();
        logger.info("Student List:\n");
        for (Student student : studentsList) {
            logger.info("Student Id: " + student.getStudentId());
            logger.info("Name: " + student.getName());
            logger.info("Gender: " + student.getGender());
            logger.info("Branch: " + student.getBranch());
            logger.info("Semester: " + student.getSemester());
            logger.info(" ");
        }
    }


    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = adminOperation.getAdminUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            logger.info(count + ". -> " + notification.getNotificationText());
        }
    }

    /**
     * Allows professor to select a particular course to teach
     */
    public void assignCourseToProfessor(){
        logger.info("Enter the course id");
        int courseId = Integer.parseInt(sc.nextLine());
        logger.info("Enter the professor id");
        int professorId = Integer.parseInt(sc.nextLine());
        try {
            adminOperation.assignProfessorToCourse(courseId, professorId);
        } catch (RepeatException e) {
            logger.info(e.getMessage());
        }
    }

}