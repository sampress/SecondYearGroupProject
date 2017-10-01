package squirrel.fdb2;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button loginButton;
    protected static String access;
    private static String userName;
    private static JSONArray myCourses = null;
    public static String ip = "http://192.168.43.247:8080/";

    public static JSONArray getMyCourses(){
        return myCourses;
    }

    /**
     * Sets a downloaded list of courses so that the app can cache them
     * @param courseList
     */
    public static void setMyCourses(JSONArray courseList){
        myCourses = null;
        myCourses = courseList;
    }

    public static void setUserName(String s){
        userName = s;
    }

    public static String getUserName(){
        return userName;
    }

    public static String getAccess() {
        return access;
    }

    public static void setAccess(String str) {
        access = str;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loginButton = (Button) findViewById(R.id.button);
        loginButton.setText("Login");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
        if (TextUtils.isEmpty(access)){
            access = "none";
            Log.e(TAG,"empty");
        }
        Log.e(TAG, getAccess());
    }
}
