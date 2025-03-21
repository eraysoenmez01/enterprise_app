package org.example.battleship_gamerservices.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class PlayerClient implements PlayerClientInterface {

    private final RestTemplate restTemplate;

    @Value("${player.service.url}")
    private String playerServiceUrl;

    public PlayerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker(name = "playerService", fallbackMethod = "fallbackGetPlayerName")
    public String getPlayerName(Long playerId) {
        Map response = restTemplate.getForObject(playerServiceUrl + "/api/players/" + playerId, Map.class);
        return response != null ? (String) response.get("name") : "Unknown";
    }

    public String fallbackGetPlayerName(Long playerId, Throwable t) {
        throw new RuntimeException("PlayerService is currently unavailable.");
    }
}