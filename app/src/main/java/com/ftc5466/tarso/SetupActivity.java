package com.ftc5466.tarso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {
    EditText firstNameEditText, teamNameEditText, teamNumberEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        firstNameEditText = (EditText)findViewById(R.id.edit_text_first_name);
        teamNameEditText = (EditText)findViewById(R.id.edit_text_team_name);
        teamNumberEditText = (EditText)findViewById(R.id.edit_text_team_number);

        findViewById(R.id.button_done_setting_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String teamName = teamNameEditText.getText().toString();
                int teamNumber = Integer.parseInt(teamNumberEditText.getText().toString());

                TarsoUser.getInstance().firstName = firstName;
                TarsoUser.getInstance().teamName = teamName;
                TarsoUser.getInstance().teamNumber = teamNumber;
                TarsoUser.getInstance().save();

                setResult(1);
                finish();
            }
        });
    }
}
