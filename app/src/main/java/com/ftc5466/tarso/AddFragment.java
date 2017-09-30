package com.ftc5466.tarso;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ftc5466.tarso.db.TarsoContract;
import com.ftc5466.tarso.db.TarsoDbHelper;
import com.ftc5466.tarso.db.TeamEntryInstance;

public class AddFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    /* View Elements */
    // Team
    EditText teamNameEditText;
    EditText teamNumberEditText;
    Button autoFillButton;

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

    /* AutoFill Types */
    private TeamEntryInstance autoFillTeamEntryInstance;
    private static final int AUTO_FILL_TYPE_NAME = 0;
    private static final int AUTO_FILL_TYPE_NUMBER = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add, container, false);

        /* View Elements */
        // Team
        teamNameEditText = view.findViewById(R.id.team_name_edittext);
        teamNumberEditText = view.findViewById(R.id.team_number_edittext);
        autoFillButton = view.findViewById(R.id.button_autofill);
        autoFillButton.setVisibility(View.GONE);

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

        teamNameEditText.addTextChangedListener(new AutoFillTextWatcher(AUTO_FILL_TYPE_NAME));
        teamNumberEditText.addTextChangedListener(new AutoFillTextWatcher(AUTO_FILL_TYPE_NUMBER));
        autoFillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                // Auto-Fill
                fillFromTeamInstance(autoFillTeamEntryInstance);
                autoFillTeamEntryInstance = null;
                autoFillButton.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        clear();

        recoverRelicCheckBox.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        relicUprightCheckBox.setEnabled(isChecked);
        if (!isChecked)
            relicUprightCheckBox.setChecked(false);

        for (CheckBox checkBox : relicRecoveryZones) {
            checkBox.setEnabled(isChecked);

            if (!isChecked)
                checkBox.setChecked(false);
        }
    }

    public void requestAdd() {
        String toastMessage = appendToDatabase();
        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void fillFromTeamInstance(TeamEntryInstance instance) {
        if (instance == null)
            return;

        // Team
        teamNameEditText.setText(instance.teamName);
        teamNumberEditText.setText(String.valueOf(instance.teamNumber));

        // Autonomous
        knockJewelCheckBox.setChecked(instance.canKnockJewel);
        scanPictographCheckBox.setChecked(instance.canScanPictograph);
        numberOfAutonomousGlyphsEditText.setText(String.valueOf(instance.numberOfAutonomousGlyphsScored));
        safeZoneCheckBox.setChecked(instance.parksInSafeZone);

        // TeleOp
        numberOfTeleOpGlyphsEditText.setText(String.valueOf(instance.numberOfTeleOpGlyphsScored));
        glyphsStrategyTeleOp[0].setChecked(instance.rowsStrategy);
        glyphsStrategyTeleOp[1].setChecked(instance.columnsStrategy);

        // End Game
        recoverRelicCheckBox.setChecked(instance.canRecoverRelic);
        if (instance.canRecoverRelic) {
            relicUprightCheckBox.setEnabled(true);
            relicUprightCheckBox.setChecked(instance.relicUpright);

            // Enable all
            for (CheckBox checkBox : relicRecoveryZones) {
                checkBox.setEnabled(true);
            }

            relicRecoveryZones[0].setChecked(instance.zone1);
            relicRecoveryZones[1].setChecked(instance.zone2);
            relicRecoveryZones[2].setChecked(instance.zone3);
        }

        balanceEndCheckBox.setChecked(instance.balanceAtEnd);
    }

    private String appendToDatabase() {
        /* Grab all data */
        // Team
        String teamName = teamNameEditText.getText().toString();
        int teamNumber;
        try {
            teamNumber = Integer.parseInt(teamNumberEditText.getText().toString());
        } catch (NumberFormatException e) {
            return "Error - Team Number Empty";
        }

        // Autonomous
        int canKnockJewel = bool(knockJewelCheckBox.isChecked());
        int canScanPictograph = bool(scanPictographCheckBox.isChecked());
        int autonomousGlyphsScored;
        try {
            autonomousGlyphsScored = Integer.parseInt(numberOfAutonomousGlyphsEditText.getText().toString());
        } catch (NumberFormatException e) {
            autonomousGlyphsScored = 0;
        }
        int parksInSafeZone = bool(safeZoneCheckBox.isChecked());

        // TeleOp
        int teleOpGlyphsScored;
        try {
            teleOpGlyphsScored = Integer.parseInt(numberOfTeleOpGlyphsEditText.getText().toString());
        } catch (NumberFormatException e) {
            teleOpGlyphsScored = 0;
        }
        int targetRows = bool(glyphsStrategyTeleOp[0].isChecked());
        int targetColumns = bool(glyphsStrategyTeleOp[1].isChecked());

        // End Game
        int canRecoverRelic = bool(recoverRelicCheckBox.isChecked());
        int relicUpright = bool(relicUprightCheckBox.isChecked());
        int firstRelicZone = bool(relicRecoveryZones[0].isChecked());
        int secondRelicZone = bool(relicRecoveryZones[1].isChecked());
        int thirdRelicZone = bool(relicRecoveryZones[2].isChecked());
        int balancedAtEnd = bool(balanceEndCheckBox.isChecked());

        // Make sure all information is valid
        if (teamName.isEmpty())
            return "Error - Team Name Empty";
        else if (recoverRelicCheckBox.isChecked() && !(relicRecoveryZones[0].isChecked() || relicRecoveryZones[1].isChecked() || relicRecoveryZones[2].isChecked()))
            return "Error - Select at least 1 Relic Recovery zone";

        clear();

        // Add to database
        SQLiteDatabase db = TarsoDbHelper.getInstance().getWritableDatabase();

        ContentValues values = new ContentValues();
        // Team
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NAME, teamName);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NUMBER, teamNumber);

        // Autonomous
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL, canKnockJewel);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_SCAN_PICTOGRAPH, canScanPictograph);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_NUMBER_OF_GLYPHS, autonomousGlyphsScored);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_PARK_SAFE_ZONE, parksInSafeZone);

        // TeleOp
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_NUMBER_OF_GLYPHS, teleOpGlyphsScored);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_ROWS, targetRows);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_COLUMNS, targetColumns);

        // End Game
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_CAN_RECOVER_RELIC, canRecoverRelic);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_UPRIGHT, relicUpright);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_1, firstRelicZone);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_2, secondRelicZone);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_3, thirdRelicZone);
        values.put(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_BALANCE, balancedAtEnd);

        boolean teamExists = TarsoDbHelper.getInstance().teamExists(teamNumber);
        if (teamExists) {
            db.update(TarsoContract.TeamEntry.TABLE_NAME, values, null, null);
        } else {
            db.insert(TarsoContract.TeamEntry.TABLE_NAME, null, values);
        }

        return teamExists ? "Success - Updated team " + teamNumber : "Success - Added team " + teamNumber;
    }

    // Please forgive me.
    private int bool(boolean input) {
        return input ? 1 : 0;
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

    private class AutoFillTextWatcher implements TextWatcher {
        private int type;

        AutoFillTextWatcher(int autoFillType) {
            this.type = autoFillType;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            TeamEntryInstance instance = null;

            if (type == AUTO_FILL_TYPE_NAME) {
                instance = attemptAutoFillByName(charSequence.toString());
            } else {
                try {
                    instance = attemptAutoFillByNumber(Integer.parseInt(charSequence.toString()));
                } catch (NumberFormatException ignored) {}
            }

            if (instance != null) {
                autoFillButton.setVisibility(View.VISIBLE);
                autoFillTeamEntryInstance = instance;
            } else {
                autoFillButton.setVisibility(View.GONE);
                autoFillTeamEntryInstance = null;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}

        private TeamEntryInstance attemptAutoFillByName(String teamName) {
            return TarsoDbHelper.getInstance().getTeamByName(teamName);
        }

        private TeamEntryInstance attemptAutoFillByNumber(int teamNumber) {
            return TarsoDbHelper.getInstance().getTeamByNumber(teamNumber);
        }
    }
}
