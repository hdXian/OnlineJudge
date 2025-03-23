import java.io.*;
import java.util.*;
import java.math.*;

// 부분 최대, 최소를 구하는 세그먼트 트리 (문제 풀이)
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M;
    public static int[] arr;
    public static int[] max_seg_tree;
    public static int[] min_seg_tree;
    public static int INF = 1000000001;

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 숫자 개수 (1~10만), 각 정수는 1~10억
        M = Integer.parseInt(tkn.nextToken()); // 쌍 개수 (1~10만)

        arr = new int[N+1]; // 1부터 시작
        for (int i=1; i<=N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            arr[i] = Integer.parseInt(tkn.nextToken());
        }

        // 크기가 n인 배열에 대하여
        // 세그먼트 트리의 높이 h -> log2(n)
        // 필요한 트리 노드 개수 -> 2^(h+1)
        double log2 = Math.ceil(Math.log(N) / Math.log(2)); // 세그먼트 트리 높이
        int nodes = (int) Math.pow(2, log2+1);
        max_seg_tree = new int[nodes];
        min_seg_tree = new int[nodes];
        Arrays.fill(max_seg_tree, 0);
        Arrays.fill(min_seg_tree, 0);
    }

    public static void main(String[] args) throws Exception {
        init();

        makeSegTree_MAX(1, 1, N);
        makeSegTree_MIN(1, 1, N);

        solution();
    }

    public static void solution() throws Exception {
        StringBuilder sb = new StringBuilder();
        StringTokenizer tkn;
        int a, b;
        int find_max, find_min;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            a = Integer.parseInt(tkn.nextToken());
            b = Integer.parseInt(tkn.nextToken());
            find_max = findMax(1, 1, N, a, b);
            find_min = findMin(1, 1, N, a, b);
            sb.append(find_min).append(" ").append(find_max).append(" \n");
        }

        System.out.println(sb);
    }

    public static int makeSegTree_MAX(int nodeNum, int start, int end) {
        // 리프 노드일 경우 노드 번호에 해당 배열 값을 저장하고 그 값을 리턴
        if (start == end) {
            max_seg_tree[nodeNum] = arr[start];
            return max_seg_tree[nodeNum];
        }

        // 아니면 재귀호출 + 구간 합 리스트에 저장
        int mid = (start + end) / 2;
        int left_max = makeSegTree_MAX(nodeNum*2, start, mid);
        int right_max = makeSegTree_MAX(nodeNum*2+1, mid+1, end);

        max_seg_tree[nodeNum] = Math.max(left_max, right_max);
        return max_seg_tree[nodeNum];
    }

    public static int makeSegTree_MIN(int nodeNum, int start, int end) {
        // 리프 노드일 경우 노드 번호에 해당 배열 값을 저장하고 그 값을 리턴
        if (start == end) {
            min_seg_tree[nodeNum] = arr[start];
            return min_seg_tree[nodeNum];
        }

        // 아니면 재귀호출 + 구간 합 리스트에 저장
        int mid = (start + end) / 2;
        int left_min = makeSegTree_MIN(nodeNum*2, start, mid);
        int right_min = makeSegTree_MIN(nodeNum*2+1, mid+1, end);

        min_seg_tree[nodeNum] = Math.min(left_min, right_min);
        return min_seg_tree[nodeNum];
    }

    public static int findMax(int nodeNum, int start, int end, int left, int right) {
        // 찾는 범위를 벗어난 경우 -> 더 검색하지 않음.
        if (start > right || end < left)
            return -1;

        // 범위에 포함된 경우 -> 해당 노드 값 리턴
        if (start >= left && end <= right)
            return max_seg_tree[nodeNum];

        // 범위가 겹친 경우 -> 더 검색을 해야 함. 찾다보면 범위에 맞는 노드가 등장. 아예 없으면 0 리턴
        int mid = (start + end) / 2;
        int find_left = findMax(nodeNum*2, start, mid, left, right);
        int find_right = findMax(nodeNum*2+1, mid+1, end, left, right);
        return Math.max(find_left, find_right);
    }

    public static int findMin(int nodeNum, int start, int end, int left, int right) {
        // 찾는 범위를 벗어난 경우 -> 더 검색하지 않음.
        if (start > right || end < left)
            return INF;

        // 범위에 포함된 경우 -> 해당 노드 값 리턴
        if (start >= left && end <= right)
            return min_seg_tree[nodeNum];

        // 범위가 겹친 경우 -> 더 검색을 해야 함. 찾다보면 범위에 맞는 노드가 등장. 아예 없으면 INF 리턴
        int mid = (start + end) / 2;
        int find_left = findMin(nodeNum*2, start, mid, left, right);
        int find_right = findMin(nodeNum*2+1, mid+1, end, left, right);
        return Math.min(find_left, find_right);
    }

}
