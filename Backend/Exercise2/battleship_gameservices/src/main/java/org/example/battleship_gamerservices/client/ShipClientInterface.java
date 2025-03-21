package org.example.battleship_gamerservices.client;

import java.util.List;

public interface ShipClientInterface {
    int getShipCountByPlayerId(Long playerId);
    void placeShips(Long playerId, List<String> positions);
    boolean guess(Long gameId, Long playerId, int x, int y);
}