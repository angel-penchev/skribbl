package com.tsb.skribbl.controller;

import com.tsb.skribbl.entity.RoundEntity;
import com.tsb.skribbl.model.request.SaveRoundRequest;
import org.apache.commons.io.FilenameUtils;
import com.tsb.skribbl.exception.GameHasAlreadyStartedException;
import com.tsb.skribbl.exception.RoomUserLimitReachedException;
import com.tsb.skribbl.model.game.DrawingLine;
import com.tsb.skribbl.model.game.Room;
import com.tsb.skribbl.model.game.User;
import com.tsb.skribbl.model.message.BoardMessage;
import com.tsb.skribbl.model.message.ChatMessage;
import com.tsb.skribbl.model.message.GameMessage;
import com.tsb.skribbl.model.request.CreateRoomRequest;
import com.tsb.skribbl.service.GameService;
import com.tsb.skribbl.service.RoundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class MainController {
    public final SimpMessageSendingOperations messagingTemplate;
    private final GameService gameService;
    private final RoundService roundService;

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Hashtable<String, Room> rooms = new Hashtable<>();
    private final Hashtable<String, ScheduledFuture<?>> roundTimers = new Hashtable<>();
    private static final String imageDirectory = System.getProperty(".") + "/images/";

    public MainController(SimpMessageSendingOperations messagingTemplate, GameService gameService, RoundService roundService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @PostMapping("/create-room")
    public Room createRoomMapping(@RequestBody CreateRoomRequest createRoomRequest) {
        ArrayList<String> customWords = new ArrayList<>();
        Collections.addAll(customWords, createRoomRequest.getCustomWords().split("\\s+"));

        Room room = gameService.createRoom(
                createRoomRequest.getWordlist(),
                customWords,
                createRoomRequest.getTimeToDraw(),
                createRoomRequest.getUserLimit(),
                createRoomRequest.getRoundLimit(),
                createRoomRequest.isPublic());
        rooms.put(room.getRoomId().toString(), room);
        return room;
    }

    @GetMapping("/{id}")
    public Room getRoomMapping(@PathVariable String id) {
        return rooms.get(id);
    }

    @RequestMapping(value = "/history", produces = {MediaType.IMAGE_PNG_VALUE, "application/json"})
    public ResponseEntity<?> uploadImage(@RequestBody SaveRoundRequest saveRoundRequest) {
        makeDirectoryIfNotExist(imageDirectory);

        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setWord(saveRoundRequest.getWord());
        roundEntity.setDrawingUserName(saveRoundRequest.getDrawingUserName());
        roundService.save(roundEntity);

        Path fileNamePath = Paths.get(imageDirectory, roundEntity.getId().toString());
        try {
            Files.write(fileNamePath, saveRoundRequest.getBoard().getBytes());
            return new ResponseEntity<>(roundEntity.getId().toString(), HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @GetMapping("/public-rooms")
    public ArrayList<Room> getPublicRoomsMapping() {
        ArrayList<Room> publicRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            publicRooms.add(room);
            if (room.isPublic()) {
                if (publicRooms.size() >= 6) break;
            }
        }

        return publicRooms;
    }

    @MessageMapping("/room/{roomId}/chat")
    @SendTo("/topic/room/{roomId}/chat")
    public void chatMapping(
            ChatMessage message,
            @DestinationVariable String roomId
    ) throws InterruptedException {
        Room room = rooms.get(roomId);
        if (room.getRound() != null) {
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId + "/chat",
                    gameService.makeGuess(room, message.getUsername(), message.getMessage()));
        } else {
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId + "/chat",
                    new ChatMessage(message.getUsername(), message.getMessage()));
        }


        if (room.getRound() != null && room.getRound().getUsersGuessed() >= room.getRound().getUserScores().size() - 1) {
            roundTimers.get(roomId).cancel(true);
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId + "/game",
                    gameService.roundEnd(room)
            );

            if (room.getRoundId() < room.getRoundLimit()) {
                TimeUnit.SECONDS.sleep(5);

                messagingTemplate.convertAndSend(
                        "/topic/room/" + roomId + "/game",
                        gameService.wordSelect(room)
                );
            }
        }
    }

    @MessageMapping("/room/{roomId}/game")
    @SendTo("/topic/room/{roomId}/game")
    public GameMessage gameMapping(
            GameMessage message,
            @DestinationVariable String roomId
    ) throws RoomUserLimitReachedException, GameHasAlreadyStartedException {
        Room room = rooms.get(roomId);
        switch (message.getType()) {
            case "connection":
                System.out.println(message.getMessage());
                room.addUser(new User(message.getMessage()));

                if (room.getUserAmount() >= 3) {
                    messagingTemplate.convertAndSend(
                            "/topic/room/" + roomId + "/game",
                            gameService.startGame(room)
                    );

                    messagingTemplate.convertAndSend(
                            "/topic/room/" + roomId + "/game",
                            gameService.wordSelect(room)
                    );
                }
                return message;

            case "word-selected":
                Runnable task = () -> messagingTemplate.convertAndSend(
                        "/topic/room/" + roomId + "/game",
                        gameService.roundEnd(room)
                );
                ScheduledFuture<?> scheduledTask =  scheduler.schedule(task, room.getTimeToDraw(), TimeUnit.SECONDS);
                this.roundTimers.put(roomId, scheduledTask);
                return gameService.roundStart(room, message.getMessage());
        }

        return message;
    }

    @MessageMapping("/room/{roomId}/board")
    @SendTo("/topic/room/{roomId}/board")
    public BoardMessage boardMapping(@Payload BoardMessage message, @DestinationVariable String roomId) {
        Room room = rooms.get(roomId);
        if (room.getRound() != null) {
            room.getRound().addDrawingLineToCanvas(
                    new DrawingLine(
                            message.getStartX(),
                            message.getStartY(),
                            message.getEndX(),
                            message.getEndY(),
                            message.getColor(),
                            message.getWidth()
                    )
            );
        }
        return message;
    }
}
