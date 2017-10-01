package com.squirrel.classes;

import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class provides the details of the courses taught by FDM.                                                                                      *
 ******************************************************************************************************************************************************/

public class Course{
    private int id;
    private String title;
    private String description;
    private int capacity;
    private int duration;
    private int prerequisiteId1;
    private int prerequisiteId2;
    private int prerequisiteId3;

    public Course(ResultSet resultSet) {
      try{
         this.id=resultSet.getInt("id");
         this.title =resultSet.getString("title");
         this.description = resultSet.getString("description");
         this.capacity=resultSet.getInt("capacity");
         this.duration=resultSet.getInt("duration");

      }
      catch (SQLException e){
         e.printStackTrace(System.err);
      }
   }

   public Course(){}

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getCapacity() {
      return capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public int getDuration() {
      return duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }

   public int getPrerequisiteId1() {
      return prerequisiteId1;
   }

   public void setPrerequisiteId1(int prerequisiteId1) {
      this.prerequisiteId1 = prerequisiteId1;
   }

   public int getPrerequisiteId2() {
      return prerequisiteId2;
   }

   public void setPrerequisiteId2(int prerequisiteId2) {
      this.prerequisiteId2 = prerequisiteId2;
   }

   public int getPrerequisiteId3() {
      return prerequisiteId3;
   }

   public void setPrerequisiteId3(int prerequisiteId3) {
      this.prerequisiteId3 = prerequisiteId3;
   }
}
