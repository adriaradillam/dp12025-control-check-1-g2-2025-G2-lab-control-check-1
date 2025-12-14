package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChessProblemRepository extends CrudRepository<ChessProblem, Integer>{

    @Query("SELECT cp FROM ChessProblem cp WHERE cp.difficultyLevel >= :difficultyLevel AND SIZE(cp.board.pieces) < :numPieces")
    List<ChessProblem> findByDifficultyAndNumPieces(Integer difficultyLevel, Integer numPieces);

    List<ChessProblem> findAll();

    Optional<ChessProblem> findById(Integer i);

    void delete(ChessProblem piece);
    
}
