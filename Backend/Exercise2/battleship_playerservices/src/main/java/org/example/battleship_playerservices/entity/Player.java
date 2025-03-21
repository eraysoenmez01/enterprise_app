package org.example.battleship_playerservices.entity;

import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long gameId; // Nur Referenz auf Game

    public Player() {}

    public Player(String name, Long gameId) {
        this.name = name;
        this.gameId = gameId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}