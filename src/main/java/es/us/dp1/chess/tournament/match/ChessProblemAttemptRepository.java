package es.us.dp1.chess.tournament.match;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.us.dp1.chess.tournament.user.User;



public interface ChessProblemAttemptRepository extends CrudRepository<ChessProblemAttempt,Integer>{

    List<ChessProblemAttempt> findAll();    
    
    @Query("SELECT cpa FROM ChessProblemAttempt cpa WHERE cpa.player=?1 AND cpa.problem=?2 AND cpa.finish IS NULL")
    List<ChessProblemAttempt> findOngoingAttemptsByUserAndProblem(User player, ChessProblem problem);
    
}
