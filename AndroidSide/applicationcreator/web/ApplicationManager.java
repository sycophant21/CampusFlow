package com.example.applicationcreator.web;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationcreator.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ApplicationManager {
    private final Gson gson ;
    private final Comparator comparator;
    private final View fragment;
    public ApplicationManager(Comparator comparator, View fragment) {
        this.comparator = comparator;
        this.fragment = fragment;
        gson = GsonCreator.getInstance().getGson();
    }

    public List<Application> getApplication(TempUser tempUser, final CustomAdapterForApplication customAdapter, final List<Application> applicationList, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "";
        if (tempUser.getUserType() == UserType.APPLICANT) {
            url = "";
        } else if (tempUser.getUserType() == UserType.AUTHENTICATOR) {
            url = "";
        }

        //Use Json
        JsonRequest<List<Application>> jsonRequest = new JsonRequest<List<Application>>(Request.Method.POST, url, gson.toJson(tempUser.getTempUserID()), createListener(customAdapter, context),createErrorListener(context)) {
            @Override
            protected Response<List<Application>> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONArray jsonArray = new JSONArray(jsonString);
                    applicationList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Simplify
                        Application application = gson.fromJson(jsonArray.get(i).toString(), Application.class);
                        if (application != null) {
                            applicationList.add(application);
                        }
                    }

                    return Response.success(applicationList, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonRequest);

        return applicationList;
    }

    private Response.Listener<List<Application>> createListener(final CustomAdapterForApplication customAdapter, final Context context) {
        return new Response.Listener<List<Application>> () {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(List<Application> response) {
                if (response.isEmpty()) {

                    Toast.makeText(context, "No New Applications", Toast.LENGTH_SHORT).show();

                } else {

                    fragment.findViewById(R.id.no_applications_textBox).setVisibility(View.INVISIBLE);
                    response.sort(comparator);
                    customAdapter.notifyDataSetChanged();
                    //System.out.println(response);

                }
            }
        };

    }
    private Response.ErrorListener createErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Fragment 1", Toast.LENGTH_LONG).show();

            }
        };
    }

}
