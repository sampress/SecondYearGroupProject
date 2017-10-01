package squirrel.fdb2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView errorText = null;

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
        setContentView(R.layout.login);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.act_bar);


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Context c = getApplicationContext();

        final EditText un  = (EditText)findViewById((R.id.un));

        final EditText pw  = (EditText)findViewById((R.id.pw));

        errorText = (TextView)findViewById(R.id.loginText);

        Button b = (Button)findViewById(R.id.logbutton);

        final Activity a = this;

        //sets the submit button to send a login request

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new Verify(a, c, un.getText().toString(), pw.getText().toString()){
                    @Override
                    public void onPostExecute(String result) {
                        Log.e(TAG, result);
                        //Toast toast = Toast.makeText(c, result, Toast.LENGTH_LONG);
                        //toast.show();
                        takeAction(c, result,  un.getText().toString());

                    }
                }.execute(MainActivity.ip + "validate");
            }
        });

        final Intent i1 = getIntent();

        String trainerData = "[{\"id\":1,\"name\":\"dfgf\",\"address\":\"sahdhasd\",\"email\":\"dfgf@gmail.com\",\"phone\":\"098747\"},{\"id\":2,\"name\":\"Hehdh\",\"address\":\"Dhsjs\",\"email\":\"shsh@gmail.com\",\"phone\":\"092938\"}]";
        JSONHandler data = new JSONHandler(trainerData, c);

    }

    /**
     * Decides whether to log in based on the server response
     * @param c THe context
     * @param access The access level
     * @param un The username
     */


    public void takeAction(Context c, String access, String un){
        MainActivity.setAccess(access);
        MainActivity.setUserName(un);
        final String TAG = "Login";
        Log.e(TAG, "the returned access level is: " + MainActivity.getAccess());

        if ((access.equals("admin")) || (access.equals("user"))){
            Intent i = new Intent(c, CourseSearch.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        }
        else if (access.equals("nopass")){
            errorText.setText("Password is incorrect.");
        }
        else if (access.equals("nouser")){
            errorText.setText("No such user or admin found.");
        }
    }

}
