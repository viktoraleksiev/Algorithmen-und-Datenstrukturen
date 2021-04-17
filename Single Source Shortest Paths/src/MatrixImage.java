import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MatrixImage implements Image {
    public int[][] field;

    public MatrixImage(int sx, int sy) {
        field = new int[sx][sy];
    }

    // copy constructor
    public MatrixImage(MatrixImage that) {
        this(that.sizeX(), that.sizeY());
        for (int x = 0; x < sizeX(); x++) {
            field[x] = that.field[x].clone();
        }
    }

    public MatrixImage(String filename) throws java.io.FileNotFoundException {
        System.setIn(new FileInputStream(filename));
        Scanner in = new Scanner(System.in);
        int sx = in.nextInt();
        int sy = in.nextInt();
        field = new int[sx][sy];
        for (int y = 0; y < sy; y++) {
            for (int x = 0; x < sx; x++) {
                field[x][y] = in.nextInt();
            }
        }
    }

    @Override
    public int sizeX() {
        return field.length;
    }

    @Override
    public int sizeY() {
        return field[0].length;
    }

    @Override
    public double contrast(Coordinate p0, Coordinate p1) throws InputMismatchException {

        if (p0.x < 0 || p0.x > field.length - 1 || p0.y < 0 || p0.y > field[0].length - 1 || p1.x < 0 || p1.x > field.length - 1 || p1.y < 0 || p1.y > field[0].length - 1){
            throw new InputMismatchException();
        }
        return Math.abs(field[p0.x][p0.y] - field[p1.x][p1.y]);
    }

    @Override
    public void removeVPath(int[] path) {
        int[][] temp = new int[field.length-1][field[0].length];
        int x=0;
        int y=0;
        for (int i=0;i <field[0].length;i++){
            for( int j = 0; j < field.length;j++) {
                if (!(j==path[i])){
                    temp[x][y] = field[j][i];
                    x++;
                }
            }
            x=0;
            y++;
        }
        field = temp;
    }

    @Override
    public String toString() {
        String str = "";
        for (int y = 0; y < sizeY(); y++) {
            for (int x = 0; x < sizeX(); x++) {
                str += field[x][y] + " ";
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public void render() {
        System.out.println(toString());
    }

}

