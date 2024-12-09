package com.bid90.jchess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * A utility class containing helper methods for chess-related calculations and operations.
 */
public class ChessUtil {

    /**
     * Calculates the file (column) of a square on a chessboard.
     * <p>
     * The file is determined by taking the modulus of the index with 8.
     * </p>
     *
     * @param i The index of the square (0 to 63).
     * @return The file (column number) of the square (0 to 7), where 0 corresponds to 'a'.
     */
    public static int file(int i) {
        return i % 8;
    }

    /**
     * Calculates the rank (row) of a square on a chessboard.
     * <p>
     * The rank is determined by dividing the index by 8 (integer division).
     * </p>
     *
     * @param i The index of the square (0 to 63).
     * @return The rank (row number) of the square (0 to 7), where 0 corresponds to the first rank.
     */
    public static int rank(int i) {
        return i / 8;
    }

    /**
     * Calculates the index of a square based on a starting square index and offsets.
     *
     * @param indexSquare The starting square index (0 to 63).
     * @param rowOffset   The row offset to apply.
     * @param columnOffset The column offset to apply.
     * @return The new square index after applying the offsets, or -1 if the new position is outside the board.
     */
    public static int getSquareByOffset(int indexSquare, int rowOffset, int columnOffset) {
        int currentRow = rank(indexSquare);
        int currentColumn = file(indexSquare);
        int newRow = currentRow + rowOffset;
        int newColumn = currentColumn + columnOffset;

        if (newRow < 0 || newRow >= 8 || newColumn < 0 || newColumn >= 8) {
            return -1;
        }

        return newRow * 8 + newColumn;
    }

    /**
     * Checks if a move represents a kingside castling.
     *
     * @param fromIndex The starting square index.
     * @param toIndex   The destination square index.
     * @return {@code true} if the move is a kingside castling, {@code false} otherwise.
     */
    static boolean isKingSideCastling(int fromIndex, int toIndex) {
        return (fromIndex == ChessConstants.DEFAULT_WHITE_KING_SQUARE && toIndex == 62) ||
               (fromIndex == ChessConstants.DEFAULT_BLACK_KING_SQUARE && toIndex == 6);
    }

    /**
     * Checks if a move represents a queenside castling.
     *
     * @param fromIndex The starting square index.
     * @param toIndex   The destination square index.
     * @return {@code true} if the move is a queenside castling, {@code false} otherwise.
     */
    static boolean isQueenSideCastling(int fromIndex, int toIndex) {
        return (fromIndex == ChessConstants.DEFAULT_WHITE_KING_SQUARE && toIndex == 58) ||
               (fromIndex == ChessConstants.DEFAULT_BLACK_KING_SQUARE && toIndex == 2);
    }

    /**
     * Converts a square index to algebraic chess notation.
     *
     * @param i The square index (0 to 63).
     * @return The square in algebraic notation (e.g., "e4").
     */
    public static String toAlgebraic(int i) {
        char fileLetter = (char) ('a' + file(i));
        char rankNumber = (char) ('8' - rank(i));
        return "" + fileLetter + rankNumber;
    }

    /**
     * Determines if a square is a light square.
     *
     * @param indexSquare The square index (0 to 63).
     * @return {@code true} if the square is light, {@code false} otherwise.
     */
    public static boolean isLightSquare(int indexSquare) {
        return (rank(indexSquare) + file(indexSquare)) % 2 == 0;
    }

    /**
     * Determines if a square is a dark square.
     *
     * @param indexSquare The square index (0 to 63).
     * @return {@code true} if the square is dark, {@code false} otherwise.
     */
    public static boolean isDarkSquare(int indexSquare) {
        return (rank(indexSquare) + file(indexSquare)) % 2 != 0;
    }

    /**
     * Generates a hash representation of a chessboard.
     *
     * @param board A list of strings representing the chessboard.
     * @return The MD5 hash of the concatenated board representation, or {@code null} if hashing fails.
     */
    public static String boardToHash(List<String> board) {
        var boardBytes = String.join("", board).getBytes();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            for (var b : digest.digest(boardBytes)) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
