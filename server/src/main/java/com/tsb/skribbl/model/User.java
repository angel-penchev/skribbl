package com.tsb.skribbl.model;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;
    private int score;

    public User(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
        this.score = 0;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
    }
}
