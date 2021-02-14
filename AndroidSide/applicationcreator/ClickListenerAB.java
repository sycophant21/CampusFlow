package com.example.applicationcreator;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.Flag;

import java.util.List;

public class ClickListenerAB implements OnItemClickListener {

    private Context context;
    private Fragment fragment;
    private List<Application> applicationList;

    public ClickListenerAB(Context context, Fragment fragment, List<Application> applicationList) {

        this.context = context;
        this.fragment = fragment;
        this.applicationList = applicationList;
    }

    @Override
    public void onClick(View view, int position) {
        if (Flag.getInstance() == 0) {
            NavHostFragment.findNavController(fragment).navigate(R.id.action_FirstFragment_to_Login);
            Toast.makeText(context, "You need to login first", Toast.LENGTH_SHORT).show();
        } else {
            FirstFragmentDirections.ActionFirstFragmentToFirst2Fragment action = FirstFragmentDirections.actionFirstFragmentToFirst2Fragment();
            action.setApplicationID(applicationList.get(position).getApplicationID().getApplicationID());
            NavHostFragment.findNavController(fragment).navigate(action);
        }
    }
}

