package com.android.demoapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selections, container, false);
    }

    // We want to ignore the first time the spinner fires its onItemSelected eventgi
    private boolean mSpinnerInitialized = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner planets = (Spinner)getActivity().findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planets.setAdapter(adapter);

        planets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSpinnerInitialized) {
                    setFeedbackText("Selected " + adapterView.getItemAtPosition(i) + " in spinner");
                } else {
                    mSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setFeedbackText("Selection canceled in spinner");
            }
        });

        ListView listView = (ListView)getActivity().findViewById(R.id.listView1);
        ArrayAdapter<CharSequence> listAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setFeedbackText("Selected " + adapterView.getItemAtPosition(i) + " in list view");
            }
        });

        GridView gridView = (GridView)getActivity().findViewById(R.id.gridView1);
        gridView.setAdapter(listAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setFeedbackText("Selected " + adapterView.getItemAtPosition(i) + " in grid view");
            }
        });
    }

    private void setFeedbackText(String text) {
        TextView feedback = (TextView)getActivity().findViewById(R.id.feedbackLabel);
        feedback.setText(text);
    }

}
