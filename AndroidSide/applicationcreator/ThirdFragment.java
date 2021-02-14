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

import com.example.applicationcreator.web.Application;
import com.example.applicationcreator.web.Flag;
import com.example.applicationcreator.web.GsonCreator;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ThirdFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    private Date date = null;
    private Application applicationReceived;
    private Application applicationToBeSent;
    private Gson gson = GsonCreator.getInstance().getGson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        CalendarView v = view.findViewById(R.id.to_date);
        v.setMinDate(calendar.getTimeInMillis());
        date = Date.from(Instant.now());
        try {
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView v = view.findViewById(R.id.to_date);
        v.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                }
                else {
                    calendar = new GregorianCalendar(year, month, dayOfMonth);

                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //ApplicationBuilder.getInstance().setStartDate(date);
                }
            }//met
        });
        view.findViewById(R.id.back_to_categorySelection_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    assert getArguments() != null;
                    ThirdFragmentDirections.ActionThirdFragmentToSecondFragment action = ThirdFragmentDirections.actionThirdFragmentToSecondFragment(ThirdFragmentArgs.fromBundle(getArguments()).getApplicationBuilder());

                    NavHostFragment.findNavController(ThirdFragment.this).navigate(action);
                }
            }
        });
        view.findViewById(R.id.from_date_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag.getInstance() == 0) {
                    NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_Login);
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else {
                    assert getArguments() != null;
                    ThirdFragmentDirections.ActionThirdFragmentToFourthFragment action = ThirdFragmentDirections.actionThirdFragmentToFourthFragment(ThirdFragmentArgs.fromBundle(getArguments()).getApplicationBuilder().withStartDate(date));
                    NavHostFragment.findNavController(ThirdFragment.this).navigate(action);
                }
            }
        });
    }
}