package com.bid90.jchess;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class containing constants and default values for a chess game.
 * This class includes board size, default piece positions, special square indices,
 * and other game-related constants. It also provides a mapping between chess
 * notation (e.g., "e4") and their corresponding board indices.
 */
public class ChessConstants {

    /**
     * The default chess position in FEN (Forsyth-Edwards Notation) format.
     * Represents the standard starting position in a game of chess.
     */
    public static final String DEFAULT_CHESS_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /**
     * The total number of squares on a standard chessboard (8x8).
     */
    public static final int BOARD_SIZE = 64;

    /**
     * The value representing an empty square on the chessboard.
     */
    public static final int EMPTY_SQUARE = -1;

    /**
     * The default square index of the white king in the starting position.
     */
    public static final int DEFAULT_WHITE_KING_SQUARE = 60;

    /**
     * The default square index of the black king in the starting position.
     */
    public static final int DEFAULT_BLACK_KING_SQUARE = 4;

    // Default square indices for white rooks in the starting position.
    public static final int DEFAULT_WHITE_ROOK_KS_SQUARE = 63;
    public static final int DEFAULT_WHITE_ROOK_QS_SQUARE = 56;

    // Default square indices for black rooks in the starting position.
    public static final int DEFAULT_BLACK_ROOK_KS_SQUARE = 7;
    public static final int DEFAULT_BLACK_ROOK_QS_SQUARE = 0;

    // Squares for white rook en passant scenarios.
    public static final int WHITE_ROOK_KS_EP_SQUARE = 61;
    public static final int WHITE_ROOK_QS_EP_SQUARE = 59;

    // Squares for black rook en passant scenarios.
    public static final int BLACK_ROOK_KS_EP_SQUARE = 5;
    public static final int BLACK_ROOK_QS_EP_SQUARE = 3;

    /**
     * The rank on which white pawns can be promoted (rank 1 in chess notation).
     */
    public static final int WHITE_RANK_PROMOTION = 0;

    /**
     * The rank on which black pawns can be promoted (rank 8 in chess notation).
     */
    public static final int BLACK_RANK_PROMOTION = 7;

    /**
     * The rank where white pawns can perform a double move (rank 7 in chess notation).
     */
    public static final int WHITE_DOUBLE_MOVE_RANK = 6;

    /**
     * The rank where black pawns can perform a double move (rank 2 in chess notation).
     */
    public static final int BLACK_DOUBLE_MOVE_RANK = 1;

    /**
     * A map linking chess notation (e.g., "e4") to their corresponding board indices.
     * The indices range from 0 to 63, where 0 represents square "a8" and 63 represents square "h1".
     */
    public static final Map<String, Integer> SQUARES;

    static {
        Map<String, Integer> squares = new HashMap<>();
        int index = 0;

        // Initializes the map with chess notation keys and board index values.
        for (char rank = '8'; rank >= '1'; rank--) {
            for (char file = 'a'; file <= 'h'; file++) {
                squares.put(String.valueOf(file) + rank, index++);
            }
        }

        SQUARES = Collections.unmodifiableMap(squares);
    }

    // Private constructor to prevent instantiation.
    private ChessConstants() {
        // This class is not meant to be instantiated.
    }
}
