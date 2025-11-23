package com.example.diaboloacademia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnCreate, btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_username);
        btnCreate = findViewById(R.id.btn_create);
        btnLogin = findViewById(R.id.btn_login);

        db = new DatabaseHelper(this);

        btnCreate.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if(username.isEmpty()) {
                Toast.makeText(this, "Entrez un nom valide", Toast.LENGTH_SHORT).show();
            } else {
                if(db.getUserByName(username) != null){
                    Toast.makeText(this, "Nom déjà utilisé", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.createUser(username);
                Toast.makeText(this, "Utilisateur créé!", Toast.LENGTH_SHORT).show();
                startStory(username);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            User user = db.getUserByName(username);
            if(user == null){
                Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            } else {
                startStory(username);
            }
        });
    }

    private void startStory(String username){
        Intent intent = new Intent(MainActivity.this, StoryActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
