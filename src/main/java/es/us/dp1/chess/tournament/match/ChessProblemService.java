package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChessProblemService {

    ChessProblemRepository repository;

    public ChessProblemService(Optional<ChessProblemRepository> repository) {
        this.repository = repository.orElse(null);
    }

    @Transactional
    public List<ChessProblem> getProblemsByDifficultyAndNumPieces(Integer difficulty, Integer numPieces) {
        return repository.findByDifficultyAndNumPieces(difficulty, numPieces);
    }

    @Transactional
    public List<ChessProblem> getAll() {
        return repository.findAll();
    }

    @Transactional
    public ChessProblem getById(Integer i) {
        return repository.findById(i).get();
    }

    @Transactional
    public void delete(ChessProblem piece) {
        repository.delete(piece);
    }
    
}
