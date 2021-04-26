package com.tsb.skribbl.model.message;

public class ConnectionRequestMessage {
    private String username;

    public ConnectionRequestMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
