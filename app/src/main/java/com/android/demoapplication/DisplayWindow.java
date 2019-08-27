package com.android.demoapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DisplayWindow extends RelativeLayout {

	public DisplayWindow(Context context) {
		super(context);
		
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		// Add the layout params to the relativelayout
		this.setLayoutParams(rlp);
		
		Button button = new Button(context);
		button.setText("Button 1");
		button.setFocusable(true);
		button.setFocusableInTouchMode(true);
		
		// Defining the layout parameters of the Button one.
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		
		// Add the layout parameters to the relativelayout
		button.setLayoutParams(lp);
		
		// Add the view to the relativelayout
		this.addView(button);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Log.d("AndroidTest application", "This was clicked! Hooray!");
			}
		});
	}
}
