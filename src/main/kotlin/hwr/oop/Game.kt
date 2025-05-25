package hwr.oop

import hwr.oop.figures.King

class Game {
    var board: ChessBoard = ChessBoard.fullBoard()
    var currentPlayerIsWhite: Boolean = true
    val moves = mutableListOf<Triple<Figure, Position, Position>>()

    fun startGame() {
        board.displayBoard()
        println("Das Spiel beginnt!")
    }

    fun makeMove(from: Position, to: Position): Boolean {
        val figure = board.getFigureAt(from)
        if (figure == null || figure.isWhite != currentPlayerIsWhite) {
            println("Ungültiger Zug: Es ist der Zug des ${if (currentPlayerIsWhite) "weißen" else "schwarzen"} Spielers.")
            return false
        }

        if (figure.availableMoves(from, board).contains(to)) {
            board.move(from, to)
            currentPlayerIsWhite = !currentPlayerIsWhite
            println("Zug erfolgreich!")
            moves.add(Triple(figure, from, to))
            return true
        } else {
              println("Ungültiger Zug!")
            return false
        }
    }

    fun kingPositions(): Pair <Position?, Position?>  {    //paar gibt die Positionen der Könige zurück
        var whiteKingPosition : Position? = null
        var blackKingPosition : Position? = null
        var col = 'a'..'h'
        var row = 1..8
        outer@for (Column in col) {                        //durchläuft alle Spalten und Zeilen
            for (Row in row) {
                val position = Position(Column, Row)
                val figur = board.getFigureAt(position)

                if (figur is King && figur.symbol() == "k") {      // wenn die Figur ein Weißer König ist dann true
                    whiteKingPosition = position
                }
                if (figur is King && figur.symbol() == "K") {       // wenn die Figur ein schwarzer König ist dann true
                    blackKingPosition = position
                }
                if(whiteKingPosition != null && blackKingPosition != null) {    // wenn beide gefunden wurden bricht es ab
                    break@outer
                }
            }
        }
        return Pair(whiteKingPosition, blackKingPosition)
    }
    fun getAllMoves(board: ChessBoard): Pair<List<Position>, List<Position>> {  //gibt ein paar aus Listen wieder
        val whiteMoves = mutableListOf<Position>() //enthält alle weißen möglichen Züge
        val blackMoves = mutableListOf<Position>() //enthält alle schwarzen möglichen Züge
        val col = 'a'..'h'
        val row = 1..8

        for (column in col) {
            for (Row in row) {
                val from = Position(column, Row)
                val figure = board.getFigureAt(from)

                if (figure != King(true) && figure != null && figure.symbol()[0].isLowerCase()) {
                    val moves = figure.availableMoves(from, board)
                    whiteMoves.addAll(moves)    //fügt alle Züge einer weißen Figur zur Liste zu
                }
                if (figure != King(false) && figure != null && figure.symbol()[0].isUpperCase()) {
                    val moves = figure.availableMoves(from, board)
                    blackMoves.addAll(moves)    //fügt alle Züge einer schwarzen Figur zur Liste zu
                }
            }
        }
        return Pair(whiteMoves, blackMoves)
    }
    fun whiteCheck(): Boolean {
        val (_, blackMoves) = getAllMoves(board)
        val (whiteKing, _) = kingPositions()
        if (whiteKing == null) return false
        return blackMoves.contains(whiteKing)
    }
    fun blackCheck(): Boolean {
        val (whiteMoves, _) = getAllMoves(board)
        val (_, blackKing) = kingPositions()
        if (blackKing == null) return false
        return whiteMoves.contains(blackKing)
    }



    fun isGameOver(): Boolean {
        fun isKingInCheck(whiteTurn: Boolean): Boolean {
            val kingPosition = board.findKing(whiteTurn) ?: return false

            for (position in board.getAllPositions() as List<Position>) {
                val figure = board.getFigureAt(position)
                if (figure != null && figure.isWhite != whiteTurn) {
                    if (figure.availableMoves(position, board).contains(kingPosition)) {
                        return true
                    }
                }
            }
            return false
        }
        fun kingPositions(): Pair <Position?, Position?>  {    //paar gibt die Positionen der Könige zurück
            var whiteKingPosition : Position? = null
            var blackKingPosition : Position? = null
            var col = 'a'..'h'
            var row = 1..8
            outer@for (Column in col) {                        //durchläuft alle Spalten und Zeilen
                for (Row in row) {
                    val position = Position(Column, Row)
                    val figur = board.getFigureAt(position)

                    if (figur is King && figur.symbol() == "k") {      // wenn die Figur ein Weißer König ist dann true
                        whiteKingPosition = position
                    }
                    if (figur is King && figur.symbol() == "K") {       // wenn die Figur ein schwarzer König ist dann true
                        blackKingPosition = position
                    }
                    if(whiteKingPosition != null && blackKingPosition != null) {    // wenn beide gefunden wurden bricht es ab
                        break@outer
                    }
                }
            }
            return Pair(whiteKingPosition, blackKingPosition)
        }
        fun getAllMoves(board: ChessBoard): Pair<List<Position>, List<Position>> {  //gibt ein paar aus Listen wieder
            val whiteMoves = mutableListOf<Position>() //enthält alle weißen möglichen Züge
            val blackMoves = mutableListOf<Position>() //enthält alle schwarzen möglichen Züge
            val col = 'a'..'h'
            val row = 1..8

            for (column in col) {
                for (Row in row) {
                    val from = Position(column, Row)
                    val figure = board.getFigureAt(from)

                    if (figure != King(true) && figure != null && figure.symbol()[0].isLowerCase()) {
                        val moves = figure.availableMoves(from, board)
                        whiteMoves.addAll(moves)    //fügt alle Züge einer weißen Figur zur Liste zu
                    }
                    if (figure != King(false) && figure != null && figure.symbol()[0].isUpperCase()) {
                        val moves = figure.availableMoves(from, board)
                        blackMoves.addAll(moves)    //fügt alle Züge einer schwarzen Figur zur Liste zu
                    }
                }
            }
            return Pair(whiteMoves, blackMoves)
        }
        fun whiteCheck(): Boolean {
            val (whiteMoves, blackMoves) = getAllMoves(board)
            val (whiteKing, blackKing) = kingPositions()
            for (white in whiteMoves) {
                if(whiteKing == white)  //für alle Elemente(Positionen) die möglich sind wird geprüft == King sind
                    return true
            }
            return false
        }
        fun blackCheck(): Boolean {
            val (whiteMoves, blackMoves) =getAllMoves(board)
            val (whiteKing, blackKing) = kingPositions()
            for (black in blackMoves) {
                if(blackKing == black)  //für alle Elemente(Positionen) die möglich sind wird geprüft == King sind
                    return true
            }
            return false
        }

        return TODO("Provide the return value")
    }
}