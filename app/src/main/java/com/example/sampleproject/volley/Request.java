package com.example.sampleproject.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by 6thenergy on 1/13/2016.
 */
public class Request extends StringRequest {

    public Request(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(method, url, listener, errorListener);

        setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public Request(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
