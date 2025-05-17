import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int T; // 테케 개수 (0 ~ 100)
    static int N, M; // 노드 개수(2 ~ 1천), 간선 개수(1 ~ 1만)
    static int[] parents;
    static int count; // 간선 개수를 셀 카운트

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        M = Integer.parseInt(tkn.nextToken());
        count = 0;
    }

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1==p2) return;

        // 더 작은 녀석이 부모
        if (p1 < p2) {
            parents[p2] = p1;
            count++;
        }
        else {
            parents[p1] = p2;
            count++;
        }
    }

    static int calc() throws Exception {
        // union-find를 활용한 MST(최소 신장 트리)

        // 부모 배열 초기화
        parents = new int[N+1];
        for(int i=1; i<=N; i++) parents[i] = i;

        // 간선 정보 입력받기
        List<String> ways = new ArrayList<>();
        for(int i=0; i<M; i++) ways.add(reader.readLine());

        StringTokenizer tkn;
        int a, b;
        for(String way: ways) {
            tkn = new StringTokenizer(way);
            // 간선의 양쪽 노드를 입력받는다.
            a = Integer.parseInt(tkn.nextToken());
            b = Integer.parseInt(tkn.nextToken());
            union(a, b);
            if (count == N-1) break;
        }

        // ??? 그냥 항상 N-1개 아니야?
        return count;
    }

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<T; i++) {
            init();
            int result = calc();
            sb.append(result).append('\n');
        }
        System.out.println(sb);
    }

}
