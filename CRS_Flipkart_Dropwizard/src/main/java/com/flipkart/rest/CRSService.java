package com.flipkart.rest;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

/**
 * @author JEDI05
 */

/**
 * CRS Service class that extends Application class
 */
public class CRSService extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new CRSService().run(args);
    }
    
    /**
     * Run method which is called by main method of our Application
     *
     * @param configuration of Application class
     * @param environment of Application class
     * @throws Exception when exception occurs
     */
    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        HelloWorldResource resource = new HelloWorldResource();
        environment.jersey().register(resource);

        StudentController studentController = new StudentController();
        environment.jersey().register(studentController);

        AdminController adminController = new AdminController();
        environment.jersey().register(adminController);

        ProfessorController professorController = new ProfessorController();
        environment.jersey().register(professorController);

    }
}
