package com.tsb.skribbl.model.request;

public class CreateRoomRequest {
    private int userLimit;
    private int roundLimit;
    private int timeToDraw;
    private String wordlist;
    private String customWords;
    private boolean isPublic;

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

    public int getTimeToDraw() {
        return timeToDraw;
    }

    public void setTimeToDraw(int timeToDraw) {
        this.timeToDraw = timeToDraw;
    }

    public String getWordlist() {
        return wordlist;
    }

    public void setWordlist(String wordlist) {
        this.wordlist = wordlist;
    }

    public String getCustomWords() {
        return customWords;
    }

    public void setCustomWords(String customWords) {
        this.customWords = customWords;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
