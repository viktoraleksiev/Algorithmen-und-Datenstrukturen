import java.util.InputMismatchException;
import java.util.Stack;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class Board {
    private int n;
    public int[][] board;
    public int free = 0;
    public int maxMoves=0;
    public int onTurn = 1;

    public Board(int n)
    {
        if( n<1 || n>10){
            throw new  InputMismatchException();
        }
        this.n = n;
        this.free = n*n;
        this.board = new int[n][n];
    }
    
    
    public int getN() { return n; }

    public int nFreeFields() {
        return this.free;
    }

    public int getField(Position pos) throws InputMismatchException
    {
        if ( this.board[pos.x][pos.y] == 0 ||this.board[pos.x][pos.y] == 1 || this.board[pos.x][pos.y] == -1 ) return this.board[pos.x][pos.y];
        else  throw new  InputMismatchException();
    }

    public void setField(Position pos, int token) throws InputMismatchException
    {
        if (token == 0||token == 1||token== -1) {
            if (token == 0 && this.board[pos.x][pos.y] != 0) {
                free++;
                this.board[pos.x][pos.y] = token;
            }
            if (token == 1 && this.board[pos.x][pos.y] == 0) {
                free--;
                this.board[pos.x][pos.y] = token;
            }
            if (token == 1 && this.board[pos.x][pos.y] == -1) {
                this.board[pos.x][pos.y] = token;
            }
            if (token == -1 && this.board[pos.x][pos.y] == 0) {
                free--;
                this.board[pos.x][pos.y] = token;
            }
            if (token == -1 && this.board[pos.x][pos.y] == 1) {
                this.board[pos.x][pos.y] = token;
            }
        }
        else throw new  InputMismatchException();
    }

    public void doMove(Position pos, int player)
    {
        if ( player == 1 || player == -1) {
            if (player == 1) this.onTurn = -1;
            if (player == -1) this.onTurn =1;
            setField(pos, player);
        }
    }

    public void undoMove(Position pos)
    {
        if ( board[pos.x][pos.y] != 0) {
            if (board[pos.x][pos.y] == 1)this.onTurn = -1;
            if (board[pos.x][pos.y] == -1)this.onTurn = 1;
            setField(pos,0);
        }
    }

    public boolean isGameWon() {
        boolean won;

        // Spalten
        for (int i = 0;i<n;i++){
            won = true;
            for(int j =0;j<(n-1);j++){
                if( (this.board[i][j] != this.board[i][j+1]) || this.board[i][j] == 0) won = false;
            }
            if (won) return true;
        }
        // Zeilen
        for (int i = 0;i<n;i++){
            won = true;
            for(int j =0;j<(n-1);j++){
                if( (this.board[j][i] != this.board[j+1][i]) || this.board[j][i] == 0) won = false;
            }
            if (won) return true;
        }
        // Diagonal 1
        won =true;
        for (int i = 0; i < n -1;i++ ){
            if ( (board[i][i] != board[i+1][i+1]) || board[i][i] == 0) won = false;
        }
        if (won) return true;
        // Diagonal 2
        won =true;
        int n2= this.n - 1;
        int i =0;
        while(i < n && n2 >= 1) {
            if (board[i][n2] != board[i + 1][n2 - 1] || board[i][n2] == 0) won = false;
            n2 = n2 - 1;
            i++;
        }
        if (won) return true;

        return false;
    }

    public Iterable<Position> validMoves()
    {
        LinkedList<Position> valid = new LinkedList<>();
        for (int i =0; i < this.getN();i++ ){
            for(int j =0;j < this.getN(); j++){
                if (this.board[j][i] == 0 ){
                    Position pos = new Position(j,i);
                    valid.add(pos);
                }
            }
        }

        return valid;
    }

    public void print()
    {
        for (int i =0; i < this.getN();i++ ){
            for(int j =0;j < this.getN(); j++){
                System.out.print(this.board[j][i] + " ");
            }
            System.out.println(" ");
        }
    }

}

