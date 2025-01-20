import java.util.*;
import java.io.*;

public class Main {

    public static int N, M;
    public static int[] degree;
    public static ArrayList<ArrayList<Integer>> edges;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 문제 수
        M = Integer.parseInt(tkn.nextToken()); // 정보 개수

        degree = new int[N+1];
        Arrays.fill(degree, 0);

        edges = new ArrayList<>();
        for(int i=0; i<N; i++) {
            edges.add(new ArrayList<>());
        }

        int n1, n2;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            n1 = Integer.parseInt(tkn.nextToken());
            n2 = Integer.parseInt(tkn.nextToken());

            // n1을 n2보다 먼저 풀어야 한다.
            edges.get(n1-1).add(n2); // n1 번호의 edges ArrayList를 가져와서 n2를 추가
            degree[n2]++;
        }
        
        topologySort();
    }

    public static void topologySort() {
        StringBuilder sb = new StringBuilder();

        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i=1; i<=N; i++) {
            if (degree[i] == 0) {
                q.add(i);
            }
        }

        while (true) {
            if (q.isEmpty())
                break;

            // 현재 노드와 연결된 노드들의 진입 차수를 1 감소시켜야 한다.
            Integer nodeNumber = q.poll();
            sb.append(nodeNumber).append(" ");

            for(Integer c: edges.get(nodeNumber-1)) {
                degree[c]--;
                if (degree[c] == 0)
                    q.add(c);
            }

        }
        System.out.println(sb);
    }

}
