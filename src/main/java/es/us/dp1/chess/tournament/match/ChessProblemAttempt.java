package es.us.dp1.chess.tournament.match;

import java.time.LocalDateTime;

import es.us.dp1.chess.tournament.model.NamedEntity;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChessProblemAttempt extends NamedEntity implements Cloneable {
    LocalDateTime start;
    LocalDateTime finish;

    @ManyToOne
    User player;

    @ManyToOne
    ChessProblem problem;

    @OneToOne(cascade = CascadeType.ALL)
    ChessBoard board;

    public ChessProblemAttempt clone() {
        ChessProblemAttempt match = new ChessProblemAttempt();
        match.setName(this.getName());
        match.setStart(this.getStart());
        match.setFinish(this.getFinish());
        match.setPlayer(this.getPlayer());
        match.setBoard(getBoard().clone());
        return match;
    }

}
