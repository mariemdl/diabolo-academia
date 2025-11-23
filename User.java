package com.example.diaboloacademia;

public class User {
    private int id;
    private String username;
    private int affection;
    private String lastNode;

    public User(int id, String username, int affection, String lastNode) {
        this.id = id;
        this.username = username;
        this.affection = affection;
        this.lastNode = lastNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAffection() {
        return affection;
    }

    public void setAffection(int affection) {
        this.affection = affection;
    }

    public String getLastNode() {
        return lastNode;
    }

    public void setLastNode(String lastNode) {
        this.lastNode = lastNode;
    }
}
