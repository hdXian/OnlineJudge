import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int[] parents;
    static int[] ranks;

    static boolean[] is_cycle;

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);
        // 부모가 같으면 사이클 체크
        if (p1 == p2) {
            is_cycle[p1] = is_cycle[p2] = true;
            return;
        }

        // 둘 중 하나가 사이클에 포함돼있어도 사이클 체크
        if (is_cycle[p1] || is_cycle[p2]) {
            is_cycle[p1] = is_cycle[p2] = true;
        }

        int r1 = ranks[p1];
        int r2 = ranks[p2];
        if (r1 == r2) {
            parents[p2] = p1;
            ranks[p1]++;
        }
        else if (r1 > r2) parents[p2] = p1;
        else parents[p1] = p2;
    }

    // 각 테케별 결과를 리턴
    static String calc() throws Exception {
        // 1. N, M을 입력받고 parents, ranks 배열을 초기화한다.
        int N, M;
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        M = Integer.parseInt(tkn.nextToken());

        if (N == 0) return "-1";

        parents = new int[N+1];
        ranks = new int[N+1];
        is_cycle = new boolean[N+1];
        for(int i=0; i<N+1; i++) {
            parents[i] = i;
            ranks[i] = 1;
            is_cycle[i] = false;
        }

        // 2. 입력받은 간선 정보를 바탕으로 union을 진행한다.
        int n1, n2;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            n1 = Integer.parseInt(tkn.nextToken());
            n2 = Integer.parseInt(tkn.nextToken());
            // 2-1. 두 노드의 부모가 같거나, 한쪽이 사이클에 포함된 경우 사이클로 체크
            union(n1, n2);
        }

        // 3. 루트 노드 개수를 세서 집합 개수를 센 다음, 사이클 개수를 뺀다.
        int roots = 0;
        for(int n=1; n<N+1; n++) {
            if (n == findParent(n) && !is_cycle[n]) roots++;
        }

        if (roots == 0) return "No trees.";
        else if (roots == 1) return "There is one tree.";
        else return String.format("A forest of %d trees.", roots);
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        int seq = 1;
        while(true) {
            String result = calc();
            if (result.equals("-1")) break;

            sb.append(String.format("Case %d: ", seq++)).append(result).append('\n');
        }

        System.out.println(sb);
    }

}
