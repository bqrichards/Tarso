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
    public int numberOfTeleOpGlyphsScored = 0;
    public boolean rowsStrategy = false;
    public boolean columnsStrategy = false;

    // End Game
    public boolean canRecoverRelic = false;
    public boolean relicUpright = false;
    public boolean zone1 = false;
    public boolean zone2 = false;
    public boolean zone3 = false;
    public boolean balanceAtEnd = false;

    public TeamEntryInstance(Cursor cursor) {
        this.teamName = cursor.getString(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NAME));
        this.teamNumber = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NUMBER));

        // Autonomous
        this.canKnockJewel = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL)) == 1;
        this.canScanPictograph = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_SCAN_PICTOGRAPH)) == 1;
        this.numberOfAutonomousGlyphsScored = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_NUMBER_OF_GLYPHS));
        this.parksInSafeZone = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_PARK_SAFE_ZONE)) == 1;

        // TeleOp
        this.numberOfTeleOpGlyphsScored = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_NUMBER_OF_GLYPHS));
        this.rowsStrategy = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_ROWS)) == 1;
        this.columnsStrategy = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_COLUMNS)) == 1;

        // End Game
        this.canRecoverRelic = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_CAN_RECOVER_RELIC)) == 1;
        this.relicUpright = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_UPRIGHT)) == 1;
        this.zone1 = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_1)) == 1;
        this.zone2 = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_2)) == 1;
        this.zone3 = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_3)) == 1;
        this.balanceAtEnd = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_ENDGAME_BALANCE)) == 1;
    }

    public List<String> getChildren() {
        ArrayList<String> children = new ArrayList<>();

        // Autonomous
        children.add("Can knock Jewel: " + getYesNo(canKnockJewel));
        children.add("Can scan Pictograph: " + getYesNo(canScanPictograph));
        children.add("Avg. number of Glyphs: " + numberOfAutonomousGlyphsScored);
        children.add("Parks in Safe Zone: " + getYesNo(parksInSafeZone));

        // TeleOp
        children.add("Avg. number of Glyphs: " + numberOfTeleOpGlyphsScored);
        children.add("Rows strategy: " + getYesNo(rowsStrategy));
        children.add("Columns strategy: " + getYesNo(columnsStrategy));

        // End Game
        children.add("Can recover relic: " + getYesNo(canRecoverRelic));
        children.add("Relic upright: " + getYesNo(relicUpright));

        String zonesString = "Zones: ";
        if (zone1) zonesString += "1 | ";
        if (zone2) zonesString += "2 | ";
        if (zone3) zonesString += "3";

        children.add(zonesString);

        children.add("Balanced at end: " + getYesNo(balanceAtEnd));

        return children;
    }

    // Please forgive me.
    private String getYesNo(boolean test) {
        return test ? "Yes" : "No";
    }
}
