package steve.yang.tradeit.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import steve.yang.tradeit.R;

/**
 * @author zhensuy
 * @date 6/22/17
 * @description
 */

public class NetworkHelper {

    public static final String TAG = NetworkHelper.class.getSimpleName();

    private Context mContext;

    public NetworkHelper(Context context) {
        mContext = context;
    }

    public String[] getZipCodeByDistance(String zipcode) {
        String[] zipcodes = new String[10];
        String apiKey = mContext.getResources().getString(R.string.zipcode_api_key);
        String format = "json";
        String distance = "50";
        String units = "miles";
        String url = "https://www.zipcodeapi.com/rest/"
                + apiKey + "/radius." + format + "/" + zipcode + "/" + distance + "/"  + units;
        httpRequest(url);

        return zipcodes;
    }

    private void httpRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "httpResponse: " + response);
                        parseJsonResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

        });
        queue.add(request);
    }

    private void parseJsonResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("zip_codes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String zipCode = obj.getString("zip_code");
                String city = obj.getString("city");
                Log.d(TAG, "zipcode: " + zipCode + ", city: " + city);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
