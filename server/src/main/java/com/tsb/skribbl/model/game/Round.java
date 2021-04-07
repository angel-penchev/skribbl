package com.tsb.skribbl.model.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

public class Round {
    private final long roundId;
    private final long startedAt = java.time.Instant.now().getEpochSecond();
    private final long timeToDraw;
    private final String word;
    private final ArrayList<DrawingLine> canvas = new ArrayList<>();
    private final Hashtable<UUID, String> userGuesses = new Hashtable<>();
    private final Hashtable<UUID, Integer> userScores;

    public Round(long roundId, long timeToDraw, ArrayList<String> words, ArrayList<User> users) {
        this.roundId = roundId;
        this.timeToDraw = timeToDraw;
        this.word = this.pickRandomWord(words);
        this.userScores = this.initUserScoresTable(users);
    }

    private String pickRandomWord(ArrayList<String> words) {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    private Hashtable<UUID, Integer> initUserScoresTable(ArrayList<User> users) {
        Hashtable<UUID, Integer> userScores = new Hashtable<>();
        for (User user : users) {
            userScores.put(user.getUserId(), 0);
        }
        return userScores;
    }

    public void addDrawingLineToCanvas(DrawingLine drawingLine) {
        this.canvas.add(drawingLine);
    }

    public boolean makeGuess(UUID userId, String guess) {
        userGuesses.put(userId, guess);

        boolean isCorrectGuess = this.word.equals(guess);
        if (!isCorrectGuess) {
            return false;
        }

        int score = (int) (this.getEndsAt() - java.time.Instant.now().getEpochSecond());
        this.userScores.replace(userId, score);
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

    public Hashtable<UUID, String> getUserGuesses() {
        return userGuesses;
    }

    public Hashtable<UUID, Integer> getUserScores() {
        return userScores;
    }
}
