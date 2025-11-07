package es.us.dp1.chess.tournament.match.jaquedetector;

public class CrappyBishopJaqueDetector implements BishopJaqueDetector {

    @Override
    public boolean isJaque(int originX, int originY, int destinationX, int destinationY)
            throws InvalidPositionException {
        return false;
    }
}