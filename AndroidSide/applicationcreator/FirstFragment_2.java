package com.example.applicationcreator;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationcreator.web.AppPreference;
import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.ApplicationID;
import com.example.applicationcreator.web.CurrentUser;
import com.example.applicationcreator.web.GsonCreator;
import com.example.applicationcreator.web.ReturnStatus;
import com.example.applicationcreator.web.StatusType;
import com.example.applicationcreator.web.StatusUpdater;
import com.example.applicationcreator.web.UserType;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class FirstFragment_2 extends Fragment {
    private Application application;
    private Gson gson = GsonCreator.getInstance().getGson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //application = ClickedApplication.getInstance().getApplication();
        View view = inflater.inflate(R.layout.fragment_first_2, container, false);
        assert getArguments() != null;
        final ApplicationID applicationID = new ApplicationID(FirstFragment_2Args.fromBundle(getArguments()).getApplicationID());
        getApplication(applicationID,view);
        if(AppPreference.getInstance().getTempUser().getUserType() == UserType.APPLICANT) {
            view.findViewById(R.id.reject_button).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.accept_button).setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        assert getArguments() != null;
        final ApplicationID applicationID = new ApplicationID(FirstFragment_2Args.fromBundle(getArguments()).getApplicationID());


        view.findViewById(R.id.accept_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptApplication(applicationID);
            }
        });
        view.findViewById(R.id.reject_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectApplication(applicationID);
            }
        });
    }
    public void getApplication(final ApplicationID applicationID, final View view) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "";

        //Use Json
        JsonRequest<Application> jsonRequest = new JsonRequest<Application>(Request.Method.POST, url, gson.toJson(applicationID), new Response.Listener<Application>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Application response) {
                if (response == null) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    TextView textView = view.findViewById(R.id.category_textbox);
                    textView.setText(application.getCategoryID().getCategoryID());
                    textView = view.findViewById(R.id.requirements_textbox);
                    textView.setText(application.getRequirements());
                    textView = view.findViewById(R.id.status_textBox);
                    textView.setText(application.getStatus().getStatusType().toString());
                    textView = view.findViewById(R.id.current_status_box);
                    textView.setText(String.valueOf(application.getStatus().getCurrentStatus()));
                    System.out.println(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<Application> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    application = gson.fromJson(jsonString,Application.class);


                    return Response.success(application, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonRequest);

    }

    public void acceptApplication(ApplicationID applicationID) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "";

        //Use Json
        JsonRequest<ReturnStatus> jsonRequest = new JsonRequest<ReturnStatus>(Request.Method.PUT, url, gson.toJson(new StatusUpdater(AppPreference.getInstance().getTempUser().getTempUserID(),applicationID,1)), new Response.Listener<ReturnStatus>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(ReturnStatus response) {
                if (response.getStatus().equals("OK")){
                    FirstFragment_2Directions.ActionFirst2FragmentToFirstFragment action = FirstFragment_2Directions.actionFirst2FragmentToFirstFragment();
                    NavHostFragment.findNavController(FirstFragment_2.this).navigate(action);
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(),"Something went wrong, try again later",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<ReturnStatus> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    ReturnStatus returnStatus = gson.fromJson(jsonString,ReturnStatus.class);

                    return Response.success(returnStatus, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonRequest);
    }
    public void rejectApplication(ApplicationID applicationID) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "";

        //Use Json
        JsonRequest<ReturnStatus> jsonRequest = new JsonRequest<ReturnStatus>(Request.Method.PUT, url, gson.toJson(new StatusUpdater(CurrentUser.getInstance().getUserID(),applicationID,0)), new Response.Listener<ReturnStatus>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(ReturnStatus response) {
                if (response.getStatus().equals("OK")){
                    FirstFragment_2Directions.ActionFirst2FragmentToFirstFragment action = FirstFragment_2Directions.actionFirst2FragmentToFirstFragment();
                    NavHostFragment.findNavController(FirstFragment_2.this).navigate(action);
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(),"Something went wrong, try again later",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<ReturnStatus> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    ReturnStatus returnStatus = gson.fromJson(jsonString,ReturnStatus.class);

                    return Response.success(returnStatus, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonRequest);
    }
}