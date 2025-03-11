package org.example.battleship.service;

import org.example.battleship.entity.Player;
import org.example.battleship.entity.Ship;
import org.example.battleship.repository.PlayerRepository;
import org.example.battleship.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {

    private final PlayerRepository playerRepository;
    private final ShipRepository shipRepository;

    public ShipService(PlayerRepository playerRepository, ShipRepository shipRepository) {
        this.playerRepository = playerRepository;
        this.shipRepository = shipRepository;
    }

    public List<Ship> placeShips(Long playerId, List<String> positions) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        for (String pos : positions) {
            int x = Character.getNumericValue(pos.charAt(0));
            int y = Character.getNumericValue(pos.charAt(1));

            Ship ship = new Ship();
            ship.setX(x);
            ship.setY(y);
            ship.setSize(1); // alle Schiffe gleich groß
            ship.setPlayer(player);

            shipRepository.save(ship);
        }

        return shipRepository.findByPlayerId(playerId);
    }

    public boolean isHit(Long opponentId, int x, int y) {
        return shipRepository.findByPlayerId(opponentId).stream()
                .anyMatch(ship -> ship.getX() == x && ship.getY() == y);
    }

    public List<Ship> getShipsByPlayer(Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }
}