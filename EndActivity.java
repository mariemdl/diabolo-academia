package com.example.diaboloacademia;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity{
    TextView tvEndMessage;
    Button btnRestart;
    DatabaseHelper db;
    String username;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        tvEndMessage = findViewById(R.id.tv_endMessage);
        btnRestart = findViewById(R.id.btn_restart);
        db = new DatabaseHelper(this);

        username = getIntent().getStringExtra("USERNAME");
        user = db.getUserByName(username);

        int aff = user.getAffection();
        if(aff >= 5) tvEndMessage.setText("Good End! Lucien appreciates you a lot!");
        else if(aff >= 2) tvEndMessage.setText("Neutral end. Lucien knows u a little bit.");
        else tvEndMessage.setText("Bad End. Lucien is disappointed.");

        btnRestart.setOnClickListener(v -> {
            user.setAffection(0);
            user.setLastNode("start");
            db.updateUser(user);
            Intent intent = new Intent(this, StoryActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });
    }
}
