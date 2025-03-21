package org.example.battleship_gamerservices.rest;

import org.example.battleship_gamerservices.dto.CreateGameRequest;
import org.example.battleship_gamerservices.entity.Game;
import org.example.battleship_gamerservices.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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

    @PostMapping("/{gameId}/players/{playerId}/ships")
    public ResponseEntity<String> placeShips(
            @PathVariable Long gameId,
            @PathVariable Long playerId,
            @RequestBody List<String> positions) {
        try {
            String response = gameService.placeShips(gameId, playerId, positions);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{gameId}/status")
    public ResponseEntity<String> getGameStatus(@PathVariable Long gameId) {
        try {
            return ResponseEntity.ok(gameService.getFormattedGameStatus(gameId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{gameId}/guess")
    public ResponseEntity<String> makeGuess(
            @PathVariable Long gameId,
            @RequestParam Long playerId,
            @RequestParam int x,
            @RequestParam int y) {
        try {
            return ResponseEntity.ok(gameService.makeGuess(gameId, playerId, x, y));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{gameId}/players/{playerId}")
    public void addPlayerToGame(@PathVariable Long gameId, @PathVariable Long playerId) {
        gameService.addPlayerToGame(gameId, playerId);
    }
}