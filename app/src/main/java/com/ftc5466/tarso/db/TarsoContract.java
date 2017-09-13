package com.ftc5466.tarso.db;

import android.provider.BaseColumns;

public final class TarsoContract {
    private TarsoContract() {} // Don't allow instantiating

    public static class TeamEntry implements BaseColumns {
        public static final String TABLE_NAME = "teamentry";

        /* Team Information */
        public static final String COLUMN_NAME_TEAM_NAME = "teamname";
        public static final String COLUMN_NAME_TEAM_NUMBER = "teamnumber";

        /* Scoring - Autonomous */
        public static final String COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL = "autonomouscanknockjewel";
        public static final String COLUMN_NAME_AUTONOMOUS_CAN_SCAN_PICTOGRAPH = "autonomouscanscanpictograph";
        public static final String COLUMN_NAME_AUTONOMOUS_NUMBER_OF_GLYPHS = "autonomousnumberofglyphs";
        public static final String COLUMN_NAME_AUTONOMOUS_CAN_PARK_SAFE_ZONE = "autonomouscanparksafezone";

        /* Scoring - TeleOp */
        public static final String COLUMN_NAME_TELEOP_NUMBER_OF_GLYPHS = "teleopnumberofglyphs";
        public static final String COLUMN_NAME_TELEOP_GLYPH_STRATEGY_ROWS = "teleopglyphstrategyrows";
        public static final String COLUMN_NAME_TELEOP_GLYPH_STRATEGY_COLUMNS = "teleopglyphstrategycolumns";

        /* Scoring - End Game */
        public static final String COLUMN_NAME_ENDGAME_CAN_RECOVER_RELIC = "endgamecanrecoverrelic";
        public static final String COLUMN_NAME_ENDGAME_RELIC_UPRIGHT = "endgamerelicupright";
        public static final String COLUMN_NAME_ENDGAME_RELIC_ZONE_1 = "endgamereliczoneone";
        public static final String COLUMN_NAME_ENDGAME_RELIC_ZONE_2 = "endgamereliczonetwo";
        public static final String COLUMN_NAME_ENDGAME_RELIC_ZONE_3 = "endgamereliczonethree";
        public static final String COLUMN_NAME_ENDGAME_BALANCE = "endgamebalance";

        /* CREATE SQL DATABASE */
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TeamEntry.TABLE_NAME + " (" +
                TeamEntry._ID + " INTEGER PRIMARY KEY," +
                TeamEntry.COLUMN_NAME_TEAM_NAME + " TEXT," +
                TeamEntry.COLUMN_NAME_TEAM_NUMBER + " INTEGER," +

                TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL + " INTEGER," +
                TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_SCAN_PICTOGRAPH + " INTEGER," +
                TeamEntry.COLUMN_NAME_AUTONOMOUS_NUMBER_OF_GLYPHS + " INTEGER," +
                TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_PARK_SAFE_ZONE + " INTEGER," +

                TeamEntry.COLUMN_NAME_TELEOP_NUMBER_OF_GLYPHS + " INTEGER," +
                TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_ROWS + " INTEGER," +
                TeamEntry.COLUMN_NAME_TELEOP_GLYPH_STRATEGY_COLUMNS + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_CAN_RECOVER_RELIC + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_RELIC_UPRIGHT + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_1 + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_2 + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_RELIC_ZONE_3 + " INTEGER," +
                TeamEntry.COLUMN_NAME_ENDGAME_BALANCE + " INTEGER);";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TeamEntry.TABLE_NAME;
    }
}
