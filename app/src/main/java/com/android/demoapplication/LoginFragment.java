package com.android.demoapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_activity, container, false);
    }

    Button btnLogin, btnCancel;
    EditText editTextUserName,editTextPassword;
    //final TextView textViewLoginMessage;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity parentActivity = getActivity();

        btnLogin = (Button)parentActivity.findViewById(R.id.btnLogin);
        editTextUserName = (EditText)parentActivity.findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)parentActivity.findViewById(R.id.editTextPassword);

        btnCancel = (Button)parentActivity.findViewById(R.id.btnCancel);
        final TextView textViewLoginMessage = (TextView)parentActivity.findViewById(R.id.textViewLoginMessage);
        //tx1.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textViewLoginMessage.setVisibility(View.GONE);
                if(editTextUserName.getText().toString().equals("admin") && editTextPassword.getText().toString().equals("admin")) {
                    textViewLoginMessage.setText("");
                    goToFragment(new MainMenuFragment());
                }
                else if(editTextUserName.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
                    textViewLoginMessage.setText("User Name or Password is Empty");
                }
                else{
                    textViewLoginMessage.setText("Invalid Credentials");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLoginMessage.setText("");
                getActivity().finish();
            }
        });
    }

    /**
     * Clear the login screen on back button press
     */
    @Override
    public void onResume(){
        super.onResume();
        editTextUserName.setText("");
        editTextPassword.setText("");
    }

    /**
     * goToFragment
     * @param newFragment
     */
    private void goToFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
