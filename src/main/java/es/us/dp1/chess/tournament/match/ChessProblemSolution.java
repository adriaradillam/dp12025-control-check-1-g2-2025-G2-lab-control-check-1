package es.us.dp1.chess.tournament.match;


import java.time.LocalDateTime;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChessProblemSolution {
    String name;
    LocalDateTime createdAt;
    boolean minimal;
    String notes;
    Integer expectedMoveCount;
    
    @Transient
    ChessProblem problem;

    @Transient
    ChessBoard solutionBoard;
}
