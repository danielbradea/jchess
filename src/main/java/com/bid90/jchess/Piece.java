package com.bid90.jchess;

/**
 * Represents a chess piece with a symbol and a color.
 * The piece's symbol is case-sensitive, with uppercase representing white pieces
 * and lowercase representing black pieces. The symbol also identifies the type of piece.
 */
public class Piece {

    /**
     * The symbol representing the type of the piece.
     * Examples: 'K' or 'k' for King, 'Q' or 'q' for Queen, etc.
     */
    private final String symbol;

    /**
     * The color of the piece, either WHITE or BLACK.
     */
    private final PieceColor color;

    /**
     * Constructs a chess piece with the specified symbol and color.
     * The symbol is automatically adjusted to uppercase for white pieces
     * and lowercase for black pieces.
     *
     * @param symbol the symbol representing the piece (e.g., "K" for King, "p" for Pawn).
     * @param color  the color of the piece, either {@link PieceColor#WHITE} or {@link PieceColor#BLACK}.
     */
    public Piece(String symbol, PieceColor color) {
        if (color.equals(PieceColor.WHITE)) {
            this.symbol = symbol.toUpperCase();
        } else {
            this.symbol = symbol.toLowerCase();
        }
        this.color = color;
    }

    /**
     * Constructs a chess piece with the specified symbol, automatically determining its color.
     * Uppercase symbols are assigned the color {@link PieceColor#WHITE}, and
     * lowercase symbols are assigned the color {@link PieceColor#BLACK}.
     *
     * @param symbol the symbol representing the piece (e.g., "K" for King, "p" for Pawn).
     */
    public Piece(String symbol) {
        this.symbol = symbol;
        if (symbol.equals(symbol.toLowerCase())) {
            this.color = PieceColor.BLACK;
        } else {
            this.color = PieceColor.WHITE;
        }
    }

    /**
     * Gets the type of the chess piece.
     * The type is returned as a lowercase string, regardless of the piece's color.
     *
     * @return the lowercase representation of the piece's type.
     *         For example, "k" for King or "p" for Pawn.
     */
    public String getType() {
        return symbol.toLowerCase();
    }

    /**
     * Gets the symbol representing the chess piece.
     *
     * @return the symbol of the piece (e.g., "K" for King, "p" for Pawn).
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the color of the chess piece.
     *
     * @return the color of the piece, either {@link PieceColor#WHITE} or {@link PieceColor#BLACK}.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Returns the string representation of the chess piece.
     * This is the same as the piece's symbol.
     *
     * @return the symbol of the chess piece.
     */
    @Override
    public String toString() {
        return symbol;
    }

    /**
     * Creates a copy of this chess piece.
     * The new piece has the same symbol and color as the original.
     *
     * @return a new {@code Piece} instance identical to this piece.
     */
    public Piece copy() {
        return new Piece(symbol, color);
    }
}
