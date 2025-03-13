package org.example.battleship_gamerservices.rest;

import org.example.battleship_gamerservices.client.PlayerClient;
import org.example.battleship_gamerservices.client.ShipClient;
import org.example.battleship_gamerservices.dto.CreateGameRequest;
import org.example.battleship_gamerservices.entity.Game;
import org.example.battleship_gamerservices.repository.GameRepository;
import org.example.battleship_gamerservices.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;
    private final GameRepository gameRepository;


    public GameController(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;

    }

    @PostMapping
    public Game createGame(@RequestBody CreateGameRequest request) {
        return gameService.saveGame(new Game(request.getName()));
    }

    @GetMapping
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @Autowired
    private PlayerClient playerClient;

    @Autowired
    private ShipClient shipClient;

    @GetMapping("/{gameId}/status")
    public ResponseEntity<String> getGameStatus(@PathVariable Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        StringBuilder sb = new StringBuilder();
        sb.append("Game: ").append(game.getName()).append(" (ID: ").append(game.getId()).append(")\n");
        sb.append("Active: ").append(game.isActive()).append("\n");
        sb.append("Finished: ").append(game.isFinished()).append("\n");
        sb.append("Current Turn: ").append(game.getCurrentTurnPlayerId() != null ? playerClient.getPlayerName(game.getCurrentTurnPlayerId()) : "None").append("\n");
        sb.append("Winner: ").append(game.getWinnerPlayerId() != null ? playerClient.getPlayerName(game.getWinnerPlayerId()) : "None").append("\n\n");

        for (Long playerId : game.getPlayerIds()) {
            String name = playerClient.getPlayerName(playerId);
            int shipCount = shipClient.getShipCountByPlayerId(playerId);
            sb.append("👤 Player: ").append(name).append(" | 🚢 Ships: ").append(shipCount).append("\n");
        }

        return ResponseEntity.ok(sb.toString());
    }
}