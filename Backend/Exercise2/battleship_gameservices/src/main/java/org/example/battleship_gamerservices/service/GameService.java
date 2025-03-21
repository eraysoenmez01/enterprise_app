package org.example.battleship_gamerservices.service;

import org.example.battleship_gamerservices.client.PlayerClientInterface;
import org.example.battleship_gamerservices.client.ShipClientInterface;
import org.example.battleship_gamerservices.entity.Game;
import org.example.battleship_gamerservices.entity.Guess;
import org.example.battleship_gamerservices.repository.GameRepository;
import org.example.battleship_gamerservices.repository.GuessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;
    private final PlayerClientInterface playerClient;
    private final ShipClientInterface shipClient;

    public GameService(GameRepository gameRepository, GuessRepository guessRepository,
                       PlayerClientInterface playerClient, ShipClientInterface shipClient) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
        this.playerClient = playerClient;
        this.shipClient = shipClient;
    }

    public Game saveGame(Game game) {
        if (game.getPlayerIds().size() == 2 && game.getCurrentTurnPlayerId() == null) {
            game.setCurrentTurnPlayerId(game.getPlayerIds().get(0));
        }
        return gameRepository.save(game);
    }

    private Long getOpponentId(Game game, Long playerId) {
        return game.getPlayerIds()
                .stream()
                .filter(id -> !id.equals(playerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No opponent found"));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public void switchTurn(Game game) {
        List<Long> playerIds = game.getPlayerIds();
        if (playerIds.size() == 2) {
            Long current = game.getCurrentTurnPlayerId();
            Long next = playerIds.get(0).equals(current) ? playerIds.get(1) : playerIds.get(0);
            game.setCurrentTurnPlayerId(next);
            gameRepository.save(game);
        }
    }

    public String placeShips(Long gameId, Long playerId, List<String> positions) {
        Game game = getGameById(gameId);

        if (!game.getPlayerIds().contains(playerId)) {
            throw new RuntimeException("Player does not belong to this game.");
        }

        shipClient.placeShips(playerId, positions);
        return "Ships placed for player " + playerId;
    }

    public String makeGuess(Long gameId, Long playerId, int x, int y) {
        Game game = getGameById(gameId);

        if (game.isFinished()) {
            throw new RuntimeException("Game is already finished!");
        }

        if (!playerId.equals(game.getCurrentTurnPlayerId())) {
            throw new RuntimeException("Not your turn!");
        }

        // Schuss ausführen
        Long opponentId = getOpponentId(game, playerId);
        boolean isHit = shipClient.guess(gameId, opponentId, x, y);

        guessRepository.save(new Guess(gameId, playerId, x, y, isHit));

        String message;
        if (isHit) {
            int remainingShips = shipClient.getShipCountByPlayerId(opponentId);
            if (remainingShips == 0) {
                game.setFinished(true);
                game.setActive(false);
                game.setWinnerPlayerId(playerId);
                message = "HIT! Game Over. Winner is " + playerClient.getPlayerName(playerId);
            } else {
                switchTurn(game);
                message = "HIT!";
            }
        } else {
            switchTurn(game);
            message = "MISS!";
        }

        gameRepository.save(game);
        return message;
    }


    public String getFormattedGameStatus(Long gameId) {
        Game game = getGameById(gameId);

        StringBuilder sb = new StringBuilder();
        sb.append("Game: ").append(game.getName()).append(" (ID: ").append(game.getId()).append(")\n");
        sb.append("Active: ").append(game.isActive()).append("\n");
        sb.append("Finished: ").append(game.isFinished()).append("\n");
        sb.append("Current Turn: ").append(game.getCurrentTurnPlayerId() != null ? playerClient.getPlayerName(game.getCurrentTurnPlayerId()) : "None").append("\n");
        sb.append("Winner: ").append(game.getWinnerPlayerId() != null ? playerClient.getPlayerName(game.getWinnerPlayerId()) : "None").append("\n\n");

        for (Long playerId : game.getPlayerIds()) {
            String name = playerClient.getPlayerName(playerId);
            int shipCount = shipClient.getShipCountByPlayerId(playerId);
            sb.append("Player: ").append(name).append(" | Ships: ").append(shipCount).append("\n");
        }

        return sb.toString();
    }

    public void addPlayerToGame(Long gameId, Long playerId) {
        Game game = getGameById(gameId);

        if (!game.getPlayerIds().contains(playerId)) {
            game.getPlayerIds().add(playerId);
            if (game.getPlayerIds().size() == 2 && game.getCurrentTurnPlayerId() == null) {
                game.setCurrentTurnPlayerId(game.getPlayerIds().get(0));
            }
            gameRepository.save(game);
        }
    }
}
