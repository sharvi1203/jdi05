package com.flipkart.client;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Professor Client that gives different options to professor
 */
public class ProfessorCRSClient {
    private static Logger logger = Logger.getLogger(ProfessorCRSClient.class);
    private static int professorId;
    private ProfessorInterface professorOperation;
    private UserOperation userOperation = new UserOperation();

    public ProfessorCRSClient(int professorId) {
        this.professorId = professorId;
        professorOperation = new ProfessorOperation(this.professorId);
    }


    Scanner sc = new Scanner(System.in);

    /**
     * Display menu for the professor
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
                    viewAssignedCourses();
                    break;
                case 3:
                    viewStudents();
                    break;
                case 4:
                    assignGrades();
                    break;
                case 5:
                    showNotifications();
                    break;
                case 6:
                    return;
            }
        } while (true);
    }

    /**
     * Displays available choices to the professor
     */
    public void showChoices() {
        logger.info(StatementConstants.Choice);
        logger.info(StatementConstants.ViewCourses);
        logger.info(StatementConstants.ViewAssignedCourse);
        //logger.info(Statements.ChooseCourse);
        logger.info(StatementConstants.ViewStudents);
        logger.info(StatementConstants.AssignGrades);
        logger.info("5. " + StatementConstants.showNotification);
        logger.info("6. Logout");
    }

    /**
     * Displays courseId, name and department of all the courses from catalog
     */
    public void viewCourses() {
        List<Course> courses = professorOperation.viewCourses();
        for (Course course : courses) {
            logger.info("Course ID: " + course.getCourseId());
            logger.info("Course Name: " + course.getName());
            logger.info("Department: "+ course.getDepartment());
            logger.info("Course Fees: "+ course.getFees());
            logger.info("Professor Id: "+ course.getProfessorId());
            logger.info(" ");
        }
    }

    /**
     * Displays Course Id and Course name of the courses assigned to a professor
     */
    private void viewAssignedCourses() {
        List<Course> assignedCourseList = professorOperation.viewAssignedCourses();
        for (Course course : assignedCourseList) {
            logger.info("Course ID: " + course.getCourseId());
            logger.info("Course Name: " + course.getName());
            logger.info("Department: "+ course.getDepartment());
            logger.info("Course Fees: "+ course.getFees());
            logger.info(" ");

        }
    }


    /**
     * Uses Course Id to view Students registered in that course
     */
    public void viewStudents() {
        logger.info(StatementConstants.CIdToView);
        int courseId = Integer.parseInt(sc.nextLine());
        List<Student> studentList = null;
        try {
            studentList = professorOperation.viewStudents(courseId);
        } catch (CourseNotAccesibleException e) {
            logger.info(e.getMessage());
        }
        if (studentList == null) {
            return;
        }
        logger.info("The List of Students enrolled in " + courseId + " taught by you are: ");
        for (Student student : studentList) {
            logger.info("Student Id: " + student.getStudentId());
            logger.info("Name: " + student.getName());
            logger.info("Branch: " + student.getBranch());
            logger.info("Semester: " + student.getSemester());
            logger.info("Gender: " + student.getGender() + "\n");
        }
    }

    /**
     * Uses course id and student id to assign grade to the student enrolled in that course
     */
    private void assignGrades() {
        logger.info(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.next());
        logger.info(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.next());
        logger.info(StatementConstants.ScoreForCourse);
        char gradeLetter = sc.next().charAt(0);
        Grade newGrade = new Grade(studentId, courseId, gradeLetter);
        try {
            professorOperation.assignGrades(newGrade);
        } catch (CourseNotAccesibleException | StudentNotFoundException | RepeatException e) {
            logger.info(e.getMessage());
        }

    }

    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = professorOperation.getProfessorUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            logger.info(count + ". -> " + notification.getNotificationText());
        }
    }

}