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
 * This class contains the details of any prerequisites required for a course.                                                                        *
 ******************************************************************************************************************************************************/

public class Prerequisite
{
    private int id;
    private int courseId;
    private int prerequisiteId;

    public Prerequisite(ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.courseId=resultSet.getInt("courseId");
            this.prerequisiteId=resultSet.getInt("prerequisiteId");
        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public int getCourseId(){ return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getPrerequisiteId(){ return prerequisiteId; }

    public void setPrerequisiteId(int prerequisiteId) { this.prerequisiteId = prerequisiteId; }
}
