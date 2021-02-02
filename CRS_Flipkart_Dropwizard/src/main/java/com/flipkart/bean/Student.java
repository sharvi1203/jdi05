package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * Student bean class that extends User bean class
 */
public class Student extends User {

    private String name;
    private int studentId;
    private String gender;
    private String branch;

    private int semester;


    public Student(String name, int studentId, String gender, String branch, int semester) {

        this.name = name;
        this.studentId = studentId;
        this.gender = gender;
        this.branch = branch;
        this.semester = semester;

    }

    /**
     * @return the student name
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
     * @return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @return semester
     */
    public int getSemester() {
        return semester;
    }

}
