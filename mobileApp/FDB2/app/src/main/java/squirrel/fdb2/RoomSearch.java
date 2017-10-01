package squirrel.fdb2;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by ed on 02/03/17.
 */

public class RoomSearch extends AppCompatActivity {


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_bar, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomsearch);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.act_bar);


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner spinner1 = (Spinner) findViewById(R.id.spin1);

        Spinner spinner2 = (Spinner) findViewById(R.id.spin2);

        Spinner spinner3 = (Spinner) findViewById(R.id.spin3);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.items1, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.items2, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.items3, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner3.setAdapter(adapter3);

        spinner1.setY(100);
        spinner2.setY(150);
        spinner3.setY(200);
        spinner1.setX(20);
        spinner2.setX(20);
        spinner3.setX(20);


        Context c = getApplicationContext();



        final Intent i1 = getIntent();

        String trainerData = "[{\"id\":1,\"name\":\"dfgf\",\"address\":\"sahdhasd\",\"email\":\"dfgf@gmail.com\",\"phone\":\"098747\"},{\"id\":2,\"name\":\"Hehdh\",\"address\":\"Dhsjs\",\"email\":\"shsh@gmail.com\",\"phone\":\"092938\"}]";
        JSONHandler data = new JSONHandler(trainerData, c);

    }
}
