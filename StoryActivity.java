package com.example.diaboloacademia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StoryActivity extends AppCompatActivity {

    // UI Elements
    TextView tvSpeaker, tvDialogue;
    Button btnChoice1, btnChoice2, btnChoice3;
    ImageView imgCharacter;

    // Logic & Data
    DatabaseHelper db;
    User user;
    String username;
    ArrayList<DialogueNode> nodes;

    // Audio Manager
    SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // 1. Initialize Views
        tvSpeaker = findViewById(R.id.tv_speaker);
        tvDialogue = findViewById(R.id.tv_dialogue);
        btnChoice1 = findViewById(R.id.btn_choice1);
        btnChoice2 = findViewById(R.id.btn_choice2);
        btnChoice3 = findViewById(R.id.btn_choice3);
        imgCharacter = findViewById(R.id.img_character);

        // 2. Initialize Helper Classes
        db = new DatabaseHelper(this);
        soundManager = new SoundManager(this);

        // 3. Get User Data
        username = getIntent().getStringExtra("USERNAME");
        user = db.getUserByName(username);

        // 4. Load Scenario
        nodes = loadDialogueJSON();

        // 5. Start Music
         soundManager.startBgm(R.raw.bgm_loop);

        // 6. Start the Story
        showNode(user.getLastNode());
    }

    private void showNode(String id){
        DialogueNode node = getNodeById(id);
        if(node == null) return;

        // Update Text
        tvSpeaker.setText(node.getSpeaker());
        String text = node.getText().replace("{player}", username);
        tvDialogue.setText(text);

        // Update Image (Only if image exists in JSON)
        if(node.getImage() != null && !node.getImage().isEmpty()){
            int resId = getResources().getIdentifier(node.getImage(), "drawable", getPackageName());
            // Only set image if resource is found (avoids crash if image missing)
            if(resId != 0) {
                imgCharacter.setImageResource(resId);
            }
        }

        // Update Buttons
        Button[] buttons = {btnChoice1, btnChoice2, btnChoice3};
        for(int i=0; i<3; i++){
            if(i < node.getChoices().size()){
                Choice c = node.getChoices().get(i);
                buttons[i].setText(c.getText());

                // Click Listener
                buttons[i].setOnClickListener(v -> {
                    soundManager.playSfx(R.raw.click_sound);

                    // Game Logic
                    user.setAffection(user.getAffection() + c.getAffection());
                    user.setLastNode(c.getNext());
                    db.updateUser(user);

                    // Navigation
                    if(c.getNext().startsWith("end")){
                        goToEndActivity();
                    } else {
                        showNode(c.getNext());
                    }
                });
                buttons[i].setVisibility(Button.VISIBLE);
            } else {
                buttons[i].setVisibility(Button.GONE);
            }
        }
    }

    private void goToEndActivity() {
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    // Helper to find node by ID
    private DialogueNode getNodeById(String id){
        for(DialogueNode n : nodes){
            if(n.getId().equals(id)) return n;
        }
        return null;
    }

    // JSON Loader
    private ArrayList<DialogueNode> loadDialogueJSON(){
        ArrayList<DialogueNode> list = new ArrayList<>();
        try{
            InputStream is = getAssets().open("dialogue.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray arr = new JSONArray(json);
            for(int i=0; i<arr.length(); i++){
                list.add(DialogueNode.fromJSON(arr.getJSONObject(i)));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up audio resources
        if(soundManager != null) soundManager.release();
    }
}