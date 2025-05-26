package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Rook(override val isWhite: Boolean) : Figure {
    private val directionsRook = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT
    )
    override fun symbol() = if (isWhite) "t" else "T"
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsRook) {
            val dx = direction.dx
            val dy = direction.dy
            var current = Position(from.column + dx, from.row + dy)
            while (current.column in 'a'..'h' && current.row in 1..8) {
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.isWhite != this.isWhite) {
                        moves.add(current)
                    }
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position(current.column + dx, current.row + dy)
            }
        }
        return moves
    }

}