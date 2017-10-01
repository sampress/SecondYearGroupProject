package squirrel.fdb2;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    public JSONArray objectList = null;
    public Context c = null;

    public JSONHandler(Context context){
        c=context;
    }

    public JSONHandler(String JSONData, Context context){
        c=context;
        try {
            objectList = new JSONArray(JSONData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getObject(int entry){
        JSONObject jObject = null;
        try {
            jObject = objectList.getJSONObject(entry);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObject;
    }
}
