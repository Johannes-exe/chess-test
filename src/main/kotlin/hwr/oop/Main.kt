package hwr.oop

fun main() {
    val game = Game()
    println("Welcome to HWR OOP Chess")
    while (!game.isGameOver()) {
        printBoard(game.board)
        val player = if (game.currentPlayerIsWhite) "White" else "Black"
        print("$player move (e2e4 or 'quit'): ")
        val input = readLine()?.trim() ?: return
        if (input.lowercase() == "quit") return
        val move = parseMove(input)
        if (move == null) {
            println("Invalid input. Use format like e2e4")
            continue
        }
        try {
            game.makeMove(move.first, move.second)
        } catch (e: Exception) {
            println(e.message)
        }
    }
    println("Game over")
}

private fun parseMove(str: String): Pair<Position, Position>? {
    if (str.length < 4) return null
    val from = parsePosition(str.substring(0,2)) ?: return null
    val to = parsePosition(str.substring(2,4)) ?: return null
    return from to to
}

private fun parsePosition(str: String): Position? {
    val colChar = str[0].lowercaseChar()
    val rowChar = str[1]
    val row = rowChar.digitToIntOrNull() ?: return null
    return Position.from(colChar, row)
}

private fun printBoard(board: ChessBoard) {
    val rows = Row.values().reversed()
    val cols = Column.values()
    for (row in rows) {
        print("${row.row} ")
        for (col in cols) {
            val fig = board.getFigureAt(Position(col, row))
            val symbol = fig?.symbol() ?: "."
            print("$symbol ")
        }
        println()
    }
    print("  ")
    for (col in cols) print("${col.column} ")
    println()
}
