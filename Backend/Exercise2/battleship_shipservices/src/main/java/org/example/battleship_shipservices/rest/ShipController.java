package org.example.battleship_shipservices.rest;

import org.example.battleship_shipservices.dto.CreateShipRequest;

import org.example.battleship_shipservices.entity.Ship;
import org.example.battleship_shipservices.service.ShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeShips(@RequestBody CreateShipRequest request) {
        try {
            shipService.placeShips(request.getPlayerId(), request.getPositions());
            return ResponseEntity.ok("Ships placed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/player/{playerId}")
    public List<Ship> getShipsByPlayer(@PathVariable Long playerId) {
        return shipService.getShipsByPlayer(playerId);
    }

    @GetMapping("/player/{playerId}/count")
    public int countShipsByPlayerId(@PathVariable Long playerId) {
        return shipService.countShipsByPlayerId(playerId);
    }

    @PostMapping("/guess")
    public ResponseEntity<Boolean> guess(
            @RequestParam("playerId") Long targetPlayerId,
            @RequestParam int x,
            @RequestParam int y) {
        boolean hit = shipService.processGuess(targetPlayerId, x, y);
        return ResponseEntity.ok(hit);
    }
}