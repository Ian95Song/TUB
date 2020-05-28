/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board  current Board object for game situation
     * @param player player who has a turn
     * @return rating of game situation from player's point of view
     **/
    public static int alphaBeta(Board board, int player) {
        // TODO
        int alpha = -1000;
        int beta = 1000;
//        return scorePlayer(board,player,-Integer.MAX_VALUE,Integer.MAX_VALUE,board.nFreeFields());
        if (player==1){
            return scorePlayerA(board,alpha,beta);
        }
        else{
            return scorePlayerB(board,alpha,beta);
        }
    }

//        public static int scorePlayer(Board board, int player, int alpha, int beta, int depth) {
//        // TODO
//        if (board.isGameWon()||board.nFreeFields()==0||depth==0){
//            return player*(1+board.nFreeFields());
//        }
//        for (Position p: board.validMoves()){
//            board.doMove(p,player);
//            int score = scorePlayer(board,-player,-beta,-alpha,depth-1);
//            board.undoMove(p);
//            if (score>alpha) {
//                alpha = score;
//                if (alpha>=beta) break;
//            }
//        }
//        return alpha;
//    }


    public static int scorePlayerA(Board board, int alpha, int beta) {
        // TODO

        if (board.isGameWon()){
            return  board.winner*(1+board.nFreeFields());
        }
        if (board.nFreeFields()==0){
            return 0;
        }
        for (Position p: board.validMoves()){
            board.doMove(p,1);
            int score = scorePlayerB(board,alpha,beta);
            board.undoMove(p);
            if (score>alpha) {
                alpha = score;
                if (alpha>=beta) break;
            }
        }
        return alpha;
    }

    public static int scorePlayerB(Board board, int alpha, int beta) {
        // TODO

        if (board.isGameWon()){
            return board.winner*(1+board.nFreeFields());
        }
        if (board.nFreeFields()==0){
            return 0;
        }
        for (Position p: board.validMoves()){
            board.doMove(p,-1);
            int score = scorePlayerA(board,alpha,beta);
            board.undoMove(p);
            if (score<beta) {
                beta = score;
                if (beta<=alpha) break;
            }
        }
        return beta;
    }


    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board  current Board object for game situation
     * @param player player who has a turn
     **/
    public static void evaluatePossibleMoves(Board board, int player) {
        // TODO
        String[][] finalboard = new String[board.getN()][board.getN()];
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getN(); j++) {
                if (board.chessboard[i][j] == 1) {
                    finalboard[i][j] = "x";
                }
                else if (board.chessboard[i][j] == -1){
                    finalboard[i][j] = "o";
                }
            }
        }

        for (Position p : board.validMoves()) {
            board.doMove(p, player);
            int result = alphaBeta(board, -player);
            finalboard[p.x][p.y] = String.valueOf(result);
            board.undoMove(p);
        }

        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getN(); j++) {
                System.out.print(finalboard[j][i] + " ");
                if (j == board.getN() - 1) {
                    System.out.println();
                }
            }
        }
    }





    public static void main(String[] args) {
        Board game = new Board(3);
        game.doMove(new Position(2, 2), 1);
//        game.doMove(new Position(0, 2), 1);
//        game.doMove(new Position(0, 0), 1);
//        game.doMove(new Position(2, 2), 1);
        game.doMove(new Position(2, 0), -1);
//        game.doMove(new Position(1, 2), -1);
//        game.doMove(new Position(1, 1), -1);
//        game.doMove(new Position(0,2),-1);
//        game.print();
        evaluatePossibleMoves(game,1);
//        int a = alphaBeta(game, -1);
//        System.out.println(a);

    }


}