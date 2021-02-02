package com.flipkart.client;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.AdminDaoImplement;
import com.flipkart.dao.ProfessorDaoImplement;
import com.flipkart.dao.StudentDaoImplement;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of CRSApplication that shows options for login, registration and forgot password to the user
 */
public class CRSMainApplication {
    private static Logger logger = Logger.getLogger(CRSMainApplication.class);
    private static Scanner sc = new Scanner(System.in);

    public static boolean loggedIn = false;

    public static void main(String[] args) {

        logger.info(StatementConstants.Welcome);
        Boolean inApplication = true;
        do {
            logger.info(StatementConstants.Choice);
            logger.info(StatementConstants.Login);
            logger.info(StatementConstants.Registration);
            logger.info(StatementConstants.ForgotPassword);
            logger.info(StatementConstants.Exit);

            int n = Integer.parseInt(sc.nextLine());

            switch (n) {
                case 1: // Login
                    logger.info(StatementConstants.LoginToSystem);
                    CRSMainApplication.login();
                    break;

                case 2: // Register as a Student
                    logger.info(StatementConstants.StudentRegistration);
                    CRSMainApplication.registerStudent();
                    break;

                case 3: // Forgot Password
                    logger.info(StatementConstants.ForgotPasswordInSystem);
                    CRSMainApplication.forgotPassword();
                    break;

                default:
                    inApplication = false;
                    break;
            }

        } while (inApplication);
        logger.info(StatementConstants.ExitSystem);
    }

    /**
     * Uses username and password of users for logging into the system
     */
    public static void login() {

        logger.info(StatementConstants.Uname);
        String username = sc.nextLine();
        logger.info(StatementConstants.Pwd);
        String password = sc.nextLine();
        UserInterface userOperation = new UserOperation();

        String userType = userOperation.login(username, password);

        switch (userType.toLowerCase()) {
            case "a": // Admin Login
                logger.info("Welcome " + username + "!");
                logger.info(StatementConstants.LoggedAsAdmin);
                CRSMainApplication.loggedIn = true;
                //AdminDaoInterface adminDaoImpl = new AdminDaoImplement();
                AdminDaoImplement adminDaoImpl = AdminDaoImplement.getInstance();
                Admin admin = adminDaoImpl.getAdmin(username);
                AdminCRSClient adminCRSClient = new AdminCRSClient(admin.getAdminId());
                adminCRSClient.displayMenu();
                break;
            case "p": // Professor Login
                logger.info("Welcome " + username + "!");
                logger.info(StatementConstants.LoggedAsProfessor);
                CRSMainApplication.loggedIn = true;
                //ProfessorDaoInterface professorDaoImpl = new ProfessorDaoImplement();
                ProfessorDaoImplement professorDaoImpl = ProfessorDaoImplement.getInstance();
                Professor professor = professorDaoImpl.getProfessor(username);
                ProfessorCRSClient professorCRSClient = new ProfessorCRSClient(professor.getProfessorId());
                professorCRSClient.displayMenu();
                break;
            case "s": // Student Login
                logger.info("Welcome " + username + "!");
                logger.info(StatementConstants.LoggedAsStudent);
                CRSMainApplication.loggedIn = true;
                //StudentDaoImplement studentDaoImpl = new StudentDaoImplement();
                StudentDaoImplement studentDaoImpl = StudentDaoImplement.getInstance();
                Student student = studentDaoImpl.getStudent(username);
                StudentCRSClient studentCRSClient = new StudentCRSClient(student.getStudentId());
                studentCRSClient.displayMenu();
                break;
            default:
                logger.info(StatementConstants.InvalidUser); // maybe add an exception here
        }
    }

    /**
     * Logouts the system by setting loggedIn variable to false
     */

    public static void logout() {
        CRSMainApplication.loggedIn = false;
    }


    /**
     * Uses id, name, branch, gender, semester, username and password for Student Registration
     */
    public static void registerStudent() {
        logger.info(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.nextLine());
        logger.info(StatementConstants.SName);
        String name = sc.nextLine();
        logger.info(StatementConstants.Branch);
        String branch = sc.nextLine();
        logger.info(StatementConstants.Gender);
        String gender = sc.nextLine();
        if (gender.equalsIgnoreCase("m")) {
            gender = "male";
        } else {
            gender = "female";
        }
        logger.info(StatementConstants.Semester);
        int semester = Integer.parseInt(sc.nextLine());
        Student student = new Student(name, studentId, gender, branch, semester);

        logger.info(StatementConstants.Uname);
        String username = sc.nextLine();
        logger.info(StatementConstants.Pwd);
        String password = sc.nextLine();
        UserInterface userOperation = new UserOperation();
        Boolean studentRegistered = userOperation.registerStudent(student, username, password); // return 1 if account created, else return 0

        if (studentRegistered == false) {
            logger.info(StatementConstants.RegFailed);
        }

    }

    /**
     * Uses username for Forgot Password
     */
    public static void forgotPassword() {
        logger.info(StatementConstants.Uname);
        String username = sc.nextLine();
        UserInterface userOperation = new UserOperation();
        logger.info(userOperation.forgotPassword(username));
    }

}