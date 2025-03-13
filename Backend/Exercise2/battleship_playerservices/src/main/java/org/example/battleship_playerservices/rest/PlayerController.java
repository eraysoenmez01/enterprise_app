package org.example.battleship_playerservices.rest;

import org.example.battleship_gamerservices.dto.CreatePlayerRequest;
import org.example.battleship_gamerservices.entity.Player;
import org.example.battleship_gamerservices.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public Player createPlayer(@RequestBody CreatePlayerRequest request) {
        return playerService.createPlayer(request.getName(), request.getGameId());
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}