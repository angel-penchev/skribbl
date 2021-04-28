package com.tsb.skribbl.model.game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Round {
    private long id;
    private long startedAt = java.time.Instant.now().getEpochSecond();
    private long timeToDraw;
    private String word;
    private ArrayList<DrawingLine> canvas = new ArrayList<>();
    private Hashtable<String, String> userGuesses = new Hashtable<>();
    private Hashtable<String, Integer> userScores;
    private User drawingUser;
    private int usersGuessed = 0;

    public Round(long id, long timeToDraw, String word, ArrayList<User> users, User drawingUser) {
        this.id = id;
        this.timeToDraw = timeToDraw;
        this.word = word;
        this.userScores = this.initUserScoresTable(users);
        this.drawingUser = drawingUser;
    }

    public Round() {

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

        int score = (int) (this.endsAt() - java.time.Instant.now().getEpochSecond());
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }


    public long endsAt() {
        return this.startedAt + this.timeToDraw;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getTimeToDraw() {
        return timeToDraw;
    }

    public void setTimeToDraw(long timeToDraw) {
        this.timeToDraw = timeToDraw;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<DrawingLine> getCanvas() {
        return canvas;
    }

    public void setCanvas(ArrayList<DrawingLine> canvas) {
        this.canvas = canvas;
    }

    public Hashtable<String, String> getUserGuesses() {
        return userGuesses;
    }

    public void setUserGuesses(Hashtable<String, String> userGuesses) {
        this.userGuesses = userGuesses;
    }

    public Hashtable<String, Integer> getUserScores() {
        return userScores;
    }

    public void setUserScores(Hashtable<String, Integer> userScores) {
        this.userScores = userScores;
    }

    public User getDrawingUser() {
        return drawingUser;
    }

    public void setDrawingUser(User drawingUser) {
        this.drawingUser = drawingUser;
    }

    public int getUsersGuessed() {
        return usersGuessed;
    }

    public void setUsersGuessed(int usersGuessed) {
        this.usersGuessed = usersGuessed;
    }
}
