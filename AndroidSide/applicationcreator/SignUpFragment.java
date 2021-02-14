package com.example.applicationcreator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.GsonCreator;
import com.example.applicationcreator.web.ReturnStatus;
import com.example.applicationcreator.web.TempUserID;
import com.example.applicationcreator.web.User;
import com.example.applicationcreator.web.UserPersonalDetails;
import com.example.applicationcreator.web.UserType;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class SignUpFragment extends Fragment {
    private Gson gson = GsonCreator.getInstance().getGson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = view.findViewById(R.id.userName_fieldbox);
                TextView textView1 = view.findViewById(R.id.email_address_textbox);
                TextView textView2 = view.findViewById(R.id.password_field_for_signup);
                User user = new User(new UserPersonalDetails(textView.getText().toString(),textView1.getText().toString(),textView2.getText().toString()), UserType.APPLICANT);
                createUser(user);

            }
        });
    }

    private void createUser(final User user) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        final String url = "";

        JsonRequest<String> jsonRequest = new JsonRequest<String>(Request.Method.PUT, url, new Gson().toJson(user), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("User creation Successful")) {
                    String userID = user.getUserPersonalDetails().getEmailId().substring(0,user.getUserPersonalDetails().getEmailId().indexOf("@"));
                    Toast.makeText(getContext(), "SignUp Successful \nuserID = "+userID+"", Toast.LENGTH_LONG).show();
                    Flag.invertFlag();
                    SignUpFragmentDirections.ActionSignUPFragmentToLogin action = SignUpFragmentDirections.actionSignUPFragmentToLogin();
                    action.setUserID(userID);
                    NavHostFragment.findNavController(SignUpFragment.this).navigate(action);
                }
                else if (response.equals("User creation Unsuccessful")) {
                    Toast.makeText(getContext(), "User Already Exists", Toast.LENGTH_LONG).show();
                    SignUpFragmentDirections.ActionSignUPFragmentToLogin action = SignUpFragmentDirections.actionSignUPFragmentToLogin();
                    TempUserID tempUserID = new TempUserID(user.getUserPersonalDetails().getEmailId().substring(0,user.getUserPersonalDetails().getEmailId().indexOf("@")));
                    action.setUserID(gson.toJson(tempUserID));
                    NavHostFragment.findNavController(SignUpFragment.this).navigate(action);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"User could not be created",Toast.LENGTH_LONG).show();

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