package com.ftc5466.tarso.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
        Cursor cursor = getReadableDatabase().query(TarsoContract.TeamEntry.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<TeamEntryInstance> results = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                results.add(new TeamEntryInstance(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return results;
    }

    public boolean deleteTeamByName(String name) {
        String selection = TarsoContract.TeamEntry.COLUMN_NAME_TEAM_NAME + " = ?";
        String[] selectionArgs = {name};
        return getWritableDatabase().delete(TarsoContract.TeamEntry.TABLE_NAME, selection, selectionArgs) > 0;
    }
}