package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Figure
import hwr.oop.Position

class King(override val isWhite: Boolean) : Figure {
    var firstMove = true
    override fun symbol() = if (isWhite) "k" else "K"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Alle möglichen Bewegungsrichtungen des Königs
        val directions = listOf(
            Pair(1, 0),   // Rechts
            Pair(-1, 0),  // Links
            Pair(0, 1),   // Oben
            Pair(0, -1),  // Unten
            Pair(1, 1),   // Rechts oben
            Pair(1, -1),  // Rechts unten
            Pair(-1, 1),  // Links oben
            Pair(-1, -1)  // Links unten
        )

        for ((dx, dy) in directions) {
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