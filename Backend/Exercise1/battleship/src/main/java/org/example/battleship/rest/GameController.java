package org.example.battleship.rest;

import org.example.battleship.dto.CreateGameRequest;
import org.example.battleship.entity.Game;
import org.example.battleship.entity.Player;
import org.example.battleship.repository.GameRepository;
import org.example.battleship.repository.ShipRepository;
import org.example.battleship.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;

    public GameController(GameService gameService, GameRepository gameRepository, ShipRepository shipRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
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

    @GetMapping("/{gameId}/status")
    public ResponseEntity<String> getGameStatus(@PathVariable Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        StringBuilder sb = new StringBuilder();
        sb.append("📌 Game: ").append(game.getName()).append(" (ID: ").append(game.getId()).append(")\n");
        sb.append("🟢 Active: ").append(game.isActive()).append("\n");
        sb.append("🎯 Current Turn: ").append(game.getCurrentTurn() != null ? game.getCurrentTurn().getName() : "None").append("\n");
        sb.append("🏁 Finished: ").append(game.isFinished()).append("\n");
        sb.append("🏆 Winner: ").append(game.getWinner() != null ? game.getWinner().getName() : "None").append("\n\n");

        for (Player player : game.getPlayers()) {
            long shipCount = shipRepository.findByPlayerId(player.getId()).size();
            sb.append("👤 Player: ").append(player.getName())
                    .append(" | Ships: ").append(shipCount).append("\n");
        }

        return ResponseEntity.ok(sb.toString());
    }
}