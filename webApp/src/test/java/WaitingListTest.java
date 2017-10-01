
import com.squirrel.classes.WaitingList;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.security.Timestamp;
import java.util.Date;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;


/**
 * Created by ll14al on 28/04/17.
 */
public class WaitingListTest {

    @Test
    public void setTimeAddedString(){
        WaitingList wl = new WaitingList();
        wl.setTimeAdded("2017-07-07 14:00");
        Assert.assertThat(wl.getTimeAdded("yyyy-MM-dd HH:mm"), CoreMatchers.is("2017-07-07 14:00"));
    }

    @Test
    public void setTimeAddedIncorrectInput() {
        WaitingList wl = new WaitingList();
        wl.setTimeAdded("abc123");
        Assert.assertThat(wl.getTimeAdded(), CoreMatchers.is(nullValue()));
    }

}
