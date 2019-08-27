package com.android.demoapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Gallery;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MiscControlsFragment extends Fragment implements KeyHandling {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.misc_controls, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.determinateProgressBar);
        final TextView labelSeekbarValue = (TextView)getActivity().findViewById(R.id.labelSeekbarValue);
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(50);
        labelSeekbarValue.setText("0");

        final SeekBar seek = (SeekBar)getActivity().findViewById(R.id.seekBar1);
        seek.setProgress(0);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressBar.setProgress(progress);
                progressBar.setSecondaryProgress(Math.min(progress + 25, progressBar.getMax()));
                labelSeekbarValue.setText(String.valueOf(seek.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        final CheckedTextView checkedTextView = (CheckedTextView)getActivity().findViewById(R.id.checkedTextView1);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedTextView.toggle();
            }
        });

        // Yes, I know that Gallery is deprecated.
        @SuppressWarnings("deprecation")
        Gallery gallery = (Gallery)getActivity().findViewById(R.id.gallery1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, android.R.layout.simple_spinner_item);
        gallery.setAdapter(adapter);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        TextView keyFeedback = (TextView)getActivity().findViewById(R.id.hardwareButtonFeedback);
        keyFeedback.setText(keyCodeToString(keyCode));
    }

    private String keyCodeToString(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return "Back";

            case KeyEvent.KEYCODE_MENU:
                return "Menu";

            case KeyEvent.KEYCODE_POWER:
                return "Power";

            case KeyEvent.KEYCODE_SEARCH:
                return "Search";

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return "Volume Down";

            case KeyEvent.KEYCODE_VOLUME_UP:
                return "Volume Up";

            default:
                return "Unknown (" + keyCode + ")";
        }
    }
    
}
