import java.io.*;
import java.math.*;
import java.util.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M;
    static PriorityQueue<Edge> pq = new PriorityQueue<>(); // MST 구성에 사용할 pq

    static int[] parents;
    static int[] ranks;

    static class Edge implements Comparable<Edge> {
        public int p1, p2; // 집어넣을 때 항상 p1 < p2로 넣기
        public Edge(int p1, int p2) {
            this.p1 = Math.min(p1, p2);
            this.p2 = Math.max(p1, p2);
        }

        @Override
        public int compareTo(Edge e) {
            if (this.p1 == e.p1) return Integer.compare(this.p2, e.p2);
            return Integer.compare(this.p1, e.p1);
        }

    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 마을 개수. 1 ~ 50
        M = Integer.parseInt(tkn.nextToken()); // 한 집합이 가져야 할 도로 개수. N-1 ~ 1000

        // 인접 행렬 바탕으로 도로 정보를 pq에 집어넣기
        String line;
        for (int i=0; i<N; i++) {
            char[] carr = reader.readLine().toCharArray();
            for(int j=i+1; j<N; j++) {
                if (carr[j] == 'Y') pq.add(new Edge(i, j));
            }
        }

        // 부모, 랭크 배열 초기화
        parents = new int[N];
        for(int i=0; i<N; i++) parents[i] = i;

        ranks = new int[N];
        Arrays.fill(ranks, 0);
    }

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 == p2) return false;

        int r1 = ranks[p1];
        int r2 = ranks[p2];

        // rank가 큰 쪽이 부모가 되어야 트리가 더 깊어지지 않는다.
        if (r1 == r2) {
            parents[p2] = p1;
            ranks[p1]++;
        }
        else if (r1 > r2) {
            parents[p2] = p1;
        }
        else {
            parents[p1] = p2;
        }

        return true;
    }

    // pq에서 도로 정보를 가져다가 MST를 구성해야 함.
    // 단, 걸러진 도로를 그냥 버리지 않고 따로 남겨둬야 함.
    // MST를 만든 다음 M을 채우기 모자란 만큼 도로를 더 추가해야 하기 때문.
    static String calc() {
        PriorityQueue<Edge> remains = new PriorityQueue<>();

        List<Edge> MST_edges = new ArrayList<>();

        // 기준에 따라 도로를 선택하며 MST를 구성한다.
        int edge_count = 0;
        while(!pq.isEmpty() && edge_count < N-1) {
            Edge cur = pq.poll();

            // 합연산에 성공하면
            if (union(cur.p1, cur.p2)) {
                MST_edges.add(cur);
                edge_count++;
            }
            // 합연산에 실패하면 버리는게 아니라 remains에 잠시 넣어둠
            else {
                remains.add(cur);
            }
        }

        if (edge_count != N-1) return "-1"; // MST 구성이 불가능하면 -1 리턴

        // 버리지 않고 남겨둔 도로들을 다시 pq에 넣는다.
        while(!remains.isEmpty()) {
            pq.add(remains.poll());
        }

        // 도로 개수가 M개를 채우도록 도로를 더 선택한다.
        while(!pq.isEmpty() && edge_count < M) {
            MST_edges.add(pq.poll());
            edge_count++;
        }

        if (edge_count != M) return "-1"; // 도로가 M개가 채워지지 않으면 -1 리턴

        // 카운트하기
        Map<Integer, Integer> counts = new HashMap<>();
        for(int i=0; i<N; i++) counts.put(i, 0);
        for(Edge e: MST_edges) {
            counts.put(e.p1, counts.get(e.p1)+1);
            counts.put(e.p2, counts.get(e.p2)+1);
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++) {
            sb.append(counts.get(i)).append(' ');
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        // 1. 도로 정보를 클래스 형태로 저장한다.
        // 2. 문제에서 정의한 형태에 따라 MST를 생성한다.
        // 3. MST를 생성하는데 성공하면 선택된 도로의 개수가 N-1개가 된다.
        // 4. 하지만 문제가 요구하는 도로의 집합은 도로를 M개 가지고 있어야 한다. M개 도로를 채우기 위해 MST를 생성하고 남은 도로들 중에서 더 선택해야 한다.
        // 5. "가장 우선순위가 높은" 집합을 요구하므로, MST를 만들 때와 같은 기준으로 도로를 더 선택하기만 하면 된다.
        init();
        String result = calc();
        System.out.println(result);
    }

}
