package org.example.battleship_gamerservices.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class PlayerClient {

    private final RestTemplate restTemplate;

    @Value("${player.service.url}")
    private String playerServiceUrl;

    public PlayerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPlayerName(Long playerId) {
        Map response = restTemplate.getForObject(playerServiceUrl + "/api/players/" + playerId, Map.class);
        return (String) response.get("name");
    }
}