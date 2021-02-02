package com.flipkart.rest;

import com.flipkart.bean.*;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.URIReferenceException;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Student Controller class
 */
@Path("studentcontroller/{studentId}")
public class StudentController {

    private static Logger logger = Logger.getLogger(StudentController.class);

    /**
     * Get method used to view courses present in catalog
     *
     * @return json string with course id and course name
     */
    @GET
    @Path("/course-catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Student's ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int studentId
    ) {
        StudentInterface studentOperation = new StudentOperation(1);
        List<Course> courseList = studentOperation.viewCourses();
        JSONArray jsonArray = new JSONArray();
        for (Course course : courseList) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            courseJson.put("Course Department", course.getDepartment());
            courseJson.put("Course Fees", course.getFees());
            jsonArray.add(courseJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }


    /**
     * Get method used to view registered(i.e. assigned) courses of student
     *
     * @param id unique identifier of student
     * @return json string with course id and course name
     */
    @GET
    @Path("/view-registered-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegisteredCourses(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Student's ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int id) throws ValidationException {
        StudentInterface studentOperation = new StudentOperation(id);
        List<Course> registeredCourses = studentOperation.viewAssignedCourses();
        JSONArray jsonArray = new JSONArray();
        for (Course course : registeredCourses) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            jsonArray.add(courseJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

    /**
     * Get method used to view requested courses of student
     *
     * @param id unique identifier of student
     * @return json string with course id and flag isPrimary denoting whether course is primary or not
     */
    @GET
    @Path("/view-requested-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestedCourses(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Student's ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int id) {
        StudentInterface studentOperation = new StudentOperation(id);
        List<RequestedCourse> requestedCourse = studentOperation.viewRequestedCourses();
        JSONArray jsonArray = new JSONArray();
        for (RequestedCourse course : requestedCourse) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("isPrimary", course.isPrimary());
            jsonArray.add(courseJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }


    /**
     * Get method that provides report card of student
     *
     * @param id unique identifier of student
     * @return json string with course id and grade letter
     */
    @GET
    @Path("view-report-card")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewReportCard(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Student's ID range till 399 only.")
            @NotNull(message = "Student ID cannot be null")
            @PathParam("studentId") int id) {
        StudentInterface studentOperation = new StudentOperation(id);
        List<Grade> gradeList = studentOperation.viewReportCard();
        JSONArray jsonArray = new JSONArray();
        for (Grade grade : gradeList) {
            JSONObject gradeJson = new JSONObject();
            gradeJson.put("Course Id", grade.getCourseId());
            gradeJson.put("Grade Letter", grade.getGradeLetter());
            jsonArray.add(gradeJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

    /**
     * Post method that allows student to choose courses from the catalog
     *
     * @param studentId unique identifer of student
     * @param courseId  unique identifier of course
     * @param isPrimary flag denoting whether course can be chosen or not
     * @return response object with the status and json string with message
     * @throws RepeatException        if course is already taken by student
     * @throws LimitExceededException if student limit for a course is exceeded
     */
    @POST
    @Path("/request-course/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourses(
            @Valid
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Students ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int studentId,
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @FormParam("courseId") int courseId,
            @NotNull(message = "isTrue flag cannot be null")
            @FormParam("isPrimary") int isPrimary) throws ValidationException {
        String result = "Saved " + courseId + "to " + studentId;
        StudentInterface studentOperation = new StudentOperation(studentId);
        try {
            studentOperation.chooseCourse(courseId, isPrimary > 0 ? true : false);
        } catch (RepeatException | LimitExceededException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(result).build();
    }

    /**
     * Post method that calculates student fees based on assigned courses and then pays fees
     *
     * @param studentId unique identifier of student
     * @return response object with the status and json string with message
     * @throws RepeatException        if course fees is already paid
     * @throws LimitExceededException if student limit for a course is exceeded
     */
    @POST
    @Path("/pay-fees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response payFees(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Students ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @FormParam("studentId") int studentId) {
        StudentInterface studentOperation = new StudentOperation(studentId);
        int totalFees = studentOperation.getFees();
        String result = "Fees for Student ID " + studentId + " recorded amounting total " + totalFees;
        studentOperation.notifyPayment(totalFees);
        return Response.status(201).entity(result).build();
    }

    /**
     * Delete method that removes assigned courses of student
     *
     * @param studentId unique identifier of student
     * @param courseId  unique identifier of course
     * @return repsonse object with the status and json string with course id and student id
     * @throws URIReferenceException   if wrong URI referred
     * @throws CourseNotFoundException if student is not assigned that course
     */
    @DELETE
    @Path("/delete-course")
    public Response deleteAssignedCourse(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Students ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int studentId,
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @QueryParam("courseId") int courseId) throws URIReferenceException, CourseNotFoundException {
        StudentInterface studentOperation = new StudentOperation(studentId);
        studentOperation.dropCourse(courseId);
        return Response.status(200).entity("The course " + courseId + " for student " + studentId + " deleted").build();
    }


    /**
     * Get method that sends notification to a particular user
     *
     * @param username of user used to send notifications
     * @return response object with the status and json string with notification id and content
     */
    @GET
    @Path("/notifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showNotifications(
            @NotNull(message = "Username cannot be null")
            @QueryParam("username") String username) {
        logger.info(username);
        UserInterface userOperation = new UserOperation();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        JSONArray jsonArray = new JSONArray();
        for (Notification notification : notificationList) {
            count++;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Notification ID", count);
            jsonObject.put("Content", notification.getNotificationText());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }


    /**
     * Method to register student
     *
     * @param studentId
     * @param name
     * @param gender
     * @param semester
     * @param branch
     * @param username
     * @param password
     * @return
     */
    @POST
    @Path("/register-student")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addStudent(
            @DecimalMin(value = "301", message = "Course ID range starts from 301.")
            @DecimalMax(value = "399", message = "Course ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @PathParam("studentId") int studentId,
            @NotNull(message = "Name cannot be null")
            @Size(min = 3, max = 25, message = "Name should be between 3 and 25")
            @FormParam("name") String name,
            @NotNull(message = "Gender cannot be null")
            @FormParam("gender") String gender,
            @NotNull(message = "Semester cannot be null")
            @FormParam("semester") int semester,
            @NotNull(message = "Semester cannot be null")
            @FormParam("branch") String branch,
            @NotNull(message = "Username cannot be null")
            @FormParam("username") String username,
            @NotNull(message = "Password cannot be null")
            @FormParam("password") String password) {
        String res = "";
        Student student = new Student(name, studentId, gender, branch, semester);
        UserInterface userOperation = new UserOperation();
        Boolean studentRegistered = userOperation.registerStudent(student, username, password);
        res = studentRegistered ? "Student was Registered sucessfully!" : "Student not registered, please try again later";
        return Response.status(201).entity(res).build();
    }

}
