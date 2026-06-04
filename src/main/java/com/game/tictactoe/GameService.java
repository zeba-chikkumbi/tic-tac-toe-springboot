package com.game.tictactoe;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class GameService {

    private final String[][] board = new String[3][3];

    private String message = "Your Turn (X)";

    private int playerWins = 0;
    private int systemWins = 0;
    private int draws = 0;

    public String[][] getBoard() {
        return board;
    }

    public String getMessage() {
        return message;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getSystemWins() {
        return systemWins;
    }

    public int getDraws() {
        return draws;
    }

    public void playMove(int row, int col) {

        if (board[row][col] != null) {
            message = "Cell already occupied!";
            return;
        }

        // Player move
        board[row][col] = "X";

        if (checkWinner("X")) {
            playerWins++;
            message = "🎉 You Win!";
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            draws++;
            message = "🤝 Match Draw!";
            resetBoard();
            return;
        }

        // System move
        systemMove();

        if (checkWinner("O")) {
            systemWins++;
            message = "🤖 System Wins!";
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            draws++;
            message = "🤝 Match Draw!";
            resetBoard();
            return;
        }

        message = "Your Turn (X)";
    }

    private void systemMove() {

        // 1. Try to win
        int[] winningMove = findWinningMove("O");
        if (winningMove != null) {
            board[winningMove[0]][winningMove[1]] = "O";
            return;
        }

        // 2. Block player win
        int[] blockingMove = findWinningMove("X");
        if (blockingMove != null) {
            board[blockingMove[0]][blockingMove[1]] = "O";
            return;
        }

        // 3. Take center
        if (board[1][1] == null) {
            board[1][1] = "O";
            return;
        }

        // 4. Take corners
        int[][] corners = {
                {0, 0},
                {0, 2},
                {2, 0},
                {2, 2}
        };

        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == null) {
                board[corner[0]][corner[1]] = "O";
                return;
            }
        }

        // 5. Take any remaining cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = "O";
                    return;
                }
            }
        }
    }

    private int[] findWinningMove(String symbol) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == null) {

                    board[i][j] = symbol;

                    boolean win = checkWinner(symbol);

                    board[i][j] = null;

                    if (win) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        return null;
    }

    private boolean checkWinner(String symbol) {

        // Rows
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[i][0]) &&
                symbol.equals(board[i][1]) &&
                symbol.equals(board[i][2])) {
                return true;
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[0][i]) &&
                symbol.equals(board[1][i]) &&
                symbol.equals(board[2][i])) {
                return true;
            }
        }

        // Main diagonal
        if (symbol.equals(board[0][0]) &&
            symbol.equals(board[1][1]) &&
            symbol.equals(board[2][2])) {
            return true;
        }

        // Anti-diagonal
        return symbol.equals(board[0][2]) &&
               symbol.equals(board[1][1]) &&
               symbol.equals(board[2][0]);
    }

    private boolean isBoardFull() {

        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public void resetBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
    }

    public void resetScores() {
        playerWins = 0;
        systemWins = 0;
        draws = 0;
        message = "Your Turn (X)";
        resetBoard();
    }
}