package com.flipkart.rest;

import com.flipkart.bean.Customer;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

@Path("/customerApi")
public class CustomerRest {

    @GET
    @Path("/getCustomer")
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces("text/plain")
    public String getCustomerDetails() {

        //  client --- service ---- dao ----> SQL
        Customer customer=new Customer();
        customer.setId(101);
        customer.setName("Flipkart");
        customer.setAddress("Bengaluru");
        JSONObject customerJson = new JSONObject();
        customerJson.put("Customer Id",customer.getId());
        customerJson.put("Customer Name", customer.getName());
        customerJson.put("Customer Address",customer.getAddress());

        return customerJson.toJSONString();
    }
    @POST
    @Path("/post/{customerId}/{Name}/{Address}/")
    @Produces("application/json")
    public Response createCustomer(@PathParam("customerId") int customerId,@PathParam("Name") String Name,@PathParam("Address") String Address) {
        System.out.println("hit post service");
        //call the business logic here and pass the customer object in business and operation is done
        Customer customer=new Customer();
        customer.setId(customerId);
        customer.setName(Name);
        customer.setAddress(Address);
        System.out.println("Customer ID " +customer.getId());
        System.out.println("Customer Name " +customer.getName());
        System.out.println("Customer Address "+customer.getAddress());
        String result = "Customer saved : " + customer;
        return Response.status(201).entity(customer.toString()).build();

    }
//    @POST
//    @Path("/post")
//    @Produces("application/json")
//    public String addMessage(){
//        return "POST works";
//    }

    @GET
    @Path("/getString")
    @Produces("text/plain")
    public String hello() {
        return "Sharvi!";
    }
}
