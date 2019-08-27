package com.android.demoapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ExpandoListFragment extends Fragment {
    private static final String[] PLANETS = { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Neptune", "Uranus", "Pluto" };
    private static final String[] ANIMALS = { "Aardvark", "Buffalo", "Cougar", "Dolphin", "Emu" };
    private static final String[] COLORS = { "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expando_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView feedback = (TextView)getActivity().findViewById(R.id.feedback);

        ExpandableListView expando = (ExpandableListView)getActivity().findViewById(R.id.expandoList1);
        expando.setAdapter(expandoListAdapter(getLayoutInflater(savedInstanceState)));
        expando.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                feedback.setText(String.format("Group %s expanded", nameForGroup(groupPosition)));
            }
        });
        expando.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                feedback.setText(String.format("Group %s collapsed", nameForGroup(groupPosition)));
            }
        });
        expando.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                String itemText = getGroup(groupPosition)[childPosition];
                feedback.setText(String.format("Selected %s", itemText));
                return true;
            }
        });
    }

    private static String nameForGroup(int groupPosition) {
        switch (groupPosition) {
            case 0: return "Planets";
            case 1: return "Animals";
            case 2: return "Colors";
            default: return "";
        }
    }

    private static String[] getGroup(int groupPosition) {
        switch (groupPosition) {
            case 0: // planets
                return PLANETS;
            case 1: // animals
                return ANIMALS;
            case 2: // colors
                return COLORS;
            default:
                return new String[0];
        }
    }

    private ExpandableListAdapter expandoListAdapter(LayoutInflater layoutInflater) {
        return new ComplicatedAssListAdapter(layoutInflater);
    }

    // Planets: Mercury, Venus, Earth, Mars, Jupiter, Saturn, Neptune, Uranus, Pluto
    // Animals: Aardvark, Buffalo, Cougar, Dolphin, Emu
    // Colors: Red, Orange, Yellow, Green, Blue, Indigo, Violet
    private static class ComplicatedAssListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater mLayoutInflater;
        ComplicatedAssListAdapter(LayoutInflater layoutInflater) {
            mLayoutInflater = layoutInflater;
        }

        @Override
        public int getGroupCount() {
            return 3;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return ((String[])getGroup(groupPosition)).length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return ExpandoListFragment.getGroup(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return ((String[])getGroup(groupPosition))[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            }
            TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
            tv.setText(nameForGroup(groupPosition));

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView tv = (TextView)convertView;
            tv.setText(getChild(groupPosition, childPosition).toString());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i2) {
            return true;
        }
    }
}
