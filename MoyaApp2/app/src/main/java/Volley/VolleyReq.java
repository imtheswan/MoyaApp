package Volley;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class VolleyReq {
    public static final String TAG = "Estado";

    public String sendInfo(String name, String text , Context context)
    {
        String responseStr;
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/function-test";
        RequestQueue requestQueue = null;
        if( text == null || text.length() == 0 || name == null || name.length() == 0 )
        {
            return "";
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , text );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
               // responseStr = response.toString();
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return "true";
    }
}
