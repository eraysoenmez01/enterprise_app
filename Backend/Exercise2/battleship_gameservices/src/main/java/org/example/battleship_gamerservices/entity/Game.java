package org.example.battleship_gamerservices.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean active;
    private boolean finished = false;

    private Long currentTurnPlayerId;
    private Long winnerPlayerId;

    @ElementCollection
    private List<Long> playerIds = new ArrayList<>();

    public Game() {
        this.active = true;
    }

    public Game(String name) {
        this.name = name;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFinished() {
        return finished;
    }

    public Long getCurrentTurnPlayerId() {
        return currentTurnPlayerId;
    }

    public void setCurrentTurnPlayerId(Long currentTurnPlayerId) {
        this.currentTurnPlayerId = currentTurnPlayerId;
    }

    public Long getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Long winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}