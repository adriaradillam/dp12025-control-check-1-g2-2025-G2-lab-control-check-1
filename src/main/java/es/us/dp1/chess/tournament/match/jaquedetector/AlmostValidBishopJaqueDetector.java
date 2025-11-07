package es.us.dp1.chess.tournament.match.jaquedetector;

public class AlmostValidBishopJaqueDetector implements BishopJaqueDetector {
    private boolean isWithinBoardLimits(int coordinate) {
        return coordinate >= 0 && coordinate <= 7;
    }

    @Override
    public boolean isJaque(int originX, int originY, int destinationX, int destinationY)
            throws InvalidPositionException {
        if (!isWithinBoardLimits(originX) || !isWithinBoardLimits(originY)
                || !isWithinBoardLimits(destinationX) || !isWithinBoardLimits(destinationY)) {
            throw new InvalidPositionException();
        }

        int deltaX = Math.abs(originX - destinationX);
        int deltaY = Math.abs(originY - destinationY);
        // Alfil casi válido: movimiento diagonal (distancias iguales) o movimiento diagonal hacia abajo a la derecha        
        return deltaX != 0 && (deltaX == deltaY || (originX<destinationX && originY<destinationY));
    }
}
