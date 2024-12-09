package com.bid90.jchess;

/**
 * Enum representing the color of a chess piece.
 * Provides utility methods to retrieve the opposite color and the symbol representation.
 */
public enum PieceColor {
    /**
     * Represents the white color for a chess piece, symbolized by "w".
     */
    WHITE("w"),

    /**
     * Represents the black color for a chess piece, symbolized by "b".
     */
    BLACK("b");

    /**
     * The symbol representing the color of the chess piece.
     * "w" for white and "b" for black.
     */
    final String symbol;

    /**
     * Constructs a PieceColor with the specified symbol.
     * Validates that the symbol is either "w" or "b".
     *
     * @param symbol the symbol representing the color ("w" for white, "b" for black).
     * @throws ChessException if the symbol is invalid.
     */
    PieceColor(String symbol) {
        if (!"w".equals(symbol) && !"b".equals(symbol)) {
            throw new ChessException("Invalid symbol for PieceColor: " + symbol);
        }
        this.symbol = symbol;
    }

    /**
     * Returns the opposite color of the current piece color.
     *
     * @return the opposite {@code PieceColor}.
     *         If the current color is {@code WHITE}, returns {@code BLACK}, and vice versa.
     */
    public PieceColor opposite() {
        return this.equals(PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
    }

    /**
     * Returns the {@code PieceColor} corresponding to the given symbol.
     *
     * @param symbol the symbol of the piece color, either "w" for white or "b" for black.
     * @return the {@code PieceColor} corresponding to the symbol.
     * @throws ChessException if the symbol is invalid.
     */
    public static PieceColor fromSymbol(String symbol) {
        for (PieceColor color : values()) {
            if (color.symbol.equals(symbol)) {
                return color;
            }
        }
        throw new ChessException("Invalid symbol for PieceColor: " + symbol);
    }

    /**
     * Returns the symbol representing the color of the chess piece.
     *
     * @return the symbol of the color, either "w" for white or "b" for black.
     */
    public String getSymbol() {
        return symbol;
    }
}

