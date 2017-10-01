package squirrel.fdb2;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ed on 03/05/17.
 */

public class UnitTestsMain extends MainActivity{

    private boolean testFailed = false;
    private List<String> errorList = new ArrayList<String>();

    public void getSetUsnTest(){
        String usnExample = "Example Jones";
        setUserName(usnExample);
        if (!getUserName().equals(usnExample)){
            testFailed = true;
            errorList.add("Set/Get Username Failed");
        }
    }

    public void getSetMyCoursesTest(){
        //JSONArray jsonArray = new

        String jData = "[{\"id\":1,\"title\":\"Shshs\",\"description\":\"Shshs\",\"capacity\":112,\"duration\":33,\"prerequisiteID\":0},{\"id\":2,\"title\":\"hfhf\",\"description\":\"rkrkr\",\"capacity\":14,\"duration\":33,\"prerequisiteID\":0}]";
        Log.e("MyCourse", jData);
        JSONObject jsonObject = null;
        JSONArray newJArray = null;
        try {
            newJArray = new JSONArray(jData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainActivity.setMyCourses(newJArray);
        if (!MainActivity.getMyCourses().toString().equals(newJArray.toString())){
            testFailed = true;
            errorList.add("Set/Get MyCourses Failed.");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
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
        setContentView(R.layout.activity_main);

        Toolbar bar = (Toolbar) findViewById(R.id.act_bar);
        setSupportActionBar(bar);
        //bar.setBackgroundResource(R.drawable.fdm);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getSetUsnTest();
        getSetMyCoursesTest();

        if (testFailed) {
            Log.e("Test Results: ", "Test was failed");
            Log.e("Test Results", errorList.toString());
        }

        else{
            Log.d("Test Results: ", "Test completed successfully.");
        }


    }
}
