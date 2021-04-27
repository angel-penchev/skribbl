package com.tsb.skribbl.model.message;

import java.util.ArrayList;

public class GameWordSelectionMessage extends GameMessage{
    private ArrayList<String> wordlist;

    public GameWordSelectionMessage(String type, String message, ArrayList<String> wordlist) {
        super(type, message);
        this.wordlist = wordlist;
    }

    public GameWordSelectionMessage(ArrayList<String> wordlist) {
        this.wordlist = wordlist;
    }

    public ArrayList<String> getWordlist() {
        return wordlist;
    }

    public void setWordlist(ArrayList<String> wordlist) {
        this.wordlist = wordlist;
    }
}
