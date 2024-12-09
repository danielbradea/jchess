package com.bid90.jchess;

/**
 * Represents the castling rights in a chess game.
 * This class provides functionality to track and modify the ability of both White and Black
 * players to castle on their king-side and queen-side. It also includes utility methods
 * to generate a FEN-compliant representation of the castling rights.
 */
public class CastleRight {

    /**
     * Indicates whether White can castle on the king-side.
     */
    private boolean whiteCanCastleKingSide;

    /**
     * Indicates whether White can castle on the queen-side.
     */
    private boolean whiteCanCastleQueenSide;

    /**
     * Indicates whether Black can castle on the king-side.
     */
    private boolean blackCanCastleKingSide;

    /**
     * Indicates whether Black can castle on the queen-side.
     */
    private boolean blackCanCastleQueenSide;

    /**
     * Constructs a new {@code CastleRight} object with the specified castling rights for
     * both White and Black players.
     *
     * @param whiteCanCastleKingSide  whether White can castle on the king-side.
     * @param whiteCanCastleQueenSide whether White can castle on the queen-side.
     * @param blackCanCastleKingSide  whether Black can castle on the king-side.
     * @param blackCanCastleQueenSide whether Black can castle on the queen-side.
     */
    public CastleRight(boolean whiteCanCastleKingSide,
                       boolean whiteCanCastleQueenSide,
                       boolean blackCanCastleKingSide,
                       boolean blackCanCastleQueenSide) {
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
    }

    /**
     * Returns whether White can castle on the king-side.
     *
     * @return {@code true} if White can castle king-side, {@code false} otherwise.
     */
    public boolean isWhiteCanCastleKingSide() {
        return whiteCanCastleKingSide;
    }

    /**
     * Returns whether White can castle on the queen-side.
     *
     * @return {@code true} if White can castle queen-side, {@code false} otherwise.
     */
    public boolean isWhiteCanCastleQueenSide() {
        return whiteCanCastleQueenSide;
    }

    /**
     * Returns whether Black can castle on the king-side.
     *
     * @return {@code true} if Black can castle king-side, {@code false} otherwise.
     */
    public boolean isBlackCanCastleKingSide() {
        return blackCanCastleKingSide;
    }

    /**
     * Returns whether Black can castle on the queen-side.
     *
     * @return {@code true} if Black can castle queen-side, {@code false} otherwise.
     */
    public boolean isBlackCanCastleQueenSide() {
        return blackCanCastleQueenSide;
    }

    /**
     * Sets whether White can castle on the king-side.
     *
     * @param whiteCanCastleKingSide {@code true} if White can castle king-side, {@code false} otherwise.
     */
    public void setWhiteCanCastleKingSide(boolean whiteCanCastleKingSide) {
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
    }

    /**
     * Sets whether White can castle on the queen-side.
     *
     * @param whiteCanCastleQueenSide {@code true} if White can castle queen-side, {@code false} otherwise.
     */
    public void setWhiteCanCastleQueenSide(boolean whiteCanCastleQueenSide) {
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
    }

    /**
     * Sets whether Black can castle on the king-side.
     *
     * @param blackCanCastleKingSide {@code true} if Black can castle king-side, {@code false} otherwise.
     */
    public void setBlackCanCastleKingSide(boolean blackCanCastleKingSide) {
        this.blackCanCastleKingSide = blackCanCastleKingSide;
    }

    /**
     * Sets whether Black can castle on the queen-side.
     *
     * @param blackCanCastleQueenSide {@code true} if Black can castle queen-side, {@code false} otherwise.
     */
    public void setBlackCanCastleQueenSide(boolean blackCanCastleQueenSide) {
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
    }

    /**
     * Creates a copy of this {@code CastleRight} object.
     *
     * @return a new {@code CastleRight} object with the same castling rights as this one.
     */
    public CastleRight copy() {
        return new CastleRight(whiteCanCastleKingSide, whiteCanCastleQueenSide,
                blackCanCastleKingSide, blackCanCastleQueenSide);
    }

    /**
     * Generates a FEN-compliant representation of the castling rights.
     *
     * @return a string representing the castling rights in FEN notation.
     *         Returns "-" if no castling rights are available.
     */
    public String stringCastleRight() {
        if (!whiteCanCastleKingSide && !whiteCanCastleQueenSide &&
                !blackCanCastleKingSide && !blackCanCastleQueenSide) {
            return "-";
        }
        String castleRight = "";
        if (whiteCanCastleKingSide) castleRight += "K";
        if (whiteCanCastleQueenSide) castleRight += "Q";
        if (blackCanCastleKingSide) castleRight += "k";
        if (blackCanCastleQueenSide) castleRight += "q";
        return castleRight;
    }

    /**
     * Returns a string representation of this {@code CastleRight} object,
     * primarily for debugging purposes.
     *
     * @return a string representation of the castling rights.
     */
    @Override
    public String toString() {
        return "CastleRight{" +
                "whiteCanCastleKingSide=" + whiteCanCastleKingSide +
                ", whiteCanCastleQueenSide=" + whiteCanCastleQueenSide +
                ", blackCanCastleKingSide=" + blackCanCastleKingSide +
                ", blackCanCastleQueenSide=" + blackCanCastleQueenSide +
                '}';
    }
}

