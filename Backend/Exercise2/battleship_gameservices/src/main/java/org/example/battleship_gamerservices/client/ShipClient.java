package org.example.battleship_gamerservices.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShipClient {

    private final RestTemplate restTemplate;

    @Value("${ship.service.url}")
    private String shipServiceUrl;

    public ShipClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getShipCountByPlayerId(Long playerId) {
        Object[] ships = restTemplate.getForObject(shipServiceUrl + "/api/ships/player/" + playerId, Object[].class);
        return ships != null ? ships.length : 0;
    }
}