package org.example.battleship_shipservices.repository;

import org.example.battleship_shipservices.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByPlayerId(Long playerId);
}