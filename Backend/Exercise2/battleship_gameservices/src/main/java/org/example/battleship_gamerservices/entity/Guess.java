package org.example.battleship_gamerservices.entity;

import jakarta.persistence.*;

@Entity
public class Guess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private Long playerId;
    private int x;
    private int y;
    private boolean hit;

    public Guess() {}

    public Guess(Long gameId, Long playerId, int x, int y, boolean hit) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}