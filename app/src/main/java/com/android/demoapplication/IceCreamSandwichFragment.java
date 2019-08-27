package com.android.demoapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class IceCreamSandwichFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.icecreamsandwich_controls, container, false);
    }

    @Override
    @TargetApi(14)
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final Activity parentActivity = getActivity();
        Switch btnSwitchICS14 = (Switch)parentActivity.findViewById(R.id.switchICS14);
        final TextView editText = (TextView)parentActivity.findViewById(R.id.icsSwitchStatus);

        btnSwitchICS14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // true if the switch is in the On position
                if(isChecked){
                    editText.setText("Switch ON");
                }
                else{
                    editText.setText("Switch OFF");
                }
            }
        });
    }
}
