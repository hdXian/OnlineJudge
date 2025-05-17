import java.io.*;
import java.math.*;
import java.util.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static final int P = 10007;
    static int[] table;

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());
        table = new int[N+1]; // 인덱스 1부터 쓰기
    }

    static int calc() {
        if (N == 1) return 1;

        table[1] = 1;
        table[2] = 3;
        for(int i=3; i<=N; i++) {
            table[i] = (table[i-1] + table[i-2]*2) % P;
        }
        return table[N];
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
