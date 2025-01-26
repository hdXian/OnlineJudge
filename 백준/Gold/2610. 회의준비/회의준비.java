import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, M;
    public static int[] parents;

    public static int INF = 1000;
    public static int[][] graph;

    public static List<Integer> presidents = new ArrayList<>();

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 참석자 수
        M = Integer.parseInt(reader.readLine()); // 참석자 간 관계 수

        // 부모 리스트 초기화
        parents = new int[N+1];
        for(int i=1; i<=N; i++) {
            parents[i] = i;
        }

        graph = new int[N+1][N+1];
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=N; j++) {
                if (i==j)
                    graph[i][j] = 0;
                else
                    graph[i][j] = INF;
            }
        }

        // 1. union-find로 위원회를 구성한다.
        StringTokenizer tkn;
        int n1, n2;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());

            n1 = Integer.parseInt(tkn.nextToken());
            n2 = Integer.parseInt(tkn.nextToken());

            union(n1, n2);
            graph[n1][n2] = 1;
            graph[n2][n1] = 1;
        }

        // 2. Floyd 알고리즘으로 모든 노드 간 최단거리를 구하고,
        // 각 노드별로 자신에게 닿는 최대 거리를 계산한 후, 그 중 가장 짧게 걸리는 노드를 대표로 선출한다.

        // 부모들의 집합을 만든다.
        Set<Integer> roots = new HashSet<>();
        for(int i=1; i<=N; i++) {
            if (parents[i] == i)
                roots.add(i);
        }
        int K = roots.size();
        sb.append(K).append("\n"); // 위원회 수 K 추가

        // 각 루트들에 대한 (각 그룹에 대한) 대표 선출이 완료되면 president 배열에 대표의 번호가 추가됨.
        for(int r: roots) {
            election(r);
        }

        Collections.sort(presidents);

        for(Integer p: presidents) {
            sb.append(p).append("\n");
        }

        System.out.println(sb);

    }

    // 각 위원회의 대표를 뽑는 메서드
    public static void election(int root) {
        List<Integer> members = new ArrayList<>();

        // 위원회 구성
        for (int i=1; i<=N; i++) {
            if (findParent(i) == root)
                members.add(i);
        }

        // 위원회 멤버들을 대상으로 플로이드 돌려야 함.
        for(int m: members) {
            // 모든 노드-노드 간에 (물론 여기서 따지는 노드는 위원회 소속 멤버들만 대상)
            // 현재 노드 m을 거치는 경우를 비교해서 더 작은 놈으로 갱신한다.
            for (int start: members) {
                for (int end: members) {
                    graph[start][end] = Math.min(graph[start][end], (graph[start][m] + graph[m][end]));
                }
            }
        }

        // 플로이드 끝나면, 위원회 멤버들을 대상으로 거리를 비교하고, 최단거리를 갱신.
        int minCost = INF;
        int president = root; // 대표 번호
        int tmp_max;
        // 의사 전달 시간의 최대 길이가 가장 짧은 멤버를 찾는다.
        for (int m: members) {
            tmp_max = 0;
            // 해당 멤버에 대한 의사 전달 시간 중 최댓값을 찾는다.
            for (int p: members) {
                // if (p==m) continue; // 자기 자신은 생략
                if ((graph[p][m] != INF) && (graph[p][m] > tmp_max))
                    tmp_max = graph[p][m];
            }
            // 의사 전달 시간의 최대 길이가 기존 길이보다 짧으면 -> 새로운 대표로 업데이트
            if (tmp_max < minCost) {
                minCost = tmp_max;
                president = m;
            }
        }

        presidents.add(president);

    }

    public static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 == p2)
            return;

        // 더 작은 놈으로 부모 업데이트
        if (p1 < p2)
            parents[p2] = p1;
        else
            parents[p1] = p2;

    }

    public static int findParent(int n) {
        if (parents[n] != n)
            parents[n] = findParent(parents[n]);
        return parents[n];
    }

}
