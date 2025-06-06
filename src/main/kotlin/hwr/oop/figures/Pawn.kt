package hwr.oop.figures

import hwr.oop.*

/**
 * Represents a Pawn chess piece.
 *
 * @property pawnColor The color of the pawn (white or black).
 */
class Pawn(private val pawnColor: Color) : Figure {
    /**
     * Returns the color of the pawn.
     *
     * @return The color of the pawn.
     */
    override fun color() = pawnColor

    /**
     * Returns the symbol representing the pawn.
     * "b" for white, "B" for black.
     *
     * @return The symbol of the pawn.
     */
    override fun symbol() = if (pawnColor == Color.WHITE) "b" else "B"

    /**
     * Calculates all valid target positions for the pawn from the given position on the current chessboard.
     *
     * The pawn can move forward one square if the square is empty, or two squares from its starting position.
     * It can also capture diagonally to the left or right if an opponent's piece is present.
     *
     * @param from The starting position of the pawn.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        val direction = if (color() == Color.WHITE) 1 else -1
        val startZeile = if (color() == Color.WHITE) 2 else 7


        // Normaler Zug
        val forwardOneIndex = from.row.ordinal + direction
        if (forwardOneIndex in Row.values().indices) {
            val forwardOne = Position(from.column, Row.values()[forwardOneIndex])
            if (board.getFigureAt(forwardOne) == null) {
                moves.add(forwardOne)
            }
        }

            // Erster Zug (2 Felder)
            val forwardTwoIndex = from.row.ordinal + 2 * direction
            if (forwardTwoIndex in Row.values().indices) {
                val forwardOne = Position(from.column, Row.values()[forwardOneIndex])
                val forwardTwo = Position(from.column, Row.values()[forwardTwoIndex])
                if (board.getFigureAt(forwardTwo) == null && board.getFigureAt(forwardOne) == null) {
                    if (from.row.ordinal + 1 == startZeile) {
                        moves.add(forwardTwo)
                    }
                }
        }

        // Schlagzüge
        val attackLeftColumnIndex = from.column.ordinal - 1
        val attackRightColumnIndex = from.column.ordinal + 1
        val attackRowIndex = from.row.ordinal + direction

        if (attackRowIndex in Row.values().indices) {
            if (attackLeftColumnIndex in Column.values().indices) {
                val attackLeft = Position(Column.values()[attackLeftColumnIndex], Row.values()[attackRowIndex])
                val leftTarget = board.getFigureAt(attackLeft)
                if (leftTarget != null && leftTarget.color() != this.color()) {
                    moves.add(attackLeft)
                }

            }

            if (attackRightColumnIndex in Column.values().indices) {
                val attackRight = Position(Column.values()[attackRightColumnIndex], Row.values()[attackRowIndex])
                val rightTarget = board.getFigureAt(attackRight)
                if (rightTarget != null && rightTarget.color() != this.color()) {
                    moves.add(attackRight)
                }
            }
        }

        return moves
    }
}
