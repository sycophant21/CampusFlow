package com.example.applicationcreator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationcreator.web.CurrentUser;
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.TempUser;
import com.example.applicationcreator.web.TempUserID;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private final Gson gson = new Gson();
    private TempUser tempUser = null;
    private AccountManager accountManager;
    private CurrentUser currentUser = CurrentUser.getInstance();
    private Account account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        accountManager = AccountManager.get(getContext());
        if (currentUser.getUserID() != null) {
            if (Flag.getInstance() != 0) {
                LoginFragmentDirections.ActionLoginToFirstFragment action = LoginFragmentDirections.actionLoginToFirstFragment();
                tempUser = new TempUser(currentUser.getUserID(), currentUser.getUserType(), currentUser.getPassword());
                action.setTempUser(tempUser);
                NavHostFragment.findNavController(LoginFragment.this).navigate(action);
            }
        }
        assert getArguments() != null;
        String userID = LoginFragmentArgs.fromBundle(getArguments()).getUserID();
        if (!userID.equals("ABC")) {
            final TextView emailTextView = (TextView) view.findViewById(R.id.login_email_textbox);
            emailTextView.setText(userID);
        }
        else {
            if (currentUser.getUserID() != null) {
                final TextView emailTextView = (TextView) view.findViewById(R.id.login_email_textbox);
                emailTextView.setText(currentUser.getUserID().getUserID());
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView emailTextView = (TextView) view.findViewById(R.id.login_email_textbox);

        final TextView passwordTextView = (TextView) view.findViewById(R.id.password_input_textbox);


        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag.invertFlag();
                RequestQueue queue = Volley.newRequestQueue(requireActivity());
                String url = "";
                Map<String, String> params = new HashMap<>();
                params.put("userId",emailTextView.getText().toString());
                params.put("password", passwordTextView.getText().toString());


                JSONObject jsonObject = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tempUser = gson.fromJson(response.toString(), TempUser.class);
                        //ApplicationBuilder.getInstance().setUserID(tempUser.getTempUserID());
                        if (tempUser != null) {
                            passwordTextView.onEditorAction(EditorInfo.IME_ACTION_DONE);
                            LoginFragmentDirections.ActionLoginToFirstFragment action = LoginFragmentDirections.actionLoginToFirstFragment();
                            action.setTempUser(tempUser);
                            NavHostFragment.findNavController(LoginFragment.this).navigate(action);
                        } else {
                            Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });


        view.findViewById(R.id.SignUp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_Login_to_SignUP_Fragment);
            }
        });
    }

}