import java.util.*;

class Solution {
    
    static int N;
    static int[] parents;
    static int[] ranks;
    
    static int result = 1000;
    
    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }
    
    static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);
        
        if (p1 == p2) return;
        
        int r1 = ranks[p1];
        int r2 = ranks[p2];
        
        if (r1 == r2) {
            parents[p2] = p1;
            ranks[p1]++;
        }
        else if (r1 > r2) parents[p2] = p1;
        else parents[p1] = p2;
    }
    
    void div(int pass_offset, int[][] wires) {
        for(int i=1; i<=N; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
        
        int n1, n2;
        for(int i=0; i<N-1; i++) {
            if (i == pass_offset) continue;
            n1 = wires[i][0];
            n2 = wires[i][1];
            union(n1, n2);
        }
        
        // 루트를 하나만 찾는다
        int r1 = 0;
        for(int i=1; i<=N; i++) {
            if (parents[i] == i) {
                r1 = i;
                break;
            }
        }
        
        // 찾은 루트노드의 집합의 크기를 구한다
        int count = 0;
        for(int i=1; i<=N; i++) {
            if (findParent(i) == r1) count++;
        }
        
        // 집합은 무조건 2개이므로 나머지 집합의 크기는 (N-count)
        result = Math.min(result, Math.abs(count - (N-count)));
    }
    
    public int solution(int n, int[][] wires) {
        N = n;
        parents = new int[N+1];
        ranks = new int[N+1];
        
        // 1. 각 간선을 제외하고서 union-find로 집합을 생성해본다.
        // 2. 각 집합의 크기 차이를 비교하고, 차이가 가장 적은 것을 저장해서 리턴한다.
        for(int i=0; i<N-1; i++) {
            div(i, wires);
        }
        
        return result;
    }
}