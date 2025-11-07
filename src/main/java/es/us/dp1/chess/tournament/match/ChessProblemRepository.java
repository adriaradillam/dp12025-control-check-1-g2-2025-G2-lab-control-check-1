package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;

public interface ChessProblemRepository {

    List<ChessProblem> findByDifficultyAndNumPieces(Integer difficultyLevel, Integer numPieces);

    List<ChessProblem> findAll();

    Optional<ChessProblem> findById(Integer i);

    void delete(ChessProblem piece);
    
}
