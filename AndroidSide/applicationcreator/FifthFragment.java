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
import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.ApplicationBuilder;
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.GsonCreator;
import com.example.applicationcreator.web.ReturnStatus;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public class FifthFragment extends Fragment {

    private Gson gson = new Gson()/*GsonCreator.getInstance().getGson()*/;
    private ApplicationBuilder applicationBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert getArguments() != null;
        applicationBuilder = FifthFragmentArgs.fromBundle(getArguments()).getApplicationBuilder();
        return inflater.inflate(R.layout.fragment_fifth, container, false);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back_to_endDateSelection_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FifthFragmentDirections.ActionFifthFragmentToFourthFragment action = FifthFragmentDirections.actionFifthFragmentToFourthFragment(applicationBuilder);
                NavHostFragment.findNavController(FifthFragment.this).navigate(action);
            }
        });
        view.findViewById(R.id.send_application_button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FifthFragment.this).navigate(R.id.action_FifthFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    TextView textView = view.findViewById(R.id.requirements_textbox);
                    applicationBuilder = applicationBuilder.withRequirements(textView.getText().toString());

                    NavHostFragment.findNavController(FifthFragment.this).navigate(R.id.action_FifthFragment_to_FirstFragment);

                    sendApplication();

                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendApplication() {

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "";

        JsonRequest<String> jsonRequest = new JsonRequest<String>(Request.Method.PUT, url, gson.toJson(applicationBuilder.build()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Application Created Successfully")) {
                    Toast.makeText(getContext(), "Application Submitted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Application could not be created",Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    ReturnStatus returnStatus = new Gson().fromJson(jsonString,ReturnStatus.class);
                    return Response.success(returnStatus.getStatus(),HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonRequest);
    }
}