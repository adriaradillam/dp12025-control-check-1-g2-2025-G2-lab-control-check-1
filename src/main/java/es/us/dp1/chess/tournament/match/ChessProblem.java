package es.us.dp1.chess.tournament.match;

import es.us.dp1.chess.tournament.model.NamedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ChessProblem extends NamedEntity {

    int difficultyLevel;
    String goal;

    @OneToOne(cascade = CascadeType.ALL)
    ChessBoard board;
        
}
