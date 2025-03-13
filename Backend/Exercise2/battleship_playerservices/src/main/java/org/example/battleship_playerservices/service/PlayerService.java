package org.example.battleship_playerservices.service;

import org.example.battleship_gamerservices.entity.Game;
import org.example.battleship_gamerservices.entity.Player;
import org.example.battleship_gamerservices.repository.GameRepository;
import org.example.battleship_gamerservices.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/{id}/name")
    public String getPlayerName(@PathVariable Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return player.getName();
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
