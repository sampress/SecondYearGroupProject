package squirrel.fdb2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyCourse  extends AppCompatActivity {

    private Calendar date = null;

    private static List<String> courseID = new ArrayList<String>();

    public static List<String> getCourseID(){
        return courseID;
    }

    public static void addCourseID(String id){
        courseID.add(id);
    }

    public static boolean isInList(String id){
        Log.e("MyCourse", courseID.toString());
        return courseID.contains(id);
    }

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

    protected void updateGui(String url, Context c){

        final String TAG = "MyCourse";
        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> description = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();

        new DownloadTask(c) {

            ProgressDialog progress;
            //show spinning wheel as it loads
            @Override
            protected void onPreExecute(){
                progress = new ProgressDialog(MyCourse.this,R.style.AppCompatAlertDialogStyle);
                progress.show();
                progress.setMessage("Loading");
            }
            @Override
            public void onPostExecute(String result) {
                //result = "[{\"id\":1,\"title\":\"Shshs\",\"description\":\"Shshs\",\"capacity\":112,\"duration\":33,\"prerequisiteID\":0},{\"id\":2,\"title\":\"hfhf\",\"description\":\"rkrkr\",\"capacity\":14,\"duration\":33,\"prerequisiteID\":0}]";
                Log.e("MyCourse", result);
                JSONObject jsonObject = null;
                JSONArray newJArray = null;
                try {
                    newJArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MainActivity.setMyCourses(newJArray);
                Log.e("MyCourse", MainActivity.getMyCourses().toString());
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
                                TextList(MyCourse.this,dateArray, titleArray, descriptionArray);
                        ListView TList = (ListView) findViewById(R.id.mc_list);
                        TList.setAdapter(adapter);

                        TList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                //Toast.makeText(CourseSearch.this, "You Clicked at " + titleArray[+position], Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyCourse.this, CoursePage.class);
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

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_bar, menu);
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_course);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.act_bar);


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button b = (Button) findViewById(R.id.logout);
        Button b1 = (Button) findViewById(R.id.my_courses);
        Button b2 = (Button) findViewById(R.id.search_courses);

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MyCourse.this, Login.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MyCourse.this, MyCourse.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MyCourse.this, CourseSearch.class);
                startActivity(i);
            }
        });

        final Context c = getApplicationContext();

        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> description = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();

        //set swipe refresh layout to load my course info

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.mycourse_refresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateGui(MainActivity.ip + "userscourseinfo/?USRNM=" + MainActivity.getUserName(), c);
                        swipeRefreshLayout.setRefreshing(false);

                    }

                }
        );

        if (MainActivity.getMyCourses() == null) {
            updateGui(MainActivity.ip + "userscourseinfo/?USRNM=" + MainActivity.getUserName(), c);
        }

        //if the page has not been refreshed and there is a course list already, display it

        else{
            final String TAG = "MyCourse";
            int itemsFound = 0;
            try {
                int i = 0;
                Log.e("Mycourse", "trying to load from getmycourses");

                Log.e(TAG, String.valueOf(MainActivity.getMyCourses().length()));
                if (!MainActivity.getMyCourses().isNull(0)) {
                    for (i = 0; i < MainActivity.getMyCourses().length(); i++) {

                        title.add(MainActivity.getMyCourses().getJSONObject(i).getString("title"));

                        description.add(MainActivity.getMyCourses().getJSONObject(i).getString("description"));

                        date.add(formatDate(MainActivity.getMyCourses().getJSONObject(i).getString("startTimeString")) +
                                " " + "-" + " " + formatDate(MainActivity.getMyCourses().getJSONObject(i).getString("endTimeString")));

                        itemsFound = 1;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (itemsFound == 1) {
                final String[] titleArray = title.toArray(new String[0]);
                final String[] descriptionArray = description.toArray(new String[0]);
                final String[] dateArray = date.toArray(new String[0]);
                TextList adapter = new
                        TextList(MyCourse.this, dateArray, titleArray, descriptionArray);
                ListView TList = (ListView) findViewById(R.id.mc_list);
                TList.setAdapter(adapter);
                Log.e(TAG, "setting adapter from cache");
                TList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //Toast.makeText(MyCourse.this, "You Clicked at " + titleArray[+position], Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyCourse.this, CoursePage.class);
                        Bundle b = new Bundle();
                        try {
                            b.putString("title", MainActivity.getMyCourses().getJSONObject(position).getString("title"));
                            b.putString("description", MainActivity.getMyCourses().getJSONObject(position).getString("description"));
                            b.putString("courseId", MainActivity.getMyCourses().getJSONObject(position).getString("existingCourseId"));
                            b.putString("classroomId", MainActivity.getMyCourses().getJSONObject(position).getString("classroomId"));
                            b.putString("formattedDate", formatDate( MainActivity.getMyCourses().getJSONObject(position).getString("startTimeString")) +
                                    " " + "-" + " " + formatDate(MainActivity.getMyCourses().getJSONObject(position).getString("endTimeString")));
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

        final Intent i1 = getIntent();
        String trainerData = "[{\"id\":1,\"name\":\"dfgf\",\"address\":\"sahdhasd\",\"email\":\"dfgf@gmail.com\",\"phone\":\"098747\"},{\"id\":2,\"name\":\"Hehdh\",\"address\":\"Dhsjs\",\"email\":\"shsh@gmail.com\",\"phone\":\"092938\"}]";
        JSONHandler data = new JSONHandler(trainerData, c);
    }
}
