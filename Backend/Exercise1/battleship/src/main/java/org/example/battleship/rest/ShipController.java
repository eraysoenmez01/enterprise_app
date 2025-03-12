package org.example.battleship.rest;

import org.example.battleship.dto.CreateShipRequest;
import org.example.battleship.entity.Game;
import org.example.battleship.entity.Player;
import org.example.battleship.entity.Ship;
import org.example.battleship.repository.GameRepository;
import org.example.battleship.repository.PlayerRepository;
import org.example.battleship.repository.ShipRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public ShipController(ShipRepository shipRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.shipRepository = shipRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeShips(@RequestBody CreateShipRequest request) {
        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        for (String pos : request.getPositions()) {
            int x = Character.getNumericValue(pos.charAt(0));
            int y = Character.getNumericValue(pos.charAt(1));

            Ship ship = new Ship();
            ship.setX(x);
            ship.setY(y);
            ship.setSize(1);
            ship.setPlayer(player);

            shipRepository.save(ship);
        }

        return ResponseEntity.ok("Ships placed successfully.");
    }

    @PostMapping("/guess")
    public ResponseEntity<String> guess(
            @RequestParam Long playerId,
            @RequestParam Long gameId,
            @RequestParam int x,
            @RequestParam int y) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Player shooter = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (!shooter.equals(game.getCurrentTurn())) {
            return ResponseEntity.badRequest().body("Not your turn!");
        }

        Player opponent = game.getPlayers().stream()
                .filter(p -> !p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Opponent not found"));

        List<Ship> opponentShips = shipRepository.findByPlayerId(opponent.getId());
        Optional<Ship> hitShip = opponentShips.stream()
                .filter(s -> s.getX() == x && s.getY() == y)
                .findFirst();

        if (hitShip.isPresent()) {
            shipRepository.delete(hitShip.get());

            boolean opponentHasShipsLeft = shipRepository.findByPlayerId(opponent.getId()).size() > 0;

            if (!opponentHasShipsLeft) {
                game.setFinished(true);
                game.setActive(false);
                game.setWinner(shooter);
                gameRepository.save(game);

                return ResponseEntity.ok("HIT! " + opponent.getName() + " hat keine Schiffe mehr! "
                        + shooter.getName() + " gewinnt!");
            }

            game.setCurrentTurn(opponent);
            gameRepository.save(game);
            return ResponseEntity.ok("HIT!");
        }

        game.setCurrentTurn(opponent);
        gameRepository.save(game);
        return ResponseEntity.ok("MISS!");
    }
    @GetMapping("/player/{playerId}")
    public List<Ship> getShipsByPlayer(@PathVariable Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }


}