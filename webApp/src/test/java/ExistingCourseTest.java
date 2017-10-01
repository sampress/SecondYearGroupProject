/**
 * Created by ll14al on 26/04/17.
 */

import com.squirrel.classes.ExistingCourse;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.squirrel.classes.*;
import org.junit.rules.ExpectedException;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class ExistingCourseTest {

    @Test public void setStartTime(){
        ExistingCourse ec = new ExistingCourse();
        ec.setStartTime("2017-07-07 14:00");
        assertThat(ec.getStartTime("yyyy-MM-dd HH:mm"),is("2017-07-07 14:00"));
    }

    @Test public void setEndTime(){
        ExistingCourse ec = new ExistingCourse();
        ec.setEndTime("2018-07-07 14:00");
        assertThat(ec.getEndTime("yyyy-MM-dd HH:mm"),is("2018-07-07 14:00"));
    }

    @Test
    public void setStartTimeIncorrectInput() {
        ExistingCourse ec = new ExistingCourse();
        ec.setStartTime("abc123");
        assertThat(ec.getStartTime(),is(nullValue()));
    }

    @Test
    public void setEndTimeIncorrectInput() {
        ExistingCourse ec = new ExistingCourse();
        ec.setEndTime("abc123");
        assertThat(ec.getEndTime(),is(nullValue()));
    }

}

