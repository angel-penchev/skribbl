package com.tsb.skribbl.model.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Round {
    private final long roundId;
    private final long startedAt = java.time.Instant.now().getEpochSecond();
    private final long timeToDraw;
    private final String word;
    private final ArrayList<DrawingLine> canvas = new ArrayList<>();
    private final Hashtable<String, String> userGuesses = new Hashtable<>();
    private final Hashtable<String, Integer> userScores;
    private final User drawingUser;
    private int usersGuessed = 0;

    public Round(long roundId, long timeToDraw, String word, ArrayList<User> users, User drawingUser) {
        this.roundId = roundId;
        this.timeToDraw = timeToDraw;
        this.word = word;
        this.userScores = this.initUserScoresTable(users);
        this.drawingUser = drawingUser;
    }

    private Hashtable<String, Integer> initUserScoresTable(ArrayList<User> users) {
        Hashtable<String, Integer> userScores = new Hashtable<>();
        for (User user : users) {
            userScores.put(user.getUsername().toString(), 0);
        }
        return userScores;
    }

    public void addDrawingLineToCanvas(DrawingLine drawingLine) {
        this.canvas.add(drawingLine);
    }

    public boolean makeGuess(String username, String guess) {
        userGuesses.put(username, guess);

        boolean isCorrectGuess = this.word.equals(guess);
        if (!isCorrectGuess) {
            return false;
        }

        int score = (int) (this.getEndsAt() - java.time.Instant.now().getEpochSecond());
        this.userScores.replace(username, score);
        usersGuessed++;
        return true;
    }

    public boolean hasEveryoneGuessed() {
        for (int roundUserScore : userScores.values()) {
            if (roundUserScore == 0) return false;
        }
        return true;
    }

    public long getRoundId() {
        return roundId;
    }

    public long getEndsAt() {
        return this.startedAt + this.timeToDraw;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public long getTimeToDraw() {
        return timeToDraw;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<DrawingLine> getCanvas() {
        return canvas;
    }

    public Hashtable<String, String> getUserGuesses() {
        return userGuesses;
    }

    public Hashtable<String, Integer> getUserScores() {
        return userScores;
    }

    public User getDrawingUser() {
        return drawingUser;
    }

    public int getUsersGuessed() {
        return usersGuessed;
    }
}
