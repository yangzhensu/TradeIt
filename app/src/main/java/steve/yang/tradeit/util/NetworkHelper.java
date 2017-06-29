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

import java.util.ArrayList;
import java.util.List;

import steve.yang.tradeit.R;

/**
 * @author zhensuy
 * @date 6/22/17
 * @description
 * TODO: change to singleton
 */

public class NetworkHelper {

    public interface OnZipCodeResponseListener{
        void onZipCodeResponse(List<String> zipCodes);
    }

    public static final String TAG = NetworkHelper.class.getSimpleName();

    private Context mContext;
    private OnZipCodeResponseListener onZipCodeResponseListener;

    public NetworkHelper(Context context) {
        mContext = context;
    }

    public void setOnZipCodeResponseListener(OnZipCodeResponseListener listener) {
        onZipCodeResponseListener = listener;
    }

    public List<String> getZipCodeByDistance(String zipcode) {
        List<String> zipcodes = new ArrayList<>();
        String apiKey = mContext.getResources().getString(R.string.zipcode_api_key);
        Log.d(TAG, "getZipcodeByDistance, apiKey: " + apiKey);
        String format = "json";
        String distance = "50";
        String units = "miles";
        String url = "https://www.zipcodeapi.com/rest/"
                + apiKey + "/radius." + format + "/" + zipcode + "/" + distance + "/"  + units;
        Log.d(TAG, "getZipcodebyDistance, url: " + url);
        httpRequest(url, zipcodes);

        return zipcodes;
    }

    private void httpRequest(String url, final List<String> zipcodes) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "httpResponse: " + response);
                        parseJsonResponse(response, zipcodes);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkHelper.this.onErrorResponse();
                    }

        });
        queue.add(request);
    }

    private void parseJsonResponse(String response, List<String> zipcodes) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("zip_codes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String zipCode = obj.getString("zip_code");
                zipcodes.add(zipCode);
                String city = obj.getString("city");
//                Log.d(TAG, "zipcode: " + zipCode + ", city: " + city);
            }
            if (onZipCodeResponseListener != null) {
                onZipCodeResponseListener.onZipCodeResponse(zipcodes);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Only for testing usage.
     */
    public void onErrorResponse() {
        List<String> zipCodes = new ArrayList<>();
        zipCodes.add("94086");
        zipCodes.add("94089");
        if (onZipCodeResponseListener != null) {
            onZipCodeResponseListener.onZipCodeResponse(zipCodes);
        }
    }

}
