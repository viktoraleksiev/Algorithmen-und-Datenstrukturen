import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Genomics {
    private long count;
    private long[] c;

    public static long optBottomUp(String strang, String[] dictionary){

        Genomics p1 = new Genomics();
        p1.count = 0;
        p1.c = new long[strang.length()];
        for (int i = strang.length()-1; i >= 0; i--){
            for(int j =0;j<dictionary.length; j++){
                if (!(dictionary[j].length() + i > strang.length())) {
                    if (strang.startsWith(dictionary[j], i)) {
                        if (dictionary[j].length() + i == strang.length()) p1.c[i]++;
                        else p1.c[i] = p1.c[i] + p1.c[i + dictionary[j].length()];
                    }
                }
            }

        }
        return p1.c[0];
    }

    public static void main(String[] args)
    {
        // for testing
    }
}

