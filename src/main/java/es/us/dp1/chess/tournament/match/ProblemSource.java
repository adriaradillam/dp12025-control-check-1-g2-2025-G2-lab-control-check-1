package es.us.dp1.chess.tournament.match;

import java.util.List;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemSource {
    String name;
    String author;
    String description;
    String contactEmail;

    @Transient
    List<ChessProblem> problems;
}
