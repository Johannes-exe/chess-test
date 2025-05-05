package hwr.oop

class Rook(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "t" else "T"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Row - from.Row
        val deltaX = to.Column - from.Column

        // Der Turm kann sich nur horizontal oder vertikal bewegen
        if (deltaX != 0 && deltaY != 0) {
            return false
        }

        // Prüfen, ob der Weg frei ist
        val stepX = if (deltaX == 0) 0 else deltaX / kotlin.math.abs(deltaX)
        val stepY = if (deltaY == 0) 0 else deltaY / kotlin.math.abs(deltaY)

        var current = Position(from.Column + stepX, from.Row + stepY)
        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position(current.Column + stepX, current.Row + stepY)
        }

        // Zielposition prüfen
        val destination = board.getFigureAt(to)
        return destination == null || destination.isWhite != this.isWhite
    }
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Prüfen aller möglichen Richtungen (horizontal und vertikal)
        val directions = listOf(
            Pair(1, 0),  // Rechts
            Pair(-1, 0), // Links
            Pair(0, 1),  // Oben
            Pair(0, -1)  // Unten
        )

        for ((dx, dy) in directions) {
            var current = Position(from.Column + dx, from.Row + dy)
            while (current.Column in 'a'..'h' && current.Row in 1..8) {
                if (canMove(from, current, board)) {
                    moves.add(current)
                }
                if (board.getFigureAt(current) != null) {
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position(current.Column + dx, current.Row + dy)
            }
        }

        return moves
    }
}