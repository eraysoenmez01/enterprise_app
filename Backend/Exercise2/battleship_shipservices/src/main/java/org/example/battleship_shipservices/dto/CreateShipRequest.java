package org.example.battleship_shipservices.dto;

import java.util.List;

public class CreateShipRequest {
    private Long playerId;
    private int size;
    private List<String> positions;

    public CreateShipRequest() {}

    public CreateShipRequest(Long playerId, int size, List<String> positions) {
        this.playerId = playerId;
        this.size = size;
        this.positions = positions;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }
}