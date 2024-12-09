package com.bid90.jchess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code PieceMoveCalculator} class provides utility methods for calculating
 * valid moves for chess pieces on a given chessboard. It supports move calculations
 * for pawns, rooks, bishops, knights, queens, kings, and castling moves.
 * Additionally, it includes methods to check whether a square is under attack
 * and retrieve adjacent squares.
 */
public class PieceMoveCalculator {

    /**
     * Calculates possible moves for a specific piece at the given square.
     *
     * @param chess            the current state of the chessboard, represented as a {@code Chess} object.
     * @param pieceSquareIndex the index of the square containing the piece.
     * @return a list of indices representing the valid move destinations for the piece,
     *         or an empty list if the piece has no valid moves or the square is empty.
     */
    public static List<Integer> calculatePieceMoves(Chess chess, int pieceSquareIndex) {
        var activePiece = chess.getBoard().get(pieceSquareIndex);
        if (activePiece == null) return Collections.emptyList();
        return switch (activePiece.getType()) {
            case "p" -> calculatePawnMove(chess, pieceSquareIndex);
            case "r" -> calculateRookMove(chess, pieceSquareIndex);
            case "b" -> calculateBishopMove(chess, pieceSquareIndex);
            case "n" -> calculateKnightMove(chess, pieceSquareIndex);
            case "q" -> calculateQueenMove(chess, pieceSquareIndex);
            case "k" -> calculateKingMove(chess, pieceSquareIndex);
            default -> Collections.emptyList();
        };
    }

    /**
     * Calculates all possible moves for a piece, including special moves like castling.
     *
     * @param chess            the current state of the chessboard, represented as a {@code Chess} object.
     * @param pieceSquareIndex the index of the square containing the piece.
     * @return a list of indices representing all valid move destinations for the piece,
     *         or an empty list if the square is empty.
     */
    public static List<Integer> calculateMoves(Chess chess, int pieceSquareIndex) {

        var activePiece = chess.getBoard().get(pieceSquareIndex);
        if (activePiece == null) return Collections.emptyList();

        List<Integer> moves = PieceMoveCalculator.calculatePieceMoves(chess, pieceSquareIndex);
        if (!activePiece.getType().equals("k")) {
            moves.addAll(calculateKingCastlingMove(chess, pieceSquareIndex));
        }
        return moves;
    }

    /**
     * Calculates valid moves for a pawn from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the pawn.
     * @return a list of indices representing valid moves for the pawn.
     */
    private static List<Integer> calculatePawnMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-1, 0},  // North
                {-2, 0},  // North + 1
                {-1, -1}, // North-West
                {-1, 1},  // North-East
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a rook from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the rook.
     * @return a list of indices representing valid moves for the rook.
     */
    private static List<Integer> calculateRookMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-1, 0}, // North
                {1, 0},  // South
                {0, -1}, // West
                {0, 1}   // East
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a bishop from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the bishop.
     * @return a list of indices representing valid moves for the bishop.
     */
    private static List<Integer> calculateBishopMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-1, -1}, // North-West
                {-1, 1},  // North-East
                {1, -1},  // South-West
                {1, 1}    // South-East
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a knight from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the knight.
     * @return a list of indices representing valid moves for the knight.
     */
    private static List<Integer> calculateKnightMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-2, -1}, {-2, 1},  // 2 squares up, 1 left/right
                {2, -1}, {2, 1},    // 2 squares down, 1 left/right
                {-1, -2}, {-1, 2},  // 1 square up, 2 left/right
                {1, -2}, {1, 2}     // 1 square down, 2 left/right
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a queen from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the queen.
     * @return a list of indices representing valid moves for the queen.
     */
    private static List<Integer> calculateQueenMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-1, 0},  // Move 1 square up (North)
                {1, 0},   // Move 1 square down (South)
                {0, -1},  // Move 1 square left (West)
                {0, 1},   // Move 1 square right (East)
                {-1, -1}, // Move 1 square up-left (North-West)
                {-1, 1},  // Move 1 square up-right (North-East)
                {1, -1},  // Move 1 square down-left (South-West)
                {1, 1}    // Move 1 square down-right (South-East)
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a king from the given square.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the king.
     * @return a list of indices representing valid moves for the king.
     */
    private static List<Integer> calculateKingMove(Chess chess, int pieceSquareIndex) {
        int[][] directions = {
                {-1, 0},  // Move 1 square up (North)
                {1, 0},   // Move 1 square down (South)
                {0, -1},  // Move 1 square left (West)
                {0, 1},   // Move 1 square right (East)
                {-1, -1}, // Move 1 square up-left (North-West)
                {-1, 1},  // Move 1 square up-right (North-East)
                {1, -1},  // Move 1 square down-left (South-West)
                {1, 1}    // Move 1 square down-right (South-East)
        };
        return calculateMove(chess, pieceSquareIndex, directions);
    }

    /**
     * Calculates valid moves for a piece based on given directions.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the piece.
     * @param directions       a 2D array representing movement directions for the piece.
     * @return a list of indices representing valid moves for the piece.
     */
    private static List<Integer> calculateMove(Chess chess, int pieceSquareIndex, int[][] directions) {
        List<Integer> possibleMoves = new ArrayList<>();
        var activePiece = chess.getBoard().get(pieceSquareIndex);

        for (var direction : directions) {
            // Handle King movement
            if (activePiece.getType().equals("k")) {
                var square = ChessUtil.getSquareByOffset(pieceSquareIndex, direction[0], direction[1]);
                if (square == -1) continue;

                // Check if the square is safe for the king
                var piece = chess.getBoard().get(square);
                boolean isSquareSafe = !getAdjacentSquares(square).contains(
                        activePiece.getColor().equals(PieceColor.WHITE) ? chess.getBlackKingPosition() : chess.getWhiteKingPosition()) &&
                        !isSquareUnderAttack(chess, square, activePiece.getColor().opposite());

                if (isSquareSafe && (piece == null || !piece.getColor().equals(activePiece.getColor()) )) {
                    possibleMoves.add(square);
                }
            }
            // Handle Knight movement
            else if (activePiece.getType().equals("n")) {
                var square = ChessUtil.getSquareByOffset(pieceSquareIndex, direction[0], direction[1]);
                if (square == -1) continue;

                var piece = chess.getBoard().get(square);
                if (piece == null || piece.getColor().equals(activePiece.getColor().opposite())) {
                    possibleMoves.add(square);
                }
            }
            // Handle Pawn movement
            else if (activePiece.getType().equals("p")) {
                var changeDir = activePiece.getColor() == PieceColor.WHITE ? 1 : -1;
                var forwardSquare = ChessUtil.getSquareByOffset(
                        pieceSquareIndex, direction[0] * changeDir, direction[1]);

                if (forwardSquare == -1) continue;

                // Validate if the pawn can perform a double move
                boolean isValidDoubleMoveRank =
                        (activePiece.getColor() == PieceColor.WHITE && ChessUtil.rank(pieceSquareIndex) == ChessConstants.WHITE_DOUBLE_MOVE_RANK) ||
                                (activePiece.getColor() == PieceColor.BLACK && ChessUtil.rank(pieceSquareIndex) == ChessConstants.BLACK_DOUBLE_MOVE_RANK);

                if (!isValidDoubleMoveRank) continue;

                var piece = chess.getBoard().get(forwardSquare);

                // Add valid forward moves or capture moves
                if (ChessUtil.file(pieceSquareIndex) == ChessUtil.file(forwardSquare)) {
                    if (piece == null) {
                        possibleMoves.add(forwardSquare); // Add empty forward square
                    }
                } else if (piece != null && piece.getColor() != activePiece.getColor()) {
                    possibleMoves.add(forwardSquare); // Add capture square
                }
            }
            // Handle Sliding pieces (Bishop, Rook, Queen)
            else {
                for (int i = 1; i < 8; i++) {
                    var square = ChessUtil.getSquareByOffset(pieceSquareIndex, direction[0] * i, direction[1] * i);
                    if (square == -1) break;

                    var piece = chess.getBoard().get(square);

                    if (piece == null) {
                        possibleMoves.add(square); // Add empty square
                    } else if (piece.getColor().equals(activePiece.getColor().opposite())) {
                        possibleMoves.add(square); // Add capture square and stop further movement
                        break;
                    } else {
                        break; // Stop if blocked by a same-color piece
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Determines whether a specific square is under attack by pieces of a given color.
     *
     * @param chess         the current state of the chessboard.
     * @param square        the index of the square to check.
     * @param byPieceColor  the color of the attacking pieces.
     * @return {@code true} if the square is under attack, {@code false} otherwise.
     */
    static boolean isSquareUnderAttack(Chess chess, int square, PieceColor byPieceColor) {
        // Loop through all squares on the board to check each piece
        for (int i = 0; i < chess.getBoard().size(); i++) {
            var piece = chess.getBoard().get(i);

            // Skip if the square is empty, the piece is a king, or the piece is from the same color
            if (piece == null ||
                    piece.getType().equals("k") ||
                    piece.getColor().equals(byPieceColor.opposite())) continue;

            // Get the possible moves for the current piece
            var possibleMoves = calculatePieceMoves(chess, i);

            // If the square is in the list of possible moves for the piece, the square is under attack (by any non-king piece)
            if (possibleMoves.contains(square)) {
                return true;
            }
        }

        // If no piece (except king) can attack the square, return false
        return false;
    }

    /**
     * Retrieves a list of adjacent squares for a given square index.
     *
     * @param squareIndex the index of the square.
     * @return a list of indices representing adjacent squares.
     */
    private static List<Integer> getAdjacentSquares(int squareIndex) {
        // List to hold the indices of adjacent squares
        List<Integer> adjacentSquares = new ArrayList<>();

        // Set of directions to check for adjacent squares (including diagonals)
        int[][] directions = {
                {-1, -1}, // Top-left diagonal
                {-1, 0},  // Top
                {-1, 1},  // Top-right diagonal
                {0, -1},  // Left
                {0, 1},   // Right
                {1, -1},  // Bottom-left diagonal
                {1, 0},   // Bottom
                {1, 1}    // Bottom-right diagonal
        };

        // Loop through each direction and calculate the new square index
        for (var direction : directions) {
            // Get the new square index by applying the direction's row and column offset
            var square = ChessUtil.getSquareByOffset(squareIndex, direction[0], direction[1]);

            // If the new square is valid (not out of bounds), add it to the adjacentSquares list
            if (square != -1) adjacentSquares.add(square);
        }

        // Return the list of adjacent squares
        return adjacentSquares;
    }

    /**
     * Calculates valid castling moves for a king.
     *
     * @param chess            the current state of the chessboard.
     * @param pieceSquareIndex the index of the square containing the king.
     * @return a list of indices representing valid castling destinations, or an empty list if no castling is possible.
     */
    static List<Integer> calculateKingCastlingMove(Chess chess, int pieceSquareIndex) {
        List<Integer> possibleMoves = new ArrayList<>();

        var activePiece = chess.getBoard().get(pieceSquareIndex);
        if (activePiece == null || !activePiece.getType().equals("k")) return Collections.emptyList();

        if (activePiece.getColor().equals(PieceColor.WHITE)) {
            // Check if the king is under attack at the start
            if (isSquareUnderAttack(chess, pieceSquareIndex, PieceColor.BLACK))
                return Collections.emptyList();
            ;

            // White king-side castling
            if (chess.getCastleRights().stringCastleRight().contains("K")) {
                var squareF = chess.getBoard().get(ChessConstants.SQUARES.get("f1"));
                var squareG = chess.getBoard().get(ChessConstants.SQUARES.get("g1"));
                if (squareF == null &&
                        squareG == null &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("e1"), PieceColor.BLACK) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("f1"), PieceColor.BLACK) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("g1"), PieceColor.BLACK)) {
                    possibleMoves.add(ChessConstants.SQUARES.get("g1"));
                }
            }

            // White queen-side castling
            if (chess.getCastleRights().stringCastleRight().contains("Q")) {
                var squareC = chess.getBoard().get(ChessConstants.SQUARES.get("c1"));
                var squareD = chess.getBoard().get(ChessConstants.SQUARES.get("d1"));
                if (squareC == null &&
                        squareD == null &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("e1"), PieceColor.BLACK) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("d1"), PieceColor.BLACK) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("c1"), PieceColor.BLACK)) {
                    possibleMoves.add(ChessConstants.SQUARES.get("c1"));
                }
            }
        } else {
            // Check if the king is under attack at the start
            if (isSquareUnderAttack(chess, pieceSquareIndex, PieceColor.WHITE))
                return Collections.emptyList();

            // Black king-side castling
            if (chess.getCastleRights().stringCastleRight().contains("k")) {
                var squareF = chess.getBoard().get(ChessConstants.SQUARES.get("f8"));
                var squareG = chess.getBoard().get(ChessConstants.SQUARES.get("g8"));
                if (squareF == null &&
                        squareG == null &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("e8"), PieceColor.WHITE) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("f8"), PieceColor.WHITE) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("g8"), PieceColor.WHITE)) {
                    possibleMoves.add(ChessConstants.SQUARES.get("g8"));
                }
            }

            // Black queen-side castling
            if (chess.getCastleRights().stringCastleRight().contains("q")) {
                var squareC = chess.getBoard().get(ChessConstants.SQUARES.get("c8"));
                var squareD = chess.getBoard().get(ChessConstants.SQUARES.get("d8"));
                if (squareC == null &&
                        squareD == null &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("e8"), PieceColor.WHITE) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("d8"), PieceColor.WHITE) &&
                        !isSquareUnderAttack(
                                chess, ChessConstants.SQUARES.get("c8"), PieceColor.WHITE)) {
                    possibleMoves.add(ChessConstants.SQUARES.get("c8"));
                }
            }
        }

        return possibleMoves;
    }

}