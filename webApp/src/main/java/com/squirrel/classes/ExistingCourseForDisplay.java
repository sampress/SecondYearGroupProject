package com.squirrel.classes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ExistingCourseForDisplay {
   private int courseId;
   private int existingCourseId;
   private int classroomId;
   private String title;
   private String description;
   private String startTimeString;
   private String endTimeString;

   public ExistingCourseForDisplay(ResultSet rs) {
      try {
         this.courseId = rs.getInt("COURSEID");
         this.existingCourseId = rs.getInt("EXISTINGCOURSEID");
         this.title = rs.getString("title");
         this.classroomId = rs.getInt("CLASSROOMID");
         this.description = rs.getString("description");
         this.endTimeString=setStringTime(rs.getTimestamp("endTime"));
         this.startTimeString=setStringTime(rs.getTimestamp("startTime"));
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   public int getCourseId() {
      return courseId;
   }

   public void setCourseId(int courseId) {
      this.courseId = courseId;
   }

   public int getExistingCourseId() {
      return existingCourseId;
   }

   public void setExistingCourseId(int existingCourseId) {
      this.existingCourseId = existingCourseId;
   }

   public int getClassroomId() {
      return classroomId;
   }

   public void setClassroomId(int classroomId) {
      this.classroomId = classroomId;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String setStringTime(Timestamp time){
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      return formatter.format(time);
   }

   public String getStartTimeString(){
      return startTimeString;
   }

   public String getEndTimeString(){
      return endTimeString;
   }

}
