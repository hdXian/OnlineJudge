import java.util.*;

class Solution {
    
    static int N;
    static List<Integer>[] neighbors;
    static boolean[] visited;
    
    static int[] infoSt;
    
    static int result = 0;
    
    static void dfs(List<Integer> routes) {
        int lambs = 0, wolves = 0;
        for(int r: routes) {
            if (infoSt[r] == 0) lambs++;
            else wolves++;
        }
        
        // 늑대가 더 많을 경우 양을 모두 잃으므로 탐색 중단
        if (wolves >= lambs) return;
        
        // 양 업데이트
        result = Math.max(result, lambs);
        
        // 이웃 노드들을 방문한다.
        for(int node: routes) {
            
            for(int neighbor: neighbors[node]) {
                if (visited[neighbor]) continue;
                
                // 다음 노드를 방문
                visited[neighbor] = true;
                
                // 방문했을 경우 형성되는 방문 노드 목록
                List<Integer> next = new ArrayList<>(routes);
                next.add(neighbor);
                
                dfs(next); // 추가된 목록을 가지고 dfs
                
                visited[neighbor] = false; // 백트래킹
            }
            
        }
        
    }
    
    public int solution(int[] info, int[][] edges) {
        // edges 길이: N-1
        N = info.length;
        infoSt = info;
        
        // 1. 인접 리스트를 생성한다.
        neighbors = new List[N];
        for(int i=0; i<N; i++) neighbors[i] = new ArrayList<>();
        
        int src, dst;
        for(int[] edge: edges) {
            src = edge[0];
            dst = edge[1];
            neighbors[src].add(dst);
        }
        
        // 2. 방문 배열을 초기화한다.
        visited = new boolean[N];
        Arrays.fill(visited, false);
        visited[0] = true;
        
        // 2. dfs를 돌려 노드를 방문하는 모든 경우의 수를 계산하고, 양이 가장 많은 경우를 카운트한다.
        List<Integer> routes = new ArrayList<>();
        routes.add(0);
        dfs(routes);
        
        return result;
    }
}