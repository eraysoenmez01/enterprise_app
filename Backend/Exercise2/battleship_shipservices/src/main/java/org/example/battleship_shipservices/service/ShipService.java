package org.example.battleship_shipservices.service;

import org.example.battleship_shipservices.entity.Ship;
import org.example.battleship_shipservices.repository.ShipRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final RestTemplate restTemplate;

    public ShipService(ShipRepository shipRepository, RestTemplate restTemplate) {
        this.shipRepository = shipRepository;
        this.restTemplate = restTemplate;
    }

    public void placeShips(Long playerId, List<String> positions) {
        String url = "http://localhost:8081/api/players/" + playerId;
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Player with ID " + playerId + " not found.");
        }

        for (String pos : positions) {
            int x = Character.getNumericValue(pos.charAt(0));
            int y = Character.getNumericValue(pos.charAt(1));
            Ship ship = new Ship(x, y, 1, playerId);
            shipRepository.save(ship);
        }
    }

    public List<Ship> getShipsByPlayer(Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }

    public int countShipsByPlayerId(Long playerId) {
        return shipRepository.findByPlayerId(playerId).size();
    }

    public boolean processGuess(Long targetPlayerId, int x, int y) {
        List<Ship> ships = shipRepository.findByPlayerId(targetPlayerId);
        for (Ship ship : ships) {
            if (ship.getX() == x && ship.getY() == y) {
                shipRepository.delete(ship);
                return true;
            }
        }
        return false;
    }
}