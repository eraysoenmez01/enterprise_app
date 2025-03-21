# Battleship Microservices 

## Services & Verantwortlichkeiten

### GameService (`:8080`)
Verantwortlich für die **gesamte Spiellogik**:
- Erstellen & Speichern von Spielen
- Spieler zum Spiel hinzufügen (PUT)
- Spielstatus verwalten
- Spielzüge ausführen (`makeGuess`)
- Gewinner bestimmen
- Spielfortschritt anzeigen (`getGameStatus`)
- Turn-Wechsel durchführen

Kommuniziert per REST mit PlayerService & ShipService  
Kapselt die Spiellogik vollständig  
Speichert Rateversuche (`Guess`-Entity)

---

### PlayerService (`:8081`)
Verantwortlich für das **Verwalten von Spielern**:
- Spieler erstellen
- Spieler abrufen
- Name & ID zurückgeben

Dient zur Spielerverwaltung und Darstellung im Status

---

### ShipService (`:8082`)
Verantwortlich für das **Setzen und Verwalten von Schiffen**:
- Schiffe auf Spielfeld platzieren
- Treffer prüfen (`guess`)
- Anzahl verbleibender Schiffe zählen
- Alle Schiffe eines Spielers abrufen

Verwaltet die Spielfeldzustände der Spieler

---

## Datenflüsse

### Spiel erstellen & Spieler hinzufügen
1. POST `/api/games` → neues Spiel
2. PUT `/api/games/{gameId}/players/{playerId}` → Spieler hinzufügen

### Schiffe setzen
- POST `/api/games/{gameId}/players/{playerId}/ships`  
  → Spiel validiert & delegiert an ShipService

### Raten / Schießen
- POST `/api/games/{gameId}/guess?playerId=...&x=...&y=...`  
  → Spiellogik im GameService prüft Turn, speichert Guess, fragt ShipService und beendet ggf. Spiel

### Status anzeigen
- GET `/api/games/{gameId}/status`  
  → Zeigt aktuellen Zustand, Spieler, Schiffe, Gewinner etc.

---
 