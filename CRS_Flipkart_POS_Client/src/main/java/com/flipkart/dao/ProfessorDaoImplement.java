package com.flipkart.dao;

import com.flipkart.bean.Professor;
import com.flipkart.constant.SQLQueries;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * ProfessorDaoImplement class that implements ProfessorDoaInterface
 */
public class ProfessorDaoImplement implements ProfessorDaoInterface {

    private static Logger logger = Logger.getLogger(ProfessorDaoImplement.class);

    private static ProfessorDaoImplement singleton;

    // set up the DB connection
    Connection conn = DBUtils.getConnection();


    private ProfessorDaoImplement() {
        // pass
    }


    public static ProfessorDaoImplement getInstance() {
        if (singleton == null) {
            singleton = new ProfessorDaoImplement();
        }
        return singleton;
    }


    /**
     * Extracts professor object of Professor class
     *
     * @param username unique identifier of professor for obtaining professor object
     * @return professor object
     */
    //@Override
    public Professor getProfessor(String username) {

        PreparedStatement stmt = null;

        Professor professor = null;

        try {
            //professorId, name, username, gender
            stmt = conn.prepareStatement(SQLQueries.GET_PROFESSOR);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                professor = new Professor(rs.getString(2), rs.getInt(1), rs.getString(4));
            }

        } catch (SQLException se) {
            logger.info(se.getMessage());
        }

        return professor;
    }

    /**
     * Extracts professor object of Professor class
     *
     * @param professorId unique identifier of professor used for obtaining professor username
     * @return professor username
     */
    public String getProfessorUsername(int professorId) {
        PreparedStatement stmt = null;
        String username = null;
        try {
            stmt = conn.prepareStatement("SELECT username from professordetails where professorId = ?");
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                username = rs.getString(1);
            }

        } catch (SQLException se) {
            logger.info(se.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return username;
    }

    /**
     * Returns list of all professors from database
     *
     * @return
     */
    public List<Professor> getAllProfessors() {
        PreparedStatement stmt = null;
        List<Professor> professorList = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * from professordetails");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int professorId = rs.getInt(2);
                String name = rs.getString(3);
                String gender = rs.getString(4);
                Professor professor = new Professor(name, professorId, gender);
                professorList.add(professor);
            }
        } catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return professorList;
    }

}
