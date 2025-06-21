class Solution {
    
    static int[] parents;
    static int[] ranks;
    
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
    
    public int solution(int n, int[][] computers) {
        // 1. parents, ranks 배열을 선언한다.
        parents = new int[n];
        ranks = new int[n];
        for(int i=0; i<n; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
        
        // 2. 연결되어 있는 두 노드를 union한다.
        for(int i=0; i<n; i++) {
            for(int j=i; j<n; j++) {
                if (computers[i][j] == 1) union(i, j);
            }
        }
        
        // 3. root 노드의 개수를 센다.
        int roots = 0;
        for(int i=0; i<n; i++) {
            if (findParent(i) == i) roots++;
        }
        
        return roots;
    }
}