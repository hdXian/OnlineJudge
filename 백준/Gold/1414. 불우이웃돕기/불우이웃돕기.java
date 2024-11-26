import java.util.*;
import java.io.*;

public class Main {

    public static int[][] graph; // 간선 비용 때문에 인접 행렬도 같이 써야 함
    public static int N;
    public static final String ALPHA = "0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static ArrayList<Edge> edges;
    public static int result = 0;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(reader.readLine());

        // 그래프 초기화
        graph = new int[N][N];
        for(int i=0; i<N; i++) {
            Arrays.fill(graph[i], Integer.MAX_VALUE);
        }

        // 그래프 그리기
        String line;
        int val;
        for(int i=0; i<N; i++) {
            line = reader.readLine();

            for(int j=0; j<N; j++) {

                char ch = line.charAt(j);

                if (ch != '0') {
                    val = ALPHA.indexOf(ch); // 거리
                    result += val; // 일단 전체 랜선 길이에 추가
                    graph[i][j] = val;

                    // [j][i]와 비교해 더 작은 값으로 업데이트
                    int small;
                    if (graph[j][i] != Integer.MAX_VALUE) {
                        small = Math.min(graph[i][j], graph[j][i]);
                        graph[i][j] = small;
                        graph[j][i] = small;
                    }
                    else {
                        graph[j][i] = val;
                    }

                }

            }

        }

        // 간선 리스트 생성
        edges = new ArrayList<>();
        for(int i=0; i<N; i++) {
            for (int j=i; j<N; j++) {

                // 간선이 존재하고 시작, 끝이 다를 경우
                if ((graph[i][j] != Integer.MAX_VALUE) && (i != j)) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        Collections.sort(edges);
        int MST = kruskal(edges);

        if (MST == -1) {
            result = -1;
        }
        else {
            result -= MST;
        }

        System.out.println(result);
    }

    // 최소 신장 트리를 만들고 간선 합을 리턴
    private static int kruskal(ArrayList<Edge> edges) {
        int total = 0;
        int edge_num = 0;

        // 부모 리스트 초기화
        int[] parent = new int[N];
        for(int i=0; i<N; i++)
            parent[i] = i;

        int n1, n2;
        int p1, p2;
        for (Edge e: edges) {
            n1 = e.node1;
            n2 = e.node2;

            // 두 노드의 부모 노드를 확인 (집합 루트 노드를 확인)
            p1 = findParent(parent, n1);
            p2 = findParent(parent, n2);
            // 집합의 루트 노드가 다르면 합치기 (부모 노드를 통일)
            if (p1 != p2) {
                union(parent, p1, p2);
                total += e.cost;
                edge_num++;
            }

        }

        if (edge_num != (N-1))
            return -1;

        return total;
    }

    // node1과 node2를 합치는 함수 (루트 노드를 같게 설정)
    private static void union(int[] parent, int node1, int node2) {
        int p1 = findParent(parent, node1);
        int p2 = findParent(parent, node2);

        // 번호가 더 작은 노드를 부모 노드로 설정
        if (p1 < p2)
            parent[p2] = p1;
        else
            parent[p1] = p2;
    }

    // 어떤 노드가 속한 집합의 루트 노드를 찾는 함수
    private static int findParent(int[] parent, int node) {
        // 부모가 자기 자신이 아니라면 (부모 노드가 아니라면)
        if (parent[node] != node) {
            parent[node] = findParent(parent, parent[node]);
        }

        // 재귀호출이므로 루트 노드는 자신을 리턴. 나머지 노드는 루트 노드가 이 루트로 설정됨.
        return parent[node];
    }

    static class Edge implements Comparable<Edge> {

        public int node1;
        public int node2;
        public int cost;

        public Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(this.cost, e.cost);
        }

        @Override
        public String toString() {
            return String.format("Edge[(%d, %d), cost=%d]\n", node1, node2, cost);
        }

    }
    

}
