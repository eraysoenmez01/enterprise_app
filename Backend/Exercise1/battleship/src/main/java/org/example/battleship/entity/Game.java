package org.example.battleship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.battleship.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatisch generierte ID durch DB
    private Long id;

    private String name;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "current_turn_player_id")
    @JsonIgnore // 🛑 Verhindert Endlosschleife!
    private Player currentTurn;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    @JsonIgnore // 🛑 Verhindert Endlosschleife!
    private Player winner;

    // In Game.java
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    private boolean finished = false;



    public Game() {
        this.active = true; // Standardwert setzen, falls nötig
    }

    public Game(String name) {
        this.name = name;
        this.active = true; // Spiel startet aktiv
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

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}