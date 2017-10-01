package squirrel.fdb2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchCourseTest extends CourseSearch{

    private List<String> searchTerms = new ArrayList<String>();
    private List<String> expectedResults = new ArrayList<String>();

    @Override
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
                progress = new ProgressDialog(SearchCourseTest.this,R.style.AppCompatAlertDialogStyle);
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

                                date.add(jsonHandler.getObject(i).getString("startTimeString") +
                                        jsonHandler.getObject(i).getString("endTimeString"));

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
                                TextList(SearchCourseTest.this, titleArray, descriptionArray, dateArray);
                        ListView TList = (ListView) findViewById(R.id.cs_list);
                        TList.setAdapter(adapter);
                        TList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                //Toast.makeText(CourseSearch.this, "You Clicked at " + titleArray[+position], Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SearchCourseTest.this, CoursePage.class);
                                //add data to bundle
                                Bundle b = new Bundle();
                                try {
                                    b.putString("title", jsonHandler.getObject(position).getString("title"));
                                    b.putString("description", jsonHandler.getObject(position).getString("description"));
                                    b.putString("courseId", jsonHandler.getObject(position).getString("existingCourseId"));
                                    b.putString("classroomId", jsonHandler.getObject(position).getString("classroomId"));
                                    b.putString("startTime", jsonHandler.getObject(position).getString("startTimeString"));
                                    b.putString("endTime", jsonHandler.getObject(position).getString("endTimeString"));
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

        Log.e("SearchCourseTest", url);

    }

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

        updateGui(MainActivity.ip + "existingbystring/?SEARCHTERM=" + "term", c);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //search(c, s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //initialSearch(c);

    }
}