package com.bid90.jchess;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a chess move, including its validity and game state implications.
 */
public class MoveResult {
    private boolean validMove;          // Indicates if the move is valid.
    private boolean captureOccurred;    // Indicates if a capture occurred during the move.
    private boolean opponentInCheck;    // Indicates if the opponent is in check after the move.
    private boolean opponentInCheckmate; // Indicates if the opponent is in checkmate after the move.
    private boolean stalemate;          // Indicates if the game ended in stalemate.
    private boolean draw;               // Indicates if the game ended in a draw.
    private List<String> feedback;      // List of feedback messages for the move.
    private PieceColor winner;          // The color of the winner, if applicable.
    private PieceColor loser;           // The color of the loser, if applicable.

    /**
     * Default constructor initializes feedback as an empty list.
     */
    public MoveResult() {
        feedback = new ArrayList<>();
    }

    /**
     * Constructor to create a MoveResult with specified properties.
     *
     * @param validMove         Whether the move is valid.
     * @param captureOccurred   Whether a capture occurred during the move.
     * @param opponentInCheck   Whether the opponent is in check after the move.
     * @param opponentInCheckmate Whether the opponent is in checkmate after the move.
     * @param stalemate         Whether the game ended in stalemate.
     * @param draw              Whether the game ended in a draw.
     * @param feedback          List of feedback messages related to the move.
     * @param winner            The color of the winner, if applicable.
     * @param loser             The color of the loser, if applicable.
     */
    public MoveResult(boolean validMove,
                      boolean captureOccurred,
                      boolean opponentInCheck,
                      boolean opponentInCheckmate,
                      boolean stalemate,
                      boolean draw,
                      List<String> feedback,
                      PieceColor winner,
                      PieceColor loser) {
        this.validMove = validMove;
        this.captureOccurred = captureOccurred;
        this.opponentInCheck = opponentInCheck;
        this.opponentInCheckmate = opponentInCheckmate;
        this.stalemate = stalemate;
        this.draw = draw;
        this.feedback = feedback;
        this.winner = winner;
        this.loser = loser;
    }

    /**
     * @return Whether the move is valid.
     */
    public boolean isValidMove() {
        return validMove;
    }

    /**
     * @return Whether a capture occurred during the move.
     */
    public boolean isCaptureOccurred() {
        return captureOccurred;
    }

    /**
     * @return Whether the opponent is in check after the move.
     */
    public boolean isOpponentInCheck() {
        return opponentInCheck;
    }

    /**
     * @return Whether the opponent is in checkmate after the move.
     */
    public boolean isOpponentInCheckmate() {
        return opponentInCheckmate;
    }

    /**
     * @return Whether the game ended in stalemate.
     */
    public boolean isStalemate() {
        return stalemate;
    }

    /**
     * @return Whether the game ended in a draw.
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * @return The list of feedback messages.
     */
    public List<String> getFeedback() {
        return feedback;
    }

    /**
     * @return The color of the winner, if applicable.
     */
    public PieceColor getWinner() {
        return winner;
    }

    /**
     * @return The color of the loser, if applicable.
     */
    public PieceColor getLoser() {
        return loser;
    }

    /**
     * Sets whether the move is valid.
     *
     * @param validMove True if the move is valid, otherwise false.
     */
    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }

    /**
     * Sets whether a capture occurred during the move.
     *
     * @param captureOccurred True if a capture occurred, otherwise false.
     */
    public void setCaptureOccurred(boolean captureOccurred) {
        this.captureOccurred = captureOccurred;
    }

    /**
     * Sets whether the opponent is in check after the move.
     *
     * @param opponentInCheck True if the opponent is in check, otherwise false.
     */
    public void setOpponentInCheck(boolean opponentInCheck) {
        this.opponentInCheck = opponentInCheck;
    }

    /**
     * Sets whether the opponent is in checkmate after the move.
     *
     * @param opponentInCheckmate True if the opponent is in checkmate, otherwise false.
     */
    public void setOpponentInCheckmate(boolean opponentInCheckmate) {
        this.opponentInCheckmate = opponentInCheckmate;
    }

    /**
     * Sets whether the game ended in stalemate.
     *
     * @param stalemate True if the game ended in stalemate, otherwise false.
     */
    public void setStalemate(boolean stalemate) {
        this.stalemate = stalemate;
    }

    /**
     * Sets whether the game ended in a draw.
     *
     * @param draw True if the game ended in a draw, otherwise false.
     */
    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    /**
     * Sets feedback messages for the move.
     *
     * @param feedback A list of feedback messages.
     */
    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    /**
     * Sets the winner of the game.
     *
     * @param winner The color of the winner.
     */
    public void setWinner(PieceColor winner) {
        this.winner = winner;
    }

    /**
     * Sets the loser of the game.
     *
     * @param loser The color of the loser.
     */
    public void setLoser(PieceColor loser) {
        this.loser = loser;
    }

    /**
     * Adds a feedback message to the list.
     *
     * @param message The feedback message to add.
     */
    public void addFeedback(String message) {
        if(feedback == null){
            feedback = new ArrayList<>();
        }
        feedback.add(message);
    }

    /**
     * @return A string representation of the MoveResult object.
     */
    @Override
    public String toString() {
        return "MoveResult{" +
                "validMove=" + validMove +
                ", captureOccurred=" + captureOccurred +
                ", opponentInCheck=" + opponentInCheck +
                ", opponentInCheckmate=" + opponentInCheckmate +
                ", stalemate=" + stalemate +
                ", draw=" + draw +
                ", feedback=" + feedback +
                ", winner=" + winner +
                ", loser=" + loser +
                '}';
    }
}
