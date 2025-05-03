import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M, X;

    static int[][] graph;
    static final int INF = 100001; // 길 비용의 최대는 1000 * 100

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 마을 개수 (1 ~ 1천)
        M = Integer.parseInt(tkn.nextToken()); // 길 개수 (1 ~ 1만)
        X = Integer.parseInt(tkn.nextToken()); // 모일 곳 (1 ~ N)

        graph = new int[N+1][N+1]; // 인덱스 1부터 쓰기
        for(int[] line: graph) Arrays.fill(line, INF);
        for(int i=1; i<=N; i++) graph[i][i] = 0;

        int s, r, t;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            s = Integer.parseInt(tkn.nextToken());
            r = Integer.parseInt(tkn.nextToken());
            t = Integer.parseInt(tkn.nextToken());
            graph[s][r] = t;
            // graph[r][s] = t;
        }

    }

    static void floyd() {
        // 1~N번 노드에 대해 현재 노드 c를 거쳐가는 모든 경로들을 비교
        for(int c=1; c<=N; c++) {
            for(int s=1; s<=N; s++) {
                for(int r=1; r<=N; r++) {
                    graph[s][r] = Math.min(graph[s][r], graph[s][c] + graph[c][r]);
                }
            }
        }
    }

    static void calc() {
        int result = -1;
        for(int i=1; i<=N; i++)
            result = Math.max(result, graph[i][X] + graph[X][i]);

        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        init();
        floyd();
        calc();
    }

}
