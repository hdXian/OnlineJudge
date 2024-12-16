import java.util.*;
import java.io.*;

public class Main {

    public static int N, M, K;
    public static int[] parents; // 그룹 부모
    public static int[] costs; // 친구비 리스트
    public static int totalPrice = 0; // 지불할 친구비

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 학생 수
        M = Integer.parseInt(tkn.nextToken()); // 친구관계 수
        K = Integer.parseInt(tkn.nextToken()); // 가진 돈

        costs = new int[N+1];
        tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<=N; i++) {
            costs[i] = Integer.parseInt(tkn.nextToken());
        }

        // 부모 리스트 초기화 (처음은 자기 자신이 부모)
        parents = new int[N+1];
        for(int i=1; i<=N; i++) {
            parents[i] = i;
        }

        // 그룹 생성
        int n1, n2;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            n1 = Integer.parseInt(tkn.nextToken());
            n2 = Integer.parseInt(tkn.nextToken());

            union(n1, n2);
        }

        int[] minCost = new int[N+1];
        Arrays.fill(minCost, Integer.MAX_VALUE);

        for(int i=1; i<=N; i++) {
            // 학생의 루트 노드를 찾는다.
            int root = findParent(i);

            // 루트 노드의 인덱스에 그 학생의 친구비를 비교해 더 작은 값으로 업데이트한다.
            minCost[root] = Math.min(minCost[root], costs[i]);
        }

        for(int i=1; i<=N; i++) {
            // i가 루트 노드라면 (경로 압축으로 parents 배열에는 루트 노드들만이 저장돼있다)
            if (i == parents[i])
                totalPrice += minCost[i];
        }

        if (totalPrice <= K)
            System.out.println(totalPrice);
        else
            System.out.println("Oh no");

    }

    public static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        // 더 작은 놈으로 부모를 업데이트 (중요 -> 부모를 업데이트 해야 함.)
        if (p1 != p2) {
            if (p1 < p2) {
                parents[p2] = p1;
            }
            else
                parents[p1] = p2;
        }

    }

    public static int findParent(int n) {
        // 경로 압축 -> 모두 루트를 부모로 직접 가리키도록 업데이트 해놓기
        if (parents[n] != n) {
            parents[n] = findParent(parents[n]);
        }
        return parents[n];
    }

}
