import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N;
    public static Node[] nodes;
    public static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 정점 개수
        nodes = new Node[N+1];
        for(int i=1; i<=N; i++)
            nodes[i] = new Node();

        dp = new int[N+1][2]; // 0은 해당 노드가 일반인이 아닐 때, 1은 해당 노드가 일반인일 때
        for(int i=0; i<2; i++)
            Arrays.fill(dp[i], 0);

        StringTokenizer tkn;
        int u, v;
        for (int i=0; i<N-1; i++) {
            tkn = new StringTokenizer(reader.readLine());
            u = Integer.parseInt(tkn.nextToken());
            v = Integer.parseInt(tkn.nextToken());
            nodes[u].neighbor.add(v);
            nodes[v].neighbor.add(u);
        }

        dfs(1, 0);

        int maxNormal = Math.max(dp[1][1], dp[1][0]);

        System.out.println(N - maxNormal);
    }

    // 일반인을 최대로
    public static void dfs(int n, int before) {
        Node cur = nodes[n];

        for (int neighbor: cur.neighbor) {
            if (neighbor != before) {
                dfs(neighbor, n);
                // 1. 현재 노드가 일반인일 때 -> 주변 노드가 모두 얼리어답터여야 한다.
                dp[n][1] += dp[neighbor][0];
                // 2. 현재 노드가 일반인이 아닐 때 -> 주변 노드는 일반인이어도 되고, 아니어도 된다.
                dp[n][0] += Math.max(dp[neighbor][1], dp[neighbor][0]);
            }
        }
        dp[n][1] += 1; // 현재 노드가 일반인인 경우에 자신을 추가
    }

    static class Node {
        public List<Integer> neighbor = new ArrayList<>();
    }

}
