package com.tsb.skribbl.controller;

import com.tsb.skribbl.exception.GameHasAlreadyStartedException;
import com.tsb.skribbl.exception.RoomUserLimitReachedException;
import com.tsb.skribbl.model.game.Room;
import com.tsb.skribbl.model.game.User;
import com.tsb.skribbl.model.message.BoardMessage;
import com.tsb.skribbl.model.message.ChatMessage;
import com.tsb.skribbl.model.message.GameMessage;
import com.tsb.skribbl.model.request.CreateRoomRequest;
import com.tsb.skribbl.service.GameLogicService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

@RestController
@RequestMapping("/api")
public class MainController {
    public final SimpMessageSendingOperations messagingTemplate;
    private final GameLogicService gameLogicService;
    private final Hashtable<String, Room> rooms = new Hashtable<>();

    public MainController(SimpMessageSendingOperations messagingTemplate, GameLogicService gameLogicService) {
        this.messagingTemplate = messagingTemplate;
        this.gameLogicService = gameLogicService;
    }

    @PostMapping("/create-room")
    public Room createRoomMapping(@RequestBody CreateRoomRequest createRoomRequest) {
        ArrayList<String> customWords = new ArrayList<>();
        Collections.addAll(customWords, createRoomRequest.getCustomWords().split("\\s+"));

        Room room = gameLogicService.createRoom(
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

    @GetMapping("/public-rooms")
    public ArrayList<Room> getPublicRoomsMapping() {
        ArrayList<Room> publicRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            publicRooms.add(room);
            if (room.isPublic()) {
                if (publicRooms.size() >= 6) break;
            }
        }

        System.out.println(publicRooms.size());
        return publicRooms;
    }

    @MessageMapping("/room/{roomId}/chat")
    @SendTo("/topic/room/{roomId}/chat")
    public ChatMessage chatMapping(
            ChatMessage message
    ) {
        System.out.println(message.getMessage());
        return message;
    }

    @MessageMapping("/room/{roomId}/game")
    @SendTo("/topic/room/{roomId}/game")
    public GameMessage gameMapping(
            GameMessage message,
            @DestinationVariable String roomId
    ) throws RoomUserLimitReachedException, GameHasAlreadyStartedException {
        switch (message.getType()) {
            case "connection":
                Room room = rooms.get(roomId);
                room.addUser(new User(message.getMessage()));

                if (room.getUserAmount() >= 3) {
                    room.startGame();
                    User currentDrawingUser = room.startRound();
                    messagingTemplate.convertAndSend(
                            "/topic/room/" + roomId + "/game",
                            new GameMessage("game-start", currentDrawingUser.getUsername())
                    );
                }

            case "word-select":
                return message;
        }

        return message;
    }

    @MessageMapping("/room/{roomId}/board")
    @SendTo("/topic/room/{roomId}/board")
    public BoardMessage boardMapping(@Payload BoardMessage message, @DestinationVariable String roomId) {
//        if (rooms.get(roomId) != null) {
//            rooms.get(roomId).getRound().addDrawingLineToCanvas(
//                    new DrawingLine(
//                            message.getStartX(),
//                            message.getStartY(),
//                            message.getEndX(),
//                            message.getEndY(),
//                            message.getColor(),
//                            message.getWidth()
//                    )
//            );
//        }
        return message;
    }
}
