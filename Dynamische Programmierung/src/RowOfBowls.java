import java.util.LinkedList;
import java.util.List;

public class RowOfBowls {
    private List reverseOrder;
    private int Matrix[][];
    private int n;


    public RowOfBowls() {}

    public static int MAX(int x, int y){
        if (x >= y) return x;
        else return y;
    }
    public static int MIN(int x, int y){
        if (x<=y) return x;
        else return y;
    }

    /* The main diagonal represents the given sequence.
    *
    * For sequence is 3,4,1,2,8,5 and X is a random position above the diagonal
    *    3  1  0  X  6  1
    *    0  4  3  3  5  2
    *    0  0  1  1  7  2
    *    0  0  0  2  6 -1
    *    0  0  0  0  8  3
    *    0  0  0  0  0  5
    *   X = 2 represents the best case for the starting player of the interval from 3 to 2 (3,4,1,2) of the main sequence.
    *   The solution is in the upper right corner.
    */

    public int maxGain(int[] values){
        this.Matrix = new int[values.length][values.length];
        this.n = values.length;

        for (int i =0; i < values.length;i++){
            Matrix[i][i] = values[i];
        }

        int l;
        for (int i =1; i<values.length;i++){
            for (int j = 0; j< values.length - i;j++){
                    l = i + j;
                    Matrix[l][j] = MAX(values[j] - Matrix[l][j+1], values[l] - Matrix[l-1][j]) ;
            }
        }

        return Matrix[values.length - 1][0];
    }

    public int maxGainRecursive(int[] values) 
    {
        this.reverseOrder = new LinkedList();
        return maxGainRecursive(values,0,values.length -1,1);
    }

    public int maxGainRecursive(int[] values,int i, int j,int turn){
            // 2 Elements left
            if (i+1==j){
                if (values[i]>=values[j]){
                    return values[i] - values[j];
                }
                else{
                    return values[j] - values[i];
                }
            }
            else if (turn == 1) {
                return  MAX(maxGainRecursive(values,i+1,j,-1) + values[i],maxGainRecursive(values,i,j-1,-1) + values[j]);
            }
            else return MIN(maxGainRecursive(values,i+1,j,1) - values[i],maxGainRecursive(values,i,j-1,1) - values[j]);
    }

    public Iterable<Integer> optimalSequence()
    {
	this.reverseOrder = new LinkedList();

        int i = this.n - 1;
        int j = 0;

        for (int k =0; k < this.n - 1; k++){
            if (this.Matrix[j][j] - this.Matrix[i][j+1] >= this.Matrix[i][i] - this.Matrix[i-1][j]) {
                reverseOrder.add(j);
                j++;
            }
            else {
                reverseOrder.add(i);
                i--;
            }
        }
        reverseOrder.add(j);




        return this.reverseOrder;
    }


    public static void main(String[] args)
    {
        // for testing
    }
}

