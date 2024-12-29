import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    public static int N, M, K;
    public static boolean[] isElec;
    public static List<Edge> edges = new ArrayList<>();

    public static int[] parents; // union-find 배열
    public static int result;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 도시 개수
        M = Integer.parseInt(tkn.nextToken()); // 케이블 개수
        K = Integer.parseInt(tkn.nextToken()); // 발전소 개수

        isElec = new boolean[N+1]; // 발전소 여부
        parents = new int[N+1]; // union-find 루트 노드 저장 배열

        // 발전소 도시들을 리스트에 추가
        tkn = new StringTokenizer(reader.readLine());
        while (tkn.hasMoreTokens()) {
            int num = Integer.parseInt(tkn.nextToken());
            isElec[num] = true;
        }

        // 부모 노드를 자기 자신으로 초기화
        for(int i=1; i<=N; i++) {
            parents[i] = i;
        }

        // 발전소들 미리 union
        for(int i=1; i<=N; i++) {
            if (isElec[i]) { // 첫 번째 발전소 찾기
                for (int j=i+1; j<=N; j++) {
                    if (isElec[j]) // 이후 발견하는 발전소들과 union
                        union(i, j);
                }
                break; // 하나 찾고 말꺼임
            }
        }

        // 그래프 그리기
        int u, v, w;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            u = Integer.parseInt(tkn.nextToken());
            v = Integer.parseInt(tkn.nextToken());
            w = Integer.parseInt(tkn.nextToken());

            edges.add(new Edge(u, v, w));
        }

        // 간선을 오름차순으로 정렬
        Collections.sort(edges);

        kruskal();

        System.out.println(result);

    }

    private static void kruskal() {

        int total = 0;
        Edge e;
        while (check() && !edges.isEmpty()) {
            e = edges.get(0);
            boolean isUnion = union(e.src, e.dst);
            if (isUnion)
                total += e.cost;
            edges.remove(0);
        }
        result = total;
    }

    // 종료 조건 체크하기 -> 루트 노드가 하나뿐이어야 함.
    private static boolean check() {
        int roots = 0;
        for(int i=1; i<=N; i++) {
            if (parents[i] == i)
                roots++;
        }
        return (roots > 1); // 루트 노드가 하나 이상일 경우 true
    }

    private static int findParent(int n) {
        if (parents[n] != n)
            parents[n] = findParent(parents[n]);
        return parents[n];
    }

    private static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);
        if (p1 != p2) {
            if (p1 < p2) {
                parents[p2] = p1;
                return true;
            }
            else {
                parents[p1] = p2;
                return true;
            }
        }
        return false;
    }

    static class Edge implements Comparable<Edge> {
        public int src;
        public int dst;
        public int cost;

        public Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(this.cost, e.cost);
        }

        @Override
        public String toString() {
            return String.format("Edge[src=%d, dst=%d, cost=%d]", src, dst, cost);
        }

    }


}
