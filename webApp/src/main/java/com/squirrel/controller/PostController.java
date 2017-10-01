package com.squirrel.controller;

import com.squirrel.classes.*;
import com.squirrel.database.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PostController {
   DatabaseManager dbManager = new DatabaseManager("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:FDMdb");

   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public ModelAndView login(@ModelAttribute("StringWeb") Person person) {
      dbManager.initializeDatabase();
      ModelAndView mv = new ModelAndView();

      String status = dbManager.validateUser(person);
      if (status.equals("admin")) {
         mv.setViewName("redirect:/course");
      } else if (status.equals("delegate")) {
         mv.setViewName("redirect:/userhomepage");
      } else {
         mv.setViewName("redirect:/index");
      }
      dbManager.closeDatabase();
      return mv;
   }

   @ResponseBody
   @RequestMapping(value = "/validate", method = RequestMethod.POST)
   public String validate(@RequestParam("username") String username, @RequestParam("password") String password) {
      dbManager.initializeDatabase();

      Person person = new Person();
      person.setPassword(password);
      person.setUsername(username);
      String status = dbManager.validateUser(person);
      dbManager.closeDatabase();
      return status;
   }

   @ResponseBody
   @RequestMapping(value = "/addcourse", method = RequestMethod.POST)
   public String addCourse(@ModelAttribute("StringWeb") Course course) {
      dbManager.initializeDatabase();
      dbManager.updateCourse(course);
      dbManager.closeDatabase();
      return "<script>window.opener.location.reload();" +
              "window.close();</script>";
   }

   @RequestMapping(value = "/schedulepluscourse", method = RequestMethod.POST)
   public String scheduleCourse(@ModelAttribute("StringWeb") ExistingCourse course) {
      dbManager.initializeDatabase();
      dbManager.scheduleCourse(course);
      dbManager.closeDatabase();

      return "redirect:/course";
   }

   @ResponseBody
   @RequestMapping(value = "/addplustrainer", method = RequestMethod.POST)
   public String addTrainer(@ModelAttribute("StringWeb") Trainer trainer) {
      dbManager.initializeDatabase();
      dbManager.updateTrainer(trainer);
      dbManager.closeDatabase();

      return "<script>window.opener.location.reload();" +
              "window.close();</script>";
   }

   @ResponseBody
   @RequestMapping(value = "/addplusclassroom", method = RequestMethod.POST)
   public String addClassroom(@ModelAttribute("StringWeb") Classroom classroom) {
      dbManager.initializeDatabase();
      dbManager.updateClassroom(classroom);
      dbManager.closeDatabase();

      return "<script>window.opener.location.reload();" +
              "window.close();</script>";
   }

   @ResponseBody
   @RequestMapping(value = "/classroom/delete", method = RequestMethod.POST)
   public String deleteClassroom(@RequestParam("id") String id) {
      int ID = Integer.parseInt(id);
      dbManager.initializeDatabase();
      dbManager.removeClassroom(ID);
      dbManager.closeDatabase();
      return "";
   }

   @ResponseBody
   @RequestMapping(value = "/trainer/delete", method = RequestMethod.POST)
   public String deleteTrainer(@RequestParam("id") String id) {
      int ID = Integer.parseInt(id);
      dbManager.initializeDatabase();
      dbManager.removeTrainer(ID);
      dbManager.closeDatabase();
      return "";
   }

   @ResponseBody
   @RequestMapping(value = "/course/delete", method = RequestMethod.POST)
   public String deleteCourse(@RequestParam("id") String id) {
      int ID = Integer.parseInt(id);
      dbManager.initializeDatabase();
      dbManager.removeCourse(ID);
      dbManager.closeDatabase();
      return "";
   }

   @ResponseBody
   @RequestMapping(value = "/existing-course/delete", method = RequestMethod.POST)
   public String deleteScheduledCourse(@RequestParam("id") String id) {
      int ID = Integer.parseInt(id);
      dbManager.initializeDatabase();
      dbManager.removeScheduledCourse(ID);
      dbManager.closeDatabase();
      return "";
   }

}
