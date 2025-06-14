package hwr.oop

import hwr.oop.figures.King
import hwr.oop.figures.Rook


data class Move(val from: Position, val to: Position, val board: ChessBoard) {

    /**
     * Creates a move from one position to another on the given chessboard.
     *
     * @param from The starting position of the move.
     * @param to The target position of the move.
     * @param board The chessboard on which the move is being made.
     */
    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }

    /**
     * Checks if the move is valid according to the rules of chess.
     *
     * @return True if the move is valid, false otherwise.
     */
    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableTargets(from, board).contains(to)
    }

    /**
     * Checks if the move is a capture.
     *
     * A move is considered a capture if there is a figure at the target position,
     * and it is of a different color than the moving figure.
     *
     * @return True if the move is a capture, false otherwise.
     */
    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.color() != movingFigure.color()
    }

    /**
     * Executes the move on the chessboard.
     *
     * @return True if the move was executed successfully, false otherwise.
     * @throws IllegalArgumentException if the move is invalid.
     */

    fun castleKingSide(game: Game): Boolean {
        val isWhite = from.row == Row.ONE
        val kingStart = if (isWhite) Position(Column.E, Row.ONE) else Position(Column.E, Row.EIGHT)
        val rookStart = if (isWhite) Position(Column.H, Row.ONE) else Position(Column.H, Row.EIGHT)
        val kingDest = if (isWhite) Position(Column.G, Row.ONE) else Position(Column.G, Row.EIGHT)
        val rookDest = if (isWhite) Position(Column.F, Row.ONE) else Position(Column.F, Row.EIGHT)

        val king = game.board.getFigureAt(kingStart)
        val rook = game.board.getFigureAt(rookStart)

        if (king !is King || rook !is Rook) return false

        if (game.moves.any { it.from == kingStart } || game.moves.any { it.from == rookStart }) {
            return false
        }

        if (game.board.getFigureAt(rookDest) != null || game.board.getFigureAt(kingDest) != null) return false

        if (isWhite) {
            if (game.whiteCheck() || !game.isSpaceFree(game, Position(Column.F, Row.ONE), true) ||
                !game.isSpaceFree(game, kingDest, true)) {
                return false
            }
        } else {
            if (game.blackCheck() || !game.isSpaceFree(game, Position(Column.F, Row.EIGHT), false) ||
                !game.isSpaceFree(game, kingDest, false)) {
                return false
            }
        }

        game.board.removePiece(kingStart)
        game.board.removePiece(rookStart)
        game.board.placePieces(kingDest, king)
        game.board.placePieces(rookDest, rook)
        return true
    }
   }

