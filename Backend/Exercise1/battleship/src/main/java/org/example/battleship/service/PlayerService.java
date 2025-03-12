package org.example.battleship.service;

import org.example.battleship.entity.Game;
import org.example.battleship.entity.Player;
import org.example.battleship.repository.GameRepository;
import org.example.battleship.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public Player createPlayer(String name, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Player player = new Player();
        player.setName(name);
        player.setGame(game);
        Player savedPlayer = playerRepository.save(player);

        game.getPlayers().add(savedPlayer);

        if (game.getPlayers().size() == 2 && game.getCurrentTurn() == null) {
            game.setCurrentTurn(game.getPlayers().get(0));
            gameRepository.save(game);
        }

        return savedPlayer;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
