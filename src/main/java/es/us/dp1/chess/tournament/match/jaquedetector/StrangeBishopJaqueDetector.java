package es.us.dp1.chess.tournament.match.jaquedetector;

public class StrangeBishopJaqueDetector implements BishopJaqueDetector {

    private boolean isWithinBoardLimits(int coordinate) {
        return coordinate >= 0 && coordinate <= 7;
    }

    @Override
    public boolean isJaque(int originX, int originY, int destinationX, int destinationY)
            throws InvalidPositionException {
        // valida límites pero contiene un BUG: permite origen == destino
        if (!isWithinBoardLimits(originX) || !isWithinBoardLimits(originY)
                || !isWithinBoardLimits(destinationX) || !isWithinBoardLimits(destinationY)) {
            return false;
        }

        int deltaX = Math.abs(originX - destinationX);
        int deltaY = Math.abs(originY - destinationY);

        // BUG intencional: no comprueba deltaX != 0, por tanto considera la misma casilla como diagonal válida
        return deltaX == deltaY;
    }
}