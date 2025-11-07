package es.us.dp1.chess.tournament.match.jaquedetector;

public class DummyBishopJaqueDetector implements BishopJaqueDetector {

    @Override
    public boolean isJaque(int originX, int originY, int destinationX, int destinationY)
            throws InvalidPositionException {
        return true;
    }
}