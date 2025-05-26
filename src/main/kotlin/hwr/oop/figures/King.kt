package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position


class King(override val isWhite: Boolean) : Figure {
    private var firstMove = true
    private val directionsKing = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT,
        Directions.UP_LEFT,
        Directions.UP_RIGHT,
        Directions.DOWN_LEFT,
        Directions.DOWN_RIGHT,
    )
    override fun symbol() = if (isWhite) "k" else "K"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKing) {
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

    fun kingCastle(): Boolean{
        if (firstMove) {
            firstMove = false
            return true
        }else{
            return false
        }
    }

}