package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChessProblemAttemptService {

    ChessProblemAttemptRepository repo;    

    @Autowired
    public ChessProblemAttemptService(ChessProblemAttemptRepository repo) {
        this.repo = repo;        
    }

    @Transactional(readOnly = true)
    public Optional<ChessProblemAttempt> getMatchById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public ChessProblemAttempt save(ChessProblemAttempt m) {        
        // TODO: Change to solve exercise 1:
        return null;
    }

    @Transactional(readOnly = true)
    public List<ChessProblemAttempt> getAttempts() {
        return repo.findAll();
    }    

}
