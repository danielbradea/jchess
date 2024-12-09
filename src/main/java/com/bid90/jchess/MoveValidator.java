package com.bid90.jchess;

/**
 * A utility class for validating chess move formats.
 * <p>
 * The {@code MoveValidator} class provides methods to validate whether a chess move
 * conforms to the expected format. It ensures that moves follow standard chess notation
 * and throws exceptions when invalid formats are detected.
 * </p>
 * <p>
 * Examples of valid move formats:
 * <ul>
 *     <li>Standard moves: {@code "e2e4"}</li>
 *     <li>Promotion moves: {@code "e7e8=Q"}</li>
 * </ul>
 * </p>
 * <p>
 * Invalid move formats include:
 * <ul>
 *     <li>Moves with incorrect square coordinates: {@code "e9e4"}</li>
 *     <li>Moves missing required components: {@code "e4"}</li>
 *     <li>Moves with invalid promotion piece identifiers: {@code "e7e8=X"}</li>
 * </ul>
 * </p>
 */
public class MoveValidator {

    /**
     * Validates the format of a chess move.
     * <p>
     * This method checks whether the given move matches the standard chess move format:
     * from and to positions (e.g., "e2e4"), and optionally, a promotion piece (e.g., "e7e8=Q").
     * </p>
     * 
     * <p>
     * The expected format for a move string is:
     * <ul>
     *     <li>{@code [a-h][1-8]}: The "from" square coordinate.</li>
     *     <li>{@code [a-h][1-8]}: The "to" square coordinate.</li>
     *     <li>Optional promotion: {@code =[qrbnQRBN]} where the character indicates the piece promoted to.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Examples:
     * <ul>
     *     <li>Valid: {@code "e2e4"}, {@code "e7e8=Q"}</li>
     *     <li>Invalid: {@code "e2e9"}, {@code "e4"}, {@code "e7e8=X"}</li>
     * </ul>
     * </p>
     *
     * @param move The chess move string to validate. It should be in the format:
     *             {@code from-square to-square} (e.g., {@code "e2e4"}) and optionally a promotion (e.g., {@code "e7e8=Q"}).
     * @throws ChessException If the move format is invalid. This exception is thrown when the move does not match
     *                        the expected format or is not a valid castling move.
     */
    public static void validMoveFormat(String move) {
        if (!move.matches("^([a-h][1-8])([a-h][1-8])(=([qrbnQRBN]))?$")) {
            throw new ChessException("Invalid move format: " + move);
        }
    }
}
