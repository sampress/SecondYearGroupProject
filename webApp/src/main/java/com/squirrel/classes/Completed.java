package com.squirrel.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class stores the users profile information and completed courses.                                                                             *
 ******************************************************************************************************************************************************/


public class Completed
{
    private int id;
    private int username;
    private int existingid;
    private int course;
    private boolean finished;
    private Timestamp startTime;
    private Timestamp endTime;

    public Completed(ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.username=resultSet.getInt("username");
            this.course=resultSet.getInt("course");
            this.finished=resultSet.getBoolean("finished");
            this.existingid=resultSet.getInt("existingcourse");
            this.startTime=resultSet.getTimestamp("starttime");
            this.endTime=resultSet.getTimestamp("endtime");
        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public int getUsername(){ return username; }

    public void setUsername(int username) { this.username = username; }

    public int getCourse(){ return course; }

    public void setCourse(int course) { this.course = course; }

    public boolean isFinished(){ return finished; }

    public void setFinished(boolean finished) { this.finished = finished; }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

   public int getExistingid() {
      return existingid;
   }

   public void setExistingid(int existingid) {
      this.existingid = existingid;
   }
}
