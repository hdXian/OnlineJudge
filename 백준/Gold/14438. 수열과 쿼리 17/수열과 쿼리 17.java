import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M;
    public static int INF = 1000000001; // 10억 + 1

    public static int[] arr;
    public static int[] seg_tree;

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 수열 크기. 1~10만

        // 수열 입력, 각 요소 1 ~ 10억
        arr = new int[N+1];
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for (int i=1; i<=N; i++) arr[i] = Integer.parseInt(tkn.nextToken());

        // 수열 크기만큼의 리프 노드를 가지는 세그먼트 트리.

        // 높이는 log2(N)의 소수점 올림 값
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));

        // 세그먼트 트리에 필요한 노드 개수는 최대 2^(h+1).
        int nodes = (int) Math.pow(2, h+1);
        seg_tree = new int[nodes];
    }

    public static int makeSegTree(int node, int start, int end) {
        if (start == end) {
            int val = arr[start];
            seg_tree[node] = val;
            return val;
        }

        int mid_idx = (start+end) / 2;
        int l_val = makeSegTree(node*2, start, mid_idx);
        int r_val = makeSegTree(node*2+1, mid_idx+1, end);

        int val = Math.min(l_val, r_val);
        seg_tree[node] = val;
        return val;
    }

    public static int find(int node, int start, int end, int left, int right) {
        // 범위를 벗어나면 INF 리턴
        if (left > end || right < start)
            return INF;

        // 범위에 속하면 해당 노드 값 리턴
        if (start >= left && end <= right)
            return seg_tree[node];

        // 범위가 걸쳐있으면 반으로 나눠서 재귀 호출
        int mid_idx = (start+end)/2;
        int l_val = find(node*2, start, mid_idx, left, right);
        int r_val = find(node*2+1, mid_idx+1, end, left, right);
        return Math.min(l_val, r_val);
    }

    // 수열의 idx번 요소에 해당하는 트리 노드의 번호를 찾는 함수 -> 이진탐색으로 찾기
    public static int findNode(int node, int start, int end, int idx) {
        if (start == end) {
            return node;
        }

        int mid = (start+end)/2;
        if (mid >= idx)
            return findNode(node*2, start, mid, idx);
        else
            return findNode(node*2+1, mid+1, end, idx);
    }

    // idx번 수열의 값을 val로 변경
    public static void update(int idx, int val) {
        int nodeNum = findNode(1, 1, N, idx);
        seg_tree[nodeNum] = val;

        int p_l, p_r;
        while (nodeNum != 1) {
            int p = nodeNum/2;
            p_l = seg_tree[p*2];
            p_r = seg_tree[p*2+1];

            seg_tree[p] = Math.min(p_l, p_r);
            nodeNum = p;
        }

    }

    public static void calc() throws Exception {
        M = Integer.parseInt(reader.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer tkn;
        int t, a, b;
        for (int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            t = Integer.parseInt(tkn.nextToken());
            a = Integer.parseInt(tkn.nextToken());
            b = Integer.parseInt(tkn.nextToken());
            if (t == 2)
                sb.append(find(1, 1, N, a, b)).append("\n");
            else
                update(a, b);
        }

        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        init();
        makeSegTree(1, 1, N);
        calc();
    }

}
