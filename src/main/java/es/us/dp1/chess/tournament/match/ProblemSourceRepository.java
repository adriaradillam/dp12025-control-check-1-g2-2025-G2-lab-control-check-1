package es.us.dp1.chess.tournament.match;

import java.util.Optional;

public interface ProblemSourceRepository {

    Optional<ProblemSource> findById(int i);
    
}
