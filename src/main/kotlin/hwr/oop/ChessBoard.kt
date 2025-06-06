package hwr.oop
import hwr.oop.figures.*
import hwr.oop.FEN

/**
 * Represents a chessboard containing positions and their corresponding chess pieces.
 *
 * @property board A mutable map associating positions with chess figures.
 */
class ChessBoard(private val board: MutableMap<Position, Figure>) {

    companion object {
        /**
         * Creates an empty chessboard with no pieces.
         *
         * @return An empty ChessBoard instance.
         */
        fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

        /**
         * Creates a chessboard with the standard initial chess setup.
         *
         * @return A ChessBoard instance with all pieces in their starting positions.
         */
        fun fullBoard(): ChessBoard {
            val board = mutableMapOf<Position, Figure>()
            for (i in 'a'..'h') {
                board[Position.from(i, 2)!!] = Pawn(Color.WHITE)
            }
            for (i in 'a'..'h') {
                board[Position.from(i, 7)!!] = Pawn(Color.BLACK)
            }
            board[Position.from('a', 1)!!] = Rook(Color.WHITE)
            board[Position.from('a', 8)!!] = Rook(Color.BLACK)
            board[Position.from('h', 1)!!] = Rook(Color.WHITE)
            board[Position.from('h', 8)!!] = Rook(Color.BLACK)

            board[Position.from('b', 1)!!] = Knight(Color.WHITE)
            board[Position.from('b', 8)!!] = Knight(Color.BLACK)
            board[Position.from('g', 1)!!] = Knight(Color.WHITE)
            board[Position.from('g', 8)!!] = Knight(Color.BLACK)

            board[Position.from('c', 1)!!] = Bishop(Color.WHITE)
            board[Position.from('c', 8)!!] = Bishop(Color.BLACK)
            board[Position.from('f', 1)!!] = Bishop(Color.WHITE)
            board[Position.from('f', 8)!!] = Bishop(Color.BLACK)

            board[Position.from('d', 1)!!] = Queen(Color.WHITE)
            board[Position.from('d', 8)!!] = Queen(Color.BLACK)

            board[Position.from('e', 1)!!] = King(Color.WHITE)
            board[Position.from('e', 8)!!] = King(Color.BLACK)

            return ChessBoard(board)
        }

        /**
         * Creates a chessboard from a FEN (Forsyth-Edwards Notation) string.
         *
         * @param fenString The FEN string representing a chess position.
         * @return A ChessBoard instance based on the FEN string.
         */
        fun fromFEN(fenString: String): ChessBoard {
            val fen = FEN(fenString)
            return ChessBoard(mutableMapOf())
        }
    }

    /**
     * Retrieves the figure at the specified position.
     *
     * @param position The position to check.
     * @return The figure at the position, or null if empty.
     */
    fun getFigureAt(position: Position): Figure? = board[position]

    /**
     * Moves a figure from one position to another if the move is valid.
     *
     * @param from The starting position.
     * @param to The target position.
     * @param promoteTo Optional promotion function for pawn promotion.
     * @return True if the move was successful, false otherwise.
     */
    fun move(from: Position, to: Position, promoteTo: ((Boolean) -> Figure)? = null): Boolean {
        val figure = board[from] ?: return false
        if (figure.availableTargets(from, this).contains(to)) {
            board.remove(from)
            board[to] = figure
            return true
        } else {
            error("Ungültiger Zug von ${figure.symbol()} von $from nach $to")
        }
    }

    /**
     * Promotes a pawn at the given position to another figure type.
     *
     * @param position The position of the pawn to promote.
     * @param promoteTo The type to promote the pawn to.
     * @return True if promotion was successful, false otherwise.
     */
    fun promoteFigure(position: Position, promoteTo: FigureType?): Boolean {
        val figure = board[position] ?: return false
        if (figure is Pawn) {
            board[position] = when (promoteTo) {
                FigureType.Pawn -> Pawn(figure.color())
                FigureType.King -> King(figure.color())
                FigureType.Rook -> Rook(figure.color())
                FigureType.Knight -> Knight(figure.color())
                FigureType.Bishop -> Bishop(figure.color())
                FigureType.Queen -> Queen(figure.color())
                else -> Queen(figure.color())
            }
            return true
        }
        return false
    }

    /**
     * Places a figure at the specified position on the board.
     *
     * @param position The position to place the figure.
     * @param figure The figure to place.
     */
    fun placePieces(position: Position, figure: Figure) {
        board[position] = figure
    }

    /**
     * Retrieves all figures of the specified color currently on the board.
     *
     * @param whiteTurn True to get white pieces, false for black.
     * @return A list of all figures of the specified color.
     */
    fun getAllFigures(whiteTurn: Boolean): List<Figure> {
        val allFigures = mutableListOf<Figure>()
        for (entry in board.entries) {
            if (entry.value.color() == Color.WHITE == whiteTurn) {
                allFigures.add(entry.value)
            }
        }
        return allFigures
    }

    /**
     * Finds the position of the king of the specified color.
     *
     * @param whiteTurn True to find the white king, false for black.
     * @return The position of the king, or null if not found.
     */
    fun findKing(whiteTurn: Boolean): Position? {
        for (entry in board.entries) {
            if (entry.value is King && (entry.value as King).color() == Color.WHITE == whiteTurn) {
                return entry.key
            }
        }
        return null
    }

    /**
     * Retrieves all occupied positions on the board.
     *
     * @return A list of all positions containing a figure.
     */
    fun getAllPositions(): List<Position> {
        val allPositions = mutableListOf<Position>()
        for (entry in board.entries) {
            allPositions.add(entry.key)
        }
        return allPositions
    }

}