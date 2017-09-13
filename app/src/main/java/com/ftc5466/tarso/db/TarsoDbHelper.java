package com.ftc5466.tarso.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TarsoDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tarso.db";

    public TarsoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TarsoContract.TeamEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(TarsoContract.TeamEntry.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public ArrayList<TeamEntryInstance> getAllTeamEntries() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TarsoContract.TeamEntry.TABLE_NAME, null);
        ArrayList<TeamEntryInstance> results = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String teamName = cursor.getString(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NAME));
                int teamNumber = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NUMBER));

                // Autonomous
                boolean canKnockJewel = cursor.getInt(cursor.getColumnIndex(TarsoContract.TeamEntry.COLUMN_NAME_AUTONOMOUS_CAN_KNOCK_JEWEL)) == 1;

                TeamEntryInstance instance = new TeamEntryInstance(teamName, teamNumber);
                instance.canKnockJewel = canKnockJewel;
                results.add(instance);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return results;
    }
}
