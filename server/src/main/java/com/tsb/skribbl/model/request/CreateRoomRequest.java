package com.tsb.skribbl.model.request;

import java.util.ArrayList;

public class CreateRoomRequest {
    private String wordlistName;
    private ArrayList<String> wordlist;
    private int timeToDraw;
    private int userLimit;
    private int roundLimit;

    public String getWordlistName() {
        return wordlistName;
    }

    public void setWordlistName(String wordlistName) {
        this.wordlistName = wordlistName;
    }

    public ArrayList<String> getWordlist() {
        return wordlist;
    }

    public void setWordlist(ArrayList<String> wordlist) {
        this.wordlist = wordlist;
    }

    public int getTimeToDraw() {
        return timeToDraw;
    }

    public void setTimeToDraw(int timeToDraw) {
        this.timeToDraw = timeToDraw;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public int getRoundLimit() {
        return roundLimit;
    }

    public void setRoundLimit(int roundLimit) {
        this.roundLimit = roundLimit;
    }
}
