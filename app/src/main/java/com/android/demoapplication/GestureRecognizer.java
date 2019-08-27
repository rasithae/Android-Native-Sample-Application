package com.android.demoapplication;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GestureRecognizer {

    final private class GesturePoint {
        public final int x;
        public final int y;
        public final Date time;

        public GesturePoint(int x, int y) {
            this.x = x;
            this.y = y;
            this.time = new Date();
        }
    }

    private final static int SWIPE_TOLERANCE = 100;
    private final static int PINCH_TOLERANCE = 200;
    private final static int ROTATION__ANGLE_TOLERANCE = 10;
    private final static int LONG_TAP_DELAY = 500;
    private final static int DOUBLE_TAP_DELAY = 500;

    private String gesture = "No gesture";
    private String lastGesture = "No gesture";
    private Date lastGestureTime = new Date();


    private int pointerCount = 0;
    private final HashMap<Integer, ArrayList<GesturePoint>> pointerPaths = new HashMap<>();

    private void cleanUp() {
        pointerPaths.clear();
    }

    private String analyzeOnePointerGesture() {
        ArrayList<GesturePoint> list = pointerPaths.get(0);
        GesturePoint start = list.get(0);
        GesturePoint end = list.get(1);

        if (start.x - end.x > SWIPE_TOLERANCE && Math.abs(start.y - end.y) < SWIPE_TOLERANCE) {
            return  "Swipe Left";
        }
        else if (start.x - end.x < -SWIPE_TOLERANCE && Math.abs(start.y - end.y) < SWIPE_TOLERANCE) {
            return  "Swipe Right";
        }
        else if (start.y - end.y > SWIPE_TOLERANCE && Math.abs(start.x - end.x) < SWIPE_TOLERANCE) {
            return "Swipe Up";
        }
        else if (start.y - end.y < -SWIPE_TOLERANCE && Math.abs(start.x - end.x) < SWIPE_TOLERANCE) {
            return "Swipe Down";
        }
        else if (Math.abs(start.x - end.x) < SWIPE_TOLERANCE && Math.abs(start.y - end.y) < SWIPE_TOLERANCE) {
            if ((end.time.getTime() - start.time.getTime() > LONG_TAP_DELAY)) {
                return "Long Press";
            }
            else if ((lastGesture == "Tap" || lastGesture == "Double Tap")&&
                     new Date().getTime() - lastGestureTime.getTime() < DOUBLE_TAP_DELAY ) {
                return "Double Tap";
            }

            return "Tap";
        }

        return "Pan";
    }

    private String analyzeTwoPointerGesture() {
        ArrayList<GesturePoint> p1Path = pointerPaths.get(0);
        GesturePoint p1s = p1Path.get(0);
        GesturePoint p1e = p1Path.get(1);

        ArrayList<GesturePoint> p2Path = pointerPaths.get(1);
        GesturePoint p2s = p2Path.get(0);
        GesturePoint p2e = p2Path.get(1);

        double startDistance = Math.sqrt(Math.pow(p1s.x - p2s.x, 2) + Math.pow( p1s.y - p2s.y, 2));
        double endDistance = Math.sqrt(Math.pow(p1e.x - p2e.x, 2) + Math.pow(p1e.y - p2e.y, 2));

        if (startDistance > endDistance + PINCH_TOLERANCE) {
            return "Pinch In";
        }
        else if (startDistance < endDistance - PINCH_TOLERANCE) {
            return "Pinch Out";
        }
        // Check the difference in vector angle. If it is more than ROTATION__ANGLE_TOLERANCE detect rotation.
        else if (Math.abs(Math.toDegrees(Math.atan2(Math.abs(p1s.y - p2s.y), Math.abs(p1s.x - p2s.x))) -
                          Math.toDegrees(Math.atan2(Math.abs(p1e.y - p2e.y), Math.abs(p1e.x - p2e.x)))) > ROTATION__ANGLE_TOLERANCE) {
            return "Rotate";
        }

        return "Unknown two finger gesture";
    }

    private void detectGesture() {
        lastGesture = gesture;

        if (pointerCount > 2 ) {
            gesture = "Three or more finger gesture";
        }
        else if (pointerCount == 2) {
            gesture = analyzeTwoPointerGesture();
        }
        else if (pointerCount == 1) {
            gesture = analyzeOnePointerGesture();
        }
        else {
            gesture = "No idea what that was";
        }

        lastGestureTime = new Date();
    }

    public void pointerDown(MotionEvent e) {

        pointerCount = e.getPointerCount();

        for (int ii = 0; ii < pointerCount; ii++) {
            ArrayList<GesturePoint> lst = new ArrayList<>();
            lst.add(new GesturePoint((int)e.getX(ii), (int)e.getY(ii)));

            pointerPaths.put(ii, lst);
        }
    }

    public void pointerMove(MotionEvent e) {
        // Do nothing
    }

    public void pointerUp(MotionEvent e) {

        if (pointerCount != e.getPointerCount() ) return;

        for (int ii = 0; ii < pointerCount; ii++) {
            ArrayList<GesturePoint> lst = pointerPaths.get(ii);
            lst.add(new GesturePoint((int)e.getX(ii), (int)e.getY(ii)));
        }

        detectGesture();
        cleanUp();
    }

    public String getGesture() {
        return gesture;
    }
}
