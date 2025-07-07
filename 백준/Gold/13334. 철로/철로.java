import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static int N;
    static int D;
    static PriorityQueue<Node> nodes = new PriorityQueue<>();

    static int result = 0;

    static class Node implements Comparable<Node> {
        public int src, dst, cost;
        public Node(int n1, int n2) {
            this.src = Math.min(n1, n2);
            this.dst = Math.max(n1, n2);
            cost = dst - src;
        }

        @Override
        public int compareTo(Node n) {
            return this.dst - n.dst; // 도착지 기준 오름차순 정렬
        }
    }

    static void init() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(reader.readLine()); // 사람 수. 1 ~ 10만

        // 정수 쌍을 어떻게 처리할지 결정 필요
        int src, dst;
        StringTokenizer tkn;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            nodes.add(new Node(src, dst));
        }

        D = Integer.parseInt(reader.readLine()); // 철로 길이: 1 ~ 2억
    }

    static void calc() {
        int count = 0;
        PriorityQueue<Integer> rail = new PriorityQueue<>();

        while(!nodes.isEmpty()) {
            // 도착지가 가장 왼쪽에 있는, 즉 가장 왼쪽에 치우처져있는 노드를 가져온다.
            Node cur = nodes.poll();

            rail.add(cur.src);
            count++;
            int start = cur.dst - D;

            while(!rail.isEmpty() && rail.peek() < start) {
                rail.poll();
                count--;
            }

            result = Math.max(result, count);
        }

    }

    public static void main(String[] args) throws Exception {
        // 1. 정수 쌍을 입력받고, 출발지 및 도착지 순으로 정렬한다.
        // 2. 각 출발지를 기준으로 철로를 깔았을 때 포함되는 사람 수를 계산한다.
        // 3. 마지막 출발지까지 갈 때까지 반복한 다음, 최대 카운트를 저장해 리턴한다.
        init();
        calc();

        System.out.println(result);
    }

}
