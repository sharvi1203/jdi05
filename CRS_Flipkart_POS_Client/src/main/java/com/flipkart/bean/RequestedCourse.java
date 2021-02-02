package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * RequestedCourse bean class
 */
public class RequestedCourse {

    int courseId;
    int studentId;
    boolean isPrimary;

    public RequestedCourse(int courseId, int studentId, boolean isPrimary) {
        this.courseId = courseId;
        this.isPrimary = isPrimary;
        this.studentId = studentId;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * @return true if primary course else false
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * @param primary the isPrimary to set
     */
    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
