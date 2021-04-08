package com.tsb.skribbl.controller;

import com.tsb.skribbl.exception.NoSuchRoomException;
import com.tsb.skribbl.exception.RoomUserLimitReachedException;
import com.tsb.skribbl.model.game.Room;
import com.tsb.skribbl.model.game.User;
import com.tsb.skribbl.model.message.ConnectionRequestMessage;
import com.tsb.skribbl.model.message.RoomNotificationMessage;
import com.tsb.skribbl.model.request.CreateRoomRequest;
import com.tsb.skribbl.service.GameLogicService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class GameCreationController {
    private final GameLogicService gameLogicService;
    private final ArrayList<Room> rooms = new ArrayList<>();

    public GameCreationController(GameLogicService gameLogicService) {
        this.gameLogicService = gameLogicService;
    }

    @PostMapping("/create-room")
    public Room createRoomMapping(@RequestBody CreateRoomRequest createRoomRequest) {
        Room room = gameLogicService.createRoom(
                createRoomRequest.getWordlistName(),
                createRoomRequest.getWordlist(),
                createRoomRequest.getTimeToDraw(),
                createRoomRequest.getUserLimit(),
                createRoomRequest.getRoundLimit());

        rooms.add(room);
        return room;
    }

    @GetMapping("/{id}")
    public Room getRoomMapping(@PathVariable String id) throws NoSuchRoomException {
        return getRoom(id);
    }

    @MessageMapping("/connect/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public RoomNotificationMessage connectMapping(
            ConnectionRequestMessage message,
            @DestinationVariable String roomId) throws NoSuchRoomException, RoomUserLimitReachedException {
        Room room = getRoom(roomId);
        room.addUser(new User(message.getUsername()));
        return new RoomNotificationMessage("connection", String.format("Glad you're here, %s", message.getUsername()));
    }

    @EventListener
    @SendTo("/topic/room/{roomId}")
    public void onUserDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
    }

    private Room getRoom(String id) throws NoSuchRoomException {
        for(Room room : rooms) {
            if (room.getRoomId().toString().equals(id)) {
                return room;
            }
        }
        throw new NoSuchRoomException();
    }
}
