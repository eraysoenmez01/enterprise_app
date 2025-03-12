package org.example.battleship.service;

import org.example.battleship.entity.Game;
import org.example.battleship.entity.Player;
import org.example.battleship.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame(Game game) {
        if (game.getPlayers().size() == 2 && game.getCurrentTurn() == null) {
            game.setCurrentTurn(game.getPlayers().get(0));
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
        List<Player> players = game.getPlayers();
        if (players.size() == 2) {
            Player current = game.getCurrentTurn();
            Player next = players.get(0).getId().equals(current.getId()) ? players.get(1) : players.get(0);
            game.setCurrentTurn(next);
        }
    }
}