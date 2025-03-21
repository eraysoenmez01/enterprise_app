package org.example.battleship_gamerservices.repository;

import org.example.battleship_gamerservices.entity.Guess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessRepository extends JpaRepository<Guess, Long> {

}