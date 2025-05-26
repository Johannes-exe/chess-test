package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Knight(override val isWhite: Boolean) : Figure {
    private val directionsKnight = listOf(
        Directions.Knight_UP_LEFT,
        Directions.Knight_UP_RIGHT,
        Directions.Knight_DOWN_LEFT,
        Directions.Knight_DOWN_RIGHT,
        Directions.Knight_LEFT_UP,
        Directions.Knight_LEFT_DOWN,
        Directions.Knight_RIGHT_UP,
        Directions.Knight_RIGHT_DOWN
    )
    override fun symbol() = if (isWhite) "s" else "S"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Ein component davon machen
        for (direction in directionsKnight) {
            val dx = direction.dx
            val dy = direction.dy
            val target = Position(from.column + dx, from.row + dy)
            if (target.column in 'a'..'h' && target.row in 1..8) {
                val destination = board.getFigureAt(target)
                if (destination == null || destination.isWhite != this.isWhite) {
                    moves.add(target)
                }
            }
        }
        return moves
    }
}