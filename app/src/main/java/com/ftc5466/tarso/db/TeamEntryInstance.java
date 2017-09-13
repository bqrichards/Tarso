package com.ftc5466.tarso.db;

import java.util.ArrayList;
import java.util.List;

public class TeamEntryInstance {
    // Team
    public String teamName;
    public int teamNumber;

    // Autonomous
    public boolean canKnockJewel = false;

    // TeleOp

    // End Game

    public TeamEntryInstance(String teamName, int teamNumber) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
    }

    public List<String> getChildren() {
        ArrayList<String> children = new ArrayList<>();
        children.add("Can knock Jewel: " + getYesNo(canKnockJewel));

        return children;
    }

    // Please forgive me.
    private String getYesNo(boolean test) {
        return test ? "Yes" : "No";
    }
}
