package com.squirrel.controller;

import com.squirrel.classes.*;
import com.squirrel.database.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

@Controller
public class GetController {

   //set up a dbManager object to be used by the methods by specifying the appropriate driver and url
   DatabaseManager dbManager = new DatabaseManager("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:FDMdb");


   /*
   When requesting the page '/index' return the page 'index.jsp' from WEB-INF.
   Prepare a person object to be incapsulated after the user enteres the username and password
    */
   @RequestMapping(value = "/index", method = RequestMethod.GET)
   public ModelAndView displayIndex() {
      return new ModelAndView("index", "command", new Person());
   }

   /*
   When requesting the page '/course' return the 'course.jsp' page from WEB-INF.
    */
   @RequestMapping(value = "/course", method = RequestMethod.GET)
   public ModelAndView displayCourse() {
      ModelAndView mv = new ModelAndView();

      //open the database and get all the courses and store them in a linked list
      dbManager.initializeDatabase();
      StringBuilder sb = new StringBuilder();
      LinkedList<Course> courseLinkedList=dbManager.getCourseList();

      //get all the courses that are scheduled
      StringBuilder sb2= new StringBuilder();
      LinkedList<ExistingCourse> existingCourseLinkedList=dbManager.getExistingCourseList();

      //loop through all the courses in the list and make them displayable in a tabular form
      //the table header is already set up in the 'course.jsp' page
      //add a edit and delete button to each course
      sb.append("<tr>");
      for (Course course:courseLinkedList) {
         sb.append("<tr><td>"+course.getTitle()+"</td><td>"+course.getDescription()+"</td>");
         sb.append("<td>"+course.getCapacity()+"</td><td>"+course.getDuration()+"</td>");
         sb.append("<td><button class='btn btn-danger btn-delete-course' id='"+course.getId()+"'>Delete</button>");
         sb.append("<button class='btn btn-info btn-edit' id='"+course.getId()+"'>Edit</button></td></tr>");
      }

      //loop through all the scheduled courses in the list and make the dispayable in tabular form
      //the table header is already set up in 'course.jsp'
      sb2.append("<tr>");
      for (ExistingCourse existingCourse:existingCourseLinkedList){
         sb2.append("<tr><td>"+dbManager.getCourseById(existingCourse.getCourseId()).getTitle()+"</td><td>"+existingCourse.getStartTime("dd/MM/yy hh:mm")+"</td>");
         sb2.append("<td>"+existingCourse.getEndTime("dd/MM/yy hh:mm")+"</td><td>"+dbManager.getClassroomById(existingCourse.getClassroomId()).getName()+"</td>");
         sb2.append("<td>"+dbManager.getTrainerById(existingCourse.getTrainerId()).getName()+"</td><td>");
         sb2.append(dbManager.getCapacity(existingCourse.getId())-dbManager.getNumOfPersonOnCourse(existingCourse.getId()));
         sb2.append("</td><td><button class='btn btn-info btn-info-person' id='"+existingCourse.getId()+"'>Info</button>");
         sb2.append("<button class='btn btn-danger btn-delete-scheduled' id='"+existingCourse.getId()+"'>Delete</button></td></tr>");
      }
      //close the database connection and display the page
      dbManager.closeDatabase();
      mv.addObject("firstTable", sb.toString());
      mv.setViewName("course");
      //to set the scheduled courses
      mv.addObject("secondTable",sb2.toString());

      return mv;
   }

   /*
   When requesting the page '/classroom' return the 'classroom.jsp' page from WEB-INF.
    */
   @RequestMapping(value = "classroom", method = RequestMethod.GET)
    public ModelAndView displayClassroom() {
        ModelAndView mv = new ModelAndView();

      //open the database and get all the classrooms and store them in a linked list
        dbManager.initializeDatabase();
        StringBuilder sb = new StringBuilder();
        LinkedList<Classroom> classroomLinkedList=dbManager.getClassroomList();

      //loop through all the classroom in the list and make them displayable in a tabular form
      //the table header is already set up in the 'classroom.jsp' page
      //add a edit and delete button to each classroom
        sb.append("<tr>");
        for (Classroom classroom:classroomLinkedList) {
            sb.append("<tr data-id='"+classroom.getId()+"'><td>"+classroom.getName()+"</td><td>"+classroom.getCity()+"</td>");
            sb.append("<td>"+classroom.getAddress()+"</td><td>"+classroom.getCapacity()+"</td><td>"+classroom.getType()+"</td>");
            sb.append("<td>"+classroom.getProjector()+"</td><td>"+classroom.getStudentComp()+"</td><td>"+classroom.getWhiteboard()+"</td>");
            sb.append("<td>"+classroom.getAudioRecording()+"</td><td>"+classroom.getVideoRecording()+"</td>");
            sb.append("<td>"+classroom.getWheelchairAccess()+"</td><td>"+classroom.getListeningSystem()+"</td>");
            sb.append("<td><button class='btn btn-danger btn-delete' data-name='"+classroom.getName()+"' id='"+classroom.getId()+"'>Delete</button>");
            sb.append("<button class='btn btn-info btn-edit' data-name='"+classroom.getName()+"' id='"+classroom.getId()+"'>Edit</button></td></tr>");
        }

        //close the database connection and display the page
        dbManager.closeDatabase();
        mv.addObject("table", sb.toString());
        mv.setViewName("classroom");

        return mv;
    }

    /*
    When requesting the page '/userhomepage' return the 'userhomepage.jsp' page from WEB-INF.
     */
   @RequestMapping(value = "/userhomepage", method = RequestMethod.GET)
   public ModelAndView displayUserHomepage() {
      return new ModelAndView("userhomepage");
   }

   /*
   When requesting the page '/trainer' return the 'trainer.jsp' page from WEB-INF.
    */
   @RequestMapping(value = "/trainer", method = RequestMethod.GET)
   public ModelAndView displayTrainer() {
      ModelAndView mv = new ModelAndView();

      //open the database and get all the trainers and store them in a linked list
      dbManager.initializeDatabase();
      StringBuilder sb = new StringBuilder();
      LinkedList<Trainer> trainerLinkedList=dbManager.getTrainerList();

      //loop through all the trainers in the list and make them displayable in a tabular form
      //the table header is already set up in the 'trainer.jsp' page
      //add a edit and delete button to each trainer
      sb.append("<tr>");
      for (Trainer trainer:trainerLinkedList) {
         sb.append("<tr data-id='"+trainer.getId()+"'><td>"+trainer.getName()+"</td><td>"+trainer.getAddress()+"</td>");
         sb.append("<td>"+trainer.getEmail()+"</td><td>"+trainer.getPhone()+"</td>");
         sb.append("<td><button class='btn btn-danger btn-delete' data-name='"+trainer.getName()+"' id='"+trainer.getId()+"'>Delete</button>");
         sb.append("<button class='btn btn-info btn-edit' data-name='"+trainer.getName()+"' id='"+trainer.getId()+"'>Edit</button></td></tr>");
      }

      //close the database and display the page
      dbManager.closeDatabase();
      mv.addObject("table", sb.toString());
      mv.setViewName("trainer");

      return mv;
   }

   /*
    When requesting the page '/addtrainer' return the 'addtrainer.jsp' page from WEB-INF.
    Note down the possible {idvalue} extension that may appear. This extension is used to
    identify the trainer by his id if the admin wants to edit him. Otherwise there will
    not be an id extension
     */
   @RequestMapping(value="/addtrainer{idvalue}", method=RequestMethod.GET)
   public ModelAndView displayAddTrainer(@PathVariable(value = "idvalue") String id){

      //initialise the database and try to parse the sent id
      dbManager.initializeDatabase();
      int ID=0;
      try{
        ID=Integer.parseInt(id);
      }
      catch (Exception e){
         //there was no id sent
      }

      //get the trainer whose id was sent, or get a empty trainer object
      //if the id was not sent ID=0
      //close the database and send the trainer object so that the 'trainer.jsp' form
      //can be filled with the information
      Trainer trainer=dbManager.getTrainerById(ID);
      dbManager.closeDatabase();
      return new ModelAndView("addtrainer", "command",trainer);
   }

   /*
    When requesting the page '/createcourse' return the 'createcourse.jsp' page from WEB-INF.
    Note down the possible {idvalue} extension that may appear. This extension is used to
    identify the course by its id if the admin wants to edit him. Otherwise there will
    not be an id extension.
     */
   @RequestMapping(value="/createcourse{idvalue}", method=RequestMethod.GET)
   public ModelAndView displayCreateCourse(@PathVariable(value = "idvalue") String id){

      //initialise the database and try to parse the sent id
      dbManager.initializeDatabase();
      int ID=0;
      try{
         ID=Integer.parseInt(id);
      }
      catch (Exception e){
         //there was no id sent
      }

      //get all the courses and store them in a hashmap
      HashMap< Integer, String > courseList = new HashMap<>();
      LinkedList<Course> courses=dbManager.getCourseList();
      for(Course courseInList:courses){
         courseList.put(courseInList.getId(),courseInList.getTitle());
      }

      //get the course whose id was sent, or get a empty course object
      //if the id was not sent ID=0
      //close the database and send the course object so that the 'course.jsp' form
      //can be filled with the information
      Course course=dbManager.getCourseById(ID);
      dbManager.closeDatabase();

      ModelAndView mv=new ModelAndView("createcourse", "command", course);
      //set the view attributes and return
      mv.addObject("courselist", courseList);
      return mv;
   }

   /*
   When requesting the page '/schedulecourse' return the 'schedulecourse.jsp' page
   Send a schedulecourse object so that the admin can fill in the required information in the form.
    */
   @RequestMapping(value="/schedulecourse{idvalue}", method=RequestMethod.GET)
   public ModelAndView displayScheduleCourse(@PathVariable(value = "idvalue") String id){

      //initialise the database and try to parse the sent id
      dbManager.initializeDatabase();
      int ID=0;
      try{
         ID=Integer.parseInt(id);
      }
      catch (Exception e){
         //there was no id sent
      }

      //get all the courses and store them in a hashmap
      HashMap< Integer, String > courseList = new HashMap<>();
      LinkedList<Course> courses=dbManager.getCourseList();
      for(Course courseInList:courses){
         courseList.put(courseInList.getId(),courseInList.getTitle());
      }

      //get the ExistingCourse by the sent id or default (blank) one
      //will be implemented later
      //Course course=dbManager.getCourseById(ID);
      dbManager.closeDatabase();

      //set the view attributes and return
      ModelAndView mv=new ModelAndView("schedulecourse", "command",new ExistingCourse());
//      mv.addObject("classroomlist", classroomList);
//      mv.addObject("trainerlist",trainerList);
      mv.addObject("courselist", courseList);
      return mv;
   }


   /*
     When requesting the page '/addclassroom' return the 'addclassroom.jsp' page from WEB-INF.
     Send a classroom object so that the user can fill in the required information in the form.
      */
   @RequestMapping(value="/addclassroom{idvalue}", method=RequestMethod.GET)
   public ModelAndView displayAddClassroom(@PathVariable(value = "idvalue") String id){

      //init db and try to parse the sent id
      dbManager.initializeDatabase();
      int ID=0;
      try{
         ID=Integer.parseInt(id);
      }
      catch (Exception e){
         //no id sent
      }
      //get the course whose id was sent, or get a empty course object
      //if the id was not sent ID=0
      //close the database and send the course object so that the 'course.jsp' form
      //can be filled with the information
      Classroom classroom=dbManager.getClassroomById(ID);
      dbManager.closeDatabase();
      return new ModelAndView("addclassroom", "command",classroom);
   }

   /*
   When requesting the page '/trainer/data' return the list of all trainers as JSON data
   */
   @ResponseBody
   @RequestMapping(value="/trainer/data")
   public LinkedList<Trainer> showTrainer(){
      dbManager.initializeDatabase();
      LinkedList<Trainer> trainerList=dbManager.getTrainerList();
      dbManager.closeDatabase();
      return trainerList;
   }

   @ResponseBody
   @RequestMapping(value="/existingcourse/data")
   public LinkedList<ExistingCourse> showExistingCourse(){
      dbManager.initializeDatabase();
      LinkedList<ExistingCourse> existingCourseList=dbManager.getExistingCourseList();
      dbManager.closeDatabase();
      return existingCourseList;
   }


   /*
   When requesting the page '/course/duration' return the duration of the course
   */
   @ResponseBody
   @RequestMapping(value="/course/duration")
   public int getDuration(@RequestParam("id") String id){
      dbManager.initializeDatabase();
      int ID=Integer.parseInt(id);
      Course course=dbManager.getCourseById(ID);
      dbManager.closeDatabase();
      return course.getDuration();
   }

   /*
   When requesting the page '/course/addtime return the time
   plus the duration specified
   */
   @ResponseBody
   @RequestMapping(value="/course/addtime")
   public String addTime(@RequestParam("duration") String DURATION, @RequestParam("time") String time){

      int duration=Integer.parseInt(DURATION);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      SimpleDateFormat secondFormatter = new SimpleDateFormat("yyyy-MM-dd");

      try{
         Timestamp newTime=new Timestamp(formatter.parse(time).getTime());
         Calendar cal = Calendar.getInstance();
         cal.setTime(newTime);
         cal.add(Calendar.DAY_OF_WEEK, duration);
         newTime.setTime(cal.getTime().getTime());
         return formatter.format(newTime)+","+secondFormatter.format(newTime)+" 23:59";
      }
      catch (ParseException e){
         System.err.println("Parse Fail");
      }
      return "";
   }

   /*
   Return a list of all the available classrooms in the given time and depending on the capacity
    */
   @ResponseBody
   @RequestMapping(value="/course/fillclassroom")
    public HashMap<Integer, String> fillClassroom(@RequestParam("startTime") String STARTtime, @RequestParam("endTime") String ENDtime, @RequestParam("id") String id){

      //parse the time into Timestamps
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      try{
         int courseid=Integer.parseInt(id);
         Timestamp startTime=new Timestamp(formatter.parse(STARTtime).getTime());
         Timestamp endTime=new Timestamp(formatter.parse(ENDtime).getTime());
         //get the non taken classrooms and store them in a hashmap
         dbManager.initializeDatabase();
         HashMap< Integer, String > classroomList = new HashMap<>();
         LinkedList<Classroom> classrooms=dbManager.getValidClassrooms(startTime,endTime,courseid);
         dbManager.closeDatabase();
         for(Classroom classroomInList:classrooms){
            classroomList.put(classroomInList.getId(),classroomInList.getName());
         }
         return classroomList;
      }
      catch (ParseException e){
         System.err.println("Parse Fail");
      }
      return new HashMap<>();
   }

   /*
   Return a list of all the available classrooms in the given time and depending on the capacity
    */
   @ResponseBody
   @RequestMapping(value="/course/filltrainer")
   public HashMap<Integer, String> fillTrainer(@RequestParam("startTime") String STARTtime, @RequestParam("endTime") String ENDtime){

      //parse the time into Timestamps
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      try{
         Timestamp startTime=new Timestamp(formatter.parse(STARTtime).getTime());
         Timestamp endTime=new Timestamp(formatter.parse(ENDtime).getTime());
         //get the non taken classrooms and store them in a hashmap
         dbManager.initializeDatabase();
         HashMap< Integer, String > trainerList = new HashMap<>();
         LinkedList<Trainer> trainerLinkedList=dbManager.getValidTrainer(startTime,endTime);
         dbManager.closeDatabase();
         for(Trainer trainerInList:trainerLinkedList){
            trainerList.put(trainerInList.getId(),trainerInList.getName());
         }
         return trainerList;
      }
      catch (ParseException e){
         System.err.println("Parse Fail");
      }
      return new HashMap<>();
   }

   //when requesting the page /usercompleted return list of courses completed by user
   @ResponseBody
   @RequestMapping(value="/usercompleted", method=RequestMethod.GET)
   public LinkedList<Completed> usersCompleted(@RequestParam("ID") String id){
      dbManager.initializeDatabase();
      int ID=Integer.parseInt(id);
       LinkedList<Completed> usersCompleted=dbManager.getUsersCompletedList(ID);
       dbManager.closeDatabase();
       return usersCompleted;
   }

   //when requesting the page /usercurrent return a list of completed courses not finished
   @ResponseBody
   @RequestMapping(value="/usercourses", method=RequestMethod.GET)
   public LinkedList<Completed> usersCourses(@RequestParam("ID") String id){
      dbManager.initializeDatabase();
      int ID=Integer.parseInt(id);
      LinkedList<Completed> usersCurrent=dbManager.getUsersCourseList(ID);
      dbManager.closeDatabase();
      return usersCurrent;
   }

   //Returns all course records to the URL .../course/data
   @ResponseBody
   @RequestMapping(value="/course/data", method=RequestMethod.GET)
   public LinkedList<Course> usersCurrent(){
      dbManager.initializeDatabase();
      LinkedList<Course> courseList=dbManager.getCourseList();
      dbManager.closeDatabase();
      return courseList;
   }

   //Returns course with provided ID to the URL .../rqstdcourse
   @ResponseBody
   @RequestMapping(value="/rqstdcourse", method=RequestMethod.GET)
   public Course courseById(@RequestParam("ID") String id){
      dbManager.initializeDatabase();
      int ID =Integer.parseInt(id);
      Course rqstdcourse = dbManager.getCourseById(ID);
      dbManager.closeDatabase();
      return rqstdcourse;
   }

   //Returns a list of courses containing string sent by user .../searchcourses
   @ResponseBody
   @RequestMapping(value="/searchcourses", method=RequestMethod.GET)
   public LinkedList<Course> courseBySearchTerm(@RequestParam("SRCHTRM") String searchTerm){
      dbManager.initializeDatabase();
      LinkedList<Course> rqstdcourses = dbManager.getCourseListByString(searchTerm);
      dbManager.closeDatabase();
      return rqstdcourses;
   }

   //Returns a list of courses containing string sent by user .../searchcourses
   @ResponseBody
   @RequestMapping(value="/userscourseinfo", method=RequestMethod.GET)
   public LinkedList<ExistingCourseForDisplay> usersCourseInfoFromUN(@RequestParam("USRNM") String username){
      dbManager.initializeDatabase();
      LinkedList<ExistingCourseForDisplay> rqstdcourses = dbManager.getUsersCourseListForDisplay(username);
      dbManager.closeDatabase();
      return rqstdcourses;
   }

   /**
    *    Books a user on an existing course.
    *
    *    @param - string username
    *    @param - string courseid
    *
    *    @return string "1" if user was booked on course
    *    @return string "2" no space on course (unsuccessfull)
    *    @return string "3" user already booked on course ( unsuccessfull)
    *    @return string "4" unknown (unsucessfull)
    *
    * */
   @ResponseBody
   @RequestMapping(value = "/bookcourse", method = RequestMethod.GET)
   public int bookCourse(@RequestParam("USERNAME") String username,
                            @RequestParam("EXCOURSEID") String exCourseId) {
      int CID=Integer.parseInt(exCourseId);
      dbManager.initializeDatabase();
      int status=dbManager.bookStudentOnCourse(username,CID);
      dbManager.closeDatabase();
      return status;
   }

   //Returns an address of the classroom at the passed id
   @ResponseBody
   @RequestMapping(value="/classroomById", method=RequestMethod.GET)
   public Classroom addressFromClassroomID(@RequestParam("CLASSROOMID") String clssrmID){
      dbManager.initializeDatabase();
      Classroom classroom = dbManager.getClassroomBySId(clssrmID);
      dbManager.closeDatabase();
      return classroom;
   }

   @RequestMapping(value="/view-person-list{idvalue}", method=RequestMethod.GET)
   public ModelAndView displayPersonList(@PathVariable(value = "idvalue") String id) {

      ModelAndView mv=new ModelAndView();

      //initialise the database and try to parse the sent id
      dbManager.initializeDatabase();
      int ID = 0;
      try {
         ID = Integer.parseInt(id);
      } catch (Exception e) {
         //there was no id sent
      }

      LinkedList<Person> personList=dbManager.studentsOnScheduledCourse(ID);
      dbManager.closeDatabase();

      StringBuilder sb=new StringBuilder();
      for (Person person:personList) {
         sb.append("<tr><td>"+person.getUsername()+"</tr></td>");
      }
      mv.addObject("table", sb.toString());
      mv.setViewName("personlist");
      dbManager.closeDatabase();
      return mv;
   }

   //Returns an address of the classroom at the passed id
   @ResponseBody
   @RequestMapping(value="/existingbystring", method=RequestMethod.GET)
   public LinkedList<ExistingCourseForDisplay> existingCourseForDisplay(@RequestParam("SEARCHTERM") String searchTerm){
      dbManager.initializeDatabase();
      LinkedList<ExistingCourseForDisplay> ecdl= dbManager.getDisplayCourses(searchTerm);
      dbManager.closeDatabase();
      return ecdl;
   }

   @ResponseBody
   @RequestMapping(value="/cancelbooking", method=RequestMethod.GET)
   public int cancelBooking(@RequestParam("USERNAME") String userName, @RequestParam("EXISTINGID") String existingId){
      int existingID=0;
      try{
         existingID=Integer.parseInt(existingId);

      }
      catch (Exception e){
         //there was no id sent
      }

      dbManager.initializeDatabase();
      int status=dbManager.cancelBooking(existingID, userName);
      dbManager.closeDatabase();

      return status;
   }

}
