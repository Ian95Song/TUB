import java.util.InputMismatchException;
import java.util.Stack;

import static java.lang.Math.abs;

/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    int[][] chessboard;
    int numfree;
    int winner;

    /**
     * Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n) {
        // TODO
        int [] candidates = {1,2,3,4,5,6,7,8,9,10};
        boolean nvalid = false;
        for (int i:candidates){
            if (i == n) {
                nvalid = true;
                break;
            }
        }

        if (nvalid){
            this.chessboard = new int[n][n];
            this.n = n;
            this.numfree = n*n;
            this.winner = 0;
        }
        else{
            throw new InputMismatchException("Dimension is not valid!");
        }

    }

    /**
     * @return length/width of the Board object
     */
    public int getN() {
        return n;
    }

    /**
     * @return number of currently free fields
     */
    public int nFreeFields() {
        // TODO
        return numfree;

    }

    /**
     * @return token at position pos
     */
    public int getField(Position pos) throws InputMismatchException {
        // TODO
        boolean xvalid = false;
        boolean yvalid = false;
        // check pos
        for (int i =0; i<n;i++){
            if (pos.x==i) xvalid = true;
            if (pos.y==i) yvalid = true;
        }
        // when pos valid, setField
        if (xvalid&&yvalid) {
            return chessboard[pos.x][pos.y];
        } else throw new InputMismatchException("input is not valid!");

    }

    /**
     * Sets the specified token at Position pos.
     */
    public void setField(Position pos, int token) throws InputMismatchException {
        // TODO
        boolean xvalid = false;
        boolean yvalid = false;
        boolean tokenvalid = false;
//      check pos
        for (int i =0; i<n;i++){
            if (pos.x==i) xvalid = true;
            if (pos.y==i) yvalid = true;
        }
//       check token
        int [] canditoken = {-1,0,1};
        for (int i:canditoken){
            if (i == token) {
                tokenvalid = true;
                break;
            }
        }
//        when input valid, setField
        if (xvalid&&yvalid&&tokenvalid) {
            chessboard[pos.x][pos.y]=token;
            if (token==0){
                numfree++;
            }
            else numfree--;
        } else throw new InputMismatchException("input is not valid!");

    }

    /**
     * Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player) {
        // TODO
        setField(pos,player);
    }

    /**
     * Clears board at Position pos.
     */
    public void undoMove(Position pos) {
        // TODO
        setField(pos,0);
    }

    /**
     * @return true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
        boolean gamewin = false;
        //check cols
        for (int i=0;i<n;i++){
            int colwin =1;
            for (int j=1;j<n;j++){
                if (chessboard[i][0]!=0&&chessboard[i][j] == chessboard[i][0]){
                    colwin++;
                }
            }
            if (colwin==n){
                gamewin =  true;
                winner = chessboard[i][0];
                break;
            }
        }
        //check rows if not win
        if (!gamewin){
            for (int i=0;i<n;i++){
                int rowwin =1;
                for (int j=1;j<n;j++){
                    if (chessboard[0][i]!=0&&chessboard[j][i] == chessboard[0][i]){
                        rowwin++;
                    }
                }
                if (rowwin==n){
                    gamewin = true;
                    winner = chessboard[0][i];
                    break;
                }
            }
        }
        //check diagonal1 if not win
        if (!gamewin){
            int diagwin1 = 1;
            for (int i =1;i<n;i++){
                if (chessboard[0][0]!=0&&chessboard[0][0]==chessboard[i][i]){
                    diagwin1++;
                }
            }
            if (diagwin1==n){
                gamewin = true;
                winner = chessboard[0][0];
            }
        }
        //check diagonal2 if not win
        if (!gamewin){
            int diagwin2 = 1;
            for (int i =1;i<n;i++){
                if (chessboard[0][n-1]!=0&&chessboard[0][n-1]==chessboard[i][n-1-i]){
                    diagwin2++;
                }
            }
            if (diagwin2==n){
                gamewin = true;
                winner = chessboard[0][n-1];
            }
        }

        return gamewin;

    }

    /**
     * @return set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves() {
        // TODO
        Stack<Position> freep= new Stack<Position>();
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                 if(chessboard[i][j]==0){
                     Position fp = new Position(i,j);
                     freep.push(fp);
                 }
            }
        }
        return freep;
    }

    /**
     * Outputs current state representation of the Board object.
     * Practical for debugging.
     */
    public void print() {
        // TODO
        for (int i=0; i<n;i++){
            for (int j=0;j<n;j++){
                System.out.print("| "+(chessboard[j][i]==0?" ":chessboard[j][i]==1?"X":"O")+" ");
                if (j==n-1){
                    System.out.println("|");
                }
            }
        }
    }

//    public static void main(String[] args) {
//        Board game = new Board(3);
//        game.doMove(new Position(1,1),1);
//        game.doMove(new Position(0,0),-1);
//        game.doMove(new Position(1,0),1);
//        game.doMove(new Position(2,2),-1);
//        game.doMove(new Position(1,2),1);
//        game.print();
//        System.out.println(game.nFreeFields());
//        System.out.println(game.validMoves());
//        System.out.println(game.isGameWon());
//        System.out.println(game.winner);
//    }

}

