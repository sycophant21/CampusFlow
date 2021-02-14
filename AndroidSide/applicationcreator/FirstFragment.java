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

import com.example.applicationcreator.web.AppPreference;
import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.ApplicationBuilder;
import com.example.applicationcreator.web.ApplicationManager;
import com.example.applicationcreator.web.Comparator;
import com.example.applicationcreator.web.CustomAdapterForApplication;
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.TempUser;
import com.example.applicationcreator.web.UserType;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private View view;
    private List<Application> applicationList;
    private ApplicationManager applicationManager;
    private CustomAdapterForApplication customAdapter;

    public FirstFragment() {
        applicationList = new ArrayList<>();
        customAdapter = new CustomAdapterForApplication(applicationList, new ClickListenerAB(getContext(), this, applicationList));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        this.applicationManager = new ApplicationManager(new Comparator(), view);
        Bundle bundle = getArguments();
        assert bundle != null;
        TempUser tempUser = FirstFragmentArgs.fromBundle(bundle).getTempUser();
        if (tempUser != null) {
            AppPreference.getInstance().setTempUser(tempUser);
        }
        else {
            tempUser = AppPreference.getInstance().getTempUser();
        }
        if (tempUser.getUserType() == UserType.AUTHENTICATOR) {
            view.findViewById(R.id.fab_create_application).setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        final TempUser tempUser = AppPreference.getInstance().getTempUser();

        view.findViewById(R.id.fab_create_application).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(new ApplicationBuilder().withUserID(tempUser.getTempUserID()));
                    NavHostFragment.findNavController(FirstFragment.this).navigate(action);
                }
            }
        });
        view.findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_Login);
                    Toast.makeText(getContext(), "You are already logged out", Toast.LENGTH_SHORT).show();
                } else {
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_Login);
                    Flag.invertFlag();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void populateEntries(RecyclerView recyclerView) {

        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        final TempUser tempUser = AppPreference.getInstance().getTempUser();
        populateEntries((RecyclerView) view.findViewById(R.id.past_applications_recyclerView));
        assert tempUser != null;
        if (applicationManager.getApplication(tempUser, customAdapter, applicationList, getContext()).isEmpty()) {
            view.findViewById(R.id.no_applications_textBox).setVisibility(View.VISIBLE);
        }
    }

}