package com.bid90.jchess;

/**
 * Represents a move in a chess game, including the details of the piece moved,
 * the origin and destination squares, any piece captured, and any promotion.
 */
public class Move {

    /**
     * The color of the player making the move.
     */
    private final PieceColor color;

    /**
     * The starting square of the move, represented as an integer.
     * The exact representation (e.g., 0-63 for 8x8 boards) is determined by the board implementation.
     */
    private final int from;

    /**
     * The destination square of the move, represented as an integer.
     * The exact representation (e.g., 0-63 for 8x8 boards) is determined by the board implementation.
     */
    private final int to;

    /**
     * The piece being moved.
     */
    private final Piece piece;

    /**
     * The piece that was captured during the move, if any.
     * This is {@code null} if no piece was captured.
     */
    private final Piece captured;

    /**
     * The piece resulting from a promotion, if applicable.
     * This is {@code null} if the move did not involve a promotion.
     */
    private final Piece promotion;

    /**
     * Constructs a Move object with all necessary details.
     *
     * @param color     the color of the player making the move.
     * @param from      the starting square of the move.
     * @param to        the destination square of the move.
     * @param piece     the piece being moved.
     * @param captured  the piece that was captured during the move, or {@code null} if none.
     * @param promotion the piece resulting from a promotion, or {@code null} if none.
     */
    public Move(PieceColor color, int from, int to, Piece piece, Piece captured, Piece promotion) {
        this.color = color;
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.captured = captured;
        this.promotion = promotion;
    }

    /**
     * Gets the color of the player making the move.
     *
     * @return the {@link PieceColor} of the player.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Gets the starting square of the move.
     *
     * @return the integer representing the starting square.
     */
    public int getFrom() {
        return from;
    }

    /**
     * Gets the destination square of the move.
     *
     * @return the integer representing the destination square.
     */
    public int getTo() {
        return to;
    }

    /**
     * Gets the piece being moved.
     *
     * @return the {@link Piece} being moved.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Gets the piece that was captured during the move, if any.
     *
     * @return the captured {@link Piece}, or {@code null} if no piece was captured.
     */
    public Piece getCaptured() {
        return captured;
    }

    /**
     * Gets the piece resulting from a promotion, if applicable.
     *
     * @return the promoted {@link Piece}, or {@code null} if no promotion occurred.
     */
    public Piece getPromotion() {
        return promotion;
    }

    /**
     * Returns a string representation of the move.
     * Includes details about the color, starting square, destination square,
     * the piece moved, any piece captured, and any promotion.
     *
     * @return a string representation of the move.
     */
    @Override
    public String toString() {
        return "Move{" +
                "color=" + color +
                ", from=" + from +
                ", to=" + to +
                ", piece=" + piece +
                ", captured=" + captured +
                ", promotion=" + promotion +
                '}';
    }
}
