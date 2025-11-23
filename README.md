# diabolo-academia
 Diabolo Academia:

An interactive narrative Android game powered by a data-driven engine.

📖 Overview
Diabolo Academia is a Visual Novel application where the player interacts with a virtual character, Lucien, a demon student. The project demonstrates a robust, data-driven architecture where the entire storyline, character expressions, and game logic are parsed dynamically from JSON files, rather than being hard-coded.

It features a branching narrative system where player choices impact the "Affection Score" and determine one of three unique endings.



✨ Key Features

🔀 Branching Narrative: Every choice matters. The story adapts based on user decisions.
💾 Persistent Save System: Uses SQLite to save the player's progress (lastNode) and relationships (affection score) automatically.
📄 Data-Driven Engine: The entire script is loaded from assets/dialogue.json, allowing for story updates without code changes.
🎧 Dynamic Audio: Features an asynchronous SoundManager for looping background music (BGM) and reactive sound effects (SFX).
🎨 Immersive UI: Custom "Dark Academia" aesthetic with transparent assets and layered layouts.



🛠️ Technical Architecture

The app follows a strict MVC (Model-View-Controller) pattern:

*Model (Data):
User.java: Stores player state.
DialogueNode.java: Represents a story frame (Speaker, Text, Image, Choices).
dialogue.json: The external source of truth for the story.

*View (UI):
StoryActivity.java: Dynamically renders the UI based on the current Node ID.
ConstraintLayout: Used for layering character sprites over backgrounds.

*Controller (Logic):
DatabaseHelper.java: Handles all CRUD operations with the local SQLite database.
SoundManager.java: Manages audio resources and memory cleanup.



🚀 Installation & Setup

1.Clone the repository:
2.Open in Android Studio:
Select File > Open and navigate to the cloned folder.
Wait for Gradle to sync.
3.Build the Project:Ensure your vitual device (AVD) is running (API 24+ recommended).
Click the Run (▶) button.



📂 Project Structure

app/src/main
├── assets/
│   └── dialogue.json       # The script of the game
├── java/com/example/diaboloacademia/
│   ├── MainActivity.java   # Login & Auth
│   ├── StoryActivity.java  # Game Engine
│   ├── DatabaseHelper.java # SQL Handling
│   └── SoundManager.java   # Audio Logic
└── res/
    ├── drawable/           # Character sprites & Backgrounds
    ├── layout/             # XML UI Definitions
    └── raw/                # MP3 Files (BGM & SFX)


🖼️ Gallery
Start Screen
<img width="364" height="814" alt="image" src="https://github.com/user-attachments/assets/e7c8918e-f027-4750-b333-96fee1964a38" />

Gameplay (one of them nodes)
<img width="1205" height="540" alt="image" src="https://github.com/user-attachments/assets/129d8e69-13a8-472d-acff-9fea8f04e10e" />

End Screen (depending on your choices)
<img width="1204" height="538" alt="image" src="https://github.com/user-attachments/assets/4d2402f5-81ab-4359-9812-d1b4691015d5" />



👨‍💻 Author
Developed by Mariem Derbel as a final project.

Note: This project uses assets generated for educational purposes.
