import java.util.*;

class Solution {
    
    static int N;
    static boolean[] visited;
    
    static void dfs(int node, int[][] computers) {
        visited[node] = true;
        
        for(int i=0; i<N; i++) {
            if(!visited[i] && computers[node][i] == 1) {
                dfs(i, computers);
            }
        }
        
    }
    
    public int solution(int n, int[][] computers) {
        // 1. visited 배열을 선언한다.
        N = n;
        visited = new boolean[N];
        Arrays.fill(visited, false);
        
        // 2. dfs를 돌면서 카운트한다.
        int count = 0;
        for(int i=0; i<N; i++) {
            if (!visited[i]) {
                dfs(i, computers);
                count++;
            }
        }
        
        return count;
    }
}