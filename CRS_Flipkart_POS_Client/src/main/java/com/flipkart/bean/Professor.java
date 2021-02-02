package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * Professor bean class that extends User bean class
 */
public class Professor extends User {

    private String name;
    private int professorId;
    private String gender;

    public Professor(String name, int professorId, String gender) {
        this.name = name;
        this.professorId = professorId;
        this.gender = gender;
    }

    /**
     * @return the professor name
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
     * @return professor gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

}
