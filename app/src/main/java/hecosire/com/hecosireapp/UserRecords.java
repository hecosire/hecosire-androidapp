package hecosire.com.hecosireapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class UserRecords {

    private JSONArray feed;

    public UserRecords(JSONArray feed){

        this.feed = feed;
    }

    public List<Map<String, String>> getRecords() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        String ALT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(
                ALT_DATE_TIME_FORMAT);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat nicer_sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        for (int i = 0; i < feed.length(); i++) {
            try {
                JSONObject jsonobject = feed.getJSONObject(i);
                JSONObject health_state = jsonobject.getJSONObject("health_state");

                String name = health_state.getString("name");
                String created_at = jsonobject.getString("created_at");

                Map<String, String> datum = new HashMap<String, String>(2);
                Date date = sdf.parse(created_at);

                Calendar instance = Calendar.getInstance(TimeZone.getDefault());
                instance.setTime(date);

                datum.put("title", name);
                datum.put("date", nicer_sdf.format(instance.getTime()));
                data.add(datum);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data;
    };

}
