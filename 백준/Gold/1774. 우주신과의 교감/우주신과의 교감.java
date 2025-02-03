import java.util.*;
import java.io.*;

public class Main {

    public static int N, M;

    static class Node {
        public int x, y;

        Node(int x, int y) { this.x = x; this.y = y; }

        public double calcDistance(Node n) {
            int scala_x = this.x - n.x;
            int scala_y = this.y - n.y;

            return Math.sqrt(Math.pow(scala_x, 2) + Math.pow(scala_y, 2));
        }
    }

    static class Edge implements Comparable<Edge> {
        public int n1, n2;
        public double cost;

        Edge(int n1, int n2, double cost) { this.n1 = n1; this.n2 = n2; this.cost = cost; }

        @Override
        public int compareTo(Edge e) {
            return Double.compare(this.cost, e.cost);
        }
    }

    public static Map<Integer, Node> nodeMap = new HashMap<>(); // 노드 번호 : 노드

    public static boolean[][] isLinked; // 노드 간 연결 상태를 표현할 그래프

    public static List<Edge> edges = new ArrayList<>(); // 간선 리스트 (MST용)
    public static int[] parents; // 부모 리스트 (union-find)

    public static double result = 0; // 추가되는 간선 비용
    public static int edge_count = 0; // 간선 개수 카운트용

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 노드 개수
        M = Integer.parseInt(tkn.nextToken()); // 간선 개수

        // 부모 리스트 초기화
        parents = new int[N+1];
        for(int i=1; i<=N; i++) {
            parents[i] = i;
        }

        // 비용 그래프 초기화
        isLinked = new boolean[N+1][N+1];
        for(int i=1; i<= N; i++) {
            Arrays.fill(isLinked[i], false);
        }

        // 노드 좌표 입력받기
        int _x, _y;
        int seq = 1;
        for (int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());

            _x = Integer.parseInt(tkn.nextToken());
            _y = Integer.parseInt(tkn.nextToken());
            nodeMap.put(seq++, new Node(_x, _y));
        }

        // 간선 입력받기
        int _n1, _n2;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());

            _n1 = Integer.parseInt(tkn.nextToken());
            _n2 = Integer.parseInt(tkn.nextToken());

            Node node1 = nodeMap.get(_n1);
            Node node2 = nodeMap.get(_n2);
            double cost = node1.calcDistance(node2);

            if (isLinked[_n1][_n2])
                continue;

            union(_n1, _n2);
            edge_count++;
            isLinked[_n1][_n2] = true;
            isLinked[_n2][_n1] = true;
        }

        // 이제 연결 안 돼있는 나머지 노드들도 다 연결해야 함
        // 그러려면 연결 돼있는 노드랑 안 돼있는 노드랑 구분해야 함
        for (int r=1; r<=N; r++) {
            for (int c=1; c<=N; c++) {
                if (r == c) continue; // 자기 자신은 제외
                // r번 노드와 c번 노드가 연결이 안 돼있으면
                if (!isLinked[r][c]) {
                    Node node1 = nodeMap.get(r);
                    Node node2 = nodeMap.get(c);
                    double cost = node1.calcDistance(node2);
                    edges.add(new Edge(r, c, cost)); // 연결해서 간선 리스트에 추가
                    isLinked[r][c] = true;
                    isLinked[c][r] = true;
                }
            }
        }

        // 간선 리스트 정렬
        Collections.sort(edges);

        // mst 돌리기
        mst();

        System.out.printf("%.2f\n", result);
    }

    public static void mst() {
        // 추가된 간선이 n-1개가 될 때까지 간선을 MST에 추가
        int n1, n2;
        double cost;
        for (Edge e: edges) {
            n1 = e.n1;
            n2 = e.n2;

            // 서로 다른 집합에 속해있으면
            // 여기서 추가되는 간선은 새로 만드는 간선. 이미 존재하는 간선은 미리 union 시켜놨음.
            if (union(n1, n2)) {
                edge_count++;
                cost = nodeMap.get(n1).calcDistance(nodeMap.get(n2));
                result += cost;
            }

            // if (edge_count == N-1)
                // break;
        }

    }

    public static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 == p2) return false;

        if (p1 < p2) {
            parents[p2] = p1;
        }
        else {
            parents[p1] = p2;
        }
        return true;
    }

    public static int findParent(int n) {
        if (parents[n] != n)
            parents[n] = findParent(parents[n]);
        return parents[n];
    }

}
