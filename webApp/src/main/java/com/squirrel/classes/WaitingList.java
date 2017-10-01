package com.squirrel.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by el15ss on 16/02/17.
 */



/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class contains the details of the waiting list of a course taught by FDM.                                                                     *
 ******************************************************************************************************************************************************/

public class WaitingList
{
    private int id;
    private int userId;
    private int existingCourseId;
    private Timestamp timeAdded;

    public WaitingList(ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.userId=resultSet.getInt("userId");
            this.existingCourseId=resultSet.getInt("existingCourseId");
            this.timeAdded=resultSet.getTimestamp("timeAdded");
        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public WaitingList(){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getExistingCourseId() { return existingCourseId; }

    public void setExistingCourseId(int existingCourseId) { this.existingCourseId = existingCourseId; }

    public String getTimeAdded(String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(timeAdded);
    }

    public Timestamp getTimeAdded(){ return timeAdded; }

    public Timestamp setTimeAdded(String startTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            this.timeAdded=new Timestamp(formatter.parse(startTime).getTime());
        }
        catch (ParseException e){
            System.out.print("Not able to parse: incorrect format, expected yyyy-MM;dd HH:mm");
        }
        return null;
    }
}
