import java.io.*;
import java.util.*;

public class Main {

    public static int D, N, M;

    public static int[] edges; // 이분 탐색을 위한 배열

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        D = Integer.parseInt(tkn.nextToken()); // 전체 거리 (1 ~ 10억)
        N = Integer.parseInt(tkn.nextToken()); // 섬 개수 (1 ~ 5만)
        M = Integer.parseInt(tkn.nextToken()); // 제거할 섬 개수 (0 ~ n)

        // 각 간선을 오름차순 정렬한 뒤, 배열에 저장한다.
        edges = new int[N+2];
        int tmp;
        // 거리 입력받기
        for(int i=1; i<=N; i++) {
            tmp = Integer.parseInt(reader.readLine());
            edges[i] = tmp;
        }

        // 양끝 노드 처리
        edges[0] = 0;
        edges[N+1] = D;

        Arrays.sort(edges);

        int start = 0;
        int end = D;

        int res = binarySearch();
        System.out.println(res);

    }

    public static int binarySearch() {
        int start = 0;
        int end = D;
        int result = D;

        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            int count = 0;
            int cur = 0;
            // 모든 노드들에 대해 탐색을 돌린다.
            for(int i=1; i<=N+1; i++) {
                // 두 노드 간 거리가 mid값보다 작을 경우 제거한다.
                if ((edges[i]-edges[cur]) < mid) {
                    count++;
                }
                // mid값보다 크거나 같을 경우 해당 노드를 들러야 함. cur를 해당 노드로 이동시킴.
                else {
                    cur = i;
                }
            }

            // 제거한 노드의 개수가 M보다 클 경우
            if (count > M) {
                end = mid-1;
            }
            // 제거한 노드의 개수가 M보다 작거나 같을 경우 -> 조건을 만족.
            else {
                result = mid;
                start = mid+1;
            }

        }

        return result;
    }

}
