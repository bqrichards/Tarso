package com.ftc5466.tarso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

public class AddFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    /* View Elements */
    CheckBox canRecoverRelic;
    CheckBox relicUpright;
    RadioGroup relicZoneRadioZone;

    public AddFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // End game
        canRecoverRelic = view.findViewById(R.id.endgame_can_recover_relic);
        relicUpright = view.findViewById(R.id.endgame_relic_upright);
        relicZoneRadioZone = view.findViewById(R.id.endgame_relic_zone_radiogroup);

        relicUpright.setEnabled(false);
        relicZoneRadioZone.setEnabled(false);

        for (int i = 0; i < relicZoneRadioZone.getChildCount(); i++) {
            relicZoneRadioZone.getChildAt(i).setEnabled(false);
        }

        canRecoverRelic.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        relicUpright.setEnabled(isChecked);

        for (int i = 0; i < relicZoneRadioZone.getChildCount(); i++) {
            relicZoneRadioZone.getChildAt(i).setEnabled(isChecked);
        }
    }
}
