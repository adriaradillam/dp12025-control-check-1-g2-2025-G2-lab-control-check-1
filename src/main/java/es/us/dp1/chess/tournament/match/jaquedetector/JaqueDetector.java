package es.us.dp1.chess.tournament.match.jaquedetector;

public interface JaqueDetector {
    boolean isJaque(int originX, int originY, int destinationX, int destinationY) throws InvalidPositionException;
}
