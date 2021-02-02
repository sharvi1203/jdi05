/**
 *
 */
package com.flipkart.bean;

/**
 * @author Dell
 *
 */
public class Customer {
    private int id;
    private String address;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
