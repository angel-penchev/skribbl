package com.tsb.skribbl.model.message;

import java.util.ArrayList;
import java.util.Hashtable;

public class GameRoundEndMessage extends GameMessage {
    private Hashtable<String, Integer> scores;

    public GameRoundEndMessage(String type, String message, Hashtable<String, Integer> scores) {
        super(type, message);
        this.scores = scores;
    }

    public Hashtable<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Hashtable<String, Integer> scores) {
        this.scores = scores;
    }
}
