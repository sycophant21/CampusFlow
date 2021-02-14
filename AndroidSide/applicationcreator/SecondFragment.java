package com.example.applicationcreator;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.Category;
import com.example.applicationcreator.web.CustomAdapterForCategory;
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.GsonCreator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private final List<Category> entriesList = new ArrayList<>();
    private final Gson gson = GsonCreator.getInstance().getGson();
    private CustomAdapterForCategory customAdapter = null;
    private RecyclerView categories_List_view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        categories_List_view = (RecyclerView) view.findViewById(R.id.categories_List_view);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.discard_application_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void populateEntries(RecyclerView recyclerView) {

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                Category category = gson.fromJson(jsonArray.get(i).toString(), Category.class);
                                entriesList.add(category);
                                customAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("abcde");

                    }
                });


        queue.add(jsonArrayRequest);

        customAdapter = new CustomAdapterForCategory(entriesList, new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    assert getArguments() != null;
                    SecondFragmentDirections.ActionSecondFragmentToThirdFragment action = SecondFragmentDirections.actionSecondFragmentToThirdFragment(SecondFragmentArgs.fromBundle(getArguments()).getApplicationBuilder().withCategoryID(entriesList.get(position).getCategoryID()));
                    Toast.makeText(getContext(), entriesList.get(position).getCategoryName(), Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SecondFragment.this).navigate(action);
                }
            }
        });

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateEntries(categories_List_view);

    }
}