import com.squirrel.classes.Course;
import com.squirrel.database.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class DatabaseManagerTest {

   DatabaseManager dbManager;

   @Before
   public void setup(){
      dbManager = new DatabaseManager("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:FDMdb");
      dbManager.initializeDatabase();
   }


   public void addCourse(){
      Course course = new Course();
      course.setDuration(100);
      course.setDescription("The description");
      course.setCapacity(150);
      course.setTitle("Test course");

      dbManager.updateCourse(course);
   }

   @Test
   public void getCourse(){
      addCourse();
      LinkedList<Course> courseList=dbManager.getCourseList();
      Course course=courseList.getLast();

      assertThat(course.getCapacity(), is(150));
      assertThat(course.getDuration(), is(100));
      assertThat(course.getTitle(), is("Test course"));
      assertThat(course.getDescription(), is("The description"));
   }

   @Test
   public void removeCourse(){
      LinkedList<Course> courseList=dbManager.getCourseList();
      Course course=courseList.getLast();
      dbManager.removeCourse(course.getId());
      Course newCourse=dbManager.getCourseById(115);
      assertThat(newCourse.getId(), is(0));
   }


   @After
   public void cleanUp(){
      dbManager.closeDatabase();
   }

}
