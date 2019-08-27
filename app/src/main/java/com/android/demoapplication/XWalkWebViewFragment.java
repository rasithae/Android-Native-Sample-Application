package com.android.demoapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import org.xwalk.core.XWalkView;

/**
 * Created by shoerob on 10/9/15.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class XWalkWebViewFragment extends Fragment {

    private final static String TAG = "DemoApplication";

    private final static String DEMO_APP_URL =  "http://demos.telerik.com/mobile-testing/tacos/";

    //private XWalkView mXWalkView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xwalk_webview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mXWalkView = (XWalkView) getActivity().findViewById(R.id.xwalk_view);
//        mXWalkView.load(DEMO_APP_URL, null);
    }
}
