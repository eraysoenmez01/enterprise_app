package org.example.battleship.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    private int size; // 👈 HINZUGEFÜGT

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference // ✅ verhindert Endlosschleife
    private Player player;

    public Ship() {}

    public Ship(int x, int y, int size, Player player) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.player = player;
    }

    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() { // 👈 GETTER für size
        return size;
    }

    public void setSize(int size) { // 👈 SETTER für size
        this.size = size;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}