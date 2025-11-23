package com.example.diaboloacademia;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class DialogueNode {
    private String id;
    private String speaker;
    private String text;
    private String image;
    private ArrayList<Choice> choices;

    public DialogueNode(String id, String speaker, String text, String image, ArrayList<Choice> choices){
        this.id = id;
        this.speaker = speaker;
        this.text = text;
        this.image = image;
        this.choices = choices;
    }

    public String getId(){ return id; }
    public String getSpeaker(){ return speaker; }
    public String getText(){ return text; }
    public String getImage(){ return image; }
    public ArrayList<Choice> getChoices(){ return choices; }

    public static DialogueNode fromJSON(JSONObject obj){
        try{
            String id = obj.getString("id");
            String speaker = obj.getString("speaker");
            String text = obj.getString("text");
            String image = obj.optString("image", null);
            ArrayList<Choice> choices = new ArrayList<>();
            JSONArray arr = obj.optJSONArray("choices");
            if(arr != null){
                for(int i=0; i<arr.length(); i++){
                    JSONObject c = arr.getJSONObject(i);
                    choices.add(new Choice(c.getString("text"),
                            c.getInt("affection"),
                            c.getString("next")));
                }
            }
            return new DialogueNode(id, speaker, text, image, choices);
        }catch(Exception e){ e.printStackTrace(); }
        return null;
    }
}
