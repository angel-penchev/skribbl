package com.tsb.skribbl.model.message;

public class GameMessage {
    private String type;
    private String message;

    public GameMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public GameMessage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
