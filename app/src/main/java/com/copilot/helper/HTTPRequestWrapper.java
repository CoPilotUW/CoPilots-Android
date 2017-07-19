package com.copilot.helper;
/**
 * Created by Akash on 2017-06-10.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for GET and POST HTTP requests
 */
public class HTTPRequestWrapper {
    private static String baseURL = "http://copilot-services.herokuapp.com/";
    private static Context context;
    // Create a request queue
    private static RequestQueue queue;

    public HTTPRequestWrapper ( String base, Context callingContext ) {
        baseURL = base;
        context = callingContext;
        queue = Volley.newRequestQueue(callingContext);
    }

    /**
     * This method is used to make a get requests
     * @param endpoint     request endpoint
     */
    public static void makeGetRequest (final String endpoint, final Map<String, String> PARAMS, final VolleyCallback success, final VolleyCallback failure,
                                       final Map<String, String> HEADERS) {
        String uri = baseURL + endpoint;
        if (PARAMS != null && PARAMS.size() > 0) {
            uri += "?";
            for (Map.Entry<String, String> param : PARAMS.entrySet()) {
                uri += param.getKey() + "=" + param.getValue();
                uri += "&";
            }
            uri = uri.substring(0, uri.lastIndexOf("&"));
            Log.d("adfasdf", "the url is: " + uri);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errToast = Toast.makeText(context, "GET request to " + baseURL +
                        endpoint + " failed.", Toast.LENGTH_SHORT);
                errToast.show();
                failure.onSuccessResponse(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> allHeaders = new HashMap<>();
                allHeaders.putAll(super.getHeaders());
                if (HEADERS != null) {
                    allHeaders.putAll(HEADERS);
                }
                return allHeaders;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * This is used to create a post request
     * @param endpoint   request endpoint
     * @param PARAMS     request params
     * @param success    success callback
     * @param failure    failure callback
     */
    public static void makePostRequest (final String endpoint, final Map<String, String> PARAMS,
                                        final VolleyCallback success, final VolleyCallback failure,
                                        final Map<String, String> HEADERS) {
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, baseURL + endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failure.onSuccessResponse(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams () {
                return PARAMS;
            }

            @Override
            public  Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> allHeaders = new HashMap<>();
                allHeaders.putAll(super.getHeaders());
                if (HEADERS != null) {
                    allHeaders.putAll(HEADERS);
                }
                return allHeaders;
            }
        };
        queue.add(jsonRequest);
    }

}
