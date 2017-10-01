package com.squirrel.classes;

import java.sql.ResultSet;
import java.sql.SQLException;



/******************************************************************************************************************************************************
 * Software Engineering Project                                                                                                                       *
 * Team Name - SQUIRREL                                                                                                                               *
 * Package - Classes                                                                                                                                  *
 * This class provides the details of the classrooms used by FDM for hosting courses.                                                                 *
 ******************************************************************************************************************************************************/

public class Classroom
{
    private int id;
    private String name;
    private String city;
    private String address;
    private int capacity;
    private String type;
    private boolean projector;
    private boolean studentComp;
    private boolean whiteboard;
    private boolean audioRecording;
    private boolean videoRecording;
    private boolean wheelchairAccess;
    private boolean listeningSystem;

    public Classroom( ){

    }

    public Classroom( ResultSet resultSet){
        try{
            this.id=resultSet.getInt("id");
            this.name=resultSet.getString("name");
            this.city=resultSet.getString("city");
            this.address=resultSet.getString("address");
            this.capacity=resultSet.getInt("capacity");
            this.type=resultSet.getString("type");
            this.projector=resultSet.getBoolean("projector");
            this.studentComp=resultSet.getBoolean("studentComp");
            this.whiteboard=resultSet.getBoolean("whiteboard");
            this.audioRecording=resultSet.getBoolean("audioRecording");
            this.videoRecording=resultSet.getBoolean("videoRecording");
            this.wheelchairAccess=resultSet.getBoolean("wheelchairAccess");
            this.listeningSystem=resultSet.getBoolean("listeningSystem");

        }
        catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public String getName(){ return name; }

    public void setName(String name) { this.name = name; }

    public String getCity(){ return city; }

    public void setCity(String city) { this.city = city; }

    public String getAddress(){ return address; }

    public void setAddress(String address) { this.address = address; }

    public int getCapacity(){ return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getType(){ return type; }

    public void setType(String type) { this.type = type; }

    public boolean getProjector(){ return projector; }

    public void setProjector(boolean projector) { this.projector = projector; }

    public boolean getStudentComp(){ return studentComp; }

    public void setStudentComp(boolean studentComp) { this.studentComp = studentComp; }

    public boolean getWhiteboard(){ return whiteboard; }

    public void setWhiteboard(boolean whiteboard) { this.whiteboard = whiteboard; }

    public boolean getAudioRecording(){ return audioRecording; }

    public void setAudioRecording(boolean audioRecording) { this.audioRecording = audioRecording; }

    public boolean getVideoRecording() { return videoRecording; }

    public void setVideoRecording(boolean videoRecording) { this.videoRecording = videoRecording; }

    public boolean getWheelchairAccess() { return wheelchairAccess; }

    public void setWheelchairAccess(boolean wheelchairAccess) { this.wheelchairAccess = wheelchairAccess; }

    public boolean getListeningSystem() { return listeningSystem; }

    public void setListeningSystem(boolean listeningSystem) { this.listeningSystem = listeningSystem; }
}
