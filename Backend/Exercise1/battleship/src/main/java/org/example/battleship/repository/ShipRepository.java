package org.example.battleship.repository;

import org.example.battleship.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByPlayerId(Long playerId);
}