package com.android.demoapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class GesturesFragment extends Fragment {

    TextView lastGestureLabel;
    GestureRecognizer recognizer = new GestureRecognizer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gesture_recognizer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity parentActivity = getActivity();
        lastGestureLabel = (TextView)parentActivity.findViewById(R.id.lastGesture);

        parentActivity.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                int pointerIndex = e.getActionIndex();
                int pointerId = e.getPointerId(pointerIndex);

                switch (MotionEventCompat.getActionMasked(e)) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("gesture detector", "ACTION_DOWN " + pointerId + " X " + e.getX(pointerIndex) + " Y " + e.getY(pointerIndex));
                        recognizer.pointerDown(e);

                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.i("gesture detector", "ACTION_POINTER_DOWN " + pointerId + " X "+ e.getX(pointerIndex) + " Y " + e.getY(pointerIndex));
                        recognizer.pointerDown(e);
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        Log.i("gesture detector", " ACTION_POINTER_UP " + pointerId + " X " + e.getX(pointerIndex) + " Y " + e.getY(pointerIndex) + " pointers " + e.getPointerCount());
                        recognizer.pointerUp(e);

                        lastGestureLabel.setText(recognizer.getGesture());
                        parentActivity.getWindow().getDecorView().invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("gesture detector", " ACTION_UP " + pointerId + " X "+ e.getX(pointerIndex) + " Y " + e.getY(pointerIndex) + " pointers " + e.getPointerCount());
                        recognizer.pointerUp(e);

                        lastGestureLabel.setText(recognizer.getGesture());
                        parentActivity.getWindow().getDecorView().invalidate();
                        break;
                }

                return false;
            }
        });
    }
}
