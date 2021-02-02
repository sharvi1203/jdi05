package com.flipkart.constant;

/**
 * @author JEDI05
 */

/**
 * StatementConstants class that contains all the statement constants of the Application
 */
public class StatementConstants {

    // CRSMainApplication Statements
    public static final String Welcome = "----- Welcome to our application ------";
    public static final String Choice = "Enter your choice: ";
    public static final String Login = "1. Login";
    public static final String Registration = "2. Registration";
    public static final String ForgotPassword = "3. Forgot Password";
    public static final String Exit = "Enter any other key to exit.\n";

    public static final String LoginToSystem = "------- Login -------";
    public static final String StudentRegistration = "------- Student Registration -------";
    public static final String ForgotPasswordInSystem = "------- Forgot Password -------";
    public static final String ExitSystem = "\n\n------- Good Bye! -------";

    public static final String Uname = "Enter Username:";
    public static final String Pwd = "Enter password:";
    public static final String LoggedAsAdmin = "You Sucessfully Logged in as admin";
    public static final String LoggedAsProfessor = "You Sucessfully Logged in as professor";
    public static final String LoggedAsStudent = "You Sucessfully Logged in as student";

    public static final String InvalidUser = "Invalid User Type";

    public static final String SId = "Enter student id: ";
    public static final String SName = "Enter student name: ";
    public static final String Branch = "Enter branch: ";
    public static final String Gender = "Enter gender: put 'm' for male and 'f' for female";
    public static final String Semester = "Enter semester: ";
    public static final String RegFailed = "Registration Failed :( \nTry again later!";
    public static final String TryAgain = "Try again with different username password!";
    public static final String TrialEnds = "Account creation trials ended. Please Try again later!";

    // AdminCRSClient Statements
    public static final String ViewCourses = "1. To view courses in catalog";
    public static final String AssignCourseToProfessor = "2. Assign course to a professor";
    public static final String AddNewCourse = "2. Add a new course to catalog";
    public static final String DropCourse = "3. Drop a course from catalog";
    public static final String AssignCourseToStudent = "4. Assign course to student";
    public static final String AddProfessor = "5. Add Professor to the University";
    public static final String RemoveProfessor = "6. Remove an existing professor";
    public static final String ViewStudentsInCourse = "7. View Students in course";
    public static final String Logout = "12. Logout";

    public static final String PId = "Enter Professor Id: ";
    public static final String PName = "Enter Professor Name: ";
    public static final String PGender = "Enter professor Gender - M for Male and F for Female";
    public static final String CId = "Enter course Id: ";
    public static final String Cname = "Enter course Name: ";
    public static final String Dept = "Enter department: ";
    public static final String Fees = "Enter fees: ";

    // ProfessorClient Statements
    public static final String ViewAssignedCourse = "2. View assigned course";
    //public static final String ChooseCourse = "3. Choose a course";
    public static final String ViewStudents = "3. View students of a course";
    public static final String AssignGrades = "4. Assign grades";
    public static final String CIdToTeach = "Enter courseId which you want to teach: ";
    public static final String CIdToView = "Enter courseId to view the students: ";
    public static final String ScoreForCourse = "Enter Score for course: ";

    // StudentClient Statements
    public static final String RequestCourse = "2. Request a course";
    public static final String DropACourse = "3. Drop a course";
    public static final String PayFees = "4. Pay fees";
    public static final String ViewReportCard = "5. View Report Card";
    public static final String ViewRequestedCourses = "6. View Requested Courses";
    public static final String ViewAssignedCourses = "7. View Assigned Courses";
    public static final String IsPrimary = "Is it primary? 0 for no, 1 for yes";
    public static final String EnterCId = "Please enter the courseId which you want to select: ";
    public static final String DropCId = "Please enter the courseId which you want to drop: ";
    public static final String GetFees = "Press any key to get fees";
    public static final String ChoosePaymentMethod = "Choose your Payment Method";
    public static final String Methods = "Press 1 for Netbanking, 2 for Card, 3 for UPI";
    public static final String Pay = "Press any key to pay";
    public static final String PaySuccess = "Payment Successful";

    // AdminDaoImplement Statements
    public static final String ValidCId = "Please enter a valid Course Id: ";
    public static final String ProfIdExists = "Professor Id already exists";
    public static final String UserExists = "User already exists";
    public static final String ProfDoesntExists = "Professor doesn't exist";
    public static final String ProfSuccess = "Successfully assigned Professor";

    // CourseDaoImplement Statements
    public static final String CourseSuccess = "Successfully assigned Course";

    // UserDaoImplement Statements
    public static final String NoUser = "Username not available :(";
    public static final String Accepted = "User Credentials Accepted!";
    public static final String NotAccepted = "Credentials not accepted :( --  Please Try again!";
    public static final String CheckMail = "Please check your mail for resetting your password";
    public static final String CorrectUName = "Please provide a correct username :(";

    // AdminOperation Statements
    public static final String CourseFull = "This course is full";
    public static final String CoursePresent = "Course already present in catalog";
    public static final String ProfAssigned = "A professor already assigned to this course";

    // ProfessorOperation Statements
    public static final String CourseNotAssigned = "Course not assigned to you";
    public static final String StudentNotFound = "Student not found";
    public static final String GradeAssigned = "Grade already assigned";
    public static final String CourseTaken = "Someone is already taking this course";

    // StudentOperation Statements
    public static final String AlreadyRequested = "You have already requested this course";
    public static final String PrimaryRequested = "4 primary course already requested";
    public static final String SecondaryRequested = "2 secondary course already requested";
    public static final String NoPrimary = "You have no more Primary courses remaining";
    public static final String NoSecondary = "You have no more Secondary courses remaining";
    public static final String NoStudent = "Student not present in course";

    // Show notifications
    public static final String showNotification = "Show Notifications";


}
