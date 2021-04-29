package com.tsb.skribbl.service;

import com.tsb.skribbl.exception.GameHasAlreadyStartedException;
import com.tsb.skribbl.model.game.Room;
import com.tsb.skribbl.model.game.User;
import com.tsb.skribbl.model.message.ChatMessage;
import com.tsb.skribbl.model.message.GameMessage;
import com.tsb.skribbl.model.message.GameRoundEndMessage;
import com.tsb.skribbl.model.message.GameWordSelectionMessage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.*;

@Service
public class GameService {
    private final ResourceLoader resourceLoader;
    private final Random random = new Random();

    public GameService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Room createRoom(
            String wordlistName,
            ArrayList<String> wordlist,
            int timeToDraw, int userLimit,
            int roundLimit, boolean isPublic
    ) {
        if ("custom".equals(wordlistName)) {
            return new Room(wordlist, timeToDraw, userLimit, roundLimit, isPublic);
        }

        Resource resource = resourceLoader.getResource("classpath:wordlists/" + wordlistName + ".txt");
        String resourceString;
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            resourceString = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        ArrayList<String> serverWordlist = new ArrayList<>();
        Collections.addAll(serverWordlist, resourceString.split("\\r?\\n"));
        return new Room(serverWordlist, timeToDraw, userLimit, roundLimit, isPublic);
    }

    public GameMessage startGame(Room room) throws GameHasAlreadyStartedException {
        room.startGame();
        return new GameMessage("game-start", "");
    }

    public GameWordSelectionMessage wordSelect(Room room) {
        User currentDrawingUser = room.drawingUser();
        ArrayList<String> wordsToPick = new ArrayList<>(room.getWords().subList(0, 3));

        return new GameWordSelectionMessage(
                "word-select-prompt",
                currentDrawingUser.getUsername(),
                wordsToPick
        );
    }

    public GameMessage roundStart(Room room, String word) {
        User currentDrawingUser = room.startRound(word).getDrawingUser();

        return new GameMessage(
                "round-start",
                currentDrawingUser.getUsername()
        );
    }

    public GameMessage roundEnd(Room room) {
        room.endRound();

        boolean isGameEnd = room.getRoundId() >= room.getRoundLimit();
        return new GameRoundEndMessage(
                isGameEnd ? "game-end" : "round-end",
                "room.getRound().getWord()",
                room.getUserScores()
        );
    }

    public ChatMessage makeGuess(Room room, String username, String guess) {
        if (!room.getRound().makeGuess(username, guess)) {
            return new ChatMessage(username, guess);
        }
        return new ChatMessage("", username + "has guessed correctly!");
    }
}
