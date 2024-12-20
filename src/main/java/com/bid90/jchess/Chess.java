package com.bid90.jchess;

import java.util.*;

/**
 * This class represents a Chess game, including the state of the board, the pieces, and all game-related operations.
 * It supports move validation, check/checkmate detection, and tracking of game history. The class is designed to
 * handle the main logic for a chess game, including determining whether a move is legal, updating the board,
 * and identifying endgame conditions (check, checkmate, stalemate, draw).
 *
 * It also provides functionality to undo the last move and track the history of moves made in the game.
 *
 * The game uses Standard Algebraic Notation (SAN) for move representation and supports standard chess rules
 * as well as game-ending conditions.
 */
public class Chess {
    private List<Piece> board;
    private PieceColor turn;
    private int npSquare;
    private int halfMove;
    private int fullMove;
    private CastleRight castleRights;
    private int whiteKingPosition;
    private int blackKingPosition;

    LinkedList<History> gameHistory = new LinkedList<>();
    LinkedList<String> san = new LinkedList<>();
    HashMap<String, Integer> positionHistoryCount = new HashMap<>();


    /**
     * Initializes a new chess game by clearing the board and setting up the default chess position.
     * This constructor performs the following actions:
     * <ul>
     *   <li>Clears the board using the {@link #clear()} method, resetting the game state.</li>
     *   <li>Loads the default chess position using the {@link #load(String)} method and the constant
     *   {@link ChessConstants#DEFAULT_CHESS_POSITION}.</li>
     * </ul>
     * After calling this constructor, the board will be set up with the standard chess pieces,
     * white will be the starting player, and the game is ready to begin.
     */
    public Chess() {
        clear();
        load(ChessConstants.DEFAULT_CHESS_POSITION);
    }

    /**
     * Clears the game state, resetting the chessboard and game variables to their initial values.
     */
    void clear() {
        board = new ArrayList<>();
        turn = PieceColor.WHITE;
        npSquare = ChessConstants.EMPTY_SQUARE;
        halfMove = 0;
        fullMove = 1;
        castleRights = new CastleRight(false, false, false, false);
        whiteKingPosition = ChessConstants.DEFAULT_WHITE_KING_SQUARE;
        blackKingPosition = ChessConstants.DEFAULT_BLACK_KING_SQUARE;
    }

    /**
     * Creates a deep copy of the current chess game state, including the board, turn, castle rights, and history.
     *
     * @return A new `Chess` object with the same state as the current game.
     */
    public Chess copy() {
        var clone = new Chess();
        clone.board = new ArrayList<>(board);
        clone.turn = this.turn;
        clone.npSquare = this.npSquare;
        clone.halfMove = this.halfMove;
        clone.fullMove = this.fullMove;
        clone.castleRights = this.castleRights.copy();
        clone.whiteKingPosition = this.whiteKingPosition;
        clone.blackKingPosition = this.blackKingPosition;
        clone.gameHistory = new LinkedList<>(this.gameHistory);
        clone.san = new LinkedList<>(this.san);
        clone.positionHistoryCount = new HashMap<>(this.positionHistoryCount);
        return clone;
    }

    /**
     * Gets the current chessboard.
     *
     * @return A list of `Piece` objects representing the chessboard.
     */
    public List<Piece> getBoard() {
        return board;
    }

    /**
     * Gets the color of the player whose turn it is.
     *
     * @return The current turn as a `PieceColor` object.
     */
    public PieceColor getTurn() {
        return turn;
    }

    /**
     * Gets the square where en passant capture can occur, or -1 if none.
     *
     * @return The en passant square as an integer.
     */
    public int getNpSquare() {
        return npSquare;
    }

    /**
     * Gets the number of half moves since the last pawn move or capture.
     *
     * @return The number of half moves.
     */
    public int getHalfMove() {
        return halfMove;
    }

    /**
     * Gets the full move number in the game.
     *
     * @return The full move number.
     */
    public int getFullMove() {
        return fullMove;
    }

    /**
     * Gets the current castling rights.
     *
     * @return The `CastleRight` object containing the castling rights.
     */
    public CastleRight getCastleRights() {
        return castleRights;
    }

    /**
     * Gets the position of the white king on the board.
     *
     * @return The index of the white king's position.
     */
    public int getWhiteKingPosition() {
        return whiteKingPosition;
    }

    /**
     * Gets the position of the black king on the board.
     *
     * @return The index of the black king's position.
     */
    public int getBlackKingPosition() {
        return blackKingPosition;
    }

    /**
     * Gets the history of the game, including all moves made.
     *
     * @return A `LinkedList` of `History` objects representing the game history.
     */
    public LinkedList<History> getGameHistory() {
        return gameHistory;
    }

    /**
     * Gets the list of moves in Standard Algebraic Notation (SAN).
     *
     * @return A `LinkedList` of strings representing the moves in SAN.
     */
    public LinkedList<String> getSan() {
        return san;
    }

    /**
     * Gets the count of positions visited during the game, used for detecting threefold repetition.
     *
     * @return A `HashMap` where the keys are board positions in FEN format and the values are the count of times those positions have been reached.
     */
    public HashMap<String, Integer> getPositionHistoryCount() {
        return positionHistoryCount;
    }

    /**
     * Resets the game to its default position.
     * This method loads the default chess position.
     */
    void reset() {
        load(ChessConstants.DEFAULT_CHESS_POSITION);
    }

    /**
     * Gets the FEN (Forsyth-Edwards Notation) string after undoing a specified number of moves.
     *
     * @param steps The number of moves to undo.
     * @return A FEN string representing the game state after the specified number of undo steps.
     */
    public String getFENAfterUndo(int steps) {
        var cloneChess = this.copy();
        if (steps == 0) return cloneChess.toFen();
        var gameHistorySize = cloneChess.getGameHistory().size();
        if (gameHistorySize == 0 || gameHistorySize < steps) {
            return null;
        }
        var count = 0;
        while (count < steps) {
            count++;
            cloneChess.moveBack();
        }

        return cloneChess.toFen();
    }

    /**
     * Converts the current game state to a FEN (Forsyth-Edwards Notation) string.
     *
     * @return A string representing the current game state in FEN format.
     */
    public String toFen() {
        StringBuilder fen = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;
            for (int col = 0; col < 8; col++) {
                var piece = board.get(row * 8 + col);
                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(piece.getSymbol());
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            if (row < 7) {
                fen.append('/');
            }
        }

        // 2. Add active color
        fen.append(" ");
        fen.append(turn.getSymbol());

        // 3. Add castling rights
        fen.append(" ");
        fen.append(castleRights.stringCastleRight());

        // 4. Add en passant square
        fen.append(" ");
        if (npSquare == -1) {
            fen.append('-');
        } else {
            fen.append(ChessUtil.toAlgebraic(npSquare));
        }

        // 5. Add half-move clock
        fen.append(' ');
        fen.append(halfMove);

        // 6. Add full-move number
        fen.append(' ');
        fen.append(fullMove);

        return fen.toString();
    }

    /**
     * Loads a chess game from a given FEN string, updating the board, turn, castling rights, and other game state variables.
     *
     * @param fen The FEN string representing the game state to load.
     */
    public void load(String fen) {
        FENValidator.validateFEN(fen);
        clear();
        var tokens = fen.split(" ");
        var boardString = tokens[0];
        var rows = boardString.split("/");
        var square = 0;
        for (var row : rows) {
            for (var p : row.split("")) {
                if (p.matches("[1-8]")) {
                    board.addAll(Collections.nCopies(Integer.parseInt(p), null));
                    square += Integer.parseInt(p);
                } else {
                    board.add(new Piece(p));
                    if (p.equals("K")) {
                        whiteKingPosition = square;
                    }
                    if (p.equals("k")) {
                        blackKingPosition = square;
                    }
                    square++;
                }
            }
        }

        var playerTurn = tokens[1];
        turn = PieceColor.fromSymbol(playerTurn);

        var castlingAvailability = tokens[2];

        if (!castlingAvailability.equals("-")) {
            if (castlingAvailability.contains("K")) castleRights.setWhiteCanCastleKingSide(true);
            if (castlingAvailability.contains("Q")) castleRights.setWhiteCanCastleQueenSide(true);
            if (castlingAvailability.contains("k")) castleRights.setBlackCanCastleKingSide(true);
            if (castlingAvailability.contains("q")) castleRights.setBlackCanCastleQueenSide(true);
        }

        var enPassant = tokens[3];
        if (!enPassant.equals("-")) {
            npSquare = ChessConstants.SQUARES.get(enPassant);
        }
        var halfMoveClock = tokens[4];
        halfMove = Integer.parseInt(halfMoveClock);

        var fullMoveNumber = tokens[5];
        fullMove = Integer.parseInt(fullMoveNumber);
    }

    /**
     * Handles the movement of a piece on the board based on the provided move string.
     * Validates the move format, applies the move, and checks for game-ending conditions such as
     * check, checkmate, stalemate, and draw.
     *
     * @param stringMove The move to be made, in standard algebraic notation (e.g., "e2e4").
     * @return A {@link MoveResult} object containing feedback, whether the move was valid,
     *         and details about captures, check, checkmate, stalemate, or draw.
     */
    public MoveResult move(String stringMove) {
        var moveResult = new MoveResult();
        try {
            MoveValidator.validMoveFormat(stringMove);
        } catch (Exception e) {
            moveResult.addFeedback(e.getMessage());
            return moveResult;
        }

        // Extract the "from" square (first two characters) from the move string
        String fromSquare = stringMove.substring(0, 2);

        // Extract the "to" square (next two characters) from the move string
        String toSquare = stringMove.substring(2, 4);

        Piece promotionPiece = null;

        if (stringMove.length() == 6) {
            promotionPiece = new Piece(stringMove.substring(5, 6));
        }
        var move = buildMove(board, ChessConstants.SQUARES.get(fromSquare), ChessConstants.SQUARES.get(toSquare), promotionPiece);
        var cloneChess = copy();

        if (move.isEmpty() || !makeMove(move.get())) {
            moveResult.addFeedback("Invalid move");
            return moveResult;
        }

        var history = addHistory(move.get(), cloneChess);


        moveResult.setValidMove(true);
        if (move.get().getCaptured() != null) {
            moveResult.setCaptureOccurred(true);
            moveResult.addFeedback("Piece captured.");
        } else {
            moveResult.addFeedback("Move successful.");
        }


        boolean isCheck = this.kingAttacked(cloneChess.turn.opposite());
        boolean isCheckmate = false;
        boolean isDraw = false;
        boolean isStalemate = false;

        var hasLegalMovesEmpty = generateLegalMoves(cloneChess.turn.opposite()).isEmpty();

        // Determine if the current move results in checkmate or stalemate
        if (isCheck && hasLegalMovesEmpty) {
            isCheckmate = true; // Checkmate occurs if the king is in check and no legal moves exist
        } else if (!isCheck && hasLegalMovesEmpty) {
            isStalemate = true; // Stalemate occurs if the king is not in check and no legal moves exist
        }

        // Determine if the game is in a draw state based on various conditions
        if (getHalfMove() >= 100 ||
                isStalemate ||
                insufficientMaterial() ||
                threefoldRepetition()) {
            isDraw = true; // Draw if the half moves exceed 100 or any other draw condition is met
        }

        if (isCheckmate) {
            var winner = history.getTurn(); // Current player wins
            var loser = history.getTurn().opposite();
            moveResult.setWinner(winner);
            moveResult.setLoser(loser);
            moveResult.addFeedback("Checkmate! " + winner + " wins, " + loser + " loses.");
            moveResult.setOpponentInCheckmate(true);
        } else if (isCheck) {
            moveResult.setOpponentInCheck(true);
            moveResult.addFeedback("Opponent is in check.");
        }

        if (isStalemate) {
            moveResult.setStalemate(true);
            moveResult.addFeedback("Stalemate!");
        }
        if (isDraw) {
            moveResult.setDraw(true);
            moveResult.addFeedback("Draw!");
        }
        return moveResult;
    }

    /**
     * Reverts the last move made in the game.
     * If there are no moves to undo, returns false.
     *
     * @return {@code true} if the last move was successfully undone, {@code false} otherwise.
     */
    public boolean moveBack() {
        if (gameHistory.isEmpty()) return false;
        var history = gameHistory.removeLast();

        turn = history.getTurn();
        npSquare = history.getNpSquare();
        halfMove = history.getHalfMoves();
        fullMove = history.getFullMove();
        blackKingPosition = history.getBlackKingPosition();
        whiteKingPosition = history.getWhiteKingPosition();
        castleRights = history.getCastlingRights().copy();


        var oldSan = san.removeLast();
        var splitOldSan = oldSan.split(" ");
        if (splitOldSan.length > 1) {
            san.add(splitOldSan[0]);
        }

        var positionCount = positionHistoryCount.get(history.getBoardHash());
        if (positionCount > 1) {
            positionHistoryCount.put(history.getBoardHash(), positionCount - 1);
        } else {
            positionHistoryCount.remove(history.getBoardHash());
        }

        // Handle en passant captures
        if (history.getMove().getTo() == npSquare && npSquare > -1 && history.getMove().getCaptured() != null) {
            int rankIndex = ChessUtil.rank(history.getMove().getTo()) + (turn == PieceColor.WHITE ? 1 : -1);
            int fileIndex = ChessUtil.file(history.getMove().getTo());

            board.set(rankIndex * 8 + fileIndex, history.getMove().getCaptured());
            board.set(history.getMove().getFrom(), board.get(history.getMove().getTo()));
            board.set(history.getMove().getTo(), null);

        } else {
            if (history.getMove().getPromotion() != null &&
                    history.getMove().getPromotion() == board.get(history.getMove().getTo())) {
                board.set(history.getMove().getFrom(), new Piece("p", history.getMove().getPromotion().getColor()));
            } else {
                board.set(history.getMove().getFrom(), board.get(history.getMove().getTo()));
            }
            board.set(history.getMove().getTo(), history.getMove().getCaptured());
        }

        return false;
    }

    /**
     * Checks if the king of the given player color is under attack.
     *
     * @param turn The color of the player whose king is being checked.
     * @return {@code true} if the king of the specified color is under attack, {@code false} otherwise.
     */
    public boolean kingAttacked(PieceColor turn) {
        int squareIndex = turn == PieceColor.WHITE ? whiteKingPosition : blackKingPosition;
        return PieceMoveCalculator.isSquareUnderAttack(this, squareIndex, turn.opposite());
    }

    /**
     * Determines if the current position is in check for the current player's turn.
     *
     * @return {@code true} if the current player's king is in check, {@code false} otherwise.
     */
    public boolean isCheck() {
        return kingAttacked(turn);
    }

    /**
     * Determines if the current position results in checkmate for the current player's turn.
     *
     * @return {@code true} if the current player's king is in check and there are no legal moves to escape,
     *         {@code false} otherwise.
     */
    public boolean isCheckmate() {
        return isCheck() && generateLegalMoves(turn).isEmpty();
    }

    /**
     * Determines if the current position results in a stalemate for the current player's turn.
     *
     * @return {@code true} if the current player's king is not in check and there are no legal moves to make,
     *         {@code false} otherwise.
     */
    public boolean inStalemate() {
        return !isCheck() && generateLegalMoves(turn).isEmpty();
    }

    /**
     * Determines if the current position results in a draw based on certain game conditions:
     * 1. The game has reached 100 half-moves without a capture or pawn move.
     * 2. Stalemate.
     * 3. Insufficient material for checkmate.
     * 4. Threefold repetition.
     *
     * @return {@code true} if the game is a draw, {@code false} otherwise.
     */
    public boolean isDraw() {
        return halfMove >= 100 ||
                inStalemate() ||
                insufficientMaterial() ||
                threefoldRepetition();
    }

    /**
     * Determines if there is insufficient material on the board to checkmate.
     * The conditions checked are:
     * 1. King vs King (k vs K).
     * 2. King vs King + Knight or Bishop (k vs K + (N or B)).
     * 3. King + Knight or Bishop vs King (k + (n or b) vs K).
     * 4. King + Bishop (same color) vs King + Bishop (same color).
     *
     * @return {@code true} if the position has insufficient material to checkmate, {@code false} otherwise.
     */
    public boolean insufficientMaterial() {
        // Initialize counters and lists for pieces
        int nrPieces = 0; // Count of total pieces
        List<Piece> whitePieces = new ArrayList<>(); // List of white pieces
        List<Piece> blackPieces = new ArrayList<>(); // List of black pieces
        List<PieceColor> bishopsPieceColors = new ArrayList<>(); // List of bishops' piece colors

        // Iterate through each square on the board
        for (int i = 0; i < board.size(); i++) {
            Piece piece = board.get(i);

            // If there's a piece on the square, process it
            if (piece != null) {
                nrPieces++; // Increment the piece counter

                // If more than 4 pieces are on the board, return false (insufficient material is not possible)
                if (nrPieces > 4) {
                    return false;
                }

                // Add piece to the respective color list
                if (piece.getColor() == PieceColor.WHITE) {
                    whitePieces.add(piece);
                } else {
                    blackPieces.add(piece);
                }

                // If the piece is a bishop, track its square color (light or dark)
                if (piece.getType().equals("b")) {
                    bishopsPieceColors.add(ChessUtil.isLightSquare(i) ? PieceColor.WHITE : PieceColor.BLACK);
                }
            }
        }

        // (1) King vs. King: Only two pieces, both kings
        if (blackPieces.size() == 1 && whitePieces.size() == 1) {
            return true;
        }

        // (2) King vs. King + (Knight or Bishop): Black has only a king, white has a king and either a knight or bishop
        if (blackPieces.size() == 1 && whitePieces.size() == 2 &&
                whitePieces.stream().anyMatch(piece -> piece.getType().equals("n") || piece.getType().equals("b"))) {
            return true;
        }

        // (3) King + (Knight or Bishop) vs. King: White has only a king, black has a king and either a knight or bishop
        if (whitePieces.size() == 1 && blackPieces.size() == 2 &&
                blackPieces.stream().anyMatch(piece -> piece.getType().equals("n") || piece.getType().equals("b"))) {
            return true;
        }

        // (4) King + Bishop (same color) vs. King + Bishop (same color): Both sides have two bishops of the same color
        return blackPieces.size() == 2 && whitePieces.size() == 2 &&
                bishopsPieceColors.size() == 2 && bishopsPieceColors.get(0) == bishopsPieceColors.get(1);

    }

    /**
     * Determines if the game is in a threefold repetition state, meaning the same position has occurred three times.
     *
     * @return {@code true} if the game is in threefold repetition, {@code false} otherwise.
     */
    public boolean threefoldRepetition() {
        for (var value : positionHistoryCount.values()) {
            if (value >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the current move to the game history and updates the game state accordingly.
     * This includes updating check, checkmate, stalemate, draw status, and position history.
     *
     * @param move The move to be added to the history.
     * @param chess The current state of the chess game.
     * @return A {@link History} object representing the added history entry.
     */
    private History addHistory(Move move, Chess chess) {

        boolean isCheck = chess.kingAttacked(move.getColor().opposite());
        boolean isCheckmate = false;
        boolean isDraw = false;
        boolean isStalemate = false;


        var hasLegalMoves = chess.generateLegalMoves(move.getColor().opposite());
        // Determine if the current move results in checkmate or stalemate
        if (isCheck && hasLegalMoves.isEmpty()) {
            isCheckmate = true; // Checkmate occurs if the king is in check and no legal moves exist
        } else if (!isCheck && hasLegalMoves.isEmpty()
        ) {
            isStalemate = true; // Stalemate occurs if the king is not in check and no legal moves exist
        }

        // Determine if the game is in a draw state based on various conditions
        if (chess.getHalfMove() >= 100 ||
                isStalemate ||
                chess.insufficientMaterial() ||
                chess.threefoldRepetition()) {
            isDraw = true; // Draw if the half moves exceed 100 or any other draw condition is met
        }

        var boardStringPiece = chess.board.stream().map(piece -> piece == null ? "-" : piece.getSymbol()).toList();
        var hashBoard = ChessUtil.boardToHash(boardStringPiece);


        var history = new History(
                move,
                chess.turn,
                chess.getNpSquare(),
                chess.getHalfMove(),
                chess.getFullMove(),
                chess.getCastleRights(),
                chess.getBlackKingPosition(),
                chess.getWhiteKingPosition(),
                isCheck,
                isCheckmate,
                isStalemate,
                isDraw,
                hashBoard);

        // Prepare the SAN string for this move
        String sanString = "";


        if (san.isEmpty()) {
            // If it's the first move and the turn is Black
            if (chess.turn == PieceColor.BLACK) {
                sanString = "..."; // Placeholder for Black's move
            } else {
                // If it's White's turn, convert the move to SAN
                sanString = toSAN(history);
            }
        } else {
            // Handle subsequent moves
            if (chess.turn == PieceColor.BLACK) {
                // If it's Black's turn, append the SAN to the last White move
                String lastSan = san.removeLast();
                sanString = lastSan + " " + toSAN(history);
            } else {
                // If it's White's turn, add the SAN with the move number
                sanString = toSAN(history);
            }
        }

        // Add the current game history entry to the history list
        gameHistory.add(history);
        positionHistoryCount.merge(hashBoard, 1, Integer::sum);
        san.add(sanString);
        return history;
    }

    /**
     * Converts a move to its Standard Algebraic Notation (SAN) representation.
     *
     * @param historyEntry The history entry containing the move to convert to SAN.
     * @return The move in SAN format.
     */
    private String toSAN(History historyEntry) {

        var move = historyEntry.getMove();
        var piece = historyEntry.getMove().getPiece();
        var promotion = move.getPromotion() == null ? "" : "=" + move.getPromotion().getType().toUpperCase();
        var sanCapture = (move.getCaptured() != null) ? "x" : "";
        var sanCheckSymbol = historyEntry.isCheckmate() ? "#" : historyEntry.isCheck() ? "+" : "";

        if (piece.getType().equals("k")) {

            if (ChessUtil.isKingSideCastling(move.getFrom(), move.getTo())) {
                return "O-O";
            } else if (ChessUtil.isQueenSideCastling(move.getFrom(), move.getTo())) {
                return "O-O-O";
            }

        }

        var basicMove = "";
        if (piece.getType().equals("p")) {
            basicMove = sanCapture + ChessUtil.toAlgebraic(move.getTo()) + promotion;
        } else {
            basicMove = piece.getType().toUpperCase() +
                    sanCapture +
                    ChessUtil.toAlgebraic(move.getTo()) +
                    promotion;
        }

        // Final SAN move string
        String sanMove = basicMove + sanCheckSymbol;

        // Add additional notations for stalemate and draw if necessary
        if (historyEntry.isStalemate()) {
            sanMove += ""; // todo (stalemate)
        } else if (historyEntry.isDraw()) {
            sanMove += ""; //todo (draw)
        }

        return sanMove;

    }

    /**
     * Generates a list of legal moves for the given player's turn.
     * This includes all possible moves that are allowed for the current player, including promotions and captures.
     *
     * @param turn The color of the player whose legal moves are being generated.
     * @return A list of {@link Move} objects representing the legal moves for the current player.
     */
    private List<Move> generateLegalMoves(PieceColor turn) {
        List<Move> moves = new ArrayList<>();
        int promotionRank = turn == PieceColor.WHITE
                ? ChessConstants.WHITE_RANK_PROMOTION
                : ChessConstants.BLACK_RANK_PROMOTION;

        for (int sourceSquare = 0; sourceSquare < board.size(); sourceSquare++) {
            var piece = board.get(sourceSquare);
            // Skip empty squares or opponent pieces
            if (piece == null || !piece.getColor().equals(turn)) continue;
            var moveIndices = PieceMoveCalculator.calculatePieceMoves(this, sourceSquare);
            for (var targetSquare : moveIndices) {
                var capturedPiece = board.get(targetSquare);
                // Handle pawn promotion
                if (piece.getType().equals("p") && ChessUtil.rank(targetSquare) == promotionRank) {
                    for (var promotionType : "qrnb".split("")) {
                        var promotionPiece = new Piece(promotionType, turn);
                        moves.add(new Move(turn, sourceSquare, targetSquare, piece, capturedPiece, promotionPiece));
                    }
                } else {
                    // Regular move
                    moves.add(new Move(turn, sourceSquare, targetSquare, piece, capturedPiece, null));
                }
            }
        }

        // Filter out illegal moves
        moves.removeIf(move -> {
            var clonedChess = copy();
            return !clonedChess.makeMove(move);
        });

        return moves;
    }

    /**
     * Attempts to make a move on the chessboard by updating the board, handling special moves such as castling,
     * en passant, and pawn promotion. This method also handles the logic for capturing pieces and updating
     * castling rights.
     *
     * @param move The {@link Move} object representing the move to be made.
     * @return {@code true} if the move was successfully made, {@code false} if the move was invalid or would
     *         result in the player's king being in check.
     */
    private boolean makeMove(Move move) {
        var chessClone = copy();
        chessClone.board.set(move.getFrom(), null);
        if (move.getPromotion() != null) {
            chessClone.board.set(move.getTo(), move.getPromotion());
        } else {
            chessClone.board.set(move.getTo(), move.getPiece());
        }
        var moves = PieceMoveCalculator.calculateMoves(this, move.getFrom());
        if (!moves.contains(move.getTo())) return false;

        if (move.getCaptured() != null && move.getCaptured().getType().equals("r")) {
            if (move.getColor() == PieceColor.WHITE &&
                    move.getTo() == ChessConstants.DEFAULT_BLACK_ROOK_KS_SQUARE) {
                chessClone.castleRights.setBlackCanCastleKingSide(false);
            } else if (move.getColor() == PieceColor.WHITE &&
                    move.getTo() == ChessConstants.DEFAULT_BLACK_ROOK_QS_SQUARE) {
                chessClone.castleRights.setBlackCanCastleQueenSide(false);
            } else if (move.getColor() == PieceColor.BLACK &&
                    move.getTo() == ChessConstants.DEFAULT_WHITE_ROOK_KS_SQUARE) {
                chessClone.castleRights.setWhiteCanCastleKingSide(false);
            } else if (move.getColor() == PieceColor.BLACK &&
                    move.getTo() == ChessConstants.DEFAULT_WHITE_ROOK_QS_SQUARE) {
                chessClone.castleRights.setWhiteCanCastleQueenSide(false);
            }
        }

        // Handle castling: move the rook
        if (move.getPiece().getType().equals("k")) {
            if (move.getColor().equals(PieceColor.WHITE)) {
                chessClone.whiteKingPosition = move.getTo();
                chessClone.castleRights.setWhiteCanCastleKingSide(false);
                chessClone.castleRights.setWhiteCanCastleQueenSide(false);
            } else {
                chessClone.blackKingPosition = move.getTo();
                chessClone.castleRights.setBlackCanCastleKingSide(false);
                chessClone.castleRights.setBlackCanCastleQueenSide(false);
            }

            if (ChessUtil.isKingSideCastling(move.getFrom(), move.getTo())) {
                if (move.getPiece().getColor().equals(PieceColor.WHITE)) {
                    chessClone.board.set(ChessConstants.DEFAULT_WHITE_ROOK_KS_SQUARE, null);
                    chessClone.board.set(ChessConstants.WHITE_ROOK_KS_EP_SQUARE, new Piece("R"));
                } else {
                    chessClone.board.set(ChessConstants.DEFAULT_BLACK_ROOK_KS_SQUARE, null);
                    chessClone.board.set(ChessConstants.BLACK_ROOK_KS_EP_SQUARE, new Piece("r"));
                }
            } else if (ChessUtil.isQueenSideCastling(move.getFrom(), move.getTo())) {
                if (move.getPiece().getColor().equals(PieceColor.WHITE)) {
                    chessClone.board.set(ChessConstants.DEFAULT_WHITE_ROOK_QS_SQUARE, null);
                    chessClone.board.set(ChessConstants.WHITE_ROOK_QS_EP_SQUARE, new Piece("R"));
                } else {
                    chessClone.board.set(ChessConstants.DEFAULT_BLACK_ROOK_QS_SQUARE, null);
                    chessClone.board.set(ChessConstants.BLACK_ROOK_QS_EP_SQUARE, new Piece("r"));
                }
            }
        }

        // Reset half-move clock for captures or pawn moves
        if (move.getPiece().getType().equals("p") || move.getCaptured() != null) {
            chessClone.halfMove = 0;
        } else {
            chessClone.halfMove++;
        }

        if (move.getPiece().getType().equals("p") && move.getTo() == npSquare) {
            var direction = move.getColor() == PieceColor.WHITE ? 1 : -1;
            var index = ChessUtil.getSquareByOffset(npSquare, direction, 0);
            chessClone.board.set(index, null);
        }

        // Increment full-move count after Black's move
        if (move.getColor() == PieceColor.BLACK) {
            chessClone.fullMove++;
        }

        // Set en passant square for a two-square pawn advance
        if (move.getPiece().getType().equals("p") && Math.abs(move.getTo() - move.getFrom()) == 16) {
            // Check if the move is a two-square pawn advance
            chessClone.npSquare =
                    (move.getFrom() + move.getTo()) / 2; // Set the "hopped over" square
        } else {
            chessClone.npSquare = -1; // Clear en passant square for other moves
        }
        if (chessClone.isCheck()) {
            return false;
        }
        board = chessClone.board;
        npSquare = chessClone.npSquare;
        halfMove = chessClone.halfMove;
        fullMove = chessClone.fullMove;
        castleRights = chessClone.castleRights;
        whiteKingPosition = chessClone.whiteKingPosition;
        blackKingPosition = chessClone.blackKingPosition;
        turn = chessClone.turn.opposite();
        return true;

    }

    /**
     * Builds a move based on the board state and the specified positions. The move is validated by checking
     * that the piece belongs to the current player, and if necessary, handles special cases such as en passant
     * and pawn promotion.
     *
     * If the move is valid, an {@link Optional} containing the created {@link Move} object is returned.
     * If the move is invalid, an empty {@link Optional} is returned.
     *
     * @param board The current state of the chessboard, represented as a list of pieces.
     * @param from The index of the square from which the piece is moving.
     * @param to The index of the square to which the piece is moving.
     * @param promotionPiece The piece to promote to, if the move involves pawn promotion.
     * @return An {@link Optional} containing the constructed {@link Move} object if the move is valid,
     *         or an empty {@link Optional} if the move is invalid.
     */
    private Optional<Move> buildMove(List<Piece> board, Integer from, Integer to, Piece promotionPiece) {
        var piece = board.get(from);
        if (piece == null || !piece.getColor().equals(turn)) return Optional.empty();

        Piece vCapturedPiece = board.get(to);
        Piece vPromotionPiece = promotionPiece;

        if (piece.getSymbol().equalsIgnoreCase("p")) {
            if (to == npSquare) {
                vCapturedPiece = new Piece("p", turn.opposite());
            }
            if (promotionPiece == null && (ChessUtil.rank(to) == ChessConstants.BLACK_RANK_PROMOTION ||
                    ChessUtil.rank(to) == ChessConstants.WHITE_RANK_PROMOTION)) {
                vPromotionPiece = new Piece("q", turn);
            }
        }
        return Optional.of(new Move(turn, from, to, piece, vCapturedPiece, vPromotionPiece));
    }

    @Override
    public String toString() {
        return "Chess{" +
                "board=" + board +
                ", turn=" + turn +
                ", npSquare=" + npSquare +
                ", halfMove=" + halfMove +
                ", fullMove=" + fullMove +
                ", castleRights=" + castleRights +
                ", whiteKingPosition=" + whiteKingPosition +
                ", blackKingPosition=" + blackKingPosition +
                ", gameHistory=" + gameHistory +
                ", san=" + san +
                ", positionHistoryCount=" + positionHistoryCount +
                '}';
    }

}

