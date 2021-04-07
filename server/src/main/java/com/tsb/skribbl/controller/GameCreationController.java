package com.tsb.skribbl.controller;

import com.tsb.skribbl.model.game.Room;
import com.tsb.skribbl.model.request.CreateRoomRequest;
import com.tsb.skribbl.service.GameLogicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Room createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        Room room = gameLogicService.createRoom(
                createRoomRequest.getWordlistName(),
                createRoomRequest.getWordlist(),
                createRoomRequest.getTimeToDraw(),
                createRoomRequest.getUserLimit(),
                createRoomRequest.getRoundLimit());

        rooms.add(room);
        return room;
    }
}
