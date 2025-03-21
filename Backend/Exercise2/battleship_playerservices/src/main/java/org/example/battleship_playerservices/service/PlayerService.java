package org.example.battleship_playerservices.service;

import org.example.battleship_playerservices.entity.Player;
import org.example.battleship_playerservices.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final RestTemplate restTemplate;

    public PlayerService(PlayerRepository playerRepository, RestTemplate restTemplate) {
        this.playerRepository = playerRepository;
        this.restTemplate = restTemplate;
    }

    public Player createPlayer(String name, Long gameId) {
        Player player = new Player();
        player.setName(name);
        Player savedPlayer = playerRepository.save(player);

        try {
            restTemplate.put("http://localhost:8080/api/games/" + gameId + "/players/" + savedPlayer.getId(), null);
        } catch (Exception e) {
            throw new RuntimeException("Game with ID " + gameId + " not found.");
        }

        return savedPlayer;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public String getPlayerName(Long id) {
        Player player = getPlayerById(id);
        return player.getName();
    }
}