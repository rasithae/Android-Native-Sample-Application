package com.android.demoapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

public class DemoApplicationActivity extends FragmentActivity {

	/** Called when the activity is created or recreated. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_app);
        if (savedInstanceState == null) {
            // Only set the main menu fragment when the activity is first created
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragmentContainer, new LoginFragment());
            transaction.commit();
        }
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment instanceof KeyHandling) {
            ((KeyHandling)currentFragment).onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}