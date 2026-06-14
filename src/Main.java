import java.util.Scanner;

class ConnectR {

    private char[][] board;
    private int width;
    private int height;
    private int connect;

    // Constructor
    public ConnectR(int width, int height, int connect) {
        this.width = width;
        this.height = height;
        this.connect = connect;
        board = new char[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                board[row][col] = '.'; // Empty space
            }
        }
    }

    // Copy constructor
    public ConnectR(ConnectR game) {
        this.width = game.width;
        this.height = game.height;
        this.connect = game.connect;
        this.board = new char[height][width];
        for (int row = 0; row < height; row++) {
            System.arraycopy(game.board[row], 0, this.board[row], 0, width);//copy the game state
        }
    }

    //checking to see if the move is valid
    public boolean isValidMove(int col) {
        return col >= 0 && col < width && board[0][col] == '.';
    }

    public ConnectR makeMove(int col, char player) {
        for (int row = height - 1; row >= 0; row--) {
            if (board[row][col] == '.') {
                board[row][col] = player;
                break;
            }
        }
        return this;
    }

    public boolean isBoardFull() {
        for (int col = 0; col < width; col++) {
            if (board[0][col] == '.') {
                return false;
            }
        }
        return true;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, char player) {
        int count = 0;
        for (int i = 0; i < connect; i++) {
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && board[newRow][newCol] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == connect;
    }

    public boolean isWin(char player) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (checkDirection(row, col, 1, 0, player) || // Check Vertical
                        checkDirection(row, col, 0, 1, player) || // Check Horizontal
                        checkDirection(row, col, 1, 1, player) || // Check Diagonal down-right
                        checkDirection(row, col, 1, -1, player)) { // Check Diagonal down-left
                    return true;
                }
            }
        }
        return false;
    }

    public int getColumns() {
        return width;
    }

    //prints board
    public void printBoard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}

class ConnectAI {

    private static final int MAX_DEPTH = 6; // Depth limit to avoid long computation

    // Get the best move for the AI using the Min-Max algorithm
    public int getBestMove(ConnectR game, char aiPlayer) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < game.getColumns(); col++) {
            if (game.isValidMove(col)) {
                ConnectR gameCopy = new ConnectR(game);
                gameCopy.makeMove(col, aiPlayer);
                int score = minMax(gameCopy, false, aiPlayer, 0);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }

        return bestMove;
    }

    // Min-Max algorithm with depth limit
    private int minMax(ConnectR game, boolean isMaximizing, char aiPlayer, int depth) {
        char humanPlayer = (aiPlayer == 'R') ? 'B' : 'R';

        if (game.isWin(aiPlayer)) return 100;
        if (game.isWin(humanPlayer)) return -100;
        if (game.isBoardFull() || depth >= MAX_DEPTH) return 0; // Stopping condition

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int col = 0; col < game.getColumns(); col++) {
                if (game.isValidMove(col)) {
                    ConnectR gameCopy = new ConnectR(game);
                    gameCopy.makeMove(col, aiPlayer);
                    int score = minMax(gameCopy, false, aiPlayer, depth + 1);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int worstScore = Integer.MAX_VALUE;
            for (int col = 0; col < game.getColumns(); col++) {
                if (game.isValidMove(col)) {
                    ConnectR gameCopy = new ConnectR(game);
                    gameCopy.makeMove(col, humanPlayer);
                    int score = minMax(gameCopy, true, aiPlayer, depth + 1);
                    worstScore = Math.min(score, worstScore);
                }
            }
            return worstScore;
        }
    }
}

public class Main {

    static char PLAYER_RED = 'R';
    static char PLAYER_BLACK = 'B';

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Get game parameters from the user
        System.out.print("Enter board width (columns): ");
        int width = scan.nextInt();
        System.out.print("Enter board height (rows): ");
        int height = scan.nextInt();
        System.out.print("Enter how many in a row to win (R): ");
        int connect = scan.nextInt();

        //creates a new game board
        ConnectR game = new ConnectR(width, height, connect);

        //added touch so the user can pick its player preference
        System.out.print("Do you want to play as RED or BLACK? (R/B): ");
        char humanPlayer = scan.next().toUpperCase().charAt(0);
        char aiPlayer = (humanPlayer == PLAYER_RED) ? PLAYER_BLACK : PLAYER_RED;

        //game loop
        boolean gameOver = false;
        char currentPlayer = PLAYER_RED; // red plyer always goes first every game
        ConnectAI ai = new ConnectAI();

        while (!gameOver) {

            game.printBoard();
            //Human player turn
            if (currentPlayer == humanPlayer) {
                System.out.print("Your move! Enter column (0 to " + (width-1) + "): ");
                int column = scan.nextInt();
                if (game.isValidMove(column)) {
                    game.makeMove(column, humanPlayer);
                    if (game.isWin(humanPlayer)) {
                        game.printBoard();
                        System.out.println("Congratulations! You win!");
                        gameOver = true;
                    }
                }
                else {
                    System.out.println("Invalid move. Try again.");
                }
            }

            else {

                //AI player turn
                System.out.println("AI is thinking...");
                int aiMove = ai.getBestMove(game, aiPlayer);
                game.makeMove(aiMove, aiPlayer);
                System.out.println("AI moves to column " + aiMove);
                if (game.isWin(aiPlayer)) {
                    game.printBoard();
                    System.out.println("AI wins!");
                    gameOver = true;
                }
            }

            //this checks to see if game is a tie
            if (game.isBoardFull() && !gameOver) {
                game.printBoard();
                System.out.println("It's a tie!");
                gameOver = true;
            }

            currentPlayer = (currentPlayer == PLAYER_RED) ? PLAYER_BLACK : PLAYER_RED;
        }

        scan.close();//ends game
    }
}
