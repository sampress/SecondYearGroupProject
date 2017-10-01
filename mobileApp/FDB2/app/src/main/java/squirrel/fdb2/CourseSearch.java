package squirrel.fdb2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * This is the Course Search page
 *
 * It allows a user to search a string and view all returned courses containing the string.
 */

public class CourseSearch extends AppCompatActivity {

    Calendar date = null;

    public String formatDate(String serverDate){

        date = Calendar.getInstance();
        try {
            date.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(serverDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Log.e("CourseSearch", date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.MONTH, null));
        //SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.UK);
        SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd", Locale.UK);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.UK);

        //String weekDay = dayFormat.format(date.getTime());
        String dayNumber = dayNumberFormat.format(date.getTime());
        String month = monthFormat.format(date.getTime());
        String courseDate = dayNumber.toString() + " " + month.toString();
        return courseDate;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


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

    /**
     * Makes a download request to the server and populates the layout on the page with the returned data
     *
     * @param url The url of the download request
     * @param c The context
     */

    protected void updateGui(String url, Context c){

        final String TAG = "CourseSearch";
        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> description = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();

        new DownloadTask(c) {

            ProgressDialog progress;
            //show spinning wheel as it loads
            @Override
            protected void onPreExecute(){
                progress = new ProgressDialog(CourseSearch.this,R.style.AppCompatAlertDialogStyle);
                progress.show();
                progress.setMessage("Loading");
            }
            @Override
            public void onPostExecute(String result) {
                //result = "[{\"id\":1,\"title\":\"Shshs\",\"description\":\"Shshs\",\"capacity\":112,\"duration\":33,\"prerequisiteID\":0},{\"id\":2,\"title\":\"hfhf\",\"description\":\"rkrkr\",\"capacity\":14,\"duration\":33,\"prerequisiteID\":0}]";
                Log.e(TAG, result);
                //add the returned object to the arraylists
                if (!result.isEmpty()) {
                    final JSONHandler jsonHandler = new JSONHandler(result, c);
                    int itemsFound = 0;
                    try {
                        int i = 0;
                        Log.e(TAG, String.valueOf(jsonHandler.objectList.length()));
                        //add text from json to arrays
                        if (!jsonHandler.objectList.isNull(0)) {
                            for (i = 0; i < jsonHandler.objectList.length(); i++) {

                                title.add(jsonHandler.getObject(i).getString("title"));

                                description.add(jsonHandler.getObject(i).getString("description"));

                                date.add(formatDate(jsonHandler.getObject(i).getString("startTimeString")) +
                                " " + "-" + " " + formatDate(jsonHandler.getObject(i).getString("endTimeString")));


                                itemsFound = 1;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //add arrays to gui
                    if (itemsFound == 1) {
                        final String[] titleArray = title.toArray(new String[0]);
                        final String[] descriptionArray = description.toArray(new String[0]);
                        final String[] dateArray = date.toArray(new String[0]);
                        TextList adapter = new
                                TextList(CourseSearch.this,dateArray, titleArray, descriptionArray);
                        ListView TList = (ListView) findViewById(R.id.cs_list);
                        TList.setAdapter(adapter);
                        TList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                //Toast.makeText(CourseSearch.this, "You Clicked at " + titleArray[+position], Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CourseSearch.this, CoursePage.class);
                                //add data to bundle
                                Bundle b = new Bundle();
                                try {
                                    b.putString("title", jsonHandler.getObject(position).getString("title"));
                                    b.putString("description", jsonHandler.getObject(position).getString("description"));
                                    b.putString("courseId", jsonHandler.getObject(position).getString("existingCourseId"));
                                    b.putString("classroomId", jsonHandler.getObject(position).getString("classroomId"));
                                    b.putString("startTime", jsonHandler.getObject(position).getString("startTimeString"));
                                    b.putString("endTime", jsonHandler.getObject(position).getString("endTimeString"));
                                    b.putString("formattedDate", formatDate(jsonHandler.getObject(position).getString("startTimeString")) +
                                            " " + "-" + " " + formatDate(jsonHandler.getObject(position).getString("endTimeString")));
                                    Log.e(TAG, "here 1");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });
                    }
                }
                progress.hide();
            }
        }.execute(url);

    }

    /**
     * Assigns the bottom navigation buttons on the page.
     */

    protected void assignButtons(){
        Button b = (Button)findViewById(R.id.logout);
        Button b1 = (Button)findViewById(R.id.my_courses);
        Button b2 = (Button)findViewById(R.id.search_courses);

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CourseSearch.this,Login.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CourseSearch.this,MyCourse.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CourseSearch.this,CourseSearch.class);
                startActivity(i);
            }
        });

    }

    /**
     * Searches either a search term input by the user, or an empty search (all courses)
     *
     * @param c The app context.
     * @param s The search field editable by the user
     */

    protected void search (final Context c, Editable s){
        // If the user has entered a search term
        if (!String.valueOf(s).isEmpty()) {

            updateGui(MainActivity.ip + "existingbystring/?SEARCHTERM=" + s, c);

        }
        else{
            final String TAG = "CourseSearch";
            Log.e(TAG, "Empty Search");
            updateGui(MainActivity.ip + "existingbystring/?SEARCHTERM=" + s, c);
        }
    }

    /**
     * Searches before the user has entered data (gets all courses)
     *
     * @param c The context
     */

    protected void initialSearch(final Context c){

        updateGui(MainActivity.ip + "existingbystring/?SEARCHTERM=", c);

        final Intent i1 = getIntent();

        String trainerData = "[{\"id\":1,\"name\":\"dfgf\",\"address\":\"sahdhasd\",\"email\":\"dfgf@gmail.com\",\"phone\":\"098747\"},{\"id\":2,\"name\":\"Hehdh\",\"address\":\"Dhsjs\",\"email\":\"shsh@gmail.com\",\"phone\":\"092938\"}]";
        JSONHandler data = new JSONHandler(trainerData, c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.act_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        assignButtons();

        final Context c = getApplicationContext();

        final EditText editText = (EditText)findViewById(R.id.cs_field);
        editText.clearFocus();

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                search(c, s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        initialSearch(c);

    }
}
