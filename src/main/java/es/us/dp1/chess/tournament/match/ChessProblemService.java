package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;

public class ChessProblemService {

    ChessProblemRepository repository;

    public ChessProblemService(Optional<ChessProblemRepository> repository) {
        this.repository = repository.orElse(null);
    }

    public List<ChessProblem> getProblemsByDifficultyAndNumPieces(Integer difficulty, Integer numPieces) {
        // TODO: implement this method to solve Exercise 2a
        return null;
    }

    public List<ChessProblem> getAll() {
        // TODO: implement this method to solve Exercise 2a
        return null;
    }

    public ChessProblem getById(Integer i) {
        // TODO: implement this method to solve Exercise 2a
        return null;
    }

    public void delete(ChessProblem piece) {
        // TODO: implement this method to solve Exercise 2a
    }
    
}
