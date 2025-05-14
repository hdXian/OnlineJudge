import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N; // n의 피보나치 수
    static final long P = 1000000007;

    static void calc() {
        // 재귀호출
        // (a + b) mod x = (a mod x + b mod x) mod x
        // 재귀호출 실행횟수
        long recur_result = 0;

        long pre1 = 1;
        long pre2 = 1;
        for(int i=3; i<=N; i++) {
            recur_result = (pre1 + pre2) % P;
            pre2 = pre1;
            pre1 = recur_result;
        }

        // dp 호출횟수
        long dp_result = N-2;
        System.out.println(recur_result + " " + dp_result);
    }

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine()); // n의 피보나치 수 (5 ~ 2억)
        calc();
    }

}
