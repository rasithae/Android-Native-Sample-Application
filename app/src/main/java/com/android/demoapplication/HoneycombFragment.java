package com.android.demoapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HoneycombFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.honeycomb_controls, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView calendarFeedback = (TextView)getActivity().findViewById(R.id.feedback);

        NumberPicker numberPicker = (NumberPicker)getActivity().findViewById(R.id.numberPicker1);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                calendarFeedback.setText(String.format("Set number picker to %d", newVal));
            }
        });

        CalendarView calendar = (CalendarView)getActivity().findViewById(R.id.calendarView1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                calendarFeedback.setText("Selected " + year + "-" + month + "-" + dayOfMonth);
            }
        });

        SearchView searchView = (SearchView)getActivity().findViewById(R.id.searchView1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                calendarFeedback.setText("Searched for " + s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                calendarFeedback.setText("User typing " + s);
                return false;
            }
        });
    }
}
