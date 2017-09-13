package com.ftc5466.tarso;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ftc5466.tarso.db.TarsoDbHelper;
import com.ftc5466.tarso.db.TeamEntryInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewFragment extends Fragment {
    private ArrayList<TeamEntryInstance> listData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        listData = new TarsoDbHelper(getContext()).getAllTeamEntries();

        ExpandableListView teamListView = view.findViewById(R.id.team_view_expandable_list_view);
        TeamViewExpandableListAdapter adapter = new TeamViewExpandableListAdapter();
        teamListView.setAdapter(adapter);

        return view;
    }

    private class TeamViewExpandableListAdapter extends BaseExpandableListAdapter {
        private List<String> _listDataHeader;
        private HashMap<String, List<String>> _listDataChildren;

        public TeamViewExpandableListAdapter() {
            craftData();
        }

        @Override
        public int getGroupCount() {
            return _listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return _listDataChildren.get(_listDataHeader.get(i)).size();
        }

        @Override
        public Object getGroup(int i) {
            return _listDataHeader.get(i);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return _listDataChildren.get(_listDataHeader.get(groupPosition)).get(childPosition);
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
        public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
            String headerTitle = (String)getGroup(groupPosition);

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_group, null);
            }

            TextView headerTextView = view.findViewById(R.id.list_header);
            headerTextView.setText(headerTitle);

            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
            final String childText = (String)getChild(groupPosition, childPosition);

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, null);
            }

            TextView canKnockJewelTextView = view.findViewById(R.id.list_item_can_knock_jewel);
            canKnockJewelTextView.setText(childText);

            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private void craftData() {
            ArrayList<String> headers = new ArrayList<>();
            HashMap<String, List<String>> children = new HashMap<>();

            for (TeamEntryInstance instance : listData) {
                String header = instance.teamName;

                headers.add(header);
                children.put(header, instance.getChildren());
            }

            _listDataHeader = headers;
            _listDataChildren = children;
        }
    }
}
