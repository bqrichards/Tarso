package com.ftc5466.tarso;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftc5466.tarso.db.TarsoDbHelper;
import com.ftc5466.tarso.db.TeamEntryInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewFragment extends Fragment {
    private ArrayList<TeamEntryInstance> listData;
    private TeamViewExpandableListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        refreshData();

        ExpandableListView teamListView = view.findViewById(R.id.team_view_expandable_list_view);
        adapter = new TeamViewExpandableListAdapter();
        teamListView.setAdapter(adapter);

        teamListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to delete this item?");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete group
                        String teamName = (String)adapter.getGroup(position / 2);

                        String toastMessage;
                        int toastLength;
                        if (TarsoDbHelper.getInstance().deleteTeamByName(teamName)) {
                            toastMessage = "Successfully deleted team " + teamName + ".";
                            toastLength = Toast.LENGTH_SHORT;
                            refreshData();
                        } else {
                            toastMessage = "Uh oh, something went wrong. Check the logs?";
                            toastLength = Toast.LENGTH_LONG;
                        }

                        Toast.makeText(getContext(), toastMessage, toastLength).show();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);
                builder.show();

                return true;
            }
        });

        return view;
    }

    public void refreshData() {
        listData = TarsoDbHelper.getInstance().getAllTeamEntries();

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private class TeamViewExpandableListAdapter extends BaseExpandableListAdapter {
        private List<String> _listDataHeader;
        private HashMap<String, List<String>> _listDataChildren;

        private TeamViewExpandableListAdapter() {
            craftData();
        }

        @Override
        public int getGroupCount() {
            return _listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return 1;
        }

        @Override
        public Object getGroup(int i) {
            return _listDataHeader.get(i);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return _listDataChildren.get(_listDataHeader.get(groupPosition));
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
            final List<String> childText = (ArrayList<String>)getChild(groupPosition, childPosition);

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, null);
            }

            /* Grab View Elements */
            // Autonomous
            TextView canKnockJewelTextView = view.findViewById(R.id.list_item_can_knock_jewel);
            TextView canScanPictographTextView = view.findViewById(R.id.list_item_can_scan_pictograph);
            TextView avgNumberOfAutonomousGlyphsTextView = view.findViewById(R.id.list_item_autonomous_number_of_glyphs);
            TextView parksInAutonomousSafeZone = view.findViewById(R.id.list_item_autonomous_parks_in_safe_zone);

            // TeleOp
            TextView avgNumberOfTeleOpGlyphsTextView = view.findViewById(R.id.list_item_teleop_number_of_glyphs);
            TextView rowsStrategyTextView = view.findViewById(R.id.list_item_teleop_rows_strategy);
            TextView columnsStrategyTextView = view.findViewById(R.id.list_item_teleop_columns_strategy);

            // End Game
            TextView canRecoverRelicTextView = view.findViewById(R.id.list_item_endgame_can_recover_relic);
            TextView relicUprightTextView = view.findViewById(R.id.list_item_endgame_relic_upright);
            TextView zonesTextView = view.findViewById(R.id.list_item_endgame_zone);
            TextView balancedAtEndTextView = view.findViewById(R.id.list_item_endgame_balance_at_end);

            /* Set Text */
            // Autonomous
            canKnockJewelTextView.setText(childText.get(0));
            canScanPictographTextView.setText(childText.get(1));
            avgNumberOfAutonomousGlyphsTextView.setText(childText.get(2));
            parksInAutonomousSafeZone.setText(childText.get(3));

            // TeleOp
            avgNumberOfTeleOpGlyphsTextView.setText(childText.get(4));
            rowsStrategyTextView.setText(childText.get(5));
            columnsStrategyTextView.setText(childText.get(6));

            // End Game
            canRecoverRelicTextView.setText(childText.get(7));
            relicUprightTextView.setText(childText.get(8));
            zonesTextView.setText(childText.get(9));
            balancedAtEndTextView.setText(childText.get(10));

            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public void notifyDataSetChanged() {
            craftData();
            super.notifyDataSetChanged();
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
