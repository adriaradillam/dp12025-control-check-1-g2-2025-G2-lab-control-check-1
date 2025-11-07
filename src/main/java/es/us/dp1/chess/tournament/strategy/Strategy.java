package es.us.dp1.chess.tournament.strategy;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Strategy {
    
    String description; 

    MatchPhase phase;

    Integer succesRate;
}
