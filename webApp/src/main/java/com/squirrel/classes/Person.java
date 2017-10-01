package com.squirrel.classes;

/**
 * Created by el15ss on 16/02/17.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Attributes                                                                                                                               *
 * This class represents the users of the system and other persons involved in the FDM course process.                                                *
 ******************************************************************************************************************************************************/

public class Person
{
    private int id;
    private String username;
    private String password;
    private boolean admin;

    public Person(){}

    public Person(ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.username=resultSet.getString("username");
            this.password=resultSet.getString("password");
            this.admin=resultSet.getBoolean("admin");
        }
        catch(SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean getAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }
}
