package com.ftc5466.tarso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class TarsoUser {
    private static final TarsoUser INSTANCE = new TarsoUser();
    private SharedPreferences sharedPreferences;

    private static final String ID_KEY = "TarsoUUID";
    private static final String FIRST_NAME_KEY = "TarsoFirstName";
    private static final String TEAM_NAME_KEY = "TarsoTeamName";
    private static final String TEAM_NUMBER_KEY = "TarsoTeamNumber";
    private static final String FIRST_TIME_KEY = "TarsoFirstTime";

    String id;
    String firstName;
    String teamName;
    int teamNumber;
    boolean firstTime;

    private TarsoUser() {}

    public static TarsoUser getInstance() {
        return INSTANCE;
    }

    public void setup(Activity activity) {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        id = sharedPreferences.getString(ID_KEY, UUID.randomUUID().toString());
        firstName = sharedPreferences.getString(FIRST_NAME_KEY, null);
        teamName = sharedPreferences.getString(TEAM_NAME_KEY, null);
        teamNumber = sharedPreferences.getInt(TEAM_NUMBER_KEY, -1);
        firstTime = sharedPreferences.getBoolean(FIRST_TIME_KEY, true);
    }

    public void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_KEY, id);
        editor.putString(FIRST_NAME_KEY, firstName);
        editor.putString(TEAM_NAME_KEY, teamName);
        editor.putInt(TEAM_NUMBER_KEY, teamNumber);
        editor.putBoolean(FIRST_TIME_KEY, false);
        editor.apply();
    }

    public void wipe() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
