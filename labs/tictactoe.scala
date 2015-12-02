class Board(val rows : List[List[Char]]) {
  def isWinner(piece : Char) : Boolean = {
    rows.count( r => win(r, piece) ) > 0 ||
      (0 until 3).count( i => win(column(i), piece) ) > 0 ||
      win(diag(0, 0, 1, 1), piece) ||
      win(diag(2, 0, -1, 1), piece)
  }

  def column(index : Int) : List[Char] = {
    rows.map( r => r(index) )
  }

  def diag(x : Int, y : Int, dx : Int, dy : Int) : List[Char] = {
    (0 until 3).map( i => rows(y + i*dy)(x + i*dx) ).toList
  }

  def win(seq : List[Char], piece : Char) : Boolean = {
    seq.count( c => c == piece ) == 3
  }

  def subst(row : List[Char], rowIndex : Int, x : Int, y : Int, piece : Char) : List[Char] = {
    (0 until 3).map( i => 
      if (i == x && rowIndex == y)
        piece
      else
        row(i)
    ).toList
  }

  def move(x : Int, y : Int, piece : Char) : Board = {
    val updatedRows = (0 until 3).map( i => subst(rows(i), i, x, y, piece) ).toList
    return new Board(updatedRows)
  }
}

def printRow( r : List[Char] ) = {
  r.foreach( piece => print(piece) )
  println("")
}

def printBoard(b : Board) = {
  b.rows.foreach( printRow )
}

var board = new Board(List(List('.', '.', '.'),
                           List('.', '.', '.'),
                           List('.', '.', '.')))

printBoard(board)

print("Enter row and column: ");
val row = readLine.toInt
val col = readLine.toInt

board = board.move(col, row, 'X')
printBoard(board)
