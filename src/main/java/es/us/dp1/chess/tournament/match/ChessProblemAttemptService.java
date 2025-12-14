package es.us.dp1.chess.tournament.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.chess.tournament.exceptions.UnfeasibleAttemptException;

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

    @Transactional(rollbackFor = UnfeasibleAttemptException.class)
    public ChessProblemAttempt save(ChessProblemAttempt m) throws UnfeasibleAttemptException {        
        List<ChessProblemAttempt> list = repo.findOngoingAttemptsByUserAndProblem(m.getPlayer(), m.getProblem())
                                    .stream().filter(x -> x.getId() != m.getId()).toList();
        if (!list.isEmpty() || (m.getFinish() != null && m.getStart().isAfter(m.getFinish()))) {
            throw new UnfeasibleAttemptException("Me cago");
        } else {
            return repo.save(m);
        } 
    }

    @Transactional(readOnly = true)
    public List<ChessProblemAttempt> getAttempts() {
        return repo.findAll();
    }    

}
