package com.example.diaboloacademia;

public class Choice {
    private String text;
    private int affection;
    private String next;

    public Choice(String text, int affection, String next){
        this.text = text;
        this.affection = affection;
        this.next = next;
    }
    public String getText(){ return text; }
    public int getAffection(){ return affection; }
    public String getNext(){ return next; }
}
