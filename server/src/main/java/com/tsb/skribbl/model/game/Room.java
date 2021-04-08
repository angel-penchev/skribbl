package com.tsb.skribbl.model.game;

import com.tsb.skribbl.exception.GameHasAlreadyStartedException;
import com.tsb.skribbl.exception.GameHasNotAlreadyStartedException;
import com.tsb.skribbl.exception.RoomUserLimitReachedException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Room {
    private final UUID roomId = UUID.randomUUID();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<String> words;
    private final long timeToDraw;
    private final int userLimit;
    private final int roundLimit;
    private boolean isGameStarted = false;
    private Round round = null;
    private int roundId = 0;
    private final Hashtable<UUID, Integer> userScores = new Hashtable<>();

    public Room(ArrayList<String> words, long timeToDraw, int userLimit, int roundLimit) {
        this.words = words;
        this.timeToDraw = timeToDraw;
        this.userLimit = userLimit;
        this.roundLimit = roundLimit;
    }

    private void updateScoresFromUserScores() {
        // Updates all existing user scores and adds new entries for users who just joined
        for (User user : this.users) {
            if (!this.userScores.contains(user.getUserId())) {
                this.userScores.put(user.getUserId(), 0);
            } else {
                int currentScore = this.userScores.get(user.getUserId());
                int roundScore = this.round.getUserScores().get(user.getUserId());
                this.userScores.replace(user.getUserId(), currentScore + roundScore);
            }
        }
    }

    private void removeDisconnectedUsersFromUserScores() {
        // Removing scores for all disconnected users
        List<UUID> currentUserIds = this.users.stream().map(User::getUserId).collect(Collectors.toList());
        for (UUID userId : userScores.keySet()) {
            if (!currentUserIds.contains(userId)) {
                this.userScores.remove(userId);
            }
        }
    }

    public void startGame() throws GameHasAlreadyStartedException {
        if (isGameStarted) {
            throw new GameHasAlreadyStartedException();
        }
        this.updateScoresFromUserScores();
        this.startRound();
        isGameStarted = true;
    }

    public void endGame() throws GameHasNotAlreadyStartedException {
        if (!isGameStarted) {
            throw new GameHasNotAlreadyStartedException();
        }
        isGameStarted = false;
    }

    public void startRound() {
        this.removeDisconnectedUsersFromUserScores();
        this.round = new Round(++this.roundId, this.timeToDraw, this.words, this.users);
    }

    public void endRound() {
        this.updateScoresFromUserScores();
        this.round = null;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public boolean isFull() {
        return this.users.size() == this.userLimit;
    }

    public void addUser(User user) throws RoomUserLimitReachedException {
        if (this.users.size() < this.userLimit) {
            throw new RoomUserLimitReachedException();
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
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
