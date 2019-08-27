package com.android.demoapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

public class TextControlsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_controls, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity parentActivity = getActivity();
        Button btnTest = (Button)parentActivity.findViewById(R.id.buttonSetText1);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = (EditText)parentActivity.findViewById(R.id.textView1);
                final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)parentActivity.findViewById(R.id.autoCompleteTextView1);
                final MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView)parentActivity.findViewById(R.id.multiAutoCompleteTextView1);

                editText.setText("EditText!");
                autoCompleteTextView.setText("AutoCompleteTextView!");
                multiAutoCompleteTextView.setText("MultiAutoCompleteTextView!");
            }
        });
    }
}
