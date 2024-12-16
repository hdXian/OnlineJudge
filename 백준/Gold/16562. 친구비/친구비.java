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

        // 집합의 루트들
        Set<Integer> roots = new HashSet<>();
        for(int i=1; i<=N; i++) {
            roots.add(findParent(i));
        }
        roots.remove(0); // 0은 안 쓰는 인덱스. 집합 루트가 될 수 없음.

        for (int r: roots) {

            int price = 100000; // 10만 (친구비 최대는 1만)

            // 부모가 r인 학생 중에서 친구비가 가장 적은 비용을 찾는다.
            for (int i=1; i<=N; i++) {
                if (findParent(i) == r)
                    price = Math.min(price, costs[i]);
            }

            totalPrice += price;

        }

        if (totalPrice <= K)
            System.out.println(totalPrice);
        else
            System.out.println("Oh no");

    }

    public static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 != p2) {
            if (p1 < p2) {
                parents[p2] = p1;
            }
            else
                parents[p1] = p2;
        }

    }

    public static int findParent(int n) {
        return (parents[n] == n) ? n : findParent(parents[n]);
    }

}
