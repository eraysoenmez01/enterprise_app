package org.example.battleship_gamerservices.service;

import org.example.battleship_gamerservices.entity.Game;
import org.example.battleship_gamerservices.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public Game saveGame(Game game) {
        // Spieler zufällig den ersten Zug geben, wenn genau 2 Spieler vorhanden sind
        if (game.getPlayerIds().size() == 2 && game.getCurrentTurnPlayerId() == null) {
            game.setCurrentTurnPlayerId(game.getPlayerIds().get(0));
        }
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public void switchTurn(Game game) {
        List<Long> playerIds = game.getPlayerIds();
        if (playerIds.size() == 2) {
            Long current = game.getCurrentTurnPlayerId();
            Long next = playerIds.get(0).equals(current) ? playerIds.get(1) : playerIds.get(0);
            game.setCurrentTurnPlayerId(next);
            gameRepository.save(game); // 🛠️ nicht vergessen!
        }
    }
}