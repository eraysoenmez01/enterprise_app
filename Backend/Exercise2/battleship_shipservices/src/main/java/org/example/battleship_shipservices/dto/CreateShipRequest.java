package org.example.battleship_shipservices.dto;

import java.util.List;

public class CreateShipRequest {
    private Long playerId;
    private List<String> positions;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }
}