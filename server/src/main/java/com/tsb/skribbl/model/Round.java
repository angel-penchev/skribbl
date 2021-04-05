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
    private final ArrayList<User> users;
    private final Hashtable<UUID, Integer> userScores;

    public Round(long roundId, long timeToDraw, ArrayList<String> words, ArrayList<User> users) {
        this.roundId = roundId;
        this.startedAt = java.time.Instant.now().getEpochSecond();
        this.timeToDraw = timeToDraw;
        this.word = this.pickRandomWord(words);
        this.users = users;
        this.userScores = this.initUserScoresTable(users);
    }

    private String pickRandomWord(ArrayList<String> words) {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    private Hashtable<UUID, Integer> initUserScoresTable(ArrayList<User> users) {
        Hashtable<UUID, Integer> userHasGuessed = new Hashtable<>();
        for (User user : users) {
            userHasGuessed.put(user.getUserId(), 0);
        }
        return userHasGuessed;
    }

    public boolean makeGuess(UUID userId, String guess) {
        boolean isCorrectGuess = this.word.equals(guess);

        if (!isCorrectGuess) {
            return false;
        }

        int score = (int) (java.time.Instant.now().getEpochSecond() - this.startedAt);
        this.userScores.replace(userId, score);
        return true;
    }

    public boolean hasEveryoneGuessed() {
        for (int userScore : userScores.values()) {
            if (userScore == 0) return false;
        }
        return true;
    }

    public Hashtable<UUID, Integer> getUserScores() {
        return userScores;
    }
}
