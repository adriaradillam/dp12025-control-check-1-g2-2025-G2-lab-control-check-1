package es.us.dp1.chess.tournament.match;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProblemSourceRepository extends CrudRepository<ProblemSource, Integer> {

    Optional<ProblemSource> findById(int i);
    
}
