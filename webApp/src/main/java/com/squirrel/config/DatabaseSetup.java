package com.squirrel.config;

import com.squirrel.classes.ExistingCourse;
import com.squirrel.database.DatabaseManager;

import java.sql.Timestamp;
import java.util.LinkedList;

public class DatabaseSetup {
   public void setUpDatabase(){
      DatabaseManager dbManager = new DatabaseManager("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:FDMdb");
      dbManager.initializeDatabase();
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      LinkedList<ExistingCourse> courseList=dbManager.getExistingCourseList();
      for (ExistingCourse course:courseList) {
         Timestamp startTimestamp=course.getStartTime();
         Timestamp endTimestamp=course.getEndTime();
         startTimestamp.setMonth(timestamp.getMonth());
         startTimestamp.setYear(timestamp.getYear());
         endTimestamp.setMonth(timestamp.getMonth());
         endTimestamp.setYear(timestamp.getYear());
         dbManager.updateExisitingCourse(course);
      }

      dbManager.closeDatabase();
   }

}
