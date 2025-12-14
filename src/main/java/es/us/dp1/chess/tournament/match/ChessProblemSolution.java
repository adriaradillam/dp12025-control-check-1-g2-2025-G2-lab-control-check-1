package es.us.dp1.chess.tournament.match;


import java.time.LocalDateTime;

import es.us.dp1.chess.tournament.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChessProblemSolution extends BaseEntity {

    @Size(min = 3, max = 50)
    @NotBlank
    String name;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    boolean minimal;

    @Size(max = 4000)
    String notes;

    @Min(1)
    @Max(15)
    Integer expectedMoveCount;
    
    @ManyToOne
    @NotNull
    ChessProblem problem;

    @OneToOne
    @NotNull
    ChessBoard solutionBoard;
}
