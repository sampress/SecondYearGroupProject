package com.squirrel.database;

import com.squirrel.classes.*;

import java.sql.*;
import java.util.LinkedList;

/**
 * Class that manages the database
 */
public class DatabaseManager {
   private Connection connection;
   private String driver;
   private String url;

   public DatabaseManager(String newDriver, String newURL) {
      this.driver = newDriver;
      this.url = newURL;
   }

   /*
   Function that initializes the database, must be run before trying
   to use any other methods from the class. Please close the database
   when finished using it (see: closeDatabase() method)
    */
   public void initializeDatabase() {
      try {
         Class.forName(driver);
         System.out.println("Driver loaded.");
         connection = DriverManager.getConnection(url);
         System.out.println("Connection established.");
      } catch (ClassNotFoundException error) {
         System.err.println("Failed to load JDBC driver!");
      } catch (SQLException error) {
         System.err.println("JDBC error!");
         DerbyUtils.traceErrors(error);
      }
   }

   /*
   Function returns the list of all the classrooms from the database
   in a LinkedList of Classroom objects
    */
   public LinkedList<Classroom> getClassroomList() {
      LinkedList<Classroom> classroomList = new LinkedList<>();
      String sql = "SELECT * FROM CLASSROOM";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            classroomList.add(new Classroom(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return classroomList;
   }

   /*
   Adds a trainer to the database if the trainer does not exist (id =0),
   otherwise just update the trainer which row matches the id
   The input is a object of type trainer.
    */
   public void updateTrainer(Trainer trainer) {
      String sql;
      if (trainer.getId() == 0) {
         sql = "INSERT INTO TRAINER"
                 + "(NAME,ADDRESS,EMAIL,PHONE) VALUES"
                 + "(?,?,?,?)";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainer.getName());
            statement.setString(2, trainer.getAddress());
            statement.setString(3, trainer.getEmail());
            statement.setString(4, trainer.getPhone());
            statement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      } else {
         sql = "UPDATE TRAINER SET NAME=?, ADDRESS=?,EMAIL=?, PHONE=?"
                 + "WHERE ID=?";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainer.getName());
            statement.setString(2, trainer.getAddress());
            statement.setString(3, trainer.getEmail());
            statement.setString(4, trainer.getPhone());
            statement.setInt(5, trainer.getId());
            statement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }
   }

   /*
   Function returns a trainer from the database based on his ID
   if there is no such trainer in the DB it returns an empty trainer
    */
   public Trainer getTrainerById(int id) {
      String sql = "SELECT * FROM TRAINER WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         ResultSet resultSet = statement.executeQuery(sql);
         if (resultSet.next()) {
            return new Trainer(resultSet);
         }
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return new Trainer();
   }

   /*
   Function returns a course from the database based on his ID
   if there is no such course in the DB it returns an empty course
    */
   public Course getCourseById(int id) {
      String sql = "SELECT * FROM COURSE WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         ResultSet resultSet = statement.executeQuery(sql);
         if (resultSet.next()) {
            return new Course(resultSet);
         }
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return new Course();
   }

   /*
   Function returns a list of courses with description or title
   matching the search time parameters. Uses toLowerCase() to
   remove the need to matching cases between search term and
   contents characters.
    */
   public LinkedList<Course> getCourseListByString(String searchterm) {
      String sql = "SELECT * FROM COURSE WHERE " +
              "LOWER(TITLE) LIKE LOWER('%" + searchterm + "%') OR " +
              "LOWER(DESCRIPTION) LIKE LOWER('%" + searchterm + "%')";
      LinkedList<Course> stringMatchList = new LinkedList<>();
      try (Statement statement = connection.createStatement()) {
         ResultSet resultSet = statement.executeQuery(sql);
         while (resultSet.next()) {
            stringMatchList.add(new Course(resultSet));
         }
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return stringMatchList;
   }

   //
   public LinkedList<Course> getUsersCourseList(String username) {
      LinkedList<Course> usersCourseList = new LinkedList<>();

      String sql = "SELECT * FROM COURSE " +
              "WHERE ID IN (SELECT COURSE FROM COMPLETED WHERE USERNAME " +
              "= (SELECT ID FROM PERSON WHERE PERSON.USERNAME='" + username + "'))";

      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            usersCourseList.add(new Course(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return usersCourseList;
   }

   public LinkedList<ExistingCourseForDisplay> getUsersCourseListForDisplay(String username){
      int uid = getIDFromUsername(username);
      LinkedList<ExistingCourseForDisplay> allCourses=getDisplayCourses("");
      LinkedList<Completed> completedByUser=getUsersCourseList(uid);
      LinkedList<ExistingCourseForDisplay> usersCourseListForDisplay = new LinkedList<>();
      for (ExistingCourseForDisplay displayCourse:allCourses) {
         for (Completed c:completedByUser){
            if(c.getExistingid()==displayCourse.getExistingCourseId()){
               usersCourseListForDisplay.add(displayCourse);
               break;
            }
         }

      }
      return usersCourseListForDisplay;
   }

   /*
   Function returns a classroom from the database based on its ID
   if there is no such classroom in the DB it returns an empty classroom
    */
   public Classroom getClassroomById(int id) {
      String sql = "SELECT * FROM CLASSROOM WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         ResultSet resultSet = statement.executeQuery(sql);
         if (resultSet.next()) {
            return new Classroom(resultSet);
         }
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return new Classroom();
   }

   /*
   Function returns a classroom from the database based on its ID
   if there is no such classroom in the DB it returns an empty classroom
    */
   public Classroom getClassroomBySId(String sClassroomId) {
      int id = Integer.parseInt(sClassroomId);
      String sql = "SELECT * FROM CLASSROOM WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         ResultSet resultSet = statement.executeQuery(sql);
         if (resultSet.next()) {
            return new Classroom(resultSet);
         }
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return new Classroom();
   }

   /*
   Function that adds a course to the database it also updates the prerequisites
   the input is an object of type course
    */
   public void updateCourse(Course course) {
      String sql;
      if (course.getId() == 0) {
         sql = "INSERT INTO COURSE " +
                 "(TITLE, DESCRIPTION, CAPACITY, DURATION) " +
                 "VALUES (?,?,?,?)";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getCapacity());
            statement.setInt(4, course.getDuration());
            statement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
         //get the id from the newly inserted course
         //and set it to the id of the course object
         sql = "SELECT * FROM COURSE WHERE TITLE='" + course.getTitle() + "'";
         try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            course.setId(resultSet.getInt(1));
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      } else {
         sql = "UPDATE COURSE SET TITLE=?, DESCRIPTION=?,CAPACITY=?, DURATION=?"
                 + "WHERE ID=?";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getCapacity());
            statement.setInt(4, course.getDuration());
            statement.setInt(5, course.getId());
            statement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }
      //add the prerequisites as well
      addPrerequisite(course);
   }


   /*
  Function that adds a specific classroom to the database,
  The input is a object of type trainer.
   */
   public void updateClassroom(Classroom classroom) {
      String sql;
      if (classroom.getId() == 0) {
         sql = "INSERT INTO CLASSROOM" +
                 "(NAME,CITY,ADDRESS,CAPACITY,TYPE,PROJECTOR,STUDENTCOMP," +
                 "WHITEBOARD,AUDIORECORDING,VIDEORECORDING,WHEELCHAIRACCESS," +
                 "LISTENINGSYSTEM) VALUES"
                 + "(?,?,?,?,?,?,?,?,?,?,?,?)";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classroom.getName());
            statement.setString(2, classroom.getCity());
            statement.setString(3, classroom.getAddress());
            statement.setInt(4, classroom.getCapacity());
            statement.setString(5, classroom.getType());
            statement.setBoolean(6, classroom.getProjector());
            statement.setBoolean(7, classroom.getStudentComp());
            statement.setBoolean(8, classroom.getWhiteboard());
            statement.setBoolean(9, classroom.getAudioRecording());
            statement.setBoolean(10, classroom.getVideoRecording());
            statement.setBoolean(11, classroom.getWheelchairAccess());
            statement.setBoolean(12, classroom.getListeningSystem());

            statement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      } else {
         sql = "UPDATE CLASSROOM SET NAME=?, CITY=?,ADDRESS=?, CAPACITY=?,"
                 + "TYPE=?, PROJECTOR=?, STUDENTCOMP=?, WHITEBOARD=?," +
                 " AUDIORECORDING=?, VIDEORECORDING=?, WHEELCHAIRACCESS=?, " +
                 "LISTENINGSYSTEM=? WHERE ID=?";
      }
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, classroom.getName());
         statement.setString(2, classroom.getCity());
         statement.setString(3, classroom.getAddress());
         statement.setInt(4, classroom.getCapacity());
         statement.setString(5, classroom.getType());
         statement.setBoolean(6, classroom.getProjector());
         statement.setBoolean(7, classroom.getStudentComp());
         statement.setBoolean(8, classroom.getWhiteboard());
         statement.setBoolean(9, classroom.getAudioRecording());
         statement.setBoolean(10, classroom.getVideoRecording());
         statement.setBoolean(11, classroom.getWheelchairAccess());
         statement.setBoolean(12, classroom.getListeningSystem());
         statement.setInt(13, classroom.getId());
         statement.executeUpdate();
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }

   }

   /**
    * Check whether the user exists in the DB and whether
    * the password entered is correct
    *
    * @param possibleUser - object of type Person
    * @return admin - if the user is an admin
    * delegate - if the user is a delegeate
    * nopass - the user exists but password is incorrect
    * nouser - the user does not exists in the database
    */
   public String validateUser(Person possibleUser) {
      String sql = "SELECT * FROM PERSON WHERE username='" + possibleUser.getUsername() + "'";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if (results.next()) {
            Person person = new Person(results);
            if (person.getPassword().equals(possibleUser.getPassword())) {
               if (person.getAdmin()) {
                  return "admin";
               } else {
                  return "delegate";
               }
            } else {
               return "nopass";
            }
         }
      } catch (SQLException e) {
         return "nouser";
      }
      return "nouser";
   }

   /*
   Function returns a linkedlist of a users completed courses from the
   database. This function is to be used when examining which deligate
   completed which course.
    */
   public LinkedList<Completed> getUsersCompletedList(int ID) {
      LinkedList<Completed> usersCompletedList = new LinkedList<>();
      String sql = "SELECT COMPLETED.ID, COMPLETED.USERNAME, COMPLETED.COURSE," +
              " COMPLETED.EXISTINGCOURSE, COMPLETED.FINISHED, EXISTINGCOURSE.STARTTIME," +
              "EXISTINGCOURSE.ENDTIME FROM COMPLETED " +
              "INNER JOIN EXISTINGCOURSE ON COMPLETED.EXISTINGCOURSE = EXISTINGCOURSE.ID" +
              " WHERE COMPLETED.USERNAME="+ID+" AND COMPLETED.FINISHED=TRUE";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            usersCompletedList.add(new Completed(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return usersCompletedList;
   }

   /*
   Function returns a linkedlist of all courses a user has currently
   taken or is taking from the database.
    */
   public LinkedList<Completed> getUsersCourseList(int ID) {
      LinkedList<Completed> usersCourseList = new LinkedList<>();
      String sql = "SELECT COMPLETED.ID, COMPLETED.USERNAME, COMPLETED.COURSE," +
              " COMPLETED.EXISTINGCOURSE, COMPLETED.FINISHED, EXISTINGCOURSE.STARTTIME," +
              "EXISTINGCOURSE.ENDTIME FROM COMPLETED " +
              "INNER JOIN EXISTINGCOURSE ON COMPLETED.EXISTINGCOURSE = EXISTINGCOURSE.ID" +
              " WHERE COMPLETED.USERNAME="+ID+" AND COMPLETED.FINISHED=FALSE";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            usersCourseList.add(new Completed(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return usersCourseList;
   }

   /*
   Function updates the existing course
    */
   public void updateExisitingCourse(ExistingCourse course){
      String sql="UPDATE EXISTINGCOURSE SET STARTTIME=?, ENDTIME=? WHERE ID=?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setTimestamp(1, course.getStartTime());
         statement.setTimestamp(2, course.getEndTime());
         statement.setInt(3, course.getId());
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }

   /*
   Function returns a object for display for the mobile app,
   it is a combination of the data from course and existing course
    */
   public LinkedList<ExistingCourseForDisplay> getDisplayCourses(String term){
      LinkedList<ExistingCourseForDisplay> list=new LinkedList<>();
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      String sql="SELECT COURSE.ID AS COURSEID, EXISTINGCOURSE.ID AS EXISTINGCOURSEID, " +
              "EXISTINGCOURSE.CLASSROOM AS CLASSROOMID, COURSE.TITLE, " +
              "COURSE.DESCRIPTION, EXISTINGCOURSE.STARTTIME, EXISTINGCOURSE.ENDTIME " +
              "FROM COURSE INNER JOIN EXISTINGCOURSE ON COURSE.ID = EXISTINGCOURSE.COURSEID " +
              "WHERE (LOWER(COURSE.TITLE) LIKE LOWER('%"+term+"%') OR LOWER(COURSE.DESCRIPTION) LIKE LOWER('%"+term+"%'))";
      try (Statement statement=connection.createStatement()){
         ResultSet results = statement.executeQuery(sql);

         while (results.next()){
               list.add(new ExistingCourseForDisplay(results));
         }

      }
      catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return list;
   }

   /*
   Function deletes the trainer from the database by specifying the ID
    */
   public void removeTrainer(int ID) {
      String sql = "DELETE FROM TRAINER WHERE ID=" + ID;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /*
   Function deletes the course from the database by specifying the ID
   First must delete all the prerequisites to other courses
    */
   public void removeCourse(int ID) {
      //delete all the courses prerequisites and
      //the other courses prerequisite which have this course
      String sql = "DELETE FROM PREREQUISITE WHERE COURSEID=" + ID +
              " OR PREREQUISITEID=" + ID;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      //delete the course itself
      sql = "DELETE FROM COURSE WHERE ID=" + ID;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /**
    * Function removes a scheduled course
    *
    * @param id of the scheduled course
    */
   public void removeScheduledCourse(int id) {
      String sql = "DELETE FROM EXISTINGCOURSE WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /**
   *Function deletes the classroom from the database by specifying the ID
   *
   * @param id of the scheduled course
   */
   public void removeClassroom(int id ) {
      String sql = "DELETE FROM CLASSROOM WHERE ID=" + id;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /*
   Add a scheduled course to the database
    */
   public void scheduleCourse(ExistingCourse course) {
      String sql = "INSERT INTO EXISTINGCOURSE " +
              "(COURSEID, CLASSROOM, STARTTIME, ENDTIME,TRAINERID) " +
              "VALUES (?,?,?,?,?)";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, course.getCourseId());
         statement.setInt(2, course.getClassroomId());
         statement.setTimestamp(3, course.getStartTime());
         statement.setTimestamp(4, course.getEndTime());
         statement.setInt(5, course.getTrainerId());
         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /**
    * Gets available classrooms depending on the inputted parameters
    *
    * @param startTime - the start time of the course
    * @param endTime   - the end time of the course
    * @param courseId  - the id of the course
    * @return a liked list of type classroom
    */
   public LinkedList<Classroom> getValidClassrooms(Timestamp startTime, Timestamp endTime, int courseId) {
      String sql = "SELECT * FROM CLASSROOM WHERE CAPACITY >= " +
              "(SELECT CAPACITY FROM COURSE WHERE COURSE.ID=" + courseId + ") AND " +
              "ID NOT IN (SELECT CLASSROOM FROM EXISTINGCOURSE " +
              "WHERE STARTTIME >'" + startTime + "' AND " +
              "ENDTIME < '" + endTime + "')";
      LinkedList<Classroom> classrooms = new LinkedList<>();
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            classrooms.add(new Classroom(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return classrooms;
   }

   /*
   Function gets all the trainers that are available in the given
   time period. It takes the startTime and endTime as a parameter
   returns a liked list of classrooms
    */
   public LinkedList<Trainer> getValidTrainer(Timestamp startTime, Timestamp endTime) {
      String sql = "SELECT * FROM TRAINER WHERE ID NOT IN (SELECT TRAINERID FROM EXISTINGCOURSE" +
              " WHERE STARTTIME >'" + startTime + "' AND" +
              " ENDTIME < '" + endTime + "')";
      LinkedList<Trainer> trainerLinkedList = new LinkedList<>();
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            trainerLinkedList.add(new Trainer(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return trainerLinkedList;
   }

   /*
   Function that adds the prerequisite courses to the database
   It uses an object of type course where the 3 prerequisites are stored
   First remove all the prerequisites from in the database based on the
   courses ID
    */
   public void addPrerequisite(Course course) {
      //remove all the prerequisites from db by course id
      String sql = "DELETE FROM PREREQUISITE WHERE COURSEID=" + course.getId();
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.executeUpdate();
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      //check if the first prerequisite is set
      if (course.getPrerequisiteId1() != 0) {
         sql = "INSERT INTO PREREQUISITE " +
                 "(COURSEID, PREREQUISITEID) " +
                 "VALUES (?,?)";
         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            //add the first prerequisite
            statement.setInt(1, course.getId());
            statement.setInt(2, course.getPrerequisiteId1());
            statement.executeUpdate();
            //check if the second prerequisite is set
            if (course.getPrerequisiteId2() != 0) {
               //add the second prerequisite
               statement.setInt(1, course.getId());
               statement.setInt(2, course.getPrerequisiteId2());
               statement.executeUpdate();
               //check if the third prerequisite is set
               if (course.getPrerequisiteId3() != 0) {
                  //add the third prerequisite
                  statement.setInt(1, course.getId());
                  statement.setInt(2, course.getPrerequisiteId3());
                  statement.executeUpdate();
               }
            }
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }
   }


   /*
  Function returns the course list from the database as a list
  of Course objects.
   */
   public LinkedList<Course> getCourseList() {
      LinkedList<Course> courseList = new LinkedList<>();
      String sql = "SELECT * FROM COURSE";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            courseList.add(new Course(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return courseList;
   }

   /*
   Function returns all the existing courses from the database.
   Note that this function returns the courses which had classrooms and
   trainers assigned to them.
    */
   public LinkedList<ExistingCourse> getExistingCourseList() {
      LinkedList<ExistingCourse> existingCourseList = new LinkedList<>();
      String sql = "SELECT * FROM EXISTINGCOURSE";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            existingCourseList.add(new ExistingCourse(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return existingCourseList;
   }

   /*
   Function returns a list of all the people stored in the database as
   a person object.
    */
   public LinkedList<Person> getPersonList() {
      LinkedList<Person> personList = new LinkedList<>();
      String sql = "SELECT * FROM PERSON";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            personList.add(new Person(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return personList;
   }

   /*
   Function returns a list of prerequisite objects as a linked list from
   the database.
    */
   public LinkedList<Prerequisite> getPrerequisiteList() {
      LinkedList<Prerequisite> prerequisiteList = new LinkedList<>();
      String sql = "SELECT * FROM PREREQUISITE";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            prerequisiteList.add(new Prerequisite(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return prerequisiteList;
   }

   public int cancelBooking(int existingId, String userName){
      String sql = "DELETE FROM COMPLETED WHERE USERNAME=(SELECT ID FROM " +
              "PERSON WHERE USERNAME='" +userName+"') AND " +
              "EXISTINGCOURSE ="+existingId ;
      try (Statement statement = connection.createStatement()) {
         statement.executeUpdate(sql);
         refreshWaitingList(existingId);
         return 1;
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return 0;
   }

   /*
   Function returns a list of all the trainers from the database
   as a linked list.
    */
   public LinkedList<Trainer> getTrainerList() {
      LinkedList<Trainer> trainerList = new LinkedList<>();
      String sql = "SELECT * FROM TRAINER";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            trainerList.add(new Trainer(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return trainerList;
   }

   /*
   Returns a linked list of waiting list objects from the database.
    */
   public LinkedList<WaitingList> getWaitingListList() {
      LinkedList<WaitingList> waitingListList = new LinkedList<>();
      String sql = "SELECT * FROM WAITINGLIST";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            waitingListList.add(new WaitingList(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return waitingListList;
   }

   public boolean addToWaitingList(String username, int excourseId){
      String sql="INSERT INTO WAITINGLIST " +
              "(USERID,EXISTINGCOURSEID) " +
              "VALUES ((SELECT ID FROM PERSON WHERE USERNAME = ?),?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, username);
         statement.setInt(2, excourseId);
         statement.executeUpdate();
         return true;

      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return false;
   }

   /**
    *  returns the number of people booked on an scheduled course by
    *  counting the list of records in completed that are linked to
    *  an existing course.
    *
    * */
   public int getNumOfPersonOnCourse(int cid) {
      String sql = "SELECT COUNT(USERNAME) as numoncourse FROM COMPLETED WHERE" +
              " EXISTINGCOURSE=" + cid;
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if (results.next()) {
            return results.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return -1;
   }

   /**
    * returns capacity of the course with the passed existing courseID
    *
    * */
   public int getCapacity(int cid) {
      String sql = "SELECT CAPACITY FROM COURSE WHERE ID= (SELECT COURSEID " +
              "FROM EXISTINGCOURSE WHERE ID=" + cid + ")";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if (results.next()) {
            return results.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }


      return -1;
   }

   /**
    * returns a boolean indicating whether a user has passed all the
    * required prerequisites og s certain course. This is done by
    * counting the users completed course with ids that match
    * prerequisites records associated with the passed course id, and
    * counting the amount of prerequistes associated wth the passed
    * course id and making sure these numbers match. True is returned
    * if satisfied .
    *
    * @param username - user for which prerequisites are being checked
    *
    * @param //courseId -
    *
    * */
   public boolean meetsPrerequisites(String username, int excourseId) {

      int i = numPrereqsForCourse(excourseId);
      int j = numUsersCompletedPrereqsForCourse(username, excourseId);
      if(i==j)return true;
      else return false;
   }

   public int numPrereqsForCourse(int excourseId){
      ResultSet results1;
      String sql1 = "SELECT COUNT(*) FROM PREREQUISITE " +
              "WHERE COURSEID =(SELECT COURSEID FROM EXISTINGCOURSE WHERE ID ="+excourseId+")";

      try (Statement statement = connection.createStatement()) {
         results1 = statement.executeQuery(sql1);

         if(results1.next()){
            return results1.getInt(1);
         }

      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return -1;
   }

   public int numUsersCompletedPrereqsForCourse(String username, int excourseId){
      int uid = getIDFromUsername(username);
      ResultSet results1;
      String sql1 = "SELECT COUNT(*) FROM COMPLETED WHERE USERNAME="+uid+" AND COURSE IN ( SELECT PREREQUISITEID FROM PREREQUISITE WHERE COURSEID=(SELECT COURSEID FROM EXISTINGCOURSE WHERE ID ="+excourseId+" )) AND FINISHED=TRUE";
      try (Statement statement = connection.createStatement()) {
         results1 = statement.executeQuery(sql1);
         if(results1.next()){
            return results1.getInt(1);
         }

      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return -1;
   }

   /**
    *    books user on course. Checks whether there is space on course.
    *    Check whether user is already on course. Checks if the user
    *    exists. Checks whether the user has passed the prerequisites
    *    required by the course.
    *
    *    @param - string username
    *    @param - string courseid
    *
    *    @return - int 1, successful booking
    *    @return - int 2, failed as course is full
    *    @return - int 3, failed as user is already on course
    *    @return - int 4, failed as user does not meet prerequisites
    *    @return - int 5, unknown failure to book
    *
    *
    * */
   public int bookStudentOnCourse(String username, int excourseId) {

      int numOnCourse = getNumOfPersonOnCourse(excourseId);
      int capacityOfCourse = getCapacity(excourseId);
      boolean userOnCouse = isUserOnCourse(username, excourseId);
      boolean isUser = doesUserExist(username);
      boolean meetsPrereqs = meetsPrerequisites(username, excourseId);
      if ((numOnCourse<capacityOfCourse)&&(!userOnCouse)&&isUser&&meetsPrereqs ) {//meets prereq
         String sql = "INSERT INTO COMPLETED " +
                 "(USERNAME,COURSE,EXISTINGCOURSE,FINISHED) " +
                 "VALUES ((SELECT ID FROM PERSON WHERE USERNAME = ?),(SELECT COURSEID FROM EXISTINGCOURSE WHERE ID=?),?,?)";

         try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setInt(2, excourseId);
            statement.setInt(3, excourseId);
            statement.setBoolean(4, false);
            statement.executeUpdate();
            return 1;

         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }
      else if((numOnCourse > capacityOfCourse)&&addToWaitingList(username, excourseId)){
         return 2;
      }
      else if (userOnCouse) return 3;
      else if (!meetsPrereqs) return 4;
      return 5;

   }

   public void refreshWaitingList(int existingId){
      String sql="SELECT * FROM WAITINGLIST WHERE EXISTINGCOURSEID="+existingId+" ORDER BY TIMEADDED";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if(results.next()){
            WaitingList waitingList=new WaitingList(results);
            sql="SELECT USERNAME FROM PERSON WHERE ID="+waitingList.getUserId();
            ResultSet newResultSet=statement.executeQuery(sql);
            if(newResultSet.next()){
               if(bookStudentOnCourse(newResultSet.getString(1),waitingList.getExistingCourseId())==1){
                  sql="DELETE FROM WAITINGLIST WHERE EXISTINGCOURSEID="+existingId+" AND" +
                          " USERID="+waitingList.getUserId();
                  statement.executeUpdate(sql);
               }
            }

         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
   }

   /**
    * check whether user exists, stops clients sending invalid
    * or empty usernames.
    *
    * */

   public boolean doesUserExist(String username) {
      String sql = "SELECT USERNAME FROM PERSON WHERE USERNAME='" + username+"'";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         return results.next();
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return false;
   }

   /**
    * Checks whether a user is already booked on a course or not.
    *
    * @param - string username
    * @param - int courseid
    *
    * @return true if user already is booked
    * @return false if user is not booked
    *
    * */
   public boolean isUserOnCourse(String username, int cid) {
      int sid = getIDFromUsername(username);
      LinkedList<Completed> stdntsCourses = getUsersCourseList(sid);
      int size = stdntsCourses.size();
      for (int i = 0; i < size; i++) {
         Completed c = stdntsCourses.get(i);
         if (c.getExistingid() == cid) {
            return true;
         }
      }
      return false;
   }

   /**
    *  returns users id associated with passed username.
    *
    * @param - username
    *
    * @return - int -1 if failed
    * @return - int id if successful
    * */
   public int getIDFromUsername(String username) {
      String sql = "SELECT ID FROM PERSON WHERE USERNAME='"+username+"'";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if (results.next()) {
            return results.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return -1;
   }

   /*
   Function closes the database connection
   Should be used to close the database after usage.
    */
   public void closeDatabase() {
      try {
         connection.close();
      } catch (SQLException error) {
         System.err.println("JDBC error!");
         DerbyUtils.traceErrors(error);
      } finally {
         DerbyUtils.shutdown("FDMdb");
      }
   }

   /**
    *  returns address field from training room record matching
    *  the passed classroom id
    *
    * */
   public String getAddressFromClassroomID(String sclassroomID) {
      int classroomID = Integer.parseInt(sclassroomID);
      String sql = "SELECT CLASSROOM FROM CLASSROOM WHERE ID ="+classroomID;
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         if (results.next()) {
            return results.getString(1);
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return "-1";


   }

   /**
    * returns a list of students(persons) on a scheduled course
    *
    * @param existingCourseId exisiting course ID
    *
    * @return list of students on scheduled course.
    * */
   public LinkedList<Person> studentsOnScheduledCourse( int existingCourseId){
      LinkedList<Person> persOnSceduledCourse = new LinkedList<>();
      String sql = "SELECT * FROM PERSON WHERE ID IN(SELECT ID FROM COMPLETED " +
              "WHERE EXISTINGCOURSE = "+existingCourseId+"  )";
      try (Statement statement = connection.createStatement()) {
         ResultSet results = statement.executeQuery(sql);
         while (results.next()) {
            persOnSceduledCourse.add(new Person(results));
         }
      } catch (SQLException e) {
         e.printStackTrace(System.err);
      }
      return persOnSceduledCourse;
   }
}