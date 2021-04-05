package com.tsb.skribbl.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Room {
    private final UUID roomId;
    private final ArrayList<User> users;
    private final ArrayList<String> words;
    private boolean isGameStarted;
    private Round round;
    private long currentRoundId;
    private final Hashtable<UUID, Integer> roomUserScores;

    public Room(ArrayList<String> words) {
        this.roomId = UUID.randomUUID();
        this.users = new ArrayList<>();
        this.words = words;
        this.isGameStarted = false;
        this.currentRoundId = 0;
        this.roomUserScores = new Hashtable<>();
    }

    private void updateRoomUserScoresTable(ArrayList<User> users) {
        // Updates all existing user scores and adds new entries for users who just joined
        for (User user : users) {
            if (!this.roomUserScores.contains(user.getUserId())) {
                this.roomUserScores.put(user.getUserId(), 0);
            } else {
                int currentScore = this.roomUserScores.get(user.getUserId());
                int roundScore = this.round.getRoundUserScores().get(user.getUserId());
                this.roomUserScores.replace(user.getUserId(), currentScore + roundScore);
            }
        }

        // Removing scores for all disconnected users
        List<UUID> currentUserIds = this.users.stream().map(User::getUserId).collect(Collectors.toList());
        for (UUID userId : roomUserScores.keySet()) {
            if (!currentUserIds.contains(userId)) {
                this.roomUserScores.remove(userId);
            }
        }
    }

    public UUID getRoomId() {
        return roomId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public Round getRound() {
        return round;
    }
}
