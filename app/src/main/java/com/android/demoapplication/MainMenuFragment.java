package com.android.demoapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        Activity activity = getActivity();
        Button btnTest = (Button)activity.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Hello World", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button btnGoButtons = (Button)activity.findViewById(R.id.btnGoButtons);
        btnGoButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new ButtonsFragment());
            }
        });

        Button goSharedButtons = (Button)activity.findViewById(R.id.btnGoSharedButtons);
        goSharedButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new SharedButtonsFragment());
            }
        });

        Button goText = (Button)activity.findViewById(R.id.btnGoText);
        goText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new TextFormFragment2());
            }
        });

        Button goSelections = (Button)activity.findViewById(R.id.btnGoSelections);
        goSelections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new SelectionsFragment());
            }
        });

        Button goExpando = (Button)activity.findViewById(R.id.btnGoExpandableList);
        goExpando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new ExpandoListFragment());
            }
        });

        Button goDateTime = (Button)activity.findViewById(R.id.btnGoDateTime);
        goDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new DateTimeFragment());
            }
        });

        Button goMisc = (Button)activity.findViewById(R.id.btnGoMisc);
        goMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new MiscControlsFragment());
            }
        });

        Button goWebView = (Button)activity.findViewById(R.id.btnGoWebView);
        goWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new WebViewFragment());
            }
        });

        Button goDrawers = (Button)activity.findViewById(R.id.btnGoDrawers);
        goDrawers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new DrawersFragment());
            }
        });

        Button goHoneycomb = (Button)activity.findViewById(R.id.btnGoHoneycomb);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            goHoneycomb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToFragment(new HoneycombFragment());
                }
            });
        } else {
            goHoneycomb.setEnabled(false);
        }

        Button goIceCreamSandwich = (Button)activity.findViewById(R.id.btnGoIceCreamSandwich);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            goIceCreamSandwich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToFragment(new IceCreamSandwichFragment());
                }
            });
        } else {
            goIceCreamSandwich.setEnabled(false);
        }

        Button goGestures = (Button)activity.findViewById(R.id.btnGoGestures);
        goGestures.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToFragment(new GesturesFragment());
            }
        });

        Button goDragZoomRotate = (Button)activity.findViewById(R.id.btnGoDragZoomRotate);
        goDragZoomRotate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToFragment(new DragZoomRotateFragment());
            }
        });

        Button goSecondaryActivity = (Button)activity.findViewById(R.id.btnGoSecondaryActivity);
        goSecondaryActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecondaryActivity.class);
                startActivity(intent);
            }
        });

        Button goXWalkWebView = (Button)activity.findViewById(R.id.btnGoXWalkWebView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            goXWalkWebView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToFragment(new XWalkWebViewFragment());
                }
            });
        } else {
            goXWalkWebView.setEnabled(false);
        }

        Button btnFormView = (Button)activity.findViewById(R.id.btnForm);
        btnFormView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragment(new TextFormFragment2());
            }
        });
    }

    private void goToFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
