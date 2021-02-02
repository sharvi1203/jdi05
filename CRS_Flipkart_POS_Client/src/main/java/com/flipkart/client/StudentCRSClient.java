package com.flipkart.client;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Notification;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.constant.StatementConstants;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Student Client that gives different options to student
 */
public class StudentCRSClient {

    private static Logger logger = Logger.getLogger(StudentCRSClient.class);
    private int studentId;
    StudentInterface studentOperation;
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    public StudentCRSClient(int studentId) {
        this.studentId = studentId;
        studentOperation = new StudentOperation(this.studentId);
    }

    /**
     * Display menus for the student
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
                    chooseCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    payFees();
                    break;
                case 5:
                    viewReportCard();
                    break;
                case 6:
                    viewRequestedCourses();
                    break;
                case 7:
                    viewAssignedCourses();
                    break;
                case 8:
                    showNotifications();
                    break;
                case 9:
                    return;
            }
        } while (true);
    }

    /**
     * Displays available choices to the student
     */
    public void showChoices() {
        logger.info(StatementConstants.Choice);
        logger.info(StatementConstants.ViewCourses);
        logger.info(StatementConstants.RequestCourse);
        logger.info(StatementConstants.DropACourse);
        logger.info(StatementConstants.PayFees);
        logger.info(StatementConstants.ViewReportCard);
        logger.info(StatementConstants.ViewRequestedCourses);
        logger.info(StatementConstants.ViewAssignedCourses);
        logger.info("8. " + StatementConstants.showNotification);
        logger.info("9. Logout");
    }

    /**
     * Displays courseId, name and department of all the courses from catalog
     */
    public void viewCourses() {
        List<Course> courses = studentOperation.viewCourses();
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
     * Uses course Id to select a course
     */
    public void chooseCourse() {
        logger.info(StatementConstants.EnterCId);
        int courseId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.IsPrimary);
        int isPrimaryInt = Integer.parseInt(sc.nextLine());
        boolean isPrimary = isPrimaryInt == 1;
        try {
            studentOperation.chooseCourse(courseId, isPrimary);
        } catch (RepeatException | LimitExceededException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Uses course Id to drop a course
     */
    public void dropCourse() {
        logger.info(StatementConstants.DropCId);
        int courseId = Integer.parseInt(sc.nextLine());
        try {
            studentOperation.dropCourse(courseId);
        } catch (CourseNotFoundException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Allows different Payment Methods for fees payment
     */
    public void payFees() {
        logger.info(StatementConstants.GetFees);
        sc.nextLine();
        int totalFees = studentOperation.getFees();
        logger.info(String.format("Your total amount is %d", totalFees));
        logger.info(StatementConstants.ChoosePaymentMethod);
        logger.info(StatementConstants.Methods);
        sc.nextLine();
        logger.info(StatementConstants.Pay);
        sc.nextLine();
        logger.info(StatementConstants.PaySuccess);
        studentOperation.notifyPayment(totalFees);
    }

    /**
     * Displays course Id and corresponding grade of a particular student
     */
    public void viewReportCard() {
        List<Grade> allGradeOfStudents = studentOperation.viewReportCard();

        for (Grade grade : allGradeOfStudents) {
            logger.info("Course Id: " + grade.getCourseId());
            logger.info("Grade:" + grade.getGradeLetter());
            logger.info(" ");
        }

    }

    /**
     * Displays all the requested courses of a student
     */
    public void viewRequestedCourses() {
        List<RequestedCourse> requestedCourseList = studentOperation.viewRequestedCourses();
        for (RequestedCourse requestedCourse : requestedCourseList) {
            logger.info("Course Id: " + requestedCourse.getCourseId());
            logger.info(requestedCourse.isPrimary() ? "Primary Choice" : "Secondary Choice");
            logger.info(" ");
        }
    }

    /**
     * Displays all the assigned courses of a student
     */
    public void viewAssignedCourses() {
        List<Course> allAssignedCourse = studentOperation.viewAssignedCourses();

        for (Course course : allAssignedCourse) {
            logger.info("Course Id: "+course.getCourseId());
            logger.info("Course Name: "+ course.getName());
            logger.info("Department: " + course.getDepartment());
            logger.info("Fees: "+ course.getFees());
            logger.info(" ");
        }
    }

    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = studentOperation.getStudentUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            logger.info(count + ". -> " + notification.getNotificationText());
        }
    }

}
