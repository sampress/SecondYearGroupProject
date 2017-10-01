package squirrel.fdb2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/**
 * Used for logging in by verifying the username and password with the server
 */

public class Verify extends AsyncTask<String, Void, String> {

    Context c;
    final String TAG = "MainActivity";
    final String username;
    final String password;
    private String access = "none";
    Activity activity = null;

    public Verify(Activity a, Context context, String un, String pw) {
            c = context;
            username = un;
            password = pw;
            activity = a;
        }

    @Override
    protected String doInBackground(String... params) {
        try {
            return performPostCall(params[0], new HashMap<String, String>());
        } catch (Exception e) {
            return "Unable to retrieve data. URL may be invalid.";
        }
    }


    @Override
    protected void onPostExecute(String result) {

        if (result.equals("nouser")){
            access = "none";
        }
        if (result.equals("nopass")){
            access = "nopass";
        }
        if (result.equals("delegate")){
            access = "user";
        }
        if (result.equals("admin")){
            access = "admin";
            //Intent i = new Intent(c, MainActivity.class);
            //c.startActivity(i);
        }
        //Toast toast = Toast.makeText(c, result, Toast.LENGTH_LONG);
        //toast.show();
        //Toast.makeText(DownloadTask.this, result, Toast.LENGTH_LONG).show();
        //Log.d(TAG, result);
    }

    public String getAccess(){
        return access;
    }

    public String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(1500);
            conn.setConnectTimeout(1500);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            postDataParams.put("username", username);
            postDataParams.put("password", password);
            Log.e(TAG, "Username: " + username + "    Password: " + password);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            Log.e(TAG, String.valueOf(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}