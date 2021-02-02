package com.flipkart.bean;
/**
 * @author JEDI05
 */

/**
 * Grade bean class
 */
public class Grade {

    int studentId;
    int courseId;
    char gradeLetter;

    public Grade(int studentId, int courseId, char gradeLetter) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeLetter = gradeLetter;
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
     * @return the gradeLetter
     */
    public char getGradeLetter() {
        return gradeLetter;
    }

    /**
     * @param gradeLetter the gradeLetter to set
     */
    public void setGradeLetter(char gradeLetter) {
        this.gradeLetter = gradeLetter;
    }
}

