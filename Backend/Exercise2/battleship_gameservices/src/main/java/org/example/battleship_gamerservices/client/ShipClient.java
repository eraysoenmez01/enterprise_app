package org.example.battleship_gamerservices.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ShipClient implements ShipClientInterface {

    private final RestTemplate restTemplate;

    @Value("${ship.service.url}")
    private String shipServiceUrl;

    public ShipClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker(name = "shipService", fallbackMethod = "fallbackGetShipCount")
    public int getShipCountByPlayerId(Long playerId) {
        Object[] ships = restTemplate.getForObject(
                shipServiceUrl + "/api/ships/player/" + playerId,
                Object[].class);
        return ships != null ? ships.length : 0;
    }

    public int fallbackGetShipCount(Long playerId, Throwable t) {
        throw new RuntimeException("ShipService is down – can't fetch ship count.");
    }

    @Override
    @CircuitBreaker(name = "shipService", fallbackMethod = "fallbackPlaceShips")
    public void placeShips(Long playerId, List<String> positions) {
        String url = shipServiceUrl + "/api/ships/place";
        var requestBody = new PlaceShipsRequest(playerId, positions);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlaceShipsRequest> request = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(url, request, Void.class);
    }

    public void fallbackPlaceShips(Long playerId, List<String> positions, Throwable t) {
        throw new RuntimeException("ShipService is down – can't place ships.");
    }

    @Override
    @CircuitBreaker(name = "shipService", fallbackMethod = "fallbackGuess")
    public boolean guess(Long gameId, Long playerId, int x, int y) {
        String url = shipServiceUrl + "/api/ships/guess"
                + "?gameId=" + gameId
                + "&playerId=" + playerId
                + "&x=" + x
                + "&y=" + y;

        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, null, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public boolean fallbackGuess(Long gameId, Long playerId, int x, int y, Throwable t) {
        throw new RuntimeException("ShipService is down – can't guess right now.");
    }

    // Inner DTO klasse for POST /place
    private static class PlaceShipsRequest {
        private Long playerId;
        private List<String> positions;

        public PlaceShipsRequest(Long playerId, List<String> positions) {
            this.playerId = playerId;
            this.positions = positions;
        }

        public Long getPlayerId() {
            return playerId;
        }

        public List<String> getPositions() {
            return positions;
        }
    }
}