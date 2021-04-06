package com.tsb.skribbl.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

public class Round {
    private final long roundId;
    private final long startedAt;
    private final long timeToDraw;
    private final String word;
    private final ArrayList<DrawingLine> canvas;
    private final Hashtable<UUID, Integer> roundUserScores;

    public Round(long roundId, long timeToDraw, ArrayList<String> words, ArrayList<User> users) {
        this.roundId = roundId;
        this.startedAt = java.time.Instant.now().getEpochSecond();
        this.timeToDraw = timeToDraw;
        this.word = this.pickRandomWord(words);
        this.canvas = new ArrayList<>();
        this.roundUserScores = this.initRoundUserScoresTable(users);
    }

    private String pickRandomWord(ArrayList<String> words) {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    private Hashtable<UUID, Integer> initRoundUserScoresTable(ArrayList<User> users) {
        Hashtable<UUID, Integer> roundUserScores = new Hashtable<>();
        for (User user : users) {
            roundUserScores.put(user.getUserId(), 0);
        }
        return roundUserScores;
    }

    public void addDrawingLineToCanvas(DrawingLine drawingLine) {
        this.canvas.add(drawingLine);
    }

    public boolean makeGuess(UUID userId, String guess) {
        boolean isCorrectGuess = this.word.equals(guess);

        if (!isCorrectGuess) {
            return false;
        }

        int score = (int) (this.getEndsAt() - java.time.Instant.now().getEpochSecond());
        this.roundUserScores.replace(userId, score);
        return true;
    }

    public boolean hasEveryoneGuessed() {
        for (int roundUserScore : roundUserScores.values()) {
            if (roundUserScore == 0) return false;
        }
        return true;
    }

    public Hashtable<UUID, Integer> getRoundUserScores() {
        return roundUserScores;
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
}
