package com.bid90.jchess;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for validating Forsyth-Edwards Notation (FEN) strings used in chess games.
 * This class contains a static method to validate the FEN string by checking various
 * components such as board layout, player turn, castling rights, and other chess-specific rules.
 */
public class FENValidator {

    /**
     * Validates the given FEN string to ensure it is well-formed and represents a valid chess position.
     * It checks for the correct number of sections, the proper arrangement of pieces on the board,
     * the presence of kings, valid castling rights, en passant, and other aspects as per the FEN standard.
     *
     * @param fen The Forsyth-Edwards Notation (FEN) string representing the chess position.
     * @throws ChessException if the FEN string is invalid, either due to incorrect formatting
     *                        or violations of chess rules.
     */
    public static void validateFEN(String fen) {
        var tokens = fen.split(" ");

        if (tokens.length != 6) {
            throw new ChessException("Invalid FEN: Must contain exactly 6 sections.");
        }
        var board = tokens[0];
        var playerTurn = tokens[1];
        var castlingAvailability = tokens[2];
        var enPassant = tokens[3];
        var halfMoveClock = tokens[4];
        var fullMoveNumber = tokens[5];

        var rows = board.split("/");
        if (rows.length != 8) {
            throw new ChessException("Invalid FEN: Board layout must contain exactly 8 rows separated by '/' characters.");
        }
        if (board.chars().filter(ch -> ch == 'K').count() != 1) {
            throw new ChessException("Invalid FEN: There must be exactly one White king ('K') on the board.");
        }
        if (board.chars().filter(ch -> ch == 'k').count() != 1) {
            throw new ChessException("Invalid FEN: There must be exactly one Black king ('k') on the board.");
        }

        List<Integer> whiteRooks = new ArrayList<>();
        List<Integer> blackRooks = new ArrayList<>();
        int blackKingPos = -1;
        int whiteKingPos = -1;
        int index = 0;
        for (var row : rows) {
            var rowSquareCount = 0;
            for (var p : row.split("")) {
                if (p.matches("[1-8]")) {
                    rowSquareCount += Integer.parseInt(p);
                    index += Integer.parseInt(p);
                } else if (p.matches("[prnbqkPRNBQK]")) {

                    switch (p) {
                        case "r" -> blackRooks.add(index);
                        case "R" -> whiteRooks.add(index);
                        case "k" -> blackKingPos = index;
                        case "K" -> whiteKingPos = index;
                    }
                    rowSquareCount++;
                    index++;
                } else {
                    throw new ChessException("Invalid FEN: Invalid character '" + p + "' found in the board layout.");
                }

            }
            if (rowSquareCount != 8) {
                throw new ChessException("Invalid FEN: Row '" + row + "' does not have exactly 8 squares.");
            }
        }

        if (whiteKingPos != ChessConstants.DEFAULT_WHITE_KING_SQUARE &&
                (castlingAvailability.contains("Q") || castlingAvailability.contains("K"))) {
            throw new ChessException("Invalid FEN: White king ('K') has moved, but castling rights are still available for king-side or queen-side.");
        }

        if (blackKingPos != ChessConstants.DEFAULT_BLACK_KING_SQUARE &&
                (castlingAvailability.contains("q") || castlingAvailability.contains("k"))) {
            throw new ChessException("Invalid FEN: Black king ('k') has moved, but castling rights are still available for king-side or queen-side.");
        }

        if (castlingAvailability.contains("K") && whiteKingPos == ChessConstants.DEFAULT_WHITE_KING_SQUARE && !whiteRooks.contains(ChessConstants.DEFAULT_WHITE_ROOK_KS_SQUARE)) {
            throw new ChessException("Invalid FEN: White king-side castling ('K') is not possible because the White king is in its default position but the White rook for king-side castling ('R') is missing.");
        }

        if (castlingAvailability.contains("Q") && whiteKingPos == ChessConstants.DEFAULT_WHITE_KING_SQUARE && !whiteRooks.contains(ChessConstants.DEFAULT_WHITE_ROOK_QS_SQUARE)) {
            throw new ChessException("Invalid FEN: White queen-side castling ('Q') is not possible because the White king is in its default position but the White rook for queen-side castling ('R') is missing.");
        }

        if (castlingAvailability.contains("k") && blackKingPos == ChessConstants.DEFAULT_BLACK_KING_SQUARE && !blackRooks.contains(ChessConstants.DEFAULT_BLACK_ROOK_KS_SQUARE)) {
            throw new ChessException("Invalid FEN: Black king-side castling ('k') is not possible because the Black king is in its default position but the Black rook for king-side castling ('r') is missing.");
        }

        if (castlingAvailability.contains("q") && blackKingPos == ChessConstants.DEFAULT_BLACK_KING_SQUARE && !blackRooks.contains(ChessConstants.DEFAULT_BLACK_ROOK_QS_SQUARE)) {
            throw new ChessException("Invalid FEN: Black queen-side castling ('q') is not possible because the Black king is in its default position but the Black rook for queen-side castling ('r') is missing.");
        }


        if (!"w".contains(playerTurn) && !"b".contains(playerTurn)) {
            throw new ChessException("Invalid FEN: Player turn must be 'w' for White or 'b' for Black, but found: '" + playerTurn + "'.");
        }

        if (!castlingAvailability.matches("^(K?Q?k?q?|-)$")) {
            throw new ChessException("Invalid FEN: Castling rights must be 'KQkq', any combination of 'K', 'Q', 'k', 'q', or '-' for no castling rights. Found: '" + castlingAvailability + "'.");
        }
        if (castlingAvailability.contains("K") && !board.contains("R")) {
            throw new ChessException("Invalid FEN: White king-side castling ('K') is not possible because the White rook ('R') is missing.");
        }

        if (castlingAvailability.contains("Q") && !board.contains("R")) {
            throw new ChessException("Invalid FEN: White queen-side castling ('Q') is not possible because the White rook ('R') is missing.");
        }

        if (castlingAvailability.contains("k") && !board.contains("r")) {
            throw new ChessException("Invalid FEN: Black king-side castling ('k') is not possible because the Black rook ('r') is missing.");
        }

        if (castlingAvailability.contains("q") && !board.contains("r")) {
            throw new ChessException("Invalid FEN: Black queen-side castling ('q') is not possible because the Black rook ('r') is missing.");
        }

        if (!board.contains("K")) {
            throw new ChessException("Invalid FEN: White king ('K') is missing from the board.");
        }

        if (!board.contains("k")) {
            throw new ChessException("Invalid FEN: Black king ('k') is missing from the board.");
        }

        if (!enPassant.matches("^(-|[a-h][36])$")) {
            throw new ChessException("Invalid FEN: En passant target square must be '-' (no en passant) or a valid square like 'a3', 'h6', etc. Found: '" + enPassant + "'.");
        }


        if (!halfMoveClock.matches("^[0-9]+$")) {
            throw new ChessException("Invalid FEN: Half-move clock must be a non-negative integer. Found: '" + halfMoveClock + "'.");
        }

        if (!fullMoveNumber.matches("^[1-9][0-9]*$")) {
            throw new ChessException("Invalid FEN: Full-move number must be a positive integer starting from 1. Found: '" + fullMoveNumber + "'.");
        }

    }
}