import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, P, K;
    static int[] distances; // 거리 합의 최댓값이 100억이라 long으로 선언

    static class Node implements Comparable<Node> {
        int number;
        int cost;
        public Node(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(this.cost, n.cost);
        }

    }

    static Map<Integer, List<Node>> neighbors = new HashMap<>(); // 노드 번호별 이웃 노드들을 저장할 Map

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 사람 수. 번호는 1 ~ N.
        P = Integer.parseInt(tkn.nextToken()); // 케이블 수. 1 ~ 1만
        K = Integer.parseInt(tkn.nextToken()); // 무료로 해줄 케이블 수. 0 ~ N

        distances = new int[N+1]; // 인덱스 1부터 쓰기

        for(int i=1; i<=N; i++) neighbors.put(i, new ArrayList<>()); // 이웃들을 저장할 Map 초기화

        int a, b, x;
        for(int i=0; i<P; i++) {
            tkn = new StringTokenizer(reader.readLine());
            a = Integer.parseInt(tkn.nextToken());
            b = Integer.parseInt(tkn.nextToken());
            x = Integer.parseInt(tkn.nextToken());

            // a, b번 노드 서로에게 이웃을 추가
            neighbors.get(a).add(new Node(b, x));
            neighbors.get(b).add(new Node(a, x));
        }

    }

    static boolean dijkstra(int min_cost) {
        // min_cost보다 작거나 같은 간선은 가중치를 0으로 잡는다.
        // min_cost보다 큰 간선은 가중치를 1로 잡는다.
        // 그 상태로 다익스트라를 돌리면, 경로 자체는 최단거리가 아닐지라도 어쨌든 최대한 min_cost보다 비용이 적은 간선들로만 최단 경로를 구성하게 될 것이다.

        // 0. 최단 비용 배열을 초기화한다.
        Arrays.fill(distances, 10001); // 1만 1. 간선은 최대 1만개.

        // 1. 시작 노드의 비용을 0으로 설정하고, 큐에 집어넣는다.
        PriorityQueue<Node> pq = new PriorityQueue<>();
        distances[1] = 0;
        pq.add(new Node(1, 0));

        // 2. 현재 노드의 인접 노드들에 대해 최소 비용을 업데이트한다.
        // 2-1. 이 때, 최소 비용이 업데이트된 노드들만 큐에 추가한다.
        // 3. 큐가 빌 때까지 반복한다.
        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            // 인접 노드들에 대해
            for(Node next: neighbors.get(cur.number)) {
                int nc = next.cost > min_cost ? 1 : 0;
                int new_cost = distances[cur.number] + nc;

                if (new_cost < distances[next.number]) {
                    distances[next.number] = new_cost;
                    pq.add(new Node(next.number, nc));
                }

            }

        }

        return distances[N] <= K;
    }

    static int calc() {
        int start = 0;
        int end = 1000000; // 간선 비용은 최대 100만

        int result = -1;
        while(start <= end) {
            int mid = (start + end)/ 2;

            boolean isAble = dijkstra(mid);
            // 가능하다 -> 최소 비용을 더 줄여도 조건을 만족할 수도 있다.
            if (isAble) {
                end = mid-1;
                result = mid;
            }
            // 불가능하다 -> 최소 비용이 모자라다. 더 늘린다.
            else {
                start = mid+1;
            }

        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
