package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * Course bean class
 */
public class Course {

    private String name;
    private int courseId;
    private int professorId;
    private String department;
    private int fees;

    public Course() {
    }

    public Course(String name, int courseId, int professorId, String department, int fees) {
        this.name = name;
        this.courseId = courseId;
        this.professorId = professorId;
        this.department = department;
        this.fees = fees;
    }

    /**
     * @return the course name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the professorId
     */
    public int getProfessorId() {
        return professorId;
    }

    /**
     * @param professorId the professorId to set
     */
    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return fees
     */
    public int getFees() {
        return fees;
    }

    /**
     * @param fees the fees to set
     */
    public void setFees(int fees) {
        this.fees = fees;
    }
}