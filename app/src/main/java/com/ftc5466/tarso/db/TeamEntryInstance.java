package com.ftc5466.tarso.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TeamEntryInstance {
    // Team
    public String teamName;
    public int teamNumber;

    // Autonomous
    public boolean canKnockJewel = false;
    public boolean canScanPictograph = false;
    public int numberOfAutonomousGlyphsScored = 0;
    public boolean parksInSafeZone = false;

    // TeleOp

    // End Game

    public TeamEntryInstance(String teamName, int teamNumber) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
    }

    public TeamEntryInstance(Cursor cursor) {
        this.teamName = cursor.getString(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NAME));
        this.teamNumber = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NUMBER));

        // Autonomous
        this.canKnockJewel = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL)) == 1;
        this.canScanPictograph = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_SCAN_PICTOGRAPH)) == 1;
        this.numberOfAutonomousGlyphsScored = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_NUMBER_OF_GLYPHS));
        this.parksInSafeZone = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_PARK_SAFE_ZONE)) == 1;
    }

    public List<String> getChildren() {
        ArrayList<String> children = new ArrayList<>();
        children.add("Can knock Jewel: " + getYesNo(canKnockJewel));
        children.add("Can scan Pictograph: " + getYesNo(canScanPictograph));
        children.add("Avg. number of Glyphs: " + numberOfAutonomousGlyphsScored);
        children.add("Parks in Safe Zone: " + getYesNo(parksInSafeZone));

        return children;
    }

    // Please forgive me.
    private String getYesNo(boolean test) {
        return test ? "Yes" : "No";
    }
}
