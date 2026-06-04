package com.game.tictactoe;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final String[][] board = new String[3][3];
    private final Random random = new Random();

    private String message = "Your Turn (X)";

    public String[][] getBoard() {
        return board;
    }

    public String getMessage() {
        return message;
    }

    public void playMove(int row, int col) {

        // User move
        if (board[row][col] != null) {
            message = "Cell already occupied";
            return;
        }

        board[row][col] = "X";

        if (checkWinner("X")) {
        message = "🎉 Congratulations! You Win! 🎉";
        resetBoard();
        return;
    }

        if (isBoardFull()) {
            message = "Match Draw!";
            resetBoard();
            return;
        }

        // Computer move
        while (true) {
            int r = random.nextInt(3);
            int c = random.nextInt(3);

            if (board[r][c] == null) {
                board[r][c] = "O";
                break;
            }
        }

        if (checkWinner("O")) {
            message = "🤖 Computer Wins!";
            resetBoard();
            return;
        }
        if (isBoardFull()) {
            message = "Match Draw!";
            resetBoard();
            return;
        }

        message = "Your Turn (X)";
    }

        private boolean checkWinner(String player) {

        for (int i = 0; i < 3; i++) {

            // Rows
            if (player.equals(board[i][0]) &&
                player.equals(board[i][1]) &&
                player.equals(board[i][2])) {
                return true;
            }

            // Columns
            if (player.equals(board[0][i]) &&
                player.equals(board[1][i]) &&
                player.equals(board[2][i])) {
                return true;
            }
        }

        // Diagonal
        if (player.equals(board[0][0]) &&
            player.equals(board[1][1]) &&
            player.equals(board[2][2])) {
            return true;
        }

        // Reverse Diagonal
        if (player.equals(board[0][2]) &&
            player.equals(board[1][1]) &&
            player.equals(board[2][0])) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {

        for (String[] rows : board) {
            for (String cell : rows) {
                if (cell == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private void resetBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
    }
}