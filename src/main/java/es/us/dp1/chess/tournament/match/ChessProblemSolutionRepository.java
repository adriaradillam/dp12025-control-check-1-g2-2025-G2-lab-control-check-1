package es.us.dp1.chess.tournament.match;

import java.util.Optional;

public interface ChessProblemSolutionRepository {

    Optional<ChessProblemSolution> findById(int i);
    
}
