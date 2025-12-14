package es.us.dp1.chess.tournament.match;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ChessProblemSolutionRepository extends CrudRepository<ChessProblemSolution, Integer>{

    Optional<ChessProblemSolution> findById(int i);
    
}
