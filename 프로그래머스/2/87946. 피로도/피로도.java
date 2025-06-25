import java.util.*;

class Solution {
    
    static int N;
    static boolean[] visited;
    static int[][] ds;
    static int result;
    
    static void dfs(int cur_hp, int count) {
        if (cur_hp < 0) return;
        
        result = Math.max(result, count);
        
        for(int i=0; i<N; i++) {
            int required = ds[i][0];
            int cost = ds[i][1];
            
            if (visited[i] || cur_hp < required) continue;
            
            visited[i] = true;
            dfs(cur_hp - cost, count+1);
            visited[i] = false;
        }
        
    }
    
    public int solution(int k, int[][] dungeons) {
        N = dungeons.length;
        ds = dungeons;
        visited = new boolean[N];
        Arrays.fill(visited, false);
        
        // dfs를 돌려 가능한 모든 경우의 수를 구하고 방문한 던전의 수를 카운트한다.
        dfs(k, 0);
        
        // 카운트가 가장 큰 경우를 저장해 리턴한다.
        return result;
    }
}