package org.example.battleship_playerservices.dto;

public class CreatePlayerRequest {
    private String name;
    private Long gameId;

    public CreatePlayerRequest(String name, Long gameId) {
        this.name = name;
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
