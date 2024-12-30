import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N; // 마을 개수
    public static int[] people; // 마을 인구수

    public static Node[] nodes; // 트리 구성에 사용할 노드
    public static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine());

        // dp 테이블 초기화
        dp = new int[N+1][2];
        for(int i=0; i<2; i++) {
            Arrays.fill(dp[i], 0);
        }

        // 노드 배열 초기화
        nodes = new Node[N+1];
        for(int i=1; i<=N; i++) {
            nodes[i] = new Node(i);
        }

        // 각 마을의 인구수 입력받기
        people = new int[N+1];
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<=N; i++) {
            people[i] = Integer.parseInt(tkn.nextToken());
        }

        // nodes를 통해 트리 구성하기
        int src, dst;
        int parent, child;
        for(int i=0; i<N-1; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            // src -> dst 방향으로 트리 구성 (오름차순)
            nodes[src].children.add(dst);
            nodes[dst].children.add(src);
        }

        // 각 노드가 우수마을이다 -> 그 하위 트리들의 우수마을 여부가 결정된다...
        // 어떤 노드가 우수마을이다 -> 그 하위 노드들은 우수마을이 아니다.
        // 해당 노드가 우수마을일 때 우수마을의 인구수를 dp 테이블에 저장.
        calc(1, 0);

        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }

    // dp 테이블에 넣을 값을 계산 -> 자신이 우수마을일 때, 우수마을이 아닐 때 중 더 큰 값으로 저장.
    public static void calc(int n, int parent) {
        Node curNode = nodes[n];

        for(int c: curNode.children) {

            if (c != parent) {
                calc(c, n);
                dp[n][0] += Math.max(dp[c][0], dp[c][1]);
                dp[n][1] += dp[c][0];
            }

        }
        dp[n][1] += people[n];

    }

    static class Node {
        public int number;
        public List<Integer> children = new ArrayList<>();

        public Node(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return String.format("Node[%d], children = " + children, number);
        }
    }

}
