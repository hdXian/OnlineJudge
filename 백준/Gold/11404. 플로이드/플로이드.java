import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int INF;
    public static int N, M;
    public static int[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 도시 개수
        M = Integer.parseInt(reader.readLine()); // 간선 개수
        
        INF = (100000 * N) + 9999;

        // 테이블 초기화
        dp = new int[N+1][N+1];
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                dp[i][j] = (i == j) ? 0 : INF;
            }
        }

        StringTokenizer tkn;
        int src, dst, cost;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            cost = Integer.parseInt(tkn.nextToken());
            dp[src][dst] = Math.min(dp[src][dst], cost);
        }

        // N개의 도시에 대해 반복문을 돌린다.
        for(int i=1; i<=N; i++) {

            // i번 도시에 대해 반복문을 돌린다.
            for(int row=1; row<=N; row++) {

                // Min( cost(a, b), cost(a, i) + cost(i, b) )를 테이블에 저장.
                // a -> b로 바로 가는 것이 빠른지, (a -> i), (i -> b)로 가는 것이 비용이 더 적게 드는지 판단.
                // 다른 노드를 여러개 건너는 경우는 어떻게 하느냐? dp라서 그것도 다 고려됨. (a -> i)로 가는 비용도 또 다시 (a -> k -> i)로 가는 다른 경우들도 계산돼 있는거임.
                // 즉 (a -> i -> b) => (a -> (k -> i -> s) -> b) 이런 식으로 중간 경로도 함께 계산되는 방식.
                for(int col=1; col<=N; col++) {
                    dp[row][col] = Math.min(dp[row][col], (dp[row][i] + dp[i][col]) );
                }

            }

        }

        printDp();

    }

    private static void printDp() {
        int val;
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                val = (dp[i][j] == INF) ? 0 : dp[i][j];
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }


}
