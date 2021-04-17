public class TicTacToe {

    private static int Max( Board board, int player, int alpha, int beta, int depth) {
        for (Position move : board.validMoves()) {
            board.doMove(move, player);
            int score = alphaBeta(board, player, alpha, beta, depth-1);
            board.undoMove(move);
            if (score > alpha) {
                alpha = score;
                if (alpha >= beta) break;
            }
        }
        return alpha;

    }
    private static int Min( Board board, int player, int alpha, int beta, int depth) {
        for (Position move : board.validMoves()) {
            board.doMove(move, -player);
            int score = alphaBeta(board, player, alpha, beta, depth-1);
            board.undoMove(move);
            if (score < beta) {
                beta = score;
                if (alpha >= beta) break;
            }
        }
        return beta;

    }

    public static int alphaBeta(Board board, int player, int alpha, int beta,int depth) {
            if (board.isGameWon()) {
                if (player != board.onTurn) {
                    return board.nFreeFields() + 1;
                } else return -board.nFreeFields() - 1;
            }
            else if (depth == 0) return 0;

        if (board.onTurn == player) {
            return Max(board, player, alpha, beta, depth );
        } else {
            return Min(board, player, alpha, beta, depth );
        }
    }


    public static int alphaBeta(Board board, int player)
    {
        board.maxMoves = board.nFreeFields();
        return alphaBeta(board, player, -Integer.MAX_VALUE, Integer.MAX_VALUE,board.maxMoves);
    }


    public static void evaluatePossibleMoves(Board board, int player)
    {
        if (player == 1) {
            System.out.println("Evaluation for player ’x’:");
            for (int i = 0; i < board.getN(); i++) {
                for (int j = 0; j < board.getN(); j++) {
                    Position pos = new Position(j,i);
                    if (board.board[j][i] == 0) {
                        board.doMove(pos, 1);
                        int n =alphaBeta(board, 1);
                        if (n >= 0) System.out.print("  " + n);
                        else System.out.print(" " + n);
                        board.undoMove(pos);
                    }
                    if (board.board[j][i] == 1)
                        System.out.print("  x");
                    if (board.board[j][i] == -1)
                        System.out.print("  o");
                }
                System.out.println("");
            }
        }

        if (player == -1) {
            System.out.println("Evaluation for player ’o’:");
            for (int i = 0; i < board.getN(); i++) {
                for (int j = 0; j < board.getN(); j++) {
                    Position pos = new Position(j,i);
                    if (board.board[j][i] == 0) {
                        board.doMove(pos, -1);
                        int m =alphaBeta(board,-1);
                        if (m >= 0) System.out.print("  " + m );
                        else System.out.print(  " " + m);
                        board.undoMove(pos);
                    }
                    if (board.board[j][i] == 1)
                        System.out.print("  x");
                    if (board.board[j][i] == -1)
                        System.out.print("  o");
                }
                System.out.println("");
            }
        }
    }

    public static void main(String[] args)
    {
    }
}

