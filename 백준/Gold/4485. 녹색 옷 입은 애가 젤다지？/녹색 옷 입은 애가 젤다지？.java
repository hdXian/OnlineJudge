import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static boolean[] visited;

    public static int[] minCost;
    public static int[] nodes;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn;
        StringBuilder sb = new StringBuilder();

        int seq = 1;
        while (true) {
            N = Integer.parseInt(reader.readLine());
            if (N == 0)
                break;

            visited = new boolean[N*N]; // 방문 기록 노드
            minCost = new int[N*N]; // 각 노드까지의 최단 거리를 저장하는 배열
            Arrays.fill(visited, false);
            Arrays.fill(minCost, 1000000);

            nodes = new int[N*N];

            // 맵 입력받기
            for (int i=0; i<N; i++) {
                tkn = new StringTokenizer(reader.readLine());
                for (int j=0; j<N; j++) {
                    int idx = i*N + j;
                    nodes[idx] = Integer.parseInt(tkn.nextToken());
                }
            }

            // 시작 노드는 0번
            dike(0);

            // 끝 노드는 (N * N - 1)번
            String result = String.format("Problem %d: %d\n", seq++, minCost[N*N-1]);
            sb.append(result);
        }

        System.out.println(sb.toString());

    }

    public static void dike(int start) {
        // 시작 노드 방문 처리
        visited[start] = true;
        minCost[start] = nodes[start];

        // 1. 오른쪽과 아래 노드를 갱신한다. (간선 연결돼 있는 노드들 업데이트)
        updateNeighbor(start);

        // 2. (전체 노드 개수-1)번 반복 (...왜?) (전체 노드를 한번씩 방문? 자세히 모르겠음.)
        for (int i=0; i<(N*N-1); i++) {
            // 2. 방문하지 않은 노드 중에서 가장 거리가 짧은 노드를 가져온다.
            int cur = getClosestNode();
            visited[cur] = true;
            updateNeighbor(cur);
        }

    }

    // 오른쪽, 아래쪽 노드를 계산하고 큐에 추가하는 함수
    public static void updateNeighbor(int num) {
        // 왼쪽 안되는 조건 -> N으로 나누어 떨어질 때
        if (num%N != 0) {
            int idx = num-1;
            minCost[idx] = Math.min(minCost[idx], minCost[num] + nodes[idx]);
        }

        // 위쪽 안되는 조건 -> N 미만일 때
        if (num >= N) {
            int idx = num - N;
            minCost[idx] = Math.min(minCost[idx], minCost[num] + nodes[idx]);
        }

        // 오른쪽 안되는 조건 -> 나머지가 N-1일 때
        if (num%N != (N-1)) {
            int idx = num+1; // 목적지
            minCost[idx] = Math.min(minCost[idx], minCost[num] + nodes[idx]);
        }

        // 아래쪽 안되는 조건 -> 몫이 N 이상일 때
        if (num / N < N-1) {
            int idx = num + N; // 목적지
            minCost[idx] = Math.min(minCost[idx], minCost[num] + nodes[idx]);
        }
    }

    // 방문하지 않은 노드 중에서 가장 가까운 노드를 구한다.
    public static int getClosestNode() {
        int idx = -1;
        int cost = 1000000;

        for (int i=0; i<N*N; i++) {
            if (!visited[i] && minCost[i] < cost) {
                idx = i;
                cost = minCost[i];
            }
        }

        return idx;
    }



}
