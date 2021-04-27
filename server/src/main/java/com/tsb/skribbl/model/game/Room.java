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
    private final boolean isPublic;
    private boolean isGameStarted = false;
    private Round round = null;
    private int roundId = 0;
    private final Hashtable<String, Integer> userScores = new Hashtable<>();

    public Room(ArrayList<String> words, long timeToDraw, int userLimit, int roundLimit, boolean isPublic) {
        this.words = words;
        this.timeToDraw = timeToDraw;
        this.userLimit = userLimit;
        this.roundLimit = roundLimit;
        this.isPublic = isPublic;
    }

    private void updateScoresFromUserScores() {
        // Updates all existing user scores and adds new entries for users who just joined
        for (User user : this.users) {
            if (!this.userScores.contains(user.getUsername())) {
                this.userScores.put(user.getUsername().toString(), 0);
            } else {
                int currentScore = this.userScores.get(user.getUsername().toString());
                int roundScore = this.round.getUserScores().get(user.getUsername());
                this.userScores.replace(user.getUsername().toString(), currentScore + roundScore);
            }
        }
    }

    private void removeDisconnectedUsersFromUserScores() {
        // Removing scores for all disconnected users
        List<String> currentUsernames = this.users.stream().map(User::getUsername).collect(Collectors.toList());
        for (String username : userScores.keySet()) {
            if (!currentUsernames.contains(username)) {
                this.userScores.remove(username);
            }
        }
    }

    public void startGame() throws GameHasAlreadyStartedException {
        if (isGameStarted) {
            throw new GameHasAlreadyStartedException();
        }
        this.updateScoresFromUserScores();
        isGameStarted = true;
    }

    public void endGame() throws GameHasNotAlreadyStartedException {
        if (!isGameStarted) {
            throw new GameHasNotAlreadyStartedException();
        }
        isGameStarted = false;
    }

    public Round startRound(String word) {
        this.removeDisconnectedUsersFromUserScores();
        User drawingUser = this.drawingUser();
        this.round = new Round(++this.roundId, this.timeToDraw, word, this.users, drawingUser);
        return this.round;
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
        if (this.users.size() >= this.userLimit) {
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

    public boolean isPublic() {
        return isPublic;
    }

    public long getTimeToDraw() {
        return timeToDraw;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public int getRoundLimit() {
        return roundLimit;
    }

    public int getRoundId() {
        return roundId;
    }

    public int getUserAmount() {
        return users.size();
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public Hashtable<String, Integer> getUserScores() {
        return userScores;
    }

    public User drawingUser() {
        return this.users.get(roundId % (this.users.size() == 0 ? 0 : 1));
    }
}
