package squirrel.fdb2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ed on 02/03/17.
 */

public class TrainerPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Context c = getApplicationContext();

        final Intent i1 = getIntent();

        String trainerData = "[{\"id\":1,\"name\":\"dfgf\",\"address\":\"sahdhasd\",\"email\":\"dfgf@gmail.com\",\"phone\":\"098747\"},{\"id\":2,\"name\":\"Hehdh\",\"address\":\"Dhsjs\",\"email\":\"shsh@gmail.com\",\"phone\":\"092938\"}]";
        JSONHandler data = new JSONHandler(trainerData, c);

    }
}
