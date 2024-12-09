
# jChess

`jChess` is a simple chess game implementation in Java. This game provides functionality to manage a chess game, including handling board setup, game state, moves, and game history. The game uses standard algebraic notation (SAN) for moves and supports piece promotion during pawn movement.

## Features
- Full chess game implementation
- Supports standard algebraic notation (SAN) for moves
- Handles piece promotion (default is Queen, but can be customized)
- Tracks game history
- Validates moves, including check, checkmate, stalemate, and draw conditions
- Supports undo functionality for moves
- Supports castling and en passant

## Methods

### `public Chess()`
Initializes a new chess game with the default chess position. It clears the board and loads the default chess setup.

### `public Chess copy()`
Creates a deep copy of the current chess game state, including the board, turn, castle rights, and history.

### `public List<Piece> getBoard()`
Gets the current chessboard, represented as a list of Piece objects.

### `public PieceColor getTurn()`
Gets the color of the player whose turn it is.

### `public int getNpSquare()`
Gets the square where en passant capture can occur, or -1 if none.

### `public int getHalfMove()`
Gets the number of half moves since the last pawn move or capture.

### `public int getFullMove()`
Gets the full move number in the game.

### `public CastleRight getCastleRights()`
Gets the current castling rights.

### `public int getWhiteKingPosition()`
Gets the position of the white king on the board.

### `public int getBlackKingPosition()`
Gets the position of the black king on the board.

### `public LinkedList<History> getGameHistory()`
Gets the history of the game, including all moves made.

### `public LinkedList<String> getSan()`
Gets the list of moves in Standard Algebraic Notation (SAN).

### `public HashMap<String, Integer> getPositionHistoryCount()`
Gets the count of positions visited during the game, used for detecting threefold repetition.

### `public void reset()`
Resets the game to its default position.

### `public String getFENAfterUndo(int steps)`
Gets the FEN (Forsyth-Edwards Notation) string after undoing a specified number of moves.

### `public String toFen()`
Converts the current game state to a FEN (Forsyth-Edwards Notation) string.

### `public void load(String fen)`
Loads a chess game from a given FEN string, updating the board, turn, castling rights, and other game state variables.

### `public MoveResult move(String stringMove)`
Handles the movement of a piece on the board based on the provided move string in standard algebraic notation (e.g., "e2e4"). It validates the move, applies it, and checks for game-ending conditions such as check, checkmate, stalemate, and draw.

- **Promotion**: If a move involves a pawn reaching the last rank (8th for white, 1st for black), the piece is promoted. By default, the promotion is a queen (denoted by `=Q`), unless specified otherwise (e.g., `=R` for rook, `=N` for knight, or `=B` for bishop).
