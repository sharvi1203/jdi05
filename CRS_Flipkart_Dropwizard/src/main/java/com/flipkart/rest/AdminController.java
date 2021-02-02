package com.flipkart.rest;

import com.flipkart.bean.*;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Admin Controller class
 */
@Path("/admincontroller")
public class AdminController {
    private static Logger logger = Logger.getLogger(AdminController.class);
    AdminInterface adminOperation = new AdminOperation(1);
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    /**
     * Get method used to view courses present in catalog
     *
     * @return response object with the status and json string with course id and course name
     */
    @GET
    @Path("/course-catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses() {
        List<Course> courseList = adminOperation.viewCoursesInCatalog();
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
     * Post method that gathers required information to add new courses in the course catalog
     *
     * @return response object with the status and json string with message
     */
//    @POST
//    @Path("/add-course")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addCourse(Course course) {
//        String res = "";
//        try {
//            adminOperation.addCourseInCatalog(course);
//            res = "Successfully added: " + course;
//        } catch (RepeatException e) {
//            res = e.getMessage();
//        }
//        return Response.status(201).entity(res).build();
//    }
    @POST
    @Path("/add-course")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addCourse(
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @FormParam("courseId") int courseId,
            @DecimalMin(value = "201", message = "Course ID range starts from 201.")
            @DecimalMax(value = "299", message = "Course ID range till 299 only.")
            @NotNull(message = "ProfessorID cannot be null")
            @FormParam("professorId") int professorId,
            @Size(min = 1, max = 25, message = "size should be between 1 and 25")
            @NotNull(message = "courseName cannot be null")
            @FormParam("courseName") String courseName,
            @Size(min = 1, max = 25, message = "size should be between 1 and 20")
            @NotNull(message = "department cannot be null")
            @FormParam("department") String department,
            @DecimalMin(value = "100", message = "Minimum Fees is 100")
            @DecimalMax(value = "999", message = "Maximum Fees is 999")
            @NotNull(message = "Fees cant be null")
            @FormParam("fees") int fees
    ) {
        String res = "";
        try {

            Course newCourse = new Course(courseName, courseId, professorId, department, fees);
            adminOperation.addCourseInCatalog(newCourse);
            res = "Successfully added: " + newCourse.getName();
        } catch (RepeatException e) {
            res = e.getMessage();
        }
        return Response.status(201).entity(res).build();
    }

    /**
     * Delete method that uses course id to delete a particular course
     *
     * @return response object with the status and json string with message
     */
    @DELETE
    @Path("/delete-course/{courseId}")
    public Response deleteCourse(
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @PathParam("courseId") int courseId) {
        String result = adminOperation.dropCourseInCatalog(courseId);
        return Response.status(200).entity(result).build();
    }

    /**
     * Post method which uses professor details for adding professor
     *
     * @param professorId unique identifier of professor used for adding professor
     * @param name        professor name
     * @param gender      professor gender
     * @param username    professor username
     * @param password    professor passowrd
     * @return response object with the status and json string with message
     */
    @POST
    @Path("/add-professor")
    public Response addProfessor(
            @DecimalMin(value = "201", message = "Course ID range starts from 201.")
            @DecimalMax(value = "299", message = "Course ID range till 299 only.")
            @NotNull(message = "ProfessorID cannot be null")
            @FormParam("professorId") int professorId,
            @NotNull(message = "Name cannot be null")
            @Size(min = 3, max = 25, message = "Name should be between 3 and 25")
            @FormParam("name") String name,
            @NotNull(message = "Gender cannot be null")
            @FormParam("gender") String gender,
            @NotNull(message = "Username cannot be null")
            @FormParam("username") String username,
            @NotNull(message = "Password cannot be null")
            @FormParam("password") String password) {
        String res = "";
        Professor professor = new Professor(name, professorId, gender);
        res = adminOperation.addProfessor(professor, username, password);
        return Response.status(201).entity(res).build();
    }

    /**
     * Delete method that uses Professor username to drop him/her
     *
     * @param username used for dropping professor
     * @return response object with the status and json string with message
     */
    @DELETE
    @Path("/delete-professor/{username}")
    public Response dropProfessor(@NotNull(message = "Username cannot be null") @PathParam("username") String username) {
        String result = adminOperation.dropProfessor(username);
        return Response.status(200).entity(result).build();
    }

    /**
     * Get method that displays student details of all the students registered in a particular course
     *
     * @param courseId unique identifier of course for viewing students in that course
     * @return response object with the status and json string with student id, name, branch, semester and gender
     */
    @GET
    @Path("/view-student-in-course/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStudentsInCourse(
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @PathParam("courseId") int courseId) {
        List<Student> studentsInCourse = adminOperation.viewStudentInCourse(courseId);
        JSONArray jsonArray = new JSONArray();
        for (Student student : studentsInCourse) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Student Id", student.getStudentId());
            jsonObject.put("Name", student.getName());
            jsonObject.put("Branch", student.getBranch());
            jsonObject.put("Semester", student.getSemester());
            jsonObject.put("Gender", student.getGender());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray).build();
    }

    /**
     * Post method that uses course Id and student Id to assign a particular course to a student
     *
     * @param studentId unique identifier of student
     * @param courseId  unique identifier of course
     * @return response object with the status and json string with message
     */
    @POST
    @Path("/approve-student-in-course")
    public Response assignCourseToStudent(
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Students ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
            @FormParam("studentId") int studentId,
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
            @FormParam("courseId") int courseId) {
        String res = "";
        logger.info(studentId);
        logger.info(courseId);
        try {
            adminOperation.AssignStudentToCourse(studentId, courseId);
            res = "Assigned " + studentId + " to " + courseId;
        } catch (LimitExceededException e) {
            res = e.getMessage();
        }
        return Response.status(201).entity(res).build();
    }


    /**
     * @return response object with the status and json string of RequestedCourse objects
     */
    @GET
    @Path("/get-all-course-requests")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCourseRequests() {
        List<RequestedCourse> requestedCourseList = adminOperation.getAllRequestedCourses();
        JSONArray jsonArray = new JSONArray();
        for (RequestedCourse requestedCourse : requestedCourseList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("studentId", requestedCourse.getStudentId());
            jsonObject.put("courseId", requestedCourse.getCourseId());
            jsonObject.put("isPrimary", requestedCourse.isPrimary());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray).build();
    }

    /**
     * @return response object with the status and json string of Professor objects
     */
    @GET
    @Path("/get-all-professors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProfessors() {
        List<Professor> professorList = adminOperation.getAllProfessors();
        JSONArray jsonArray = new JSONArray();
        for (Professor professor : professorList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", professor.getProfessorId());
            jsonObject.put("Name", professor.getName());
            jsonObject.put("Gender", professor.getGender());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

    /**
     * @return response object with the status and json string of Student objects
     */
    @GET
    @Path("/get-all-students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        List<Student> studentList = adminOperation.getAllStudents();
        JSONArray jsonArray = new JSONArray();
        for (Student student : studentList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", student.getStudentId());
            jsonObject.put("Name", student.getName());
            jsonObject.put("Gender", student.getGender());
            jsonObject.put("Branch", student.getBranch());
            jsonObject.put("Semester", student.getSemester());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }


    /**
     * Get method that sends notification to a particular user
     *
     * @param username of user used for sending notifications
     * @return response object with the status and json string with notification id and content
     */
    @GET
    @Path("/notifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showNotifications(@NotNull(message = "Userame cannot be null") @QueryParam("username") String username) {
        logger.info(username);
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

}
