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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    /* View Elements */
    // Team
    EditText teamNameEditText;
    EditText teamNumberEditText;

    // Autonomous
    CheckBox knockJewelCheckBox;
    CheckBox scanPictographCheckBox;
    EditText numberOfAutonomousGlyphsEditText;
    CheckBox safeZoneCheckBox;

    // TeleOp
    EditText numberOfTeleOpGlyphsEditText;
    CheckBox[] glyphsStrategyTeleOp = new CheckBox[2];

    // End Game
    CheckBox recoverRelicCheckBox;
    CheckBox relicUprightCheckBox;
    CheckBox[] relicRecoveryZones = new CheckBox[3];
    CheckBox balanceEndCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        /* View Elements */
        // Team
        teamNameEditText = view.findViewById(R.id.team_name_edittext);
        teamNumberEditText = view.findViewById(R.id.team_number_edittext);

        // Autonomous
        knockJewelCheckBox = view.findViewById(R.id.autonomous_knock_jewl_checkbox);
        scanPictographCheckBox = view.findViewById(R.id.autonomous_scan_pictograph_checkbox);
        numberOfAutonomousGlyphsEditText = view.findViewById(R.id.autonomous_glyphs_in_cryptograph_editText);
        safeZoneCheckBox = view.findViewById(R.id.autonomous_park_safe_zone_checkbox);

        // TeleOp
        numberOfTeleOpGlyphsEditText = view.findViewById(R.id.teleop_glyphs_scored_editText);
        glyphsStrategyTeleOp[0] = view.findViewById(R.id.teleop_rows_checkbox);
        glyphsStrategyTeleOp[1] = view.findViewById(R.id.teleop_columns_checkbox);

        // End game
        recoverRelicCheckBox = view.findViewById(R.id.endgame_can_recover_relic);
        relicUprightCheckBox = view.findViewById(R.id.endgame_relic_upright);

        relicRecoveryZones[0] = view.findViewById(R.id.endgame_relic_zone_10);
        relicRecoveryZones[1] = view.findViewById(R.id.endgame_relic_zone_20);
        relicRecoveryZones[2] = view.findViewById(R.id.endgame_relic_zone_40);

        balanceEndCheckBox = view.findViewById(R.id.endgame_balanced_end_checkbox);
        /* Done setting up View Elements*/

        clear();

        recoverRelicCheckBox.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        relicUprightCheckBox.setEnabled(isChecked);

        for (CheckBox checkBox : relicRecoveryZones) {
            checkBox.setEnabled(isChecked);
        }
    }

    public void requestAdd() {
        String toastMessage = appendToDatabase();
        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    private String appendToDatabase() {
        // Grab all data
        String teamName = teamNameEditText.getText().toString();
        String teamNumber = teamNumberEditText.getText().toString();

        // Make sure all information is valid
        if (teamName.isEmpty())
            return "Error - Team Name Empty";
        else if (teamNumber.isEmpty())
            return "Error - Team Number Empty";

        clear();
        return "Success";
    }

    private void clear() {
        // Team
        teamNameEditText.setText("");
        teamNumberEditText.setText("");

        // Autonomous
        knockJewelCheckBox.setChecked(false);
        scanPictographCheckBox.setChecked(false);
        numberOfAutonomousGlyphsEditText.setText("");
        safeZoneCheckBox.setChecked(false);

        // TeleOp
        numberOfTeleOpGlyphsEditText.setText("");
        for (CheckBox checkBox : glyphsStrategyTeleOp) {
            checkBox.setChecked(false);
        }

        // End Game
        recoverRelicCheckBox.setChecked(false);
        relicUprightCheckBox.setChecked(false);
        relicUprightCheckBox.setEnabled(false);

        for (CheckBox checkBox : relicRecoveryZones) {
            checkBox.setEnabled(false);
            checkBox.setChecked(false);
        }

        balanceEndCheckBox.setChecked(false);
    }
}
