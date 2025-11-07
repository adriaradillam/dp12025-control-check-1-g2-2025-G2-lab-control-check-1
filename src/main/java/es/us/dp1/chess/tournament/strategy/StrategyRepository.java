package es.us.dp1.chess.tournament.strategy;

import java.util.Optional;

public interface StrategyRepository {
    Optional<Strategy> findById(Integer id);
}
