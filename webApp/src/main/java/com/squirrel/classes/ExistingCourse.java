package com.squirrel.classes;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class contains the details of the users existing courses.                                                                                     *
 ******************************************************************************************************************************************************/


public class ExistingCourse
{
    private int id;
    private int courseId;
    private int classroomId;
    private int trainerId;
    private Timestamp startTime;
    private Timestamp endTime;

    public ExistingCourse(ResultSet resultsSet){
        try{
            this.id=resultsSet.getInt("id");
            this.courseId=resultsSet.getInt("courseId");
            this.classroomId=resultsSet.getInt("classroom");
            this.trainerId=resultsSet.getInt("trainerId");
            this.startTime=resultsSet.getTimestamp("startTime");
            this.endTime=resultsSet.getTimestamp("endTime");
        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }
    public ExistingCourse(){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getClassroomId() { return classroomId; }

    public void setClassroomId(int classroomId) { this.classroomId = classroomId; }

    public int getTrainerId() { return trainerId; }

    public void setTrainerId(int trainerId) { this.trainerId = trainerId; }

    public Timestamp getStartTime() { return startTime; }

    public String getStartTime(String pattern){
       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
         return formatter.format(startTime);
    }

    public void setStartTime(String startTime) {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       try{
          this.startTime=new Timestamp(formatter.parse(startTime).getTime());
       }
       catch (ParseException e){
          System.out.print("Not able to parse");
       }
    }

    public Timestamp getEndTime() { return endTime; }

    public String getEndTime(String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(endTime);
    }

    public void setEndTime(String endTime) {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       try{
          this.endTime=new Timestamp(formatter.parse(endTime).getTime());
       }
       catch (ParseException e){
          System.out.print("Not able to parse");
       }
    }

    public void setStartTime(Timestamp time){
       startTime=time;
    }
    public void setEndTime(Timestamp time){
       endTime=time;
    }
}
