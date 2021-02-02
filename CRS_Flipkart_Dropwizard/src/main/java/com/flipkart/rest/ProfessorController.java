package com.flipkart.rest;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Professor Controller class
 */
@Path("/professorcontroller/{professorId}")
public class ProfessorController {

    /**
     * Get method used to view courses present in catalog
     *
     * @return json string with course id and course name
     */
    @GET
    @Path("/course-catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses(@PathParam("professorId")
                                @DecimalMin(value = "201", message = "Professor ID range starts from 201.")
                                @DecimalMax(value = "299", message = "Professor ID range till 299 only.")
                                @NotNull(message = "ProfessorID cannot be null")
                                        int professorId) {
        ProfessorInterface professorOperation = new ProfessorOperation(professorId);
        List<Course> courseList = professorOperation.viewCourses();
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
     * Get method used to view assigned courses to professor
     *
     * @return json string with course id and course name
     */
    @GET
    @Path("view-assigned-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAssignedCourses(@PathParam("professorId")
                                        @DecimalMin(value = "201", message = "Professor ID range starts from 201.")
                                        @DecimalMax(value = "299", message = "Professor ID range till 299 only.")
                                        @NotNull(message = "ProfessorID cannot be null")
                                                int professorId) {
        ProfessorInterface professorOperation = new ProfessorOperation(professorId);
        List<Course> assignedCourseList = professorOperation.viewAssignedCourses();
        JSONArray jsonArray = new JSONArray();
        for (Course course : assignedCourseList) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            jsonArray.add(courseJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

    /**
     * Get method used to view students enrolled in particular course
     *
     * @return json string with student id, name, gender, semester and branch
     */
    @GET
    @Path("view-student-in-course/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStudents(@PathParam("professorId")
                                 @DecimalMin(value = "201", message = "Professor ID range starts from 201.")
                                 @DecimalMax(value = "299", message = "Professor ID range till 299 only.")
                                 @NotNull(message = "ProfessorID cannot be null")
                                         int professorId,
                                 @PathParam("courseId")
                                 @DecimalMin(value = "101", message = "Course ID range starts from 101.")
                                 @DecimalMax(value = "199", message = "Course ID range till 199 only.")
                                 @NotNull(message = "CourseID cannot be null")
                                         int courseId) {
        ProfessorInterface professorOperation = new ProfessorOperation(professorId);
        List<Student> studentsInCourse = null;
        try {
            studentsInCourse = professorOperation.viewStudents(courseId);
        } catch (CourseNotAccesibleException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
        JSONArray jsonArray = new JSONArray();
        for (Student student : studentsInCourse) {
            JSONObject studentJson = new JSONObject();
            studentJson.put("Student Id", student.getStudentId());
            studentJson.put("Name", student.getName());
            studentJson.put("Gender", student.getGender());
            studentJson.put("Semester", student.getSemester());
            studentJson.put("Branch", student.getBranch());
            jsonArray.add(studentJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();

    }

    /**
     * Post method used for selecting courses
     *
     * @param courseId unique identifier of course
     * @param profId   unique identifier of professor
     * @return response object with status and json string with message
     * @throws RepeatException if course is already assigned to a Professor
     */
    @POST
    @Path("/choose-course/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response chooseCourses(@PathParam("courseId")
                                  @DecimalMin(value = "101", message = "Course ID range starts from 101.")
                                  @DecimalMax(value = "199", message = "Course ID range till 199 only.")
                                  @NotNull(message = "CourseID cannot be null")
                                          int courseId,
                                  @PathParam("professorId")
                                  @DecimalMin(value = "201", message = "Professor ID range starts from 201.")
                                  @DecimalMax(value = "299", message = "Professor ID range till 299 only.")
                                  @NotNull(message = "ProfessorID cannot be null")
                                          int profId) {
        String result = "Saved " + courseId + " to " + profId;
        ProfessorInterface professorOperation = new ProfessorOperation(profId);
        try {
            professorOperation.chooseCourse(courseId);
        } catch (RepeatException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(result).build();
    }

    /**
     * Post method used for assigning grades to student in particular course
     *
     * @param courseId    unique identifier of course
     * @param professorId unique identifier of professor
     * @param studentId   unique identifier of student
     * @param score       score of student in a course
     * @return response object with the status and json string with message
     * @throws RepeatException             if course is already graded
     * @throws CourseNotAccesibleException if course is not accessible
     * @throws StudentNotFoundException    if student is not enrolled in particular course
     */
    @POST
    @Path("/assign-grade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignGrades(
            @PathParam("professorId")
            @DecimalMin(value = "201", message = "Professor ID range starts from 201.")
            @DecimalMax(value = "299", message = "Professor ID range till 299 only.")
            @NotNull(message = "ProfessorID cannot be null")
                    int professorId,
            @FormParam("courseId")
            @DecimalMin(value = "101", message = "Course ID range starts from 101.")
            @DecimalMax(value = "199", message = "Course ID range till 199 only.")
            @NotNull(message = "CourseID cannot be null")
                    int courseId,
            @FormParam("studentId")
            @DecimalMin(value = "301", message = "Student's ID range starts from 300.")
            @DecimalMax(value = "399", message = "Students ID range till 399 only.")
            @NotNull(message = "StudentID cannot be null")
                    int studentId,
            @FormParam("score")
            @NotNull(message = "score cannot be null")
                    String score) {
        String result = "Grade added for" + courseId + "for " + studentId;
        ProfessorInterface professorOperation = new ProfessorOperation(professorId);
        char gradeLetter = score.charAt(0);
        Grade newGrade = new Grade(studentId, courseId, gradeLetter);
        try {
            professorOperation.assignGrades(newGrade);
        } catch (CourseNotAccesibleException e) {
            return Response.status(401).entity(e.getMessage()).build();
        } catch (StudentNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (RepeatException e) {
            return Response.status((400)).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(result).build();
    }

}