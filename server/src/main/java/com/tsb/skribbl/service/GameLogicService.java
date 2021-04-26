package com.tsb.skribbl.service;

import com.tsb.skribbl.model.game.Room;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class GameLogicService {
    private final ResourceLoader resourceLoader;

    public GameLogicService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Room createRoom(String wordlistName, ArrayList<String> wordlist, int timeToDraw, int userLimit, int roundLimit, boolean isPublic) {
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
}
