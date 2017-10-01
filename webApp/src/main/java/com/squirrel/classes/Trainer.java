package com.squirrel.classes;

/**
 * Created by el15ss on 16/02/17.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class contains the details of the trainers working for FDM.                                                                                   *
 ******************************************************************************************************************************************************/

public class Trainer
{
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Trainer(){}

    public Trainer(ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.name=resultSet.getString("name");
            this.address=resultSet.getString("address");
            this.email=resultSet.getString("email");
            this.phone=resultSet.getString("phone");
        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
