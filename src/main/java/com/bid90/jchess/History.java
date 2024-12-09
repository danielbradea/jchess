package com.bid90.jchess;

/**
 * Represents the state of a chess game at a specific point in its history.
 * <p>
 * The {@code History} class is used to encapsulate all relevant information about the game state
 * after a specific move, allowing for tracking and analysis of the game's progression. It includes
 * details about the move made, the turn, the board's state, and other metadata such as castling rights
 * and check/draw statuses.
 * </p>
 * 
 * <p>
 * This class is immutable, ensuring that historical game states remain unchanged.
 * </p>
 */
public class History {

    private final Move move;
    private final PieceColor turn;
    private final int npSquare;
    private final int halfMoves;
    private final int fullMove;
    private final CastleRight castlingRights;
    private final int whiteKingPosition;
    private final int blackKingPosition;
    private final boolean isCheck;
    private final boolean isCheckmate;
    private final boolean isStalemate;
    private final boolean isDraw;
    private final String boardHash;

    /**
     * Constructs a new {@code History} object with the given game state details.
     *
     * @param move The move that was made.
     * @param turn The color of the player whose turn it is.
     * @param npSquare The square number of the en passant target, or -1 if no en passant is possible.
     * @param halfMoves The number of half moves since the last pawn move or capture.
     * @param fullMove The full move number in the game.
     * @param castlingRights The castling rights available for both sides.
     * @param whiteKingPosition The position of the white king on the board.
     * @param blackKingPosition The position of the black king on the board.
     * @param isCheck {@code true} if the current state is a check.
     * @param isCheckmate {@code true} if the current state is a checkmate.
     * @param isStalemate {@code true} if the current state is a stalemate.
     * @param isDraw {@code true} if the game is a draw.
     * @param boardHash A unique hash representing the current board position.
     */
    public History(Move move,
                   PieceColor turn,
                   int npSquare,
                   int halfMoves,
                   int fullMove,
                   CastleRight castlingRights,
                   int whiteKingPosition,
                   int blackKingPosition,
                   boolean isCheck,
                   boolean isCheckmate,
                   boolean isStalemate,
                   boolean isDraw,
                   String boardHash) {
        this.move = move;
        this.turn = turn;
        this.npSquare = npSquare;
        this.halfMoves = halfMoves;
        this.fullMove = fullMove;
        this.castlingRights = castlingRights;
        this.whiteKingPosition = whiteKingPosition;
        this.blackKingPosition = blackKingPosition;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
        this.isStalemate = isStalemate;
        this.isDraw = isDraw;
        this.boardHash = boardHash;
    }

    /** @return The move that was made. */
    public Move getMove() {
        return move;
    }

    /** @return The color of the player whose turn it is. */
    public PieceColor getTurn() {
        return turn;
    }

    /** @return The square number of the en passant target, or -1 if no en passant is possible. */
    public int getNpSquare() {
        return npSquare;
    }

    /** @return The number of half moves since the last pawn move or capture. */
    public int getHalfMoves() {
        return halfMoves;
    }

    /** @return The full move number in the game. */
    public int getFullMove() {
        return fullMove;
    }

    /** @return The castling rights available for both sides. */
    public CastleRight getCastlingRights() {
        return castlingRights;
    }

    /** @return The position of the white king on the board. */
    public int getWhiteKingPosition() {
        return whiteKingPosition;
    }

    /** @return The position of the black king on the board. */
    public int getBlackKingPosition() {
        return blackKingPosition;
    }

    /** @return {@code true} if the current state is a check. */
    public boolean isCheck() {
        return isCheck;
    }

    /** @return {@code true} if the current state is a checkmate. */
    public boolean isCheckmate() {
        return isCheckmate;
    }

    /** @return {@code true} if the current state is a stalemate. */
    public boolean isStalemate() {
        return isStalemate;
    }

    /** @return {@code true} if the game is a draw. */
    public boolean isDraw() {
        return isDraw;
    }

    /** @return A unique hash representing the current board position. */
    public String getBoardHash() {
        return boardHash;
    }

    /**
     * Provides a string representation of this {@code History} object.
     *
     * @return A string representation of the game state.
     */
    @Override
    public String toString() {
        return "History{" +
                "move=" + move +
                ", turn=" + turn +
                ", npSquare=" + npSquare +
                ", halfMoves=" + halfMoves +
                ", fullMove=" + fullMove +
                ", castlingRights=" + castlingRights +
                ", whiteKingPosition=" + whiteKingPosition +
                ", blackKingPosition=" + blackKingPosition +
                ", isCheck=" + isCheck +
                ", isCheckmate=" + isCheckmate +
                ", isStalemate=" + isStalemate +
                ", isDraw=" + isDraw +
                ", boardHash='" + boardHash + '\'' +
                '}';
    }
}
