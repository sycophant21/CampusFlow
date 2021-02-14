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
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.applicationcreator.web.ApplicationBuilder;
import com.example.applicationcreator.web.Flag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FourthFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    private Date date = null;
    private ApplicationBuilder applicationBuilder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            View view = inflater.inflate(R.layout.fragment_fourth, container, false);
            CalendarView v = view.findViewById(R.id.to_date);
            assert getArguments() != null;
            applicationBuilder = FourthFragmentArgs.fromBundle(getArguments()).getApplicationBuilder();
            v.setMinDate(applicationBuilder.getStartDate().getTime());
            date = Date.from(Instant.now());
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
            return view;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView v = view.findViewById(R.id.to_date);
        v.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_FourthFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    calendar = new GregorianCalendar(year, month, dayOfMonth);
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        view.findViewById(R.id.back_to_startDateSelection_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_FourthFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    FourthFragmentDirections.ActionFourthFragmentToThirdFragment action = FourthFragmentDirections.actionFourthFragmentToThirdFragment(applicationBuilder);
                    NavHostFragment.findNavController(FourthFragment.this).navigate(action);
                }
            }
        });
        view.findViewById(R.id.from_date_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_FourthFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    FourthFragmentDirections.ActionFourthFragmentToFifthFragment action = FourthFragmentDirections.actionFourthFragmentToFifthFragment(applicationBuilder.withEndDate(date));
                    NavHostFragment.findNavController(FourthFragment.this).navigate(action);
                }
            }
        });
    }
}