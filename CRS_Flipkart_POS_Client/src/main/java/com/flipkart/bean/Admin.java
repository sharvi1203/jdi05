package com.flipkart.bean;

/**
 * @author JEDI05
 */

/**
 * Admin bean class that extends User bean class
 */
public class Admin extends User {
    private int adminId;
    private String name;
    private String gender;

    public Admin(int adminId, String name, String gender) {
        this.adminId = adminId;
        this.name = name;
        this.gender = gender;
    }

    /**
     * @return the adminId
     */
    public int getAdminId() {

        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the admin name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the admin gender
     */
    public String getGender() {
        return gender;
    }


}
