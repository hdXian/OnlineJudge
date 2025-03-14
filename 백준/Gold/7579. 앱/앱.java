import java.util.*;
import java.io.*;
import java.math.*;

// 풀이 2 - 비용을 기준으로 테이블 구성
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M;
    public static int[] mem;
    public static int[] cost;

    public static int[][] table;

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 메모리 상 어플 개수
        M = Integer.parseInt(tkn.nextToken()); // 확보해야 할 메모리 공간

        mem = new int[N+1];
        mem[0] = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<N+1; i++) {
            mem[i] = Integer.parseInt(tkn.nextToken());
        }

        cost = new int[N+1];
        cost[0] = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<N+1; i++) {
            cost[i] = Integer.parseInt(tkn.nextToken());
        }

        table = new int[N+1][10001];
        for(int i=0; i<N+1; i++) Arrays.fill(table[i], 0);
    }

    public static void main(String[] args) throws Exception {
        init();

        calc();

        int result = -1;
        // M 이상의 메모리를 담게 되는 최초의 k가 정답
        for(int n=1; n<N+1; n++) {
            for(int k=0; k<10001; k++) {
                if (table[n][k] >= M) {
                    result = k;
                    break;
                }
            }
        }

        System.out.println(result);
    }

    // 최대 k만큼의 비용을 수용할 수 있는 선택지에 메모리를 최대한 담는다.
    public static void calc() {

        int res;
        for (int n=1; n<N+1; n++) {

            for(int k=0; k<10001; k++) {
                res = k - cost[n]; // res는 n번 프로그램을 담고 남는 비용

                // n번 프로그램을 담고 비용이 남는다면 (해당 프로그램을 담을 수 있다면)
                if (res >= 0) {
                    // 1. (n번 프로그램 메모리) + (n을 제외한 프로그램 들 중에서 res 비용을 구성하는 최대 메모리 양)
                    int c1 = mem[n] + table[n-1][res];

                    // 2. n번 프로그램을 제외하고 k 만큼의 비용을 구성하는 최대 메모리 합
                    int c2 = table[n-1][k];

                    table[n][k] = Math.max(c1, c2);
                }
                // n번 프로그램을 담을 수 없다면 (비용 초과)
                else {
                    // n번 프로그램을 제외하고 k 비용을 구성하는 기존 메모리 합을 가져와 저장
                    table[n][k] = table[n-1][k];
                }

            }

        }

    }

}
