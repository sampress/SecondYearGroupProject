package squirrel.fdb2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;

/**
 * This page is called when a user clicks on a course from the app.
 *
 * It is a generic template that is populated with data from the bundle sent from the previous page.
 */

public class CoursePage  extends AppCompatActivity implements OnMapReadyCallback {

    private Button book = null;         /**< The button clicked to book the course */
    private String id = null;           /**< The id of the course that is displayed*/
    SupportMapFragment mapFragment;
    GoogleMap gMap;
    Context c = null;
    private String courseLocation = "Leeds University";
    private String classroomId = "";
    private static final String TAG = "Course Page";
    private String roomData = "";
    public Button unbook = null;

    private void setClassroomId(String classId){
        classroomId = classId;
    }

    /**
     * Called when a user has booked a course displayed on this page.
     *
     * It shows a map widget which shows the pin containing the location of the course.
     */

    private void showMap(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.VISIBLE);
        unbook.setVisibility(View.VISIBLE);
    }

    /**
     * Takes an address as a string and returns a LongLat for the location.
     */

    private LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        p1 = new LatLng(0,0);

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    /**
     *  Adds an alert to the un-book button
     * @param b The unbook button
     */
    private void addAlert(Button b){
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new DownloadTask(c){
                                    @Override
                                    protected void onPreExecute(){}
                                    @Override
                                    public void onPostExecute(String result) {
                                        Log.e("CoursePage", result);
                                        if (result.equals("1")){
                                            revertBooking();
                                            book.setOnClickListener(new Button.OnClickListener() {
                                                public void onClick(View v) {
                                                    sendBookRequest(c, book);
                                                }
                                            });
                                        }
                                        else{
                                            unbook.setText("ERROR UN-BOOKING");
                                        }
                                    }


                                }.execute(MainActivity.ip + "cancelbooking?USERNAME=" + MainActivity.getUserName() + "&EXISTINGID=" + id);
                                Log.e("CoursePage", MainActivity.ip + "?USERNAME=" + MainActivity.getUserName() + "&EXISTINGID=" + id);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(CoursePage.this);
                builder.setMessage("Are you sure you want to un-book the course?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

    }

    /**
     *  Runs when the map has loaded successfully
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng add = getLocationFromAddress(c,courseLocation);
        gMap.addMarker(new MarkerOptions()
                .position(add)
                .title(""));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new MarkerOptions()
                .position(add)
                .title("")).getPosition(), 14));
    }

    /**
     *  Checks to see if the course is already in the list of MyCourses
     */

    private boolean checkCourseBooked(Button b){
        Log.e("CoursePage", "Checking Course with id:" + id);
        int i = 0;
        String idCheck = null;
        try {
            if (MainActivity.getMyCourses()!=null) {
                while (i < MainActivity.getMyCourses().length()) {
                    idCheck = MainActivity.getMyCourses().getJSONObject(i).getString("existingCourseId");
                    if (idCheck.equals(id)) {
                        b.setText("COURSE BOOKED");
                        b.setOnClickListener(null);
                        getClassroomData();
                        return true;
                    }
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void showMapLoading(){
        TextView t = (TextView) findViewById(R.id.map_loading);
        t.setText("Map Loading...");
    }

    private void showMapLoaded(){
        TextView t = (TextView) findViewById(R.id.map_loading);
        t.setText("Location of venue:");
    }

    private void getClassroomData(){
        new DownloadTask(c) {
            @Override
            protected void onPreExecute(){}
            @Override
            public void onPostExecute(String result) {
                //result = "{\"id\":1,\"name\":\"RT9\",\"city\":\"Leeds\",\"address\":\"LS6 1JR Harold Avenue\",\"capacity\":100,\"type\":\"Lecture Theatre\",\"projector\":true,\"studentComp\":false,\"whiteboard\":true,\"audioRecording\":true,\"videoRecording\":true,\"wheelchairAccess\":false,\"listeningSystem\":false}";
                result = "[" + result + "]";
                Log.e("CoursePage", result);
                roomData = result;
                Log.e("CoursePage", " roomData = " + roomData);
                JSONHandler j = new JSONHandler(roomData, c);
                try {
                    courseLocation = j.getObject(0).getString("address");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showMapLoading();
                showMap();
                showMapLoaded();

            }
        }.execute(MainActivity.ip + "classroomById/?CLASSROOMID=" + classroomId);
        //Log.e("CoursePage", " ClassroomId = " + classroomId);
        //Log.e("CoursePage", " roomData = " + roomData);
        }

    /**
     *  Reverts the GUI after an un-booking
     */

    private void revertBooking(){
        book.setText("BOOK COURSE");
        mapFragment.getView().setVisibility(View.GONE);
        unbook.setVisibility(View.GONE);
        TextView t = (TextView) findViewById(R.id.map_loading);
        t.setText("");
    }

    /**
     *  Called if the server returns success.
     */

    private void bookSuccess(Button b){
        MyCourse.addCourseID(id);
        getClassroomData();
        if (MyCourse.isInList(id)){
            b.setText("COURSE BOOKED");
            b.setOnClickListener(null);
        }
        Log.e(TAG, "Course Booked Successfully");
    }

    /**
     *  Called if the server returns that the course is full.
     */

    private void bookFull(Button b){
        b.setOnClickListener(null);
        b.setText("COURSE FULL - ON WAITING LIST");
        Log.e(TAG, "Course is Full");
    }

    /**
     *  Called if the server returns that the course is already booked.
     */

    private void bookAlready(Button b){
        b.setText("COURSE ALREADY BOOKED");
        b.setOnClickListener(null);
        Log.e(TAG, "Course is Already Booked");
        getClassroomData();
    }

    /**
     *  Called if the server returns that there are prerequisite course requirements missing.
     */

    private void bookPreReq(Button b){
        Log.e(TAG, "Course Requirements Lacking");
        b.setText("COURSE(S) LACKING");
        b.setOnClickListener(null);
    }

    /**
     *  Called if the server returns an unknown error.
     */

    private void bookUndefined(){
        Log.e(TAG, "Undefined Error");
    }

    /**
     *  Sends a book request to the server.
     */

    private void sendBookRequest(Context c, final Button b){
        new DownloadTask(c) {
            @Override
            protected void onPreExecute(){}
            @Override
            public void onPostExecute(String result) {
                if (result.equals("1")){bookSuccess(b);}
                else if (result.equals("2")){bookFull(b);}
                else if (result.equals("3")){bookAlready(b);}
                else if (result.equals("4")){bookPreReq(b);}
                else if (result.equals("5")){bookUndefined();}
                Log.e("CoursePageTag", result);
            }
        }.execute(MainActivity.ip + "bookcourse/?USERNAME=" + MainActivity.getUserName() + "&EXCOURSEID=" + id);
    Log.e(TAG, MainActivity.ip + "bookcourse/?USERNAME=" + MainActivity.getUserName() + "&EXCOURSEID=" + id);
    }

    /**
     *  Sets the 'book course' button actions.
     */

    private void setupBookButton(final Context c){
        book= (Button)findViewById(R.id.book_course);
        Log.e("CoursePage", "Checking for booking...");
        if (checkCourseBooked(book)){
            Log.e("Course", "Already Booked");
            return;
        }
        else {
            book.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    sendBookRequest(c, book);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Button b = (Button)findViewById(R.id.logout);
        Button b1 = (Button)findViewById(R.id.my_courses);
        Button b2 = (Button)findViewById(R.id.search_courses);

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CoursePage.this,Login.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CoursePage.this,MyCourse.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CoursePage.this,CourseSearch.class);
                startActivity(i);
            }
        });

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
        setContentView(R.layout.course_page);
        Toolbar bar = (Toolbar) findViewById(R.id.act_bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        c = getApplicationContext();
        Bundle b = getIntent().getExtras();

        TextView courseName = (TextView)findViewById(R.id.course_name);
        TextView courseDescription = (TextView)findViewById(R.id.course_description);
        TextView courseDuration = (TextView)findViewById(R.id.course_duration);
        TextView courseCapacity = (TextView)findViewById(R.id.course_capacity);
        unbook = (Button)findViewById(R.id.unbook);
        addAlert(unbook);

        courseName.setText(b.getString("title"));
        courseDescription.setText(b.getString("description"));
        setClassroomId(b.getString("classroomId"));
        courseDuration.setText(b.getString("formattedDate"));
        courseCapacity.setText("");

        id = b.getString("courseId");
        Log.e("CoursePage", "CourseID =" + id);
        setupBookButton(c);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getView().setVisibility(View.GONE);
    }
}

